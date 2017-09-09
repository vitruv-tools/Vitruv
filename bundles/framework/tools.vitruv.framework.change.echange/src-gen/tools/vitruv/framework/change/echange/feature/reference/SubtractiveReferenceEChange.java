/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which removes an EObject from a reference.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange#isIsUnset <em>Is Unset</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getSubtractiveReferenceEChange()
 * @model abstract="true"
 * @generated
 */
public interface SubtractiveReferenceEChange<A extends EObject, T extends EObject> extends UpdateReferenceEChange<A>, EObjectSubtractedEChange<T> {
	/**
	 * Returns the value of the '<em><b>Is Unset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Unset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Unset</em>' attribute.
	 * @see #setIsUnset(boolean)
	 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getSubtractiveReferenceEChange_IsUnset()
	 * @model
	 * @generated
	 */
	boolean isIsUnset();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange#isIsUnset <em>Is Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Unset</em>' attribute.
	 * @see #isIsUnset()
	 * @generated
	 */
	void setIsUnset(boolean value);

} // SubtractiveReferenceEChange
