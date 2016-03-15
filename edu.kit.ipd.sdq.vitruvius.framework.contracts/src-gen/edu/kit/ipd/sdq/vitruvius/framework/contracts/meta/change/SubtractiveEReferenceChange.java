/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Subtractive EReference Change</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange()
 * @model abstract="true"
 * @generated
 */
public interface SubtractiveEReferenceChange<T extends EObject> extends SubtractiveEChange<T> {
    /**
     * Returns the value of the '<em><b>Old Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old Value</em>' reference isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old Value</em>' reference.
     * @see #setOldValue(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange_OldValue()
     * @model required="true"
     * @generated
     */
    @Override
    T getOldValue();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldValue <em>Old Value</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' reference.
     * @see #getOldValue()
     * @generated
     */
    void setOldValue(T value);

    /**
     * Returns the value of the '<em><b>Is Delete</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Delete</em>' attribute isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Delete</em>' attribute.
     * @see #setIsDelete(boolean)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange_IsDelete()
     * @model required="true"
     * @generated
     */
    boolean isIsDelete();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete <em>Is Delete</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Delete</em>' attribute.
     * @see #isIsDelete()
     * @generated
     */
    void setIsDelete(boolean value);

} // SubtractiveEReferenceChange
