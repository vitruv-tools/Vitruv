/**
 */
package tools.vitruv.framework.change.echange.compound.util;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;
import tools.vitruv.framework.change.echange.compound.*;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.Switch;
import tools.vitruv.framework.change.echange.AdditiveEChange;

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
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage
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
			case CompoundPackage.COMPOUND_ECHANGE: {
				CompoundEChange compoundEChange = (CompoundEChange)theEObject;
				T1 result = caseCompoundEChange(compoundEChange);
				if (result == null) result = caseEChange(compoundEChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.MOVE_EOBJECT: {
				MoveEObject<?, ?, ?> moveEObject = (MoveEObject<?, ?, ?>)theEObject;
				T1 result = caseMoveEObject(moveEObject);
				if (result == null) result = caseCompoundEChange(moveEObject);
				if (result == null) result = caseEChange(moveEObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE: {
				ExplicitUnsetEFeature<?, ?, ?, ?> explicitUnsetEFeature = (ExplicitUnsetEFeature<?, ?, ?, ?>)theEObject;
				T1 result = caseExplicitUnsetEFeature(explicitUnsetEFeature);
				if (result == null) result = caseCompoundEChange(explicitUnsetEFeature);
				if (result == null) result = caseEChange(explicitUnsetEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CompoundPackage.REPLACE_IN_ELIST: {
				ReplaceInEList<?, ?, ?, ?, ?> replaceInEList = (ReplaceInEList<?, ?, ?, ?, ?>)theEObject;
				T1 result = caseReplaceInEList(replaceInEList);
				if (result == null) result = caseCompoundEChange(replaceInEList);
				if (result == null) result = caseEChange(replaceInEList);
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
    public T1 caseCompoundEChange(CompoundEChange object) {
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
    public <A extends EObject, F extends EStructuralFeature, T extends Object, S extends FeatureEChange<A, F> & SubtractiveEChange<T>> T1 caseExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F, T, S> object) {
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
	public <A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromListEChange<A, F, T> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F, T> & FeatureEChange<A, F> & AdditiveEChange<T>> T1 caseReplaceInEList(ReplaceInEList<A, F, T, R, I> object) {
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
