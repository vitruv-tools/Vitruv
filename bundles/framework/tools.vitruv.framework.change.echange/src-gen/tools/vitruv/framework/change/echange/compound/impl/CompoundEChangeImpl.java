/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

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
	public boolean resolveBefore(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveAfter(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveBeforeAndApply(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveAfterAndApply(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolve(final ResourceSet resourceSet, final boolean resolveBefore, final boolean revertAfterResolving) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			boolean _resolveBefore = super.resolveBefore(resourceSet);
			boolean _not_1 = (!_resolveBefore);
			if (_not_1) {
				return false;
			}
			final BasicEList<EChange> changesMade = new BasicEList<EChange>();
			if (resolveBefore) {
				EList<AtomicEChange> _atomicChanges = this.getAtomicChanges();
				for (final EChange change : _atomicChanges) {
					boolean _resolveBeforeAndApplyForward = change.resolveBeforeAndApplyForward(resourceSet);
					boolean _not_2 = (!_resolveBeforeAndApplyForward);
					if (_not_2) {
						EList<EChange> _reverse = XcoreEListExtensions.<EChange>reverse(changesMade);
						for (final EChange changed : _reverse) {
							changed.applyBackward();
						}
						return false;
					}
					else {
						changesMade.add(change);
					}
				}
			}
			else {
				EList<AtomicEChange> _atomicChanges_1 = this.getAtomicChanges();
				EList<AtomicEChange> _reverse_1 = XcoreEListExtensions.<AtomicEChange>reverse(_atomicChanges_1);
				for (final EChange change_1 : _reverse_1) {
					boolean _resolveAfterAndApplyBackward = change_1.resolveAfterAndApplyBackward(resourceSet);
					boolean _not_3 = (!_resolveAfterAndApplyBackward);
					if (_not_3) {
						EList<EChange> _reverse_2 = XcoreEListExtensions.<EChange>reverse(changesMade);
						for (final EChange changed_1 : _reverse_2) {
							changed_1.applyForward();
						}
						return false;
					}
					else {
						changesMade.add(change_1);
					}
				}
			}
			if (revertAfterResolving) {
				if (resolveBefore) {
					this.applyBackward();
				}
				else {
					this.applyForward();
				}
			}
		}
		return true;
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
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY__RESOURCESET:
				return resolveBeforeAndApply((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY__RESOURCESET:
				return resolveAfterAndApply((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE__RESOURCESET_BOOLEAN_BOOLEAN:
				return resolve((ResourceSet)arguments.get(0), (Boolean)arguments.get(1), (Boolean)arguments.get(2));
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundEChangeImpl
