// generated with ast extension for cup
// version 0.8
// 16/1/2026 20:24:21


package rs.ac.bg.etf.pp1.ast;

public class StmtBlock extends Statement {

    private StatementMultiple StatementMultiple;

    public StmtBlock (StatementMultiple StatementMultiple) {
        this.StatementMultiple=StatementMultiple;
        if(StatementMultiple!=null) StatementMultiple.setParent(this);
    }

    public StatementMultiple getStatementMultiple() {
        return StatementMultiple;
    }

    public void setStatementMultiple(StatementMultiple StatementMultiple) {
        this.StatementMultiple=StatementMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementMultiple!=null) StatementMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementMultiple!=null) StatementMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtBlock(\n");

        if(StatementMultiple!=null)
            buffer.append(StatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtBlock]");
        return buffer.toString();
    }
}
