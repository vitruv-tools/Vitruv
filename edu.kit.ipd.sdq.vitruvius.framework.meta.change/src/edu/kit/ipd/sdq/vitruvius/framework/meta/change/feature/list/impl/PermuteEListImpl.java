/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.PermuteEListImpl#getNewIndexForElementAt <em>New Index For Element At</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PermuteEListImpl<T extends Object> extends UpdateMultiValuedEFeatureImpl<T> implements PermuteEList<T> {
	/**
     * The cached value of the '{@link #getNewIndexForElementAt() <em>New Index For Element At</em>}' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getNewIndexForElementAt()
     * @generated
     * @ordered
     */
	protected EList<Integer> newIndexForElementAt;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected PermuteEListImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ListPackage.Literals.PERMUTE_ELIST;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EList<Integer> getNewIndexForElementAt() {
        if (newIndexForElementAt == null) {
            newIndexForElementAt = new EDataTypeUniqueEList<Integer>(Integer.class, this, ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT);
        }
        return newIndexForElementAt;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                return getNewIndexForElementAt();
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                getNewIndexForElementAt().clear();
                getNewIndexForElementAt().addAll((Collection<? extends Integer>)newValue);
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                getNewIndexForElementAt().clear();
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                return newIndexForElementAt != null && !newIndexForElementAt.isEmpty();
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
        result.append(" (newIndexForElementAt: ");
        result.append(newIndexForElementAt);
        result.append(')');
        return result.toString();
    }

} //PermuteEListImpl
