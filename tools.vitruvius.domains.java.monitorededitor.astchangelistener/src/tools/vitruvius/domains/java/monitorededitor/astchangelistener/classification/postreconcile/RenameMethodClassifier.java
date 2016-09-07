package tools.vitruvius.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeMethodSignatureEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameMethodEvent;

public class RenameMethodClassifier extends ChangeMethodSignatureClassifier {

	@Override
	protected ChangeMethodSignatureEvent classifyChange(
			MethodDeclaration original, MethodDeclaration changed, int line) {
		if (!original.getName().subtreeMatch(AST_MATCHER, changed.getName()))
			return new RenameMethodEvent(original, changed, line);
		else
			return null;
	}

}
