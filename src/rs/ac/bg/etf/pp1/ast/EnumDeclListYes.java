// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclListYes extends EnumDeclList {

    private String enumItemName;
    private NumConstOpt NumConstOpt;
    private EnumDeclList EnumDeclList;

    public EnumDeclListYes (String enumItemName, NumConstOpt NumConstOpt, EnumDeclList EnumDeclList) {
        this.enumItemName=enumItemName;
        this.NumConstOpt=NumConstOpt;
        if(NumConstOpt!=null) NumConstOpt.setParent(this);
        this.EnumDeclList=EnumDeclList;
        if(EnumDeclList!=null) EnumDeclList.setParent(this);
    }

    public String getEnumItemName() {
        return enumItemName;
    }

    public void setEnumItemName(String enumItemName) {
        this.enumItemName=enumItemName;
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

        buffer.append(" "+tab+enumItemName);
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
