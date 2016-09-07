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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesFactory;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdvancedFeaturesPackageImpl extends EPackageImpl implements AdvancedFeaturesPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass featureMapContainingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass referenceListContainingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dummyDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dummyDataContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeListContainingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rootContainerEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private AdvancedFeaturesPackageImpl() {
        super(eNS_URI, AdvancedFeaturesFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link AdvancedFeaturesPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static AdvancedFeaturesPackage init() {
        if (isInited) return (AdvancedFeaturesPackage)EPackage.Registry.INSTANCE.getEPackage(AdvancedFeaturesPackage.eNS_URI);

        // Obtain or create and register package
        AdvancedFeaturesPackageImpl theAdvancedFeaturesPackage = (AdvancedFeaturesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AdvancedFeaturesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AdvancedFeaturesPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theAdvancedFeaturesPackage.createPackageContents();

        // Initialize created meta-data
        theAdvancedFeaturesPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theAdvancedFeaturesPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(AdvancedFeaturesPackage.eNS_URI, theAdvancedFeaturesPackage);
        return theAdvancedFeaturesPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFeatureMapContaining() {
        return featureMapContainingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReferenceListContaining() {
        return referenceListContainingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReferenceListContaining_NonContainmentRefList() {
        return (EReference)referenceListContainingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDummyData() {
        return dummyDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDummyData_DummyData() {
        return (EAttribute)dummyDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDummyDataContainer() {
        return dummyDataContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDummyDataContainer_DummyDataObjs() {
        return (EReference)dummyDataContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeListContaining() {
        return attributeListContainingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeListContaining_AttrList() {
        return (EAttribute)attributeListContainingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRootContainer() {
        return rootContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRootContainer_FeatureMapContaining() {
        return (EReference)rootContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRootContainer_ReferenceListContaining() {
        return (EReference)rootContainerEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRootContainer_DummyDataContainerContaining() {
        return (EReference)rootContainerEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRootContainer_AttributeListContaining() {
        return (EReference)rootContainerEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdvancedFeaturesFactory getAdvancedFeaturesFactory() {
        return (AdvancedFeaturesFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        featureMapContainingEClass = createEClass(FEATURE_MAP_CONTAINING);

        referenceListContainingEClass = createEClass(REFERENCE_LIST_CONTAINING);
        createEReference(referenceListContainingEClass, REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST);

        dummyDataEClass = createEClass(DUMMY_DATA);
        createEAttribute(dummyDataEClass, DUMMY_DATA__DUMMY_DATA);

        dummyDataContainerEClass = createEClass(DUMMY_DATA_CONTAINER);
        createEReference(dummyDataContainerEClass, DUMMY_DATA_CONTAINER__DUMMY_DATA_OBJS);

        attributeListContainingEClass = createEClass(ATTRIBUTE_LIST_CONTAINING);
        createEAttribute(attributeListContainingEClass, ATTRIBUTE_LIST_CONTAINING__ATTR_LIST);

        rootContainerEClass = createEClass(ROOT_CONTAINER);
        createEReference(rootContainerEClass, ROOT_CONTAINER__FEATURE_MAP_CONTAINING);
        createEReference(rootContainerEClass, ROOT_CONTAINER__REFERENCE_LIST_CONTAINING);
        createEReference(rootContainerEClass, ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING);
        createEReference(rootContainerEClass, ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes, features, and operations; add parameters
        initEClass(featureMapContainingEClass, FeatureMapContaining.class, "FeatureMapContaining", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(referenceListContainingEClass, ReferenceListContaining.class, "ReferenceListContaining", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getReferenceListContaining_NonContainmentRefList(), this.getDummyData(), null, "nonContainmentRefList", null, 0, -1, ReferenceListContaining.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dummyDataEClass, DummyData.class, "DummyData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDummyData_DummyData(), ecorePackage.getEInt(), "dummyData", null, 0, 1, DummyData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dummyDataContainerEClass, DummyDataContainer.class, "DummyDataContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDummyDataContainer_DummyDataObjs(), this.getDummyData(), null, "dummyDataObjs", null, 0, -1, DummyDataContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(attributeListContainingEClass, AttributeListContaining.class, "AttributeListContaining", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAttributeListContaining_AttrList(), ecorePackage.getEString(), "attrList", null, 0, -1, AttributeListContaining.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(rootContainerEClass, RootContainer.class, "RootContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRootContainer_FeatureMapContaining(), this.getFeatureMapContaining(), null, "featureMapContaining", null, 0, 1, RootContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRootContainer_ReferenceListContaining(), this.getReferenceListContaining(), null, "referenceListContaining", null, 0, 1, RootContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRootContainer_DummyDataContainerContaining(), this.getDummyDataContainer(), null, "dummyDataContainerContaining", null, 0, 1, RootContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRootContainer_AttributeListContaining(), this.getAttributeListContaining(), null, "attributeListContaining", null, 0, 1, RootContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //AdvancedFeaturesPackageImpl
