package edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeMethodSignatureEvent;
import edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameMethodEvent;

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
