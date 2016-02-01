/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage
 * @generated
 */
public class ListSwitch<T1> extends Switch<T1> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ListPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ListSwitch() {
        if (modelPackage == null) {
            modelPackage = ListPackage.eINSTANCE;
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
            case ListPackage.UPDATE_SINGLE_ELIST_ENTRY: {
                UpdateSingleEListEntry<?> updateSingleEListEntry = (UpdateSingleEListEntry<?>)theEObject;
                T1 result = caseUpdateSingleEListEntry(updateSingleEListEntry);
                if (result == null) result = caseUpdateMultiValuedEFeature(updateSingleEListEntry);
                if (result == null) result = caseUpdateEFeature(updateSingleEListEntry);
                if (result == null) result = caseEAtomicChange(updateSingleEListEntry);
                if (result == null) result = caseEChange(updateSingleEListEntry);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.INSERT_IN_ELIST: {
                InsertInEList<?> insertInEList = (InsertInEList<?>)theEObject;
                T1 result = caseInsertInEList(insertInEList);
                if (result == null) result = caseUpdateSingleEListEntry(insertInEList);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertInEList);
                if (result == null) result = caseUpdateEFeature(insertInEList);
                if (result == null) result = caseEAtomicChange(insertInEList);
                if (result == null) result = caseEChange(insertInEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.REMOVE_FROM_ELIST: {
                RemoveFromEList<?> removeFromEList = (RemoveFromEList<?>)theEObject;
                T1 result = caseRemoveFromEList(removeFromEList);
                if (result == null) result = caseUpdateSingleEListEntry(removeFromEList);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeFromEList);
                if (result == null) result = caseUpdateEFeature(removeFromEList);
                if (result == null) result = caseEAtomicChange(removeFromEList);
                if (result == null) result = caseEChange(removeFromEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.PERMUTE_ELIST: {
                PermuteEList<?> permuteEList = (PermuteEList<?>)theEObject;
                T1 result = casePermuteEList(permuteEList);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteEList);
                if (result == null) result = caseUpdateEFeature(permuteEList);
                if (result == null) result = caseEAtomicChange(permuteEList);
                if (result == null) result = caseEChange(permuteEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
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

} //ListSwitch
