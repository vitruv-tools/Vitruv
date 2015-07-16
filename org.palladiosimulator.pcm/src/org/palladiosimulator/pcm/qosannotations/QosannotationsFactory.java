/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage
 * @generated
 */
public interface QosannotationsFactory extends EFactory {

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
    QosannotationsFactory eINSTANCE = org.palladiosimulator.pcm.qosannotations.impl.QosannotationsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Qo SAnnotations</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Qo SAnnotations</em>'.
     * @generated
     */
    QoSAnnotations createQoSAnnotations();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    QosannotationsPackage getQosannotationsPackage();

} // QosannotationsFactory
