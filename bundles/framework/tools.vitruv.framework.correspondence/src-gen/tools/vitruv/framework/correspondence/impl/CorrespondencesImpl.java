/**
 */
package tools.vitruv.framework.correspondence.impl;

import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondencePackage;
import tools.vitruv.framework.correspondence.Correspondences;
import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
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
 * </ul>
 *
 * @generated
 */
public class CorrespondencesImpl extends MinimalEObjectImpl.Container implements Correspondences {
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
		}
		return super.eIsSet(featureID);
	}

} //CorrespondencesImpl
