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

import tools.vitruv.framework.change.echange.util.ApplyCommandSwitch;
import tools.vitruv.framework.change.echange.util.RevertCommandSwitch;

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
	public EChange resolveApply(final ResourceSet resourceSet) {
		boolean _equals = Objects.equal(resourceSet, null);
		if (_equals) {
			return null;
		}
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			return EcoreUtil.<EChange>copy(this);
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolveRevert(final ResourceSet resourceSet) {
		return this.resolveApply(resourceSet);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean apply() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			ApplyCommandSwitch _applyCommandSwitch = new ApplyCommandSwitch();
			final List<Command> commands = _applyCommandSwitch.doSwitch(this);
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
	public boolean revert() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			RevertCommandSwitch _revertCommandSwitch = new RevertCommandSwitch();
			final List<Command> commands = _revertCommandSwitch.doSwitch(this);
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
			case EChangePackage.ECHANGE___RESOLVE_APPLY__RESOURCESET:
				return resolveApply((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___RESOLVE_REVERT__RESOURCESET:
				return resolveRevert((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___APPLY:
				return apply();
			case EChangePackage.ECHANGE___REVERT:
				return revert();
		}
		return super.eInvoke(operationID, arguments);
	}

} //EChangeImpl
