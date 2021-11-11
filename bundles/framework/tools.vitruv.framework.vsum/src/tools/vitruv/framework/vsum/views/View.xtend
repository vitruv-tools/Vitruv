package tools.vitruv.framework.vsum.views

import java.util.Collection
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.vsum.ChangePropagationListener

/**
 * A Vitruv view on the virtual model. Upon creation, each view must be registered at the VSUM as change propagation listener.
 */
interface View extends ChangePropagationListener {

    /**
     * Provides the root model elements of this view.
     */
    def Collection<EObject> rootObjects()

    /**
     * Provides all root model elements of this view that conform to a certain type.
     * @param clazz is requested root element type.
     */
    def <T> Collection<T> rootObjects(Class<T> clazz) {
        rootObjects.filter[clazz.isInstance(it)].map[clazz.cast(it)].toList
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
     * Updates the view from the underlying virtual model.
     * TODO TS: Document that UnsupExc if change recorder still has changes, meaning view is dirty, and add Issue that this needs to change
     */
    def void update()

    /**
     * {@linkplain #record Records} the changes to {@code notifier} created by the provided {@code consumer},
     * saves the modified resource and propagates all recorded changes (including changes that have been recorded
     * before calling this method). Where the changes are propagated to depends on this view.
     * <p>
     * Whether changes will effectively be recorded depends on this view. It is permissible for a view not to record
     * any changes if it deems them irrelevant. In this case, the returned list will be empty.
     *
     * @return the changes resulting from propagating the recorded changes.
     */
    def <T extends Notifier> List<PropagatedChange> commitChanges(T notifier) // TODO TS: Doc that update will follow
    // TODO TS: There may be views that do not always record changes, and thus require explicit changes provided upon commit
}
