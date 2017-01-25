/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECHANGE_FEATURE_COUNT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 1;

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
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT___GET_NEW_VALUE = ROOT_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Insert Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSERT_ROOT_EOBJECT_OPERATION_COUNT = ROOT_ECHANGE_OPERATION_COUNT + 1;

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
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT___GET_OLD_VALUE = ROOT_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Remove Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_ROOT_EOBJECT_OPERATION_COUNT = ROOT_ECHANGE_OPERATION_COUNT + 1;


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
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.root.InsertRootEObject <em>Insert Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Insert Root EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.root.InsertRootEObject
	 * @generated
	 */
	EClass getInsertRootEObject();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl <em>Insert Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.root.impl.InsertRootEObjectImpl
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getInsertRootEObject()
		 * @generated
		 */
		EClass INSERT_ROOT_EOBJECT = eINSTANCE.getInsertRootEObject();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl <em>Remove Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.root.impl.RemoveRootEObjectImpl
		 * @see tools.vitruv.framework.change.echange.root.impl.RootPackageImpl#getRemoveRootEObject()
		 * @generated
		 */
		EClass REMOVE_ROOT_EOBJECT = eINSTANCE.getRemoveRootEObject();

	}

} //RootPackage
