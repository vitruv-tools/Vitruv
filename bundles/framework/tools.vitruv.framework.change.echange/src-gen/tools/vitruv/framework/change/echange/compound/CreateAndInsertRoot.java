/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.eobject.CreateEObject;

import tools.vitruv.framework.change.echange.root.InsertRootEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create And Insert Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot#getInsertChange <em>Insert Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertRoot()
 * @model
 * @generated
 */
public interface CreateAndInsertRoot<T extends EObject> extends CompoundEChange {
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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertRoot_CreateChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CreateEObject<T> getCreateChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot#getCreateChange <em>Create Change</em>}' containment reference.
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
	 * @see #setInsertChange(InsertRootEObject)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndInsertRoot_InsertChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InsertRootEObject<T> getInsertChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot#getInsertChange <em>Insert Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insert Change</em>' containment reference.
	 * @see #getInsertChange()
	 * @generated
	 */
	void setInsertChange(InsertRootEObject<T> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> result = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>();\n<%tools.vitruv.framework.change.echange.eobject.CreateEObject%><T> _createChange = this.getCreateChange();\nresult.add(_createChange);\n<%tools.vitruv.framework.change.echange.root.InsertRootEObject%><T> _insertChange = this.getInsertChange();\nresult.add(_insertChange);\nreturn result;'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // CreateAndInsertRoot
