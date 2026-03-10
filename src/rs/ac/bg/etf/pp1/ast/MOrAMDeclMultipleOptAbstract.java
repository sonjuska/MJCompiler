// generated with ast extension for cup
// version 0.8
// 10/2/2026 19:24:11


package rs.ac.bg.etf.pp1.ast;

public class MOrAMDeclMultipleOptAbstract extends MOrAMDeclMultipleOpt {

    private AbstractMethodDecl AbstractMethodDecl;
    private MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt;

    public MOrAMDeclMultipleOptAbstract (AbstractMethodDecl AbstractMethodDecl, MOrAMDeclMultipleOpt MOrAMDeclMultipleOpt) {
        this.AbstractMethodDecl=AbstractMethodDecl;
        if(AbstractMethodDecl!=null) AbstractMethodDecl.setParent(this);
        this.MOrAMDeclMultipleOpt=MOrAMDeclMultipleOpt;
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.setParent(this);
    }

    public AbstractMethodDecl getAbstractMethodDecl() {
        return AbstractMethodDecl;
    }

    public void setAbstractMethodDecl(AbstractMethodDecl AbstractMethodDecl) {
        this.AbstractMethodDecl=AbstractMethodDecl;
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
        if(AbstractMethodDecl!=null) AbstractMethodDecl.accept(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractMethodDecl!=null) AbstractMethodDecl.traverseTopDown(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractMethodDecl!=null) AbstractMethodDecl.traverseBottomUp(visitor);
        if(MOrAMDeclMultipleOpt!=null) MOrAMDeclMultipleOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MOrAMDeclMultipleOptAbstract(\n");

        if(AbstractMethodDecl!=null)
            buffer.append(AbstractMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MOrAMDeclMultipleOpt!=null)
            buffer.append(MOrAMDeclMultipleOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MOrAMDeclMultipleOptAbstract]");
        return buffer.toString();
    }
}
