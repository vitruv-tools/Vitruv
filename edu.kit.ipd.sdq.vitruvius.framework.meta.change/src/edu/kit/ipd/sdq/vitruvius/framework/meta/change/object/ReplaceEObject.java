/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject#getNewEObject <em>New EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getReplaceEObject()
 * @model abstract="true"
 * @generated
 */
public interface ReplaceEObject<T extends EObject> extends EObjectChange<T> {
	/**
	 * Returns the value of the '<em><b>New EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New EObject</em>' reference.
	 * @see #setNewEObject(EObject)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getReplaceEObject_NewEObject()
	 * @model required="true"
	 * @generated
	 */
	T getNewEObject();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject#getNewEObject <em>New EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New EObject</em>' reference.
	 * @see #getNewEObject()
	 * @generated
	 */
	void setNewEObject(T value);

} // ReplaceEObject
