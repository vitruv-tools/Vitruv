package edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.changeclassification;

import edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

/**
 * @author messinger
 * 
 *         Observer for change events.
 */
public interface ChangeOperationListener {

    void update(ChangeClassifyingEvent event);
}
