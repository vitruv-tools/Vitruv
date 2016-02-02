/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import org.eclipse.emf.ecore.EClass;
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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributeFactory
 * @model kind="package"
 * @generated
 */
public interface AttributePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "attribute";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Feature/Attribute/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "attribute";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AttributePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.UpdateEAttributeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateEAttribute()
     * @generated
     */
    int UPDATE_EATTRIBUTE = 0;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EATTRIBUTE__AFFECTED_FEATURE = FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EATTRIBUTE__AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EATTRIBUTE__OLD_TUID_OF_AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__OLD_TUID_OF_AFFECTED_EOBJECT;

    /**
     * The number of structural features of the '<em>Update EAttribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EATTRIBUTE_FEATURE_COUNT = FeaturePackage.EFEATURE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Update EAttribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_EATTRIBUTE_OPERATION_COUNT = FeaturePackage.EFEATURE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl <em>Replace Single Valued EAttribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getReplaceSingleValuedEAttribute()
     * @generated
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE = 1;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_TUID_OF_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Replace Single Valued EAttribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 5;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 0;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 1;

    /**
     * The number of operations of the '<em>Replace Single Valued EAttribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_SINGLE_VALUED_EATTRIBUTE_OPERATION_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_OPERATION_COUNT + 2;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
     * @generated
     */
    int INSERT_EATTRIBUTE_VALUE = 2;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE__INDEX = ListPackage.INSERT_IN_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE__NEW_VALUE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Insert EAttribute Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 4;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE___GET_NEW_VALUE = ListPackage.INSERT_IN_ELIST_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Insert EAttribute Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_EATTRIBUTE_VALUE_OPERATION_COUNT = ListPackage.INSERT_IN_ELIST_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
     * @generated
     */
    int REMOVE_EATTRIBUTE_VALUE = 3;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__INDEX = ListPackage.REMOVE_FROM_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__REMOVED_OBJECT_URI_FRAGMENT = ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__AFFECTED_FEATURE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE__OLD_VALUE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Remove EAttribute Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 4;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE___GET_OLD_VALUE = ListPackage.REMOVE_FROM_ELIST_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Remove EAttribute Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_EATTRIBUTE_VALUE_OPERATION_COUNT = ListPackage.REMOVE_FROM_ELIST_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl <em>Permute EAttribute Values</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getPermuteEAttributeValues()
     * @generated
     */
    int PERMUTE_EATTRIBUTE_VALUES = 4;

    /**
     * The feature id for the '<em><b>Old Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES__OLD_INDEX = ListPackage.PERMUTE_ELIST__OLD_INDEX;

    /**
     * The feature id for the '<em><b>New Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES__NEW_INDEX = ListPackage.PERMUTE_ELIST__NEW_INDEX;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES__AFFECTED_FEATURE = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES__AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES__OLD_TUID_OF_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Permute EAttribute Values</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES_FEATURE_COUNT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Permute EAttribute Values</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMUTE_EATTRIBUTE_VALUES_OPERATION_COUNT = ListPackage.PERMUTE_ELIST_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateEAttribute <em>Update EAttribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EAttribute</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateEAttribute
     * @generated
     */
    EClass getUpdateEAttribute();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute <em>Replace Single Valued EAttribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace Single Valued EAttribute</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
     * @generated
     */
    EClass getReplaceSingleValuedEAttribute();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue <em>Insert EAttribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Insert EAttribute Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
     * @generated
     */
    EClass getInsertEAttributeValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue <em>Remove EAttribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove EAttribute Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
     * @generated
     */
    EClass getRemoveEAttributeValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues <em>Permute EAttribute Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Permute EAttribute Values</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues
     * @generated
     */
    EClass getPermuteEAttributeValues();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    AttributeFactory getAttributeFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.UpdateEAttributeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateEAttribute()
         * @generated
         */
        EClass UPDATE_EATTRIBUTE = eINSTANCE.getUpdateEAttribute();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl <em>Replace Single Valued EAttribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getReplaceSingleValuedEAttribute()
         * @generated
         */
        EClass REPLACE_SINGLE_VALUED_EATTRIBUTE = eINSTANCE.getReplaceSingleValuedEAttribute();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.InsertEAttributeValueImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
         * @generated
         */
        EClass INSERT_EATTRIBUTE_VALUE = eINSTANCE.getInsertEAttributeValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
         * @generated
         */
        EClass REMOVE_EATTRIBUTE_VALUE = eINSTANCE.getRemoveEAttributeValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl <em>Permute EAttribute Values</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl#getPermuteEAttributeValues()
         * @generated
         */
        EClass PERMUTE_EATTRIBUTE_VALUES = eINSTANCE.getPermuteEAttributeValues();

    }

} //AttributePackage
