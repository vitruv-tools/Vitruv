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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;

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
public class CompoundSubtractionImpl<T extends Object, S extends SubtractiveEChange<T>> extends CompoundEChangeImpl implements CompoundSubtraction<T, S> {
	/**
	 * The cached value of the '{@link #getSubtractiveChanges() <em>Subtractive Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubtractiveChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<S> subtractiveChanges;

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
	public EList<S> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectContainmentEList<S>(SubtractiveEChange.class, this, CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> result = new BasicEList<AtomicEChange>();
		EList<S> _subtractiveChanges = this.getSubtractiveChanges();
		result.addAll(_subtractiveChanges);
		return result;
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
				getSubtractiveChanges().addAll((Collection<? extends S>)newValue);
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundSubtractionImpl
