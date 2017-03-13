/**
 */
package uml_mockup.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import uml_mockup.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see uml_mockup.Uml_mockupPackage
 * @generated
 */
public class Uml_mockupSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Uml_mockupPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Uml_mockupSwitch() {
		if (modelPackage == null) {
			modelPackage = Uml_mockupPackage.eINSTANCE;
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
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case Uml_mockupPackage.IDENTIFIED: {
				Identified identified = (Identified)theEObject;
				T result = caseIdentified(identified);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UPACKAGE: {
				UPackage uPackage = (UPackage)theEObject;
				T result = caseUPackage(uPackage);
				if (result == null) result = caseIdentified(uPackage);
				if (result == null) result = caseUNamedElement(uPackage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UINTERFACE: {
				UInterface uInterface = (UInterface)theEObject;
				T result = caseUInterface(uInterface);
				if (result == null) result = caseIdentified(uInterface);
				if (result == null) result = caseUNamedElement(uInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UCLASS: {
				UClass uClass = (UClass)theEObject;
				T result = caseUClass(uClass);
				if (result == null) result = caseIdentified(uClass);
				if (result == null) result = caseUNamedElement(uClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UNAMED_ELEMENT: {
				UNamedElement uNamedElement = (UNamedElement)theEObject;
				T result = caseUNamedElement(uNamedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UMETHOD: {
				UMethod uMethod = (UMethod)theEObject;
				T result = caseUMethod(uMethod);
				if (result == null) result = caseIdentified(uMethod);
				if (result == null) result = caseUNamedElement(uMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Uml_mockupPackage.UATTRIBUTE: {
				UAttribute uAttribute = (UAttribute)theEObject;
				T result = caseUAttribute(uAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identified</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identified</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentified(Identified object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UPackage</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UPackage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUPackage(UPackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInterface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInterface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUInterface(UInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UClass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UClass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUClass(UClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UNamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUNamedElement(UNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UMethod</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UMethod</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUMethod(UMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUAttribute(UAttribute object) {
		return null;
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
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //Uml_mockupSwitch
