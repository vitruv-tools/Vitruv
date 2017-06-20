/** 
 */
package tools.vitruv.framework.versioning.author.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.util.Switch
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature
import tools.vitruv.framework.versioning.author.Signed

/** 
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see AuthorPackage
 * @generated
 */
class AuthorSwitch<T> extends Switch<T> {
	/** 
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AuthorPackage modelPackage

	/** 
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = AuthorPackage::eINSTANCE
		}
	}

	/** 
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	override protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage === modelPackage
	}

	/** 
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	override protected T doSwitch(int classifierID, EObject theEObject) {

		switch (classifierID) {
			case AuthorPackage::AUTHOR: {
				var Author author = (theEObject as Author)
				var T result = caseAuthor(author)
				if (result === null) result = caseNamed(author)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case AuthorPackage::SIGNATURE: {
				var Signature signature = (theEObject as Signature)
				var T result = caseSignature(signature)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case AuthorPackage::SIGNED: {
				var Signed signed = (theEObject as Signed)
				var T result = caseSigned(signed)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			default: {
				return defaultCase(theEObject)
			}
		}
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Author</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Author</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseAuthor(Author object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Signature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseSignature(Signature object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Signed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseSigned(Signed object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>Named</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseNamed(Named object) {
		return null
	}

	/** 
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	override T defaultCase(EObject object) {
		return null
	} // AuthorSwitch
}
