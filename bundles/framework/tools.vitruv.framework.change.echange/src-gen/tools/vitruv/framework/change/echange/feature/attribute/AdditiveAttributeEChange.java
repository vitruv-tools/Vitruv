/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AdditiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive Attribute EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which inserts a new value into an attribute.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getAdditiveAttributeEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.attribute.EObj" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface AdditiveAttributeEChange<A extends EObject, T extends Object> extends UpdateAttributeEChange<A>, AdditiveEChange<T> {
	/**
	 * Returns the value of the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The value which will be inserted.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>New Value</em>' attribute.
	 * @see #setNewValue(Object)
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getAdditiveAttributeEChange_NewValue()
	 * @model unique="false" required="true"
	 * @generated
	 */
	T getNewValue();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange#getNewValue <em>New Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' attribute.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(T value);

} // AdditiveAttributeEChange
