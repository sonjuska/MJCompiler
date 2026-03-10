// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:37:42


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private MethodTypeName MethodTypeName;
    private FormParsOpt FormParsOpt;
    private VarDeclMultiple VarDeclMultiple;
    private StatementMultiple StatementMultiple;

    public MethodDecl (MethodTypeName MethodTypeName, FormParsOpt FormParsOpt, VarDeclMultiple VarDeclMultiple, StatementMultiple StatementMultiple) {
        this.MethodTypeName=MethodTypeName;
        if(MethodTypeName!=null) MethodTypeName.setParent(this);
        this.FormParsOpt=FormParsOpt;
        if(FormParsOpt!=null) FormParsOpt.setParent(this);
        this.VarDeclMultiple=VarDeclMultiple;
        if(VarDeclMultiple!=null) VarDeclMultiple.setParent(this);
        this.StatementMultiple=StatementMultiple;
        if(StatementMultiple!=null) StatementMultiple.setParent(this);
    }

    public MethodTypeName getMethodTypeName() {
        return MethodTypeName;
    }

    public void setMethodTypeName(MethodTypeName MethodTypeName) {
        this.MethodTypeName=MethodTypeName;
    }

    public FormParsOpt getFormParsOpt() {
        return FormParsOpt;
    }

    public void setFormParsOpt(FormParsOpt FormParsOpt) {
        this.FormParsOpt=FormParsOpt;
    }

    public VarDeclMultiple getVarDeclMultiple() {
        return VarDeclMultiple;
    }

    public void setVarDeclMultiple(VarDeclMultiple VarDeclMultiple) {
        this.VarDeclMultiple=VarDeclMultiple;
    }

    public StatementMultiple getStatementMultiple() {
        return StatementMultiple;
    }

    public void setStatementMultiple(StatementMultiple StatementMultiple) {
        this.StatementMultiple=StatementMultiple;
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
        if(MethodTypeName!=null) MethodTypeName.accept(visitor);
        if(FormParsOpt!=null) FormParsOpt.accept(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.accept(visitor);
        if(StatementMultiple!=null) StatementMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeName!=null) MethodTypeName.traverseTopDown(visitor);
        if(FormParsOpt!=null) FormParsOpt.traverseTopDown(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseTopDown(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.traverseBottomUp(visitor);
        if(FormParsOpt!=null) FormParsOpt.traverseBottomUp(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseBottomUp(visitor);
        if(StatementMultiple!=null) StatementMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodTypeName!=null)
            buffer.append(MethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsOpt!=null)
            buffer.append(FormParsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclMultiple!=null)
            buffer.append(VarDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementMultiple!=null)
            buffer.append(StatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
