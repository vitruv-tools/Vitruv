package tools.vitruv.framework.vsum.internal.schedule;

import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.framework.vsum.schedule.PropagationStrategy;
import tools.vitruv.framework.vsum.schedule.SchedulableChangeableViewSource;
import tools.vitruv.framework.vsum.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PreconstructedSchedulePropagationStrategy implements PropagationStrategy {
    public List<PropagatedChange> propagate(Schedule schedule, SchedulableChangeableViewSource viewsource) {
        Map<Integer, List<PropagatedChange>> tracker = new ConcurrentHashMap<>();
        var threadPool = Executors.newFixedThreadPool(schedule.schedule().size());
        List<Future<List<List<PropagatedChange>>>> tasks = new ArrayList<>();
        schedule.schedule().forEach((index, changes) -> {
            var task = threadPool.submit(() -> viewsource.propagateChanges(changes));
            tasks.add(task);
        });
        schedule.schedule().forEach((index, changes) -> {
            try {
                var tracked = tasks.get(index-1).get();
                for (int i = 0; i<tracked.size();i++) {
                    tracker.put(changes.get(i).hashCode(), tracked.get(i));
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(999, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return schedule.originalChangeHashOrder().stream().map(tracker::get).flatMap(List::stream).toList();
    }
}
