package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.util.ModifierUtil;

public class ChangeClassModifiersEvent extends ChangeTypeModifiersEvent {

    public ChangeClassModifiersEvent(TypeDeclaration original, TypeDeclaration changed) {
        super(original, changed);
    }

    @Override
    public String toString() {
        String originalModifiers = ModifierUtil.toModifiersString(this.original.getModifiers());
        String changedModifiers = ModifierUtil.toModifiersString(this.changed.getModifiers());

        return "ChangeClassModifierEvent [original=" + originalModifiers + " "
                + this.original.getName().getIdentifier() + ", changed=" + changedModifiers + " "
                + this.changed.getName().getIdentifier() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
