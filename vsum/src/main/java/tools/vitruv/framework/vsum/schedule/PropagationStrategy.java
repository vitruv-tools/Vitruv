package tools.vitruv.framework.vsum.schedule;

import tools.vitruv.change.composite.description.PropagatedChange;

import java.util.List;

public interface PropagationStrategy {
    List<PropagatedChange> propagate(Schedule schedule, SchedulableChangeableViewSource viewsource);
}
