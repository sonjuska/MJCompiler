// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private DesignatorRestMultiple DesignatorRestMultiple;

    public Designator (String I1, DesignatorRestMultiple DesignatorRestMultiple) {
        this.I1=I1;
        this.DesignatorRestMultiple=DesignatorRestMultiple;
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public DesignatorRestMultiple getDesignatorRestMultiple() {
        return DesignatorRestMultiple;
    }

    public void setDesignatorRestMultiple(DesignatorRestMultiple DesignatorRestMultiple) {
        this.DesignatorRestMultiple=DesignatorRestMultiple;
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
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(DesignatorRestMultiple!=null)
            buffer.append(DesignatorRestMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
