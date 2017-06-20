/** 
 */
package tools.vitruv.framework.versioning.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.util.Switch
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.Root
import tools.vitruv.framework.versioning.VersioningPackage

/** 
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see VersioningPackage
 * @generated
 */
class VersioningSwitch<T> extends Switch<T> {
	/** 
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static VersioningPackage modelPackage

	/** 
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = VersioningPackage::eINSTANCE
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
			case VersioningPackage::NAMED: {
				var Named named = (theEObject as Named)
				var T result = caseNamed(named)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			case VersioningPackage::ROOT: {
				var Root root = (theEObject as Root)
				var T result = caseRoot(root)
				if (result === null) result = defaultCase(theEObject)
				return result
			}
			default: {
				return defaultCase(theEObject)
			}
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	def T caseRoot(Root object) {
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
	} // VersioningSwitch
}
