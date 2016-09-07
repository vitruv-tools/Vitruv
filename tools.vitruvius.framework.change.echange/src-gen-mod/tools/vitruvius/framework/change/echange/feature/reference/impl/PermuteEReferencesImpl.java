/**
 */
package tools.vitruvius.framework.change.echange.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruvius.framework.change.echange.feature.reference.PermuteEReferences;
import tools.vitruvius.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange;
import tools.vitruvius.framework.change.echange.AtomicEChange;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.FeaturePackage;
import tools.vitruvius.framework.change.echange.feature.list.impl.PermuteListEChangeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Permute
 * EReferences</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.PermuteEReferencesImpl#getAffectedFeature
 * <em>Affected Feature</em>}</li>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.feature.reference.impl.PermuteEReferencesImpl#getAffectedEObject
 * <em>Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PermuteEReferencesImpl<A extends EObject> extends PermuteListEChangeImpl<A, EReference>
        implements PermuteEReferences<A> {
    /**
     * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAffectedFeature()
     * @generated
     * @ordered
     */
    protected EReference affectedFeature;

    /**
     * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAffectedEObject()
     * @generated
     * @ordered
     */
    protected A affectedEObject;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PermuteEReferencesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.PERMUTE_EREFERENCES;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAffectedFeature() {
        if (this.affectedFeature != null && this.affectedFeature.eIsProxy()) {
            InternalEObject oldAffectedFeature = (InternalEObject) this.affectedFeature;
            this.affectedFeature = (EReference) eResolveProxy(oldAffectedFeature);
            if (this.affectedFeature != oldAffectedFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE, oldAffectedFeature,
                            this.affectedFeature));
            }
        }
        return this.affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference basicGetAffectedFeature() {
        return this.affectedFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAffectedFeature(final EReference newAffectedFeature) {
        EReference oldAffectedFeature = this.affectedFeature;
        this.affectedFeature = newAffectedFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE, oldAffectedFeature, this.affectedFeature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public A getAffectedEObject() {
        if (this.affectedEObject != null && this.affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject) this.affectedEObject;
            this.affectedEObject = (A) eResolveProxy(oldAffectedEObject);
            if (this.affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT, oldAffectedEObject,
                            this.affectedEObject));
            }
        }
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public A basicGetAffectedEObject() {
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAffectedEObject(final A newAffectedEObject) {
        A oldAffectedEObject = this.affectedEObject;
        this.affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT, oldAffectedEObject, this.affectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE:
            if (resolve)
                return getAffectedFeature();
            return basicGetAffectedFeature();
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT:
            if (resolve)
                return getAffectedEObject();
            return basicGetAffectedEObject();
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
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE:
            setAffectedFeature((EReference) newValue);
            return;
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT:
            setAffectedEObject((A) newValue);
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
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE:
            setAffectedFeature((EReference) null);
            return;
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT:
            setAffectedEObject((A) null);
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
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE:
            return this.affectedFeature != null;
        case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT:
            return this.affectedEObject != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
        if (baseClass == EChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE:
                return FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE;
            case ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT:
                return FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
        if (baseClass == EChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (baseFeatureID) {
            case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
                return ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_FEATURE;
            case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
                return ReferencePackage.PERMUTE_EREFERENCES__AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedOperationID(final int baseOperationID, final Class<?> baseClass) {
        if (baseClass == EChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == AtomicEChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == FeatureEChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == UpdateReferenceEChange.class) {
            switch (baseOperationID) {
            case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT:
                return ReferencePackage.PERMUTE_EREFERENCES___IS_CONTAINMENT;
            default:
                return -1;
            }
        }
        return super.eDerivedOperationID(baseOperationID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eInvoke(final int operationID, final EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case ReferencePackage.PERMUTE_EREFERENCES___IS_CONTAINMENT:
            return isContainment();
        }
        return super.eInvoke(operationID, arguments);
    }

} // PermuteEReferencesImpl
