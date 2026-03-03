package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

    private boolean errorDetected = false;

    private Obj currentMethod = Tab.noObj;
    //private Struct currentDeclType = Tab.noType;     //Type za VarDecl/ConstDecl
    private Struct currentVarBaseType = Tab.noType;  // za VarDeclList (ponavljanje)
    private Struct currentConstDeclType = Tab.noType;
    private boolean currentVarIsArray = false;
    private Obj currentEnumType = Tab.noObj;
    private int currentEnumValue = 0;
    private boolean enumOverrideSet = false;
    private int enumOverrideValue = 0;
    private java.util.HashSet<Integer> enumUsedValues = new java.util.HashSet<>();
    private Obj currentDesignatorObj = Tab.noObj;
    private java.util.HashSet<Obj> enumTypes = new java.util.HashSet<>();


    private int varDeclCount = 0;
    private int printCallCount = 0;
    private int formParamCount = 0;

    boolean mainFound = false;
    int mainParamCount = -1;     
    private Struct mainReturnType = null;  // mora Tab.noType (void)     

    private boolean returnFound = false;
    private int nVars = 0;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
    private boolean alreadyDeclaredInCurrentScope(String name) {
        return Tab.currentScope().findSymbol(name) != null;
    }
    
    private String kindToString(int kind) {
        switch (kind) {
            case Obj.Var:  return "promenljiva";
            case Obj.Con:  return "konstanta";
            case Obj.Meth: return "metoda";
            case Obj.Type: return "tip";
            case Obj.Prog: return "program";
            case Obj.Fld:  return "polje";
            case Obj.Elem: return "element niza";
            default:       return "simbol";
        }
    }

    private String typeToString(Struct t) {
        if (t == null) return "<?>";

        if (t == Tab.noType) return "void";
        if (t == Tab.intType) return "int";
        if (t == Tab.charType) return "char";

        Obj boolObj = Tab.find("bool");
        if (boolObj != Tab.noObj && boolObj.getKind() == Obj.Type && t.equals(boolObj.getType())) {
            return "bool";
        }

        if (t.getKind() == Struct.Array) {
            return typeToString(t.getElemType()) + "[]";
        }

        // za sve ostalo (npr. enum/klase) - nema lepog imena bez dodatnih mapa,
        // ali makar vrati kind:
        if (t.getKind() == Struct.Class) return "class";
        return "type(kind=" + t.getKind() + ")";
    }
    
    //proverava da li je nesto promenljiva, element niza ili polje objekta
    private boolean isLValue(Obj o) {
        if (o == null || o == Tab.noObj) return false;
        return o.getKind() == Obj.Var || o.getKind() == Obj.Elem || o.getKind() == Obj.Fld;
    }

    
    private Struct boolType() {
        Obj b = Tab.find("bool");
        return (b != Tab.noObj && b.getKind() == Obj.Type) ? b.getType() : Tab.noType;
    }

    //proverava da li je tip Struct cvora bool
    private boolean isBool(Struct t) {
        return t != null && t.equals(boolType());
    }

    //proverava da li je tip Struct cvora int, char ili bool
    private boolean isIntCharBool(Struct t) {
        return t != null && (t.equals(Tab.intType) || t.equals(Tab.charType) || isBool(t));
    }

    //ispisuje deklaracije promenljivih i konstanti 
    private void report_decl(Obj obj, SyntaxNode where, String extra) {
        if (obj == null || obj == Tab.noObj) return;

        String msg = "Deklarisano: " + kindToString(obj.getKind())
                + " '" + obj.getName() + "' : " + typeToString(obj.getType());

        if (extra != null && !extra.isEmpty()) msg += " " + extra;

        report_info(msg, where);
    }
    private void declareVar(String name, Struct baseType, boolean isArray, SyntaxNode where) {
        if (alreadyDeclaredInCurrentScope(name)) {
            report_error("Promenljiva " + name + " je vec deklarisana u istom opsegu", where);
            return;
        }
        Struct t = baseType;
        if (isArray) t = new Struct(Struct.Array, baseType);
        Obj v = Tab.insert(Obj.Var, name, t);
        if (isArray) report_decl(v, where, "(niz)");
        else report_decl(v, where, "");
    }
    
    //provera broja i tipova argumenat standardnih metoda
    private void checkStdCall(String mname, ActParsOpt apo, SyntaxNode where) {
        if (apo instanceof ActParsOptEmpty) {
            report_error("Poziv standardne metode mora imati tacno 1 argument.", where);
            return;
        }

        ActPars ap = ((ActParsOptYes) apo).getActPars();
        Struct at = ap.getExpr().struct;

        if (!(ap.getExprList() instanceof ExprListEmpty)) {
            report_error("Poziv standardne metode mora imati tacno 1 argument.", where);
        }

        if ("chr".equals(mname)) {
            if (!Tab.intType.equals(at)) report_error("chr(e): e mora biti izraz tipa int.", where);
        } else if ("ord".equals(mname)) {
            if (!Tab.charType.equals(at)) report_error("ord(c): c mora biti tipa char.", where);
        } else { // len
            if (at == null || at.getKind() != Struct.Array) {
                report_error("len(a): a mora biti niz.", where);
            }
        }
    }


    
	
	//program
	
    //Program ::= (ProgramDecl) PROGRAM ProgName GlobalDeclList LBRACE MethodDeclList RBRACE ;
    public void visit(ProgramDecl p) {
        //zatvaranje programa + tsdump broj var
        nVars = Tab.currentScope().getnVars();
        Tab.chainLocalSymbols(p.getProgName().obj);
        Tab.closeScope();

        if (!mainFound) {
            report_error("Nedostaje metoda void main()", p); // main() mora postojati
        }
    }
    //ProgName ::= (ProgName) IDENT:progName ;
    public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
	
    public void visit(StmtPrint p) {
        printCallCount++;
        if (!isIntCharBool(p.getExpr().struct)) {  //proverava tip izraza u StmtPrint
            report_error("PRINT: izraz mora biti tipa int, char ili bool.", p);
        }
    }
    
    //Type ::= (TypeIdent) IDENT:typeName ;
    public void visit(TypeIdent t) {
        Obj typeNode = Tab.find(t.getTypeName());  //nadjemo Obj cvor datog tipa u tabeli simbola
        if (typeNode == Tab.noObj || typeNode.getKind() != Obj.Type) {
            report_error("Ime " + t.getTypeName() + " ne predstavlja tip!", t);
            t.struct = Tab.noType;
        } else {
            t.struct = typeNode.getType();
        }
        //currentDeclType = t.struct;
    }
    
    //ConstDecl
    
    //ConstItem ::= (ConstItemNum) NUMCONST
    //        | (ConstItemChar) CHARCONST
    //        | (ConstItemBool) BOOLCONST ;
    
    public void visit(ConstItemNum n) {
        n.struct = Tab.intType;
    }

    public void visit(ConstItemChar c) {
        c.struct = Tab.charType;
    }

    public void visit(ConstItemBool b) {
        Obj boolType = Tab.find("bool");
        if (boolType == Tab.noObj || boolType.getKind() != Obj.Type) {
            report_error("Tip bool nije definisan u univerzumu", b);
            b.struct = Tab.noType;
        } else {
            b.struct = boolType.getType();
        }
    }

    //ConstDecl ::= (ConstDecl) CONST Type:constType IDENT:constName ASSIGN ConstItem ConstDeclList SEMI;
    public void visit(ConstDecl c) {
        currentConstDeclType = c.getType().struct;

        String name = c.getConstName();
        Struct itemType = c.getConstItem().struct;
        
        if (!currentConstDeclType.equals(itemType)) {
            report_error("Tip konstante " + name + " nije kompatibilan sa tipom deklaracije", c);
        }
        if (Tab.currentScope().findSymbol(name) != null) {
            report_error("Simbol " + name + " je vec deklarisan u istom opsegu", c);
            return;
        }
        Obj con = Tab.insert(Obj.Con, name, currentConstDeclType);
        report_decl(con, c, "");

        // (opciono) upisi vrednost u adr (ako ti kasnije treba u CodeGenerator-u)
        // con.setAdr(extractConstValue(c.getConstItem()));
    }
    
    //ConstDeclList ::= (ConstDeclListYes) COMMA IDENT:constName ASSIGN ConstItem ConstDeclList
    public void visit(ConstDeclListYes cl) {
        String name = cl.getConstName();

        Struct itemType = cl.getConstItem().struct;

        if (!currentConstDeclType.equals(itemType)) {
            report_error("Tip konstante " + name + " nije kompatibilan sa tipom deklaracije", cl);
        }

        if (Tab.currentScope().findSymbol(name) != null) {
            report_error("Simbol " + name + " je vec deklarisan u istom opsegu", cl);
            return;
        }

        Obj con = Tab.insert(Obj.Con, name, currentConstDeclType);
        report_decl(con, cl, "");

        // (opciono) vrednost:
        // con.setAdr(extractConstValue(cl.getConstItem()));
    }
    
    //VarDecl
    
    //VarDecl ::= (VarDeclOk) Type:varType IDENT:varName BracketsOpt VarDeclTail
    public void visit(VarDeclOk v) {
        currentVarBaseType = v.getType().struct;
        currentVarIsArray = v.getBracketsOpt() instanceof BracketsOptYes;

        String name = v.getVarName();
        declareVar(name, currentVarBaseType, currentVarIsArray, v);
    }

    //VarDeclList ::= (VarDeclListYes) COMMA IDENT:varName BracketsOpt VarDeclList
    public void visit(VarDeclListYes vl) {
        String name = vl.getVarName();
        boolean isArr = vl.getBracketsOpt() instanceof BracketsOptYes;
        declareVar(name, currentVarBaseType, isArr, vl);
    }
    
    //MethodDecl (samo provere za main)
    
    //MethodDecl ::= (MethodDecl) MethodTypeName LPAREN FormParsOpt RPAREN VarDeclMultiple LBRACE StatementMultiple RBRACE ;
    public void visit(MethodDecl md) {

        if (currentMethod != null) {
            String name = currentMethod.getName();

            //main mora biti void i bez parametara
            if ("main".equals(name)) {
                if (currentMethod.getType() != Tab.noType) {
                    report_error("Metoda main mora biti deklarisana kao void!", md);
                }
                if (formParamCount != 0) {
                    report_error("Metoda main ne sme imati formalne parametre!", md);
                }
                mainFound = true;
                mainParamCount = formParamCount;
            }

        }

        //pakujem lokalne simbole metode i zatvaram scope
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        // reset
        currentMethod = null;
        returnFound = false;   
        formParamCount = 0;
    }
    
    //MethodTypeName ::= (MethodTypeName) ReturnType IDENT:methodName;
    public void visit(MethodTypeName mtn) {
    	
    	formParamCount = 0;
        String name = mtn.getMethodName();

        //povratni tip
        Struct retType;
        if (mtn.getReturnType() instanceof ReturnTypeVoid) {
            retType = Tab.noType;
        } else {
            retType = ((ReturnTypeType) mtn.getReturnType()).getType().struct;
        }

        if (Tab.currentScope().findSymbol(name) != null) {
            report_error("Ime metode '" + name + "' je vec deklarisano u ovom opsegu.", mtn);
        }

        currentMethod = Tab.insert(Obj.Meth, name, retType);
        mtn.obj = currentMethod;

        Tab.openScope();

        if ("main".equals(name)) {
            mainFound = true;
            mainReturnType = retType; 
        }
    }
    
    //FormPars ::= (FormPars) Type:formParsType IDENT:formParsName BracketsOpt FormParsList;
    public void visit(FormPars fp) {
        formParamCount = 1;
    }
    //FormParsList ::= (FormParsListYes) COMMA Type:formParsType IDENT:formParsName BracketsOpt FormParsList
    public void visit(FormParsListYes fp) {
        formParamCount++;   
    }
    
    //EnumDecl ::= (EnumDecl) ENUM EnumName LBRACE IDENT:enumItemName NumConstOpt EnumDeclList RBRACE ;
    public void visit(EnumDecl ed) {
        String item = ed.getEnumItemName();

        if (currentEnumType == Tab.noObj) {
            return;
        }

        if (Tab.currentScope().findSymbol(item) != null) {
            report_error("Enum polje " + item + " je vec deklarisano u istom enum-u", ed);
        } else {
        	int val = enumOverrideSet ? enumOverrideValue : currentEnumValue;

        	Obj c = Tab.insert(Obj.Con, item, Tab.intType);
        	c.setAdr(val);
        	
        	if (!enumUsedValues.add(val)) {
        	    report_error("Enum vrednost " + val + " nije jedinstvena u okviru ovog enum-a", ed);
        	}

        	currentEnumValue = val + 1;
        	enumOverrideSet = false;

        }

        Tab.chainLocalSymbols(currentEnumType);
        Tab.closeScope();

        currentEnumType = Tab.noObj;
        currentEnumValue = 0;
    }
    
    //EnumName ::= (EnumName) IDENT:enumName ;
    public void visit(EnumName en) {
        String enumName = en.getEnumName();

        if (Tab.currentScope().findSymbol(enumName) != null) {
            report_error("Tip " + enumName + " je vec deklarisan u istom opsegu.", en);
            currentEnumType = Tab.noObj;
            return;
        }

        currentEnumType = Tab.insert(Obj.Type, enumName, Tab.intType);
        enumTypes.add(currentEnumType);
        Tab.openScope();
        
        currentEnumValue = 0;
        enumOverrideSet = false;
        enumUsedValues.clear();

    }
    //EnumDeclList ::= (EnumDeclListYes) COMMA IDENT:enumItemName NumConstOpt EnumDeclList
    public void visit(EnumDeclListYes el) {
        String item = el.getEnumItemName();

        if (Tab.currentScope().findSymbol(item) != null) {
            report_error("Enum polje " + item + " je vec deklarisano u istom enum-u", el);
            return;
        }

        int val = enumOverrideSet ? enumOverrideValue : currentEnumValue;

        Obj c = Tab.insert(Obj.Con, item, Tab.intType);
        c.setAdr(val);
        if (!enumUsedValues.add(val)) {
            report_error("Enum vrednost " + val + " nije jedinstvena u okviru ovog enum-a", el);
        }

        currentEnumValue = val + 1;
        enumOverrideSet = false;

    }
    
    //NumConstOpt ::= (NumConstOptAssign) ASSIGN NUMCONST
    public void visit(NumConstOptAssign nc) {
        enumOverrideSet = true;
        enumOverrideValue = nc.getN1();
    }
    
    //Designator ::= (Designator) IDENT:name DesignatorRestMultiple ;
    public void visit(Designator d) {

        Obj cur = Tab.find(d.getName());
        if (cur == Tab.noObj) {
            report_error("Simbol '" + d.getName() + "' nije deklarisan.", d);
            d.obj = Tab.noObj;
            return;
        }

        // 2) prodji kroz DesignatorRestMultiple lanac
        DesignatorRestMultiple rest = d.getDesignatorRestMultiple();

        
        while (!(rest instanceof DesignatorRestMultipleEmpty)) {

            //DesignatorRestMultiple ::= (DesignatorRestMultipleIndex) LBRACKET Expr RBRACKET DesignatorRestMultiple
            if (rest instanceof DesignatorRestMultipleIndex) {
                DesignatorRestMultipleIndex idx = (DesignatorRestMultipleIndex) rest;

                // levi mora biti niz
                Struct lt = cur.getType();
                if (lt.getKind() != Struct.Array) {
                    report_error("Indeksiranje [] je dozvoljeno samo nad nizom.", d);
                    d.obj = Tab.noObj;
                    return;
                }

                // Expr mora biti int
                if (idx.getExpr().struct != Tab.intType) {
                    report_error("Indeks niza mora biti tipa int.", idx);
                }

                // rezultat je element niza (Obj.Elem)
                cur = new Obj(Obj.Elem, cur.getName() + "[]", lt.getElemType());

                rest = idx.getDesignatorRestMultiple();
                continue;
            }

            //DesignatorRestMultiple ::= (DesignatorRestMultipleDot) DOT IdentLength DesignatorRestMultiple
            if (rest instanceof DesignatorRestMultipleDot) {
                DesignatorRestMultipleDot dot = (DesignatorRestMultipleDot) rest;
                IdentLength il = dot.getIdentLength();

                // IdentLength ::= (IdentLengthLength) LENGTH ;
                if (il instanceof IdentLengthLength) {
                    if (cur.getType().getKind() != Struct.Array) {
                        report_error("'.length' je dozvoljeno samo nad nizom.", d);
                        d.obj = Tab.noObj;
                        return;
                    }
                    // length vraca int
                    cur = new Obj(Obj.Con, cur.getName() + ".length", Tab.intType);

                    // (po zelji strogo) ne dozvoli dalje posle length
                    if (!(dot.getDesignatorRestMultiple() instanceof DesignatorRestMultipleEmpty)) {
                        report_error("Nakon '.length' nije dozvoljeno dalje grananje designatora.", d);
                        d.obj = Tab.noObj;
                        return;
                    }

                    rest = dot.getDesignatorRestMultiple();
                    continue;
                }

                //IdentLength ::= (IdentLengthIdent) IDENT:ident
                if (il instanceof IdentLengthIdent) {
                    String member = ((IdentLengthIdent) il).getIdent();
                    Struct lt = cur.getType();
                    
                 // ENUM: Broj.NULA
                    if (cur.getKind() == Obj.Type && enumTypes.contains(cur)) {
                        Obj found = null;

                        for (Obj c : cur.getLocalSymbols()) {          // enum konstante su lokalni simboli tog tipa
                            if (c.getName().equals(member)) {
                                found = c;
                                break;
                            }
                        }

                        if (found == null) {
                            report_error("Enum '" + cur.getName() + "' nema konstantu '" + member + "'.", dot);
                            d.obj = Tab.noObj;
                            return;
                        }

                        cur = found;                                   // sad je to Obj.Con (konstanta)
                        rest = dot.getDesignatorRestMultiple();
                        continue;
                    }

                    // enum: ovde ti treba dodatna informacija (jer ti enum = intType),
                    // pa za sada prijavi gresku (ili ostavi kao TODO)
                    report_error("Operator '.' je dozvoljen samo nad klasom (ili enum kad ga dopunis).", d);
                    d.obj = Tab.noObj;
                    return;
                }

                report_error("Nepoznat oblik posle '.'", d);
                d.obj = Tab.noObj;
                return;
            }

            report_error("Nepoznata forma designator nastavka.", d);
            d.obj = Tab.noObj;
            return;
        }

        // 3) postavi rezultat
        d.obj = cur;

        if (cur.getKind() == Obj.Var) {
            report_info("Koriscenje promenljive '" + cur.getName() + "'", d);
        } else if (cur.getKind() == Obj.Con) {
            report_info("Koriscenje konstante '" + cur.getName() + "'", d);
        } else if (cur.getKind() == Obj.Meth) {
            report_info("Koriscenje metode '" + cur.getName() + "'", d);
        }
    }
    public void visit(DesignatorStatement ds) {
        Obj lhs = ds.getDesignator().obj;
        DesignatorStatementRest r = ds.getDesignatorStatementRest();

        if (lhs == null || lhs == Tab.noObj) return;

        //1) dodela
        if (r instanceof DesignatorStmtAssign) {
            DesignatorStmtAssign asg = (DesignatorStmtAssign) r;
            Struct rhs = asg.getExpr().struct;

            if (!isLValue(lhs)) {
                report_error("Leva strana dodele mora biti promenljiva, element niza ili polje objekta.", ds);
                return;
            }
            if (lhs.getType() == Tab.noType || rhs == Tab.noType) return;

            if (!rhs.assignableTo(lhs.getType())) {
                report_error("Nekompatibilni tipovi u dodeli: " +
                        typeToString(lhs.getType()) + " = " + typeToString(rhs), ds);
            }
            return;
        }

        //2) ++
        if (r instanceof DesignatorStmtInc) {
            if (!isLValue(lhs)) {
                report_error("Operator ++ je dozvoljen samo nad promenljivom, elementom niza ili poljem objekta.", ds);
                return;
            }
            if (!lhs.getType().equals(Tab.intType)) {
                report_error("Operator ++ je dozvoljen samo nad int tipom.", ds);
            }
            return;
        }

        //3) --
        if (r instanceof DesignatorStmtDec) {
            if (!isLValue(lhs)) {
                report_error("Operator -- je dozvoljen samo nad promenljivom, elementom niza ili poljem objekta.", ds);
                return;
            }
            if (!lhs.getType().equals(Tab.intType)) {
                report_error("Operator -- je dozvoljen samo nad int tipom.", ds);
            }
            return;
        }

     //4) Poziv funkcije/metode
        if (r instanceof DesignatorStmtCall) {
            if (lhs.getKind() != Obj.Meth) {
                report_error("Poziv sa () je dozvoljen samo nad metodom/funkcijom.", ds);
                return;
            }

            DesignatorStmtCall call = (DesignatorStmtCall) r;
            ActParsOpt apo = call.getActParsOpt();

            String mname = lhs.getName();
            if ("chr".equals(mname) || "ord".equals(mname) || "len".equals(mname)) {
                checkStdCall(mname, apo, ds);
            }

            //ovde provera za user metode
            return;
        }

    }

    public void visit(FactorNum f) {
        f.struct = Tab.intType;
    }

    public void visit(FactorChar f) {
        f.struct = Tab.charType;
    }

    public void visit(FactorBool f) {
        Obj bt = Tab.find("bool");
        f.struct = (bt != Tab.noObj && bt.getKind() == Obj.Type) ? bt.getType() : Tab.noType;
        if (f.struct == Tab.noType) report_error("Tip bool nije definisan.", f);
    }

    public void visit(FactorParen f) {
        f.struct = f.getExpr().struct;
    }

    public void visit(FactorNewArray f) {
        // NEW Type [ Expr ]
        if (f.getExpr().struct != Tab.intType) {
            report_error("Velicina niza u new Type[Expr] mora biti int.", f);
        }
        f.struct = new Struct(Struct.Array, f.getType().struct);
    }

    public void visit(FactorDesignator f) {
        // Designator FactorRest
        Obj o = f.getDesignator().obj;

        if (o == null || o == Tab.noObj) {
            f.struct = Tab.noType;
            return;
        }

        // ako je poziv: Designator(...)
        if (f.getFactorRest() instanceof FactorRestCall) {
            if (o.getKind() != Obj.Meth) {
                report_error("Poziv sa () je dozvoljen samo nad metodom/funkcijom.", f);
                f.struct = Tab.noType;
                return;
            }

            FactorRestCall call = (FactorRestCall) f.getFactorRest();
            ActParsOpt apo = call.getActParsOpt();

            String mname = o.getName();
            if ("chr".equals(mname) || "ord".equals(mname) || "len".equals(mname)) {
                checkStdCall(mname, apo, f);
            }

            f.struct = o.getType();
            return;
        
        } else {
            f.struct = o.getType();
        }

    }
    
    public void visit(TermRestEmpty tr) {
        tr.struct = Tab.noType; // oznaka "nema nastavka"
    }

    public void visit(TermRestYes tr) {
        // Mulop Factor TermRest
        if (tr.getFactor().struct != Tab.intType) {
            report_error("Factor u mnozenju/deljenju/mod mora biti int.", tr);
        }
        if (tr.getTermRest().struct != Tab.noType && tr.getTermRest().struct != Tab.intType) {
            report_error("TermRest u mnozenju/deljenju/mod mora biti int.", tr);
        }
        tr.struct = Tab.intType;
    }

    public void visit(Term t) {
        // Factor TermRest
        if (t.getTermRest().struct == Tab.noType) {
            t.struct = t.getFactor().struct;   // samo Factor
        } else {
            // ima * / % lanac - mora int
            if (t.getFactor().struct != Tab.intType) {
                report_error("Levi operand u * / % mora biti int.", t);
            }
            t.struct = Tab.intType;
        }
    }
    
    public void visit(ExprRestEmpty er) {
        er.struct = Tab.noType; // "nema nastavka"
    }

    public void visit(ExprRestYes er) {
        // Addop Term ExprRest
        if (er.getTerm().struct != Tab.intType) {
            report_error("Term u sabiranju/oduzimanju mora biti int.", er);
        }
        if (er.getExprRest().struct != Tab.noType && er.getExprRest().struct != Tab.intType) {
            report_error("ExprRest u sabiranju/oduzimanju mora biti int.", er);
        }
        er.struct = Tab.intType;
    }

    //SimpleExpr ::= (SimpleExpr) MinusOpt Term ExprRest ;
    public void visit(SimpleExpr se) {
    	boolean hasMinus = se.getMinusOpt() instanceof MinusOptYes;

        if (hasMinus && se.getTerm().struct != Tab.intType) {
            report_error("Unary minus je dozvoljen samo nad int.", se);
        }

        if (se.getExprRest().struct == Tab.noType) {
            // nema +/-
            se.struct = se.getTerm().struct;
        } else {
            // ima +/-
            if (se.getTerm().struct != Tab.intType) {
                report_error("Levi operand u + ili - mora biti int.", se);
            }
            se.struct = Tab.intType;
        }
    }

    public void visit(CondFactRestEmpty cfr) {
        cfr.struct = Tab.noType; // nema relacije
    }

    public void visit(CondFactRestRel cfr) {
        //konkretan operator proveravamo u visit(CondFact)
    }


    public void visit(CondFact cf) {
    	Struct boolT = boolType();

        // 1) Ako nema relacije: SimpleExpr mora biti bool (za A nivo ternary uslov)
        if (cf.getCondFactRest() instanceof CondFactRestEmpty) {
            if (!cf.getSimpleExpr().struct.equals(boolT)) {
                report_error("Uslov (bez relacije) mora biti tipa bool.", cf);
            }
            cf.struct = boolT;
            return;
        }

        // 2) Ima relaciju: Expr Relop Expr (kod tebe je SimpleExpr + desni SimpleExpr)
        CondFactRestRel rel = (CondFactRestRel) cf.getCondFactRest();

        Struct t1 = cf.getSimpleExpr().struct;
        Struct t2 = rel.getSimpleExpr().struct;

        if (t1 == null || t2 == null || t1 == Tab.noType || t2 == Tab.noType) {
            cf.struct = boolT;
            return;
        }

        // (a) kompatibilnost tipova: ekvivalentni ili ref-null (u oba smera)
        boolean okCompat = t1.compatibleWith(t2);
        if (!okCompat) {
            report_error("Tipovi u relacijskom izrazu nisu kompatibilni: " +
                    typeToString(t1) + " i " + typeToString(t2), cf);
        }

        // (b) izvuci operator: == ili != ili ostali
        Relop op = rel.getRelop();
        boolean isEq  = (op instanceof RelopEq);   // ==  
        boolean isNeq = (op instanceof RelopNeq);  // !=

        // (c) reference pravilo: uz niz/null dozvoljeno samo == i !=
        boolean refLike1 = t1.isRefType();
        boolean refLike2 = t2.isRefType();

        if (refLike1 || refLike2) {
            if (!(isEq || isNeq)) {
                report_error("Za tip klase ili niza dozvoljeni su samo == i !=.", cf);
            }
            cf.struct = boolT;
            return;
        }

        // (d) A nivo (po tvojoj dosadasnjoj logici): relacijski <,>,<=,>= samo nad int
        if (!(isEq || isNeq)) { // dakle <,>,<=,>=
            if (!t1.equals(Tab.intType) || !t2.equals(Tab.intType)) {
                report_error("Relacijski operatori (<,>,<=,>=) ocekuju int.", cf);
            }
        }

        cf.struct = boolT;
    }


    public void visit(ExprSimple e) {
        e.struct = e.getSimpleExpr().struct;
    }

    public void visit(ExprTernary e) {
        Struct boolT = boolType();

        Struct condT = (e.getCondFact() != null) ? e.getCondFact().struct : Tab.noType;
        if (!boolT.equals(condT)) {
            report_error("Uslov u ternarnom operatoru mora biti bool.", e.getCondFact() != null ? e.getCondFact() : e);
        }

        Struct tThen = (e.getExpr()  != null) ? e.getExpr().struct  : Tab.noType;
        Struct tElse = (e.getExpr1() != null) ? e.getExpr1().struct : Tab.noType;

        if (tThen == Tab.noType || tElse == Tab.noType) {
            e.struct = Tab.noType;
            return;
        }

        if (!tThen.equals(tElse)) {
            report_error("Drugi i treci izraz u ?: moraju biti istog tipa.", e.getExpr1() != null ? e.getExpr1() : e);
            e.struct = Tab.noType;
            return;
        }

        e.struct = tThen;
    }


    public void visit(NumConstOptEmpty nc) {
        
    }
    
    //Statement ::= (StmtRead) READ LPAREN Designator RPAREN SEMI
    public void visit(StmtRead r) {
        Obj d = r.getDesignator().obj;
        if (d == null || d == Tab.noObj) return;

        if (!isLValue(d)) {
            report_error("READ: designator mora biti promenljiva, element niza ili polje objekta.", r);
            return;
        }

        if (!isIntCharBool(d.getType())) {
            report_error("READ: dozvoljeni tipovi su int, char ili bool.", r);
        }
    }

    //Statement ::= (StmtReturn) RETURN ExprOpt SEMI
    public void visit(StmtReturn sr) {
        returnFound = true;
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
}
