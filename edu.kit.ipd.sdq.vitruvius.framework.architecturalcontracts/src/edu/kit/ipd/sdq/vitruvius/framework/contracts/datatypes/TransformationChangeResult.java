package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * A TransformationChangeResult holds the EObjects that are created/changed/deleted during the
 * actual execution of the transformations. To get the VURIs of the EObjects one have to translate
 * this into {@link EMFChangeResult} using project specific information. This is done, e.g. by the
 * class {@link PCMJaMoPPTransformationExecuter}.
 *
 * @author Langhamm
 *
 */
public class TransformationChangeResult extends AddDeleteChangeResult<EObject, EObject, VURI> {

    public TransformationChangeResult() {
        super();
    }

    public TransformationChangeResult(final Set<EObject> existingEObjectsToSave, final Set<EObject> newEObjects,
            final Set<VURI> existingEObjectsIsToDelete) {
        super(existingEObjectsToSave, newEObjects, existingEObjectsIsToDelete);
    }
}
