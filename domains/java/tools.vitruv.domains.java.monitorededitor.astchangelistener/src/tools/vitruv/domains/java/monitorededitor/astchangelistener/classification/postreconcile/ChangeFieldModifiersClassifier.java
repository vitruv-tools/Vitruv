package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeFieldModifiersEvent;

public class ChangeFieldModifiersClassifier extends ChangeFieldClassifier {

    @Override
    protected ChangeClassifyingEvent classifyChange(IField ifield, FieldDeclaration original, FieldDeclaration changed,
            int line) {
        if (original.getModifiers() != changed.getModifiers()) {
            return new ChangeFieldModifiersEvent(original, changed, line);
        }
        return null;
    }
}
