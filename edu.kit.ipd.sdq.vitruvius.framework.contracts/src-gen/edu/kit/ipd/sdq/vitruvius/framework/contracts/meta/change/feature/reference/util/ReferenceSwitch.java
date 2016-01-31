/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleEListEntry;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage
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
            case ReferencePackage.UPDATE_EREFERENCE: {
                UpdateEReference<?> updateEReference = (UpdateEReference<?>)theEObject;
                T1 result = caseUpdateEReference(updateEReference);
                if (result == null) result = caseEFeatureChange(updateEReference);
                if (result == null) result = caseEChange(updateEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE: {
                ReplaceSingleValuedEReference<?> replaceSingleValuedEReference = (ReplaceSingleValuedEReference<?>)theEObject;
                T1 result = caseReplaceSingleValuedEReference(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateSingleValuedEFeature(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateEReference(replaceSingleValuedEReference);
                if (result == null) result = caseReplaciveReferenceChange(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateEFeature(replaceSingleValuedEReference);
                if (result == null) result = caseEFeatureChange(replaceSingleValuedEReference);
                if (result == null) result = caseSubtractiveReferenceChange(replaceSingleValuedEReference);
                if (result == null) result = caseAdditiveReferenceChange(replaceSingleValuedEReference);
                if (result == null) result = caseSubtractiveChange(replaceSingleValuedEReference);
                if (result == null) result = caseAdditiveChange(replaceSingleValuedEReference);
                if (result == null) result = caseEChange(replaceSingleValuedEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.INSERT_EREFERENCE: {
                InsertEReference<?> insertEReference = (InsertEReference<?>)theEObject;
                T1 result = caseInsertEReference(insertEReference);
                if (result == null) result = caseInsertInEList(insertEReference);
                if (result == null) result = caseUpdateEReference(insertEReference);
                if (result == null) result = caseAdditiveReferenceChange(insertEReference);
                if (result == null) result = caseUpdateSingleEListEntry(insertEReference);
                if (result == null) result = caseEFeatureChange(insertEReference);
                if (result == null) result = caseAdditiveChange(insertEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertEReference);
                if (result == null) result = caseUpdateEFeature(insertEReference);
                if (result == null) result = caseEChange(insertEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REMOVE_EREFERENCE: {
                RemoveEReference<?> removeEReference = (RemoveEReference<?>)theEObject;
                T1 result = caseRemoveEReference(removeEReference);
                if (result == null) result = caseRemoveFromEList(removeEReference);
                if (result == null) result = caseUpdateEReference(removeEReference);
                if (result == null) result = caseSubtractiveReferenceChange(removeEReference);
                if (result == null) result = caseUpdateSingleEListEntry(removeEReference);
                if (result == null) result = caseEFeatureChange(removeEReference);
                if (result == null) result = caseSubtractiveChange(removeEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeEReference);
                if (result == null) result = caseUpdateEFeature(removeEReference);
                if (result == null) result = caseEChange(removeEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES: {
                PermuteEReferenceValues<?> permuteEReferenceValues = (PermuteEReferenceValues<?>)theEObject;
                T1 result = casePermuteEReferenceValues(permuteEReferenceValues);
                if (result == null) result = casePermuteEList(permuteEReferenceValues);
                if (result == null) result = caseUpdateEReference(permuteEReferenceValues);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteEReferenceValues);
                if (result == null) result = caseEFeatureChange(permuteEReferenceValues);
                if (result == null) result = caseUpdateEFeature(permuteEReferenceValues);
                if (result == null) result = caseEChange(permuteEReferenceValues);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update EReference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseUpdateEReference(UpdateEReference<T> object) {
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
    public <T extends EObject> T1 caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference<T> object) {
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
    public <T extends EObject> T1 caseInsertEReference(InsertEReference<T> object) {
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
    public <T extends EObject> T1 caseRemoveEReference(RemoveEReference<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Permute EReference Values</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute EReference Values</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 casePermuteEReferenceValues(PermuteEReferenceValues<T> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T1 caseSubtractiveReferenceChange(SubtractiveReferenceChange object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Additive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseAdditiveReferenceChange(AdditiveReferenceChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Replacive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replacive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseReplaciveReferenceChange(ReplaciveReferenceChange<T> object) {
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

} //ReferenceSwitch
