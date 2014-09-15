package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

public class CorrespondenceUtils {

    public static void updateCorrespondence(CorrespondenceInstance correspondenceInstance, EObject oldEObject, EObject newEObject) {
        correspondenceInstance.update(oldEObject, newEObject);
    }
    
}
