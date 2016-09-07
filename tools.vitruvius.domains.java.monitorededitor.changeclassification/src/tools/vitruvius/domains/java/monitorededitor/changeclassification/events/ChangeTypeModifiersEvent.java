package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.util.ModifierUtil;

public abstract class ChangeTypeModifiersEvent extends ChangeClassifyingEvent {

    public final TypeDeclaration original, changed;

    public ChangeTypeModifiersEvent(TypeDeclaration original, TypeDeclaration changed) {
        this.original = original;
        this.changed = changed;
    }

    @Override
    public String toString() {
        String originalModifiers = ModifierUtil.toModifiersString(this.original.getModifiers());
        String changedModifiers = ModifierUtil.toModifiersString(this.changed.getModifiers());

        return "ChangeTypeModifierEvent [original=" + originalModifiers + " " + this.original.getName().getIdentifier()
                + ", changed=" + changedModifiers + " " + this.changed.getName().getIdentifier() + "]";
    }
}
