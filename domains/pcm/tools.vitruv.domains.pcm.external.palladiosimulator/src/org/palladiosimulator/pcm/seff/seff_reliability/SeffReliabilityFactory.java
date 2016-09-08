/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage
 * @generated
 */
public interface SeffReliabilityFactory extends EFactory {

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
    SeffReliabilityFactory eINSTANCE = org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityFactoryImpl
            .init();

    /**
     * Returns a new object of class '<em>Recovery Action Behaviour</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Recovery Action Behaviour</em>'.
     * @generated
     */
    RecoveryActionBehaviour createRecoveryActionBehaviour();

    /**
     * Returns a new object of class '<em>Recovery Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Recovery Action</em>'.
     * @generated
     */
    RecoveryAction createRecoveryAction();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    SeffReliabilityPackage getSeffReliabilityPackage();

} // SeffReliabilityFactory
