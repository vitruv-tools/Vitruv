/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;

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
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ReplaceInEList<T extends Object> extends ECompoundChange {
    /**
     * Returns the value of the '<em><b>Remove Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remove Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Remove Change</em>' containment reference.
     * @see #setRemoveChange(RemoveFromEList)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getReplaceInEList_RemoveChange()
     * @model containment="true" required="true"
     * @generated
     */
    RemoveFromEList<T> getRemoveChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getRemoveChange <em>Remove Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Remove Change</em>' containment reference.
     * @see #getRemoveChange()
     * @generated
     */
    void setRemoveChange(RemoveFromEList<T> value);

    /**
     * Returns the value of the '<em><b>Insert Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Insert Change</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Insert Change</em>' containment reference.
     * @see #setInsertChange(InsertInEList)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getReplaceInEList_InsertChange()
     * @model containment="true" required="true"
     * @generated
     */
    InsertInEList<T> getInsertChange();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getInsertChange <em>Insert Change</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Insert Change</em>' containment reference.
     * @see #getInsertChange()
     * @generated
     */
    void setInsertChange(InsertInEList<T> value);

} // ReplaceInEList
