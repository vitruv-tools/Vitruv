package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;

public abstract class ChangeFieldEvent extends ChangeClassifyingEvent implements HasLineInformation {

    public FieldDeclaration original, changed;
    public final int line;

    public ChangeFieldEvent(FieldDeclaration original, FieldDeclaration changed, int line) {
        this.original = original;
        this.changed = changed;
        this.line = line;
    }

    @Override
    public String toString() {
        return "ChangeFieldEvent [original=" + this.original.toString().replace(";\n", "") + ", changed="
                + this.changed.toString().replace(";\n", "") + ", line=" + this.line + "]";
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public abstract void accept(ChangeEventVisitor visitor);

}
