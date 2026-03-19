package tools.vitruv.framework.vsum.schedule;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;

public interface Scheduler {
    boolean add(VitruviusChange<Uuid> change);
    Schedule end();

}
