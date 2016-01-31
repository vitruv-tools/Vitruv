/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage
 * @generated
 */
public class CompoundSwitch<T1> extends Switch<T1> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static CompoundPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompoundSwitch() {
        if (modelPackage == null) {
            modelPackage = CompoundPackage.eINSTANCE;
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
            case CompoundPackage.ECOMPOUND_CHANGE: {
                ECompoundChange eCompoundChange = (ECompoundChange)theEObject;
                T1 result = caseECompoundChange(eCompoundChange);
                if (result == null) result = caseEChange(eCompoundChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.CREATE_EOBJECT_AND_ADD: {
                CreateEObjectAndAdd<?> createEObjectAndAdd = (CreateEObjectAndAdd<?>)theEObject;
                T1 result = caseCreateEObjectAndAdd(createEObjectAndAdd);
                if (result == null) result = caseECompoundChange(createEObjectAndAdd);
                if (result == null) result = caseEChange(createEObjectAndAdd);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT: {
                DeleteEObjectAndSubtract<?> deleteEObjectAndSubtract = (DeleteEObjectAndSubtract<?>)theEObject;
                T1 result = caseDeleteEObjectAndSubtract(deleteEObjectAndSubtract);
                if (result == null) result = caseECompoundChange(deleteEObjectAndSubtract);
                if (result == null) result = caseEChange(deleteEObjectAndSubtract);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE: {
                DeleteEObjectCreateEObjectAndReplace<?, ?> deleteEObjectCreateEObjectAndReplace = (DeleteEObjectCreateEObjectAndReplace<?, ?>)theEObject;
                T1 result = caseDeleteEObjectCreateEObjectAndReplace(deleteEObjectCreateEObjectAndReplace);
                if (result == null) result = caseECompoundChange(deleteEObjectCreateEObjectAndReplace);
                if (result == null) result = caseEChange(deleteEObjectCreateEObjectAndReplace);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE: {
                DeleteEObjectCreateEObjectAndReplaceSingle<?> deleteEObjectCreateEObjectAndReplaceSingle = (DeleteEObjectCreateEObjectAndReplaceSingle<?>)theEObject;
                T1 result = caseDeleteEObjectCreateEObjectAndReplaceSingle(deleteEObjectCreateEObjectAndReplaceSingle);
                if (result == null) result = caseDeleteEObjectCreateEObjectAndReplace(deleteEObjectCreateEObjectAndReplaceSingle);
                if (result == null) result = caseECompoundChange(deleteEObjectCreateEObjectAndReplaceSingle);
                if (result == null) result = caseEChange(deleteEObjectCreateEObjectAndReplaceSingle);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST: {
                DeleteEObjectCreateEObjectAndReplaceInList<?> deleteEObjectCreateEObjectAndReplaceInList = (DeleteEObjectCreateEObjectAndReplaceInList<?>)theEObject;
                T1 result = caseDeleteEObjectCreateEObjectAndReplaceInList(deleteEObjectCreateEObjectAndReplaceInList);
                if (result == null) result = caseDeleteEObjectCreateEObjectAndReplace(deleteEObjectCreateEObjectAndReplaceInList);
                if (result == null) result = caseECompoundChange(deleteEObjectCreateEObjectAndReplaceInList);
                if (result == null) result = caseEChange(deleteEObjectCreateEObjectAndReplaceInList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.MOVE_EOBJECT: {
                MoveEObject<?> moveEObject = (MoveEObject<?>)theEObject;
                T1 result = caseMoveEObject(moveEObject);
                if (result == null) result = caseECompoundChange(moveEObject);
                if (result == null) result = caseEChange(moveEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.REPLACE_IN_ELIST: {
                ReplaceInEList<?> replaceInEList = (ReplaceInEList<?>)theEObject;
                T1 result = caseReplaceInEList(replaceInEList);
                if (result == null) result = caseECompoundChange(replaceInEList);
                if (result == null) result = caseEChange(replaceInEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>ECompound Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>ECompound Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T1 caseECompoundChange(ECompoundChange object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create EObject And Add</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create EObject And Add</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseCreateEObjectAndAdd(CreateEObjectAndAdd<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete EObject And Subtract</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete EObject And Subtract</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseDeleteEObjectAndSubtract(DeleteEObjectAndSubtract<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject, R extends EChange> T1 caseDeleteEObjectCreateEObjectAndReplace(DeleteEObjectCreateEObjectAndReplace<T, R> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace Single</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace Single</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseDeleteEObjectCreateEObjectAndReplaceSingle(DeleteEObjectCreateEObjectAndReplaceSingle<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace In List</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete EObject Create EObject And Replace In List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseDeleteEObjectCreateEObjectAndReplaceInList(DeleteEObjectCreateEObjectAndReplaceInList<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Move EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Move EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseMoveEObject(MoveEObject<T> object) {
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

} //CompoundSwitch
