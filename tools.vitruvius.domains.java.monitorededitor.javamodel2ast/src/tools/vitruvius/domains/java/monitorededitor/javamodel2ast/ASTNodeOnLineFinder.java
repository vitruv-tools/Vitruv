package tools.vitruvius.domains.java.monitorededitor.javamodel2ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTNodeOnLineFinder extends ASTVisitor {

    private ASTNode foundNode = null;
    private final int line;
    private final Class<?> nodeType;
    private final CompilationUnit unit;

    public ASTNodeOnLineFinder(int line, Class<?> nodeType, CompilationUnit unit) {
        this.line = line;
        this.nodeType = nodeType;
        this.unit = unit;
    }

    @Override
    public void preVisit(ASTNode node) {
        int nodeLine = this.unit.getLineNumber(node.getStartPosition());
        if ((nodeLine == this.line) && this.nodeType.isInstance(node))
            this.foundNode = node;
    }

    public ASTNode getFoundASTNode() {
        return this.foundNode;
    }
}