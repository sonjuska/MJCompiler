// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class CondFact implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private SimpleExpr SimpleExpr;
    private CondFactRest CondFactRest;

    public CondFact (SimpleExpr SimpleExpr, CondFactRest CondFactRest) {
        this.SimpleExpr=SimpleExpr;
        if(SimpleExpr!=null) SimpleExpr.setParent(this);
        this.CondFactRest=CondFactRest;
        if(CondFactRest!=null) CondFactRest.setParent(this);
    }

    public SimpleExpr getSimpleExpr() {
        return SimpleExpr;
    }

    public void setSimpleExpr(SimpleExpr SimpleExpr) {
        this.SimpleExpr=SimpleExpr;
    }

    public CondFactRest getCondFactRest() {
        return CondFactRest;
    }

    public void setCondFactRest(CondFactRest CondFactRest) {
        this.CondFactRest=CondFactRest;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SimpleExpr!=null) SimpleExpr.accept(visitor);
        if(CondFactRest!=null) CondFactRest.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SimpleExpr!=null) SimpleExpr.traverseTopDown(visitor);
        if(CondFactRest!=null) CondFactRest.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SimpleExpr!=null) SimpleExpr.traverseBottomUp(visitor);
        if(CondFactRest!=null) CondFactRest.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact(\n");

        if(SimpleExpr!=null)
            buffer.append(SimpleExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFactRest!=null)
            buffer.append(CondFactRest.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact]");
        return buffer.toString();
    }
}
