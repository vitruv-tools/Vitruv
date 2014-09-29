package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

public class DefaultTUIDCalculatorAndResolver implements TUIDCalculatorAndResolver {

    private static final Logger logger = Logger.getLogger(DefaultTUIDCalculatorAndResolver.class.getSimpleName());
    private String nameOfIdFeature;
    private String fileExtensionsAsString = new String();

    public DefaultTUIDCalculatorAndResolver(final String... fileExtensions) {
        this(VitruviusConstants.getDefaultNameOfIdentifierFeature(), fileExtensions);
    }

    public DefaultTUIDCalculatorAndResolver(final String nameOfIDFeature, final String... fileExtensions) {
        this.nameOfIdFeature = nameOfIDFeature;
        for (String fileExtension : fileExtensions) {
            this.fileExtensionsAsString = fileExtension + "_";
        }
    }

    /**
     * class name is used as prefix for every TUID to determine whether an TUID was created using
     * this class
     */
    private static final String TUIDIdentifier = DefaultTUIDCalculatorAndResolver.class.getSimpleName();

    @Override
    public String calculateTUIDFromEObject(final EObject eObject) {
        if (eObject != null) {
            String uri = String.valueOf(TUIDIdentifier) + VitruviusConstants.getTUIDSegmentSeperator();
            if (null == this.fileExtensionsAsString) {
                uri = uri + VitruviusConstants.getTUIDSegmentSeperator();
            } else {
                uri = uri + this.fileExtensionsAsString + VitruviusConstants.getTUIDSegmentSeperator();
            }
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
    public String calculateTUIDFromEObject(final EObject eObject, final EObject virtualRootObject, final String prefix) {
        throw new UnsupportedOperationException("The " + DefaultTUIDCalculatorAndResolver.class.getSimpleName()
                + " is not able to process a prefix during the TUID calculation.");
    }

    @Override
    public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        checkTUID(tuidParts);
        String vuriKey = tuidParts[2];
        return VURI.getInstance(vuriKey);
    }

    @Override
    public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        checkTUID(tuidParts);
        String id = tuidParts[3];
        // check if the tuid is part of the root contents:
        TreeIterator<EObject> iter = EcoreUtil.getAllContents(root, true);
        while (iter.hasNext()) {
            EObject eObject = iter.next();
            if (id.equals(getValueOfIdFeature(eObject))) {
                return eObject;
            }
        }
        // it is not in the contents. check if its the root object itself:
        if (id.equals(getValueOfIdFeature(root))) {
            return root;
        }
        // EObject not found
        logger.warn("EObject with tuid: " + tuid + " not found within root EObject " + root);
        return null;
    }

    private void checkTUID(final String[] tuidParts) {
        boolean claim = true;
        isValidTUID(tuidParts, claim);
    }

    /**
     * Returns whether a TUID is valid for this specific TUIDCalculatorAndResolver. In case of
     * DefaultTUIDCalculatorAndResolver a tuid is valid iff 1.) the TUID was created using the class
     * TUIDCalculatorAndResolver and 2)
     */
    @Override
    public boolean isValidTUID(final String tuid) {
        String[] tuidParts = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        boolean claim = false;
        return isValidTUID(tuidParts, claim);
    }

    private boolean isValidTUID(final String[] tuidParts, final boolean claim) {
        if (2 > tuidParts.length) {
            if (claim) {
                throw new RuntimeException("Cannot parse TUID: " + tuidParts);
            } else {
                return false;
            }
        }
        boolean sameIdentifier = tuidParts[0].equals(TUIDIdentifier);
        boolean responsibleForFileExtensions = tuidParts[1].equals(this.fileExtensionsAsString);
        if (!(sameIdentifier && responsibleForFileExtensions)) {
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
