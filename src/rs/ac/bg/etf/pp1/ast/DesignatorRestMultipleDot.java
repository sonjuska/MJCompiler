// generated with ast extension for cup
// version 0.8
// 6/2/2026 20:16:59


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRestMultipleDot extends DesignatorRestMultiple {

    private IdentLength IdentLength;
    private DesignatorRestMultiple DesignatorRestMultiple;

    public DesignatorRestMultipleDot (IdentLength IdentLength, DesignatorRestMultiple DesignatorRestMultiple) {
        this.IdentLength=IdentLength;
        if(IdentLength!=null) IdentLength.setParent(this);
        this.DesignatorRestMultiple=DesignatorRestMultiple;
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.setParent(this);
    }

    public IdentLength getIdentLength() {
        return IdentLength;
    }

    public void setIdentLength(IdentLength IdentLength) {
        this.IdentLength=IdentLength;
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
        if(IdentLength!=null) IdentLength.accept(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentLength!=null) IdentLength.traverseTopDown(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentLength!=null) IdentLength.traverseBottomUp(visitor);
        if(DesignatorRestMultiple!=null) DesignatorRestMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRestMultipleDot(\n");

        if(IdentLength!=null)
            buffer.append(IdentLength.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorRestMultiple!=null)
            buffer.append(DesignatorRestMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRestMultipleDot]");
        return buffer.toString();
    }
}
