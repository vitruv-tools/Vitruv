/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory
 * @model kind="package"
 * @generated
 */
public interface ContainmentPackage extends EPackage {
	/**
     * The package name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNAME = "containment";

	/**
     * The package namespace URI.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Reference/Containment/1.0";

	/**
     * The package namespace name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_PREFIX = "containment";

	/**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	ContainmentPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl.init();

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateContainmentEReferenceImpl <em>Update Containment EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateContainmentEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getUpdateContainmentEReference()
     * @generated
     */
	int UPDATE_CONTAINMENT_EREFERENCE = 0;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = ReferencePackage.UPDATE_EREFERENCE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = ReferencePackage.UPDATE_EREFERENCE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = ReferencePackage.UPDATE_EREFERENCE__NEW_AFFECTED_EOBJECT;

	/**
     * The number of structural features of the '<em>Update Containment EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_CONTAINMENT_EREFERENCE_FEATURE_COUNT = ReferencePackage.UPDATE_EREFERENCE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateSingleValuedContainmentEReferenceImpl <em>Update Single Valued Containment EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateSingleValuedContainmentEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getUpdateSingleValuedContainmentEReference()
     * @generated
     */
	int UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE = 1;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Update Single Valued Containment EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT + 3;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectSingleImpl <em>Create Non Root EObject Single</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectSingleImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getCreateNonRootEObjectSingle()
     * @generated
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE = 2;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE__NEW_VALUE = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The number of structural features of the '<em>Create Non Root EObject Single</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_SINGLE_FEATURE_COUNT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectSingleImpl <em>Replace Non Root EObject Single</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectSingleImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getReplaceNonRootEObjectSingle()
     * @generated
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE = 3;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE__NEW_VALUE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The number of structural features of the '<em>Replace Non Root EObject Single</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_SINGLE_FEATURE_COUNT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 5;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl <em>Delete Non Root EObject Single</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getDeleteNonRootEObjectSingle()
     * @generated
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE = 4;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE__AFFECTED_FEATURE = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_AFFECTED_EOBJECT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE__NEW_AFFECTED_EOBJECT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The number of structural features of the '<em>Delete Non Root EObject Single</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_SINGLE_FEATURE_COUNT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectInListImpl <em>Create Non Root EObject In List</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectInListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getCreateNonRootEObjectInList()
     * @generated
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST = 5;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST__INDEX = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST__OLD_AFFECTED_EOBJECT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST__NEW_AFFECTED_EOBJECT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The number of structural features of the '<em>Create Non Root EObject In List</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_IN_LIST_FEATURE_COUNT = ObjectPackage.CREATE_EOBJECT_FEATURE_COUNT + 5;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl <em>Replace Non Root EObject In List</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getReplaceNonRootEObjectInList()
     * @generated
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST = 6;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_AFFECTED_EOBJECT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_AFFECTED_EOBJECT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 5;

