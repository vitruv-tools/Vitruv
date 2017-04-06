/**
 */
package tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage;

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
 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage
 * @generated
 */
public class InputTypesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InputTypesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputTypesSwitch() {
		if (modelPackage == null) {
			modelPackage = InputTypesPackage.eINSTANCE;
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
			case InputTypesPackage.STRING: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String string = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String)theEObject;
				T result = caseString(string);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.INTEGER: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer integer = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer)theEObject;
				T result = caseInteger(integer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.SHORT: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short short_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short)theEObject;
				T result = caseShort(short_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.BOOLEAN: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean boolean_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean)theEObject;
				T result = caseBoolean(boolean_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.LONG: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long long_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long)theEObject;
				T result = caseLong(long_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.CHARACTER: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character character = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character)theEObject;
				T result = caseCharacter(character);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.BYTE: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte byte_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte)theEObject;
				T result = caseByte(byte_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.FLOAT: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float float_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float)theEObject;
				T result = caseFloat(float_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InputTypesPackage.DOUBLE: {
				tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double double_ = (tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double)theEObject;
				T result = caseDouble(double_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseString(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInteger(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Short</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Short</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShort(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolean(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Long</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Long</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLong(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Character</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Character</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharacter(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Byte</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Byte</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseByte(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFloat(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDouble(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double object) {
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

} //InputTypesSwitch
