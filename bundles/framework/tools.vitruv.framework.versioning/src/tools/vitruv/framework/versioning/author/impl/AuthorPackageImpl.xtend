/** 
 */
package tools.vitruv.framework.versioning.author.impl

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.impl.EPackageImpl
import org.eclipse.emf.ecore.impl.EcorePackageImpl
import tools.vitruv.framework.change.echange.EChangePackage
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl
import tools.vitruv.framework.versioning.VersioningPackage
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorFactory
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature
import tools.vitruv.framework.versioning.author.Signed
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl
import tools.vitruv.framework.versioning.impl.VersioningPackageImpl
import tools.vitruv.framework.versioning.repository.RepositoryPackage
import tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class AuthorPackageImpl extends EPackageImpl implements AuthorPackage {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass authorEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass signatureEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass signedEClass = null

	/** 
	 * Creates an instance of the model <b>Package</b>, registered with{@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see tools.vitruv.framework.versioning.author.AuthorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private new() {
		super(eNS_URI, AuthorFactory.eINSTANCE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	static boolean isInited = false

	/** 
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>This method is used to initialize {@link AuthorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	def static AuthorPackage init() {
		if (isInited) return (EPackage.Registry.INSTANCE.getEPackage(AuthorPackage.eNS_URI) as AuthorPackage)
		// Obtain or create and register package
		var AuthorPackageImpl theAuthorPackage = ((if (EPackage.Registry.INSTANCE.get(
			eNS_URI) instanceof AuthorPackageImpl) EPackage.Registry.INSTANCE.get(
			eNS_URI) else new AuthorPackageImpl() ) as AuthorPackageImpl)
		isInited = true
		// Obtain or create and register interdependencies
		var VersioningPackageImpl theVersioningPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) instanceof VersioningPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) else VersioningPackage.eINSTANCE ) as VersioningPackageImpl)
		var ConflictPackageImpl theConflictPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			ConflictPackage.eNS_URI) instanceof ConflictPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			ConflictPackage.eNS_URI) else ConflictPackage.eINSTANCE ) as ConflictPackageImpl)
		var CommitPackageImpl theCommitPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) instanceof CommitPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) else CommitPackage.eINSTANCE ) as CommitPackageImpl)
		var BranchPackageImpl theBranchPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) instanceof BranchPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) else BranchPackage.eINSTANCE ) as BranchPackageImpl)
		var RepositoryPackageImpl theRepositoryPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			RepositoryPackage.eNS_URI) instanceof RepositoryPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			RepositoryPackage.eNS_URI) else RepositoryPackage.eINSTANCE ) as RepositoryPackageImpl)
		var EcorePackageImpl theEcorePackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			EcorePackage.eNS_URI) instanceof EcorePackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			EcorePackage.eNS_URI) else EcorePackage.eINSTANCE ) as EcorePackageImpl)
		var EChangePackageImpl theEChangePackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) instanceof EChangePackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) else EChangePackage.eINSTANCE ) as EChangePackageImpl)
		// Create package meta-data objects
		theAuthorPackage.createPackageContents()
		theVersioningPackage.createPackageContents()
		theConflictPackage.createPackageContents()
		theCommitPackage.createPackageContents()
		theBranchPackage.createPackageContents()
		theRepositoryPackage.createPackageContents()
		theEcorePackage.createPackageContents()
		theEChangePackage.createPackageContents()
		// Initialize created meta-data
		theAuthorPackage.initializePackageContents()
		theVersioningPackage.initializePackageContents()
		theConflictPackage.initializePackageContents()
		theCommitPackage.initializePackageContents()
		theBranchPackage.initializePackageContents()
		theRepositoryPackage.initializePackageContents()
		theEcorePackage.initializePackageContents()
		theEChangePackage.initializePackageContents()
		// Mark meta-data to indicate it can't be changed
		theAuthorPackage.freeze()
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AuthorPackage.eNS_URI, theAuthorPackage)
		return theAuthorPackage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getAuthor() {
		return authorEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getAuthor_Email() {
		return (authorEClass.getEStructuralFeatures().get(0) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getAuthor_OwnedBranches() {
		return (authorEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getAuthor_ContributedBranches() {
		return (authorEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getAuthor_Commits() {
		return (authorEClass.getEStructuralFeatures().get(3) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getAuthor_CurrentRepository() {
		return (authorEClass.getEStructuralFeatures().get(4) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getAuthor_CurrentBranch() {
		return (authorEClass.getEStructuralFeatures().get(5) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getAuthor__CreateSimpleCommit__String_Commit_EList() {
		return authorEClass.getEOperations().get(0)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getAuthor__CreateBranch__String() {
		return authorEClass.getEOperations().get(1)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getAuthor__SwitchToBranch__Branch() {
		return authorEClass.getEOperations().get(2)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getAuthor__SwitchToRepository__Repository() {
		return authorEClass.getEOperations().get(3)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getSignature() {
		return signatureEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getSignature_Signer() {
		return (signatureEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getSigned() {
		return signedEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getSigned_Signature() {
		return (signedEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override AuthorFactory getAuthorFactory() {
		return (getEFactoryInstance() as AuthorFactory)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	boolean isCreated = false

	/** 
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def void createPackageContents() {
		if (isCreated) return;
		isCreated = true
		// Create classes and their features
		authorEClass = createEClass(AUTHOR)
		createEAttribute(authorEClass, AUTHOR__EMAIL)
		createEReference(authorEClass, AUTHOR__OWNED_BRANCHES)
		createEReference(authorEClass, AUTHOR__CONTRIBUTED_BRANCHES)
		createEReference(authorEClass, AUTHOR__COMMITS)
		createEReference(authorEClass, AUTHOR__CURRENT_REPOSITORY)
		createEReference(authorEClass, AUTHOR__CURRENT_BRANCH)
		createEOperation(authorEClass, AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST)
		createEOperation(authorEClass, AUTHOR___CREATE_BRANCH__STRING)
		createEOperation(authorEClass, AUTHOR___SWITCH_TO_BRANCH__BRANCH)
		createEOperation(authorEClass, AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY)
		signatureEClass = createEClass(SIGNATURE)
		createEReference(signatureEClass, SIGNATURE__SIGNER)
		signedEClass = createEClass(SIGNED)
		createEReference(signedEClass, SIGNED__SIGNATURE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	boolean isInitialized = false

	/** 
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true
		// Initialize package
		setName(eNAME)
		setNsPrefix(eNS_PREFIX)
		setNsURI(eNS_URI)
		// Obtain other dependent packages
		var VersioningPackage theVersioningPackage = (EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) as VersioningPackage)
		var BranchPackage theBranchPackage = (EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) as BranchPackage)
		var CommitPackage theCommitPackage = (EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) as CommitPackage)
		var RepositoryPackage theRepositoryPackage = (EPackage.Registry.INSTANCE.getEPackage(
			RepositoryPackage.eNS_URI) as RepositoryPackage)
		var EChangePackage theEChangePackage = (EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) as EChangePackage)
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		authorEClass.getESuperTypes().add(theVersioningPackage.getNamed())
		// Initialize classes, features, and operations; add parameters
		initEClass(authorEClass, Author, "Author", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEAttribute(getAuthor_Email(), ecorePackage.getEString(), "email", null, 1, 1, Author, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getAuthor_OwnedBranches(), theBranchPackage.getUserBranch(),
			theBranchPackage.getUserBranch_Owner(), "ownedBranches", null, 0, -1, Author, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getAuthor_ContributedBranches(), theBranchPackage.getBranch(),
			theBranchPackage.getBranch_Contributors(), "contributedBranches", null, 0, -1, Author, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED)
			initEReference(getAuthor_Commits(), theCommitPackage.getCommit(), null, "commits", null, 0, -1, Author,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEReference(getAuthor_CurrentRepository(), theRepositoryPackage.getRepository(), null,
				"currentRepository", null, 0, 1, Author, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEReference(getAuthor_CurrentBranch(), theBranchPackage.getBranch(), null, "currentBranch", null, 0, 1,
				Author, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			var EOperation op = initEOperation(getAuthor__CreateSimpleCommit__String_Commit_EList(),
				theCommitPackage.getSimpleCommit(), "createSimpleCommit", 0, 1, IS_UNIQUE, IS_ORDERED)
			addEParameter(op, ecorePackage.getEString(), "message", 1, 1, IS_UNIQUE, IS_ORDERED)
			addEParameter(op, theCommitPackage.getCommit(), "parent", 1, 1, IS_UNIQUE, IS_ORDERED)
			addEParameter(op, theEChangePackage.getEChange(), "changes", 0, -1, IS_UNIQUE, IS_ORDERED)
			op = initEOperation(getAuthor__CreateBranch__String(), theBranchPackage.getUserBranch(), "createBranch", 0,
				1, IS_UNIQUE, IS_ORDERED)
			addEParameter(op, ecorePackage.getEString(), "branchName", 1, 1, IS_UNIQUE, IS_ORDERED)
			op = initEOperation(getAuthor__SwitchToBranch__Branch(), null, "switchToBranch", 0, 1, IS_UNIQUE,
				IS_ORDERED)
			addEParameter(op, theBranchPackage.getBranch(), "targetBranch", 1, 1, IS_UNIQUE, IS_ORDERED)
			op = initEOperation(getAuthor__SwitchToRepository__Repository(), null, "switchToRepository", 0, 1,
				IS_UNIQUE, IS_ORDERED)
			addEParameter(op, theRepositoryPackage.getRepository(), "repository", 1, 1, IS_UNIQUE, IS_ORDERED)
			initEClass(signatureEClass, Signature, "Signature", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS)
			initEReference(getSignature_Signer(), this.getAuthor(), null, "signer", null, 1, 1, Signature,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEClass(signedEClass, Signed, "Signed", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
			initEReference(getSigned_Signature(), this.getSignature(), null, "signature", null, 0, 1, Signed,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		} // AuthorPackageImpl
	}
	