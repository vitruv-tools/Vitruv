/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;

import tools.vitruv.framework.change.echange.impl.EChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class CompoundEChangeImpl extends EChangeImpl implements CompoundEChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompoundEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.COMPOUND_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		EList<AtomicEChange> _atomicChanges = this.getAtomicChanges();
		for (final AtomicEChange change : _atomicChanges) {
			boolean _isResolved = change.isResolved();
			boolean _not = (!_isResolved);
			if (_not) {
				return false;
			}
		}
		return super.isResolved();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveApply(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveRevert(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet, final boolean applyChange) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolveApply = super.resolveApply(resourceSet);
			final CompoundEChange resolvedChange = ((CompoundEChange) _resolveApply);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean apply() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean revert() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == EChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ECHANGE___IS_RESOLVED: return CompoundPackage.COMPOUND_ECHANGE___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE_APPLY__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_APPLY__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_REVERT__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_REVERT__RESOURCESET;
				case EChangePackage.ECHANGE___APPLY: return CompoundPackage.COMPOUND_ECHANGE___APPLY;
				case EChangePackage.ECHANGE___REVERT: return CompoundPackage.COMPOUND_ECHANGE___REVERT;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
			case CompoundPackage.COMPOUND_ECHANGE___IS_RESOLVED:
				return isResolved();
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_APPLY__RESOURCESET:
				return resolveApply((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_REVERT__RESOURCESET:
				return resolveRevert((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE__RESOURCESET_BOOLEAN:
				return resolve((ResourceSet)arguments.get(0), (Boolean)arguments.get(1));
			case CompoundPackage.COMPOUND_ECHANGE___APPLY:
				return apply();
			case CompoundPackage.COMPOUND_ECHANGE___REVERT:
				return revert();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundEChangeImpl
