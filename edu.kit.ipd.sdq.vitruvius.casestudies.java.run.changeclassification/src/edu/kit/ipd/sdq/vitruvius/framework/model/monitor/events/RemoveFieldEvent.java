package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class RemoveFieldEvent extends ChangeClassifyingEvent implements HasLineInformation {

    public final TypeDeclaration typeAfterRemove;
    public final FieldDeclaration field;
    public final VariableDeclarationFragment fieldFragment;
    public final int line;

    public RemoveFieldEvent(TypeDeclaration typeAfterRemove, VariableDeclarationFragment fieldFragment, int line) {
        this.typeAfterRemove = typeAfterRemove;
        this.field = (FieldDeclaration) fieldFragment.getParent();
        this.fieldFragment = fieldFragment;
        this.line = line;
    }

    @Override
    public String toString() {
        return "RemoveFieldEvent [field=" + this.field.toString().replace(";\n", "") + ", fragment="
                + this.fieldFragment + ", line=" + this.line + "]";
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
