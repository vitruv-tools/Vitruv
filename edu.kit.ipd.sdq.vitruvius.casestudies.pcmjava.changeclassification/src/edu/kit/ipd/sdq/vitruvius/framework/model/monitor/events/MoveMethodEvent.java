package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class MoveMethodEvent extends ChangeClassifyingEvent {

    public TypeDeclaration typeMovedFromAfterRemove, typeMovedToBeforeAdd;
    public MethodDeclaration original, moved;

    public MoveMethodEvent(TypeDeclaration typeMovedFromAfterRemove, TypeDeclaration typeMovedToBeforeAdd,
            MethodDeclaration original, MethodDeclaration moved) {
        this.typeMovedFromAfterRemove = typeMovedFromAfterRemove;
        this.typeMovedToBeforeAdd = typeMovedToBeforeAdd;
        this.original = original;
        this.moved = moved;
    }

    @Override
    public String toString() {
        return "MoveMethodEvent [original=" + this.original.getName().getIdentifier() + ", moved="
                + this.moved.getName().getIdentifier() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
