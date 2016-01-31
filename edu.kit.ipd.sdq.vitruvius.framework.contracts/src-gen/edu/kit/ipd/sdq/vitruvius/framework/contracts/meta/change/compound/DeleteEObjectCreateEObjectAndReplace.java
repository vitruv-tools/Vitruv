/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete EObject Create EObject And Replace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getDeleteChange <em>Delete Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getReplaceChange <em>Replace Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectCreateEObjectAndReplace()
 * @model abstract="true"
 * @generated
 */
public interface DeleteEObjectCreateEObjectAndReplace<T extends EObject, R extends EChange> extends ECompoundChange {
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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectCreateEObjectAndReplace_DeleteChange()
     * @model containment="true" required="true"
     * @generated
     */
    DeleteEObject<T> getDeleteChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getDeleteChange <em>Delete Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Change</em>' containment reference.
     * @see #getDeleteChange()
     * @generated
     */
    void setDeleteChange(DeleteEObject<T> value);

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectCreateEObjectAndReplace_CreateChange()
     * @model containment="true" required="true"
     * @generated
     */
    CreateEObject<T> getCreateChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getCreateChange <em>Create Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Create Change</em>' containment reference.
     * @see #getCreateChange()
     * @generated
     */
    void setCreateChange(CreateEObject<T> value);

    /**
     * Returns the value of the '<em><b>Replace Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Replace Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Replace Change</em>' containment reference.
     * @see #setReplaceChange(EChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getDeleteEObjectCreateEObjectAndReplace_ReplaceChange()
     * @model containment="true" required="true"
     * @generated
     */
    R getReplaceChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getReplaceChange <em>Replace Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Replace Change</em>' containment reference.
     * @see #getReplaceChange()
     * @generated
     */
    void setReplaceChange(R value);

} // DeleteEObjectCreateEObjectAndReplace
