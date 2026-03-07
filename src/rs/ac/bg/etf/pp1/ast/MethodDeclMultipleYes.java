// generated with ast extension for cup
// version 0.8
// 7/2/2026 23:40:7


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclMultipleYes extends MethodDeclMultiple {

    private MethodDecl MethodDecl;
    private MethodDeclMultiple MethodDeclMultiple;

    public MethodDeclMultipleYes (MethodDecl MethodDecl, MethodDeclMultiple MethodDeclMultiple) {
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
        this.MethodDeclMultiple=MethodDeclMultiple;
        if(MethodDeclMultiple!=null) MethodDeclMultiple.setParent(this);
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public MethodDeclMultiple getMethodDeclMultiple() {
        return MethodDeclMultiple;
    }

    public void setMethodDeclMultiple(MethodDeclMultiple MethodDeclMultiple) {
        this.MethodDeclMultiple=MethodDeclMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDecl!=null) MethodDecl.accept(visitor);
        if(MethodDeclMultiple!=null) MethodDeclMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
        if(MethodDeclMultiple!=null) MethodDeclMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        if(MethodDeclMultiple!=null) MethodDeclMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclMultipleYes(\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclMultiple!=null)
            buffer.append(MethodDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclMultipleYes]");
        return buffer.toString();
    }
}
