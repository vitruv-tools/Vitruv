/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which removes an existing EObject from a many valued containment reference and deletes it.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteNonRoot()
 * @model
 * @generated
 */
public interface RemoveAndDeleteNonRoot<A extends EObject, T extends EObject> extends RemoveAndDeleteEObject<T, RemoveEReference<A, T>> {
} // RemoveAndDeleteNonRoot
