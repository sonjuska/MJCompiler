// generated with ast extension for cup
// version 0.8
// 16/1/2026 20:24:21


package rs.ac.bg.etf.pp1.ast;

public class BracketsOptEmpty extends BracketsOpt {

    public BracketsOptEmpty () {
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
        buffer.append("BracketsOptEmpty(\n");

        buffer.append(tab);
        buffer.append(") [BracketsOptEmpty]");
        return buffer.toString();
    }
}
