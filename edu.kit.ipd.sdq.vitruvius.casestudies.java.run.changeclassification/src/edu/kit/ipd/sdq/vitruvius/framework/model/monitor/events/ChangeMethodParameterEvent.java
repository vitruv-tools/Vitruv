package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ChangeMethodParameterEvent extends ChangeMethodSignatureEvent {

    public ChangeMethodParameterEvent(MethodDeclaration original, MethodDeclaration renamed, int line) {
        super(original, renamed, line);
    }

    @Override
    public String toString() {
        return "ChangeMethodParameterEvent [original=" + this.original.getName().getIdentifier() + ", changed="
                + this.renamed.getName().getIdentifier() + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
