/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.ecore.EObject

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.Named#getName <em>Name</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.VersioningPackage#getNamed()
 * @model abstract="true"
 * @generated
 */
interface Named extends EObject {
	/** 
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getNamed_Name()
	 * @model required="true"
	 * @generated
	 */
	def String getName()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.Named#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	def void setName(String value)
// Named
}
