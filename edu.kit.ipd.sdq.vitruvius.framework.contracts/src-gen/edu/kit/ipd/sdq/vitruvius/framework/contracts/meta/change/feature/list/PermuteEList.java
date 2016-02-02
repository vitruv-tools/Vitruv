/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Permute EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getOldIndex <em>Old Index</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getNewIndex <em>New Index</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getPermuteEList()
 * @model abstract="true"
 * @generated
 */
public interface PermuteEList extends UpdateMultiValuedEFeature {
    /**
     * Returns the value of the '<em><b>Old Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old Index</em>' attribute.
     * @see #setOldIndex(int)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getPermuteEList_OldIndex()
     * @model required="true"
     * @generated
     */
    int getOldIndex();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getOldIndex <em>Old Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Index</em>' attribute.
     * @see #getOldIndex()
     * @generated
     */
    void setOldIndex(int value);

    /**
     * Returns the value of the '<em><b>New Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>New Index</em>' attribute.
     * @see #setNewIndex(int)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getPermuteEList_NewIndex()
     * @model required="true"
     * @generated
     */
    int getNewIndex();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getNewIndex <em>New Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Index</em>' attribute.
     * @see #getNewIndex()
     * @generated
     */
    void setNewIndex(int value);

} // PermuteEList
