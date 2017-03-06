/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.compound.CompoundAddition;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Addition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.CompoundAdditionImpl#getAdditiveChanges <em>Additive Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompoundAdditionImpl<T extends Object, S extends AdditiveEChange<T>> extends CompoundEChangeImpl implements CompoundAddition<T, S> {
	/**
	 * The cached value of the '{@link #getAdditiveChanges() <em>Additive Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditiveChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<S> additiveChanges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompoundAdditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.COMPOUND_ADDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<S> getAdditiveChanges() {
		if (additiveChanges == null) {
			additiveChanges = new EObjectContainmentEList<S>(AdditiveEChange.class, this, CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES);
		}
		return additiveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> result = new BasicEList<AtomicEChange>();
		EList<S> _additiveChanges = this.getAdditiveChanges();
		result.addAll(_additiveChanges);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void resolveAtomicChanges(final ResourceSet resourceSet, final boolean applyChange) {
		if (applyChange) {
			for (int i = 0; (i < this.getAdditiveChanges().size()); i++) {
				EList<S> _additiveChanges = this.getAdditiveChanges();
				EList<S> _additiveChanges_1 = this.getAdditiveChanges();
				S _get = _additiveChanges_1.get(i);
				EChange _resolveApply = _get.resolveApply(resourceSet);
				_additiveChanges.set(i, ((S) _resolveApply));
			}
		}
		else {
			for (int i = (this.getAdditiveChanges().size() - 1); (i >= 0); i--) {
				EList<S> _additiveChanges = this.getAdditiveChanges();
				EList<S> _additiveChanges_1 = this.getAdditiveChanges();
				S _get = _additiveChanges_1.get(i);
				EChange _resolveRevert = _get.resolveRevert(resourceSet);
				_additiveChanges.set(i, ((S) _resolveRevert));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES:
				return ((InternalEList<?>)getAdditiveChanges()).basicRemove(otherEnd, msgs);
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
			case CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES:
				return getAdditiveChanges();
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
			case CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES:
				getAdditiveChanges().clear();
				getAdditiveChanges().addAll((Collection<? extends S>)newValue);
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
			case CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES:
				getAdditiveChanges().clear();
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
			case CompoundPackage.COMPOUND_ADDITION__ADDITIVE_CHANGES:
				return additiveChanges != null && !additiveChanges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.COMPOUND_ADDITION___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
			case CompoundPackage.COMPOUND_ADDITION___RESOLVE_ATOMIC_CHANGES__RESOURCESET_BOOLEAN:
				resolveAtomicChanges((ResourceSet)arguments.get(0), (Boolean)arguments.get(1));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundAdditionImpl
