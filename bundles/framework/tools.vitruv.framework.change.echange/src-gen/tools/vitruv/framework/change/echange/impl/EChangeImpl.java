/**
 */
package tools.vitruv.framework.change.echange.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.resolve.EChangeResolver;

import tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class EChangeImpl extends MinimalEObjectImpl.Container implements EChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EChangePackage.Literals.ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveBefore(final ResourceSet resourceSet) {
		return EChangeResolver.resolveCopy(this, resourceSet, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveAfter(final ResourceSet resourceSet) {
		return EChangeResolver.resolveCopy(this, resourceSet, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveBeforeAndApplyForward(final ResourceSet resourceSet) {
		final EChange resolvedChange = this.resolveBefore(resourceSet);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyForward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveAfterAndApplyBackward(final ResourceSet resourceSet) {
		final EChange resolvedChange = this.resolveAfter(resourceSet);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyBackward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean applyForward() {
		return ApplyEChangeSwitch.applyEChange(this, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean applyBackward() {
		return ApplyEChangeSwitch.applyEChange(this, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case EChangePackage.ECHANGE___IS_RESOLVED:
				return isResolved();
			case EChangePackage.ECHANGE___RESOLVE_BEFORE__RESOURCESET:
				return resolveBefore((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___RESOLVE_AFTER__RESOURCESET:
				return resolveAfter((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET:
				return resolveBeforeAndApplyForward((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET:
				return resolveAfterAndApplyBackward((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___APPLY_FORWARD:
				return applyForward();
			case EChangePackage.ECHANGE___APPLY_BACKWARD:
				return applyBackward();
		}
		return super.eInvoke(operationID, arguments);
	}

} //EChangeImpl
