/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferenceFactory
 * @model kind="package"
 * @generated
 */
public interface ReferencePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "reference";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Feature/Reference/Containment/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "reference";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ReferencePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.UpdateEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateEReference()
     * @generated
     */
    int UPDATE_EREFERENCE = 0;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE__AFFECTED_FEATURE = FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE__AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;

    /**
     * The number of structural features of the '<em>Update EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE_FEATURE_COUNT = FeaturePackage.EFEATURE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Is Containment</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE___IS_CONTAINMENT = FeaturePackage.EFEATURE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Update EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EREFERENCE_OPERATION_COUNT = FeaturePackage.EFEATURE_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl <em>Replace Single Valued EReference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getReplaceSingleValuedEReference()
     * @generated
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE = 1;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__OLD_TUID = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Feature2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__FEATURE2_OLD_VALUE_MAP = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Is Delete</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__IS_DELETE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Is Create</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE__IS_CREATE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Replace Single Valued EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 8;

    /**
     * The operation id for the '<em>Is Containment</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE___IS_CONTAINMENT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE___GET_OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 1;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE___GET_NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 2;

    /**
     * The number of operations of the '<em>Replace Single Valued EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EREFERENCE_OPERATION_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 3;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl <em>Insert EReference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getInsertEReference()
     * @generated
     */
    int INSERT_EREFERENCE = 2;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__INDEX = ListPackage.INSERT_IN_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__AFFECTED_FEATURE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__NEW_VALUE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Create</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE__IS_CREATE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Insert EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE_FEATURE_COUNT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 5;

    /**
     * The operation id for the '<em>Is Containment</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE___IS_CONTAINMENT = ListPackage.INSERT_IN_ELIST_OPERATION_COUNT + 0;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE___GET_NEW_VALUE = ListPackage.INSERT_IN_ELIST_OPERATION_COUNT + 1;

    /**
     * The number of operations of the '<em>Insert EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EREFERENCE_OPERATION_COUNT = ListPackage.INSERT_IN_ELIST_OPERATION_COUNT + 2;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl <em>Remove EReference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getRemoveEReference()
     * @generated
     */
    int REMOVE_EREFERENCE = 3;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__INDEX = ListPackage.REMOVE_FROM_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__REMOVED_OBJECT_URI_FRAGMENT = ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__AFFECTED_FEATURE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__OLD_TUID = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Feature2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__FEATURE2_OLD_VALUE_MAP = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Is Delete</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE__IS_DELETE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Remove EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE_FEATURE_COUNT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 6;

    /**
     * The operation id for the '<em>Is Containment</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE___IS_CONTAINMENT = ListPackage.REMOVE_FROM_ELIST_OPERATION_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE___GET_OLD_VALUE = ListPackage.REMOVE_FROM_ELIST_OPERATION_COUNT + 1;

    /**
     * The number of operations of the '<em>Remove EReference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EREFERENCE_OPERATION_COUNT = ListPackage.REMOVE_FROM_ELIST_OPERATION_COUNT + 2;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl <em>Permute EReference Values</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getPermuteEReferenceValues()
     * @generated
     */
    int PERMUTE_EREFERENCE_VALUES = 4;

    /**
     * The feature id for the '<em><b>New Index For Element At</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES__NEW_INDEX_FOR_ELEMENT_AT = ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES__AFFECTED_FEATURE = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES__AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Permute EReference Values</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES_FEATURE_COUNT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 3;

    /**
     * The operation id for the '<em>Is Containment</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES___IS_CONTAINMENT = ListPackage.PERMUTE_ELIST_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Permute EReference Values</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EREFERENCE_VALUES_OPERATION_COUNT = ListPackage.PERMUTE_ELIST_OPERATION_COUNT + 1;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference <em>Update EReference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference
     * @generated
     */
    EClass getUpdateEReference();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference#isContainment() <em>Is Containment</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Is Containment</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference#isContainment()
     * @generated
     */
    EOperation getUpdateEReference__IsContainment();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference <em>Replace Single Valued EReference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace Single Valued EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference
     * @generated
     */
    EClass getReplaceSingleValuedEReference();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference <em>Insert EReference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Insert EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
     * @generated
     */
    EClass getInsertEReference();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference <em>Remove EReference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference
     * @generated
     */
    EClass getRemoveEReference();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferenceValues <em>Permute EReference Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Permute EReference Values</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferenceValues
     * @generated
     */
    EClass getPermuteEReferenceValues();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ReferenceFactory getReferenceFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.UpdateEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateEReference()
         * @generated
         */
        EClass UPDATE_EREFERENCE = eINSTANCE.getUpdateEReference();

        /**
         * The meta object literal for the '<em><b>Is Containment</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation UPDATE_EREFERENCE___IS_CONTAINMENT = eINSTANCE.getUpdateEReference__IsContainment();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl <em>Replace Single Valued EReference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReplaceSingleValuedEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getReplaceSingleValuedEReference()
         * @generated
         */
        EClass REPLACE_SINGLE_VALUED_EREFERENCE = eINSTANCE.getReplaceSingleValuedEReference();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl <em>Insert EReference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.InsertEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getInsertEReference()
         * @generated
         */
        EClass INSERT_EREFERENCE = eINSTANCE.getInsertEReference();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl <em>Remove EReference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.RemoveEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getRemoveEReference()
         * @generated
         */
        EClass REMOVE_EREFERENCE = eINSTANCE.getRemoveEReference();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl <em>Permute EReference Values</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.PermuteEReferenceValuesImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl#getPermuteEReferenceValues()
         * @generated
         */
        EClass PERMUTE_EREFERENCE_VALUES = eINSTANCE.getPermuteEReferenceValues();

    }

} //ReferencePackage
