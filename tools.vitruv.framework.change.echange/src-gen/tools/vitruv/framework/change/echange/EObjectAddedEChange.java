/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Added EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.EObjectAddedEChange#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.EObjectAddedEChange#isIsCreate <em>Is Create</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectAddedEChange()
 * @model abstract="true"
 * @generated
 */
public interface EObjectAddedEChange<T extends EObject> extends AdditiveEChange<T> {
    /**
	 * Returns the value of the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Value</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' reference.
	 * @see #setNewValue(EObject)
	 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectAddedEChange_NewValue()
	 * @model required="true"
	 * @generated
	 */
    T getNewValue();

    /**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.EObjectAddedEChange#getNewValue <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' reference.
	 * @see #getNewValue()
	 * @generated
	 */
    void setNewValue(T value);

    /**
	 * Returns the value of the '<em><b>Is Create</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Create</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Create</em>' attribute.
	 * @see #setIsCreate(boolean)
	 * @see tools.vitruv.framework.change.echange.EChangePackage#getEObjectAddedEChange_IsCreate()
	 * @model required="true"
	 * @generated
	 */
    boolean isIsCreate();

    /**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.EObjectAddedEChange#isIsCreate <em>Is Create</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Create</em>' attribute.
	 * @see #isIsCreate()
	 * @generated
	 */
    void setIsCreate(boolean value);

} // EObjectAddedEChange
