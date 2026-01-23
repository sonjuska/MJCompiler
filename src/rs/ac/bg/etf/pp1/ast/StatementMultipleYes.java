// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class StatementMultipleYes extends StatementMultiple {

    private Statement Statement;
    private StatementMultiple StatementMultiple;

    public StatementMultipleYes (Statement Statement, StatementMultiple StatementMultiple) {
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.StatementMultiple=StatementMultiple;
        if(StatementMultiple!=null) StatementMultiple.setParent(this);
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
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
        if(Statement!=null) Statement.accept(visitor);
        if(StatementMultiple!=null) StatementMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementMultipleYes(\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementMultiple!=null)
            buffer.append(StatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementMultipleYes]");
        return buffer.toString();
    }
}
