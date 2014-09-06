/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Permute EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList#getNewIndexForElementAt <em>New Index For Element At</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#getPermuteEList()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface PermuteEList<T extends Object> extends UpdateMultiValuedEFeature<T> {
	/**
     * Returns the value of the '<em><b>New Index For Element At</b></em>' attribute list.
     * The list contents are of type {@link java.lang.Integer}.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Index For Element At</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>New Index For Element At</em>' attribute list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#getPermuteEList_NewIndexForElementAt()
     * @model required="true"
     * @generated
     */
	EList<Integer> getNewIndexForElementAt();

} // PermuteEList
