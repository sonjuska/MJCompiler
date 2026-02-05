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
	
    public void visit(StmtPrint print) {
		printCallCount++;
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

        String name = v.getVarName(); // PROVERI getter!
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

    public void visit(NumConstOptEmpty nc) {
        
    }
    
    public void visit(StmtReturn sr) {
        returnFound = true;
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
}
