/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import tools.vitruv.framework.change.echange.EChangePackage;

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
 * @see tools.vitruv.framework.change.echange.root.RootFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange'"
 * @generated
 */
public interface RootPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "root";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/Root/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "root";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RootPackage eINSTANCE = tools.vitruv.framework.change.echange.root.impl.RootPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.root.impl.RootEChangeImpl <em>EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.root.impl.RootEChangeImpl
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getRootEChange()
	 * @generated
	 */
	int ROOT_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE__URI = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE__RESOURCE = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE__INDEX = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE_FEATURE_COUNT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE___IS_RESOLVED = EChangePackage.ATOMIC_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE___RESOLVE__RESOURCESET = EChangePackage.ATOMIC_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE___APPLY = EChangePackage.ATOMIC_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE___REVERT = EChangePackage.ATOMIC_ECHANGE___REVERT;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE_OPERATION_COUNT = EChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl <em>Insert Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getInsertRootEObject()
	 * @generated
	 */
	int INSERT_ROOT_EOBJECT = 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT__URI = ROOT_ECHANGE__URI;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT__RESOURCE = ROOT_ECHANGE__RESOURCE;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT__INDEX = ROOT_ECHANGE__INDEX;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT__NEW_VALUE = ROOT_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Insert Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT_FEATURE_COUNT = ROOT_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___APPLY = ROOT_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___REVERT = ROOT_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___GET_NEW_VALUE = ROOT_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___IS_RESOLVED = ROOT_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET = ROOT_ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET_EOBJECT = ROOT_ECHANGE_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Insert Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT_OPERATION_COUNT = ROOT_ECHANGE_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl <em>Remove Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getRemoveRootEObject()
	 * @generated
	 */
	int REMOVE_ROOT_EOBJECT = 2;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT__URI = ROOT_ECHANGE__URI;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT__RESOURCE = ROOT_ECHANGE__RESOURCE;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT__INDEX = ROOT_ECHANGE__INDEX;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT__OLD_VALUE = ROOT_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT_FEATURE_COUNT = ROOT_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___APPLY = ROOT_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___REVERT = ROOT_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___GET_OLD_VALUE = ROOT_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___IS_RESOLVED = ROOT_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET = ROOT_ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET_EOBJECT = ROOT_ECHANGE_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Remove Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT_OPERATION_COUNT = ROOT_ECHANGE_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '<em>EObj</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EObject
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getEObj()
	 * @generated
	 */
	int EOBJ = 3;

	/**
	 * The meta object id for the '<em>Resource Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 4;

	/**
	 * The meta object id for the '<em>Resource</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.Resource
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 5;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getURI()
	 * @generated
	 */
	int URI = 6;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.root.RootEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.root.RootEChange
	 * @generated
	 */
	EClass getRootEChange();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see tools.vitruv.framework.change.echange.root.RootEChange#getUri()
	 * @see #getRootEChange()
	 * @generated
	 */
	EAttribute getRootEChange_Uri();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.root.RootEChange#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see tools.vitruv.framework.change.echange.root.RootEChange#getResource()
	 * @see #getRootEChange()
	 * @generated
	 */
	EAttribute getRootEChange_Resource();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.root.RootEChange#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see tools.vitruv.framework.change.echange.root.RootEChange#getIndex()
	 * @see #getRootEChange()
	 * @generated
	 */
	EAttribute getRootEChange_Index();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.root.InsertRootEObject <em>Insert Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert Root EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.root.InsertRootEObject
	 * @generated
	 */
	EClass getInsertRootEObject();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.InsertRootEObject#isResolved() <em>Is Resolved</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Resolved</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.InsertRootEObject#isResolved()
	 * @generated
	 */
	EOperation getInsertRootEObject__IsResolved();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.InsertRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.InsertRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getInsertRootEObject__Resolve__ResourceSet();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.InsertRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet, org.eclipse.emf.ecore.EObject) <em>Resolve</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.InsertRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getInsertRootEObject__Resolve__ResourceSet_EObject();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.root.RemoveRootEObject <em>Remove Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Root EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.root.RemoveRootEObject
	 * @generated
	 */
	EClass getRemoveRootEObject();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.RemoveRootEObject#isResolved() <em>Is Resolved</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Resolved</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.RemoveRootEObject#isResolved()
	 * @generated
	 */
	EOperation getRemoveRootEObject__IsResolved();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.RemoveRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.RemoveRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getRemoveRootEObject__Resolve__ResourceSet();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.root.RemoveRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet, org.eclipse.emf.ecore.EObject) <em>Resolve</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve</em>' operation.
	 * @see tools.vitruv.framework.change.echange.root.RemoveRootEObject#resolve(org.eclipse.emf.ecore.resource.ResourceSet, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getRemoveRootEObject__Resolve__ResourceSet_EObject();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EObject <em>EObj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EObj</em>'.
	 * @see org.eclipse.emf.ecore.EObject
	 * @model instanceClass="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EDataType getEObj();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.resource.ResourceSet <em>Resource Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource Set</em>'.
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @model instanceClass="org.eclipse.emf.ecore.resource.ResourceSet"
	 * @generated
	 */
	EDataType getResourceSet();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.resource.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource</em>'.
	 * @see org.eclipse.emf.ecore.resource.Resource
	 * @model instanceClass="org.eclipse.emf.ecore.resource.Resource"
	 * @generated
	 */
	EDataType getResource();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI"
	 * @generated
	 */
	EDataType getURI();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RootFactory getRootFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.root.impl.RootEChangeImpl <em>EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.root.impl.RootEChangeImpl
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getRootEChange()
		 * @generated
		 */
		EClass ROOT_ECHANGE = eINSTANCE.getRootEChange();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT_ECHANGE__URI = eINSTANCE.getRootEChange_Uri();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT_ECHANGE__RESOURCE = eINSTANCE.getRootEChange_Resource();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT_ECHANGE__INDEX = eINSTANCE.getRootEChange_Index();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl <em>Insert Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getInsertRootEObject()
		 * @generated
		 */
		EClass INSERT_ROOT_EOBJECT = eINSTANCE.getInsertRootEObject();

		/**
		 * The meta object literal for the '<em><b>Is Resolved</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INSERT_ROOT_EOBJECT___IS_RESOLVED = eINSTANCE.getInsertRootEObject__IsResolved();

		/**
		 * The meta object literal for the '<em><b>Resolve</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET = eINSTANCE.getInsertRootEObject__Resolve__ResourceSet();

		/**
		 * The meta object literal for the '<em><b>Resolve</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INSERT_ROOT_EOBJECT___RESOLVE__RESOURCESET_EOBJECT = eINSTANCE.getInsertRootEObject__Resolve__ResourceSet_EObject();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl <em>Remove Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getRemoveRootEObject()
		 * @generated
		 */
		EClass REMOVE_ROOT_EOBJECT = eINSTANCE.getRemoveRootEObject();

		/**
		 * The meta object literal for the '<em><b>Is Resolved</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REMOVE_ROOT_EOBJECT___IS_RESOLVED = eINSTANCE.getRemoveRootEObject__IsResolved();

		/**
		 * The meta object literal for the '<em><b>Resolve</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET = eINSTANCE.getRemoveRootEObject__Resolve__ResourceSet();

		/**
		 * The meta object literal for the '<em><b>Resolve</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REMOVE_ROOT_EOBJECT___RESOLVE__RESOURCESET_EOBJECT = eINSTANCE.getRemoveRootEObject__Resolve__ResourceSet_EObject();

		/**
		 * The meta object literal for the '<em>EObj</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EObject
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getEObj()
		 * @generated
		 */
		EDataType EOBJ = eINSTANCE.getEObj();

		/**
		 * The meta object literal for the '<em>Resource Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.ResourceSet
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getResourceSet()
		 * @generated
		 */
		EDataType RESOURCE_SET = eINSTANCE.getResourceSet();

		/**
		 * The meta object literal for the '<em>Resource</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.Resource
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getResource()
		 * @generated
		 */
		EDataType RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

	}

} //RootPackage
