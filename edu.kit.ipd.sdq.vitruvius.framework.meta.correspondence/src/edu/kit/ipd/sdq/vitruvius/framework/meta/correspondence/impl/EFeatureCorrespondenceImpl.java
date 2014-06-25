/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>EFeature Correspondence</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getFeatureA <em>Feature A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getFeatureB <em>Feature B</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EFeatureCorrespondenceImpl<TFeature extends EStructuralFeature> extends
        SameTypeCorrespondenceImpl implements EFeatureCorrespondence<TFeature> {
    /**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
    protected static final CorrespondenceType TYPE_EDEFAULT = CorrespondenceType.IDENTITY;
    /**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
    protected CorrespondenceType type = TYPE_EDEFAULT;
    /**
     * The cached value of the '{@link #getFeatureA() <em>Feature A</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFeatureA()
     * @generated
     * @ordered
     */
    protected TFeature featureA;
    /**
     * The cached value of the '{@link #getFeatureB() <em>Feature B</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFeatureB()
     * @generated
     * @ordered
     */
    protected TFeature featureB;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected EFeatureCorrespondenceImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CorrespondenceType getType() {
		return type;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setType(CorrespondenceType newType) {
		CorrespondenceType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE, oldType, type));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    @SuppressWarnings("unchecked")
    public TFeature getFeatureA() {
		if (featureA != null && featureA.eIsProxy()) {
			InternalEObject oldFeatureA = (InternalEObject)featureA;
			featureA = (TFeature)eResolveProxy(oldFeatureA);
			if (featureA != oldFeatureA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A, oldFeatureA, featureA));
			}
		}
		return featureA;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TFeature basicGetFeatureA() {
		return featureA;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    @SuppressWarnings("unchecked")
    public TFeature getFeatureB() {
		if (featureB != null && featureB.eIsProxy()) {
			InternalEObject oldFeatureB = (InternalEObject)featureB;
			featureB = (TFeature)eResolveProxy(oldFeatureB);
			if (featureB != oldFeatureB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B, oldFeatureB, featureB));
			}
		}
		return featureB;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TFeature basicGetFeatureB() {
		return featureB;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
				return getType();
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A:
				if (resolve) return getFeatureA();
				return basicGetFeatureA();
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B:
				if (resolve) return getFeatureB();
				return basicGetFeatureB();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
				setType((CorrespondenceType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
				return type != TYPE_EDEFAULT;
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A:
				return featureA != null;
			case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B:
				return featureB != null;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} // EFeatureCorrespondenceImpl
