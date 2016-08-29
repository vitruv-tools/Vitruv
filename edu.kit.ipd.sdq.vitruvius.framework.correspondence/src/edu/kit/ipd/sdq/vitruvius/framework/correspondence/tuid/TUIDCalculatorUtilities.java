package edu.kit.ipd.sdq.vitruvius.framework.correspondence.tuid;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Utility class for TUID generators.
 * 
 * @author Stephan Seifermann
 *
 */
public class TUIDCalculatorUtilities {

    /**
     * Takes an EObject and a prefix and calculates a generic individual identifier from it. It uses
     * the containing feature and the index to make it unique at the parent object.
     * 
     * Attention: This method must NOT be used since during correspondence adding or updating there
     * might be issues with overlapping identifiers!
     * 
     * @param obj The object for which the ID shall be calculated.
     * @param prefix A prefix, which is prepended.
     * @return The individual TUID.
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static String getGenericIndividualIdentifier(EObject obj, String prefix) {
        EObject container = obj.eContainer();
        EReference containingFeature = obj.eContainmentFeature();
        String tuidPart = prefix + containingFeature.getName();
        if (containingFeature.getUpperBound() == -1 || containingFeature.getUpperBound() > 1) {
            tuidPart += ((EList<EObject>) container.eGet(containingFeature)).indexOf(obj);
        }
        return tuidPart;
    }

}
