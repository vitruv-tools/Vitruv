package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ChangeMethodReturnTypeEvent extends ChangeMethodSignatureEvent {

	public ChangeMethodReturnTypeEvent(MethodDeclaration original,
			MethodDeclaration renamed, int line) {
		super(original, renamed, line);
	}

	@Override
	public String toString() {
		return "ChangeMethodReturnTypeEvent [original="
				+ original.getName().getIdentifier() + ", changed="
				+ renamed.getName().getIdentifier() + ", line=" + line + "]";
	}
	
	@Override
	public void accept(ChangeEventVisitor visitor) {
		visitor.visit(this);
	}

}
