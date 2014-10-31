package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeFieldModifiersEvent;

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
