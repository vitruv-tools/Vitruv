/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;

import tools.vitruv.framework.change.echange.impl.SubtractiveEChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtraction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.CompoundSubtractionImpl#getSubtractiveChanges <em>Subtractive Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompoundSubtractionImpl<T extends Object> extends SubtractiveEChangeImpl<T> implements CompoundSubtraction<T> {
	/**
	 * The cached value of the '{@link #getSubtractiveChanges() <em>Subtractive Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubtractiveChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<SubtractiveEChange<T>> subtractiveChanges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompoundSubtractionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.COMPOUND_SUBTRACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubtractiveEChange<T>> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectContainmentEList<SubtractiveEChange<T>>(SubtractiveEChange.class, this, CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES:
				return ((InternalEList<?>)getSubtractiveChanges()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES:
				return getSubtractiveChanges();
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
			case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES:
				getSubtractiveChanges().clear();
				getSubtractiveChanges().addAll((Collection<? extends SubtractiveEChange<T>>)newValue);
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
			case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES:
				getSubtractiveChanges().clear();
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
			case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES:
				return subtractiveChanges != null && !subtractiveChanges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CompoundSubtractionImpl