	/**
     * The number of structural features of the '<em>Replace Non Root EObject In List</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_NON_ROOT_EOBJECT_IN_LIST_FEATURE_COUNT = ObjectPackage.REPLACE_EOBJECT_FEATURE_COUNT + 6;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl <em>Delete Non Root EObject In List</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getDeleteNonRootEObjectInList()
     * @generated
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST = 7;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_AFFECTED_EOBJECT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 4;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST__NEW_AFFECTED_EOBJECT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 5;

	/**
     * The number of structural features of the '<em>Delete Non Root EObject In List</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_IN_LIST_FEATURE_COUNT = ObjectPackage.DELETE_EOBJECT_FEATURE_COUNT + 6;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.PermuteContainmentEReferenceValuesImpl <em>Permute Containment EReference Values</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.PermuteContainmentEReferenceValuesImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getPermuteContainmentEReferenceValues()
     * @generated
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES = 8;

	/**
     * The feature id for the '<em><b>New Index For Element At</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES__NEW_INDEX_FOR_ELEMENT_AT = ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES__AFFECTED_FEATURE = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES__OLD_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES__NEW_AFFECTED_EOBJECT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Permute Containment EReference Values</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_CONTAINMENT_EREFERENCE_VALUES_FEATURE_COUNT = ListPackage.PERMUTE_ELIST_FEATURE_COUNT + 3;


	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.InsertNonRootEObjectInContainmentListImpl <em>Insert Non Root EObject In Containment List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.InsertNonRootEObjectInContainmentListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getInsertNonRootEObjectInContainmentList()
     * @generated
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST = 9;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST__INDEX = ListPackage.INSERT_IN_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST__NEW_VALUE = ListPackage.INSERT_IN_ELIST__NEW_VALUE;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST__AFFECTED_FEATURE = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST__OLD_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST__NEW_AFFECTED_EOBJECT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Insert Non Root EObject In Containment List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST_FEATURE_COUNT = ListPackage.INSERT_IN_ELIST_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.RemoveNonRootEObjectFromContainmentListImpl <em>Remove Non Root EObject From Containment List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.RemoveNonRootEObjectFromContainmentListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getRemoveNonRootEObjectFromContainmentList()
     * @generated
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST = 10;

    /**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__INDEX = ListPackage.REMOVE_FROM_ELIST__INDEX;

    /**
     * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__REMOVED_OBJECT_URI_FRAGMENT = ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__OLD_VALUE = ListPackage.REMOVE_FROM_ELIST__OLD_VALUE;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__AFFECTED_FEATURE = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__OLD_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST__NEW_AFFECTED_EOBJECT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Remove Non Root EObject From Containment List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST_FEATURE_COUNT = ListPackage.REMOVE_FROM_ELIST_FEATURE_COUNT + 3;

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference <em>Update Containment EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Containment EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference
     * @generated
     */
	EClass getUpdateContainmentEReference();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateSingleValuedContainmentEReference <em>Update Single Valued Containment EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Single Valued Containment EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateSingleValuedContainmentEReference
     * @generated
     */
	EClass getUpdateSingleValuedContainmentEReference();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle <em>Create Non Root EObject Single</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Non Root EObject Single</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle
     * @generated
     */
	EClass getCreateNonRootEObjectSingle();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle#getNewValue()
     * @see #getCreateNonRootEObjectSingle()
     * @generated
     */
	EAttribute getCreateNonRootEObjectSingle_NewValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle <em>Replace Non Root EObject Single</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace Non Root EObject Single</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle
     * @generated
     */
	EClass getReplaceNonRootEObjectSingle();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getOldValue()
     * @see #getReplaceNonRootEObjectSingle()
     * @generated
     */
	EAttribute getReplaceNonRootEObjectSingle_OldValue();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle#getNewValue()
     * @see #getReplaceNonRootEObjectSingle()
     * @generated
     */
	EAttribute getReplaceNonRootEObjectSingle_NewValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle <em>Delete Non Root EObject Single</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Non Root EObject Single</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle
     * @generated
     */
	EClass getDeleteNonRootEObjectSingle();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle#getOldValue()
     * @see #getDeleteNonRootEObjectSingle()
     * @generated
     */
	EAttribute getDeleteNonRootEObjectSingle_OldValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList <em>Create Non Root EObject In List</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Non Root EObject In List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
     * @generated
     */
	EClass getCreateNonRootEObjectInList();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList <em>Replace Non Root EObject In List</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace Non Root EObject In List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList
     * @generated
     */
	EClass getReplaceNonRootEObjectInList();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList <em>Delete Non Root EObject In List</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Non Root EObject In List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
     * @generated
     */
	EClass getDeleteNonRootEObjectInList();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues <em>Permute Containment EReference Values</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Permute Containment EReference Values</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues
     * @generated
     */
	EClass getPermuteContainmentEReferenceValues();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList <em>Insert Non Root EObject In Containment List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Insert Non Root EObject In Containment List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList
     * @generated
     */
    EClass getInsertNonRootEObjectInContainmentList();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.RemoveNonRootEObjectFromContainmentList <em>Remove Non Root EObject From Containment List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove Non Root EObject From Containment List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.RemoveNonRootEObjectFromContainmentList
     * @generated
     */
    EClass getRemoveNonRootEObjectFromContainmentList();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
	ContainmentFactory getContainmentFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateContainmentEReferenceImpl <em>Update Containment EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateContainmentEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getUpdateContainmentEReference()
         * @generated
         */
		EClass UPDATE_CONTAINMENT_EREFERENCE = eINSTANCE.getUpdateContainmentEReference();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateSingleValuedContainmentEReferenceImpl <em>Update Single Valued Containment EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.UpdateSingleValuedContainmentEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getUpdateSingleValuedContainmentEReference()
         * @generated
         */
		EClass UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE = eINSTANCE.getUpdateSingleValuedContainmentEReference();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectSingleImpl <em>Create Non Root EObject Single</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectSingleImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getCreateNonRootEObjectSingle()
         * @generated
         */
		EClass CREATE_NON_ROOT_EOBJECT_SINGLE = eINSTANCE.getCreateNonRootEObjectSingle();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CREATE_NON_ROOT_EOBJECT_SINGLE__NEW_VALUE = eINSTANCE.getCreateNonRootEObjectSingle_NewValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectSingleImpl <em>Replace Non Root EObject Single</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectSingleImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getReplaceNonRootEObjectSingle()
         * @generated
         */
		EClass REPLACE_NON_ROOT_EOBJECT_SINGLE = eINSTANCE.getReplaceNonRootEObjectSingle();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REPLACE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE = eINSTANCE.getReplaceNonRootEObjectSingle_OldValue();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REPLACE_NON_ROOT_EOBJECT_SINGLE__NEW_VALUE = eINSTANCE.getReplaceNonRootEObjectSingle_NewValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl <em>Delete Non Root EObject Single</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectSingleImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getDeleteNonRootEObjectSingle()
         * @generated
         */
		EClass DELETE_NON_ROOT_EOBJECT_SINGLE = eINSTANCE.getDeleteNonRootEObjectSingle();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute DELETE_NON_ROOT_EOBJECT_SINGLE__OLD_VALUE = eINSTANCE.getDeleteNonRootEObjectSingle_OldValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectInListImpl <em>Create Non Root EObject In List</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.CreateNonRootEObjectInListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getCreateNonRootEObjectInList()
         * @generated
         */
		EClass CREATE_NON_ROOT_EOBJECT_IN_LIST = eINSTANCE.getCreateNonRootEObjectInList();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl <em>Replace Non Root EObject In List</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getReplaceNonRootEObjectInList()
         * @generated
         */
		EClass REPLACE_NON_ROOT_EOBJECT_IN_LIST = eINSTANCE.getReplaceNonRootEObjectInList();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl <em>Delete Non Root EObject In List</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getDeleteNonRootEObjectInList()
         * @generated
         */
		EClass DELETE_NON_ROOT_EOBJECT_IN_LIST = eINSTANCE.getDeleteNonRootEObjectInList();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.PermuteContainmentEReferenceValuesImpl <em>Permute Containment EReference Values</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.PermuteContainmentEReferenceValuesImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getPermuteContainmentEReferenceValues()
         * @generated
         */
		EClass PERMUTE_CONTAINMENT_EREFERENCE_VALUES = eINSTANCE.getPermuteContainmentEReferenceValues();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.InsertNonRootEObjectInContainmentListImpl <em>Insert Non Root EObject In Containment List</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.InsertNonRootEObjectInContainmentListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getInsertNonRootEObjectInContainmentList()
         * @generated
         */
        EClass INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST = eINSTANCE.getInsertNonRootEObjectInContainmentList();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.RemoveNonRootEObjectFromContainmentListImpl <em>Remove Non Root EObject From Containment List</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.RemoveNonRootEObjectFromContainmentListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl#getRemoveNonRootEObjectFromContainmentList()
         * @generated
         */
        EClass REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST = eINSTANCE.getRemoveNonRootEObjectFromContainmentList();

	}

} //ContainmentPackage
