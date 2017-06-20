/** 
 */
package tools.vitruv.framework.versioning.repository

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.versioning.VersioningPackage

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
 * @see tools.vitruv.framework.versioning.repository.RepositoryFactory
 * @model kind="package"
 * @generated
 */
interface RepositoryPackage extends EPackage {
	/** 
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "repository"
	/** 
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/repository"
	/** 
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning.repository"
	/** 
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepositoryPackage eINSTANCE = tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl::init()
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.repository.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.repository.impl.TagImpl
	 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 0
	/** 
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__NAME = VersioningPackage::NAMED__NAME
	/** 
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__SIGNATURE = VersioningPackage::NAMED_FEATURE_COUNT + 0
	/** 
	 * The feature id for the '<em><b>Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__COMMIT = VersioningPackage::NAMED_FEATURE_COUNT + 1
	/** 
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = VersioningPackage::NAMED_FEATURE_COUNT + 2
	/** 
	 * The number of operations of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_OPERATION_COUNT = VersioningPackage::NAMED_OPERATION_COUNT + 0
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.repository.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryImpl
	 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 1
	/** 
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__TAGS = 0
	/** 
	 * The feature id for the '<em><b>Commits</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__COMMITS = 1
	/** 
	 * The feature id for the '<em><b>Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__BRANCHES = 2
	/** 
	 * The feature id for the '<em><b>Initial Commit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__INITIAL_COMMIT = 3
	/** 
	 * The feature id for the '<em><b>Master</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__MASTER = 4
	/** 
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = 5
	/** 
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_OPERATION_COUNT = 0

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.repository.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Tag
	 * @generated
	 */
	def EClass getTag()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.repository.Tag#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Commit</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Tag#getCommit()
	 * @see #getTag()
	 * @generated
	 */
	def EReference getTag_Commit()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.repository.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository
	 * @generated
	 */
	def EClass getRepository()

	/** 
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.versioning.repository.Repository#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tags</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository#getTags()
	 * @see #getRepository()
	 * @generated
	 */
	def EReference getRepository_Tags()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.repository.Repository#getCommits <em>Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository#getCommits()
	 * @see #getRepository()
	 * @generated
	 */
	def EReference getRepository_Commits()

	/** 
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.versioning.repository.Repository#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Branches</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository#getBranches()
	 * @see #getRepository()
	 * @generated
	 */
	def EReference getRepository_Branches()

	/** 
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.versioning.repository.Repository#getInitialCommit <em>Initial Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Commit</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository#getInitialCommit()
	 * @see #getRepository()
	 * @generated
	 */
	def EReference getRepository_InitialCommit()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.repository.Repository#getMaster <em>Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Master</em>'.
	 * @see tools.vitruv.framework.versioning.repository.Repository#getMaster()
	 * @see #getRepository()
	 * @generated
	 */
	def EReference getRepository_Master()

	/** 
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	def RepositoryFactory getRepositoryFactory()

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
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.repository.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.repository.impl.TagImpl
		 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag()
		/** 
		 * The meta object literal for the '<em><b>Commit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__COMMIT = eINSTANCE.getTag_Commit()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.repository.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryImpl
		 * @see tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository()
		/** 
		 * The meta object literal for the '<em><b>Tags</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__TAGS = eINSTANCE.getRepository_Tags()
		/** 
		 * The meta object literal for the '<em><b>Commits</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__COMMITS = eINSTANCE.getRepository_Commits()
		/** 
		 * The meta object literal for the '<em><b>Branches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__BRANCHES = eINSTANCE.getRepository_Branches()
		/** 
		 * The meta object literal for the '<em><b>Initial Commit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__INITIAL_COMMIT = eINSTANCE.getRepository_InitialCommit()
		/** 
		 * The meta object literal for the '<em><b>Master</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__MASTER = eINSTANCE.getRepository_Master() // RepositoryPackage
	}
}
