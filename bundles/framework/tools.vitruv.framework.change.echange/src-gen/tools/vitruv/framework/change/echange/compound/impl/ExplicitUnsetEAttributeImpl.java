/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute;

import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl#getSubtractiveChanges <em>Subtractive Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExplicitUnsetEAttributeImpl<A extends EObject, T extends Object> extends ExplicitUnsetEFeatureImpl<A, EAttribute> implements ExplicitUnsetEAttribute<A, T> {
	/**
	 * The cached value of the '{@link #getSubtractiveChanges() <em>Subtractive Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubtractiveChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<SubtractiveAttributeEChange<A, T>> subtractiveChanges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExplicitUnsetEAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setAffectedFeature(EAttribute newAffectedFeature) {
		super.setAffectedFeature(newAffectedFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubtractiveAttributeEChange<A, T>> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectContainmentEList<SubtractiveAttributeEChange<A, T>>(SubtractiveAttributeEChange.class, this, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		EList<SubtractiveAttributeEChange<A, T>> _subtractiveChanges = this.getSubtractiveChanges();
		final Function1<SubtractiveAttributeEChange<A, T>, AtomicEChange> _function = new Function1<SubtractiveAttributeEChange<A, T>, AtomicEChange>() {
			public AtomicEChange apply(final SubtractiveAttributeEChange<A, T> it) {
				return it;
			}
		};
		return XcoreEListExtensions.<SubtractiveAttributeEChange<A, T>, AtomicEChange>map(_subtractiveChanges, _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES:
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES:
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES:
				getSubtractiveChanges().clear();
				getSubtractiveChanges().addAll((Collection<? extends SubtractiveAttributeEChange<A, T>>)newValue);
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES:
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == CompoundSubtraction.class) {
			switch (derivedFeatureID) {
				case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES: return CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == CompoundSubtraction.class) {
			switch (baseFeatureID) {
				case CompoundPackage.COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == CompoundEChange.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == CompoundSubtraction.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES;
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExplicitUnsetEAttributeImpl
