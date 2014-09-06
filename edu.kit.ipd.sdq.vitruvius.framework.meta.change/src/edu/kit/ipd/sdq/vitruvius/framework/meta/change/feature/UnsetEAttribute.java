/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature;

import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unset EAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute#getOldValue <em>Old Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getUnsetEAttribute()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface UnsetEAttribute<T extends Object> extends UnsetEFeature<EAttribute> {

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getUnsetEAttribute_OldValue()
     * @model required="true"
     * @generated
     */
	T getOldValue();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute#getOldValue <em>Old Value</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Value</em>' attribute.
     * @see #getOldValue()
     * @generated
     */
	void setOldValue(T value);
} // UnsetEAttribute
