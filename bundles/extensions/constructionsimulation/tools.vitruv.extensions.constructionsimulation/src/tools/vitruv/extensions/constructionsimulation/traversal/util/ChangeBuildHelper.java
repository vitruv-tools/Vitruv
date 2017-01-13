package tools.vitruv.extensions.constructionsimulation.traversal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CompoundFactory;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.EobjectFactory;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory;

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

        final InsertEReference<EObject, EObject> insertChange = ReferenceFactory.eINSTANCE
                .createInsertEReference();
        final EObject container = source.eContainer();

        final EReference containingReference = (EReference) source.eContainingFeature();

        // list of all contained elements in the container
        final Object contents = container.eGet(containingReference);

        insertChange.setAffectedEObject(container);
        
        // find index of current element in the container
        int index = -1;
        if (contents instanceof EList) {
            @SuppressWarnings("unchecked")
            final EList<EObject> contentsList = ((EList<EObject>) contents);
            index = contentsList.indexOf(source);
        } else {
            throw new IllegalStateException("ContainingReference must be of type EList");
        }

        insertChange.setAffectedFeature(containingReference);
        insertChange.setIndex(index);
        insertChange.setNewValue(source);

        final CreateEObject<EObject> createChange = EobjectFactory.eINSTANCE.createCreateEObject();
        createChange.setAffectedEObject(source);

        final CreateAndInsertNonRoot<EObject, EObject> compositeChange = CompoundFactory.eINSTANCE.createCreateAndInsertNonRoot();
        compositeChange.setCreateChange(createChange);
        compositeChange.setInsertChange(insertChange);
        
        return compositeChange;
    }

}
