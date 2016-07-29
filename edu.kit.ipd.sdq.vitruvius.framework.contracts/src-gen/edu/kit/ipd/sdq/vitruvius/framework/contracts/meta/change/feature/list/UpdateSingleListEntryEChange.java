/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update Single List Entry EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getUpdateSingleListEntryEChange()
 * @model abstract="true"
 * @generated
 */
public interface UpdateSingleListEntryEChange extends UpdateMultiValuedFeatureEChange {
    /**
     * Returns the value of the '<em><b>Index</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Index</em>' attribute.
     * @see #setIndex(int)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getUpdateSingleListEntryEChange_Index()
     * @model default="0" required="true"
     * @generated
     */
    int getIndex();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange#getIndex <em>Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Index</em>' attribute.
     * @see #getIndex()
     * @generated
     */
    void setIndex(int value);

} // UpdateSingleListEntryEChange
