// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class StatementMultipleEmpty extends StatementMultiple {

    public StatementMultipleEmpty () {
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
        buffer.append("StatementMultipleEmpty(\n");

        buffer.append(tab);
        buffer.append(") [StatementMultipleEmpty]");
        return buffer.toString();
    }
}
