// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class StmtSwitch extends Statement {

    private Expr Expr;
    private CaseMultiple CaseMultiple;

    public StmtSwitch (Expr Expr, CaseMultiple CaseMultiple) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CaseMultiple=CaseMultiple;
        if(CaseMultiple!=null) CaseMultiple.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
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
        if(Expr!=null) Expr.accept(visitor);
        if(CaseMultiple!=null) CaseMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CaseMultiple!=null) CaseMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CaseMultiple!=null) CaseMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtSwitch(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseMultiple!=null)
            buffer.append(CaseMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtSwitch]");
        return buffer.toString();
    }
}
