/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove And Delete Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteRoot()
 * @model
 * @generated
 */
public interface RemoveAndDeleteRoot<T extends EObject> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove Change</em>' containment reference.
	 * @see #setRemoveChange(RemoveRootEObject)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteRoot_RemoveChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RemoveRootEObject<T> getRemoveChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot#getRemoveChange <em>Remove Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Change</em>' containment reference.
	 * @see #getRemoveChange()
	 * @generated
	 */
	void setRemoveChange(RemoveRootEObject<T> value);

	/**
	 * Returns the value of the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Change</em>' containment reference.
	 * @see #setDeleteChange(DeleteEObject)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteRoot_DeleteChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DeleteEObject<T> getDeleteChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot#getDeleteChange <em>Delete Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Change</em>' containment reference.
	 * @see #getDeleteChange()
	 * @generated
	 */
	void setDeleteChange(DeleteEObject<T> value);

} // RemoveAndDeleteRoot
