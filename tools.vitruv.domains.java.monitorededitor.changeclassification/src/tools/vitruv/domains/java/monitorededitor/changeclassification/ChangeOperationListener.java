package tools.vitruv.domains.java.monitorededitor.changeclassification;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

/**
 * @author messinger
 * 
 *         Observer for change events.
 */
public interface ChangeOperationListener {

    void update(ChangeClassifyingEvent event);
}
