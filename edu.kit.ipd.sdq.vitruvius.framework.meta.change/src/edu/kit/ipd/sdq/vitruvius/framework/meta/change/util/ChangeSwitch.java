/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage
 * @generated
 */
public class ChangeSwitch<T1> extends Switch<T1> {
	/**
     * The cached model package
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static ChangePackage modelPackage;

	/**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ChangeSwitch() {
        if (modelPackage == null) {
            modelPackage = ChangePackage.eINSTANCE;
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
            case ChangePackage.ECHANGE: {
                EChange eChange = (EChange)theEObject;
                T1 result = caseEChange(eChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EOBJECT_CHANGE: {
                EObjectChange eObjectChange = (EObjectChange)theEObject;
                T1 result = caseEObjectChange(eObjectChange);
                if (result == null) result = caseEChange(eObjectChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EFEATURE_CHANGE: {
                EFeatureChange<?> eFeatureChange = (EFeatureChange<?>)theEObject;
                T1 result = caseEFeatureChange(eFeatureChange);
                if (result == null) result = caseEChange(eFeatureChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EATTRIBUTE_CHANGE: {
                EAttributeChange eAttributeChange = (EAttributeChange)theEObject;
                T1 result = caseEAttributeChange(eAttributeChange);
                if (result == null) result = caseEFeatureChange(eAttributeChange);
                if (result == null) result = caseEChange(eAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EREFERENCE_CHANGE: {
                EReferenceChange eReferenceChange = (EReferenceChange)theEObject;
                T1 result = caseEReferenceChange(eReferenceChange);
                if (result == null) result = caseEFeatureChange(eReferenceChange);
                if (result == null) result = caseEChange(eReferenceChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.CREATE_EOBJECT: {
                CreateEObject createEObject = (CreateEObject)theEObject;
                T1 result = caseCreateEObject(createEObject);
                if (result == null) result = caseEObjectChange(createEObject);
                if (result == null) result = caseEChange(createEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.CREATE_NON_ROOT_EOBJECT: {
                CreateNonRootEObject<?> createNonRootEObject = (CreateNonRootEObject<?>)theEObject;
                T1 result = caseCreateNonRootEObject(createNonRootEObject);
                if (result == null) result = caseCreateEObject(createNonRootEObject);
                if (result == null) result = caseUpdateEContainmentReference(createNonRootEObject);
                if (result == null) result = caseEObjectChange(createNonRootEObject);
                if (result == null) result = caseUpdateEReference(createNonRootEObject);
                if (result == null) result = caseUpdateEFeature(createNonRootEObject);
                if (result == null) result = caseEReferenceChange(createNonRootEObject);
                if (result == null) result = caseEFeatureChange(createNonRootEObject);
                if (result == null) result = caseEChange(createNonRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.CREATE_ROOT_EOBJECT: {
                CreateRootEObject createRootEObject = (CreateRootEObject)theEObject;
                T1 result = caseCreateRootEObject(createRootEObject);
                if (result == null) result = caseCreateEObject(createRootEObject);
                if (result == null) result = caseEObjectChange(createRootEObject);
                if (result == null) result = caseEChange(createRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UPDATE_EFEATURE: {
                UpdateEFeature<?> updateEFeature = (UpdateEFeature<?>)theEObject;
                T1 result = caseUpdateEFeature(updateEFeature);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UNSET_EFEATURE: {
                UnsetEFeature<?> unsetEFeature = (UnsetEFeature<?>)theEObject;
                T1 result = caseUnsetEFeature(unsetEFeature);
                if (result == null) result = caseEFeatureChange(unsetEFeature);
                if (result == null) result = caseEChange(unsetEFeature);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UPDATE_EATTRIBUTE: {
                UpdateEAttribute<?> updateEAttribute = (UpdateEAttribute<?>)theEObject;
                T1 result = caseUpdateEAttribute(updateEAttribute);
                if (result == null) result = caseUpdateEFeature(updateEAttribute);
                if (result == null) result = caseEAttributeChange(updateEAttribute);
                if (result == null) result = caseEFeatureChange(updateEAttribute);
                if (result == null) result = caseEChange(updateEAttribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UPDATE_EREFERENCE: {
                UpdateEReference<?> updateEReference = (UpdateEReference<?>)theEObject;
                T1 result = caseUpdateEReference(updateEReference);
                if (result == null) result = caseUpdateEFeature(updateEReference);
                if (result == null) result = caseEReferenceChange(updateEReference);
                if (result == null) result = caseEFeatureChange(updateEReference);
                if (result == null) result = caseEChange(updateEReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UPDATE_ECONTAINMENT_REFERENCE: {
                UpdateEContainmentReference<?> updateEContainmentReference = (UpdateEContainmentReference<?>)theEObject;
                T1 result = caseUpdateEContainmentReference(updateEContainmentReference);
                if (result == null) result = caseUpdateEReference(updateEContainmentReference);
                if (result == null) result = caseUpdateEFeature(updateEContainmentReference);
                if (result == null) result = caseEReferenceChange(updateEContainmentReference);
                if (result == null) result = caseEFeatureChange(updateEContainmentReference);
                if (result == null) result = caseEChange(updateEContainmentReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.DELETE_EOBJECT: {
                DeleteEObject deleteEObject = (DeleteEObject)theEObject;
                T1 result = caseDeleteEObject(deleteEObject);
                if (result == null) result = caseEObjectChange(deleteEObject);
                if (result == null) result = caseEChange(deleteEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.DELETE_NON_ROOT_EOBJECT: {
                DeleteNonRootEObject<?> deleteNonRootEObject = (DeleteNonRootEObject<?>)theEObject;
                T1 result = caseDeleteNonRootEObject(deleteNonRootEObject);
                if (result == null) result = caseDeleteEObject(deleteNonRootEObject);
                if (result == null) result = caseUpdateEContainmentReference(deleteNonRootEObject);
                if (result == null) result = caseEObjectChange(deleteNonRootEObject);
                if (result == null) result = caseUpdateEReference(deleteNonRootEObject);
                if (result == null) result = caseUpdateEFeature(deleteNonRootEObject);
                if (result == null) result = caseEReferenceChange(deleteNonRootEObject);
                if (result == null) result = caseEFeatureChange(deleteNonRootEObject);
                if (result == null) result = caseEChange(deleteNonRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.DELETE_ROOT_EOBJECT: {
                DeleteRootEObject deleteRootEObject = (DeleteRootEObject)theEObject;
                T1 result = caseDeleteRootEObject(deleteRootEObject);
                if (result == null) result = caseDeleteEObject(deleteRootEObject);
                if (result == null) result = caseEObjectChange(deleteRootEObject);
                if (result == null) result = caseEChange(deleteRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.UPDATE_ELIST: {
                UpdateEList<?> updateEList = (UpdateEList<?>)theEObject;
                T1 result = caseUpdateEList(updateEList);
                if (result == null) result = caseEFeatureChange(updateEList);
                if (result == null) result = caseEChange(updateEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.INSERT_IN_ELIST: {
                InsertInEList<?> insertInEList = (InsertInEList<?>)theEObject;
                T1 result = caseInsertInEList(insertInEList);
                if (result == null) result = caseUpdateEList(insertInEList);
                if (result == null) result = caseEFeatureChange(insertInEList);
                if (result == null) result = caseEChange(insertInEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.REMOVE_FROM_ELIST: {
                RemoveFromEList<?> removeFromEList = (RemoveFromEList<?>)theEObject;
                T1 result = caseRemoveFromEList(removeFromEList);
                if (result == null) result = caseUpdateEList(removeFromEList);
                if (result == null) result = caseEFeatureChange(removeFromEList);
                if (result == null) result = caseEChange(removeFromEList);
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
	public T1 caseEChange(EChange object) {
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
	public T1 caseEObjectChange(EObjectChange object) {
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
     * Returns the result of interpreting the object as an instance of '<em>EAttribute Change</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EAttribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T1 caseEAttributeChange(EAttributeChange object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>EReference Change</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EReference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T1 caseEReferenceChange(EReferenceChange object) {
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
	public T1 caseCreateEObject(CreateEObject object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Create Non Root EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Non Root EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends Object> T1 caseCreateNonRootEObject(CreateNonRootEObject<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Create Root EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Root EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T1 caseCreateRootEObject(CreateRootEObject object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Unset EFeature</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unset EFeature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EStructuralFeature> T1 caseUnsetEFeature(UnsetEFeature<T> object) {
        return null;
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
	public <T extends Object> T1 caseUpdateEReference(UpdateEReference<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Update EContainment Reference</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update EContainment Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends Object> T1 caseUpdateEContainmentReference(UpdateEContainmentReference<T> object) {
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
	public T1 caseDeleteEObject(DeleteEObject object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Delete Non Root EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Non Root EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends Object> T1 caseDeleteNonRootEObject(DeleteNonRootEObject<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Delete Root EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Root EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T1 caseDeleteRootEObject(DeleteRootEObject object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Update EList</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update EList</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EStructuralFeature> T1 caseUpdateEList(UpdateEList<T> object) {
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
    public <T extends EStructuralFeature> T1 caseInsertInEList(InsertInEList<T> object) {
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
    public <T extends EStructuralFeature> T1 caseRemoveFromEList(RemoveFromEList<T> object) {
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

} //ChangeSwitch
