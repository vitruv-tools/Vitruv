/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEFeature#getNewValue <em>New Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getUpdateEFeature()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface UpdateEFeature<T extends Object> extends EObject {
    /**
     * Returns the value of the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Value</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>New Value</em>' reference.
     * @see #setNewValue(Object)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getUpdateEFeature_NewValue()
     * @model kind="reference" required="true"
     * @generated
     */
    T getNewValue();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEFeature#getNewValue <em>New Value</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Value</em>' reference.
     * @see #getNewValue()
     * @generated
     */
    void setNewValue(T value);

} // UpdateEFeature
