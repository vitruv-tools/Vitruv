package tools.vitruvius.domains.java.monitorededitor.methodchange.events;

import org.eclipse.jdt.core.dom.ASTNode;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEventExtension;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeEventVisitor;
import tools.vitruvius.domains.java.monitorededitor.jamopputil.CompilationUnitAdapter;
import tools.vitruvius.domains.java.monitorededitor.methodchange.utils.JaMoPPUtilities;

/**
 * Base class for all change classifying event extensions, which need an old and
 * a new state.
 * @param <T> The type of the changed element.
 */
public abstract class ChangeClassifiyingEventExtensionBase <T extends ASTNode> extends
		ChangeClassifyingEvent implements
		OriginalAndOldChangeClassifiyingEventExtension {

	private final CompilationUnitAdapter originalCU;
	private final CompilationUnitAdapter changedCU;
	private final T originalElement;
	private final T changedElement;

	/**
	 * Constructor.
	 * 
	 * @param originalASTNode
	 *            An AST node from the old state.
	 * @param changedASTNode
	 *            An AST node from the new state.
	 */
	protected ChangeClassifiyingEventExtensionBase(T originalASTNode,
			T changedASTNode) {
		originalCU = JaMoPPUtilities
				.getUnsavedCompilationUnitAdapter(originalASTNode);
		changedCU = JaMoPPUtilities
				.getUnsavedCompilationUnitAdapter(changedASTNode);
		originalElement = originalASTNode;
		changedElement = changedASTNode;
	}

	@Override
	public void accept(ChangeEventVisitor visitor) {
		visitor.visit((ChangeClassifyingEventExtension) this);
	}

	@Override
	public CompilationUnitAdapter getChangedCompilationUnit() {
		return changedCU;
	}

	@Override
	public CompilationUnitAdapter getOriginalCompilationUnit() {
		return originalCU;
	}

	/**
	 * @return The original element.
	 */
    public T getOriginalElement() {
        return originalElement;
    }

    /**
     * @return The changed element.
     */
    public T getChangedElement() {
        return changedElement;
    }

}