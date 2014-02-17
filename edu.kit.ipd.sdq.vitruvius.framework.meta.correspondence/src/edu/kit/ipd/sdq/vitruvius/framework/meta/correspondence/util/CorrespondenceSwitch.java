/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage
 * @generated
 */
public class CorrespondenceSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CorrespondencePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceSwitch() {
		if (modelPackage == null) {
			modelPackage = CorrespondencePackage.eINSTANCE;
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
			case CorrespondencePackage.CORRESPONDENCES: {
				Correspondences correspondences = (Correspondences)theEObject;
				T1 result = caseCorrespondences(correspondences);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.CORRESPONDENCE: {
				Correspondence correspondence = (Correspondence)theEObject;
				T1 result = caseCorrespondence(correspondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE: {
				SameTypeCorrespondence<?> sameTypeCorrespondence = (SameTypeCorrespondence<?>)theEObject;
				T1 result = caseSameTypeCorrespondence(sameTypeCorrespondence);
				if (result == null) result = caseCorrespondence(sameTypeCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.EOBJECT_CORRESPONDENCE: {
				EObjectCorrespondence eObjectCorrespondence = (EObjectCorrespondence)theEObject;
				T1 result = caseEObjectCorrespondence(eObjectCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(eObjectCorrespondence);
				if (result == null) result = caseCorrespondence(eObjectCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE: {
				EFeatureCorrespondence<?> eFeatureCorrespondence = (EFeatureCorrespondence<?>)theEObject;
				T1 result = caseEFeatureCorrespondence(eFeatureCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(eFeatureCorrespondence);
				if (result == null) result = caseCorrespondence(eFeatureCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.EATTRIBUTE_CORRESPONDENCE: {
				EAttributeCorrespondence eAttributeCorrespondence = (EAttributeCorrespondence)theEObject;
				T1 result = caseEAttributeCorrespondence(eAttributeCorrespondence);
				if (result == null) result = caseEFeatureCorrespondence(eAttributeCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(eAttributeCorrespondence);
				if (result == null) result = caseCorrespondence(eAttributeCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.EREFERENCE_CORRESPONDENCE: {
				EReferenceCorrespondence eReferenceCorrespondence = (EReferenceCorrespondence)theEObject;
				T1 result = caseEReferenceCorrespondence(eReferenceCorrespondence);
				if (result == null) result = caseEFeatureCorrespondence(eReferenceCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(eReferenceCorrespondence);
				if (result == null) result = caseCorrespondence(eReferenceCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.ECONTAINMENT_REFERENCE_CORRESPONDENCE: {
				EContainmentReferenceCorrespondence eContainmentReferenceCorrespondence = (EContainmentReferenceCorrespondence)theEObject;
				T1 result = caseEContainmentReferenceCorrespondence(eContainmentReferenceCorrespondence);
				if (result == null) result = caseEReferenceCorrespondence(eContainmentReferenceCorrespondence);
				if (result == null) result = caseEFeatureCorrespondence(eContainmentReferenceCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(eContainmentReferenceCorrespondence);
				if (result == null) result = caseCorrespondence(eContainmentReferenceCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.PARTIAL_EFEATURE_CORRESPONDENCE: {
				PartialEFeatureCorrespondence<?> partialEFeatureCorrespondence = (PartialEFeatureCorrespondence<?>)theEObject;
				T1 result = casePartialEFeatureCorrespondence(partialEFeatureCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE: {
				PartialEAttributeCorrespondence<?> partialEAttributeCorrespondence = (PartialEAttributeCorrespondence<?>)theEObject;
				T1 result = casePartialEAttributeCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = casePartialEFeatureCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = caseEAttributeCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = caseEFeatureCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = caseCorrespondence(partialEAttributeCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE: {
				PartialEReferenceCorrespondence<?> partialEReferenceCorrespondence = (PartialEReferenceCorrespondence<?>)theEObject;
				T1 result = casePartialEReferenceCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = casePartialEFeatureCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = caseEReferenceCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = caseEFeatureCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = caseSameTypeCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = caseCorrespondence(partialEReferenceCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Correspondences</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Correspondences</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseCorrespondences(Correspondences object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseCorrespondence(Correspondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Same Type Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Same Type Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseSameTypeCorrespondence(SameTypeCorrespondence<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEObjectCorrespondence(EObjectCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EFeature Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EFeature Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EStructuralFeature> T1 caseEFeatureCorrespondence(EFeatureCorrespondence<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EAttribute Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EAttribute Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEAttributeCorrespondence(EAttributeCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EReference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EReference Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEReferenceCorrespondence(EReferenceCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EContainment Reference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EContainment Reference Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEContainmentReferenceCorrespondence(EContainmentReferenceCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Partial EFeature Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Partial EFeature Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <TValue extends Object> T1 casePartialEFeatureCorrespondence(PartialEFeatureCorrespondence<TValue> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Partial EAttribute Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Partial EAttribute Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <TValue extends Object> T1 casePartialEAttributeCorrespondence(PartialEAttributeCorrespondence<TValue> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Partial EReference Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Partial EReference Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <TValue extends EObject> T1 casePartialEReferenceCorrespondence(PartialEReferenceCorrespondence<TValue> object) {
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

} //CorrespondenceSwitch
