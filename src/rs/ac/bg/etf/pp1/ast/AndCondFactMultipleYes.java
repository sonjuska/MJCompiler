// generated with ast extension for cup
// version 0.8
// 7/2/2026 23:40:7


package rs.ac.bg.etf.pp1.ast;

public class AndCondFactMultipleYes extends AndCondFactMultiple {

    private CondFact CondFact;
    private AndCondFactMultiple AndCondFactMultiple;

    public AndCondFactMultipleYes (CondFact CondFact, AndCondFactMultiple AndCondFactMultiple) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.AndCondFactMultiple=AndCondFactMultiple;
        if(AndCondFactMultiple!=null) AndCondFactMultiple.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public AndCondFactMultiple getAndCondFactMultiple() {
        return AndCondFactMultiple;
    }

    public void setAndCondFactMultiple(AndCondFactMultiple AndCondFactMultiple) {
        this.AndCondFactMultiple=AndCondFactMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(AndCondFactMultiple!=null) AndCondFactMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(AndCondFactMultiple!=null) AndCondFactMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(AndCondFactMultiple!=null) AndCondFactMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AndCondFactMultipleYes(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AndCondFactMultiple!=null)
            buffer.append(AndCondFactMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AndCondFactMultipleYes]");
        return buffer.toString();
    }
}
