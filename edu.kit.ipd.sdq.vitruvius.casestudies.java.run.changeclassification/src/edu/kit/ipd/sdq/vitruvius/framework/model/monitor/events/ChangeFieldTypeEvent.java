package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;

public class ChangeFieldTypeEvent extends ChangeFieldEvent {

    public ChangeFieldTypeEvent(FieldDeclaration original, FieldDeclaration changed, int line) {
        super(original, changed, line);
    }

    @Override
    public String toString() {
        return "ChangeFieldTypeEvent [original=" + this.original.toString().replace(";\n", "") + ", changed="
                + this.changed.toString().replace(";\n", "") + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
