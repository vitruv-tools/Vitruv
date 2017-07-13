/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

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

import tools.vitruv.framework.change.echange.resolve.EChangeResolver;

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
	public EChange resolveBeforeAndApplyForward(final ResourceSet resourceSet) {
		return EChangeResolver.resolveCopy(this, resourceSet, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveAfterAndApplyBackward(final ResourceSet resourceSet) {
		return EChangeResolver.resolveCopy(this, resourceSet, false, false);
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
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET:
				return resolveBeforeAndApplyForward((ResourceSet)arguments.get(0));
			case CompoundPackage.COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET:
				return resolveAfterAndApplyBackward((ResourceSet)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundEChangeImpl
