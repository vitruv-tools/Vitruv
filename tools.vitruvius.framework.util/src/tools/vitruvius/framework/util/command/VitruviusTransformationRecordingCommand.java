package tools.vitruvius.framework.util.command;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionChangeDescription;

import tools.vitruvius.framework.util.bridges.JavaBridge;

public abstract class VitruviusTransformationRecordingCommand extends VitruviusRecordingCommand {

    protected TransformationResult transformationResult;

    public TransformationResult getTransformationResult() {
        return this.transformationResult;
    }

    @Override
    public Collection<?> getAffectedObjects() {
        Transaction transaction = JavaBridge.getFieldFromClass(RecordingCommand.class, "transaction", this);
        if (transaction == null) {
            // TODO DW what to do, if transaction is null? when is this the case?
            return Collections.EMPTY_SET;
        }

        final TransactionChangeDescription changeDescription = transaction.getChangeDescription();
        final Collection<EObject> affectedEObjects = EMFChangeBridge.getAffectedObjects(changeDescription);

        return affectedEObjects;
    }

}
