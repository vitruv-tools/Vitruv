package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class RenameFieldEvent extends ChangeFieldEvent {

    public final VariableDeclarationFragment originalFragment, changedFragment;

    public RenameFieldEvent(VariableDeclarationFragment originalFragment, VariableDeclarationFragment renamedFragment,
            int line) {
        super(null, null, line);
        this.original = (FieldDeclaration) originalFragment.getParent();
        this.changed = (FieldDeclaration) renamedFragment.getParent();
        this.originalFragment = originalFragment;
        this.changedFragment = renamedFragment;
    }

    @Override
    public String toString() {
        return "RenameFieldEvent [original=" + this.original.toString().replace(";\n", "") + ", changed="
                + this.changed.toString().replace(";\n", "") + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
