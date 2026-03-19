package tools.vitruv.framework.vsum.internal.schedule;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.vsum.schedule.Schedule;
import tools.vitruv.framework.vsum.schedule.Scheduler;

import java.util.*;

public class OneThreadScheduler implements Scheduler {
    private final List<VitruviusChange<Uuid>> changes = new ArrayList<>();
    /**
     * @param change
     * @return
     */
    @Override
    public boolean add(VitruviusChange<Uuid> change) {
        return changes.add(change);
    }

    /**
     * @return
     */
    @Override
    public Schedule end() {
        return new Schedule(Map.of(1, changes), changes.stream().map(Objects::hashCode).toList());
    }
}
