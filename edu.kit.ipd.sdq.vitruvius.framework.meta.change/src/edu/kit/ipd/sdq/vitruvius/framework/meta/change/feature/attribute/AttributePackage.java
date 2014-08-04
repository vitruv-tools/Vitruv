/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory
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
	AttributePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateEAttributeImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateEAttribute()
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
	 * The number of structural features of the '<em>Update EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_EATTRIBUTE_FEATURE_COUNT = FeaturePackage.EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl <em>Update Single Valued EAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateSingleValuedEAttribute()
	 * @generated
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE__AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Update Single Valued EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_EATTRIBUTE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.InsertEAttributeValueImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
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
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE__NEW_VALUE = ListPackage.INSERT_IN_ELIST__NEW_VALUE;

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
	 * The number of structural features of the '<em>Insert EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.ReplaceEAttributeValueImpl <em>Replace EAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.ReplaceEAttributeValueImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getReplaceEAttributeValue()
	 * @generated
	 */
	int REPLACE_EATTRIBUTE_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE__INDEX = ListPackage.REPLACE_IN_ELIST__INDEX;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE__OLD_VALUE = ListPackage.REPLACE_IN_ELIST__OLD_VALUE;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE__NEW_VALUE = ListPackage.REPLACE_IN_ELIST__NEW_VALUE;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE__AFFECTED_FEATURE = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE__AFFECTED_EOBJECT = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Replace EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
	 * @generated
	 */
	int REMOVE_EATTRIBUTE_VALUE = 4;

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
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE__OLD_VALUE = ListPackage.REMOVE_FROM_ELIST__OLD_VALUE;

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
	 * The number of structural features of the '<em>Remove EAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_EATTRIBUTE_VALUE_FEATURE_COUNT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl <em>Permute EAttribute Values</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getPermuteEAttributeValues()
	 * @generated
	 */
	int PERMUTE_EATTRIBUTE_VALUES = 5;

	/**
	 * The feature id for the '<em><b>New Index For Element At</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_EATTRIBUTE_VALUES__NEW_INDEX_FOR_ELEMENT_AT = ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT;

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
	 * The number of structural features of the '<em>Permute EAttribute Values</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_EATTRIBUTE_VALUES_FEATURE_COUNT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute <em>Update EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update EAttribute</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute
	 * @generated
	 */
	EClass getUpdateEAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute <em>Update Single Valued EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Single Valued EAttribute</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute
	 * @generated
	 */
	EClass getUpdateSingleValuedEAttribute();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute#getOldValue()
	 * @see #getUpdateSingleValuedEAttribute()
	 * @generated
	 */
	EAttribute getUpdateSingleValuedEAttribute_OldValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute#getNewValue()
	 * @see #getUpdateSingleValuedEAttribute()
	 * @generated
	 */
	EAttribute getUpdateSingleValuedEAttribute_NewValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue <em>Insert EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert EAttribute Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue
	 * @generated
	 */
	EClass getInsertEAttributeValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.ReplaceEAttributeValue <em>Replace EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace EAttribute Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.ReplaceEAttributeValue
	 * @generated
	 */
	EClass getReplaceEAttributeValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue <em>Remove EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove EAttribute Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue
	 * @generated
	 */
	EClass getRemoveEAttributeValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.PermuteEAttributeValues <em>Permute EAttribute Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Permute EAttribute Values</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.PermuteEAttributeValues
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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateEAttributeImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateEAttribute()
		 * @generated
		 */
		EClass UPDATE_EATTRIBUTE = eINSTANCE.getUpdateEAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl <em>Update Single Valued EAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.UpdateSingleValuedEAttributeImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getUpdateSingleValuedEAttribute()
		 * @generated
		 */
		EClass UPDATE_SINGLE_VALUED_EATTRIBUTE = eINSTANCE.getUpdateSingleValuedEAttribute();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPDATE_SINGLE_VALUED_EATTRIBUTE__OLD_VALUE = eINSTANCE.getUpdateSingleValuedEAttribute_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPDATE_SINGLE_VALUED_EATTRIBUTE__NEW_VALUE = eINSTANCE.getUpdateSingleValuedEAttribute_NewValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.InsertEAttributeValueImpl <em>Insert EAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.InsertEAttributeValueImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getInsertEAttributeValue()
		 * @generated
		 */
		EClass INSERT_EATTRIBUTE_VALUE = eINSTANCE.getInsertEAttributeValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.ReplaceEAttributeValueImpl <em>Replace EAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.ReplaceEAttributeValueImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getReplaceEAttributeValue()
		 * @generated
		 */
		EClass REPLACE_EATTRIBUTE_VALUE = eINSTANCE.getReplaceEAttributeValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl <em>Remove EAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.RemoveEAttributeValueImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getRemoveEAttributeValue()
		 * @generated
		 */
		EClass REMOVE_EATTRIBUTE_VALUE = eINSTANCE.getRemoveEAttributeValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl <em>Permute EAttribute Values</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.PermuteEAttributeValuesImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl#getPermuteEAttributeValues()
		 * @generated
		 */
		EClass PERMUTE_EATTRIBUTE_VALUES = eINSTANCE.getPermuteEAttributeValues();

	}

} //AttributePackage
