// generated with ast extension for cup
// version 0.8
// 11/2/2026 17:40:25


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String name;
    private DesignatorRestMultiple DesignatorRestMultiple;

    public Designator (String name, DesignatorRestMultiple DesignatorRestMultiple) {
        this.name=name;
        this.DesignatorRestMultiple=DesignatorRestMultiple;
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
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

        buffer.append(" "+tab+name);
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
