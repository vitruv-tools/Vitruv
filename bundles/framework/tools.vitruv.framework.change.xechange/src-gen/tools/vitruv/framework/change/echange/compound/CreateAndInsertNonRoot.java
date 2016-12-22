/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.CreateEObject;

import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create And Insert Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot#getInsertChange <em>Insert Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertNonRoot()
 * @model
 * @generated
 */
public interface CreateAndInsertNonRoot<A extends EObject, T extends EObject> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Change</em>' containment reference.
	 * @see #setCreateChange(CreateEObject)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertNonRoot_CreateChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CreateEObject<T> getCreateChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot#getCreateChange <em>Create Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Change</em>' containment reference.
	 * @see #getCreateChange()
	 * @generated
	 */
	void setCreateChange(CreateEObject<T> value);

	/**
	 * Returns the value of the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insert Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insert Change</em>' containment reference.
	 * @see #setInsertChange(InsertEReference)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertNonRoot_InsertChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InsertEReference<A, T> getInsertChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot#getInsertChange <em>Insert Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insert Change</em>' containment reference.
	 * @see #getInsertChange()
	 * @generated
	 */
	void setInsertChange(InsertEReference<A, T> value);

} // CreateAndInsertNonRoot
