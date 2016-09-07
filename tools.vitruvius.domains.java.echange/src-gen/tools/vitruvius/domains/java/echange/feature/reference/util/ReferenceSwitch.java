/**
 */
package tools.vitruvius.domains.java.echange.feature.reference.util;

import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange;
import tools.vitruvius.domains.java.echange.feature.reference.*;
import tools.vitruvius.framework.change.echange.AdditiveEChange;
import tools.vitruvius.framework.change.echange.AtomicEChange;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.EObjectAddedEChange;
import tools.vitruvius.framework.change.echange.EObjectSubtractedEChange;
import tools.vitruvius.framework.change.echange.SubtractiveEChange;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;
import tools.vitruvius.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;
import tools.vitruvius.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruvius.framework.change.echange.feature.list.RemoveFromListEChange;
import tools.vitruvius.framework.change.echange.feature.list.UpdateSingleListEntryEChange;
import tools.vitruvius.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruvius.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

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
 * @see tools.vitruvius.domains.java.echange.feature.reference.ReferencePackage
 * @generated
 */
public class ReferenceSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ReferencePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceSwitch() {
		if (modelPackage == null) {
			modelPackage = ReferencePackage.eINSTANCE;
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
			case ReferencePackage.JAVA_INSERT_EREFERENCE: {
				JavaInsertEReference<?, ?> javaInsertEReference = (JavaInsertEReference<?, ?>)theEObject;
				T1 result = caseJavaInsertEReference(javaInsertEReference);
				if (result == null) result = caseInsertEReference(javaInsertEReference);
				if (result == null) result = caseJavaFeatureEChange(javaInsertEReference);
				if (result == null) result = caseInsertInListEChange(javaInsertEReference);
				if (result == null) result = caseAdditiveReferenceEChange(javaInsertEReference);
				if (result == null) result = caseUpdateSingleListEntryEChange(javaInsertEReference);
				if (result == null) result = caseEObjectAddedEChange(javaInsertEReference);
				if (result == null) result = caseUpdateReferenceEChange(javaInsertEReference);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(javaInsertEReference);
				if (result == null) result = caseAdditiveEChange(javaInsertEReference);
				if (result == null) result = caseFeatureEChange(javaInsertEReference);
				if (result == null) result = caseAtomicEChange(javaInsertEReference);
				if (result == null) result = caseEChange(javaInsertEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ReferencePackage.JAVA_REMOVE_EREFERENCE: {
				JavaRemoveEReference<?, ?> javaRemoveEReference = (JavaRemoveEReference<?, ?>)theEObject;
				T1 result = caseJavaRemoveEReference(javaRemoveEReference);
				if (result == null) result = caseRemoveEReference(javaRemoveEReference);
				if (result == null) result = caseJavaFeatureEChange(javaRemoveEReference);
				if (result == null) result = caseRemoveFromListEChange(javaRemoveEReference);
				if (result == null) result = caseSubtractiveReferenceEChange(javaRemoveEReference);
				if (result == null) result = caseUpdateSingleListEntryEChange(javaRemoveEReference);
				if (result == null) result = caseEObjectSubtractedEChange(javaRemoveEReference);
				if (result == null) result = caseUpdateReferenceEChange(javaRemoveEReference);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(javaRemoveEReference);
				if (result == null) result = caseSubtractiveEChange(javaRemoveEReference);
				if (result == null) result = caseFeatureEChange(javaRemoveEReference);
				if (result == null) result = caseAtomicEChange(javaRemoveEReference);
				if (result == null) result = caseEChange(javaRemoveEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ReferencePackage.JAVA_REPLACE_SINGLE_VALUED_EREFERENCE: {
				JavaReplaceSingleValuedEReference<?, ?> javaReplaceSingleValuedEReference = (JavaReplaceSingleValuedEReference<?, ?>)theEObject;
				T1 result = caseJavaReplaceSingleValuedEReference(javaReplaceSingleValuedEReference);
				if (result == null) result = caseReplaceSingleValuedEReference(javaReplaceSingleValuedEReference);
				if (result == null) result = caseJavaFeatureEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseUpdateSingleValuedFeatureEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseSubtractiveReferenceEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseAdditiveReferenceEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseEObjectSubtractedEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseUpdateReferenceEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseEObjectAddedEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseFeatureEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseSubtractiveEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseAdditiveEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseAtomicEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = caseEChange(javaReplaceSingleValuedEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Insert EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Insert EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseJavaInsertEReference(JavaInsertEReference<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Remove EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Remove EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseJavaRemoveEReference(JavaRemoveEReference<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Replace Single Valued EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Replace Single Valued EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseJavaReplaceSingleValuedEReference(JavaReplaceSingleValuedEReference<A, T> object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Atomic EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Atomic EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAtomicEChange(AtomicEChange object) {
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
	public <A extends EObject, F extends EStructuralFeature> T1 caseFeatureEChange(FeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Multi Valued Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Multi Valued Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Single List Entry EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Single List Entry EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateSingleListEntryEChange(UpdateSingleListEntryEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Insert In List EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Insert In List EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseInsertInListEChange(InsertInListEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseAdditiveEChange(AdditiveEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Added EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Added EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseEObjectAddedEChange(EObjectAddedEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Reference EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Reference EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject> T1 caseUpdateReferenceEChange(UpdateReferenceEChange<A> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive Reference EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive Reference EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseAdditiveReferenceEChange(AdditiveReferenceEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Insert EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Insert EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseInsertEReference(InsertEReference<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseJavaFeatureEChange(JavaFeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove From List EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove From List EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseRemoveFromListEChange(RemoveFromListEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subtractive EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtractive EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseSubtractiveEChange(SubtractiveEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Subtracted EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Subtracted EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseEObjectSubtractedEChange(EObjectSubtractedEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subtractive Reference EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtractive Reference EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseSubtractiveReferenceEChange(SubtractiveReferenceEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseRemoveEReference(RemoveEReference<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Single Valued Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Single Valued Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateSingleValuedFeatureEChange(UpdateSingleValuedFeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Replace Single Valued EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Replace Single Valued EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends EObject> T1 caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference<A, T> object) {
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

} //ReferenceSwitch
