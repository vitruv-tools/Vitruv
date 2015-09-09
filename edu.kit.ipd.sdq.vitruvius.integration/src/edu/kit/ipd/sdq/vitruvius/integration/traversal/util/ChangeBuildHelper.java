package edu.kit.ipd.sdq.vitruvius.integration.traversal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;

/**
 * A helper class that provides methods for creating atomic change models from EMF elements
 *
 * @author Sven Leonhardt
 */
public abstract class ChangeBuildHelper {

    /**
     * This method creates a list which contains a single EChange element.
     *
     * @param source
     *            : The element which was added
     * @return : EList containing one EChange element
     */
    protected static EChange createSingleAddNonRootEObjectInListChange(final EObject source) {

        final CreateNonRootEObjectInList<EObject> change = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        final EObject container = source.eContainer();

        final EReference containingReference = (EReference) source.eContainingFeature();

        // list of all contained elements in the container
        final Object contents = container.eGet(containingReference);

        change.setNewAffectedEObject(container);
        change.setOldAffectedEObject(container);

        // find index of current element in the container
        int index = -1;
        if (contents instanceof EList) {
            @SuppressWarnings("unchecked")
            final EList<EObject> contentsList = ((EList<EObject>) contents);
            index = contentsList.indexOf(source);
        } else {
            throw new IllegalStateException("ContainingReference must be of type EList");
        }

        change.setAffectedFeature(containingReference);
        change.setIndex(index);
        change.setNewValue(source);

        return change;
    }

}
