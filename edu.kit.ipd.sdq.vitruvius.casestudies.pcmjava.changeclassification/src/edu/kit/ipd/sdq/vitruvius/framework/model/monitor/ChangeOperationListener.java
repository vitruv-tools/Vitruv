package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;

/**
 * @author messinger
 * 
 *         Observer for change events.
 */
public interface ChangeOperationListener {

    void update(ChangeClassifyingEvent event);
}
