package tools.vitruv.domains.java.monitorededitor.methodchange.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

/**
 * Change event for a name change of a method parameter.
 */
public class MethodParameterNameChangedEvent extends
		ChangeClassifiyingEventExtensionBase<MethodDeclaration> {

	private final SingleVariableDeclaration paramOriginal;
	private final SingleVariableDeclaration paramChanged;

	/**
	 * Constructor.
	 * 
	 * @param methodOriginal
	 *            The original method.
	 * @param methodChanged
	 *            The changed method.
	 * @param paramOriginal
	 *            The original parameter.
	 * @param paramChanged
	 *            The changed parameter.
	 */
	public MethodParameterNameChangedEvent(MethodDeclaration methodOriginal,
			MethodDeclaration methodChanged,
			SingleVariableDeclaration paramOriginal,
			SingleVariableDeclaration paramChanged) {
		super(methodOriginal, methodChanged);
		this.paramOriginal = paramOriginal;
		this.paramChanged = paramChanged;
	}

	/**
	 * @return The original parameter.
	 */
	public SingleVariableDeclaration getParamOriginal() {
		return paramOriginal;
	}

	/**
	 * @return The changed parameter.
	 */
	public SingleVariableDeclaration getParamChanged() {
		return paramChanged;
	}

}
