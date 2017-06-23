/** 
 */
package tools.vitruv.framework.versioning.author

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
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
 * @see tools.vitruv.framework.versioning.author.AuthorFactory
 * @model kind="package"
 * @generated
 */
interface AuthorPackage extends EPackage {
	/** 
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "author"
	/** 
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/author"
	/** 
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning.author"
	/** 
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AuthorPackage eINSTANCE = tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl::init()
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl <em>Author</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.impl.AuthorImpl
	 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getAuthor()
	 * @generated
	 */
	int AUTHOR = 0
	/** 
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__NAME = VersioningPackage::NAMED__NAME
	/** 
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__EMAIL = VersioningPackage::NAMED_FEATURE_COUNT + 0
	/** 
	 * The feature id for the '<em><b>Owned Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__OWNED_BRANCHES = VersioningPackage::NAMED_FEATURE_COUNT + 1
	/** 
	 * The feature id for the '<em><b>Contributed Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__CONTRIBUTED_BRANCHES = VersioningPackage::NAMED_FEATURE_COUNT + 2
	/** 
	 * The feature id for the '<em><b>Commits</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__COMMITS = VersioningPackage::NAMED_FEATURE_COUNT + 3
	/** 
	 * The feature id for the '<em><b>Current Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__CURRENT_REPOSITORY = VersioningPackage::NAMED_FEATURE_COUNT + 4
	/** 
	 * The feature id for the '<em><b>Current Branch</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__CURRENT_BRANCH = VersioningPackage::NAMED_FEATURE_COUNT + 5
	/** 
	 * The number of structural features of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_FEATURE_COUNT = VersioningPackage::NAMED_FEATURE_COUNT + 6
	/** 
	 * The operation id for the '<em>Create Simple Commit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST = VersioningPackage::NAMED_OPERATION_COUNT + 0
	/** 
	 * The operation id for the '<em>Create Branch</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR___CREATE_BRANCH__STRING = VersioningPackage::NAMED_OPERATION_COUNT + 1
	/** 
	 * The operation id for the '<em>Switch To Branch</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR___SWITCH_TO_BRANCH__BRANCH = VersioningPackage::NAMED_OPERATION_COUNT + 2
	/** 
	 * The operation id for the '<em>Switch To Repository</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY = VersioningPackage::NAMED_OPERATION_COUNT + 3
	/** 
	 * The number of operations of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_OPERATION_COUNT = VersioningPackage::NAMED_OPERATION_COUNT + 4
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.author.impl.SignatureImpl <em>Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.impl.SignatureImpl
	 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getSignature()
	 * @generated
	 */
	int SIGNATURE = 1
	/** 
	 * The feature id for the '<em><b>Signer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__SIGNER = 0
	/** 
	 * The number of structural features of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_FEATURE_COUNT = 1
	/** 
	 * The number of operations of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_OPERATION_COUNT = 0
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.author.impl.SignedImpl <em>Signed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.impl.SignedImpl
	 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getSigned()
	 * @generated
	 */
	int SIGNED = 2
	/** 
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED__SIGNATURE = 0
	/** 
	 * The number of structural features of the '<em>Signed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED_FEATURE_COUNT = 1
	/** 
	 * The number of operations of the '<em>Signed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED_OPERATION_COUNT = 0

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.author.Author <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Author</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author
	 * @generated
	 */
	def EClass getAuthor()

