/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.util;

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

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.*;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage
 * @generated
 */
public class ContainmentSwitch<T1> extends Switch<T1> {
	/**
     * The cached model package
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static ContainmentPackage modelPackage;

	/**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ContainmentSwitch() {
        if (modelPackage == null) {
            modelPackage = ContainmentPackage.eINSTANCE;
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
            case ContainmentPackage.UPDATE_CONTAINMENT_EREFERENCE: {
                UpdateContainmentEReference<?> updateContainmentEReference = (UpdateContainmentEReference<?>)theEObject;
                T1 result = caseUpdateContainmentEReference(updateContainmentEReference);
                if (result == null) result = caseUpdateEReference(updateContainmentEReference);
                if (result == null) result = caseEFeatureChange(updateContainmentEReference);
                if (result == null) result = caseEChange(updateContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE: {
                UpdateSingleValuedContainmentEReference<?> updateSingleValuedContainmentEReference = (UpdateSingleValuedContainmentEReference<?>)theEObject;
                T1 result = caseUpdateSingleValuedContainmentEReference(updateSingleValuedContainmentEReference);
                if (result == null) result = caseUpdateSingleValuedEFeature(updateSingleValuedContainmentEReference);
                if (result == null) result = caseUpdateContainmentEReference(updateSingleValuedContainmentEReference);
                if (result == null) result = caseUpdateEFeature(updateSingleValuedContainmentEReference);
                if (result == null) result = caseUpdateEReference(updateSingleValuedContainmentEReference);
                if (result == null) result = caseEFeatureChange(updateSingleValuedContainmentEReference);
                if (result == null) result = caseEChange(updateSingleValuedContainmentEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.CREATE_NON_ROOT_EOBJECT_SINGLE: {
                CreateNonRootEObjectSingle<?> createNonRootEObjectSingle = (CreateNonRootEObjectSingle<?>)theEObject;
                T1 result = caseCreateNonRootEObjectSingle(createNonRootEObjectSingle);
                if (result == null) result = caseCreateEObject(createNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedContainmentEReference(createNonRootEObjectSingle);
                if (result == null) result = caseEObjectChange(createNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedEFeature(createNonRootEObjectSingle);
                if (result == null) result = caseUpdateContainmentEReference(createNonRootEObjectSingle);
                if (result == null) result = caseUpdateEFeature(createNonRootEObjectSingle);
                if (result == null) result = caseUpdateEReference(createNonRootEObjectSingle);
                if (result == null) result = caseEFeatureChange(createNonRootEObjectSingle);
                if (result == null) result = caseEChange(createNonRootEObjectSingle);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_SINGLE: {
                ReplaceNonRootEObjectSingle<?> replaceNonRootEObjectSingle = (ReplaceNonRootEObjectSingle<?>)theEObject;
                T1 result = caseReplaceNonRootEObjectSingle(replaceNonRootEObjectSingle);
                if (result == null) result = caseReplaceEObject(replaceNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedContainmentEReference(replaceNonRootEObjectSingle);
                if (result == null) result = caseEObjectChange(replaceNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedEFeature(replaceNonRootEObjectSingle);
                if (result == null) result = caseUpdateContainmentEReference(replaceNonRootEObjectSingle);
                if (result == null) result = caseUpdateEFeature(replaceNonRootEObjectSingle);
                if (result == null) result = caseUpdateEReference(replaceNonRootEObjectSingle);
                if (result == null) result = caseEFeatureChange(replaceNonRootEObjectSingle);
                if (result == null) result = caseEChange(replaceNonRootEObjectSingle);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE: {
                DeleteNonRootEObjectSingle<?> deleteNonRootEObjectSingle = (DeleteNonRootEObjectSingle<?>)theEObject;
                T1 result = caseDeleteNonRootEObjectSingle(deleteNonRootEObjectSingle);
                if (result == null) result = caseDeleteEObject(deleteNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedContainmentEReference(deleteNonRootEObjectSingle);
                if (result == null) result = caseEObjectChange(deleteNonRootEObjectSingle);
                if (result == null) result = caseUpdateSingleValuedEFeature(deleteNonRootEObjectSingle);
                if (result == null) result = caseUpdateContainmentEReference(deleteNonRootEObjectSingle);
                if (result == null) result = caseUpdateEFeature(deleteNonRootEObjectSingle);
                if (result == null) result = caseUpdateEReference(deleteNonRootEObjectSingle);
                if (result == null) result = caseEFeatureChange(deleteNonRootEObjectSingle);
                if (result == null) result = caseEChange(deleteNonRootEObjectSingle);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.CREATE_NON_ROOT_EOBJECT_IN_LIST: {
                CreateNonRootEObjectInList<?> createNonRootEObjectInList = (CreateNonRootEObjectInList<?>)theEObject;
                T1 result = caseCreateNonRootEObjectInList(createNonRootEObjectInList);
                if (result == null) result = caseCreateEObject(createNonRootEObjectInList);
                if (result == null) result = caseInsertInEList(createNonRootEObjectInList);
                if (result == null) result = caseUpdateContainmentEReference(createNonRootEObjectInList);
                if (result == null) result = caseEObjectChange(createNonRootEObjectInList);
                if (result == null) result = caseUpdateSingleEListEntry(createNonRootEObjectInList);
                if (result == null) result = caseUpdateEReference(createNonRootEObjectInList);
                if (result == null) result = caseUpdateMultiValuedEFeature(createNonRootEObjectInList);
                if (result == null) result = caseEFeatureChange(createNonRootEObjectInList);
                if (result == null) result = caseEChange(createNonRootEObjectInList);
                if (result == null) result = caseUpdateEFeature(createNonRootEObjectInList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST: {
                ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList = (ReplaceNonRootEObjectInList<?>)theEObject;
                T1 result = caseReplaceNonRootEObjectInList(replaceNonRootEObjectInList);
                if (result == null) result = caseReplaceEObject(replaceNonRootEObjectInList);
                if (result == null) result = caseReplaceInEList(replaceNonRootEObjectInList);
                if (result == null) result = caseUpdateContainmentEReference(replaceNonRootEObjectInList);
                if (result == null) result = caseEObjectChange(replaceNonRootEObjectInList);
                if (result == null) result = caseUpdateSingleEListEntry(replaceNonRootEObjectInList);
                if (result == null) result = caseUpdateEReference(replaceNonRootEObjectInList);
                if (result == null) result = caseUpdateMultiValuedEFeature(replaceNonRootEObjectInList);
                if (result == null) result = caseEFeatureChange(replaceNonRootEObjectInList);
                if (result == null) result = caseEChange(replaceNonRootEObjectInList);
                if (result == null) result = caseUpdateEFeature(replaceNonRootEObjectInList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST: {
                DeleteNonRootEObjectInList<?> deleteNonRootEObjectInList = (DeleteNonRootEObjectInList<?>)theEObject;
                T1 result = caseDeleteNonRootEObjectInList(deleteNonRootEObjectInList);
                if (result == null) result = caseDeleteEObject(deleteNonRootEObjectInList);
                if (result == null) result = caseRemoveFromEList(deleteNonRootEObjectInList);
                if (result == null) result = caseUpdateContainmentEReference(deleteNonRootEObjectInList);
                if (result == null) result = caseEObjectChange(deleteNonRootEObjectInList);
                if (result == null) result = caseUpdateSingleEListEntry(deleteNonRootEObjectInList);
                if (result == null) result = caseUpdateEReference(deleteNonRootEObjectInList);
                if (result == null) result = caseUpdateMultiValuedEFeature(deleteNonRootEObjectInList);
                if (result == null) result = caseEFeatureChange(deleteNonRootEObjectInList);
                if (result == null) result = caseEChange(deleteNonRootEObjectInList);
                if (result == null) result = caseUpdateEFeature(deleteNonRootEObjectInList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.PERMUTE_CONTAINMENT_EREFERENCE_VALUES: {
                PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues = (PermuteContainmentEReferenceValues<?>)theEObject;
                T1 result = casePermuteContainmentEReferenceValues(permuteContainmentEReferenceValues);
                if (result == null) result = casePermuteEList(permuteContainmentEReferenceValues);
                if (result == null) result = caseUpdateContainmentEReference(permuteContainmentEReferenceValues);
                if (result == null) result = caseUpdateMultiValuedEFeature(permuteContainmentEReferenceValues);
                if (result == null) result = caseUpdateEReference(permuteContainmentEReferenceValues);
                if (result == null) result = caseUpdateEFeature(permuteContainmentEReferenceValues);
                if (result == null) result = caseEFeatureChange(permuteContainmentEReferenceValues);
                if (result == null) result = caseEChange(permuteContainmentEReferenceValues);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST: {
                InsertNonRootEObjectInContainmentList<?> insertNonRootEObjectInContainmentList = (InsertNonRootEObjectInContainmentList<?>)theEObject;
                T1 result = caseInsertNonRootEObjectInContainmentList(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseInsertInEList(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseUpdateContainmentEReference(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseUpdateSingleEListEntry(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseUpdateEReference(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseUpdateMultiValuedEFeature(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseEFeatureChange(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseUpdateEFeature(insertNonRootEObjectInContainmentList);
                if (result == null) result = caseEChange(insertNonRootEObjectInContainmentList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ContainmentPackage.REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST: {
                RemoveNonRootEObjectFromContainmentList<?> removeNonRootEObjectFromContainmentList = (RemoveNonRootEObjectFromContainmentList<?>)theEObject;
                T1 result = caseRemoveNonRootEObjectFromContainmentList(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseRemoveFromEList(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseUpdateContainmentEReference(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseUpdateSingleEListEntry(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseUpdateEReference(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseUpdateMultiValuedEFeature(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseEFeatureChange(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseUpdateEFeature(removeNonRootEObjectFromContainmentList);
                if (result == null) result = caseEChange(removeNonRootEObjectFromContainmentList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Update Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseUpdateContainmentEReference(UpdateContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Update Single Valued Containment EReference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Single Valued Containment EReference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseUpdateSingleValuedContainmentEReference(UpdateSingleValuedContainmentEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Create Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Non Root EObject Single</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseCreateNonRootEObjectSingle(CreateNonRootEObjectSingle<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Replace Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace Non Root EObject Single</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseReplaceNonRootEObjectSingle(ReplaceNonRootEObjectSingle<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Delete Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Non Root EObject Single</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseDeleteNonRootEObjectSingle(DeleteNonRootEObjectSingle<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Create Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Non Root EObject In List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseCreateNonRootEObjectInList(CreateNonRootEObjectInList<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Replace Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace Non Root EObject In List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseReplaceNonRootEObjectInList(ReplaceNonRootEObjectInList<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Delete Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Non Root EObject In List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseDeleteNonRootEObjectInList(DeleteNonRootEObjectInList<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Permute Containment EReference Values</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Permute Containment EReference Values</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 casePermuteContainmentEReferenceValues(PermuteContainmentEReferenceValues<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Insert Non Root EObject In Containment List</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Insert Non Root EObject In Containment List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseInsertNonRootEObjectInContainmentList(InsertNonRootEObjectInContainmentList<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Remove Non Root EObject From Containment List</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Remove Non Root EObject From Containment List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseRemoveNonRootEObjectFromContainmentList(RemoveNonRootEObjectFromContainmentList<T> object) {
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
     * Returns the result of interpreting the object as an instance of '<em>EObject Change</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseEObjectChange(EObjectChange<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Create EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseCreateEObject(CreateEObject<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Replace EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseReplaceEObject(ReplaceEObject<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Delete EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseDeleteEObject(DeleteEObject<T> object) {
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

} //ContainmentSwitch
