// generated with ast extension for cup
// version 0.8
// 5/1/2026 20:46:12


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOk extends VarDecl {

    private Type Type;
    private String varName;
    private BracketsOpt BracketsOpt;
    private VarDeclTail VarDeclTail;

    public VarDeclOk (Type Type, String varName, BracketsOpt BracketsOpt, VarDeclTail VarDeclTail) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.varName=varName;
        this.BracketsOpt=BracketsOpt;
        if(BracketsOpt!=null) BracketsOpt.setParent(this);
        this.VarDeclTail=VarDeclTail;
        if(VarDeclTail!=null) VarDeclTail.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public BracketsOpt getBracketsOpt() {
        return BracketsOpt;
    }

    public void setBracketsOpt(BracketsOpt BracketsOpt) {
        this.BracketsOpt=BracketsOpt;
    }

    public VarDeclTail getVarDeclTail() {
        return VarDeclTail;
    }

    public void setVarDeclTail(VarDeclTail VarDeclTail) {
        this.VarDeclTail=VarDeclTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(BracketsOpt!=null) BracketsOpt.accept(visitor);
        if(VarDeclTail!=null) VarDeclTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseTopDown(visitor);
        if(VarDeclTail!=null) VarDeclTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseBottomUp(visitor);
        if(VarDeclTail!=null) VarDeclTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOk(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(BracketsOpt!=null)
            buffer.append(BracketsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclTail!=null)
            buffer.append(VarDeclTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOk]");
        return buffer.toString();
    }
}
