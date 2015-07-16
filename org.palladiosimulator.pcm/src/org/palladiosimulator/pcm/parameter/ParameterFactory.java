/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.parameter.ParameterPackage
 * @generated
 */
public interface ParameterFactory extends EFactory {

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
    ParameterFactory eINSTANCE = org.palladiosimulator.pcm.parameter.impl.ParameterFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Variable Usage</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Variable Usage</em>'.
     * @generated
     */
    VariableUsage createVariableUsage();

    /**
     * Returns a new object of class '<em>Variable Characterisation</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Variable Characterisation</em>'.
     * @generated
     */
    VariableCharacterisation createVariableCharacterisation();

    /**
     * Returns a new object of class '<em>Characterised Variable</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Characterised Variable</em>'.
     * @generated
     */
    CharacterisedVariable createCharacterisedVariable();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    ParameterPackage getParameterPackage();

} // ParameterFactory
