package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public class DefaultTUIDCalculatorAndResolver implements TUIDCalculatorAndResolver {

    private static final Logger logger = Logger.getLogger(DefaultTUIDCalculatorAndResolver.class.getSimpleName());

    /**
     * class name is used as prefix for every UUID to determine wheather an TUID was createded using
     * this class
     */
    private static final String TUIDIdentifier = DefaultTUIDCalculatorAndResolver.class.getSimpleName();

    @Override
    public String getTUID(final EObject eObject) {
        if (eObject != null) {
            String uri = String.valueOf(TUIDIdentifier) + EOBJECT_SEPERATOR;
            if (null != eObject.eResource()) {
                VURI vuri = VURI.getInstance(eObject.eResource());
                uri += vuri.toString();
            } else {
                logger.info("EObject " + eObject
                        + " is not contained in a resource. Use id as only identifier for object.");
            }
            String id = getValueOfIdFeature(eObject);
            if (null != id) {
                return uri + EOBJECT_SEPERATOR + id;
            }
        }
        throw new RuntimeException("The DefaultTUIDCalculator cannot be used for the eObject '" + eObject + "'");
    }

    @Override
    public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
        String[] tuidParts = tuid.split(EOBJECT_SEPERATOR);
        checkTUID(tuidParts);
        String vuriKey = tuidParts[1];
        return VURI.getInstance(vuriKey);
    }

    @Override
    public EObject getIdentifiedEObjectWithinRootEObject(final EObject root, final String tuid) {
        String[] tuidParts = tuid.split(EOBJECT_SEPERATOR);
        checkTUID(tuidParts);
        String id = tuidParts[2];
        for (EObject eObject : root.eContents()) {
            if (id.equals(getValueOfIdFeature(eObject))) {
                return eObject;
            }
        }
        logger.warn("EObject with tuid: " + tuid + " not found within root EObject " + root);
        return null;
    }

    private void checkTUID(final String[] tuidParts) {
        if (0 == tuidParts.length) {
            throw new RuntimeException("Can not parse TUID: " + tuidParts);
        }
        if (!tuidParts[0].equals(String.valueOf(TUIDIdentifier))) {
            throw new RuntimeException("TUID " + tuidParts + " was not created using " + TUIDIdentifier);
        }
    }

    private String getValueOfIdFeature(final EObject eObject) {
        EStructuralFeature idFeature = eObject.eClass().getEStructuralFeature("id");
        if (idFeature != null && idFeature instanceof EAttribute) {
            EAttribute idAttribute = (EAttribute) idFeature;
            EDataType eAttributeType = idAttribute.getEAttributeType();
            EDataType eString = EcorePackage.eINSTANCE.getEString();
            if (eString != null && eString.equals(eAttributeType)) {
                return (String) eObject.eGet(idFeature);
            }
            EDataType eInt = EcorePackage.eINSTANCE.getEInt();
            if (eInt != null && eString.equals(eAttributeType)) {
                return String.valueOf(eObject.eGet(idFeature));
            }
        }
        return null;
    }

}
