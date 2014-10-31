package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

/**
 * @author messinger
 * 
 *         Marker interface that is to be implemented by new {@link ChangeClassifyingEvent}s that
 *         are not expected to be handled by the {@link ChangeEventVisitor}. Should be dispatched to
 *         a ChangeEventExtendedVisitor which states to be responsible for the change type of this
 *         class.
 */
public interface ChangeClassifyingEventExtension {

}
