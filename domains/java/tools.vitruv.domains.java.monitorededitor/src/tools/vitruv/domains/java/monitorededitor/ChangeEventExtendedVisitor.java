package tools.vitruv.domains.java.monitorededitor;

import java.util.Collection;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEventExtension;

/**
 * @author messinger
 * 
 *         Visitor for {@link ChangeClassifyingEventExtension}s which is responsible for handling a
 *         predefined list of change types.
 * 
 */
public interface ChangeEventExtendedVisitor {

    public void visit(ChangeClassifyingEventExtension changeClassifyingEvent, ChangeSubmitter submitter);

    public Collection<Class<? extends ChangeClassifyingEventExtension>> getTreatedClasses();
}
