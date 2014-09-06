/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getUnsetEReference()
 * @model abstract="true"
 * @generated
 */
public interface UnsetEReference<T extends EObject> extends UnsetEFeature<EReference> {

	/**
     * Returns the value of the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Old Value</em>' attribute.
     * @see #setOldValue(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getUnsetEReference_OldValue()
     * @model required="true"
     * @generated
     */
	T getOldValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference#getOldValue <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' attribute.
     * @see #getOldValue()
     * @generated
     */
	void setOldValue(T value);
} // UnsetEReference
