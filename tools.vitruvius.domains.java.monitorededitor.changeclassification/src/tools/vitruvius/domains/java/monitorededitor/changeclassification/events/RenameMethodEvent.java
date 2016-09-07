package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class RenameMethodEvent extends ChangeMethodSignatureEvent {

	public RenameMethodEvent(MethodDeclaration original,
			MethodDeclaration renamed, int line) {
		super(original, renamed, line);
	}

	@Override
	public String toString() {
		return "RenameMethodEvent [original="
				+ original.getName().getIdentifier() + ", renamed="
				+ renamed.getName().getIdentifier() + ", line=" + line + "]";
	}

	@Override
	public void accept(ChangeEventVisitor visitor) {
		visitor.visit(this);
	}
}
