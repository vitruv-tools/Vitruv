package tools.vitruv.framework.vsum.views

import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.vsum.ChangePropagationListener
import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A Vitruv view on the virtual model. Upon creation, each view must be registered at the VSUM as change propagation listener.
 */
interface View extends ChangePropagationListener, AutoCloseable {

    /**
     * Provides the root model elements of this view.
     * @throws IllegalStateException if called on a closed view.
     * @see View#isClosed()
     */
    def Collection<EObject> rootObjects()

    /**
     * Provides all root model elements of this view that conform to a certain type.
     * @param clazz is requested root element type.
     * @throws IllegalStateException if called on a closed view.
     * @see View#isClosed()
     */
    def <T> Collection<T> rootObjects(Class<T> clazz) {
        rootObjects.filter(clazz).toList
    }

    /**
     * Specifies whether the view was modified.
     */
    def boolean isModified()

    /**
     * Specifies whether the underlying virtual model has changed.
     */
    def boolean hasVSUMChanged()

    /**
     * Updates the view from the underlying virtual model, thus invalidating its previous state and now
     * providing a updated view on the virtual model. This can only be done for an unmodified view.
     * TODO TS: Add issue - in the long term we need to changes this!
     * @throws UnsupportedOperationException if the update is called for a dirty view.
     * @throws IllegalStateException if called on a closed view.
     * @see View#isClosed()
     */
    def void update()

    /**
     * Commits the changes made to the view and its containing elements to the virtual model. This explicitly includes all
     * changes that have been made before calling this method. Whether changes will effectively be recorded depends on this view.
     * It is permissible for a view not to record any changes if it deems them irrelevant.
     * Note that committing changes will automatically be followed by an {@link #update} from the virtual model to keep both
     * the view and the virtual model synchronized.
     * @return the changes resulting from propagating the recorded changes.
     * @throws IllegalStateException if called on a closed view.
     * @see View#isClosed()
     */
    def List<PropagatedChange> commitChanges() // TODO TS: Some views may not always record changes, and thus require transactional record-and-commit.

    /**
     * Checks whether the view was closed. Closed view should not further be used.
     */
    def boolean isClosed()
    
    /**
     * Returns the {@link ViewSelector} with which this view has been created.
     */
     // TODO Potentially return some read-only interface of the selector (and even store it in a view)
    def ViewSelector getSelector()
    
}
