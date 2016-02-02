/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove From EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getRemoveFromEList()
 * @model abstract="true"
 * @generated
 */
public interface RemoveFromEList extends UpdateSingleEListEntry {
    /**
     * Returns the value of the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Removed Object URI Fragment</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Removed Object URI Fragment</em>' attribute.
     * @see #setRemovedObjectURIFragment(String)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage#getRemoveFromEList_RemovedObjectURIFragment()
     * @model default="0" required="true"
     * @generated
     */
    String getRemovedObjectURIFragment();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Removed Object URI Fragment</em>' attribute.
     * @see #getRemovedObjectURIFragment()
     * @generated
     */
    void setRemovedObjectURIFragment(String value);

} // RemoveFromEList
