package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RenameInterfaceEvent extends RenameTypeEvent {

	public RenameInterfaceEvent(TypeDeclaration original,
			TypeDeclaration renamed) {
		super(original, renamed);
	}
	
	@Override
	public String toString() {
		return "RenameInterfaceEvent [original=" + original.getName().getIdentifier()
				+ ", renamed=" + renamed.getName().getIdentifier() + "]";
	}

	@Override
	public void accept(ChangeEventVisitor visitor) {
		visitor.visit(this);
	}

}
