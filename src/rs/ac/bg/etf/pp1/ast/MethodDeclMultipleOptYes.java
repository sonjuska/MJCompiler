// generated with ast extension for cup
// version 0.8
// 10/2/2026 20:42:37


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclMultipleOptYes extends MethodDeclMultipleOpt {

    private MethodDeclMultiple MethodDeclMultiple;

    public MethodDeclMultipleOptYes (MethodDeclMultiple MethodDeclMultiple) {
        this.MethodDeclMultiple=MethodDeclMultiple;
        if(MethodDeclMultiple!=null) MethodDeclMultiple.setParent(this);
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
        if(MethodDeclMultiple!=null) MethodDeclMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclMultiple!=null) MethodDeclMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclMultiple!=null) MethodDeclMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclMultipleOptYes(\n");

        if(MethodDeclMultiple!=null)
            buffer.append(MethodDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclMultipleOptYes]");
        return buffer.toString();
    }
}
