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
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getType
 * <em>Type</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getFeatureA
 * <em>Feature A</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl#getFeatureB
 * <em>Feature B</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class EFeatureCorrespondenceImpl<TFeature extends EStructuralFeature> extends
        SameTypeCorrespondenceImpl implements EFeatureCorrespondence<TFeature> {
    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final CorrespondenceType TYPE_EDEFAULT = CorrespondenceType.IDENTITY;
    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
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
     * 
     * @generated
     */
    protected EFeatureCorrespondenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CorrespondenceType getType() {
        return this.type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setType(final CorrespondenceType newType) {
        final CorrespondenceType oldType = this.type;
        this.type = newType == null ? TYPE_EDEFAULT : newType;
        if (this.eNotificationRequired()) {
            this.eNotify(new ENotificationImpl(this, Notification.SET,
                    CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE, oldType, this.type));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public TFeature getFeatureA() {
        if (this.featureA != null && this.featureA.eIsProxy()) {
            final InternalEObject oldFeatureA = (InternalEObject) this.featureA;
            this.featureA = (TFeature) this.eResolveProxy(oldFeatureA);
            if (this.featureA != oldFeatureA) {
                if (this.eNotificationRequired()) {
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A, oldFeatureA, this.featureA));
                }
            }
        }
        return this.featureA;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public TFeature basicGetFeatureA() {
        return this.featureA;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public TFeature getFeatureB() {
        if (this.featureB != null && this.featureB.eIsProxy()) {
            final InternalEObject oldFeatureB = (InternalEObject) this.featureB;
            this.featureB = (TFeature) this.eResolveProxy(oldFeatureB);
            if (this.featureB != oldFeatureB) {
                if (this.eNotificationRequired()) {
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B, oldFeatureB, this.featureB));
                }
            }
        }
        return this.featureB;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public TFeature basicGetFeatureB() {
        return this.featureB;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
            return this.getType();
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A:
            if (resolve) {
                return this.getFeatureA();
            }
            return this.basicGetFeatureA();
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B:
            if (resolve) {
                return this.getFeatureB();
            }
            return this.basicGetFeatureB();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
            this.setType((CorrespondenceType) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
            this.setType(TYPE_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE:
            return this.type != TYPE_EDEFAULT;
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A:
            return this.featureA != null;
        case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B:
            return this.featureB != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy()) {
            return super.toString();
        }

        final StringBuffer result = new StringBuffer(super.toString());
        result.append(" (type: ");
        result.append(this.type);
        result.append(')');
        return result.toString();
    }

} // EFeatureCorrespondenceImpl
