/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformanceFactory;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.SystemSpecifiedExecutionTime;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class QosPerformanceFactoryImpl extends EFactoryImpl implements QosPerformanceFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static QosPerformanceFactory init() {
        try {
            final QosPerformanceFactory theQosPerformanceFactory = (QosPerformanceFactory) EPackage.Registry.INSTANCE
                    .getEFactory(QosPerformancePackage.eNS_URI);
            if (theQosPerformanceFactory != null) {
                return theQosPerformanceFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new QosPerformanceFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public QosPerformanceFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(final EClass eClass) {
        switch (eClass.getClassifierID()) {
        case QosPerformancePackage.SYSTEM_SPECIFIED_EXECUTION_TIME:
            return this.createSystemSpecifiedExecutionTime();
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME:
            return this.createComponentSpecifiedExecutionTime();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SystemSpecifiedExecutionTime createSystemSpecifiedExecutionTime() {
        final SystemSpecifiedExecutionTimeImpl systemSpecifiedExecutionTime = new SystemSpecifiedExecutionTimeImpl();
        return systemSpecifiedExecutionTime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ComponentSpecifiedExecutionTime createComponentSpecifiedExecutionTime() {
        final ComponentSpecifiedExecutionTimeImpl componentSpecifiedExecutionTime = new ComponentSpecifiedExecutionTimeImpl();
        return componentSpecifiedExecutionTime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public QosPerformancePackage getQosPerformancePackage() {
        return (QosPerformancePackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static QosPerformancePackage getPackage() {
        return QosPerformancePackage.eINSTANCE;
    }

} // QosPerformanceFactoryImpl
