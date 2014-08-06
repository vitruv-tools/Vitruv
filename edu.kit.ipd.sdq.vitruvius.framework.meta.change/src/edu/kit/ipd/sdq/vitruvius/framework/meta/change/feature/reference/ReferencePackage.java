/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory
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
	ReferencePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateEReference()
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
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_EREFERENCE__OLD_AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_EREFERENCE__NEW_AFFECTED_EOBJECT = FeaturePackage.EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT;

	/**
	 * The number of structural features of the '<em>Update EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_EREFERENCE_FEATURE_COUNT = FeaturePackage.EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateNonContainmentEReferenceImpl <em>Update Non Containment EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateNonContainmentEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateNonContainmentEReference()
	 * @generated
	 */
	int UPDATE_NON_CONTAINMENT_EREFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = UPDATE_EREFERENCE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = UPDATE_EREFERENCE__OLD_AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = UPDATE_EREFERENCE__NEW_AFFECTED_EOBJECT;

	/**
	 * The number of structural features of the '<em>Update Non Containment EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = UPDATE_EREFERENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateSingleValuedNonContainmentEReferenceImpl <em>Update Single Valued Non Containment EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateSingleValuedNonContainmentEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateSingleValuedNonContainmentEReference()
	 * @generated
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Update Single Valued Non Containment EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.InsertNonContainmentEReferenceImpl <em>Insert Non Containment EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.InsertNonContainmentEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getInsertNonContainmentEReference()
	 * @generated
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE__INDEX = ListPackage.INSERT_IN_ELIST__INDEX;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE__NEW_VALUE = ListPackage.INSERT_IN_ELIST__NEW_VALUE;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Insert Non Containment EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReplaceNonContainmentEReferenceImpl <em>Replace Non Containment EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReplaceNonContainmentEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getReplaceNonContainmentEReference()
	 * @generated
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__INDEX = ListPackage.REPLACE_IN_ELIST__INDEX;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__OLD_VALUE = ListPackage.REPLACE_IN_ELIST__OLD_VALUE;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__NEW_VALUE = ListPackage.REPLACE_IN_ELIST__NEW_VALUE;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Replace Non Containment EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = ListPackage.REPLACE_IN_ELIST_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl <em>Remove Non Containment EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getRemoveNonContainmentEReference()
	 * @generated
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE = 5;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__INDEX = ListPackage.REMOVE_FROM_ELIST__INDEX;

	/**
	 * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__REMOVED_OBJECT_URI_FRAGMENT = ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__OLD_VALUE = ListPackage.REMOVE_FROM_ELIST__OLD_VALUE;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Remove Non Containment EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.PermuteNonContainmentEReferenceValuesImpl <em>Permute Non Containment EReference Values</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.PermuteNonContainmentEReferenceValuesImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getPermuteNonContainmentEReferenceValues()
	 * @generated
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES = 6;

	/**
	 * The feature id for the '<em><b>New Index For Element At</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES__NEW_INDEX_FOR_ELEMENT_AT = ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES__AFFECTED_FEATURE = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES__OLD_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES__NEW_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Permute Non Containment EReference Values</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES_FEATURE_COUNT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference <em>Update EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference
	 * @generated
	 */
	EClass getUpdateEReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateNonContainmentEReference <em>Update Non Containment EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Non Containment EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateNonContainmentEReference
	 * @generated
	 */
	EClass getUpdateNonContainmentEReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference <em>Update Single Valued Non Containment EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Single Valued Non Containment EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference
	 * @generated
	 */
	EClass getUpdateSingleValuedNonContainmentEReference();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference#getOldValue()
	 * @see #getUpdateSingleValuedNonContainmentEReference()
	 * @generated
	 */
	EAttribute getUpdateSingleValuedNonContainmentEReference_OldValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference#getNewValue()
	 * @see #getUpdateSingleValuedNonContainmentEReference()
	 * @generated
	 */
	EAttribute getUpdateSingleValuedNonContainmentEReference_NewValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference <em>Insert Non Containment EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert Non Containment EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference
	 * @generated
	 */
	EClass getInsertNonContainmentEReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReplaceNonContainmentEReference <em>Replace Non Containment EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace Non Containment EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReplaceNonContainmentEReference
	 * @generated
	 */
	EClass getReplaceNonContainmentEReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference <em>Remove Non Containment EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Non Containment EReference</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference
	 * @generated
	 */
	EClass getRemoveNonContainmentEReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.PermuteNonContainmentEReferenceValues <em>Permute Non Containment EReference Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Permute Non Containment EReference Values</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.PermuteNonContainmentEReferenceValues
	 * @generated
	 */
	EClass getPermuteNonContainmentEReferenceValues();

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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateEReference()
		 * @generated
		 */
		EClass UPDATE_EREFERENCE = eINSTANCE.getUpdateEReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateNonContainmentEReferenceImpl <em>Update Non Containment EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateNonContainmentEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateNonContainmentEReference()
		 * @generated
		 */
		EClass UPDATE_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getUpdateNonContainmentEReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateSingleValuedNonContainmentEReferenceImpl <em>Update Single Valued Non Containment EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.UpdateSingleValuedNonContainmentEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getUpdateSingleValuedNonContainmentEReference()
		 * @generated
		 */
		EClass UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getUpdateSingleValuedNonContainmentEReference();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__OLD_VALUE = eINSTANCE.getUpdateSingleValuedNonContainmentEReference_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__NEW_VALUE = eINSTANCE.getUpdateSingleValuedNonContainmentEReference_NewValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.InsertNonContainmentEReferenceImpl <em>Insert Non Containment EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.InsertNonContainmentEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getInsertNonContainmentEReference()
		 * @generated
		 */
		EClass INSERT_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getInsertNonContainmentEReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReplaceNonContainmentEReferenceImpl <em>Replace Non Containment EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReplaceNonContainmentEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getReplaceNonContainmentEReference()
		 * @generated
		 */
		EClass REPLACE_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getReplaceNonContainmentEReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl <em>Remove Non Containment EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.RemoveNonContainmentEReferenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getRemoveNonContainmentEReference()
		 * @generated
		 */
		EClass REMOVE_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getRemoveNonContainmentEReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.PermuteNonContainmentEReferenceValuesImpl <em>Permute Non Containment EReference Values</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.PermuteNonContainmentEReferenceValuesImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl#getPermuteNonContainmentEReferenceValues()
		 * @generated
		 */
		EClass PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES = eINSTANCE.getPermuteNonContainmentEReferenceValues();

	}

} //ReferencePackage
