// generated with ast extension for cup
// version 0.8
// 11/2/2026 17:40:25


package rs.ac.bg.etf.pp1.ast;

public class MOrAMDeclMultipleOptMethod extends MOrAMDeclMultipleOpt {

    private MethodDecl MethodDecl;
    private MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt;

    public MOrAMDeclMultipleOptMethod (MethodDecl MethodDecl, MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt) {
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
        this.MOrAMDeclMultipleOpt=MOrAMDeclMultipleOpt;
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.setParent(this);
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public MOrAMDeclMultipleOpt getMOrAMDeclMultipleOpt() {
        return MOrAMDeclMultipleOpt;
    }

    public void setMOrAMDeclMultipleOpt(MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt) {
        this.MOrAMDeclMultipleOpt=MOrAMDeclMultipleOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDecl!=null) MethodDecl.accept(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MOrAMDeclMultipleOptMethod(\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MOrAMDeclMultipleOpt!=null)
            buffer.append(MOrAMDeclMultipleOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MOrAMDeclMultipleOptMethod]");
        return buffer.toString();
    }
}
