/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

/**
 */
package tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage
 * @generated
 */
public interface AdvancedFeaturesFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AdvancedFeaturesFactory eINSTANCE = tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Feature Map Containing</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Feature Map Containing</em>'.
     * @generated
     */
    FeatureMapContaining createFeatureMapContaining();

    /**
     * Returns a new object of class '<em>Reference List Containing</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Reference List Containing</em>'.
     * @generated
     */
    ReferenceListContaining createReferenceListContaining();

    /**
     * Returns a new object of class '<em>Dummy Data</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dummy Data</em>'.
     * @generated
     */
    DummyData createDummyData();

    /**
     * Returns a new object of class '<em>Dummy Data Container</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dummy Data Container</em>'.
     * @generated
     */
    DummyDataContainer createDummyDataContainer();

    /**
     * Returns a new object of class '<em>Attribute List Containing</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute List Containing</em>'.
     * @generated
     */
    AttributeListContaining createAttributeListContaining();

    /**
     * Returns a new object of class '<em>Root Container</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Root Container</em>'.
     * @generated
     */
    RootContainer createRootContainer();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    AdvancedFeaturesPackage getAdvancedFeaturesPackage();

} //AdvancedFeaturesFactory
