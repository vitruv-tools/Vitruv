/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Permute EReference Values</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getAffectedFeature
 * <em>Affected Feature</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getAffectedEObject
 * <em>Affected EObject</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl#getOldTUIDOfAffectedEObject
 * <em>Old TUID Of Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PermuteEReferenceValuesImpl<T extends EObject> extends PermuteEListImpl<T>
        implements PermuteEReferenceValues<T> {
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
    protected EObject affectedEObject;

    /**
     * The default value of the '{@link #getOldTUIDOfAffectedEObject()
     * <em>Old TUID Of Affected EObject</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected static final TUID OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOldTUIDOfAffectedEObject()
     * <em>Old TUID Of Affected EObject</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     * @ordered
     */
    protected TUID oldTUIDOfAffectedEObject = OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected PermuteEReferenceValuesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.PERMUTE_EREFERENCE_VALUES;
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
                            ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE, oldAffectedFeature,
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
                    ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE, oldAffectedFeature,
                    this.affectedFeature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EObject getAffectedEObject() {
        if (this.affectedEObject != null && this.affectedEObject.eIsProxy()) {
            InternalEObject oldAffectedEObject = (InternalEObject) this.affectedEObject;
            this.affectedEObject = eResolveProxy(oldAffectedEObject);
            if (this.affectedEObject != oldAffectedEObject) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject,
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
    public EObject basicGetAffectedEObject() {
        return this.affectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAffectedEObject(final EObject newAffectedEObject) {
        EObject oldAffectedEObject = this.affectedEObject;
        this.affectedEObject = newAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT, oldAffectedEObject,
                    this.affectedEObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public TUID getOldTUIDOfAffectedEObject() {
        return this.oldTUIDOfAffectedEObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setOldTUIDOfAffectedEObject(final TUID newOldTUIDOfAffectedEObject) {
        TUID oldOldTUIDOfAffectedEObject = this.oldTUIDOfAffectedEObject;
        this.oldTUIDOfAffectedEObject = newOldTUIDOfAffectedEObject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT,
                    oldOldTUIDOfAffectedEObject, this.oldTUIDOfAffectedEObject));
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
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
            if (resolve)
                return getAffectedFeature();
            return basicGetAffectedFeature();
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
            if (resolve)
                return getAffectedEObject();
            return basicGetAffectedEObject();
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
            return getOldTUIDOfAffectedEObject();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
            setAffectedFeature((EReference) newValue);
            return;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) newValue);
            return;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject((TUID) newValue);
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
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
            setAffectedFeature((EReference) null);
            return;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
            setAffectedEObject((EObject) null);
            return;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
            setOldTUIDOfAffectedEObject(OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT);
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
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
            return this.affectedFeature != null;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
            return this.affectedEObject != null;
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
            return OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT == null ? this.oldTUIDOfAffectedEObject != null
                    : !OLD_TUID_OF_AFFECTED_EOBJECT_EDEFAULT.equals(this.oldTUIDOfAffectedEObject);
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
        if (baseClass == EFeatureChange.class) {
            switch (derivedFeatureID) {
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT:
                return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT:
                return FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
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
        if (baseClass == EFeatureChange.class) {
            switch (baseFeatureID) {
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE:
                return ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE;
            case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT:
                return ReferencePackage.PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT;
            case FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT:
                return ReferencePackage.PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT;
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
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
        if (baseClass == EFeatureChange.class) {
            switch (baseOperationID) {
            default:
                return -1;
            }
        }
        if (baseClass == UpdateEReference.class) {
            switch (baseOperationID) {
            case ReferencePackage.UPDATE_EREFERENCE___IS_CONTAINMENT:
                return ReferencePackage.PERMUTE_EREFERENCE_VALUES___IS_CONTAINMENT;
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
        case ReferencePackage.PERMUTE_EREFERENCE_VALUES___IS_CONTAINMENT:
            return isContainment();
        }
        return super.eInvoke(operationID, arguments);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (oldTUIDOfAffectedEObject: ");
        result.append(this.oldTUIDOfAffectedEObject);
        result.append(')');
        return result.toString();
    }

} // PermuteEReferenceValuesImpl
