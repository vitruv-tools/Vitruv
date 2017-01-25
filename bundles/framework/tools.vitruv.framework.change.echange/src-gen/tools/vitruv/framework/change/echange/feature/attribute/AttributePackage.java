/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

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
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange.feature'"
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
	String eNS_URI = "http://tools.vitruv/EChange/Feature/Attribute/1.0";

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
	AttributePackage eINSTANCE = tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.UpdateAttributeEChangeImpl <em>Update Attribute EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.UpdateAttributeEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getUpdateAttributeEChange()
	 * @generated
	 */
	int UPDATE_ATTRIBUTE_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_ATTRIBUTE_ECHANGE__AFFECTED_FEATURE = FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_ATTRIBUTE_ECHANGE__AFFECTED_EOBJECT = FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The number of structural features of the '<em>Update Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_ATTRIBUTE_ECHANGE_FEATURE_COUNT = FeaturePackage.FEATURE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Update Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_ATTRIBUTE_ECHANGE_OPERATION_COUNT = FeaturePackage.FEATURE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.AdditiveAttributeEChangeImpl <em>Additive Attribute EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AdditiveAttributeEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getAdditiveAttributeEChange()
	 * @generated
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE = 1;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE__AFFECTED_FEATURE = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE__AFFECTED_EOBJECT = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Additive Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE_FEATURE_COUNT = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE___GET_NEW_VALUE = EChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE;

	/**
	 * The number of operations of the '<em>Additive Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT = EChangePackage.ADDITIVE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl <em>Subtractive Attribute EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getSubtractiveAttributeEChange()
	 * @generated
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE = 2;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE__AFFECTED_FEATURE = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE__AFFECTED_EOBJECT = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Subtractive Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE_FEATURE_COUNT = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE___GET_OLD_VALUE = EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE;

	/**
	 * The number of operations of the '<em>Subtractive Attribute EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT = EChangePackage.SUBTRACTIVE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.InsertEAttributeValueImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
	 * @generated
	 */
	int INSERT_EATTRIBUTE_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE__AFFECTED_FEATURE = ListPackage.INSERT_IN_LIST_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE__AFFECTED_EOBJECT = ListPackage.INSERT_IN_LIST_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE__INDEX = ListPackage.INSERT_IN_LIST_ECHANGE__INDEX;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE__NEW_VALUE = ListPackage.INSERT_IN_LIST_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Insert EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.INSERT_IN_LIST_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE___GET_NEW_VALUE = ListPackage.INSERT_IN_LIST_ECHANGE___GET_NEW_VALUE;

	/**
	 * The number of operations of the '<em>Insert EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE_OPERATION_COUNT = ListPackage.INSERT_IN_LIST_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.RemoveEAttributeValueImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
	 * @generated
	 */
	int REMOVE_EATTRIBUTE_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE__AFFECTED_FEATURE = ListPackage.REMOVE_FROM_LIST_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE__AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_LIST_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE__INDEX = ListPackage.REMOVE_FROM_LIST_ECHANGE__INDEX;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE__OLD_VALUE = ListPackage.REMOVE_FROM_LIST_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.REMOVE_FROM_LIST_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE___GET_OLD_VALUE = ListPackage.REMOVE_FROM_LIST_ECHANGE___GET_OLD_VALUE;

	/**
	 * The number of operations of the '<em>Remove EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE_OPERATION_COUNT = ListPackage.REMOVE_FROM_LIST_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl <em>Replace Single Valued EAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
	 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getReplaceSingleValuedEAttribute()
	 * @generated
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE = 5;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE = ADDITIVE_ATTRIBUTE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_EOBJECT = ADDITIVE_ATTRIBUTE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Replace Single Valued EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE_FEATURE_COUNT = ADDITIVE_ATTRIBUTE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_NEW_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE___GET_NEW_VALUE;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE___GET_OLD_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is From Non Default Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_FROM_NON_DEFAULT_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is To Non Default Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE___IS_TO_NON_DEFAULT_VALUE = ADDITIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Replace Single Valued EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_EATTRIBUTE_OPERATION_COUNT = ADDITIVE_ATTRIBUTE_ECHANGE_OPERATION_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange <em>Update Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Attribute EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange
	 * @generated
	 */
	EClass getUpdateAttributeEChange();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange <em>Additive Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Additive Attribute EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange
	 * @generated
	 */
	EClass getAdditiveAttributeEChange();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange#getNewValue()
	 * @see #getAdditiveAttributeEChange()
	 * @generated
	 */
	EAttribute getAdditiveAttributeEChange_NewValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange <em>Subtractive Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subtractive Attribute EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
	 * @generated
	 */
	EClass getSubtractiveAttributeEChange();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange#getOldValue()
	 * @see #getSubtractiveAttributeEChange()
	 * @generated
	 */
	EAttribute getSubtractiveAttributeEChange_OldValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue <em>Insert EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert EAttribute Value</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
	 * @generated
	 */
	EClass getInsertEAttributeValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue <em>Remove EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove EAttribute Value</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
	 * @generated
	 */
	EClass getRemoveEAttributeValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute <em>Replace Single Valued EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace Single Valued EAttribute</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
	 * @generated
	 */
	EClass getReplaceSingleValuedEAttribute();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.UpdateAttributeEChangeImpl <em>Update Attribute EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.UpdateAttributeEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getUpdateAttributeEChange()
		 * @generated
		 */
		EClass UPDATE_ATTRIBUTE_ECHANGE = eINSTANCE.getUpdateAttributeEChange();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.AdditiveAttributeEChangeImpl <em>Additive Attribute EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AdditiveAttributeEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getAdditiveAttributeEChange()
		 * @generated
		 */
		EClass ADDITIVE_ATTRIBUTE_ECHANGE = eINSTANCE.getAdditiveAttributeEChange();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE = eINSTANCE.getAdditiveAttributeEChange_NewValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl <em>Subtractive Attribute EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getSubtractiveAttributeEChange()
		 * @generated
		 */
		EClass SUBTRACTIVE_ATTRIBUTE_ECHANGE = eINSTANCE.getSubtractiveAttributeEChange();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE = eINSTANCE.getSubtractiveAttributeEChange_OldValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.InsertEAttributeValueImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
		 * @generated
		 */
		EClass INSERT_EATTRIBUTE_VALUE = eINSTANCE.getInsertEAttributeValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.RemoveEAttributeValueImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
		 * @generated
		 */
		EClass REMOVE_EATTRIBUTE_VALUE = eINSTANCE.getRemoveEAttributeValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl <em>Replace Single Valued EAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
		 * @see tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl#getReplaceSingleValuedEAttribute()
		 * @generated
		 */
		EClass REPLACE_SINGLE_VALUED_EATTRIBUTE = eINSTANCE.getReplaceSingleValuedEAttribute();

	}

} //AttributePackage
