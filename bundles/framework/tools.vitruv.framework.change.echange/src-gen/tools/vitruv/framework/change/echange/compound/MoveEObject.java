/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Move EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getMoveEObject()
 * @model
 * @generated
 */
public interface MoveEObject<A extends EObject, B extends EObject, T extends EObject> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Subtract Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subtract Where Change</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subtract Where Change</em>' reference.
	 * @see #setSubtractWhereChange(UpdateReferenceEChange)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getMoveEObject_SubtractWhereChange()
	 * @model
	 * @generated
	 */
	UpdateReferenceEChange<A> getSubtractWhereChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subtract Where Change</em>' reference.
	 * @see #getSubtractWhereChange()
	 * @generated
	 */
	void setSubtractWhereChange(UpdateReferenceEChange<A> value);

	/**
	 * Returns the value of the '<em><b>Subtract What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subtract What Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subtract What Change</em>' containment reference.
	 * @see #setSubtractWhatChange(EObjectSubtractedEChange)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getMoveEObject_SubtractWhatChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EObjectSubtractedEChange<T> getSubtractWhatChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subtract What Change</em>' containment reference.
	 * @see #getSubtractWhatChange()
	 * @generated
	 */
	void setSubtractWhatChange(EObjectSubtractedEChange<T> value);

	/**
	 * Returns the value of the '<em><b>Add Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Add Where Change</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Add Where Change</em>' reference.
	 * @see #setAddWhereChange(UpdateReferenceEChange)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getMoveEObject_AddWhereChange()
	 * @model
	 * @generated
	 */
	UpdateReferenceEChange<B> getAddWhereChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Add Where Change</em>' reference.
	 * @see #getAddWhereChange()
	 * @generated
	 */
	void setAddWhereChange(UpdateReferenceEChange<B> value);

	/**
	 * Returns the value of the '<em><b>Add What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Add What Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Add What Change</em>' containment reference.
	 * @see #setAddWhatChange(EObjectAddedEChange)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getMoveEObject_AddWhatChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EObjectAddedEChange<T> getAddWhatChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Add What Change</em>' containment reference.
	 * @see #getAddWhatChange()
	 * @generated
	 */
	void setAddWhatChange(EObjectAddedEChange<T> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> list = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>();\nfinal <%tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange%><A> subWhereChange = this.getSubtractWhereChange();\nboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(subWhereChange, null));\nif (_notEquals)\n{\n\tlist.add(subWhereChange);\n}\n<%tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange%><T> _subtractWhatChange = this.getSubtractWhatChange();\nlist.add(_subtractWhatChange);\nfinal <%tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange%><B> addWhereChange = this.getAddWhereChange();\nboolean _notEquals_1 = (!<%com.google.common.base.Objects%>.equal(addWhereChange, null));\nif (_notEquals_1)\n{\n\tlist.add(addWhereChange);\n}\n<%tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange%><T> _addWhatChange = this.getAddWhatChange();\nlist.add(_addWhatChange);\nreturn list;'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // MoveEObject
