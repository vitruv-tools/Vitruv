package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class RenameTypeEvent extends ChangeClassifyingEvent {

	public final TypeDeclaration original;
	public final TypeDeclaration renamed;

	public RenameTypeEvent(TypeDeclaration original, TypeDeclaration renamed) {
		this.original = original;
		this.renamed = renamed;
	}

	@Override
	public String toString() {
		return "RenameTypeEvent [original=" + original.getName().getIdentifier()
				+ ", renamed=" + renamed.getName().getIdentifier() + "]";
	}

}
