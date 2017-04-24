/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.SubtractiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive Attribute EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which removes a value from an attribute.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange#getOldValue <em>Old Value</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getSubtractiveAttributeEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.attribute.EObj" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface SubtractiveAttributeEChange<A extends EObject, T extends Object> extends UpdateAttributeEChange<A>, SubtractiveEChange<T> {
	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The value which will be removed.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Old Value</em>' attribute.
	 * @see #setOldValue(Object)
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getSubtractiveAttributeEChange_OldValue()
	 * @model unique="false" required="true"
	 * @generated
	 */
	T getOldValue();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange#getOldValue <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' attribute.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(T value);

} // SubtractiveAttributeEChange
