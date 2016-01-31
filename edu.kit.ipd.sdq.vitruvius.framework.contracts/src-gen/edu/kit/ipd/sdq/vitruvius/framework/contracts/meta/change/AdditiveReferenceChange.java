/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive Reference Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getAdditiveReferenceChange()
 * @model abstract="true"
 * @generated
 */
public interface AdditiveReferenceChange<T extends EObject> extends AdditiveChange<T> {
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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getAdditiveReferenceChange_NewValue()
     * @model required="true"
     * @generated
     */
    T getNewValue();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange#getNewValue <em>New Value</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Value</em>' reference.
     * @see #getNewValue()
     * @generated
     */
    void setNewValue(T value);

} // AdditiveReferenceChange
