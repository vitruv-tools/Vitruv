package tools.vitruv.domains.java.monitorededitor.methodchange.changeclassifiers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.methodchange.events.MethodParameterNameChangedEvent;

/**
 * Classifier for renamed method parameters. It checks every method of the
 * changed compilation unit and returns the set of all changed method parameter
 * names.
 */
public class MethodParameterNameChangedClassifier implements
		ConcreteChangeClassifier {

	@Override
	public List<? extends ChangeClassifyingEvent> match(
			IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
			PreviousASTState previousState) {

		List<ChangeClassifyingEvent> events = new ArrayList<ChangeClassifyingEvent>();

		if (delta.getAffectedChildren() == null
				&& (delta.getFlags() & IJavaElementDelta.F_FINE_GRAINED) != 0) {
			return events;
		}

		for (@SuppressWarnings("unchecked")
		Iterator<TypeDeclaration> typeIt = currentCompilationUnit.types()
				.iterator(); typeIt.hasNext();) {
			for (MethodDeclaration md : typeIt.next().getMethods()) {
				MethodDeclaration originalMethodDeclaration = getOriginalMethodDeclaration(
						md,
						previousState
								.getOldCompilationUnit(currentCompilationUnit));
				if (originalMethodDeclaration != null) {
					events.addAll(createEventIfParameterRenamed(md,
							originalMethodDeclaration));
				}
			}
		}

		return events;
	}

	/**
	 * Compares the parameters of the method declarations and creates a change
	 * event if a parameter name has been changed.
	 * 
	 * @param changed
	 *            The (possibly) changed method declaration.
	 * @param original
	 *            The original method declaration.
	 * @return A set of change events for every changed parameter name.
	 */
	private static List<ChangeClassifyingEvent> createEventIfParameterRenamed(
			MethodDeclaration changed, MethodDeclaration original) {
		List<ChangeClassifyingEvent> result = new ArrayList<ChangeClassifyingEvent>();

		for (int i = 0; i < changed.parameters().size(); ++i) {
			SingleVariableDeclaration p1 = (SingleVariableDeclaration) changed
					.parameters().get(i);
			SingleVariableDeclaration p2 = (SingleVariableDeclaration) original
					.parameters().get(i);

			if (!AST_MATCHER.match(p1.getName(), p2.getName())) {
				result.add(new MethodParameterNameChangedEvent(original,
						changed, p2, p1));
				// result.add(new ChangeMethodParameterEvent(original, changed,
				// cu.getLineNumber(original.getStartPosition())));
			}
		}

		return result;
	}

	/**
	 * Looks for a matching method declaration in the unchanged state. The name,
	 * return type and parameter types of the methods are compared.
	 * 
	 * @param changedMethodDeclaration
	 *            The changed method declaration.
	 * @param previousCompilationUnit
	 *            The unchanged state represented as compilation unit.
	 * @return The matching unchanged method declaration.
	 */
	private static MethodDeclaration getOriginalMethodDeclaration(
			MethodDeclaration changedMethodDeclaration,
			CompilationUnit previousCompilationUnit) {

		if (changedMethodDeclaration == null) {
			return null;
		}

		for (@SuppressWarnings("unchecked")
		Iterator<TypeDeclaration> typeIt = previousCompilationUnit.types()
				.iterator(); typeIt.hasNext();) {
			for (MethodDeclaration md : typeIt.next().getMethods()) {
				if (!changedMethodDeclaration.getName().subtreeMatch(
						AST_MATCHER, md.getName())) {
					continue;
				}
				if (changedMethodDeclaration.getReturnType2() != null && md.getReturnType2() != null && !changedMethodDeclaration.getReturnType2().subtreeMatch(
						AST_MATCHER, md.getReturnType2())) {
					continue;
				}
				if (changedMethodDeclaration.parameters().size() != md
						.parameters().size()) {
					continue;
				}
				boolean matches = true;
				for (int i = 0; i < changedMethodDeclaration.parameters()
						.size(); ++i) {
					SingleVariableDeclaration p1 = (SingleVariableDeclaration) changedMethodDeclaration
							.parameters().get(i);
					SingleVariableDeclaration p2 = (SingleVariableDeclaration) md
							.parameters().get(i);
					if (!(p1.getType().subtreeMatch(AST_MATCHER, p2.getType()))) {
						matches = false;
					}
				}
				if (matches) {
					return md;
				}
			}
		}

		return null;
	}

}
