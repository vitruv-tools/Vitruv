package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.interaction.UserInteractionBase;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.QueuedChange;
import tools.vitruv.change.propagation.QueuedPropagationStep;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;

/**
 * A virtual model in the VSUM framework that provides changeable model repositories and views.
 *
 * <p>Changes can be propagated in two ways. {@link #propagateChange(VitruviusChange)} propagates a
 * change completely before it returns and asks the user interactor of the virtual model whenever
 * user input is needed. Alternatively, changes can be enqueued with {@link
 * #enqueueChange(VitruviusChange)} and propagated one step at a time with {@link
 * #propagateNextQueuedChange()}, typically by a single worker thread. A queued change that needs
 * user input is set aside until the answer is registered with {@link #registerUserInteractions}, so
 * it does not hold up the other queued changes.
 */
public interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {

  /**
   * Gets the folder of the virtual model.
   *
   * @return the folder of the virtual model
   */
  Path getFolder();

  /**
   * Defines how changes are propagated, both when passed to {@link
   * #propagateChange(VitruviusChange)} and when enqueued with {@link
   * #enqueueChange(VitruviusChange)}. By default, {@link ChangePropagationMode#TRANSITIVE_CYCLIC}
   * is used, i.e., changes are transitively propagated until no further changes are produced.
   *
   * @param changePropagationMode the mode to use from now on
   * @throws IllegalStateException if changes are queued, i.e., {@link #getQueuedChanges()} is not
   *     empty
   */
  void setChangePropagationMode(ChangePropagationMode changePropagationMode);

  /**
   * Enqueues a change to be propagated later by {@link #propagateNextQueuedChange()}. Returns right
   * away, also while a change is being propagated.
   *
   * @param change the change to apply and propagate, not {@code null} and containing a concrete
   *     change
   * @return the task id that identifies the change in the queue
   */
  int enqueueChange(VitruviusChange<Uuid> change);

  /**
   * Enqueues a change under the given task id, otherwise like {@link
   * #enqueueChange(VitruviusChange)}. Useful when the caller needs to know the id before the change
   * is enqueued, e.g., to hand it out to a client first.
   *
   * @param change the change to apply and propagate, not {@code null} and containing a concrete
   *     change
   * @param taskId the id to identify the change by
   * @return {@code taskId}
   * @throws IllegalArgumentException if the id is already used by a change that is still queued
   */
  int enqueueChange(VitruviusChange<Uuid> change, int taskId);

  /**
   * Takes one step on the queue of changes. The first queued change is propagated through the
   * change propagation specifications once (an enqueued change is applied to the models first), and
   * the changes this produces are queued right at the front, so they are propagated before any
   * change enqueued later. A blocked change is just moved to the end of the queue.
   *
   * <p>If the change needs an answer to a user interaction that has not been registered yet, it is
   * set aside instead of waiting for the answer: the step is {@link
   * QueuedPropagationStep.Outcome#DEFERRED}, the change is moved to the end of the queue with the
   * question attached (see {@link #getQueuedChanges()}), and it is propagated again once the answer
   * is registered with {@link #registerUserInteractions}. Change propagation specifications must
   * therefore ask for user input before they modify any model.
   *
   * <p>Steps never run at the same time as each other or as {@link
   * #propagateChange(VitruviusChange)}. After a {@link QueuedPropagationStep.Outcome#PROPAGATED}
   * step, the models are saved and every {@link ChangePropagationListener} gets {@link
   * ChangePropagationListener#finishedChangePropagation} with the changes of that step, so views
   * know they are outdated. The first step of an enqueued change applies the change to the models
   * even if it is then deferred, so after such a step the listeners get {@link
   * ChangePropagationListener#finishedChangePropagation} as well, with no propagated changes; the
   * models are saved once the change is propagated. {@link
   * ChangePropagationListener#startedChangePropagation} is not called: a queued change is
   * propagated in several steps, interleaved with other changes, so its propagation has no single
   * start, and the changes produced along the way are no {@code VitruviusChange<Uuid>}. The
   * listeners in the framework ignore that call anyway.
   *
   * @return what happened in this step, empty if no change is queued
   * @throws tools.vitruv.change.propagation.NonDeferrablePropagationException if the change needs
   *     an unanswered user interaction but has already modified models; the change is dropped from
   *     the queue
   * @throws RuntimeException if propagating the change fails for any other reason; the change is
   *     dropped from the queue
   */
  Optional<QueuedPropagationStep> propagateNextQueuedChange();

  /**
   * Returns the queued changes: the one being propagated right now first, then the others in the
   * order they will be attempted. Besides the enqueued changes, this includes the changes their
   * propagation produced that still have to be propagated; {@link QueuedChange#rootTaskId()} tells
   * which enqueued change they belong to. Does not wait for a running propagation step.
   *
   * @return snapshots of the queued changes
   */
  List<QueuedChange> getQueuedChanges();

  /**
   * Returns whether the change enqueued under the given task id, or any change its propagation
   * produced, still has to be propagated. Does not wait for a running propagation step.
   *
   * @param taskId the task id returned by {@link #enqueueChange(VitruviusChange)}
   * @return {@code false} once nothing of the change is queued anymore, i.e., it has been
   *     propagated completely or its propagation failed, or if no change with this id was enqueued
   */
  boolean isChangePending(int taskId);

  /**
   * Registers answers to user interactions of a queued change. Every part of the change that waits
   * for an answer becomes ready to be propagated again, and the answers stay available for all
   * further steps of the change. Can be called at any time, also while a step is running.
   *
   * @param taskId the task id of the enqueued change, or of any queued change it produced
   * @param interactions the answers, matched to the questions as described at {@link
   *     tools.vitruv.change.interaction.PendingUserInteraction}
   * @throws IllegalArgumentException if no change with this id is queued
   */
  void registerUserInteractions(int taskId, UserInteractionBase... interactions);
}
