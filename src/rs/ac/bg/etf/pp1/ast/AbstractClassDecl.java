// generated with ast extension for cup
// version 0.8
// 11/2/2026 17:40:25


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ExtendsOpt ExtendsOpt;
    private MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt;

    public AbstractClassDecl (String I1, ExtendsOpt ExtendsOpt, MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt) {
        this.I1=I1;
        this.ExtendsOpt=ExtendsOpt;
        if(ExtendsOpt!=null) ExtendsOpt.setParent(this);
        this.MOrAMDeclMultipleOpt=MOrAMDeclMultipleOpt;
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.setParent(this);
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

    public MOrAMDeclMultipleOpt getMOrAMDeclMultipleOpt() {
        return MOrAMDeclMultipleOpt;
    }

    public void setMOrAMDeclMultipleOpt(MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt) {
        this.MOrAMDeclMultipleOpt=MOrAMDeclMultipleOpt;
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
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsOpt!=null) ExtendsOpt.traverseTopDown(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsOpt!=null) ExtendsOpt.traverseBottomUp(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtendsOpt!=null)
            buffer.append(ExtendsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MOrAMDeclMultipleOpt!=null)
            buffer.append(MOrAMDeclMultipleOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDecl]");
        return buffer.toString();
    }
}
