/**
 */
package tools.vitruv.framework.change.echange.feature.single.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.feature.impl.UpdateSingleValuedFeatureEChangeImpl;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Single Valued Feature EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ReplaceSingleValuedFeatureEChangeImpl<A extends EObject, F extends EStructuralFeature, T extends Object> extends UpdateSingleValuedFeatureEChangeImpl<A, F> implements ReplaceSingleValuedFeatureEChange<A, F, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReplaceSingleValuedFeatureEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SinglePackage.Literals.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFromNonDefaultValue() {
		return !java.util.Objects.equals(getOldValue(), getAffectedFeature().getDefaultValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isToNonDefaultValue() {
		return !java.util.Objects.equals(getNewValue(), getAffectedFeature().getDefaultValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getOldValue() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getNewValue() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == AdditiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE: return SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_NEW_VALUE;
				default: return -1;
			}
		}
		if (baseClass == SubtractiveEChange.class) {
			switch (baseOperationID) {
				case EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE: return SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_OLD_VALUE;
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
			case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE:
				return isFromNonDefaultValue();
			case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE:
				return isToNonDefaultValue();
			case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_OLD_VALUE:
				return getOldValue();
			case SinglePackage.REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_NEW_VALUE:
				return getNewValue();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ReplaceSingleValuedFeatureEChangeImpl
