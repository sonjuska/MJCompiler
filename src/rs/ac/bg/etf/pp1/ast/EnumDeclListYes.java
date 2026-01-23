// generated with ast extension for cup
// version 0.8
// 24/0/2026 0:1:43


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclListYes extends EnumDeclList {

    private String I1;
    private NumConstOpt NumConstOpt;
    private EnumDeclList EnumDeclList;

    public EnumDeclListYes (String I1, NumConstOpt NumConstOpt, EnumDeclList EnumDeclList) {
        this.I1=I1;
        this.NumConstOpt=NumConstOpt;
        if(NumConstOpt!=null) NumConstOpt.setParent(this);
        this.EnumDeclList=EnumDeclList;
        if(EnumDeclList!=null) EnumDeclList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public NumConstOpt getNumConstOpt() {
        return NumConstOpt;
    }

    public void setNumConstOpt(NumConstOpt NumConstOpt) {
        this.NumConstOpt=NumConstOpt;
    }

    public EnumDeclList getEnumDeclList() {
        return EnumDeclList;
    }

    public void setEnumDeclList(EnumDeclList EnumDeclList) {
        this.EnumDeclList=EnumDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NumConstOpt!=null) NumConstOpt.accept(visitor);
        if(EnumDeclList!=null) EnumDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NumConstOpt!=null) NumConstOpt.traverseTopDown(visitor);
        if(EnumDeclList!=null) EnumDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NumConstOpt!=null) NumConstOpt.traverseBottomUp(visitor);
        if(EnumDeclList!=null) EnumDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclListYes(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(NumConstOpt!=null)
            buffer.append(NumConstOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDeclList!=null)
            buffer.append(EnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclListYes]");
        return buffer.toString();
    }
}
