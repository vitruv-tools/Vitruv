/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove From EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#getRemoveFromEList()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface RemoveFromEList<T extends Object> extends UpdateSingleEListEntry<T> {
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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#getRemoveFromEList_RemovedObjectURIFragment()
	 * @model default="0" required="true"
	 * @generated
	 */
	String getRemovedObjectURIFragment();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Removed Object URI Fragment</em>' attribute.
	 * @see #getRemovedObjectURIFragment()
	 * @generated
	 */
	void setRemovedObjectURIFragment(String value);

	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' attribute.
	 * @see #setOldValue(Object)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#getRemoveFromEList_OldValue()
	 * @model required="true"
	 * @generated
	 */
	T getOldValue();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getOldValue <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' attribute.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(T value);

} // RemoveFromEList
