package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class RenameMethodParameterEvent extends ChangeMethodSignatureEvent implements ChangeClassifyingEventExtension {

    public RenameMethodParameterEvent(MethodDeclaration original, MethodDeclaration renamed, int line) {
        super(original, renamed, line);
    }

    @Override
    public String toString() {
        return "RenameMethodParameterEvent [original=" + this.original.getName().getIdentifier() + ", changed="
                + this.renamed.getName().getIdentifier() + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
