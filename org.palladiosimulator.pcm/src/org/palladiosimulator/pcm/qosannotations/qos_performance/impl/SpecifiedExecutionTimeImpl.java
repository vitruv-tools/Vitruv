/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.qosannotations.impl.SpecifiedQoSAnnotationImpl;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Specified Execution Time</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SpecifiedExecutionTimeImpl#getSpecification_SpecifiedExecutionTime
 * <em>Specification Specified Execution Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SpecifiedExecutionTimeImpl extends SpecifiedQoSAnnotationImpl implements SpecifiedExecutionTime {

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
    protected SpecifiedExecutionTimeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QosPerformancePackage.Literals.SPECIFIED_EXECUTION_TIME;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PCMRandomVariable getSpecification_SpecifiedExecutionTime() {
        return (PCMRandomVariable) this.eDynamicGet(
                QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME,
                QosPerformancePackage.Literals.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSpecification_SpecifiedExecutionTime(
            final PCMRandomVariable newSpecification_SpecifiedExecutionTime, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newSpecification_SpecifiedExecutionTime,
                QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSpecification_SpecifiedExecutionTime(
            final PCMRandomVariable newSpecification_SpecifiedExecutionTime) {
        this.eDynamicSet(QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME,
                QosPerformancePackage.Literals.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME,
                newSpecification_SpecifiedExecutionTime);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            final PCMRandomVariable specification_SpecifiedExecutionTime = this
                    .getSpecification_SpecifiedExecutionTime();
            if (specification_SpecifiedExecutionTime != null) {
                msgs = ((InternalEObject) specification_SpecifiedExecutionTime).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME,
                        null, msgs);
            }
            return this.basicSetSpecification_SpecifiedExecutionTime((PCMRandomVariable) otherEnd, msgs);
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
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            return this.basicSetSpecification_SpecifiedExecutionTime(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            return this.getSpecification_SpecifiedExecutionTime();
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
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            this.setSpecification_SpecifiedExecutionTime((PCMRandomVariable) newValue);
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
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            this.setSpecification_SpecifiedExecutionTime((PCMRandomVariable) null);
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
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME:
            return this.getSpecification_SpecifiedExecutionTime() != null;
        }
        return super.eIsSet(featureID);
    }

} // SpecifiedExecutionTimeImpl
