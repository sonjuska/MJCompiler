// generated with ast extension for cup
// version 0.8
// 12/2/2026 19:16:31


package rs.ac.bg.etf.pp1.ast;

public class GlobalDeclListEmpty extends GlobalDeclList {

    public GlobalDeclListEmpty () {
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
        buffer.append("GlobalDeclListEmpty(\n");

        buffer.append(tab);
        buffer.append(") [GlobalDeclListEmpty]");
        return buffer.toString();
    }
}
