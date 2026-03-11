// generated with ast extension for cup
// version 0.8
// 11/2/2026 17:40:25


package rs.ac.bg.etf.pp1.ast;

public class NumConstOptEmpty extends NumConstOpt {

    public NumConstOptEmpty () {
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
        buffer.append("NumConstOptEmpty(\n");

        buffer.append(tab);
        buffer.append(") [NumConstOptEmpty]");
        return buffer.toString();
    }
}
