package tools.vitruv.framework.views;

import java.util.List;

import tools.vitruv.change.composite.description.PropagatedChange;

/**
 * A {@link View} that allows to commit its changes back to the underlying {@link ChangeableViewSource}.
 */
public interface CommittableView extends View {
	/**
	 * Commits the changes made to the view and its containing elements to the
	 * underlying {@link ChangeableViewSource}. This explicitly includes all changes
	 * that have been made before calling this method. Whether changes will
	 * effectively be recorded depends on this view. It is permissible for a view
	 * not to record any changes if it deems them irrelevant.
	 * 
	 * To ensure that the view is not modified ({@link #isModified()}) afterwards
	 * because further changes may be performed to the underlying sources during
	 * commit, consider using {@link #commitChangesAndUpdate()} instead to perform
	 * an update of the view afterwards.
	 * 
	 * @return the changes resulting from propagating the recorded changes
	 * @throws IllegalStateException if called on a closed view
	 * @see #isClosed()
	 * @see #commitChangesAndUpdate()
	 */
	List<PropagatedChange> commitChanges();

	/**
	 * Convenience method for subsequent execution of {@link #commitChanges()} and
	 * {@link #update()}. Commits the changes made to the view and its containing
	 * elements to the underlying {@link ChangeableViewSource} and updates the view
	 * elements from the {@link ChangeableViewSource} afterwards to reflect
	 * potential further changes made during commit.
	 * 
	 * @return the changes resulting from propagating the recorded changes
	 * @throws IllegalStateException if called on a closed view
	 * @see #commitChanges()
	 * @see #update()
	 * @see #isClosed()
	 */
	default List<PropagatedChange> commitChangesAndUpdate() {
		List<PropagatedChange> changes = commitChanges();
		update();
		return changes;
	}
}
