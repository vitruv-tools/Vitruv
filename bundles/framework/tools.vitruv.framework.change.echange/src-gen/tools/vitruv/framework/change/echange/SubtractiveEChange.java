/**
 */
package tools.vitruv.framework.change.echange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which removes an existing value, like an EObject or primitive type.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getSubtractiveEChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface SubtractiveEChange<T extends Object> extends AtomicEChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Get the removed value.
	 * @return The value which will be removed.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	T getOldValue();

} // SubtractiveEChange
