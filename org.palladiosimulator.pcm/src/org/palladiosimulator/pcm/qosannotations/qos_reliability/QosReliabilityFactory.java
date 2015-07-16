/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_reliability;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage
 * @generated
 */
public interface QosReliabilityFactory extends EFactory {

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
    QosReliabilityFactory eINSTANCE = org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityFactoryImpl
            .init();

    /**
     * Returns a new object of class '<em>Specified Reliability Annotation</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Specified Reliability Annotation</em>'.
     * @generated
     */
    SpecifiedReliabilityAnnotation createSpecifiedReliabilityAnnotation();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    QosReliabilityPackage getQosReliabilityPackage();

} // QosReliabilityFactory
