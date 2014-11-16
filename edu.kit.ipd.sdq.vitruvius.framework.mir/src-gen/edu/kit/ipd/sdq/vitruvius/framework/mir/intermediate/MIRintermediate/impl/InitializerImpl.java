/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initializer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.InitializerImpl#getCallStatement <em>Call Statement</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InitializerImpl extends MinimalEObjectImpl.Container implements Initializer {
	/**
	 * The default value of the '{@link #getCallStatement() <em>Call Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallStatement()
	 * @generated
	 * @ordered
	 */
	protected static final String CALL_STATEMENT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getCallStatement() <em>Call Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallStatement()
	 * @generated
	 * @ordered
	 */
	protected String callStatement = CALL_STATEMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitializerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.INITIALIZER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCallStatement() {
		return callStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallStatement(String newCallStatement) {
		String oldCallStatement = callStatement;
		callStatement = newCallStatement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.INITIALIZER__CALL_STATEMENT, oldCallStatement, callStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MIRintermediatePackage.INITIALIZER__CALL_STATEMENT:
				return getCallStatement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MIRintermediatePackage.INITIALIZER__CALL_STATEMENT:
				setCallStatement((String)newValue);
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
			case MIRintermediatePackage.INITIALIZER__CALL_STATEMENT:
				setCallStatement(CALL_STATEMENT_EDEFAULT);
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
			case MIRintermediatePackage.INITIALIZER__CALL_STATEMENT:
				return CALL_STATEMENT_EDEFAULT == null ? callStatement != null : !CALL_STATEMENT_EDEFAULT.equals(callStatement);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (callStatement: ");
		result.append(callStatement);
		result.append(')');
		return result.toString();
	}

} //InitializerImpl
