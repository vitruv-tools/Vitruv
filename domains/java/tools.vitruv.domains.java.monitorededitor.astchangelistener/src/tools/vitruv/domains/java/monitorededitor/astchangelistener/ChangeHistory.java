package tools.vitruv.domains.java.monitorededitor.astchangelistener;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

/**
 * @author messinger
 * 
 *         Holds a list of {@link ChangeClassifyingEvent}s. Events are discarded after a predefined
 *         timespan which can be set in the constructor. It is assumed that the events are held in
 *         chronological order because event submissions are assumed to happen in chronological
 *         order. Chronological means that the timespan of an earlier submitted event is not
 *         younger.
 * 
 */
public class ChangeHistory {

    private final List<ChangeClassifyingEvent> events;
    private final long timespan;

    public ChangeHistory(int minutes) {
        this.timespan = minutes * 60 * 1000;
        this.events = new LinkedList<ChangeClassifyingEvent>();
    }

    // assumes newEvents in chronological order
    public void update(Collection<ChangeClassifyingEvent> newEvents) {
        long now = System.currentTimeMillis();
        long tooOld = now - this.timespan;
        ListIterator<ChangeClassifyingEvent> it = this.events.listIterator();
        while (it.hasNext() && (it.next().getTimestamp() < tooOld))
            it.remove();
        this.events.addAll(newEvents);
    }

    public List<ChangeClassifyingEvent> getEvents() {
        return this.events;
    }

}
