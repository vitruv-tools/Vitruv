/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListFactory
 * @model kind="package"
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
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Feature/List/1.0";

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
	ListPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl.init();

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.UpdateSingleEListEntryImpl <em>Update Single EList Entry</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.UpdateSingleEListEntryImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getUpdateSingleEListEntry()
     * @generated
     */
	int UPDATE_SINGLE_ELIST_ENTRY = 0;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_ELIST_ENTRY__INDEX = FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>Update Single EList Entry</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT = FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE_FEATURE_COUNT + 1;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.InsertInEListImpl <em>Insert In EList</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.InsertInEListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getInsertInEList()
     * @generated
     */
	int INSERT_IN_ELIST = 1;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INSERT_IN_ELIST__INDEX = UPDATE_SINGLE_ELIST_ENTRY__INDEX;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INSERT_IN_ELIST__NEW_VALUE = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>Insert In EList</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INSERT_IN_ELIST_FEATURE_COUNT = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 1;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ReplaceInEListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getReplaceInEList()
     * @generated
     */
	int REPLACE_IN_ELIST = 2;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_IN_ELIST__INDEX = UPDATE_SINGLE_ELIST_ENTRY__INDEX;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_IN_ELIST__OLD_VALUE = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_IN_ELIST__NEW_VALUE = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 1;

	/**
     * The number of structural features of the '<em>Replace In EList</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REPLACE_IN_ELIST_FEATURE_COUNT = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 2;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.RemoveFromEListImpl <em>Remove From EList</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.RemoveFromEListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getRemoveFromEList()
     * @generated
     */
	int REMOVE_FROM_ELIST = 3;

	/**
     * The feature id for the '<em><b>Index</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REMOVE_FROM_ELIST__INDEX = UPDATE_SINGLE_ELIST_ENTRY__INDEX;

	/**
     * The feature id for the '<em><b>Removed Object URI Fragment</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REMOVE_FROM_ELIST__OLD_VALUE = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 1;

	/**
     * The number of structural features of the '<em>Remove From EList</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int REMOVE_FROM_ELIST_FEATURE_COUNT = UPDATE_SINGLE_ELIST_ENTRY_FEATURE_COUNT + 2;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.PermuteEListImpl <em>Permute EList</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.PermuteEListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getPermuteEList()
     * @generated
     */
	int PERMUTE_ELIST = 4;

	/**
     * The feature id for the '<em><b>New Index For Element At</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT = FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>Permute EList</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int PERMUTE_ELIST_FEATURE_COUNT = FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE_FEATURE_COUNT + 1;


	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry <em>Update Single EList Entry</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Single EList Entry</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry
     * @generated
     */
	EClass getUpdateSingleEListEntry();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry#getIndex <em>Index</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Index</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry#getIndex()
     * @see #getUpdateSingleEListEntry()
     * @generated
     */
	EAttribute getUpdateSingleEListEntry_Index();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList <em>Insert In EList</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Insert In EList</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList
     * @generated
     */
	EClass getInsertInEList();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList#getNewValue()
     * @see #getInsertInEList()
     * @generated
     */
	EAttribute getInsertInEList_NewValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList <em>Replace In EList</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace In EList</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList
     * @generated
     */
	EClass getReplaceInEList();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList#getOldValue()
     * @see #getReplaceInEList()
     * @generated
     */
	EAttribute getReplaceInEList_OldValue();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList#getNewValue()
     * @see #getReplaceInEList()
     * @generated
     */
	EAttribute getReplaceInEList_NewValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList <em>Remove From EList</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove From EList</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList
     * @generated
     */
	EClass getRemoveFromEList();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Removed Object URI Fragment</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getRemovedObjectURIFragment()
     * @see #getRemoveFromEList()
     * @generated
     */
	EAttribute getRemoveFromEList_RemovedObjectURIFragment();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList#getOldValue()
     * @see #getRemoveFromEList()
     * @generated
     */
	EAttribute getRemoveFromEList_OldValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList <em>Permute EList</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Permute EList</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList
     * @generated
     */
	EClass getPermuteEList();

	/**
     * Returns the meta object for the attribute list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList#getNewIndexForElementAt <em>New Index For Element At</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>New Index For Element At</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList#getNewIndexForElementAt()
     * @see #getPermuteEList()
     * @generated
     */
	EAttribute getPermuteEList_NewIndexForElementAt();

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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
     * @generated
     */
	interface Literals {
		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.UpdateSingleEListEntryImpl <em>Update Single EList Entry</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.UpdateSingleEListEntryImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getUpdateSingleEListEntry()
         * @generated
         */
		EClass UPDATE_SINGLE_ELIST_ENTRY = eINSTANCE.getUpdateSingleEListEntry();

		/**
         * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute UPDATE_SINGLE_ELIST_ENTRY__INDEX = eINSTANCE.getUpdateSingleEListEntry_Index();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.InsertInEListImpl <em>Insert In EList</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.InsertInEListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getInsertInEList()
         * @generated
         */
		EClass INSERT_IN_ELIST = eINSTANCE.getInsertInEList();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute INSERT_IN_ELIST__NEW_VALUE = eINSTANCE.getInsertInEList_NewValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ReplaceInEListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getReplaceInEList()
         * @generated
         */
		EClass REPLACE_IN_ELIST = eINSTANCE.getReplaceInEList();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REPLACE_IN_ELIST__OLD_VALUE = eINSTANCE.getReplaceInEList_OldValue();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REPLACE_IN_ELIST__NEW_VALUE = eINSTANCE.getReplaceInEList_NewValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.RemoveFromEListImpl <em>Remove From EList</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.RemoveFromEListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getRemoveFromEList()
         * @generated
         */
		EClass REMOVE_FROM_ELIST = eINSTANCE.getRemoveFromEList();

		/**
         * The meta object literal for the '<em><b>Removed Object URI Fragment</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT = eINSTANCE.getRemoveFromEList_RemovedObjectURIFragment();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute REMOVE_FROM_ELIST__OLD_VALUE = eINSTANCE.getRemoveFromEList_OldValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.PermuteEListImpl <em>Permute EList</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.PermuteEListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl#getPermuteEList()
         * @generated
         */
		EClass PERMUTE_ELIST = eINSTANCE.getPermuteEList();

		/**
         * The meta object literal for the '<em><b>New Index For Element At</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT = eINSTANCE.getPermuteEList_NewIndexForElementAt();

	}

} //ListPackage
