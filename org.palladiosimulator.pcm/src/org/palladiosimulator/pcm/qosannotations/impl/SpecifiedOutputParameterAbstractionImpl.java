/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Specified Output Parameter Abstraction</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedOutputParameterAbstractionImpl#getSignature_SpecifiedOutputParameterAbstraction
 * <em>Signature Specified Output Parameter Abstraction</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedOutputParameterAbstractionImpl#getRole_SpecifiedOutputParameterAbstraction
 * <em>Role Specified Output Parameter Abstraction</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedOutputParameterAbstractionImpl#getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction
 * <em>Expected External Outputs Specified Output Parameter Abstraction</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.impl.SpecifiedOutputParameterAbstractionImpl#getQosAnnotations_SpecifiedOutputParameterAbstraction
 * <em>Qos Annotations Specified Output Parameter Abstraction</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SpecifiedOutputParameterAbstractionImpl extends CDOObjectImpl
        implements SpecifiedOutputParameterAbstraction {

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
    protected SpecifiedOutputParameterAbstractionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION;
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
    public Signature getSignature_SpecifiedOutputParameterAbstraction() {
        return (Signature) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Signature basicGetSignature_SpecifiedOutputParameterAbstraction() {
        return (Signature) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSignature_SpecifiedOutputParameterAbstraction(
            final Signature newSignature_SpecifiedOutputParameterAbstraction) {
        this.eDynamicSet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                newSignature_SpecifiedOutputParameterAbstraction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Role getRole_SpecifiedOutputParameterAbstraction() {
        return (Role) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Role basicGetRole_SpecifiedOutputParameterAbstraction() {
        return (Role) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRole_SpecifiedOutputParameterAbstraction(final Role newRole_SpecifiedOutputParameterAbstraction) {
        this.eDynamicSet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                newRole_SpecifiedOutputParameterAbstraction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<VariableUsage> getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction() {
        return (EList<VariableUsage>) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public QoSAnnotations getQosAnnotations_SpecifiedOutputParameterAbstraction() {
        return (QoSAnnotations) this.eDynamicGet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetQosAnnotations_SpecifiedOutputParameterAbstraction(
            final QoSAnnotations newQosAnnotations_SpecifiedOutputParameterAbstraction, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newQosAnnotations_SpecifiedOutputParameterAbstraction,
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setQosAnnotations_SpecifiedOutputParameterAbstraction(
            final QoSAnnotations newQosAnnotations_SpecifiedOutputParameterAbstraction) {
        this.eDynamicSet(
                QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                QosannotationsPackage.Literals.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                newQosAnnotations_SpecifiedOutputParameterAbstraction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction()).basicAdd(otherEnd, msgs);
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetQosAnnotations_SpecifiedOutputParameterAbstraction((QoSAnnotations) otherEnd, msgs);
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return ((InternalEList<?>) this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction())
                    .basicRemove(otherEnd, msgs);
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.basicSetQosAnnotations_SpecifiedOutputParameterAbstraction(null, msgs);
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.eInternalContainer().eInverseRemove(this,
                    QosannotationsPackage.QO_SANNOTATIONS__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTIONS_QO_SANNOTATIONS,
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            if (resolve) {
                return this.getSignature_SpecifiedOutputParameterAbstraction();
            }
            return this.basicGetSignature_SpecifiedOutputParameterAbstraction();
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            if (resolve) {
                return this.getRole_SpecifiedOutputParameterAbstraction();
            }
            return this.basicGetRole_SpecifiedOutputParameterAbstraction();
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction();
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.getQosAnnotations_SpecifiedOutputParameterAbstraction();
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setSignature_SpecifiedOutputParameterAbstraction((Signature) newValue);
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setRole_SpecifiedOutputParameterAbstraction((Role) newValue);
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction().clear();
            this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction()
                    .addAll((Collection<? extends VariableUsage>) newValue);
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setQosAnnotations_SpecifiedOutputParameterAbstraction((QoSAnnotations) newValue);
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setSignature_SpecifiedOutputParameterAbstraction((Signature) null);
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setRole_SpecifiedOutputParameterAbstraction((Role) null);
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction().clear();
            return;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            this.setQosAnnotations_SpecifiedOutputParameterAbstraction((QoSAnnotations) null);
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
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.basicGetSignature_SpecifiedOutputParameterAbstraction() != null;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.basicGetRole_SpecifiedOutputParameterAbstraction() != null;
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return !this.getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction().isEmpty();
        case QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION:
            return this.getQosAnnotations_SpecifiedOutputParameterAbstraction() != null;
        }
        return super.eIsSet(featureID);
    }

} // SpecifiedOutputParameterAbstractionImpl
