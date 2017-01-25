/**
 */
package tools.vitruv.framework.change.echange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive EChange</b></em>'.
 * <!-- end-user-doc -->
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
	 * @model kind="operation" unique="false" required="true"
	 * @generated
	 */
	T getOldValue();

} // SubtractiveEChange
