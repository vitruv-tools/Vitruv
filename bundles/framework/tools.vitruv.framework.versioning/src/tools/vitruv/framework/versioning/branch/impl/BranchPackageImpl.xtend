/** 
 */
package tools.vitruv.framework.versioning.branch.impl

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
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchDiffCreator
import tools.vitruv.framework.versioning.branch.BranchFactory
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.MasterBranch
import tools.vitruv.framework.versioning.branch.UserBranch
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
class BranchPackageImpl extends EPackageImpl implements BranchPackage {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass branchDiffCreatorEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass branchDiffEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass userBranchEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass masterBranchEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass branchEClass = null

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
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private new() {
		super(eNS_URI, BranchFactory.eINSTANCE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	static boolean isInited = false

	/** 
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>This method is used to initialize {@link BranchPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	def static BranchPackage init() {
		if (isInited) return (EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) as BranchPackage)
		// Obtain or create and register package
		var BranchPackageImpl theBranchPackage = ((if (EPackage.Registry.INSTANCE.get(
			eNS_URI) instanceof BranchPackageImpl) EPackage.Registry.INSTANCE.get(
			eNS_URI) else new BranchPackageImpl() ) as BranchPackageImpl)
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
		var AuthorPackageImpl theAuthorPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) instanceof AuthorPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) else AuthorPackage.eINSTANCE ) as AuthorPackageImpl)
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
		theBranchPackage.createPackageContents()
		theVersioningPackage.createPackageContents()
		theConflictPackage.createPackageContents()
		theCommitPackage.createPackageContents()
		theAuthorPackage.createPackageContents()
		theRepositoryPackage.createPackageContents()
		theEcorePackage.createPackageContents()
		theEChangePackage.createPackageContents()
		// Initialize created meta-data
		theBranchPackage.initializePackageContents()
		theVersioningPackage.initializePackageContents()
		theConflictPackage.initializePackageContents()
		theCommitPackage.initializePackageContents()
		theAuthorPackage.initializePackageContents()
		theRepositoryPackage.initializePackageContents()
		theEcorePackage.initializePackageContents()
		theEChangePackage.initializePackageContents()
		// Mark meta-data to indicate it can't be changed
		theBranchPackage.freeze()
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BranchPackage.eNS_URI, theBranchPackage)
		return theBranchPackage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getBranchDiffCreator() {
		return branchDiffCreatorEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiffCreator_Source() {
		return (branchDiffCreatorEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiffCreator_Target() {
		return (branchDiffCreatorEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getBranchDiffCreator__CreateVersionDiff() {
		return branchDiffCreatorEClass.getEOperations().get(0)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getBranchDiff() {
		return branchDiffEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiff_SourceCommits() {
		return (branchDiffEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiff_TargetCommits() {
		return (branchDiffEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiff_Source() {
		return (branchDiffEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiff_Target() {
		return (branchDiffEClass.getEStructuralFeatures().get(3) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranchDiff_LastCommonAncestor() {
		return (branchDiffEClass.getEStructuralFeatures().get(4) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getUserBranch() {
		return userBranchEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getUserBranch_Owner() {
		return (userBranchEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getUserBranch_BranchedFrom() {
		return (userBranchEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getMasterBranch() {
		return masterBranchEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getBranch() {
		return branchEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranch_CurrentHeadCommit() {
		return (branchEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranch_Contributors() {
		return (branchEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getBranch_ChildBranches() {
		return (branchEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override BranchFactory getBranchFactory() {
		return (getEFactoryInstance() as BranchFactory)
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
		branchDiffCreatorEClass = createEClass(BRANCH_DIFF_CREATOR)
		createEReference(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR__SOURCE)
		createEReference(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR__TARGET)
		createEOperation(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR___CREATE_VERSION_DIFF)
		branchDiffEClass = createEClass(BRANCH_DIFF)
		createEReference(branchDiffEClass, BRANCH_DIFF__SOURCE_COMMITS)
		createEReference(branchDiffEClass, BRANCH_DIFF__TARGET_COMMITS)
		createEReference(branchDiffEClass, BRANCH_DIFF__SOURCE)
		createEReference(branchDiffEClass, BRANCH_DIFF__TARGET)
		createEReference(branchDiffEClass, BRANCH_DIFF__LAST_COMMON_ANCESTOR)
		userBranchEClass = createEClass(USER_BRANCH)
		createEReference(userBranchEClass, USER_BRANCH__OWNER)
		createEReference(userBranchEClass, USER_BRANCH__BRANCHED_FROM)
		masterBranchEClass = createEClass(MASTER_BRANCH)
		branchEClass = createEClass(BRANCH)
		createEReference(branchEClass, BRANCH__CURRENT_HEAD_COMMIT)
		createEReference(branchEClass, BRANCH__CONTRIBUTORS)
		createEReference(branchEClass, BRANCH__CHILD_BRANCHES)
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
		var CommitPackage theCommitPackage = (EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) as CommitPackage)
		var AuthorPackage theAuthorPackage = (EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) as AuthorPackage)
		var VersioningPackage theVersioningPackage = (EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) as VersioningPackage)
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		userBranchEClass.getESuperTypes().add(this.getBranch())
		masterBranchEClass.getESuperTypes().add(this.getBranch())
		branchEClass.getESuperTypes().add(theVersioningPackage.getNamed())
		// Initialize classes, features, and operations; add parameters
		initEClass(branchDiffCreatorEClass, BranchDiffCreator, "BranchDiffCreator", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		initEReference(getBranchDiffCreator_Source(), this.getUserBranch(), null, "source", null, 1, 1,
			BranchDiffCreator, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getBranchDiffCreator_Target(), this.getUserBranch(), null, "target", null, 1, 1,
			BranchDiffCreator, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEOperation(getBranchDiffCreator__CreateVersionDiff(), this.getBranchDiff(), "createVersionDiff", 0, 1,
			IS_UNIQUE, IS_ORDERED)
		initEClass(branchDiffEClass, BranchDiff, "BranchDiff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEReference(getBranchDiff_SourceCommits(), theCommitPackage.getCommit(), null, "sourceCommits", null, 0, -1,
			BranchDiff, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getBranchDiff_TargetCommits(), theCommitPackage.getCommit(), null, "targetCommits", null, 0, -1,
			BranchDiff, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getBranchDiff_Source(), this.getUserBranch(), null, "source", null, 1, 1, BranchDiff,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED)
		initEReference(getBranchDiff_Target(), this.getUserBranch(), null, "target", null, 1, 1, BranchDiff,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED)
		initEReference(getBranchDiff_LastCommonAncestor(), theCommitPackage.getCommit(), null, "lastCommonAncestor",
			null, 1, 1, BranchDiff, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(userBranchEClass, UserBranch, "UserBranch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEReference(getUserBranch_Owner(), theAuthorPackage.getAuthor(), theAuthorPackage.getAuthor_OwnedBranches(),
			"owner", null, 1, 1, UserBranch, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getUserBranch_BranchedFrom(), this.getBranch(), this.getBranch_ChildBranches(), "branchedFrom",
			null, 1, 1, UserBranch, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(masterBranchEClass, MasterBranch, "MasterBranch", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		initEClass(branchEClass, Branch, "Branch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEReference(getBranch_CurrentHeadCommit(), theCommitPackage.getCommit(), null, "currentHeadCommit", null, 1,
			1, Branch, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getBranch_Contributors(), theAuthorPackage.getAuthor(),
			theAuthorPackage.getAuthor_ContributedBranches(), "contributors", null, 0, -1, Branch, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED)
			initEReference(getBranch_ChildBranches(), this.getUserBranch(), this.getUserBranch_BranchedFrom(),
				"childBranches", null, 0, -1, Branch, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		} // BranchPackageImpl
	}
	