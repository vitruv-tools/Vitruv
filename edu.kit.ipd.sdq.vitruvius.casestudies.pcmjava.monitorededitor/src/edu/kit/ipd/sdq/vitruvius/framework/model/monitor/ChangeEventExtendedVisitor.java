package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEventExtension;

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
