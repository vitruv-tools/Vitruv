/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create And Replace And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getReplaceChange <em>Replace Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndReplaceAndDeleteNonRoot()
 * @model
 * @generated
 */
public interface CreateAndReplaceAndDeleteNonRoot<A extends EObject, T extends EObject> extends CompoundEChange {
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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndReplaceAndDeleteNonRoot_CreateChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CreateEObject<T> getCreateChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getCreateChange <em>Create Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Change</em>' containment reference.
	 * @see #getCreateChange()
	 * @generated
	 */
	void setCreateChange(CreateEObject<T> value);

	/**
	 * Returns the value of the '<em><b>Replace Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replace Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replace Change</em>' containment reference.
	 * @see #setReplaceChange(ReplaceSingleValuedEReference)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndReplaceAndDeleteNonRoot_ReplaceChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ReplaceSingleValuedEReference<A, T> getReplaceChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getReplaceChange <em>Replace Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replace Change</em>' containment reference.
	 * @see #getReplaceChange()
	 * @generated
	 */
	void setReplaceChange(ReplaceSingleValuedEReference<A, T> value);

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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndReplaceAndDeleteNonRoot_DeleteChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DeleteEObject<T> getDeleteChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getDeleteChange <em>Delete Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Change</em>' containment reference.
	 * @see #getDeleteChange()
	 * @generated
	 */
	void setDeleteChange(DeleteEObject<T> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> result = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>();\n<%tools.vitruv.framework.change.echange.eobject.CreateEObject%><T> _createChange = this.getCreateChange();\nresult.add(_createChange);\n<%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T> _replaceChange = this.getReplaceChange();\nresult.add(_replaceChange);\n<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T> _deleteChange = this.getDeleteChange();\nresult.add(_deleteChange);\nreturn result;'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // CreateAndReplaceAndDeleteNonRoot
