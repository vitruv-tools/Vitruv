/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage#getUnsetEReference()
 * @model
 * @generated
 */
public interface UnsetEReference<T extends EObject> extends UnsetEFeature<EReference>, SubtractiveReferenceChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    boolean isContainment();

} // UnsetEReference
