/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.FeatureEChange;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Update Reference EChange</b></em>'. <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.ReferencePackage#getUpdateReferenceEChange()
 * @model abstract="true"
 * @generated
 */
public interface UpdateReferenceEChange<A extends EObject> extends FeatureEChange<A, EReference> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation" required="true"
     * @generated NOT
     */
    default boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

} // UpdateReferenceEChange
