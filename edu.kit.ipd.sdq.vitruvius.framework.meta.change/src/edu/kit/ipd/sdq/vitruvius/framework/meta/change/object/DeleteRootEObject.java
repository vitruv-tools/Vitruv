/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getDeleteRootEObject()
 * @model
 * @generated
 */
public interface DeleteRootEObject<T extends EObject> extends DeleteEObject<T> {

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage#getDeleteRootEObject_OldValue()
     * @model required="true"
     * @generated
     */
	T getOldValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject#getOldValue <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' attribute.
     * @see #getOldValue()
     * @generated
     */
	void setOldValue(T value);
} // DeleteRootEObject
