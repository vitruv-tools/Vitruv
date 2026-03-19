package tools.vitruv.framework.vsum.schedule;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.views.ChangeableViewSource;

import java.util.List;

public interface SchedulableChangeableViewSource extends ChangeableViewSource {

    /**
     * Registers the given scheduler, which is used for scheduling transactions
     */
    void registerScheduler(Scheduler scheduler);
    /**
     * propagates the schedule
     * @param schedule
     * @return
     */
    List<PropagatedChange> propagateSchedule(PropagationStrategy strategy, Schedule schedule);

    /**
     * propagates all changes in the order of the list, does not check for conflicts
     * @return
     */
    List<List<PropagatedChange>> propagateChanges(List<VitruviusChange<Uuid>> changes);
}
