/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Insert Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which inserts a new root EObject into a resource. The object will
 * be taken from the staging area and needs to be filled by {@link CreateEObject} change first.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getInsertRootEObject()
 * @model
 * @generated
 */
public interface InsertRootEObject<T extends EObject> extends RootEChange, EObjectAddedEChange<T> {
} // InsertRootEObject
