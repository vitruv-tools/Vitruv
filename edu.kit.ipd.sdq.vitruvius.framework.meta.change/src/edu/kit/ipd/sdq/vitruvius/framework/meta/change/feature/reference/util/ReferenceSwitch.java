/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateSingleValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage
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
     * @parameter ePackage the package in question.
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
            case ReferencePackage.UPDATE_NON_CONTAINMENT_EREFERENCE: {
                UpdateNonContainmentEReference<?> updateNonContainmentEReference = (UpdateNonContainmentEReference<?>)theEObject;
                T1 result = caseUpdateNonContainmentEReference(updateNonContainmentEReference);
                if (result == null) result = caseUpdateEReference(updateNonContainmentEReference);
                if (result == null) result = caseEFeatureChange(updateNonContainmentEReference);
                if (result == null) result = caseEChange(updateNonContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE: {
                UpdateSingleValuedNonContainmentEReference<?> updateSingleValuedNonContainmentEReference = (UpdateSingleValuedNonContainmentEReference<?>)theEObject;
                T1 result = caseUpdateSingleValuedNonContainmentEReference(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseUpdateSingleValuedEFeature(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseUpdateNonContainmentEReference(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseUpdateEFeature(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseUpdateEReference(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseEFeatureChange(updateSingleValuedNonContainmentEReference);
                if (result == null) result = caseEChange(updateSingleValuedNonContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.INSERT_NON_CONTAINMENT_EREFERENCE: {
                InsertNonContainmentEReference<?> insertNonContainmentEReference = (InsertNonContainmentEReference<?>)theEObject;
                T1 result = caseInsertNonContainmentEReference(insertNonContainmentEReference);
                if (result == null) result = caseInsertInEList(insertNonContainmentEReference);
                if (result == null) result = caseUpdateNonContainmentEReference(insertNonContainmentEReference);
                if (result == null) result = caseUpdateSingleEListEntry(insertNonContainmentEReference);
                if (result == null) result = caseUpdateEReference(insertNonContainmentEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertNonContainmentEReference);
                if (result == null) result = caseEFeatureChange(insertNonContainmentEReference);
                if (result == null) result = caseUpdateEFeature(insertNonContainmentEReference);
                if (result == null) result = caseEChange(insertNonContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REPLACE_NON_CONTAINMENT_EREFERENCE: {
                ReplaceNonContainmentEReference<?> replaceNonContainmentEReference = (ReplaceNonContainmentEReference<?>)theEObject;
                T1 result = caseReplaceNonContainmentEReference(replaceNonContainmentEReference);
                if (result == null) result = caseReplaceInEList(replaceNonContainmentEReference);
                if (result == null) result = caseUpdateNonContainmentEReference(replaceNonContainmentEReference);
                if (result == null) result = caseUpdateSingleEListEntry(replaceNonContainmentEReference);
                if (result == null) result = caseUpdateEReference(replaceNonContainmentEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(replaceNonContainmentEReference);
                if (result == null) result = caseEFeatureChange(replaceNonContainmentEReference);
                if (result == null) result = caseUpdateEFeature(replaceNonContainmentEReference);
                if (result == null) result = caseEChange(replaceNonContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE: {
                RemoveNonContainmentEReference<?> removeNonContainmentEReference = (RemoveNonContainmentEReference<?>)theEObject;
                T1 result = caseRemoveNonContainmentEReference(removeNonContainmentEReference);
                if (result == null) result = caseRemoveFromEList(removeNonContainmentEReference);
                if (result == null) result = caseUpdateNonContainmentEReference(removeNonContainmentEReference);
                if (result == null) result = caseUpdateSingleEListEntry(removeNonContainmentEReference);
                if (result == null) result = caseUpdateEReference(removeNonContainmentEReference);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeNonContainmentEReference);
                if (result == null) result = caseEFeatureChange(removeNonContainmentEReference);
                if (result == null) result = caseUpdateEFeature(removeNonContainmentEReference);
                if (result == null) result = caseEChange(removeNonContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ReferencePackage.PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES: {
                PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues = (PermuteNonContainmentEReferenceValues<?>)theEObject;
                T1 result = casePermuteNonContainmentEReferenceValues(permuteNonContainmentEReferenceValues);
                if (result == null) result = casePermuteEList(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseUpdateNonContainmentEReference(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseUpdateEReference(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseUpdateEFeature(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseEFeatureChange(permuteNonContainmentEReferenceValues);
                if (result == null) result = caseEChange(permuteNonContainmentEReferenceValues);
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
     * Returns the result of interpreting the object as an instance of '<em>Update Non Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Non Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseUpdateNonContainmentEReference(UpdateNonContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Update Single Valued Non Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Single Valued Non Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseUpdateSingleValuedNonContainmentEReference(UpdateSingleValuedNonContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Insert Non Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Insert Non Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseInsertNonContainmentEReference(InsertNonContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Replace Non Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace Non Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseReplaceNonContainmentEReference(ReplaceNonContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Remove Non Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Remove Non Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseRemoveNonContainmentEReference(RemoveNonContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Permute Non Containment EReference Values</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute Non Containment EReference Values</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 casePermuteNonContainmentEReferenceValues(PermuteNonContainmentEReferenceValues<T> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Replace In EList</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace In EList</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends Object> T1 caseReplaceInEList(ReplaceInEList<T> object) {
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
