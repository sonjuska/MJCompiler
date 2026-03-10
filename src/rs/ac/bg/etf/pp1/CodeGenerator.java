package rs.ac.bg.etf.pp1;


import java.util.Map;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import java.util.ArrayDeque;
import java.util.Deque;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private boolean designatorValueEmitted = false;
	private boolean methodReturnEmitted = false;

	private final Deque<Integer> ternaryElseFix = new ArrayDeque<>(); //stekovi za ugnjezdjene ternarne
	private final Deque<Integer> ternaryEndFix  = new ArrayDeque<>();
	
    private final Map<Designator, Obj> baseMap;
    
    //java -cp lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run test/test301.obj za runObj iz terminala

    public CodeGenerator(Map<Designator, Obj> baseMap) {
        this.baseMap = baseMap;
    }
	
	public int getMainPc(){
		return mainPc;
	}

	private int parseCharConst(String s) {

	    //'c'
	    if (s == null || s.length() < 3 || s.charAt(0) != '\'' || s.charAt(s.length() - 1) != '\'') {
	    	System.out.println("Neispravan format char konstante: " + s);
	        return 0;
	    }

	    String inner = s.substring(1, s.length() - 1);

	    if (inner.length() == 1) {
	        return (int) inner.charAt(0);
	    }

	    //escape: '\n', '\r', '\t', '\0', '\'', '\\'
	    if (inner.length() == 2 && inner.charAt(0) == '\\') {
	        char esc = inner.charAt(1);

	        switch (esc) {
	            case 'n':  return 10;
	            case 'r':  return 13;
	            case 't':  return 9;
	            case '0':  return 0;
	            case '\'': return (int) '\'';
	            case '\\': return (int) '\\';

	            default:
	                System.out.println("Nepoznata escape sekvenca u char konstanti: \\" + esc);
	                return 0;
	        }
	    }
	    //sus
	    System.out.println("Neispravna char konstanta: " + s);
	    return 0;
	}
	
	private int relopToCode(Relop r) {
	    if (r instanceof RelopEq)  return Code.eq;
	    if (r instanceof RelopNeq) return Code.ne;
	    if (r instanceof RelopLt)  return Code.lt;
	    if (r instanceof RelopLe)  return Code.le;
	    if (r instanceof RelopGt)  return Code.gt;
	    if (r instanceof RelopGe)  return Code.ge;
	    return Code.eq;
	}
	private int countActPars(ActParsOpt apo) {
	    if (apo instanceof ActParsOptEmpty) return 0;
	    ActPars ap = ((ActParsOptYes)apo).getActPars();

	    int cnt = 1; //prvi Expr u ActPars
	    ExprList list = ap.getExprList();
	    while (list instanceof ExprListYes) {
	        cnt++;
	        list = ((ExprListYes)list).getExprList();
	    }
	    return cnt;
	}
	
	public void visit(MethodTypeName mn) {
		methodReturnEmitted = false;  
	    if ("main".equals(mn.getMethodName())) {
	        mainPc = Code.pc;
	    }

	    Code.put(Code.enter);

	    Code.put(0);    //broj formalnih parametara
	    Code.put(mn.obj.getLocalSymbols().size());  //broj promenljivih
	}
	
	public void visit(MethodDecl md) {
	    if (!methodReturnEmitted) {
	        Code.put(Code.exit);
	        Code.put(Code.return_);
	    }
	}
	public void visit(StmtReturn sr) {
	    //return expr; - expr je vec na steku, bacam
	    if (sr.getExprOpt() instanceof ExprOptYes) {
	        Code.put(Code.pop);
	    }

	    //zavrsavam metodu
	    Code.put(Code.exit);
	    Code.put(Code.return_);

	    methodReturnEmitted = true;
	}
	
	public void visit(DesignatorStatement ds) {
	    Obj lhs = ds.getDesignator().obj;
	    if (lhs == null || lhs == Tab.noObj) return;

	    DesignatorStatementRest r = ds.getDesignatorStatementRest();

	    if (r instanceof DesignatorStmtAssign) {
	        //Expr je vec na steku
	        Code.store(lhs);
	        return;
	    }

	    if (r instanceof DesignatorStmtInc) {
	        //++
	    	if (lhs.getKind() == Obj.Elem) {
	    	    Code.put(Code.dup2);   //duplira [arrayRef, index]
	    	}
	        Code.load(lhs);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(lhs);
	        return;
	    }

	    if (r instanceof DesignatorStmtDec) {
	    	//--
	    	if (lhs.getKind() == Obj.Elem) {
	    	    Code.put(Code.dup2);   //duplira [arrayRef, index]
	    	}
	        Code.load(lhs);
	        Code.loadConst(1);
	        Code.put(Code.sub);
	        Code.store(lhs);
	        return;
	    }

	    if (r instanceof DesignatorStmtCall) {

	        DesignatorStmtCall call = (DesignatorStmtCall) r;
	        ActParsOpt apo = call.getActParsOpt();

	        String mname = lhs.getName();

	        if ("len".equals(mname) || "chr".equals(mname) || "ord".equals(mname)) {

	            int argc = countActPars(apo);
	            if (argc != 1) {
	                //greska - ocisti sve sto je eventualno gurnuto
	                for (int i = 0; i < argc; i++) Code.put(Code.pop);
	                return;
	            }

	            if ("len".equals(mname)) {
	                
	                Code.put(Code.arraylength); //skida ref i gura duzinu
	            } else {

	            }

	            //posto je statement rezultat se ignorise
	            Code.put(Code.pop);
	            return;
	        }

	        //za user metode
	        return;
	    }
	}
	
	
	public void visit(StmtRead r) {
	    Obj dst = r.getDesignator().obj;
	    if (dst == null || dst == Tab.noObj) return;

	    if (dst.getType().equals(Tab.charType)) {
	        Code.put(Code.bread);
	    } else {
	        Code.put(Code.read);
	    }
	    Code.store(dst);
	}
	
	public void visit(StmtPrint p) {
	    int width = 0;
	    if (p.getCommaNumConstOpt() instanceof CommaNumConstOptYes) {
	        width = ((CommaNumConstOptYes)p.getCommaNumConstOpt()).getN1();
	    } else {
	        width = (p.getExpr().struct == Tab.charType) ? 1 : 5;
	    }

	    Code.loadConst(width);

	    if (p.getExpr().struct == Tab.charType) Code.put(Code.bprint);
	    else Code.put(Code.print);
	}
	
	
	//Term := Factor {Mulop Factor}
	//Mulop := "*" | "/" | "%"
	
	//Term ::= (Term) Factor TermRest ;
	//TermRest ::= (TermRestYes) Mulop Factor TermRest
	
	public void visit(TermRestYes tr) {
	    Mulop op = tr.getMulop();
	    if (op instanceof MulopMul){
	    	Code.put(Code.mul);
	    }else if (op instanceof MulopDiv){
	    	Code.put(Code.div);
	    }else{
	    	Code.put(Code.rem);
	    }
	}
	
	//Factor := Factor := numConst | charConst | "(" Expr ")" | boolConst | Designator |"new" Type "[" Expr "]"
	public void visit(FactorNum fn) {
		Code.loadConst(fn.getValue());
	}
	public void visit(FactorChar fc) {
		Code.loadConst(parseCharConst(fc.getValue()));
	}
	public void visit(FactorBool fb) {
		Code.loadConst(fb.getValue());
	}
	public void visit(FactorNewArray f) {
	    Struct elemType = f.getType().struct; 

	    Code.put(Code.newarray);

	    if (elemType.equals(Tab.charType)) {
	        Code.put(0);
	    } else {
	        Code.put(1);
	    }

	}
	public void visit(FactorDesignator f) {
	    Obj o = f.getDesignator().obj;
	    if (o == null || o == Tab.noObj) return;

	    //Designator vec emitovao vrednost - length ili enum konst
	    if (designatorValueEmitted && (f.getFactorRest() instanceof FactorRestEmpty)) {
	        return;
	    }

	    //obican Designator kao faktor tj. ucitavanje vrednosti var,elem,const
	    if (f.getFactorRest() instanceof FactorRestEmpty) {
	        Code.load(o);
	        return;
	    }

	    //Designator(ActParsOpt)
	    if (f.getFactorRest() instanceof FactorRestCall) {
	        FactorRestCall call = (FactorRestCall) f.getFactorRest();
	        ActParsOpt apo = call.getActParsOpt();

	        String mname = o.getName();

	        if ("len".equals(mname) || "chr".equals(mname) || "ord".equals(mname)) {

	            int argc = countActPars(apo);
	            if (argc != 1) {
	                for (int i = 0; i < argc; i++) Code.put(Code.pop);
	                //greska koju prijavljuje semantika svakako
	                Code.loadConst(0);
	                return;
	            }

	            if ("len".equals(mname)) {
	                //array ref je vec na steku 
	                Code.put(Code.arraylength); //skida ref i gura duzinu
	                return;
	            }

	            // chr/ord: identitet (argument vec na steku), ne emituj nista
	            return;
	        }

	        //ocisti arg i ostavi 0
	        int argc = countActPars(apo);
	        for (int i = 0; i < argc; i++) Code.put(Code.pop);
	        Code.loadConst(0);
	        return;
	    }
	}
	
	//Expr ::= [-] Term {Addop Term}
	//Addop := "+" | "-"
	
	//SimpleExpr ::= (SimpleExpr) MinusOpt Term ExprRest ;
	//MinusOpt ::= (MinusOptYes) MINUS
	//ExprRest ::= (ExprRestYes) Addop Term ExprRest
	
	public void visit(SimpleExpr se) {
	    if (se.getMinusOpt() instanceof MinusOptYes) {
	        Code.put(Code.neg);
	    }
	}
	
	public void visit(ExprRestYes er) {

	    Addop op = er.getAddop();
	    if (op instanceof AddopPlus) {    
	        Code.put(Code.add);
	    } else {
	        Code.put(Code.sub);
	    }
	}
	
	public void visit(Designator d) {

	    designatorValueEmitted = false;

	    //pocetni ident
	    Obj cur = baseMap.get(d);
	    if (cur == null || cur == Tab.noObj) {
	        //fail-safe
	        d.obj = Tab.noObj;
	        return;
	    }

	    DesignatorRestMultiple rest = d.getDesignatorRestMultiple();

	    //prolaz kroz nastavke (1 za A nivo)
	    while (!(rest instanceof DesignatorRestMultipleEmpty)) {

	        // -------------------- [ Expr ] --------------------
	        if (rest instanceof DesignatorRestMultipleIndex) {
	            DesignatorRestMultipleIndex idx = (DesignatorRestMultipleIndex) rest;

	            //u bottomUpje Expr za indeks vec obidjen - na steku je index
	            //treba mi [arrayRef, index]

	            //ubacujem referencu niza na stek
	            Code.load(cur);                 //stek: [index, arrayRef]

	            //swap top 2: [index, arrayRef] - [arrayRef, index]
	            //dup_x1 - [arrayRef, index, arrayRef], pa pop - [arrayRef, index]
	            Code.put(Code.dup_x1);
	            Code.put(Code.pop);

	            //sad je designator element niza
	            //(Obj.Elem) - Code.load/Code.store ce koristiti aload/baload ili astore/bastore
	            Struct arrType = cur.getType();
	            Struct elemType = (arrType != null && arrType.getKind() == Struct.Array) ? arrType.getElemType() : Tab.noType;
	            cur = new Obj(Obj.Elem, cur.getName() + "[]", elemType);

	            rest = idx.getDesignatorRestMultiple();
	            continue;
	        }

	        // -------------------- . ident / . length --------------------
	        if (rest instanceof DesignatorRestMultipleDot) {
	            DesignatorRestMultipleDot dot = (DesignatorRestMultipleDot) rest;
	            IdentLength il = dot.getIdentLength();

	            // ---- .length ----
	            if (il instanceof IdentLengthLength) {
	                //samo za nizove
	                Code.load(cur);                 //na stek ide arrayRef
	                Code.put(Code.arraylength);     //skida arrayRef i gura duzinu (int)
	                designatorValueEmitted = true;

	                //napravim obj da d.obj ne bude noObj; adr nije bitan
	                cur = new Obj(Obj.Con, cur.getName() + ".length", Tab.intType);

	                rest = dot.getDesignatorRestMultiple();
	                continue;
	            }

	         // ---- .IDENT ---- (enum konstanta)
	            if (il instanceof IdentLengthIdent) {
	                //semantika je vec postavila d.obj na enum konstantu (Obj.Con)
	                Obj enumCon = d.obj;
	                if (enumCon == null || enumCon == Tab.noObj || enumCon.getKind() != Obj.Con) {
	                    d.obj = Tab.noObj;
	                    return;
	                }

	                Code.load(enumCon);              //gura vrednost enum konstante
	                designatorValueEmitted = true;

	                cur = enumCon;
	                rest = dot.getDesignatorRestMultiple();
	                continue;
	            }

	            d.obj = Tab.noObj;
	            return;
	        }

	        //ako se pojavi nesto nepoznato
	        d.obj = Tab.noObj;
	        return;
	    }

	    //rezultat designatora
	    d.obj = cur;
	}
	
	//CondFact ::= (CondFact) SimpleExpr CondFactRest ;
	//CondFactRest ::= (CondFactRestRel) Relop SimpleExpr
	public void visit(CondFact cf) {
	    CondFactRest rest = cf.getCondFactRest();

	    //ako ima relaciju (left op right) -> na stek stavim 1/0
	    if (rest instanceof CondFactRestRel) {
	        CondFactRestRel r = (CondFactRestRel) rest;
	        int op = relopToCode(r.getRelop());

	        //na steku [left, right]
	        Code.putFalseJump(op, 0);
	        int falseFix = Code.pc - 2;

	        Code.loadConst(1);
	        Code.putJump(0);
	        int endFix = Code.pc - 2;

	        Code.fixup(falseFix);
	        Code.loadConst(0);

	        Code.fixup(endFix);
	        //sad je na steku 1 ili 0
	    } 
	    //ako nema relacije vec imam bool izraz pretpostavim da je na steku 0/1

	    //ako je CondFact deo ExprTernary: odmah posle uslova napravim skok na ELSE ako je uslov 0
	    if (cf.getParent() instanceof ExprTernary) {
	        // na steku je cond (0/1)
	        Code.loadConst(0);                 // stek [cond, 0]
	        Code.putFalseJump(Code.ne, 0);     // jump-if-false za (cond != 0) => false kad cond==0 => ELSE
	        int elseFix = Code.pc - 2;

	        ternaryElseFix.push(elseFix);
	        // jcc popuje operande => stek cist (sto zelimo pre THEN izraza)
	    }
	}
	
	public void visit(TernaryColon tc) {
	    //zavrsili smo THEN izraz (jer je bottom-up), sada:
	    //bezuslovno preskoci ELSE (skoci na END)
	    Code.putJump(0);
	    int endFix = Code.pc - 2;

	    //patchujem ELSE skok da ide ovde (pocetak ELSE koda)
	    int elseFix = ternaryElseFix.pop();
	    Code.fixup(elseFix);

	    //zapamtim END skok za kasnije patchovanje
	    ternaryEndFix.push(endFix);
	}
	
	public void visit(ExprTernary e) {
	    int endFix = ternaryEndFix.pop();
	    Code.fixup(endFix);
	    //posle ovoga, na steku ostaje vrednost ili THEN ili ELSE izraza (tacno jedna)
	}
	

}
