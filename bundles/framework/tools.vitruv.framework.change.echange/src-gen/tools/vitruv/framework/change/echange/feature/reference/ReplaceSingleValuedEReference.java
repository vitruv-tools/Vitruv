/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which replaces a single valued reference with a new EObject.
 * If the reference is a containment reference, the new object will be taken from the staging
 * area and the old one will be placed in it.
 * The new object must be placed in the staging area by a {@link CreateEObject} EChange or by removing
 * it from another reference.
 * The old one can be deleted by a {@link DeleteEObject} EChange or resinserted by another change.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getReplaceSingleValuedEReference()
 * @model
 * @generated
 */
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends ReplaceSingleValuedFeatureEChange<A, EReference, T>, AdditiveReferenceEChange<A, T>, SubtractiveReferenceEChange<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns if all proxy EObjects of the change are resolved to concrete EObjects of a resource set.
	 * Needs to be true to apply the change.
	 * @return	All proxy EObjects are resolved to concrete EObjects.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && (<%com.google.common.base.Objects%>.equal(this.getOldValue(), null) || (!this.getOldValue().eIsProxy()))) && (<%com.google.common.base.Objects%>.equal(this.getNewValue(), null) || (!this.getNewValue().eIsProxy())));'"
	 * @generated
	 */
	boolean isResolved();

} // ReplaceSingleValuedEReference
