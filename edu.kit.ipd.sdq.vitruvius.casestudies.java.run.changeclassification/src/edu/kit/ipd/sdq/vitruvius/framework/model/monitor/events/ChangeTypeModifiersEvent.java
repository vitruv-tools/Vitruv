package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.util.ModifierUtil;

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
