/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.list.impl.PermuteListEChangeImpl;

import tools.vitruv.framework.change.echange.feature.reference.PermuteEReferences;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EReferences</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PermuteEReferencesImpl<A extends EObject> extends PermuteListEChangeImpl<A, EReference> implements PermuteEReferences<A> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PermuteEReferencesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferencePackage.Literals.PERMUTE_EREFERENCES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isContainment() {
		return getAffectedFeature().isContainment();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == UpdateReferenceEChange.class) {
			switch (baseOperationID) {
				case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT: return ReferencePackage.PERMUTE_EREFERENCES___IS_CONTAINMENT;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ReferencePackage.PERMUTE_EREFERENCES___IS_CONTAINMENT:
				return isContainment();
		}
		return super.eInvoke(operationID, arguments);
	}

} //PermuteEReferencesImpl
