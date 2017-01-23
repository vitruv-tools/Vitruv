/**
 */
package tools.vitruv.framework.change.echange.feature.list;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

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
 * @see tools.vitruv.framework.change.echange.feature.list.ListFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange.feature'"
 * @generated
 */
public interface ListPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "list";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/Feature/List/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "list";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ListPackage eINSTANCE = tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.UpdateSingleListEntryEChangeImpl <em>Update Single List Entry EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.UpdateSingleListEntryEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getUpdateSingleListEntryEChange()
	 * @generated
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_FEATURE = FeaturePackage.UPDATE_MULTI_VALUED_FEATURE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_EOBJECT = FeaturePackage.UPDATE_MULTI_VALUED_FEATURE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX = FeaturePackage.UPDATE_MULTI_VALUED_FEATURE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Update Single List Entry EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE_FEATURE_COUNT = FeaturePackage.UPDATE_MULTI_VALUED_FEATURE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Update Single List Entry EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_LIST_ENTRY_ECHANGE_OPERATION_COUNT = FeaturePackage.UPDATE_MULTI_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl <em>Insert In List EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getInsertInListEChange()
	 * @generated
	 */
	int INSERT_IN_LIST_ECHANGE = 1;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE__AFFECTED_FEATURE = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE__AFFECTED_EOBJECT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE__INDEX = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX;

	/**
	 * The number of structural features of the '<em>Insert In List EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE_FEATURE_COUNT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE___GET_NEW_VALUE = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Insert In List EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_IN_LIST_ECHANGE_OPERATION_COUNT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl <em>Remove From List EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getRemoveFromListEChange()
	 * @generated
	 */
	int REMOVE_FROM_LIST_ECHANGE = 2;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE__AFFECTED_FEATURE = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE__AFFECTED_EOBJECT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE__INDEX = UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX;

	/**
	 * The number of structural features of the '<em>Remove From List EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE_FEATURE_COUNT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE___GET_OLD_VALUE = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Remove From List EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_FROM_LIST_ECHANGE_OPERATION_COUNT = UPDATE_SINGLE_LIST_ENTRY_ECHANGE_OPERATION_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange <em>Update Single List Entry EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Single List Entry EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
	 * @generated
	 */
	EClass getUpdateSingleListEntryEChange();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange#getIndex()
	 * @see #getUpdateSingleListEntryEChange()
	 * @generated
	 */
	EAttribute getUpdateSingleListEntryEChange_Index();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.list.InsertInListEChange <em>Insert In List EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert In List EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
	 * @generated
	 */
	EClass getInsertInListEChange();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange <em>Remove From List EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove From List EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
	 * @generated
	 */
	EClass getRemoveFromListEChange();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ListFactory getListFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.UpdateSingleListEntryEChangeImpl <em>Update Single List Entry EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.UpdateSingleListEntryEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getUpdateSingleListEntryEChange()
		 * @generated
		 */
		EClass UPDATE_SINGLE_LIST_ENTRY_ECHANGE = eINSTANCE.getUpdateSingleListEntryEChange();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX = eINSTANCE.getUpdateSingleListEntryEChange_Index();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl <em>Insert In List EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.InsertInListEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getInsertInListEChange()
		 * @generated
		 */
		EClass INSERT_IN_LIST_ECHANGE = eINSTANCE.getInsertInListEChange();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl <em>Remove From List EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.RemoveFromListEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl#getRemoveFromListEChange()
		 * @generated
		 */
		EClass REMOVE_FROM_LIST_ECHANGE = eINSTANCE.getRemoveFromListEChange();

	}

} //ListPackage
