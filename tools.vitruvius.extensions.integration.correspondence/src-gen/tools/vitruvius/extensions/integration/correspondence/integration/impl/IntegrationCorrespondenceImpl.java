/**
 */
package tools.vitruvius.extensions.integration.correspondence.integration.impl;

import tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponseCorrespondenceImpl;

import tools.vitruvius.extensions.integration.correspondence.integration.IntegrationCorrespondence;
import tools.vitruvius.extensions.integration.correspondence.integration.IntegrationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.extensions.integration.correspondence.integration.impl.IntegrationCorrespondenceImpl#isCreatedByIntegration <em>Created By Integration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntegrationCorrespondenceImpl extends ResponseCorrespondenceImpl implements IntegrationCorrespondence {
	/**
	 * The default value of the '{@link #isCreatedByIntegration() <em>Created By Integration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreatedByIntegration()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CREATED_BY_INTEGRATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCreatedByIntegration() <em>Created By Integration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreatedByIntegration()
	 * @generated
	 * @ordered
	 */
	protected boolean createdByIntegration = CREATED_BY_INTEGRATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntegrationCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IntegrationPackage.Literals.INTEGRATION_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCreatedByIntegration() {
		return createdByIntegration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreatedByIntegration(boolean newCreatedByIntegration) {
		boolean oldCreatedByIntegration = createdByIntegration;
		createdByIntegration = newCreatedByIntegration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IntegrationPackage.INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION, oldCreatedByIntegration, createdByIntegration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IntegrationPackage.INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION:
				return isCreatedByIntegration();
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
			case IntegrationPackage.INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION:
				setCreatedByIntegration((Boolean)newValue);
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
			case IntegrationPackage.INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION:
				setCreatedByIntegration(CREATED_BY_INTEGRATION_EDEFAULT);
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
			case IntegrationPackage.INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION:
				return createdByIntegration != CREATED_BY_INTEGRATION_EDEFAULT;
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
		result.append(" (createdByIntegration: ");
		result.append(createdByIntegration);
		result.append(')');
		return result.toString();
	}

} //IntegrationCorrespondenceImpl
