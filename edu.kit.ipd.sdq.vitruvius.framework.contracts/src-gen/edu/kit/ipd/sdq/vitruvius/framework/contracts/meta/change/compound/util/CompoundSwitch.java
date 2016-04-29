/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.*;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;
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
            case CompoundPackage.MOVE_EOBJECT: {
                MoveEObject<?, ?, ?> moveEObject = (MoveEObject<?, ?, ?>)theEObject;
                T1 result = caseMoveEObject(moveEObject);
                if (result == null) result = caseECompoundChange(moveEObject);
                if (result == null) result = caseEChange(moveEObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.REPLACE_IN_ELIST: {
                ReplaceInEList<?, ?, ?, ?, ?> replaceInEList = (ReplaceInEList<?, ?, ?, ?, ?>)theEObject;
                T1 result = caseReplaceInEList(replaceInEList);
                if (result == null) result = caseECompoundChange(replaceInEList);
                if (result == null) result = caseEChange(replaceInEList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CompoundPackage.EXPLICIT_UNSET_EFEATURE: {
                ExplicitUnsetEFeature<?, ?, ?, ?> explicitUnsetEFeature = (ExplicitUnsetEFeature<?, ?, ?, ?>)theEObject;
                T1 result = caseExplicitUnsetEFeature(explicitUnsetEFeature);
                if (result == null) result = caseECompoundChange(explicitUnsetEFeature);
                if (result == null) result = caseEChange(explicitUnsetEFeature);
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
    public <A extends EObject, B extends EObject, T extends EObject> T1 caseMoveEObject(MoveEObject<A, B, T> object) {
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
    public <A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromEList & EFeatureChange<A, F> & SubtractiveEChange<T>, I extends InsertInEList & EFeatureChange<A, F> & AdditiveEChange<T>> T1 caseReplaceInEList(ReplaceInEList<A, F, T, R, I> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Explicit Unset EFeature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Explicit Unset EFeature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <A extends EObject, F extends EStructuralFeature, T extends Object, S extends EFeatureChange<A, F> & SubtractiveEChange<T>> T1 caseExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F, T, S> object) {
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
