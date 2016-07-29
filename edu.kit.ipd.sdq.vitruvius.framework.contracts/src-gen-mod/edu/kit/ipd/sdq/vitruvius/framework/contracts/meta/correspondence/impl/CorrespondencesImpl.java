/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences;

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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.impl.CorrespondencesImpl#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.impl.CorrespondencesImpl#getCorrespondenceInstance <em>Correspondence Instance</em>}</li>
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
     * The default value of the '{@link #getCorrespondenceInstance() <em>Correspondence Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrespondenceInstance()
     * @generated
     * @ordered
     */
    protected static final CorrespondenceInstance CORRESPONDENCE_INSTANCE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCorrespondenceInstance() <em>Correspondence Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrespondenceInstance()
     * @generated
     * @ordered
     */
    protected CorrespondenceInstance correspondenceInstance = CORRESPONDENCE_INSTANCE_EDEFAULT;

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
    public CorrespondenceInstance getCorrespondenceInstance() {
        return correspondenceInstance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCorrespondenceInstance(CorrespondenceInstance newCorrespondenceInstance) {
        CorrespondenceInstance oldCorrespondenceInstance = correspondenceInstance;
        correspondenceInstance = newCorrespondenceInstance;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_INSTANCE, oldCorrespondenceInstance, correspondenceInstance));
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
            case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_INSTANCE:
                return getCorrespondenceInstance();
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
            case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_INSTANCE:
                setCorrespondenceInstance((CorrespondenceInstance)newValue);
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
            case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_INSTANCE:
                setCorrespondenceInstance(CORRESPONDENCE_INSTANCE_EDEFAULT);
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
            case CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCE_INSTANCE:
                return CORRESPONDENCE_INSTANCE_EDEFAULT == null ? correspondenceInstance != null : !CORRESPONDENCE_INSTANCE_EDEFAULT.equals(correspondenceInstance);
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
        result.append(" (correspondenceInstance: ");
        result.append(correspondenceInstance);
        result.append(')');
        return result.toString();
    }

} //CorrespondencesImpl
