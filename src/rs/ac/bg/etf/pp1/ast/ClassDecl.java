// generated with ast extension for cup
// version 0.8
// 3/2/2026 18:28:13


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ExtendsOpt ExtendsOpt;
    private VarDeclMultiple VarDeclMultiple;
    private MethodDeclMultipleOpt MethodDeclMultipleOpt;

    public ClassDecl (String I1, ExtendsOpt ExtendsOpt, VarDeclMultiple VarDeclMultiple, MethodDeclMultipleOpt MethodDeclMultipleOpt) {
        this.I1=I1;
        this.ExtendsOpt=ExtendsOpt;
        if(ExtendsOpt!=null) ExtendsOpt.setParent(this);
        this.VarDeclMultiple=VarDeclMultiple;
        if(VarDeclMultiple!=null) VarDeclMultiple.setParent(this);
        this.MethodDeclMultipleOpt=MethodDeclMultipleOpt;
        if(MethodDeclMultipleOpt!=null) MethodDeclMultipleOpt.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtendsOpt getExtendsOpt() {
        return ExtendsOpt;
    }

    public void setExtendsOpt(ExtendsOpt ExtendsOpt) {
        this.ExtendsOpt=ExtendsOpt;
    }

    public VarDeclMultiple getVarDeclMultiple() {
        return VarDeclMultiple;
    }

    public void setVarDeclMultiple(VarDeclMultiple VarDeclMultiple) {
        this.VarDeclMultiple=VarDeclMultiple;
    }

    public MethodDeclMultipleOpt getMethodDeclMultipleOpt() {
        return MethodDeclMultipleOpt;
    }

    public void setMethodDeclMultipleOpt(MethodDeclMultipleOpt MethodDeclMultipleOpt) {
        this.MethodDeclMultipleOpt=MethodDeclMultipleOpt;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsOpt!=null) ExtendsOpt.accept(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.accept(visitor);
        if(MethodDeclMultipleOpt!=null) MethodDeclMultipleOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsOpt!=null) ExtendsOpt.traverseTopDown(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseTopDown(visitor);
        if(MethodDeclMultipleOpt!=null) MethodDeclMultipleOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsOpt!=null) ExtendsOpt.traverseBottomUp(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseBottomUp(visitor);
        if(MethodDeclMultipleOpt!=null) MethodDeclMultipleOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtendsOpt!=null)
            buffer.append(ExtendsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclMultiple!=null)
            buffer.append(VarDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclMultipleOpt!=null)
            buffer.append(MethodDeclMultipleOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
