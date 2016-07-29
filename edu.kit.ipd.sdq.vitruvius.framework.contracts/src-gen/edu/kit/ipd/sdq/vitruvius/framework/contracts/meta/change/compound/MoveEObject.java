/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EObjectAddedEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EObjectSubtractedEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateReferenceEChange;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Move EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject()
 * @model
 * @generated
 */
public interface MoveEObject<A extends EObject, B extends EObject, T extends EObject> extends CompoundEChange {
    /**
     * Returns the value of the '<em><b>Subtract Where Change</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subtract Where Change</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subtract Where Change</em>' reference.
     * @see #setSubtractWhereChange(UpdateReferenceEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_SubtractWhereChange()
     * @model
     * @generated
     */
    UpdateReferenceEChange<A> getSubtractWhereChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Subtract Where Change</em>' reference.
     * @see #getSubtractWhereChange()
     * @generated
     */
    void setSubtractWhereChange(UpdateReferenceEChange<A> value);

    /**
     * Returns the value of the '<em><b>Subtract What Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subtract What Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subtract What Change</em>' containment reference.
     * @see #setSubtractWhatChange(EObjectSubtractedEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_SubtractWhatChange()
     * @model containment="true" required="true"
     * @generated
     */
    EObjectSubtractedEChange<T> getSubtractWhatChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Subtract What Change</em>' containment reference.
     * @see #getSubtractWhatChange()
     * @generated
     */
    void setSubtractWhatChange(EObjectSubtractedEChange<T> value);

    /**
     * Returns the value of the '<em><b>Add Where Change</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Add Where Change</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Add Where Change</em>' reference.
     * @see #setAddWhereChange(UpdateReferenceEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_AddWhereChange()
     * @model
     * @generated
     */
    UpdateReferenceEChange<B> getAddWhereChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Where Change</em>' reference.
     * @see #getAddWhereChange()
     * @generated
     */
    void setAddWhereChange(UpdateReferenceEChange<B> value);

    /**
     * Returns the value of the '<em><b>Add What Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Add What Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Add What Change</em>' containment reference.
     * @see #setAddWhatChange(EObjectAddedEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_AddWhatChange()
     * @model containment="true" required="true"
     * @generated
     */
    EObjectAddedEChange<T> getAddWhatChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add What Change</em>' containment reference.
     * @see #getAddWhatChange()
     * @generated
     */
    void setAddWhatChange(EObjectAddedEChange<T> value);

} // MoveEObject
