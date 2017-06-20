/** 
 */
package tools.vitruv.framework.versioning.repository.impl

import org.eclipse.emf.ecore.EClass
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
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl
import tools.vitruv.framework.versioning.impl.VersioningPackageImpl
import tools.vitruv.framework.versioning.repository.Repository
import tools.vitruv.framework.versioning.repository.RepositoryFactory
import tools.vitruv.framework.versioning.repository.RepositoryPackage
import tools.vitruv.framework.versioning.repository.Tag

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class RepositoryPackageImpl extends EPackageImpl implements RepositoryPackage {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass tagEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass repositoryEClass = null

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
	 * @see tools.vitruv.framework.versioning.repository.RepositoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private new() {
		super(eNS_URI, RepositoryFactory.eINSTANCE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	static boolean isInited = false

	/** 
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>This method is used to initialize {@link RepositoryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	def static RepositoryPackage init() {
		if (isInited) return (EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI) as RepositoryPackage)
		// Obtain or create and register package
		var RepositoryPackageImpl theRepositoryPackage = ((if (EPackage.Registry.INSTANCE.get(
			eNS_URI) instanceof RepositoryPackageImpl) EPackage.Registry.INSTANCE.get(
			eNS_URI) else new RepositoryPackageImpl() ) as RepositoryPackageImpl)
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
		var AuthorPackageImpl theAuthorPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) instanceof AuthorPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) else AuthorPackage.eINSTANCE ) as AuthorPackageImpl)
		var EcorePackageImpl theEcorePackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			EcorePackage.eNS_URI) instanceof EcorePackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			EcorePackage.eNS_URI) else EcorePackage.eINSTANCE ) as EcorePackageImpl)
		var EChangePackageImpl theEChangePackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) instanceof EChangePackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) else EChangePackage.eINSTANCE ) as EChangePackageImpl)
		// Create package meta-data objects
		theRepositoryPackage.createPackageContents()
		theVersioningPackage.createPackageContents()
		theConflictPackage.createPackageContents()
		theCommitPackage.createPackageContents()
		theBranchPackage.createPackageContents()
		theAuthorPackage.createPackageContents()
		theEcorePackage.createPackageContents()
		theEChangePackage.createPackageContents()
		// Initialize created meta-data
		theRepositoryPackage.initializePackageContents()
		theVersioningPackage.initializePackageContents()
		theConflictPackage.initializePackageContents()
		theCommitPackage.initializePackageContents()
		theBranchPackage.initializePackageContents()
		theAuthorPackage.initializePackageContents()
		theEcorePackage.initializePackageContents()
		theEChangePackage.initializePackageContents()
		// Mark meta-data to indicate it can't be changed
		theRepositoryPackage.freeze()
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RepositoryPackage.eNS_URI, theRepositoryPackage)
		return theRepositoryPackage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getTag() {
		return tagEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getTag_Commit() {
		return (tagEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getRepository() {
		return repositoryEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getRepository_Tags() {
		return (repositoryEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getRepository_Commits() {
		return (repositoryEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getRepository_Branches() {
		return (repositoryEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getRepository_InitialCommit() {
		return (repositoryEClass.getEStructuralFeatures().get(3) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getRepository_Master() {
		return (repositoryEClass.getEStructuralFeatures().get(4) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override RepositoryFactory getRepositoryFactory() {
		return (getEFactoryInstance() as RepositoryFactory)
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
		tagEClass = createEClass(TAG)
		createEReference(tagEClass, TAG__COMMIT)
		repositoryEClass = createEClass(REPOSITORY)
		createEReference(repositoryEClass, REPOSITORY__TAGS)
		createEReference(repositoryEClass, REPOSITORY__COMMITS)
		createEReference(repositoryEClass, REPOSITORY__BRANCHES)
		createEReference(repositoryEClass, REPOSITORY__INITIAL_COMMIT)
		createEReference(repositoryEClass, REPOSITORY__MASTER)
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
		var AuthorPackage theAuthorPackage = (EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) as AuthorPackage)
		var CommitPackage theCommitPackage = (EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) as CommitPackage)
		var BranchPackage theBranchPackage = (EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) as BranchPackage)
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		tagEClass.getESuperTypes().add(theVersioningPackage.getNamed())
		tagEClass.getESuperTypes().add(theAuthorPackage.getSigned())
		// Initialize classes, features, and operations; add parameters
		initEClass(tagEClass, Tag, "Tag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEReference(getTag_Commit(), theCommitPackage.getCommit(), null, "commit", null, 1, 1, Tag, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED)
			initEClass(repositoryEClass, Repository, "Repository", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS)
			initEReference(getRepository_Tags(), this.getTag(), null, "tags", null, 0, -1, Repository, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED)
				initEReference(getRepository_Commits(), theCommitPackage.getCommit(), null, "commits", null, 0, -1,
					Repository, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
					!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
				initEReference(getRepository_Branches(), theBranchPackage.getBranch(), null, "branches", null, 0, -1,
					Repository, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
					!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
				initEReference(getRepository_InitialCommit(), theCommitPackage.getInitialCommit(), null,
					"initialCommit", null, 1, 1, Repository, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
					!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
				initEReference(getRepository_Master(), theBranchPackage.getMasterBranch(), null, "master", null, 1, 1,
					Repository, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
					!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			} // RepositoryPackageImpl
		}
		