/**
 */
package tools.vitruv.framework.change.echange.feature.list;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Permute List EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.list.PermuteListEChange#getNewIndicesForElementsAtOldIndices <em>New Indices For Elements At Old Indices</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getPermuteListEChange()
 * @model abstract="true"
 * @generated
 */
public interface PermuteListEChange<A extends EObject, F extends EStructuralFeature> extends UpdateMultiValuedFeatureEChange<A, F> {
    /**
	 * Returns the value of the '<em><b>New Indices For Elements At Old Indices</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Indices For Elements At Old Indices</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>New Indices For Elements At Old Indices</em>' attribute list.
	 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getPermuteListEChange_NewIndicesForElementsAtOldIndices()
	 * @model required="true"
	 * @generated
	 */
    EList<Integer> getNewIndicesForElementsAtOldIndices();

} // PermuteListEChange
