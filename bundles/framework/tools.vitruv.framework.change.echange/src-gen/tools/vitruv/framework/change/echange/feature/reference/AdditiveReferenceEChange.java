/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which inserts an EObject into a reference.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange#isWasUnset <em>Was Unset</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getAdditiveReferenceEChange()
 * @model abstract="true"
 * @generated
 */
public interface AdditiveReferenceEChange<A extends EObject, T extends EObject> extends UpdateReferenceEChange<A>, EObjectAddedEChange<T> {
	/**
	 * Returns the value of the '<em><b>Was Unset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Was Unset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Was Unset</em>' attribute.
	 * @see #setWasUnset(boolean)
	 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getAdditiveReferenceEChange_WasUnset()
	 * @model
	 * @generated
	 */
	boolean isWasUnset();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange#isWasUnset <em>Was Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Was Unset</em>' attribute.
	 * @see #isWasUnset()
	 * @generated
	 */
	void setWasUnset(boolean value);

} // AdditiveReferenceEChange
