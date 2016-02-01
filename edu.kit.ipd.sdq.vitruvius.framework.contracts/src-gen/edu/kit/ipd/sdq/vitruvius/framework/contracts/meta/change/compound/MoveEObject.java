/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractChange <em>Subtract Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddChange <em>Add Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject()
 * @model
 * @generated
 */
public interface MoveEObject<T extends EObject> extends ECompoundChange {
    /**
     * Returns the value of the '<em><b>Subtract Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subtract Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subtract Change</em>' containment reference.
     * @see #setSubtractChange(SubtractiveEReferenceChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_SubtractChange()
     * @model containment="true" required="true"
     * @generated
     */
    SubtractiveEReferenceChange getSubtractChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractChange <em>Subtract Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Subtract Change</em>' containment reference.
     * @see #getSubtractChange()
     * @generated
     */
    void setSubtractChange(SubtractiveEReferenceChange value);

    /**
     * Returns the value of the '<em><b>Add Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Add Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Add Change</em>' containment reference.
     * @see #setAddChange(AdditiveEReferenceChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getMoveEObject_AddChange()
     * @model containment="true" required="true"
     * @generated
     */
    AdditiveEReferenceChange<T> getAddChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddChange <em>Add Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Change</em>' containment reference.
     * @see #getAddChange()
     * @generated
     */
    void setAddChange(AdditiveEReferenceChange<T> value);

} // MoveEObject
