package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;

public class ChangeFieldModifiersEvent extends ChangeFieldEvent {

    public ChangeFieldModifiersEvent(FieldDeclaration original, FieldDeclaration changed, int line) {
        super(original, changed, line);
    }

    @Override
    public String toString() {
        return "ChangeFieldModifiersEvent [original=" + this.original.toString().replace(";\n", "") + ", changed="
                + this.changed.toString().replace(";\n", "") + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
