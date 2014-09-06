/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage
 * @generated
 */
public class ObjectSwitch<T1> extends Switch<T1> {
	/**
     * The cached model package
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static ObjectPackage modelPackage;

	/**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ObjectSwitch() {
        if (modelPackage == null) {
            modelPackage = ObjectPackage.eINSTANCE;
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
            case ObjectPackage.EOBJECT_CHANGE: {
                EObjectChange<?> eObjectChange = (EObjectChange<?>)theEObject;
                T1 result = caseEObjectChange(eObjectChange);
                if (result == null) result = caseEChange(eObjectChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.CREATE_EOBJECT: {
                CreateEObject<?> createEObject = (CreateEObject<?>)theEObject;
                T1 result = caseCreateEObject(createEObject);
                if (result == null) result = caseEObjectChange(createEObject);
                if (result == null) result = caseEChange(createEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.REPLACE_EOBJECT: {
                ReplaceEObject<?> replaceEObject = (ReplaceEObject<?>)theEObject;
                T1 result = caseReplaceEObject(replaceEObject);
                if (result == null) result = caseEObjectChange(replaceEObject);
                if (result == null) result = caseEChange(replaceEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.DELETE_EOBJECT: {
                DeleteEObject<?> deleteEObject = (DeleteEObject<?>)theEObject;
                T1 result = caseDeleteEObject(deleteEObject);
                if (result == null) result = caseEObjectChange(deleteEObject);
                if (result == null) result = caseEChange(deleteEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.CREATE_ROOT_EOBJECT: {
                CreateRootEObject<?> createRootEObject = (CreateRootEObject<?>)theEObject;
                T1 result = caseCreateRootEObject(createRootEObject);
                if (result == null) result = caseCreateEObject(createRootEObject);
                if (result == null) result = caseEObjectChange(createRootEObject);
                if (result == null) result = caseEChange(createRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.REPLACE_ROOT_EOBJECT: {
                ReplaceRootEObject<?> replaceRootEObject = (ReplaceRootEObject<?>)theEObject;
                T1 result = caseReplaceRootEObject(replaceRootEObject);
                if (result == null) result = caseReplaceEObject(replaceRootEObject);
                if (result == null) result = caseEObjectChange(replaceRootEObject);
                if (result == null) result = caseEChange(replaceRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ObjectPackage.DELETE_ROOT_EOBJECT: {
                DeleteRootEObject<?> deleteRootEObject = (DeleteRootEObject<?>)theEObject;
                T1 result = caseDeleteRootEObject(deleteRootEObject);
                if (result == null) result = caseDeleteEObject(deleteRootEObject);
                if (result == null) result = caseEObjectChange(deleteRootEObject);
                if (result == null) result = caseEChange(deleteRootEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
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
	public <T extends EObject> T1 caseCreateRootEObject(CreateRootEObject<T> object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Replace Root EObject</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replace Root EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public <T extends EObject> T1 caseReplaceRootEObject(ReplaceRootEObject<T> object) {
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
	public <T extends EObject> T1 caseDeleteRootEObject(DeleteRootEObject<T> object) {
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

} //ObjectSwitch
