/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Non Root EObject Single</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#getCreateNonRootEObjectSingle()
 * @model
 * @generated
 */
public interface CreateNonRootEObjectSingle<T extends EObject> extends CreateEObject<T>, UpdateSingleValuedContainmentEReference<T> {

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#getCreateNonRootEObjectSingle_NewValue()
     * @model required="true"
     * @generated
     */
	T getNewValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle#getNewValue <em>New Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Value</em>' attribute.
     * @see #getNewValue()
     * @generated
     */
	void setNewValue(T value);
} // CreateNonRootEObjectSingle
