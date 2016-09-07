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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesFactory
 * @model kind="package"
 * @generated
 */
public interface AdvancedFeaturesPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "advancedfeatures";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://example.com/advancedfeatures";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AdvancedFeaturesPackage eINSTANCE = tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl.init();

    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.FeatureMapContainingImpl <em>Feature Map Containing</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.FeatureMapContainingImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getFeatureMapContaining()
     * @generated
     */
    int FEATURE_MAP_CONTAINING = 0;

    /**
     * The number of structural features of the '<em>Feature Map Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_MAP_CONTAINING_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Feature Map Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_MAP_CONTAINING_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.ReferenceListContainingImpl <em>Reference List Containing</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.ReferenceListContainingImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getReferenceListContaining()
     * @generated
     */
    int REFERENCE_LIST_CONTAINING = 1;

    /**
     * The feature id for the '<em><b>Non Containment Ref List</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST = 0;

    /**
     * The number of structural features of the '<em>Reference List Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_LIST_CONTAINING_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Reference List Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_LIST_CONTAINING_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataImpl <em>Dummy Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getDummyData()
     * @generated
     */
    int DUMMY_DATA = 2;

    /**
     * The feature id for the '<em><b>Dummy Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA__DUMMY_DATA = 0;

    /**
     * The number of structural features of the '<em>Dummy Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Dummy Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataContainerImpl <em>Dummy Data Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataContainerImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getDummyDataContainer()
     * @generated
     */
    int DUMMY_DATA_CONTAINER = 3;

    /**
     * The feature id for the '<em><b>Dummy Data Objs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA_CONTAINER__DUMMY_DATA_OBJS = 0;

    /**
     * The number of structural features of the '<em>Dummy Data Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Dummy Data Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DUMMY_DATA_CONTAINER_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AttributeListContainingImpl <em>Attribute List Containing</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AttributeListContainingImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getAttributeListContaining()
     * @generated
     */
    int ATTRIBUTE_LIST_CONTAINING = 4;

    /**
     * The feature id for the '<em><b>Attr List</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_LIST_CONTAINING__ATTR_LIST = 0;

    /**
     * The number of structural features of the '<em>Attribute List Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_LIST_CONTAINING_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Attribute List Containing</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_LIST_CONTAINING_OPERATION_COUNT = 0;


    /**
     * The meta object id for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl <em>Root Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getRootContainer()
     * @generated
     */
    int ROOT_CONTAINER = 5;

    /**
     * The feature id for the '<em><b>Feature Map Containing</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER__FEATURE_MAP_CONTAINING = 0;

    /**
     * The feature id for the '<em><b>Reference List Containing</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER__REFERENCE_LIST_CONTAINING = 1;

    /**
     * The feature id for the '<em><b>Dummy Data Container Containing</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING = 2;

    /**
     * The feature id for the '<em><b>Attribute List Containing</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING = 3;

    /**
     * The number of structural features of the '<em>Root Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Root Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROOT_CONTAINER_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining <em>Feature Map Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Feature Map Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining
     * @generated
     */
    EClass getFeatureMapContaining();

    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining <em>Reference List Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reference List Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining
     * @generated
     */
    EClass getReferenceListContaining();

    /**
     * Returns the meta object for the reference list '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining#getNonContainmentRefList <em>Non Containment Ref List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Non Containment Ref List</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining#getNonContainmentRefList()
     * @see #getReferenceListContaining()
     * @generated
     */
    EReference getReferenceListContaining_NonContainmentRefList();

    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData <em>Dummy Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dummy Data</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData
     * @generated
     */
    EClass getDummyData();

    /**
     * Returns the meta object for the attribute '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData#getDummyData <em>Dummy Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Dummy Data</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData#getDummyData()
     * @see #getDummyData()
     * @generated
     */
    EAttribute getDummyData_DummyData();

    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer <em>Dummy Data Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dummy Data Container</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer
     * @generated
     */
    EClass getDummyDataContainer();

    /**
     * Returns the meta object for the containment reference list '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer#getDummyDataObjs <em>Dummy Data Objs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dummy Data Objs</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer#getDummyDataObjs()
     * @see #getDummyDataContainer()
     * @generated
     */
    EReference getDummyDataContainer_DummyDataObjs();

    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining <em>Attribute List Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute List Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining
     * @generated
     */
    EClass getAttributeListContaining();

    /**
     * Returns the meta object for the attribute list '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining#getAttrList <em>Attr List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Attr List</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining#getAttrList()
     * @see #getAttributeListContaining()
     * @generated
     */
    EAttribute getAttributeListContaining_AttrList();

    /**
     * Returns the meta object for class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer <em>Root Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Root Container</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer
     * @generated
     */
    EClass getRootContainer();

    /**
     * Returns the meta object for the containment reference '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getFeatureMapContaining <em>Feature Map Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Feature Map Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getFeatureMapContaining()
     * @see #getRootContainer()
     * @generated
     */
    EReference getRootContainer_FeatureMapContaining();

    /**
     * Returns the meta object for the containment reference '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getReferenceListContaining <em>Reference List Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reference List Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getReferenceListContaining()
     * @see #getRootContainer()
     * @generated
     */
    EReference getRootContainer_ReferenceListContaining();

    /**
     * Returns the meta object for the containment reference '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getDummyDataContainerContaining <em>Dummy Data Container Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Dummy Data Container Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getDummyDataContainerContaining()
     * @see #getRootContainer()
     * @generated
     */
    EReference getRootContainer_DummyDataContainerContaining();

    /**
     * Returns the meta object for the containment reference '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getAttributeListContaining <em>Attribute List Containing</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Attribute List Containing</em>'.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer#getAttributeListContaining()
     * @see #getRootContainer()
     * @generated
     */
    EReference getRootContainer_AttributeListContaining();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    AdvancedFeaturesFactory getAdvancedFeaturesFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.FeatureMapContainingImpl <em>Feature Map Containing</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.FeatureMapContainingImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getFeatureMapContaining()
         * @generated
         */
        EClass FEATURE_MAP_CONTAINING = eINSTANCE.getFeatureMapContaining();

        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.ReferenceListContainingImpl <em>Reference List Containing</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.ReferenceListContainingImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getReferenceListContaining()
         * @generated
         */
        EClass REFERENCE_LIST_CONTAINING = eINSTANCE.getReferenceListContaining();

        /**
         * The meta object literal for the '<em><b>Non Containment Ref List</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST = eINSTANCE.getReferenceListContaining_NonContainmentRefList();

        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataImpl <em>Dummy Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getDummyData()
         * @generated
         */
        EClass DUMMY_DATA = eINSTANCE.getDummyData();

        /**
         * The meta object literal for the '<em><b>Dummy Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DUMMY_DATA__DUMMY_DATA = eINSTANCE.getDummyData_DummyData();

        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataContainerImpl <em>Dummy Data Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataContainerImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getDummyDataContainer()
         * @generated
         */
        EClass DUMMY_DATA_CONTAINER = eINSTANCE.getDummyDataContainer();

        /**
         * The meta object literal for the '<em><b>Dummy Data Objs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DUMMY_DATA_CONTAINER__DUMMY_DATA_OBJS = eINSTANCE.getDummyDataContainer_DummyDataObjs();

        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AttributeListContainingImpl <em>Attribute List Containing</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AttributeListContainingImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getAttributeListContaining()
         * @generated
         */
        EClass ATTRIBUTE_LIST_CONTAINING = eINSTANCE.getAttributeListContaining();

        /**
         * The meta object literal for the '<em><b>Attr List</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_LIST_CONTAINING__ATTR_LIST = eINSTANCE.getAttributeListContaining_AttrList();

        /**
         * The meta object literal for the '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl <em>Root Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl
         * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesPackageImpl#getRootContainer()
         * @generated
         */
        EClass ROOT_CONTAINER = eINSTANCE.getRootContainer();

        /**
         * The meta object literal for the '<em><b>Feature Map Containing</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROOT_CONTAINER__FEATURE_MAP_CONTAINING = eINSTANCE.getRootContainer_FeatureMapContaining();

        /**
         * The meta object literal for the '<em><b>Reference List Containing</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROOT_CONTAINER__REFERENCE_LIST_CONTAINING = eINSTANCE.getRootContainer_ReferenceListContaining();

        /**
         * The meta object literal for the '<em><b>Dummy Data Container Containing</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING = eINSTANCE.getRootContainer_DummyDataContainerContaining();

        /**
         * The meta object literal for the '<em><b>Attribute List Containing</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING = eINSTANCE.getRootContainer_AttributeListContaining();

    }

} //AdvancedFeaturesPackage
