/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getNewValue <em>New Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getReplaceRootEObject()
 * @model
 * @generated
 */
public interface ReplaceRootEObject<T extends EObject> extends ReplaceEObject<T> {
	/**
     * Returns the value of the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>New Value</em>' attribute.
     * @see #setNewValue(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getReplaceRootEObject_NewValue()
     * @model required="true"
     * @generated
     */
	T getNewValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getNewValue <em>New Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Value</em>' attribute.
     * @see #getNewValue()
     * @generated
     */
	void setNewValue(T value);

	/**
     * Returns the value of the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Old Value</em>' attribute.
     * @see #setOldValue(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getReplaceRootEObject_OldValue()
     * @model required="true"
     * @generated
     */
	T getOldValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getOldValue <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' attribute.
     * @see #getOldValue()
     * @generated
     */
	void setOldValue(T value);

} // ReplaceRootEObject
