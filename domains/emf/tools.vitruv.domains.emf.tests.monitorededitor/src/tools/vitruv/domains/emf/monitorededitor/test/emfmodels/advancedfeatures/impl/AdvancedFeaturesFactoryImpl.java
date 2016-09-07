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
package tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdvancedFeaturesFactoryImpl extends EFactoryImpl implements AdvancedFeaturesFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AdvancedFeaturesFactory init() {
        try {
            AdvancedFeaturesFactory theAdvancedFeaturesFactory = (AdvancedFeaturesFactory)EPackage.Registry.INSTANCE.getEFactory(AdvancedFeaturesPackage.eNS_URI);
            if (theAdvancedFeaturesFactory != null) {
                return theAdvancedFeaturesFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new AdvancedFeaturesFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdvancedFeaturesFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case AdvancedFeaturesPackage.FEATURE_MAP_CONTAINING: return createFeatureMapContaining();
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING: return createReferenceListContaining();
            case AdvancedFeaturesPackage.DUMMY_DATA: return createDummyData();
            case AdvancedFeaturesPackage.DUMMY_DATA_CONTAINER: return createDummyDataContainer();
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING: return createAttributeListContaining();
            case AdvancedFeaturesPackage.ROOT_CONTAINER: return createRootContainer();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMapContaining createFeatureMapContaining() {
        FeatureMapContainingImpl featureMapContaining = new FeatureMapContainingImpl();
        return featureMapContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReferenceListContaining createReferenceListContaining() {
        ReferenceListContainingImpl referenceListContaining = new ReferenceListContainingImpl();
        return referenceListContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DummyData createDummyData() {
        DummyDataImpl dummyData = new DummyDataImpl();
        return dummyData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DummyDataContainer createDummyDataContainer() {
        DummyDataContainerImpl dummyDataContainer = new DummyDataContainerImpl();
        return dummyDataContainer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeListContaining createAttributeListContaining() {
        AttributeListContainingImpl attributeListContaining = new AttributeListContainingImpl();
        return attributeListContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RootContainer createRootContainer() {
        RootContainerImpl rootContainer = new RootContainerImpl();
        return rootContainer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdvancedFeaturesPackage getAdvancedFeaturesPackage() {
        return (AdvancedFeaturesPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static AdvancedFeaturesPackage getPackage() {
        return AdvancedFeaturesPackage.eINSTANCE;
    }

} //AdvancedFeaturesFactoryImpl
