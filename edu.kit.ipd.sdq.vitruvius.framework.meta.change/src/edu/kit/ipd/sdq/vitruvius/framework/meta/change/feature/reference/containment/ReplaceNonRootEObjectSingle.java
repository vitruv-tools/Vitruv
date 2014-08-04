/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Non Root EObject Single</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#getReplaceNonRootEObjectSingle()
 * @model
 * @generated
 */
public interface ReplaceNonRootEObjectSingle<T extends EObject> extends ReplaceEObject<T>, UpdateSingleValuedContainmentEReference<T> {

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#getReplaceNonRootEObjectSingle_OldValue()
	 * @model required="true"
	 * @generated
	 */
	T getOldValue();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getOldValue <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' attribute.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(T value);

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#getReplaceNonRootEObjectSingle_NewValue()
	 * @model required="true"
	 * @generated
	 */
	T getNewValue();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getNewValue <em>New Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' attribute.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(T value);
} // ReplaceNonRootEObjectSingle
