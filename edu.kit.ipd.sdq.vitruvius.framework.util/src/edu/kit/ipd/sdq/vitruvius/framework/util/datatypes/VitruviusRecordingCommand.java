package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.lang.reflect.Modifier;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public abstract class VitruviusRecordingCommand extends RecordingCommand {
    public VitruviusRecordingCommand() {
        super(null);
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
}