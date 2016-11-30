/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Subtracted EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.EObjectSubtractedEChange#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.EObjectSubtractedEChange#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectSubtractedEChange()
 * @model abstract="true"
 * @generated
 */
public interface EObjectSubtractedEChange<T extends EObject> extends SubtractiveEChange<T> {
    /**
	 * Returns the value of the '<em><b>Old Value</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old Value</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' reference.
	 * @see #setOldValue(EObject)
	 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectSubtractedEChange_OldValue()
	 * @model required="true"
	 * @generated
	 */
    T getOldValue();

    /**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.EObjectSubtractedEChange#getOldValue <em>Old Value</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' reference.
	 * @see #getOldValue()
	 * @generated
	 */
    void setOldValue(T value);

    /**
	 * Returns the value of the '<em><b>Is Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Delete</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Delete</em>' attribute.
	 * @see #setIsDelete(boolean)
	 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectSubtractedEChange_IsDelete()
	 * @model required="true"
	 * @generated
	 */
    boolean isIsDelete();

    /**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.EObjectSubtractedEChange#isIsDelete <em>Is Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Delete</em>' attribute.
	 * @see #isIsDelete()
	 * @generated
	 */
    void setIsDelete(boolean value);

} // EObjectSubtractedEChange
