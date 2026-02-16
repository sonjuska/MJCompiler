package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

    private boolean errorDetected = false;

    private Obj currentMethod = Tab.noObj;
    private Struct currentDeclType = Tab.noType;     //Type za VarDecl/ConstDecl
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

    boolean mainFound = false;
    int mainParamCount = -1;     
    private Struct mainReturnType = null;  // mora Tab.noType (void)

    int formParamCount = 0;        


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
    
    private boolean isLValue(Obj o) {
        if (o == null || o == Tab.noObj) return false;
        return o.getKind() == Obj.Var || o.getKind() == Obj.Elem || o.getKind() == Obj.Fld;
    }

    private boolean assignable(Struct dst, Struct src) {
        return dst.equals(src); 
    }
    private boolean isBoolType(Struct t) {
        Obj b = Tab.find("bool");
        return (b != Tab.noObj && b.getKind() == Obj.Type && t.equals(b.getType()));
    }

    private boolean isIntCharBool(Struct t) {
        if (t == null) return false;
        return t.equals(Tab.intType) || t.equals(Tab.charType) || isBoolType(t);
    }



    private void report_decl(Obj obj, SyntaxNode where, String extra) {
        if (obj == null || obj == Tab.noObj) return;

        String msg = "Deklarisano: " + kindToString(obj.getKind())
                + " '" + obj.getName() + "' : " + typeToString(obj.getType());

        if (extra != null && !extra.isEmpty()) msg += " " + extra;

        report_info(msg, where);
    }

    
	
	//program
	
    public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    public void visit(ProgramDecl p) {
        //zatvaranje programa + tsdump broj var
        nVars = Tab.currentScope().getnVars();
        Tab.chainLocalSymbols(p.getProgName().obj);
        Tab.closeScope();

        if (!mainFound) {
            report_error("Nedostaje metoda void main()", p); // main() mora postojati
        }
    }
	
    public void visit(StmtPrint p) {
        printCallCount++;

        Struct t = p.getExpr().struct;
        if (t == null || t == Tab.noType) return;

        // Expr mora biti int/char/bool
        if (!isIntCharBool(t)) {
            report_error("PRINT: izraz mora biti tipa int, char ili bool.", p);
        }
    }
    
    //Type
    public void visit(TypeIdent t) {
        Obj typeNode = Tab.find(t.getTypeName());
        if (typeNode == Tab.noObj || typeNode.getKind() != Obj.Type) {
            report_error("Ime " + t.getTypeName() + " ne predstavlja tip!", t);
            t.struct = Tab.noType;
        } else {
            t.struct = typeNode.getType();
        }
        currentDeclType = t.struct;
    }
    
    //ConstDecl
    public void visit(ConstItemNum n) {
        n.struct = Tab.intType;
    }

    public void visit(ConstItemChar c) {
        c.struct = Tab.charType;
    }

    public void visit(ConstItemBool b) {
        Obj boolType = Tab.find("bool"); // ili Tab.boolType ako imate
        if (boolType == Tab.noObj || boolType.getKind() != Obj.Type) {
            report_error("Tip bool nije definisan u univerzumu", b);
            b.struct = Tab.noType;
        } else {
            b.struct = boolType.getType();
        }
    }

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
    
    // ---------------- VAR DECL ----------------
    // VarDeclOk ::= Type:varType IDENT BracketsOpt VarDeclTail

    public void visit(VarDeclOk v) {
        currentVarBaseType = v.getType().struct;
        currentVarIsArray = v.getBracketsOpt() instanceof BracketsOptYes;

        String name = v.getVarName();
        declareVar(name, currentVarBaseType, currentVarIsArray, v);
        
    }

    // VarDeclListYes ::= COMMA IDENT BracketsOpt VarDeclList
    public void visit(VarDeclListYes vl) {
        String name = vl.getVarName();
        boolean isArr = vl.getBracketsOpt() instanceof BracketsOptYes;
        declareVar(name, currentVarBaseType, isArr, vl);
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
    
    //MethodDecl
    private Struct resolveReturnType(ReturnType rt) {
        if (rt instanceof ReturnTypeVoid) return Tab.noType;
        return ((ReturnTypeType) rt).getType().struct;
    }
    
    public void visit(MethodTypeName mtn) {
        // nova metoda pocinje
        currentMethod = null;
        returnFound = false;
        formParamCount = 0;

        String name = mtn.getMethodName();

        // 1) izracunaj povratni tip (Struct)
        Struct retType = resolveReturnType(mtn.getReturnType());

        // 2) provera duplikata u istom opsegu
        if (Tab.currentScope().findSymbol(name) != null) {
            report_error("Ime metode '" + name + "' je vec deklarisano u ovom opsegu!", mtn);
            // da bi analiza mogla da nastavi, ipak napravi "metodu" (ali prijavi gresku)
        }

        // 3) ubaci metodu u tabelu simbola (uvek jednom)
        currentMethod = Tab.insert(Obj.Meth, name, retType);
        mtn.obj = currentMethod;

        // 4) otvori scope metode (formalni + lokalni idu ovde)
        Tab.openScope();

        report_info("Obrada deklaracije metode: " + name, mtn);

        // 5) main info (proveru void i 0 param radis u visit(MethodDecl))
        if ("main".equals(name)) {
            mainFound = true;
            mainReturnType = retType; // mora Tab.noType
        }
    }
    private Struct applyBrackets(Struct base, BracketsOpt br) {
        if (br instanceof BracketsOptYes) {
            return new Struct(Struct.Array, base);
        }
        return base;
    }

    public void visit(FormPars fp) {
        String pname = fp.getFormParsName(); // IDENT:formParsName
        Struct ptype = applyBrackets(fp.getType().struct, fp.getBracketsOpt());

        if (Tab.currentScope().findSymbol(pname) != null) {
            report_error("Formalni parametar '" + pname + "' je vec deklarisan u metodi!", fp);
        } else {
            Obj p = Tab.insert(Obj.Var, pname, ptype);
            // (opciono) p.setFpPos(formParamCount);  // ako tvoj Obj ima fpPos (cesto ima) 
        }
        formParamCount++;
    }
    public void visit(FormParsListYes fp) {
        String pname = fp.getFormParsName();  
        Struct ptype = applyBrackets(fp.getType().struct, fp.getBracketsOpt());

        if (Tab.currentScope().findSymbol(pname) != null) {
            report_error("Formalni parametar '" + pname + "' je vec deklarisan u metodi!", fp);
        } else {
            Tab.insert(Obj.Var, pname, ptype);
        }
        formParamCount++;
    }


    
    public void visit(MethodDecl md) {
        // 1) provera return za ne-void
        if (currentMethod != null && currentMethod.getType() != Tab.noType) {
            if (!returnFound) {
                report_error("Metoda '" + currentMethod.getName() + "' nema return iskaz, a nije void!", md);
            }
        }

        // 2) ako je main, proveri uslove
        if (currentMethod != null && "main".equals(currentMethod.getName())) {
            // void?
            if (currentMethod.getType() != Tab.noType) {
                report_error("Metoda main mora biti deklarisana kao void!", md);
            }
            // bez argumenata?
            if (formParamCount != 0) {
                report_error("Metoda main ne sme imati formalne argumente!", md);
            }
            // zapamti da kasnije (na kraju programa) mozes dodatno proveriti
            mainParamCount = formParamCount;
        }

        // 3) spakuj lokalne simbole metode i zatvori scope
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        // 4) reset
        currentMethod = null;
        returnFound = false;
        formParamCount = 0;
    }

    public void visit(EnumName en) {
        String enumName = en.getEnumName();

        if (Tab.currentScope().findSymbol(enumName) != null) {
            report_error("Tip " + enumName + " je vec deklarisan u istom opsegu", en);
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
    
    public void visit(NumConstOptAssign nc) {
        enumOverrideSet = true;
        enumOverrideValue = nc.getN1();
    }
    
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

            // ---------- [ Expr ] ----------
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

            // ---------- . IdentLength ----------
            if (rest instanceof DesignatorRestMultipleDot) {
                DesignatorRestMultipleDot dot = (DesignatorRestMultipleDot) rest;
                IdentLength il = dot.getIdentLength();

                // ----- .length -----
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

                // ----- .IDENT -----
                if (il instanceof IdentLengthIdent) {
                    // PAZNJA: proveri tacan getter u tvojoj AST klasi.
                    // Nekad je getIdent(), nekad getI1(), nekad getName()...
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

                    // klasa: trazi clana u kolekciji po imenu clana
                    if (lt.getKind() == Struct.Class) {
                        Obj found = null;

                        Object mem = lt.getMembers();

                        if (mem instanceof rs.etf.pp1.symboltable.structure.SymbolDataStructure) {
                            rs.etf.pp1.symboltable.structure.SymbolDataStructure sds =
                                    (rs.etf.pp1.symboltable.structure.SymbolDataStructure) mem;

                            found = sds.searchKey(member);   // pronadje polje/metodu po imenu
                        } else if (mem instanceof java.util.Collection) {
                            for (Object o : (java.util.Collection) mem) {
                                Obj m = (Obj) o;
                                if (m.getName().equals(member)) { found = m; break; }
                            }
                        }

                        if (found == null) {
                            report_error("Klasa nema clana '" + member + "'.", dot);
                            d.obj = Tab.noObj;
                            return;
                        }

                        // pomeri dalje kroz lanac designatora
                        cur = found;
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

        // 1) DODELA
        if (r instanceof DesignatorStmtAssign) {
            DesignatorStmtAssign asg = (DesignatorStmtAssign) r;
            Struct rhs = asg.getExpr().struct;

            if (!isLValue(lhs)) {
                report_error("Leva strana dodele mora biti promenljiva, element niza ili polje objekta.", ds);
                return;
            }
            if (lhs.getType() == Tab.noType || rhs == Tab.noType) return;

            if (!assignable(lhs.getType(), rhs)) {
                report_error("Nekompatibilni tipovi u dodeli: " +
                        typeToString(lhs.getType()) + " = " + typeToString(rhs), ds);
            }
            return;
        }

        // 2) ++
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

        // 3) --
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

        // 4) Poziv funkcije/metode (A nivo: obavezno samo da je Meth)
        if (r instanceof DesignatorStmtCall) {
            if (lhs.getKind() != Obj.Meth) {
                report_error("Poziv sa () je dozvoljen samo nad metodom/funkcijom.", ds);
            }
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
            } else {
                f.struct = o.getType(); // povratni tip metode
            }
        } else {
            // obican designator (promenljiva/konstanta/elem/fld)
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

    public void visit(SimpleExpr se) {
        // MinusOpt Term ExprRest
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
        // Relop SimpleExpr
        cfr.struct = Tab.intType; // marker ima relaciju
    }

    public void visit(CondFact cf) {
        // SimpleExpr CondFactRest
        Obj bt = Tab.find("bool");
        Struct boolType = (bt != Tab.noObj && bt.getKind() == Obj.Type) ? bt.getType() : Tab.noType;

        if (cf.getCondFactRest().struct == Tab.noType) {
            // bez relacije - SimpleExpr mora biti bool (ovo ti treba za bt ? x : y)
            if (!cf.getSimpleExpr().struct.equals(boolType)) {
                report_error("Uslov (bez relacije) mora biti tipa bool.", cf);
            }
        } else {

            SimpleExpr right = ((CondFactRestRel)cf.getCondFactRest()).getSimpleExpr();
            if (cf.getSimpleExpr().struct != Tab.intType || right.struct != Tab.intType) {
                report_error("Relacijski operatori (>,>=,<,<=) ocekuju int (minimalna provera).", cf);
            }
        }

        cf.struct = boolType;
    }

    public void visit(AndCondFactMultipleEmpty a) { a.struct = Tab.noType; }
    public void visit(AndCondFactMultipleYes a)   { a.struct = Tab.intType; } 

    public void visit(CondTerm ct) {
        Obj bt = Tab.find("bool");
        Struct boolType = (bt != Tab.noObj && bt.getKind() == Obj.Type) ? bt.getType() : Tab.noType;

        if (!ct.getCondFact().struct.equals(boolType)) {
            report_error("Operand '&&' mora biti bool.", ct);
        }
        ct.struct = boolType;
    }

    public void visit(OrCondTermMultipleEmpty o) { o.struct = Tab.noType; }
    public void visit(OrCondTermMultipleYes o)  { o.struct = Tab.intType; } 

    public void visit(Condition c) {
        Obj bt = Tab.find("bool");
        Struct boolType = (bt != Tab.noObj && bt.getKind() == Obj.Type) ? bt.getType() : Tab.noType;

        if (!c.getCondTerm().struct.equals(boolType)) {
            report_error("Operand '||' mora biti bool.", c);
        }
        c.struct = boolType;
    }

    public void visit(ExprSimple e) {
        e.struct = e.getSimpleExpr().struct;
    }

    public void visit(ExprTernary e) {
        // CondFact ? Expr : Expr
        Obj bt = Tab.find("bool");
        Struct boolType = (bt != Tab.noObj && bt.getKind() == Obj.Type) ? bt.getType() : Tab.noType;

        if (!e.getCondFact().struct.equals(boolType)) {
            report_error("Uslov u ternarnom operatoru mora biti bool.", e);
        }
        if (!e.getExpr().struct.equals(e.getExpr1().struct)) { // proveri: cesto su getExpr() i getExpr1()
            report_error("Drugi i treci izraz u ?: moraju biti istog tipa.", e);
        }
        e.struct = e.getExpr().struct;
    }



    public void visit(NumConstOptEmpty nc) {
        
    }
    public void visit(StmtRead r) {
        Obj d = r.getDesignator().obj;

        if (d == null || d == Tab.noObj) return;

        // Designator mora biti promenljiva/elem niza/polje
        if (!isLValue(d)) {
            report_error("READ: designator mora biti promenljiva, element niza ili polje objekta.", r);
            return;
        }

        // Tip mora biti int/char/bool
        if (!isIntCharBool(d.getType())) {
            report_error("READ: dozvoljeni tipovi su int, char ili bool.", r);
        }
    }


    
    public void visit(StmtReturn sr) {
        returnFound = true;
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
}
