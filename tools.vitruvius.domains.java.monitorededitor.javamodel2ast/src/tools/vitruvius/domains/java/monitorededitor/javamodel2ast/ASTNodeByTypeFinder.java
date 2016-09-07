package tools.vitruvius.domains.java.monitorededitor.javamodel2ast;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class ASTNodeByTypeFinder extends ASTVisitor {

    private List<ASTNode> foundNodes = null;
    private final Class<?> nodeType;

    public ASTNodeByTypeFinder(Class<?> nodeType) {
        this.nodeType = nodeType;
        this.foundNodes = new LinkedList<ASTNode>();
    }

    @Override
    public void preVisit(ASTNode node) {
        if (this.nodeType.isInstance(node))
            this.foundNodes.add(node);
    }

    public List<ASTNode> getFoundASTNodes() {
        return this.foundNodes;
    }
}