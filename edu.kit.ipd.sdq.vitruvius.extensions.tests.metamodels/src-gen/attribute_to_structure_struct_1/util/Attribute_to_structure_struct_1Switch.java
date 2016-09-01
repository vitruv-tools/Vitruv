/**
 */
package attribute_to_structure_struct_1.util;

import attribute_to_structure_struct_1.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package
 * @generated
 */
public class Attribute_to_structure_struct_1Switch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Attribute_to_structure_struct_1Package modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_struct_1Switch() {
		if (modelPackage == null) {
			modelPackage = Attribute_to_structure_struct_1Package.eINSTANCE;
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
			case Attribute_to_structure_struct_1Package.IDENTIFIED: {
				Identified identified = (Identified)theEObject;
				T result = caseIdentified(identified);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Attribute_to_structure_struct_1Package.MODEL_B: {
				ModelB modelB = (ModelB)theEObject;
				T result = caseModelB(modelB);
				if (result == null) result = caseIdentified(modelB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Attribute_to_structure_struct_1Package.STRUCTURED: {
				Structured structured = (Structured)theEObject;
				T result = caseStructured(structured);
				if (result == null) result = caseIdentified(structured);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Attribute_to_structure_struct_1Package.INT_CONTAINER: {
				IntContainer intContainer = (IntContainer)theEObject;
				T result = caseIntContainer(intContainer);
				if (result == null) result = caseIdentified(intContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Attribute_to_structure_struct_1Package.STR_CONTAINER: {
				StrContainer strContainer = (StrContainer)theEObject;
				T result = caseStrContainer(strContainer);
				if (result == null) result = caseIdentified(strContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Attribute_to_structure_struct_1Package.FLOAT_CONTAINER: {
				FloatContainer floatContainer = (FloatContainer)theEObject;
				T result = caseFloatContainer(floatContainer);
				if (result == null) result = caseIdentified(floatContainer);
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
	 * Returns the result of interpreting the object as an instance of '<em>Model B</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model B</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelB(ModelB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructured(Structured object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntContainer(IntContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Str Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Str Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStrContainer(StrContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFloatContainer(FloatContainer object) {
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

} //Attribute_to_structure_struct_1Switch
