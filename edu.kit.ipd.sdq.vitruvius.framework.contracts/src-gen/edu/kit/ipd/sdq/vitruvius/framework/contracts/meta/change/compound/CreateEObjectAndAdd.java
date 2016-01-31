/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create EObject And Add</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getAddChange <em>Add Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getCreateEObjectAndAdd()
 * @model
 * @generated
 */
public interface CreateEObjectAndAdd<T extends EObject> extends ECompoundChange {
    /**
     * Returns the value of the '<em><b>Create Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Create Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Create Change</em>' containment reference.
     * @see #setCreateChange(CreateEObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getCreateEObjectAndAdd_CreateChange()
     * @model containment="true" required="true"
     * @generated
     */
    CreateEObject<T> getCreateChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getCreateChange <em>Create Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Create Change</em>' containment reference.
     * @see #getCreateChange()
     * @generated
     */
    void setCreateChange(CreateEObject<T> value);

    /**
     * Returns the value of the '<em><b>Add Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Add Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Add Change</em>' containment reference.
     * @see #setAddChange(AdditiveReferenceChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getCreateEObjectAndAdd_AddChange()
     * @model containment="true" required="true"
     * @generated
     */
    AdditiveReferenceChange<T> getAddChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getAddChange <em>Add Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Change</em>' containment reference.
     * @see #getAddChange()
     * @generated
     */
    void setAddChange(AdditiveReferenceChange<T> value);

} // CreateEObjectAndAdd
