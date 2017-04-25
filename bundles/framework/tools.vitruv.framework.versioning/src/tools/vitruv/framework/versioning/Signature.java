/**
 */
package tools.vitruv.framework.versioning;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Signature#getSigner <em>Signer</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.VersioningPackage#getSignature()
 * @model
 * @generated
 */
public interface Signature extends EObject {
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
	 * @see tools.vitruv.framework.versioning.VersioningPackage#getSignature_Signer()
	 * @model required="true"
	 * @generated
	 */
	Author getSigner();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.Signature#getSigner <em>Signer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signer</em>' reference.
	 * @see #getSigner()
	 * @generated
	 */
	void setSigner(Author value);

} // Signature
