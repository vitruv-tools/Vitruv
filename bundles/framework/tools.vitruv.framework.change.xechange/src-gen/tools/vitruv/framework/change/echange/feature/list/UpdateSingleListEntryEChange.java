/**
 */
package tools.vitruv.framework.change.echange.feature.list;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update Single List Entry EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getUpdateSingleListEntryEChange()
 * @model abstract="true"
 * @generated
 */
public interface UpdateSingleListEntryEChange<A extends EObject, F extends EStructuralFeature> extends UpdateMultiValuedFeatureEChange<A, F> {
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
	 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getUpdateSingleListEntryEChange_Index()
	 * @model default="0" unique="false" required="true"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // UpdateSingleListEntryEChange
