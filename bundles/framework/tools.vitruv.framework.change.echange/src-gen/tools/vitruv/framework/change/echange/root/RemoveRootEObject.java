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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && (!<%com.google.common.base.Objects%>.equal(this.getOldValue(), null))) && (!this.getOldValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

} // RemoveRootEObject
