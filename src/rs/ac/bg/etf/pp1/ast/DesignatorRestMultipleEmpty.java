// generated with ast extension for cup
// version 0.8
// 6/2/2026 20:16:59


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRestMultipleEmpty extends DesignatorRestMultiple {

    public DesignatorRestMultipleEmpty () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRestMultipleEmpty(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRestMultipleEmpty]");
        return buffer.toString();
    }
}
