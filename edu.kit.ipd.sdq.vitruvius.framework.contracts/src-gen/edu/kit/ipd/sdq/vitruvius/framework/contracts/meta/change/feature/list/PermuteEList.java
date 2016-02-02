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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getNewIndexForElementAt <em>New Index For Element At</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getPermuteEList()
 * @model abstract="true"
 * @generated
 */
public interface PermuteEList extends UpdateMultiValuedEFeature {
    /**
     * Returns the value of the '<em><b>New Index For Element At</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Index For Element At</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>New Index For Element At</em>' attribute.
     * @see #setNewIndexForElementAt(int)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getPermuteEList_NewIndexForElementAt()
     * @model required="true"
     * @generated
     */
    int getNewIndexForElementAt();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList#getNewIndexForElementAt <em>New Index For Element At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Index For Element At</em>' attribute.
     * @see #getNewIndexForElementAt()
     * @generated
     */
    void setNewIndexForElementAt(int value);

} // PermuteEList
