/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Specified Qo SAnnotation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedQoSAnnotationImpl#getSignature_SpecifiedQoSAnnation
 * <em>Signature Specified Qo SAnnation</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedQoSAnnotationImpl#getRole_SpecifiedQoSAnnotation
 * <em>Role Specified Qo SAnnotation</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedQoSAnnotationImpl#getQosAnnotations_SpecifiedQoSAnnotation
 * <em>Qos Annotations Specified Qo SAnnotation</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SpecifiedQoSAnnotationImpl extends CDOObjectImpl implements SpecifiedQoSAnnotation {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected SpecifiedQoSAnnotationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Signature getSignature_SpecifiedQoSAnnation() {
        return (Signature) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Signature basicGetSignature_SpecifiedQoSAnnation() {
        return (Signature) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSignature_SpecifiedQoSAnnation(final Signature newSignature_SpecifiedQoSAnnation) {
        this.eDynamicSet(QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION,
                newSignature_SpecifiedQoSAnnation);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Role getRole_SpecifiedQoSAnnotation() {
        return (Role) this.eDynamicGet(QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Role basicGetRole_SpecifiedQoSAnnotation() {
        return (Role) this.eDynamicGet(QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRole_SpecifiedQoSAnnotation(final Role newRole_SpecifiedQoSAnnotation) {
        this.eDynamicSet(QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION,
                newRole_SpecifiedQoSAnnotation);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public QoSAnnotations getQosAnnotations_SpecifiedQoSAnnotation() {
        return (QoSAnnotations) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetQosAnnotations_SpecifiedQoSAnnotation(
            final QoSAnnotations newQosAnnotations_SpecifiedQoSAnnotation, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newQosAnnotations_SpecifiedQoSAnnotation,
                QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setQosAnnotations_SpecifiedQoSAnnotation(
            final QoSAnnotations newQosAnnotations_SpecifiedQoSAnnotation) {
        this.eDynamicSet(QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION,
                QosannotationsPackage.Literals.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION,
                newQosAnnotations_SpecifiedQoSAnnotation);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetQosAnnotations_SpecifiedQoSAnnotation((QoSAnnotations) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            return this.basicSetQosAnnotations_SpecifiedQoSAnnotation(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            return this.eInternalContainer().eInverseRemove(this,
                    QosannotationsPackage.QO_SANNOTATIONS__SPECIFIED_QO_SANNOTATIONS_QO_SANNOTATIONS,
                    QoSAnnotations.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION:
            if (resolve) {
                return this.getSignature_SpecifiedQoSAnnation();
            }
            return this.basicGetSignature_SpecifiedQoSAnnation();
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION:
            if (resolve) {
                return this.getRole_SpecifiedQoSAnnotation();
            }
            return this.basicGetRole_SpecifiedQoSAnnotation();
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            return this.getQosAnnotations_SpecifiedQoSAnnotation();
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
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION:
            this.setSignature_SpecifiedQoSAnnation((Signature) newValue);
            return;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION:
            this.setRole_SpecifiedQoSAnnotation((Role) newValue);
            return;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            this.setQosAnnotations_SpecifiedQoSAnnotation((QoSAnnotations) newValue);
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
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION:
            this.setSignature_SpecifiedQoSAnnation((Signature) null);
            return;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION:
            this.setRole_SpecifiedQoSAnnotation((Role) null);
            return;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            this.setQosAnnotations_SpecifiedQoSAnnotation((QoSAnnotations) null);
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
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION:
            return this.basicGetSignature_SpecifiedQoSAnnation() != null;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION:
            return this.basicGetRole_SpecifiedQoSAnnotation() != null;
        case QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION:
            return this.getQosAnnotations_SpecifiedQoSAnnotation() != null;
        }
        return super.eIsSet(featureID);
    }

} // SpecifiedQoSAnnotationImpl
