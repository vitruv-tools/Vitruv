/**
 */
package tools.vitruv.framework.change.echange.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.util.ApplyBackwardCommandSwitch;
import tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch;

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
	public boolean resolveBefore(final ResourceSet resourceSet) {
		boolean _equals = Objects.equal(resourceSet, null);
		if (_equals) {
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveAfter(final ResourceSet resourceSet) {
		return this.resolveBefore(resourceSet);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange copyAndResolveBefore(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final EChange resolvedChange = EcoreUtil.<EChange>copy(this);
			boolean _resolveBefore = resolvedChange.resolveBefore(resourceSet);
			if (_resolveBefore) {
				return resolvedChange;
			}
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange copyAndResolveAfter(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final EChange resolvedChange = EcoreUtil.<EChange>copy(this);
			boolean _resolveAfter = resolvedChange.resolveAfter(resourceSet);
			if (_resolveAfter) {
				return resolvedChange;
			}
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveBeforeAndApplyForward(final ResourceSet resourceSet) {
		boolean _resolveBefore = this.resolveBefore(resourceSet);
		if (_resolveBefore) {
			return this.applyForward();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveAfterAndApplyBackward(final ResourceSet resourceSet) {
		boolean _resolveAfter = this.resolveAfter(resourceSet);
		if (_resolveAfter) {
			return this.applyBackward();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean applyForward() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			ApplyForwardCommandSwitch _applyForwardCommandSwitch = new ApplyForwardCommandSwitch();
			final List<Command> commands = _applyForwardCommandSwitch.doSwitch(this);
			boolean _notEquals = (!Objects.equal(commands, null));
			if (_notEquals) {
				for (final Command c : commands) {
					boolean _canExecute = c.canExecute();
					if (_canExecute) {
						c.execute();
					}
					else {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean applyBackward() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			ApplyBackwardCommandSwitch _applyBackwardCommandSwitch = new ApplyBackwardCommandSwitch();
			final List<Command> commands = _applyBackwardCommandSwitch.doSwitch(this);
			boolean _notEquals = (!Objects.equal(commands, null));
			if (_notEquals) {
				for (final Command c : commands) {
					boolean _canExecute = c.canExecute();
					if (_canExecute) {
						c.execute();
					}
					else {
						return false;
					}
				}
				return true;
			}
		}
		return false;
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
			case EChangePackage.ECHANGE___COPY_AND_RESOLVE_BEFORE__RESOURCESET:
				return copyAndResolveBefore((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___COPY_AND_RESOLVE_AFTER__RESOURCESET:
				return copyAndResolveAfter((ResourceSet)arguments.get(0));
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
