/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete EObject And Subtract</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getDeleteChange <em>Delete Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getSubtractChange <em>Subtract Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectAndSubtract()
 * @model
 * @generated
 */
public interface DeleteEObjectAndSubtract<T extends EObject> extends ECompoundChange {
    /**
     * Returns the value of the '<em><b>Delete Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Delete Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Delete Change</em>' containment reference.
     * @see #setDeleteChange(DeleteEObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectAndSubtract_DeleteChange()
     * @model containment="true" required="true"
     * @generated
     */
    DeleteEObject<T> getDeleteChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getDeleteChange <em>Delete Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Change</em>' containment reference.
     * @see #getDeleteChange()
     * @generated
     */
    void setDeleteChange(DeleteEObject<T> value);

    /**
     * Returns the value of the '<em><b>Subtract Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subtract Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subtract Change</em>' containment reference.
     * @see #setSubtractChange(SubtractiveReferenceChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectAndSubtract_SubtractChange()
     * @model containment="true" required="true"
     * @generated
     */
    SubtractiveReferenceChange getSubtractChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getSubtractChange <em>Subtract Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Subtract Change</em>' containment reference.
     * @see #getSubtractChange()
     * @generated
     */
    void setSubtractChange(SubtractiveReferenceChange value);

} // DeleteEObjectAndSubtract
