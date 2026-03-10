// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class ExtendsOptEmpty extends ExtendsOpt {

    public ExtendsOptEmpty () {
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
        buffer.append("ExtendsOptEmpty(\n");

        buffer.append(tab);
        buffer.append(") [ExtendsOptEmpty]");
        return buffer.toString();
    }
}
