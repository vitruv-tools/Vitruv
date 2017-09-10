/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange removes an existing root EObject from its resource and places it in the staging area.
 * There it can be deleted or be taken by another change to reinsert it.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRemoveRootEObject()
 * @model
 * @generated
 */
public interface RemoveRootEObject<T extends EObject> extends RootEChange, EObjectSubtractedEChange<T> {
} // RemoveRootEObject
