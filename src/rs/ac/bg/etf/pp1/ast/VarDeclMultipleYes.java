// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:37:42


package rs.ac.bg.etf.pp1.ast;

public class VarDeclMultipleYes extends VarDeclMultiple {

    private VarDecl VarDecl;
    private VarDeclMultiple VarDeclMultiple;

    public VarDeclMultipleYes (VarDecl VarDecl, VarDeclMultiple VarDeclMultiple) {
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
        this.VarDeclMultiple=VarDeclMultiple;
        if(VarDeclMultiple!=null) VarDeclMultiple.setParent(this);
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public VarDeclMultiple getVarDeclMultiple() {
        return VarDeclMultiple;
    }

    public void setVarDeclMultiple(VarDeclMultiple VarDeclMultiple) {
        this.VarDeclMultiple=VarDeclMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecl!=null) VarDecl.accept(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclMultipleYes(\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclMultiple!=null)
            buffer.append(VarDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclMultipleYes]");
        return buffer.toString();
    }
}
