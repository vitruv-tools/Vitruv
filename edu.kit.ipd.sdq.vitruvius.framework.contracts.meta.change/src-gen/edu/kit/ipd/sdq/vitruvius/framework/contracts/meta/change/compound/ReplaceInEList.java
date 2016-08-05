/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInListEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromListEChange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace In EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getInsertChange <em>Insert Change</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getReplaceInEList()
 * @model
 * @generated
 */
public interface ReplaceInEList<A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromListEChange<A, F> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F> & FeatureEChange<A, F> & AdditiveEChange<T>> extends CompoundEChange {
    /**
     * Returns the value of the '<em><b>Remove Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remove Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Remove Change</em>' containment reference.
     * @see #setRemoveChange(RemoveFromListEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getReplaceInEList_RemoveChange()
     * @model containment="true" required="true"
     * @generated
     */
    R getRemoveChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getRemoveChange <em>Remove Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Remove Change</em>' containment reference.
     * @see #getRemoveChange()
     * @generated
     */
    void setRemoveChange(R value);

    /**
     * Returns the value of the '<em><b>Insert Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Insert Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Insert Change</em>' containment reference.
     * @see #setInsertChange(InsertInListEChange)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getReplaceInEList_InsertChange()
     * @model containment="true" required="true"
     * @generated
     */
    I getInsertChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getInsertChange <em>Insert Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Insert Change</em>' containment reference.
     * @see #getInsertChange()
     * @generated
     */
    void setInsertChange(I value);

} // ReplaceInEList
