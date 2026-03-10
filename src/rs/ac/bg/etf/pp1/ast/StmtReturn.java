// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class StmtReturn extends Statement {

    private ExprOpt ExprOpt;

    public StmtReturn (ExprOpt ExprOpt) {
        this.ExprOpt=ExprOpt;
        if(ExprOpt!=null) ExprOpt.setParent(this);
    }

    public ExprOpt getExprOpt() {
        return ExprOpt;
    }

    public void setExprOpt(ExprOpt ExprOpt) {
        this.ExprOpt=ExprOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprOpt!=null) ExprOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprOpt!=null) ExprOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprOpt!=null) ExprOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtReturn(\n");

        if(ExprOpt!=null)
            buffer.append(ExprOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtReturn]");
        return buffer.toString();
    }
}
