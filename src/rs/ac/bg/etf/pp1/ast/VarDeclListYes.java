// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListYes extends VarDeclList {

    private String I1;
    private BracketsOpt BracketsOpt;
    private VarDeclList VarDeclList;

    public VarDeclListYes (String I1, BracketsOpt BracketsOpt, VarDeclList VarDeclList) {
        this.I1=I1;
        this.BracketsOpt=BracketsOpt;
        if(BracketsOpt!=null) BracketsOpt.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public BracketsOpt getBracketsOpt() {
        return BracketsOpt;
    }

    public void setBracketsOpt(BracketsOpt BracketsOpt) {
        this.BracketsOpt=BracketsOpt;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BracketsOpt!=null) BracketsOpt.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BracketsOpt!=null) BracketsOpt.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListYes(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(BracketsOpt!=null)
            buffer.append(BracketsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListYes]");
        return buffer.toString();
    }
}
