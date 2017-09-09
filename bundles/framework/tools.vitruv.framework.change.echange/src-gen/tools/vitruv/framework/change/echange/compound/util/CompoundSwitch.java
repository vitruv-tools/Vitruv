/**
 */
package tools.vitruv.framework.change.echange.compound.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.compound.*;

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
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage
 * @generated
 */
public class CompoundSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CompoundPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundSwitch() {
		if (modelPackage == null) {
			modelPackage = CompoundPackage.eINSTANCE;
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
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CompoundPackage.COMPOUND_ECHANGE: {
				CompoundEChange compoundEChange = (CompoundEChange)theEObject;
				T1 result = caseCompoundEChange(compoundEChange);
				if (result == null) result = caseEChange(compoundEChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE: {
				ExplicitUnsetEFeature<?, ?> explicitUnsetEFeature = (ExplicitUnsetEFeature<?, ?>)theEObject;
				T1 result = caseExplicitUnsetEFeature(explicitUnsetEFeature);
				if (result == null) result = caseCompoundEChange(explicitUnsetEFeature);
				if (result == null) result = caseEChange(explicitUnsetEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE: {
				ExplicitUnsetEAttribute<?, ?> explicitUnsetEAttribute = (ExplicitUnsetEAttribute<?, ?>)theEObject;
				T1 result = caseExplicitUnsetEAttribute(explicitUnsetEAttribute);
				if (result == null) result = caseCompoundSubtraction(explicitUnsetEAttribute);
				if (result == null) result = caseExplicitUnsetEFeature(explicitUnsetEAttribute);
				if (result == null) result = caseCompoundEChange(explicitUnsetEAttribute);
				if (result == null) result = caseEChange(explicitUnsetEAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE: {
				ExplicitUnsetEReference<?> explicitUnsetEReference = (ExplicitUnsetEReference<?>)theEObject;
				T1 result = caseExplicitUnsetEReference(explicitUnsetEReference);
				if (result == null) result = caseExplicitUnsetEFeature(explicitUnsetEReference);
				if (result == null) result = caseCompoundEChange(explicitUnsetEReference);
				if (result == null) result = caseEChange(explicitUnsetEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.COMPOUND_SUBTRACTION: {
				CompoundSubtraction<?, ?> compoundSubtraction = (CompoundSubtraction<?, ?>)theEObject;
				T1 result = caseCompoundSubtraction(compoundSubtraction);
				if (result == null) result = caseCompoundEChange(compoundSubtraction);
				if (result == null) result = caseEChange(compoundSubtraction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.COMPOUND_ADDITION: {
				CompoundAddition<?, ?> compoundAddition = (CompoundAddition<?, ?>)theEObject;
				T1 result = caseCompoundAddition(compoundAddition);
				if (result == null) result = caseCompoundEChange(compoundAddition);
				if (result == null) result = caseEChange(compoundAddition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseCompoundEChange(CompoundEChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Explicit Unset EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Explicit Unset EFeature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Explicit Unset EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Explicit Unset EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseExplicitUnsetEAttribute(ExplicitUnsetEAttribute<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Explicit Unset EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Explicit Unset EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject> T1 caseExplicitUnsetEReference(ExplicitUnsetEReference<A> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subtraction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtraction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object, S extends SubtractiveEChange<T>> T1 caseCompoundSubtraction(CompoundSubtraction<T, S> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Addition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Addition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object, S extends AdditiveEChange<T>> T1 caseCompoundAddition(CompoundAddition<T, S> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEChange(EChange object) {
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
	public T1 defaultCase(EObject object) {
		return null;
	}

} //CompoundSwitch
