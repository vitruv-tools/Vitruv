/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive Attribute Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange#getOldValue <em>Old Value</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveAttributeChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface SubtractiveAttributeChange<T extends Object> extends SubtractiveChange<T> {
    /**
     * Returns the value of the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old Value</em>' attribute.
     * @see #setOldValue(Object)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveAttributeChange_OldValue()
     * @model required="true"
     * @generated
     */
    T getOldValue();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange#getOldValue <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' attribute.
     * @see #getOldValue()
     * @generated
     */
    void setOldValue(T value);

} // SubtractiveAttributeChange
