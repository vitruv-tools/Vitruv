/**
 */
package tools.vitruv.framework.change.echange.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

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
	public EChange resolve(final ResourceSet resourceSet) {
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
	public Command getApplyCommand() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean apply() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			final Command command = this.getApplyCommand();
			boolean _canExecute = command.canExecute();
			if (_canExecute) {
				command.execute();
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
	public Command getRevertCommand() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean revert() {
		boolean _isResolved = this.isResolved();
		if (_isResolved) {
			final Command command = this.getRevertCommand();
			boolean _canExecute = command.canExecute();
			if (_canExecute) {
				command.execute();
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
			case EChangePackage.ECHANGE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
			case EChangePackage.ECHANGE___GET_APPLY_COMMAND:
				return getApplyCommand();
			case EChangePackage.ECHANGE___APPLY:
				return apply();
			case EChangePackage.ECHANGE___GET_REVERT_COMMAND:
				return getRevertCommand();
			case EChangePackage.ECHANGE___REVERT:
				return revert();
		}
		return super.eInvoke(operationID, arguments);
	}

} //EChangeImpl
