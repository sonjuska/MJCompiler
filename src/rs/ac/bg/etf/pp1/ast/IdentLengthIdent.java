// generated with ast extension for cup
// version 0.8
// 12/2/2026 19:16:31


package rs.ac.bg.etf.pp1.ast;

public class IdentLengthIdent extends IdentLength {

    private String ident;

    public IdentLengthIdent (String ident) {
        this.ident=ident;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
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
        buffer.append("IdentLengthIdent(\n");

        buffer.append(" "+tab+ident);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentLengthIdent]");
        return buffer.toString();
    }
}
