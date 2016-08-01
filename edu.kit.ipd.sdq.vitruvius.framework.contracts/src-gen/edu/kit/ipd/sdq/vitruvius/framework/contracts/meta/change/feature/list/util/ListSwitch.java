/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange;

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
public class ListSwitch<T> extends Switch<T> {
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
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE: {
                UpdateSingleListEntryEChange updateSingleListEntryEChange = (UpdateSingleListEntryEChange)theEObject;
                T result = caseUpdateSingleListEntryEChange(updateSingleListEntryEChange);
                if (result == null) result = caseUpdateMultiValuedFeatureEChange(updateSingleListEntryEChange);
                if (result == null) result = caseAtomicEChange(updateSingleListEntryEChange);
                if (result == null) result = caseEChange(updateSingleListEntryEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.INSERT_IN_LIST_ECHANGE: {
                InsertInListEChange insertInListEChange = (InsertInListEChange)theEObject;
                T result = caseInsertInListEChange(insertInListEChange);
                if (result == null) result = caseUpdateSingleListEntryEChange(insertInListEChange);
                if (result == null) result = caseUpdateMultiValuedFeatureEChange(insertInListEChange);
                if (result == null) result = caseAtomicEChange(insertInListEChange);
                if (result == null) result = caseEChange(insertInListEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.REMOVE_FROM_LIST_ECHANGE: {
                RemoveFromListEChange removeFromListEChange = (RemoveFromListEChange)theEObject;
                T result = caseRemoveFromListEChange(removeFromListEChange);
                if (result == null) result = caseUpdateSingleListEntryEChange(removeFromListEChange);
                if (result == null) result = caseUpdateMultiValuedFeatureEChange(removeFromListEChange);
                if (result == null) result = caseAtomicEChange(removeFromListEChange);
                if (result == null) result = caseEChange(removeFromListEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ListPackage.PERMUTE_LIST_ECHANGE: {
                PermuteListEChange permuteListEChange = (PermuteListEChange)theEObject;
                T result = casePermuteListEChange(permuteListEChange);
                if (result == null) result = caseUpdateMultiValuedFeatureEChange(permuteListEChange);
                if (result == null) result = caseAtomicEChange(permuteListEChange);
                if (result == null) result = caseEChange(permuteListEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
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
    public T caseUpdateSingleListEntryEChange(UpdateSingleListEntryEChange object) {
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
    public T caseInsertInListEChange(InsertInListEChange object) {
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
    public T caseRemoveFromListEChange(RemoveFromListEChange object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Permute List EChange</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute List EChange</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePermuteListEChange(PermuteListEChange object) {
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
    public T caseEChange(EChange object) {
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
    public T caseAtomicEChange(AtomicEChange object) {
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
    public T caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
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

} //ListSwitch
