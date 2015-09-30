package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;

public abstract class VitruviusRecordingCommand extends RecordingCommand {

    protected static final Logger logger = Logger.getLogger(VitruviusRecordingCommand.class.getSimpleName());

    protected TransformationResult transformationResult;
    private RuntimeException runtimeException;

    public VitruviusRecordingCommand() {
        super(null);
        this.runtimeException = null;
    }

    public void setTransactionDomain(final TransactionalEditingDomain domain) {
        try {
            java.lang.reflect.Field domainField;
            domainField = RecordingCommand.class.getDeclaredField("domain");
            // copied from
            // http://zarnekow.blogspot.de/2013/01/java-hacks-changing-final-fields.html
            domainField.setAccessible(true);
            domainField.setAccessible(true);
            int modifiers = domainField.getModifiers();
            final java.lang.reflect.Field modifierField = domainField.getClass().getDeclaredField("modifiers");
            modifiers = modifiers & ~Modifier.FINAL;
            modifierField.setAccessible(true);
            modifierField.setInt(domainField, modifiers);
            domainField.set(this, domain);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public TransformationResult getTransformationResult() {
        return this.transformationResult;
    }

    @Override
    protected void preExecute() {
        this.runtimeException = null;
        super.preExecute();
    }

    public void setRuntimeException(final RuntimeException e) {
        this.runtimeException = e;
    }

    public void rethrowRuntimeExceptionIfExisting() {
        if (this.runtimeException != null) {
            throw (this.runtimeException);
        }
    }
}