/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Component Specified Execution Time</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.ComponentSpecifiedExecutionTimeImpl#getAssemblyContext_ComponentSpecifiedExecutionTime
 * <em>Assembly Context Component Specified Execution Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentSpecifiedExecutionTimeImpl extends SpecifiedExecutionTimeImpl
        implements ComponentSpecifiedExecutionTime {

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
    protected ComponentSpecifiedExecutionTimeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QosPerformancePackage.Literals.COMPONENT_SPECIFIED_EXECUTION_TIME;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext_ComponentSpecifiedExecutionTime() {
        return (AssemblyContext) this.eDynamicGet(
                QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                QosPerformancePackage.Literals.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext_ComponentSpecifiedExecutionTime() {
        return (AssemblyContext) this.eDynamicGet(
                QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                QosPerformancePackage.Literals.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext_ComponentSpecifiedExecutionTime(
            final AssemblyContext newAssemblyContext_ComponentSpecifiedExecutionTime) {
        this.eDynamicSet(
                QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                QosPerformancePackage.Literals.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME,
                newAssemblyContext_ComponentSpecifiedExecutionTime);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME:
            if (resolve) {
                return this.getAssemblyContext_ComponentSpecifiedExecutionTime();
            }
            return this.basicGetAssemblyContext_ComponentSpecifiedExecutionTime();
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
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME:
            this.setAssemblyContext_ComponentSpecifiedExecutionTime((AssemblyContext) newValue);
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
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME:
            this.setAssemblyContext_ComponentSpecifiedExecutionTime((AssemblyContext) null);
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
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME:
            return this.basicGetAssemblyContext_ComponentSpecifiedExecutionTime() != null;
        }
        return super.eIsSet(featureID);
    }

} // ComponentSpecifiedExecutionTimeImpl
