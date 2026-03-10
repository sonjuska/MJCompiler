// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class OrCondTermMultipleYes extends OrCondTermMultiple {

    private CondTerm CondTerm;
    private OrCondTermMultiple OrCondTermMultiple;

    public OrCondTermMultipleYes (CondTerm CondTerm, OrCondTermMultiple OrCondTermMultiple) {
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
        this.OrCondTermMultiple=OrCondTermMultiple;
        if(OrCondTermMultiple!=null) OrCondTermMultiple.setParent(this);
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public OrCondTermMultiple getOrCondTermMultiple() {
        return OrCondTermMultiple;
    }

    public void setOrCondTermMultiple(OrCondTermMultiple OrCondTermMultiple) {
        this.OrCondTermMultiple=OrCondTermMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTerm!=null) CondTerm.accept(visitor);
        if(OrCondTermMultiple!=null) OrCondTermMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
        if(OrCondTermMultiple!=null) OrCondTermMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        if(OrCondTermMultiple!=null) OrCondTermMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OrCondTermMultipleYes(\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OrCondTermMultiple!=null)
            buffer.append(OrCondTermMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OrCondTermMultipleYes]");
        return buffer.toString();
    }
}
