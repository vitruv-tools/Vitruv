/**
 */
package tools.vitruv.framework.change.echange.feature.list.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import tools.vitruv.framework.change.echange.feature.impl.UpdateMultiValuedFeatureEChangeImpl;
import tools.vitruv.framework.change.echange.feature.list.ListPackage;
import tools.vitruv.framework.change.echange.feature.list.PermuteListEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute List EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.list.impl.PermuteListEChangeImpl#getNewIndicesForElementsAtOldIndices <em>New Indices For Elements At Old Indices</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PermuteListEChangeImpl<A extends EObject, F extends EStructuralFeature> extends UpdateMultiValuedFeatureEChangeImpl<A, F> implements PermuteListEChange<A, F> {
    /**
	 * The cached value of the '{@link #getNewIndicesForElementsAtOldIndices() <em>New Indices For Elements At Old Indices</em>}' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNewIndicesForElementsAtOldIndices()
	 * @generated
	 * @ordered
	 */
    protected EList<Integer> newIndicesForElementsAtOldIndices;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected PermuteListEChangeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ListPackage.Literals.PERMUTE_LIST_ECHANGE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Integer> getNewIndicesForElementsAtOldIndices() {
		if (newIndicesForElementsAtOldIndices == null) {
			newIndicesForElementsAtOldIndices = new EDataTypeUniqueEList<Integer>(Integer.class, this, ListPackage.PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES);
		}
		return newIndicesForElementsAtOldIndices;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ListPackage.PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES:
				return getNewIndicesForElementsAtOldIndices();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ListPackage.PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES:
				getNewIndicesForElementsAtOldIndices().clear();
				getNewIndicesForElementsAtOldIndices().addAll((Collection<? extends Integer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case ListPackage.PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES:
				getNewIndicesForElementsAtOldIndices().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ListPackage.PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES:
				return newIndicesForElementsAtOldIndices != null && !newIndicesForElementsAtOldIndices.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (newIndicesForElementsAtOldIndices: ");
		result.append(newIndicesForElementsAtOldIndices);
		result.append(')');
		return result.toString();
	}

} //PermuteListEChangeImpl
