/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage#getReplaceSingleValuedEReference()
 * @model
 * @generated
 */
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends UpdateSingleValuedFeatureEChange<A, EReference>, SubtractiveReferenceEChange<A, T>, AdditiveReferenceEChange<A, T> {
} // ReplaceSingleValuedEReference
