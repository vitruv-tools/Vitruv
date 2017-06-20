/** 
 */
package tools.vitruv.framework.versioning.author

import org.eclipse.emf.ecore.EObject

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.author.Signature#getSigner <em>Signer</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getSignature()
 * @model
 * @generated
 */
interface Signature extends EObject {
	/** 
	 * Returns the value of the '<em><b>Signer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signer</em>' reference.
	 * @see #setSigner(Author)
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#getSignature_Signer()
	 * @model required="true"
	 * @generated
	 */
	def Author getSigner()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.author.Signature#getSigner <em>Signer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signer</em>' reference.
	 * @see #getSigner()
	 * @generated
	 */
	def void setSigner(Author value)
// Signature
}
