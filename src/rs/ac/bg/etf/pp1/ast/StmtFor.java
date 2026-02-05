// generated with ast extension for cup
// version 0.8
// 5/1/2026 20:46:12


package rs.ac.bg.etf.pp1.ast;

public class StmtFor extends Statement {

    private DesignatorStatementOpt DesignatorStatementOpt;
    private ConditionOpt ConditionOpt;
    private DesignatorStatementOpt DesignatorStatementOpt1;
    private Statement Statement;

    public StmtFor (DesignatorStatementOpt DesignatorStatementOpt, ConditionOpt ConditionOpt, DesignatorStatementOpt DesignatorStatementOpt1, Statement Statement) {
        this.DesignatorStatementOpt=DesignatorStatementOpt;
        if(DesignatorStatementOpt!=null) DesignatorStatementOpt.setParent(this);
        this.ConditionOpt=ConditionOpt;
        if(ConditionOpt!=null) ConditionOpt.setParent(this);
        this.DesignatorStatementOpt1=DesignatorStatementOpt1;
        if(DesignatorStatementOpt1!=null) DesignatorStatementOpt1.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public DesignatorStatementOpt getDesignatorStatementOpt() {
        return DesignatorStatementOpt;
    }

    public void setDesignatorStatementOpt(DesignatorStatementOpt DesignatorStatementOpt) {
        this.DesignatorStatementOpt=DesignatorStatementOpt;
    }

    public ConditionOpt getConditionOpt() {
        return ConditionOpt;
    }

    public void setConditionOpt(ConditionOpt ConditionOpt) {
        this.ConditionOpt=ConditionOpt;
    }

    public DesignatorStatementOpt getDesignatorStatementOpt1() {
        return DesignatorStatementOpt1;
    }

    public void setDesignatorStatementOpt1(DesignatorStatementOpt DesignatorStatementOpt1) {
        this.DesignatorStatementOpt1=DesignatorStatementOpt1;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementOpt!=null) DesignatorStatementOpt.accept(visitor);
        if(ConditionOpt!=null) ConditionOpt.accept(visitor);
        if(DesignatorStatementOpt1!=null) DesignatorStatementOpt1.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementOpt!=null) DesignatorStatementOpt.traverseTopDown(visitor);
        if(ConditionOpt!=null) ConditionOpt.traverseTopDown(visitor);
        if(DesignatorStatementOpt1!=null) DesignatorStatementOpt1.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementOpt!=null) DesignatorStatementOpt.traverseBottomUp(visitor);
        if(ConditionOpt!=null) ConditionOpt.traverseBottomUp(visitor);
        if(DesignatorStatementOpt1!=null) DesignatorStatementOpt1.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtFor(\n");

        if(DesignatorStatementOpt!=null)
            buffer.append(DesignatorStatementOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionOpt!=null)
            buffer.append(ConditionOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementOpt1!=null)
            buffer.append(DesignatorStatementOpt1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtFor]");
        return buffer.toString();
    }
}
