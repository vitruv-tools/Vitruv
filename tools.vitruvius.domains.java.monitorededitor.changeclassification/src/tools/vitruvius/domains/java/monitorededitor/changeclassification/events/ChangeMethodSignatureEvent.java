package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public abstract class ChangeMethodSignatureEvent extends ChangeClassifyingEvent implements
		HasLineInformation {

	public final MethodDeclaration original;
	public final MethodDeclaration renamed;
	public final int line;

	public ChangeMethodSignatureEvent(MethodDeclaration method,
			MethodDeclaration changed, int line) {
		this.original = method;
		this.renamed = changed;
		this.line = line;
	}

	@Override
	public String toString() {
		return "ChangeMethodEvent [original="
				+ original.getName().getIdentifier() + ", changed="
				+ renamed.getName().getIdentifier() + ", line=" + line + "]";
	}

	@Override
	public int getLine() {
		return line;
	}

}
