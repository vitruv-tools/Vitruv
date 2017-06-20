/** 
 */
package tools.vitruv.framework.versioning

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference

/** 
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.VersioningFactory
 * @model kind="package"
 * @generated
 */
interface VersioningPackage extends EPackage {
	/** 
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "versioning"
	/** 
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0"
	/** 
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning"
	/** 
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VersioningPackage eINSTANCE = tools.vitruv.framework.versioning.impl.VersioningPackageImpl::init()
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.NamedImpl <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.NamedImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getNamed()
	 * @generated
	 */
	int NAMED = 0
	/** 
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED__NAME = 0
	/** 
	 * The number of structural features of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_FEATURE_COUNT = 1
	/** 
	 * The number of operations of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_OPERATION_COUNT = 0
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.RootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.RootImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getRoot()
	 * @generated
	 */
	int ROOT = 1
	/** 
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__REPOSITORIES = 0
	/** 
	 * The feature id for the '<em><b>Authors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__AUTHORS = 1
	/** 
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FEATURE_COUNT = 2
	/** 
	 * The operation id for the '<em>Create Author</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT___CREATE_AUTHOR__STRING_STRING = 0
	/** 
	 * The operation id for the '<em>Create Repository</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT___CREATE_REPOSITORY = 1
	/** 
	 * The number of operations of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_OPERATION_COUNT = 2

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see tools.vitruv.framework.versioning.Named
	 * @generated
	 */
	def EClass getNamed()

	/** 
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.Named#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see tools.vitruv.framework.versioning.Named#getName()
	 * @see #getNamed()
	 * @generated
	 */
	def EAttribute getNamed_Name()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Root <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see tools.vitruv.framework.versioning.Root
	 * @generated
	 */
	def EClass getRoot()

	/** 
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.versioning.Root#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see tools.vitruv.framework.versioning.Root#getRepositories()
	 * @see #getRoot()
	 * @generated
	 */
	def EReference getRoot_Repositories()

	/** 
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.versioning.Root#getAuthors <em>Authors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authors</em>'.
	 * @see tools.vitruv.framework.versioning.Root#getAuthors()
	 * @see #getRoot()
	 * @generated
	 */
	def EReference getRoot_Authors()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.Root#createAuthor(java.lang.String, java.lang.String) <em>Create Author</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Author</em>' operation.
	 * @see tools.vitruv.framework.versioning.Root#createAuthor(java.lang.String, java.lang.String)
	 * @generated
	 */
	def EOperation getRoot__CreateAuthor__String_String()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.Root#createRepository() <em>Create Repository</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Repository</em>' operation.
	 * @see tools.vitruv.framework.versioning.Root#createRepository()
	 * @generated
	 */
	def EOperation getRoot__CreateRepository()

	/** 
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	def VersioningFactory getVersioningFactory()

	/** 
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.NamedImpl <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.NamedImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getNamed()
		 * @generated
		 */
		EClass NAMED = eINSTANCE.getNamed()
		/** 
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED__NAME = eINSTANCE.getNamed_Name()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.RootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.RootImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getRoot()
		 * @generated
		 */
		EClass ROOT = eINSTANCE.getRoot()
		/** 
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT__REPOSITORIES = eINSTANCE.getRoot_Repositories()
		/** 
		 * The meta object literal for the '<em><b>Authors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT__AUTHORS = eINSTANCE.getRoot_Authors()
		/** 
		 * The meta object literal for the '<em><b>Create Author</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ROOT___CREATE_AUTHOR__STRING_STRING = eINSTANCE.getRoot__CreateAuthor__String_String()
		/** 
		 * The meta object literal for the '<em><b>Create Repository</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ROOT___CREATE_REPOSITORY = eINSTANCE.getRoot__CreateRepository() // VersioningPackage
	}
}
