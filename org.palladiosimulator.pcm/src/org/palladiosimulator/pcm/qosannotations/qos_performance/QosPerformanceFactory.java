/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage
 * @generated
 */
public interface QosPerformanceFactory extends EFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    QosPerformanceFactory eINSTANCE = org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformanceFactoryImpl
            .init();

    /**
     * Returns a new object of class '<em>System Specified Execution Time</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>System Specified Execution Time</em>'.
     * @generated
     */
    SystemSpecifiedExecutionTime createSystemSpecifiedExecutionTime();

    /**
     * Returns a new object of class '<em>Component Specified Execution Time</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Component Specified Execution Time</em>'.
     * @generated
     */
    ComponentSpecifiedExecutionTime createComponentSpecifiedExecutionTime();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    QosPerformancePackage getQosPerformancePackage();

} // QosPerformanceFactory
