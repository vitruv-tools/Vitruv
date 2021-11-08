package tools.vitruv.framework.vsum.views

import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List
import java.util.function.Consumer

/**
 * A Vitruv view on the virtual model.
 */
interface View {

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
    def <T extends Notifier> List<PropagatedChange> commitChanges(T notifier, Consumer<T> consumer)
}
