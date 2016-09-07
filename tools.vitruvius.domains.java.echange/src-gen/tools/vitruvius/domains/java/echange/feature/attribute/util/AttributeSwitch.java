/**
 */
package tools.vitruvius.domains.java.echange.feature.attribute.util;

import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange;
import tools.vitruvius.domains.java.echange.feature.attribute.*;
import tools.vitruvius.framework.change.echange.AdditiveEChange;
import tools.vitruvius.framework.change.echange.AtomicEChange;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.SubtractiveEChange;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;
import tools.vitruvius.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;
import tools.vitruvius.framework.change.echange.feature.attribute.AdditiveAttributeEChange;
import tools.vitruvius.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruvius.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;
import tools.vitruvius.framework.change.echange.feature.attribute.UpdateAttributeEChange;
import tools.vitruvius.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruvius.framework.change.echange.feature.list.RemoveFromListEChange;
import tools.vitruvius.framework.change.echange.feature.list.UpdateSingleListEntryEChange;

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
 * @see tools.vitruvius.domains.java.echange.feature.attribute.AttributePackage
 * @generated
 */
public class AttributeSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AttributePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeSwitch() {
		if (modelPackage == null) {
			modelPackage = AttributePackage.eINSTANCE;
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
			case AttributePackage.JAVA_INSERT_EATTRIBUTE_VALUE: {
				JavaInsertEAttributeValue<?, ?> javaInsertEAttributeValue = (JavaInsertEAttributeValue<?, ?>)theEObject;
				T1 result = caseJavaInsertEAttributeValue(javaInsertEAttributeValue);
				if (result == null) result = caseInsertEAttributeValue(javaInsertEAttributeValue);
				if (result == null) result = caseJavaFeatureEChange(javaInsertEAttributeValue);
				if (result == null) result = caseInsertInListEChange(javaInsertEAttributeValue);
				if (result == null) result = caseAdditiveAttributeEChange(javaInsertEAttributeValue);
				if (result == null) result = caseUpdateSingleListEntryEChange(javaInsertEAttributeValue);
				if (result == null) result = caseAdditiveEChange(javaInsertEAttributeValue);
				if (result == null) result = caseUpdateAttributeEChange(javaInsertEAttributeValue);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(javaInsertEAttributeValue);
				if (result == null) result = caseFeatureEChange(javaInsertEAttributeValue);
				if (result == null) result = caseAtomicEChange(javaInsertEAttributeValue);
				if (result == null) result = caseEChange(javaInsertEAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.JAVA_REMOVE_EATTRIBUTE_VALUE: {
				JavaRemoveEAttributeValue<?, ?> javaRemoveEAttributeValue = (JavaRemoveEAttributeValue<?, ?>)theEObject;
				T1 result = caseJavaRemoveEAttributeValue(javaRemoveEAttributeValue);
				if (result == null) result = caseRemoveEAttributeValue(javaRemoveEAttributeValue);
				if (result == null) result = caseJavaFeatureEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseRemoveFromListEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseSubtractiveAttributeEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseUpdateSingleListEntryEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseSubtractiveEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseUpdateAttributeEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseFeatureEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseAtomicEChange(javaRemoveEAttributeValue);
				if (result == null) result = caseEChange(javaRemoveEAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.JAVA_REPLACE_SINGLE_VALUED_EATTRIBUTE: {
				JavaReplaceSingleValuedEAttribute<?, ?> javaReplaceSingleValuedEAttribute = (JavaReplaceSingleValuedEAttribute<?, ?>)theEObject;
				T1 result = caseJavaReplaceSingleValuedEAttribute(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseReplaceSingleValuedEAttribute(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseJavaFeatureEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseUpdateSingleValuedFeatureEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseSubtractiveAttributeEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseAdditiveAttributeEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseSubtractiveEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseUpdateAttributeEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseAdditiveEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseFeatureEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseAtomicEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = caseEChange(javaReplaceSingleValuedEAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Insert EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Insert EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseJavaInsertEAttributeValue(JavaInsertEAttributeValue<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Remove EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Remove EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseJavaRemoveEAttributeValue(JavaRemoveEAttributeValue<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Replace Single Valued EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Replace Single Valued EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseJavaReplaceSingleValuedEAttribute(JavaReplaceSingleValuedEAttribute<A, T> object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Update Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject> T1 caseUpdateAttributeEChange(UpdateAttributeEChange<A> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseAdditiveAttributeEChange(AdditiveAttributeEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Insert EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Insert EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseInsertEAttributeValue(InsertEAttributeValue<A, T> object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Subtractive Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtractive Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseSubtractiveAttributeEChange(SubtractiveAttributeEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseRemoveEAttributeValue(RemoveEAttributeValue<A, T> object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Replace Single Valued EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Replace Single Valued EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<A, T> object) {
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

} //AttributeSwitch
