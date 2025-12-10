package tools.vitruv.framework.views;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

/**
 * A Vitruv view on an underlying {@link ChangeableViewSource}.
 * 
 */
public interface View extends AutoCloseable {

  /**
   * Provides the root model elements of this view.
   *
   * @throws IllegalStateException if called on a closed view.
   * @see View#isClosed()
   * 
   * @return all root model elements of this view.
   */
  Collection<EObject> getRootObjects();

  /**
   * Provides all root model elements of this view that conform to a certain type.
   *
   * @param clazz is requested root element type.
   * @throws IllegalStateException if called on a closed view.
   * @see View#isClosed()
   * 
   * @return all root model elements of this view of the given type.
   */
  default <T> Collection<T> getRootObjects(Class<T> clazz) {
    return FluentIterable.from(getRootObjects()).filter(clazz).toList();
  }

  /**
   * Returns whether the view was modified.
   * 
   * @return whether the view was modified.
   */
  boolean isModified();

  /**
   * Returns whether the view is outdated, i.e., whether the underlying view
   * sources have changed.
   * 
   * @return whether the view is outdated.
   */
  boolean isOutdated();

  /**
   * Updates the view from the underlying {@link ViewSource}, thus invalidating
   * its previous state
   * and now providing an updated view on the {@code ViewSource}. It reuses the
   * {@link
   * ViewSelection} with whom the view has been created. This can only be done for
   * an unmodified
   * view.
   *
   * @throws UnsupportedOperationException if called on a modified view
   * @throws IllegalStateException         if called on a closed view
   * @see #isClosed()
   * @see #isModified()
   * 
   * @return void
   */
  void update();

  /**
   * Checks whether the view was closed. Closed views cannot be used further. All
   * methods may thrown
   * an {@link IllegalStateException}.
   * 
   * @return whether the view was closed.
   */
  boolean isClosed();

  /**
   * Persists the given object at the given {@link URI} and adds it as view root.
   * The newly
   * registered root will not be present in the view, you have to commit the view
   * and checkout a new
   * one (you probably also have to adapt the selection) in order to be able to
   * see and modify the
   * new root.
   *
   * <p>
   * This method is in beta state, as it is still under evaluation whether it is
   * sufficient and
   * appropriate for registering root objects in views.
   * 
   * @param object    the object to register as root
   * @param persistAt the URI where the root object is to be persisted
   */
  @Beta
  void registerRoot(EObject object, URI persistAt);

  /**
   * Moves the given object to the given {@link URI}. The given {@link EObject}
   * must already be a
   * root object of the view, otherwise an {@link IllegalStateException} is
   * thrown.
   *
   * <p>
   * This method is in beta state, as it is still under evaluation whether it is
   * sufficient and
   * appropriate for registering root objects in views.
   * 
   * @param object the object to move
   */
  @Beta
  void moveRoot(EObject object, URI newLocation);

  /**
   * Returns the {@link ViewSelection} with which this view has been created.
   * 
   * @return the selection of this view
   */
  ViewSelection getSelection();

  /**
   * Returns the {@link ViewType} this view conforms to.
   * 
   * @return the view type
   */
  ViewType<? extends ViewSelector> getViewType();

  /**
   * Returns a {@link CommittableView} based on the view's configuration. Changes
   * to commit are
   * identified by recording any changes made to the view.
   *
   * @throws UnsupportedOperationException if called on a modified view
   * @throws IllegalStateException         if called on a closed view
   * @see #isClosed()
   * @see #isModified()
   * 
   * @return a committable view based on change recording
   */
  CommittableView withChangeRecordingTrait();

  /**
   * Returns a {@link CommittableView} based on the view's configuration. Changes
   * to commit are
   * identified by comparing the current view state with its state from the last
   * update.
   *
   * @param changeResolutionStrategy The change resolution strategy to use for
   *                                 view state
   *                                 comparison. Must not be <code>null</code>.
   * @throws UnsupportedOperationException if called on a modified view
   * @throws IllegalStateException         if called on a closed view
   * @see #isClosed()
   * @see #isModified()
   * 
   * @return a committable view based on change derivation
   */
  CommittableView withChangeDerivingTrait(
      StateBasedChangeResolutionStrategy changeResolutionStrategy);

  /**
   * Returns a {@link CommittableView} based on the view's configuration. Changes
   * to commit are
   * identified by comparing the current view state with its state from the last
   * update. To compare
   * states the {@link DefaultStateBasedChangeResolutionStrategy} is applied.
   *
   * @throws UnsupportedOperationException if called on a modified view
   * @throws IllegalStateException         if called on a closed view
   * @see #isClosed()
   * @see #isModified()
   * 
   * @return a committable view based on change derivation
   */
  default CommittableView withChangeDerivingTrait() {
    return withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
  }
}
