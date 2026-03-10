// generated with ast extension for cup
// version 0.8
// 10/2/2026 17:12:47


package rs.ac.bg.etf.pp1.ast;

public class MinusOptEmpty extends MinusOpt {

    public MinusOptEmpty () {
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
        buffer.append("MinusOptEmpty(\n");

        buffer.append(tab);
        buffer.append(") [MinusOptEmpty]");
        return buffer.toString();
    }
}
