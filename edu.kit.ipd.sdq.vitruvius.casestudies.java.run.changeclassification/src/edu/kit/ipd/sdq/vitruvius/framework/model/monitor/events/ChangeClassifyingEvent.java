package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

/**
 * @author messinger
 * 
 *         Abstract superclass for all AST change events. Sets a timestamp on construction, accepts
 *         a {@link ChangeEventVisitor} according to the visitor pattern. A
 *         {@link ChangeClassifyingEvent} subclass contains fields for by the change affected AST
 *         nodes and overrides toString() for log/debug purposes.
 * 
 */
public abstract class ChangeClassifyingEvent {

    private final long timestamp;

    public ChangeClassifyingEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public abstract void accept(ChangeEventVisitor visitor);

}
