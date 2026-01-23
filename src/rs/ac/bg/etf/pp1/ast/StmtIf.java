// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class StmtIf extends Statement {

    private Condition Condition;
    private Statement Statement;
    private ElseStatementOpt ElseStatementOpt;

    public StmtIf (Condition Condition, Statement Statement, ElseStatementOpt ElseStatementOpt) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseStatementOpt=ElseStatementOpt;
        if(ElseStatementOpt!=null) ElseStatementOpt.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseStatementOpt getElseStatementOpt() {
        return ElseStatementOpt;
    }

    public void setElseStatementOpt(ElseStatementOpt ElseStatementOpt) {
        this.ElseStatementOpt=ElseStatementOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseStatementOpt!=null) ElseStatementOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseStatementOpt!=null) ElseStatementOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseStatementOpt!=null) ElseStatementOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtIf(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStatementOpt!=null)
            buffer.append(ElseStatementOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtIf]");
        return buffer.toString();
    }
}
