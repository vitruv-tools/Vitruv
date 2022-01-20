package tools.vitruv.framework.vsum.views;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

import tools.vitruv.framework.change.description.PropagatedChange;

/**
 * A Vitruv view on an underlying {@link ChangeableViewSource}.
 */
public interface View extends AutoCloseable {

	/**
	 * Provides the root model elements of this view.
	 * 
	 * @throws IllegalStateException if called on a closed view.
	 * @see View#isClosed()
	 */
	Collection<EObject> rootObjects();

	/**
	 * Provides all root model elements of this view that conform to a certain type.
	 * 
	 * @param clazz is requested root element type.
	 * @throws IllegalStateException if called on a closed view.
	 * @see View#isClosed()
	 */
	default <T> Collection<T> rootObjects(Class<T> clazz) {
		return FluentIterable.from(rootObjects()).filter(clazz).toList();
	};

	/**
	 * Specifies whether the view was modified.
	 */
	boolean isModified();

	/**
	 * Specifies whether the underlying view sources have changed.
	 */
	boolean haveViewSourcesChanged();

	/**
	 * Updates the view from the underlying virtual model, thus invalidating its
	 * previous state and now providing a updated view on the virtual model. This
	 * can only be done for an unmodified view.
	 * 
	 * @throws UnsupportedOperationException if the update is called for a dirty
	 *                                       view.
	 * @throws IllegalStateException         if called on a closed view.
	 * @see View#isClosed()
	 */
	// TODO TS Add issue: in the long term we need to allow updates for modified
	// views
	void update();

	/**
	 * Commits the changes made to the view and its containing elements to the
	 * virtual model. This explicitly includes all changes that have been made
	 * before calling this method. Whether changes will effectively be recorded
	 * depends on this view. It is permissible for a view not to record any changes
	 * if it deems them irrelevant. Note that committing changes will automatically
	 * be followed by an {@link #update} from the virtual model to keep both the
	 * view and the virtual model synchronized.
	 * 
	 * @return the changes resulting from propagating the recorded changes.
	 * @throws IllegalStateException if called on a closed view.
	 * @see View#isClosed()
	 */
	// TODO TS: Some views may not always record changes, and thus require
	// transactional record-and-commit.
	List<PropagatedChange> commitChanges();

	/**
	 * Checks whether the view was closed. Closed view should not further be used.
	 */
	boolean isClosed();

	/**
	 * Persists the given object at the given {@link URI} and adds it as view root.
	 *
	 * This method is in beta state, as it is still under evaluation whether it is
	 * sufficient and appropriate for registering root objects in views.
	 */
	@Beta
	void registerRoot(EObject object, URI persistAt);

	/**
	 * Moves the given object to the given {@link URI}. The given {@link EObject}
	 * must already be a root object of the view, otherwise an
	 * {@link IllegalStateException} is thrown.
	 *
	 * This method is in beta state, as it is still under evaluation whether it is
	 * sufficient and appropriate for registering root objects in views.
	 */
	@Beta
	void moveRoot(EObject object, URI newLocation);

	/**
	 * Returns the {@link ViewSelection} with which this view has been created.
	 */
	ViewSelection getSelection();

	/**
	 * Returns the {@link ViewType} this view conforms to.
	 */
	ViewType<? extends ViewSelector> getViewType();
}