	/** 
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.author.Author#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getEmail()
	 * @see #getAuthor()
	 * @generated
	 */
	def EAttribute getAuthor_Email()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.author.Author#getOwnedBranches <em>Owned Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Branches</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getOwnedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	def EReference getAuthor_OwnedBranches()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.author.Author#getContributedBranches <em>Contributed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contributed Branches</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getContributedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	def EReference getAuthor_ContributedBranches()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.author.Author#getCommits <em>Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getCommits()
	 * @see #getAuthor()
	 * @generated
	 */
	def EReference getAuthor_Commits()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.author.Author#getCurrentRepository <em>Current Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Repository</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getCurrentRepository()
	 * @see #getAuthor()
	 * @generated
	 */
	def EReference getAuthor_CurrentRepository()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.author.Author#getCurrentBranch <em>Current Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Branch</em>'.
	 * @see tools.vitruv.framework.versioning.author.Author#getCurrentBranch()
	 * @see #getAuthor()
	 * @generated
	 */
	def EReference getAuthor_CurrentBranch()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.author.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Simple Commit</em>' operation.
	 * @see tools.vitruv.framework.versioning.author.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	def EOperation getAuthor__CreateSimpleCommit__String_Commit_EList()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.author.Author#createBranch(java.lang.String) <em>Create Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Branch</em>' operation.
	 * @see tools.vitruv.framework.versioning.author.Author#createBranch(java.lang.String)
	 * @generated
	 */
	def EOperation getAuthor__CreateBranch__String()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.author.Author#switchToBranch(tools.vitruv.framework.versioning.branch.Branch) <em>Switch To Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Switch To Branch</em>' operation.
	 * @see tools.vitruv.framework.versioning.author.Author#switchToBranch(tools.vitruv.framework.versioning.branch.Branch)
	 * @generated
	 */
	def EOperation getAuthor__SwitchToBranch__Branch()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.author.Author#switchToRepository(tools.vitruv.framework.versioning.repository.Repository) <em>Switch To Repository</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Switch To Repository</em>' operation.
	 * @see tools.vitruv.framework.versioning.author.Author#switchToRepository(tools.vitruv.framework.versioning.repository.Repository)
	 * @generated
	 */
	def EOperation getAuthor__SwitchToRepository__Repository()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.author.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature</em>'.
	 * @see tools.vitruv.framework.versioning.author.Signature
	 * @generated
	 */
	def EClass getSignature()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.author.Signature#getSigner <em>Signer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signer</em>'.
	 * @see tools.vitruv.framework.versioning.author.Signature#getSigner()
	 * @see #getSignature()
	 * @generated
	 */
	def EReference getSignature_Signer()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.author.Signed <em>Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signed</em>'.
	 * @see tools.vitruv.framework.versioning.author.Signed
	 * @generated
	 */
	def EClass getSigned()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.author.Signed#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signature</em>'.
	 * @see tools.vitruv.framework.versioning.author.Signed#getSignature()
	 * @see #getSigned()
	 * @generated
	 */
	def EReference getSigned_Signature()

	/** 
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	def AuthorFactory getAuthorFactory()

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
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.author.impl.AuthorImpl <em>Author</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.author.impl.AuthorImpl
		 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getAuthor()
		 * @generated
		 */
		EClass AUTHOR = eINSTANCE.getAuthor()
		/** 
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__EMAIL = eINSTANCE.getAuthor_Email()
		/** 
		 * The meta object literal for the '<em><b>Owned Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__OWNED_BRANCHES = eINSTANCE.getAuthor_OwnedBranches()
		/** 
		 * The meta object literal for the '<em><b>Contributed Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__CONTRIBUTED_BRANCHES = eINSTANCE.getAuthor_ContributedBranches()
		/** 
		 * The meta object literal for the '<em><b>Commits</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__COMMITS = eINSTANCE.getAuthor_Commits()
		/** 
		 * The meta object literal for the '<em><b>Current Repository</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__CURRENT_REPOSITORY = eINSTANCE.getAuthor_CurrentRepository()
		/** 
		 * The meta object literal for the '<em><b>Current Branch</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__CURRENT_BRANCH = eINSTANCE.getAuthor_CurrentBranch()
		/** 
		 * The meta object literal for the '<em><b>Create Simple Commit</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST = eINSTANCE.
			getAuthor__CreateSimpleCommit__String_Commit_EList()
		/** 
		 * The meta object literal for the '<em><b>Create Branch</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUTHOR___CREATE_BRANCH__STRING = eINSTANCE.getAuthor__CreateBranch__String()
		/** 
		 * The meta object literal for the '<em><b>Switch To Branch</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUTHOR___SWITCH_TO_BRANCH__BRANCH = eINSTANCE.getAuthor__SwitchToBranch__Branch()
		/** 
		 * The meta object literal for the '<em><b>Switch To Repository</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY = eINSTANCE.getAuthor__SwitchToRepository__Repository()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.author.impl.SignatureImpl <em>Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.author.impl.SignatureImpl
		 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getSignature()
		 * @generated
		 */
		EClass SIGNATURE = eINSTANCE.getSignature()
		/** 
		 * The meta object literal for the '<em><b>Signer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__SIGNER = eINSTANCE.getSignature_Signer()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.author.impl.SignedImpl <em>Signed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.author.impl.SignedImpl
		 * @see tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl#getSigned()
		 * @generated
		 */
		EClass SIGNED = eINSTANCE.getSigned()
		/** 
		 * The meta object literal for the '<em><b>Signature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNED__SIGNATURE = eINSTANCE.getSigned_Signature() // AuthorPackage
	}
}
