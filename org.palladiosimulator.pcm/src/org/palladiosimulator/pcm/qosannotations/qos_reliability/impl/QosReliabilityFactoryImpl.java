/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_reliability.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityFactory;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class QosReliabilityFactoryImpl extends EFactoryImpl implements QosReliabilityFactory {

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
    public static QosReliabilityFactory init() {
        try {
            final QosReliabilityFactory theQosReliabilityFactory = (QosReliabilityFactory) EPackage.Registry.INSTANCE
                    .getEFactory(QosReliabilityPackage.eNS_URI);
            if (theQosReliabilityFactory != null) {
                return theQosReliabilityFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new QosReliabilityFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public QosReliabilityFactoryImpl() {
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
        case QosReliabilityPackage.SPECIFIED_RELIABILITY_ANNOTATION:
            return this.createSpecifiedReliabilityAnnotation();
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
    public SpecifiedReliabilityAnnotation createSpecifiedReliabilityAnnotation() {
        final SpecifiedReliabilityAnnotationImpl specifiedReliabilityAnnotation = new SpecifiedReliabilityAnnotationImpl();
        return specifiedReliabilityAnnotation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public QosReliabilityPackage getQosReliabilityPackage() {
        return (QosReliabilityPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static QosReliabilityPackage getPackage() {
        return QosReliabilityPackage.eINSTANCE;
    }

} // QosReliabilityFactoryImpl
