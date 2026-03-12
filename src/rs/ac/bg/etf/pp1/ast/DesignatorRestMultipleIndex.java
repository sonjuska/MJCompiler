// generated with ast extension for cup
// version 0.8
// 12/2/2026 19:16:31


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRestMultipleIndex extends DesignatorRestMultiple {

    private Expr Expr;
    private DesignatorRestMultiple DesignatorRestMultiple;

    public DesignatorRestMultipleIndex (Expr Expr, DesignatorRestMultiple DesignatorRestMultiple) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.DesignatorRestMultiple=DesignatorRestMultiple;
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public DesignatorRestMultiple getDesignatorRestMultiple() {
        return DesignatorRestMultiple;
    }

    public void setDesignatorRestMultiple(DesignatorRestMultiple DesignatorRestMultiple) {
        this.DesignatorRestMultiple=DesignatorRestMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRestMultipleIndex(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorRestMultiple!=null)
            buffer.append(DesignatorRestMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRestMultipleIndex]");
        return buffer.toString();
    }
}
