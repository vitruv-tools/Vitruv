/**
 */
package tools.vitruv.framework.change.echange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which adds a new value, like an EObject or primitive type.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getAdditiveEChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface AdditiveEChange<T extends Object> extends AtomicEChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Get the added value.
	 * @return The newly added value.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	T getNewValue();

} // AdditiveEChange
