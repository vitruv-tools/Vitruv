/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which removes an EObject from a many valued reference.
 * If the reference is a containment reference, the removed object will be placed in the staging area.
 * There it can be deleted by a {@link DeleteEObject} EChange or reinserted by another change.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getRemoveEReference()
 * @model
 * @generated
 */
public interface RemoveEReference<A extends EObject, T extends EObject> extends RemoveFromListEChange<A, EReference, T>, SubtractiveReferenceEChange<A, T> {
} // RemoveEReference
