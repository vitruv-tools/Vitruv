package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public class DefaultTUIDCalculatorAndResolver implements TUIDCalculatorAndResolver {

    @Override
    public String getTUID(final EObject eObject) {
        if (eObject != null) {
            VURI vuri = VURI.getInstance(eObject.eResource());
            String uri = vuri.toString();
            EStructuralFeature idFeature = eObject.eClass().getEStructuralFeature("id");
            if (idFeature != null && idFeature instanceof EAttribute) {
                EAttribute idAttribute = (EAttribute) idFeature;
                EDataType eAttributeType = idAttribute.getEAttributeType();
                EDataType eString = EcorePackage.eINSTANCE.getEString();
                if (eString != null && eString.equals(eAttributeType)) {
                    return uri + EOBJECT_SEPERATOR + (String) eObject.eGet(idFeature);
                }
                EDataType eInt = EcorePackage.eINSTANCE.getEInt();
                if (eInt != null && eString.equals(eAttributeType)) {
                    return uri + EOBJECT_SEPERATOR + String.valueOf(eObject.eGet(idFeature));
                }
            }
        }
        throw new RuntimeException("The DefaultTUIDCalculator cannot be used for the eObject '" + eObject + "'");
    }

    @Override
    public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EObject getIdentifiedEObjectWithinRootEObject(final EObject root, final String tuid) {
        // TODO Auto-generated method stub
        return null;
    }
}
