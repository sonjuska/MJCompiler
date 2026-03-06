// generated with ast extension for cup
// version 0.8
// 6/2/2026 17:14:9


package rs.ac.bg.etf.pp1.ast;

public class ConditionOptEmpty extends ConditionOpt {

    public ConditionOptEmpty () {
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
        buffer.append("ConditionOptEmpty(\n");

        buffer.append(tab);
        buffer.append(") [ConditionOptEmpty]");
        return buffer.toString();
    }
}
