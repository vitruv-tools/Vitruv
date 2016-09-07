package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RenameClassEvent extends RenameTypeEvent {

	public RenameClassEvent(TypeDeclaration original, TypeDeclaration renamed) {
		super(original, renamed);
	}

	@Override
	public String toString() {
		return "RenameClassEvent [original=" + original.getName().getIdentifier()
				+ ", renamed=" + renamed.getName().getIdentifier() + "]";
	}
	
	@Override
	public void accept(ChangeEventVisitor visitor) {
		visitor.visit(this);
	}

}
