/**
 */
package tools.vitruv.framework.correspondence.impl;

import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondencePackage;
import tools.vitruv.framework.correspondence.Correspondences;

import tools.vitruv.framework.correspondence.CorrespondenceModel;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correspondences</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondencesImpl#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondencesImpl#getCorrespondenceModel <em>Correspondence Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CorrespondencesImpl extends EObjectImpl implements Correspondences {
	/**
	 * The cached value of the '{@link #getCorrespondences() <em>Correspondences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondences()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> correspondences;

	/**
	 * The default value of the '{@link #getCorrespondenceModel() <em>Correspondence Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondenceModel()
	 * @generated
	 * @ordered
	 */
	protected static final CorrespondenceModel CORRESPONDENCE_MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorrespondenceModel() <em>Correspondence Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondenceModel()
	 * @generated
	 * @ordered
	 */
	protected CorrespondenceModel correspondenceModel = CORRESPONDENCE_MODEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorrespondencesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.CORRESPONDENCES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Correspondence> getCorrespondences() {
		if (correspondences == null) {
			correspondences = new EObjectContainmentWithInverseEList<Correspondence>(Correspondence.class, this, CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES, CorrespondencePackage.CORRESPONDENCE__PARENT);
		}
		return correspondences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceModel getCorrespondenceModel() {
		return correspondenceModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorrespondenceModel(CorrespondenceModel newCorrespondenceModel) {
		CorrespondenceModel oldCorrespondenceModel = correspondenceModel;
		correspondenceModel = newCorrespondenceModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_MODEL, oldCorrespondenceModel, correspondenceModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCorrespondences()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				return ((InternalEList<?>)getCorrespondences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				return getCorrespondences();
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_MODEL:
				return getCorrespondenceModel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				getCorrespondences().clear();
				getCorrespondences().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_MODEL:
				setCorrespondenceModel((CorrespondenceModel)newValue);
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
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				getCorrespondences().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_MODEL:
				setCorrespondenceModel(CORRESPONDENCE_MODEL_EDEFAULT);
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
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES:
				return correspondences != null && !correspondences.isEmpty();
			case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_MODEL:
				return CORRESPONDENCE_MODEL_EDEFAULT == null ? correspondenceModel != null : !CORRESPONDENCE_MODEL_EDEFAULT.equals(correspondenceModel);
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
		result.append(" (correspondenceModel: ");
		result.append(correspondenceModel);
		result.append(')');
		return result.toString();
	}

} //CorrespondencesImpl
