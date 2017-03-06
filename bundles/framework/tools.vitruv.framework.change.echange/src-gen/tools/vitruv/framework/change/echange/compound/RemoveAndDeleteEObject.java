/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove And Delete EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteEObject()
 * @model abstract="true" TBounds="tools.vitruv.framework.change.echange.compound.EObj"
 * @generated
 */
public interface RemoveAndDeleteEObject<T extends EObject, C extends EObjectSubtractedEChange<T>> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove Change</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove Change</em>' containment reference.
	 * @see #setRemoveChange(EObjectSubtractedEChange)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteEObject_RemoveChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	C getRemoveChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getRemoveChange <em>Remove Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Change</em>' containment reference.
	 * @see #getRemoveChange()
	 * @generated
	 */
	void setRemoveChange(C value);

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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getRemoveAndDeleteEObject_DeleteChange()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DeleteEObject<T> getDeleteChange();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getDeleteChange <em>Delete Change</em>}' containment reference.
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> result = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>();\nC _removeChange = this.getRemoveChange();\nresult.add(_removeChange);\n<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T> _deleteChange = this.getDeleteChange();\nresult.add(_deleteChange);\nreturn result;'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (applyChange)\n{\n\tC _removeChange = this.getRemoveChange();\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply = _removeChange.resolveApply(resourceSet);\n\tthis.setRemoveChange(((C) _resolveApply));\n\t<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T> _deleteChange = this.getDeleteChange();\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply_1 = _deleteChange.resolveApply(resourceSet);\n\tthis.setDeleteChange(((<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T>) _resolveApply_1));\n}\nelse\n{\n\t<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T> _deleteChange_1 = this.getDeleteChange();\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveRevert = _deleteChange_1.resolveRevert(resourceSet);\n\tthis.setDeleteChange(((<%tools.vitruv.framework.change.echange.eobject.DeleteEObject%><T>) _resolveRevert));\n\tC _removeChange_1 = this.getRemoveChange();\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveRevert_1 = _removeChange_1.resolveRevert(resourceSet);\n\tthis.setRemoveChange(((C) _resolveRevert_1));\n}'"
	 * @generated
	 */
	void resolveAtomicChanges(ResourceSet resourceSet, boolean applyChange);

} // RemoveAndDeleteEObject
