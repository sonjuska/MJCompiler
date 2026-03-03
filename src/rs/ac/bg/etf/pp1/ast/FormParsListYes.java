// generated with ast extension for cup
// version 0.8
// 3/2/2026 18:28:13


package rs.ac.bg.etf.pp1.ast;

public class FormParsListYes extends FormParsList {

    private Type Type;
    private String formParsName;
    private BracketsOpt BracketsOpt;
    private FormParsList FormParsList;

    public FormParsListYes (Type Type, String formParsName, BracketsOpt BracketsOpt, FormParsList FormParsList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formParsName=formParsName;
        this.BracketsOpt=BracketsOpt;
        if(BracketsOpt!=null) BracketsOpt.setParent(this);
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormParsName() {
        return formParsName;
    }

    public void setFormParsName(String formParsName) {
        this.formParsName=formParsName;
    }

    public BracketsOpt getBracketsOpt() {
        return BracketsOpt;
    }

    public void setBracketsOpt(BracketsOpt BracketsOpt) {
        this.BracketsOpt=BracketsOpt;
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(BracketsOpt!=null) BracketsOpt.accept(visitor);
        if(FormParsList!=null) FormParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseTopDown(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseBottomUp(visitor);
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsListYes(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formParsName);
        buffer.append("\n");

        if(BracketsOpt!=null)
            buffer.append(BracketsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsListYes]");
        return buffer.toString();
    }
}
