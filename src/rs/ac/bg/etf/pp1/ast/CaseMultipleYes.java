// generated with ast extension for cup
// version 0.8
// 12/2/2026 19:16:31


package rs.ac.bg.etf.pp1.ast;

public class CaseMultipleYes extends CaseMultiple {

    private Integer N1;
    private StatementMultiple StatementMultiple;
    private CaseMultiple CaseMultiple;

    public CaseMultipleYes (Integer N1, StatementMultiple StatementMultiple, CaseMultiple CaseMultiple) {
        this.N1=N1;
        this.StatementMultiple=StatementMultiple;
        if(StatementMultiple!=null) StatementMultiple.setParent(this);
        this.CaseMultiple=CaseMultiple;
        if(CaseMultiple!=null) CaseMultiple.setParent(this);
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public StatementMultiple getStatementMultiple() {
        return StatementMultiple;
    }

    public void setStatementMultiple(StatementMultiple StatementMultiple) {
        this.StatementMultiple=StatementMultiple;
    }

    public CaseMultiple getCaseMultiple() {
        return CaseMultiple;
    }

    public void setCaseMultiple(CaseMultiple CaseMultiple) {
        this.CaseMultiple=CaseMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementMultiple!=null) StatementMultiple.accept(visitor);
        if(CaseMultiple!=null) CaseMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseTopDown(visitor);
        if(CaseMultiple!=null) CaseMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementMultiple!=null) StatementMultiple.traverseBottomUp(visitor);
        if(CaseMultiple!=null) CaseMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseMultipleYes(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        if(StatementMultiple!=null)
            buffer.append(StatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseMultiple!=null)
            buffer.append(CaseMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseMultipleYes]");
        return buffer.toString();
    }
}
