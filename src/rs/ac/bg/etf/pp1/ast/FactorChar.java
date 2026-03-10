// generated with ast extension for cup
// version 0.8
// 10/2/2026 17:12:47


package rs.ac.bg.etf.pp1.ast;

public class FactorChar extends Factor {

    private String value;

    public FactorChar (String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
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
        buffer.append("FactorChar(\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorChar]");
        return buffer.toString();
    }
}
