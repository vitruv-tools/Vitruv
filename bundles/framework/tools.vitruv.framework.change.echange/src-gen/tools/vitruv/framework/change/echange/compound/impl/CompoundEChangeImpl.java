/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;

import tools.vitruv.framework.change.echange.impl.EChangeImpl;

import tools.vitruv.framework.change.echange.resolve.CompoundEChangeResolver;

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
	public EChange resolveBefore(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final CompoundEChange change = EcoreUtil.<CompoundEChange>copy(this);
			boolean _resolve = CompoundEChangeResolver.resolve(change, resourceSet, true, true);
			if (_resolve) {
				return change;
			}
			return null;
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveAfter(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final CompoundEChange change = EcoreUtil.<CompoundEChange>copy(this);
			boolean _resolve = CompoundEChangeResolver.resolve(change, resourceSet, false, true);
			if (_resolve) {
				return change;
			}
			return null;
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveBeforeAndApplyForward(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final CompoundEChange change = EcoreUtil.<CompoundEChange>copy(this);
			boolean _resolve = CompoundEChangeResolver.resolve(change, resourceSet, true, false);
			if (_resolve) {
				return change;
			}
		}
		else {
			boolean _applyForward = this.applyForward();
			if (_applyForward) {
				return this;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveAfterAndApplyBackward(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final CompoundEChange change = EcoreUtil.<CompoundEChange>copy(this);
			boolean _resolve = CompoundEChangeResolver.resolve(change, resourceSet, false, false);
			if (_resolve) {
				return change;
			}
		}
		else {
			boolean _applyBackward = this.applyBackward();
			if (_applyBackward) {
				return this;
			}
		}
		return null;
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
				case EChangePackage.ECHANGE___RESOLVE_BEFORE__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_AFTER__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET: return CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;
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
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET:
				return resolveBefore((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET:
				return resolveAfter((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET:
				return resolveBeforeAndApplyForward((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET:
				return resolveAfterAndApplyBackward((ResourceSet)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundEChangeImpl
