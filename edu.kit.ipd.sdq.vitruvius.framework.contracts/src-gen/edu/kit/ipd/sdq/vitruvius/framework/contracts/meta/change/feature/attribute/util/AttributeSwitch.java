/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.*;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleEListEntry;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage
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
            case AttributePackage.UPDATE_EATTRIBUTE: {
                UpdateEAttribute<?> updateEAttribute = (UpdateEAttribute<?>)theEObject;
                T1 result = caseUpdateEAttribute(updateEAttribute);
                if (result == null) result = caseEFeatureChange(updateEAttribute);
                if (result == null) result = caseEChange(updateEAttribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE: {
                ReplaceSingleValuedEAttribute<?> replaceSingleValuedEAttribute = (ReplaceSingleValuedEAttribute<?>)theEObject;
                T1 result = caseReplaceSingleValuedEAttribute(replaceSingleValuedEAttribute);
                if (result == null) result = caseUpdateSingleValuedEFeature(replaceSingleValuedEAttribute);
                if (result == null) result = caseUpdateEAttribute(replaceSingleValuedEAttribute);
                if (result == null) result = caseReplaciveAttributeChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseUpdateEFeature(replaceSingleValuedEAttribute);
                if (result == null) result = caseEFeatureChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseSubtractiveAttributeChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseAdditiveAttributeChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseSubtractiveChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseAdditiveChange(replaceSingleValuedEAttribute);
                if (result == null) result = caseEChange(replaceSingleValuedEAttribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AttributePackage.INSERT_EATTRIBUTE_VALUE: {
                InsertEAttributeValue<?> insertEAttributeValue = (InsertEAttributeValue<?>)theEObject;
                T1 result = caseInsertEAttributeValue(insertEAttributeValue);
                if (result == null) result = caseInsertInEList(insertEAttributeValue);
                if (result == null) result = caseUpdateEAttribute(insertEAttributeValue);
                if (result == null) result = caseAdditiveAttributeChange(insertEAttributeValue);
                if (result == null) result = caseUpdateSingleEListEntry(insertEAttributeValue);
                if (result == null) result = caseEFeatureChange(insertEAttributeValue);
                if (result == null) result = caseAdditiveChange(insertEAttributeValue);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertEAttributeValue);
                if (result == null) result = caseUpdateEFeature(insertEAttributeValue);
                if (result == null) result = caseEChange(insertEAttributeValue);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AttributePackage.REMOVE_EATTRIBUTE_VALUE: {
                RemoveEAttributeValue<?> removeEAttributeValue = (RemoveEAttributeValue<?>)theEObject;
                T1 result = caseRemoveEAttributeValue(removeEAttributeValue);
                if (result == null) result = caseRemoveFromEList(removeEAttributeValue);
                if (result == null) result = caseUpdateEAttribute(removeEAttributeValue);
                if (result == null) result = caseSubtractiveAttributeChange(removeEAttributeValue);
                if (result == null) result = caseUpdateSingleEListEntry(removeEAttributeValue);
                if (result == null) result = caseEFeatureChange(removeEAttributeValue);
                if (result == null) result = caseSubtractiveChange(removeEAttributeValue);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeEAttributeValue);
                if (result == null) result = caseUpdateEFeature(removeEAttributeValue);
                if (result == null) result = caseEChange(removeEAttributeValue);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES: {
                PermuteEAttributeValues<?> permuteEAttributeValues = (PermuteEAttributeValues<?>)theEObject;
                T1 result = casePermuteEAttributeValues(permuteEAttributeValues);
                if (result == null) result = casePermuteEList(permuteEAttributeValues);
                if (result == null) result = caseUpdateEAttribute(permuteEAttributeValues);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteEAttributeValues);
                if (result == null) result = caseEFeatureChange(permuteEAttributeValues);
                if (result == null) result = caseUpdateEFeature(permuteEAttributeValues);
                if (result == null) result = caseEChange(permuteEAttributeValues);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update EAttribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update EAttribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseUpdateEAttribute(UpdateEAttribute<T> object) {
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
    public <T extends Object> T1 caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<T> object) {
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
    public <T extends Object> T1 caseInsertEAttributeValue(InsertEAttributeValue<T> object) {
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
    public <T extends Object> T1 caseRemoveEAttributeValue(RemoveEAttributeValue<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Permute EAttribute Values</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute EAttribute Values</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 casePermuteEAttributeValues(PermuteEAttributeValues<T> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>EFeature Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EFeature Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EStructuralFeature> T1 caseEFeatureChange(EFeatureChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update EFeature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update EFeature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseUpdateEFeature(UpdateEFeature<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update Single Valued EFeature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Single Valued EFeature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseUpdateSingleValuedEFeature(UpdateSingleValuedEFeature<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseSubtractiveChange(SubtractiveChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseSubtractiveAttributeChange(SubtractiveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Additive Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseAdditiveChange(AdditiveChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Additive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseAdditiveAttributeChange(AdditiveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Replacive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replacive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseReplaciveAttributeChange(ReplaciveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update Multi Valued EFeature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Multi Valued EFeature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseUpdateMultiValuedEFeature(UpdateMultiValuedEFeature<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update Single EList Entry</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Single EList Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseUpdateSingleEListEntry(UpdateSingleEListEntry<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Insert In EList</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Insert In EList</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseInsertInEList(InsertInEList<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Remove From EList</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Remove From EList</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseRemoveFromEList(RemoveFromEList<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Permute EList</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute EList</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 casePermuteEList(PermuteEList<T> object) {
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
