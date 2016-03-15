/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
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
                if (result == null) result = caseEAtomicChange(updateEReference);
                if (result == null) result = caseEChange(updateEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE: {
                ReplaceSingleValuedEReference<?, ?> replaceSingleValuedEReference = (ReplaceSingleValuedEReference<?, ?>)theEObject;
                T1 result = caseReplaceSingleValuedEReference(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateSingleValuedEFeature(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateEReference(replaceSingleValuedEReference);
                if (result == null) result = caseSubtractiveEReferenceChange(replaceSingleValuedEReference);
                if (result == null) result = caseAdditiveEReferenceChange(replaceSingleValuedEReference);
                if (result == null) result = caseUpdateEFeature(replaceSingleValuedEReference);
                if (result == null) result = caseEFeatureChange(replaceSingleValuedEReference);
                if (result == null) result = caseSubtractiveEChange(replaceSingleValuedEReference);
                if (result == null) result = caseAdditiveEChange(replaceSingleValuedEReference);
                if (result == null) result = caseEAtomicChange(replaceSingleValuedEReference);
                if (result == null) result = caseEChange(replaceSingleValuedEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.INSERT_EREFERENCE: {
                InsertEReference<?, ?> insertEReference = (InsertEReference<?, ?>)theEObject;
                T1 result = caseInsertEReference(insertEReference);
                if (result == null) result = caseInsertInEList(insertEReference);
                if (result == null) result = caseUpdateEReference(insertEReference);
                if (result == null) result = caseAdditiveEReferenceChange(insertEReference);
                if (result == null) result = caseUpdateSingleEListEntry(insertEReference);
                if (result == null) result = caseEFeatureChange(insertEReference);
                if (result == null) result = caseAdditiveEChange(insertEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertEReference);
                if (result == null) result = caseUpdateEFeature(insertEReference);
                if (result == null) result = caseEAtomicChange(insertEReference);
                if (result == null) result = caseEChange(insertEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REMOVE_EREFERENCE: {
                RemoveEReference<?, ?> removeEReference = (RemoveEReference<?, ?>)theEObject;
                T1 result = caseRemoveEReference(removeEReference);
                if (result == null) result = caseRemoveFromEList(removeEReference);
                if (result == null) result = caseUpdateEReference(removeEReference);
                if (result == null) result = caseSubtractiveEReferenceChange(removeEReference);
                if (result == null) result = caseUpdateSingleEListEntry(removeEReference);
                if (result == null) result = caseEFeatureChange(removeEReference);
                if (result == null) result = caseSubtractiveEChange(removeEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeEReference);
                if (result == null) result = caseUpdateEFeature(removeEReference);
                if (result == null) result = caseEAtomicChange(removeEReference);
                if (result == null) result = caseEChange(removeEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.PERMUTE_EREFERENCES: {
                PermuteEReferences<?> permuteEReferences = (PermuteEReferences<?>)theEObject;
                T1 result = casePermuteEReferences(permuteEReferences);
                if (result == null) result = casePermuteEList(permuteEReferences);
                if (result == null) result = caseUpdateEReference(permuteEReferences);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteEReferences);
                if (result == null) result = caseEFeatureChange(permuteEReferences);
                if (result == null) result = caseUpdateEFeature(permuteEReferences);
                if (result == null) result = caseEAtomicChange(permuteEReferences);
                if (result == null) result = caseEChange(permuteEReferences);
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
    public <A extends EObject> T1 caseUpdateEReference(UpdateEReference<A> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Permute EReferences</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute EReferences</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <A extends EObject> T1 casePermuteEReferences(PermuteEReferences<A> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>EAtomic Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EAtomic Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T1 caseEAtomicChange(EAtomicChange object) {
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
    public <A extends EObject, F extends EStructuralFeature> T1 caseEFeatureChange(EFeatureChange<A, F> object) {
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
    public T1 caseUpdateEFeature(UpdateEFeature object) {
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
    public T1 caseUpdateSingleValuedEFeature(UpdateSingleValuedEFeature object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Subtractive EReference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive EReference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseSubtractiveEReferenceChange(SubtractiveEReferenceChange<T> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Additive EReference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive EReference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseAdditiveEReferenceChange(AdditiveEReferenceChange<T> object) {
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
    public T1 caseUpdateMultiValuedEFeature(UpdateMultiValuedEFeature object) {
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
    public T1 caseUpdateSingleEListEntry(UpdateSingleEListEntry object) {
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
    public T1 caseInsertInEList(InsertInEList object) {
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
    public T1 caseRemoveFromEList(RemoveFromEList object) {
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
    public T1 casePermuteEList(PermuteEList object) {
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
