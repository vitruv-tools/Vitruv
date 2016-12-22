/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteNonRoot()
 * @model
 * @generated
 */
public interface RemoveAndDeleteNonRoot<A extends EObject, T extends EObject> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove Change</em>' containment reference.
	 * @see #setRemoveChange(RemoveEReference)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteNonRoot_RemoveChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RemoveEReference<A, T> getRemoveChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot#getRemoveChange <em>Remove Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Change</em>' containment reference.
	 * @see #getRemoveChange()
	 * @generated
	 */
	void setRemoveChange(RemoveEReference<A, T> value);

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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteNonRoot_DeleteChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DeleteEObject<T> getDeleteChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot#getDeleteChange <em>Delete Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Change</em>' containment reference.
	 * @see #getDeleteChange()
	 * @generated
	 */
	void setDeleteChange(DeleteEObject<T> value);

} // RemoveAndDeleteNonRoot
