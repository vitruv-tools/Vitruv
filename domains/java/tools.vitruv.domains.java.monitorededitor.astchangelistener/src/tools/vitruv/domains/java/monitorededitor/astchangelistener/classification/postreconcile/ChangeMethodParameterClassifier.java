package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeMethodParameterEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeMethodSignatureEvent;

public class ChangeMethodParameterClassifier extends ChangeMethodSignatureClassifier {

    @Override
    protected ChangeMethodSignatureEvent classifyChange(MethodDeclaration original, MethodDeclaration changed, int line) {
        if (!parametersEqual(original, changed))
            return new ChangeMethodParameterEvent(original, changed, line);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    private boolean parametersEqual(MethodDeclaration original, MethodDeclaration changed) {
        if (original.parameters().size() != changed.parameters().size())
            return false;

        Iterator<SingleVariableDeclaration> originalIt = original.parameters().iterator();
        Iterator<SingleVariableDeclaration> changedIt = changed.parameters().iterator();
        while (originalIt.hasNext() && changedIt.hasNext()) {
            SingleVariableDeclaration originalParam = originalIt.next();
            SingleVariableDeclaration changedParam = changedIt.next();

            if (!originalParam.subtreeMatch(AST_MATCHER, changedParam))
                return false;
        }
        return true;
    }

}
