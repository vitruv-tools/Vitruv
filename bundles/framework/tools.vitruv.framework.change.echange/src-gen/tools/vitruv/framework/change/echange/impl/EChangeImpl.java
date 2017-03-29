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

import tools.vitruv.framework.change.echange.resolve.AtomicEChangeResolver;

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
	public EChange resolveBefore(final ResourceSet resourceSet) {
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			final EChange change = EcoreUtil.<EChange>copy(this);
			boolean _resolve = AtomicEChangeResolver.resolve(change, resourceSet, true);
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
			final EChange change = EcoreUtil.<EChange>copy(this);
			boolean _resolve = AtomicEChangeResolver.resolve(change, resourceSet, false);
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
			final EChange resolvedChange = this.resolveBefore(resourceSet);
			if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyForward())) {
				return resolvedChange;
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
			final EChange resolvedChange = this.resolveAfter(resourceSet);
			if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyBackward())) {
				return resolvedChange;
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
	public boolean applyForward() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			ApplyForwardCommandSwitch _instance = ApplyForwardCommandSwitch.getInstance();
			final List<Command> commands = _instance.doSwitch(this);
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
			ApplyBackwardCommandSwitch _instance = ApplyBackwardCommandSwitch.getInstance();
			final List<Command> commands = _instance.doSwitch(this);
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
