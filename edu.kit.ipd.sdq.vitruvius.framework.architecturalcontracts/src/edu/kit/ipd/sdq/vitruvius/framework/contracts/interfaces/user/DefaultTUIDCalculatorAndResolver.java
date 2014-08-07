package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

public class DefaultTUIDCalculatorAndResolver implements TUIDCalculatorAndResolver {

    private static final Logger logger = Logger.getLogger(DefaultTUIDCalculatorAndResolver.class.getSimpleName());
    private String nameOfIdFeature;

    public DefaultTUIDCalculatorAndResolver() {
        this(VitruviusConstants.getDefaultNameOfIdentifierFeature());
    }

    public DefaultTUIDCalculatorAndResolver(final String nameOfIDFeature) {
        this.nameOfIdFeature = nameOfIDFeature;
    }

    /**
     * class name is used as prefix for every UUID to determine whether an TUID was created using
     * this class
     */
    private static final String TUIDIdentifier = DefaultTUIDCalculatorAndResolver.class.getSimpleName();

    @Override
    public String calculateTUIDFromEObject(final EObject eObject) {
        if (eObject != null) {
            String uri = String.valueOf(TUIDIdentifier) + VitruviusConstants.getTUIDSegmentSeperator();
            if (null != eObject.eResource()) {
                VURI vuri = VURI.getInstance(eObject.eResource());
                uri += vuri.toString();
            } else {
                logger.info("EObject " + eObject
                        + " is not contained in a resource. Use id as only identifier for object.");
            }
            String id = getValueOfIdFeature(eObject);
            if (null != id) {
                return uri + VitruviusConstants.getTUIDSegmentSeperator() + id;
            }
        }
        throw new RuntimeException("The DefaultTUIDCalculator cannot be used for the eObject '" + eObject + "'");
    }

    @Override
    public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        checkTUID(tuidParts);
        String vuriKey = tuidParts[1];
        return VURI.getInstance(vuriKey);
    }

    @Override
    public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
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
        boolean claim = true;
        isValidTUID(tuidParts, claim);
    }

    @Override
    public boolean isValidTUID(final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        boolean claim = false;
        return isValidTUID(tuidParts, claim);
    }

    private boolean isValidTUID(final String[] tuidParts, final boolean claim) {
        if (0 == tuidParts.length) {
            if (claim) {
                throw new RuntimeException("Cannot parse TUID: " + tuidParts);
            } else {
                return false;
            }
        }
        if (!tuidParts[0].equals(String.valueOf(TUIDIdentifier))) {
            if (claim) {
                throw new RuntimeException("TUID " + tuidParts + " was not created using " + TUIDIdentifier);
            } else {
                return false;
            }
        }
        return true;
    }

    private String getValueOfIdFeature(final EObject eObject) {
        EStructuralFeature idFeature = eObject.eClass().getEStructuralFeature(this.nameOfIdFeature);
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
