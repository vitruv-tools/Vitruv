/**
 */
package tools.vitruv.framework.change.echange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive EChange</b></em>'.
 * <!-- end-user-doc -->
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
	 * @model kind="operation" unique="false" required="true"
	 * @generated
	 */
	T getNewValue();

} // AdditiveEChange
