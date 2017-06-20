/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.emf.ecore.EAttribute
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
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit
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
class CommitPackageImpl extends EPackageImpl implements CommitPackage {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass mergeCommitEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass simpleCommitEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass commitEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass commitMessageEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass initialCommitEClass = null

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
	 * @see tools.vitruv.framework.versioning.commit.CommitPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private new() {
		super(eNS_URI, CommitFactory.eINSTANCE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	static boolean isInited = false

	/** 
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>This method is used to initialize {@link CommitPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	def static CommitPackage init() {
		if (isInited) return (EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) as CommitPackage)
		// Obtain or create and register package
		var CommitPackageImpl theCommitPackage = ((if (EPackage.Registry.INSTANCE.get(
			eNS_URI) instanceof CommitPackageImpl) EPackage.Registry.INSTANCE.get(
			eNS_URI) else new CommitPackageImpl() ) as CommitPackageImpl)
		isInited = true
		// Obtain or create and register interdependencies
		var VersioningPackageImpl theVersioningPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) instanceof VersioningPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) else VersioningPackage.eINSTANCE ) as VersioningPackageImpl)
		var ConflictPackageImpl theConflictPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			ConflictPackage.eNS_URI) instanceof ConflictPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			ConflictPackage.eNS_URI) else ConflictPackage.eINSTANCE ) as ConflictPackageImpl)
		var BranchPackageImpl theBranchPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) instanceof BranchPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) else BranchPackage.eINSTANCE ) as BranchPackageImpl)
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
		theCommitPackage.createPackageContents()
		theVersioningPackage.createPackageContents()
		theConflictPackage.createPackageContents()
		theBranchPackage.createPackageContents()
		theAuthorPackage.createPackageContents()
		theRepositoryPackage.createPackageContents()
		theEcorePackage.createPackageContents()
		theEChangePackage.createPackageContents()
		// Initialize created meta-data
		theCommitPackage.initializePackageContents()
		theVersioningPackage.initializePackageContents()
		theConflictPackage.initializePackageContents()
		theBranchPackage.initializePackageContents()
		theAuthorPackage.initializePackageContents()
		theRepositoryPackage.initializePackageContents()
		theEcorePackage.initializePackageContents()
		theEChangePackage.initializePackageContents()
		// Mark meta-data to indicate it can't be changed
		theCommitPackage.freeze()
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommitPackage.eNS_URI, theCommitPackage)
		return theCommitPackage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getMergeCommit() {
		return mergeCommitEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getMergeCommit_CommitsMergedToThis() {
		return (mergeCommitEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getSimpleCommit() {
		return simpleCommitEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getSimpleCommit_Parent() {
		return (simpleCommitEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getCommit() {
		return commitEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getCommit_Checksum() {
		return (commitEClass.getEStructuralFeatures().get(0) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getCommit_Changes() {
		return (commitEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getCommit_Commitmessage() {
		return (commitEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getCommit_CommitsBranchedFromThis() {
		return (commitEClass.getEStructuralFeatures().get(3) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getCommit_CommitsMergedFromThis() {
		return (commitEClass.getEStructuralFeatures().get(4) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getCommit_Identifier() {
		return (commitEClass.getEStructuralFeatures().get(5) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getCommitMessage() {
		return commitMessageEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getCommitMessage_Date() {
		return (commitMessageEClass.getEStructuralFeatures().get(0) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getCommitMessage_Message() {
		return (commitMessageEClass.getEStructuralFeatures().get(1) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getCommitMessage_Author() {
		return (commitMessageEClass.getEStructuralFeatures().get(2) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getInitialCommit() {
		return initialCommitEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override CommitFactory getCommitFactory() {
		return (getEFactoryInstance() as CommitFactory)
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
		mergeCommitEClass = createEClass(MERGE_COMMIT)
		createEReference(mergeCommitEClass, MERGE_COMMIT__COMMITS_MERGED_TO_THIS)
		simpleCommitEClass = createEClass(SIMPLE_COMMIT)
		createEReference(simpleCommitEClass, SIMPLE_COMMIT__PARENT)
		commitEClass = createEClass(COMMIT)
		createEAttribute(commitEClass, COMMIT__CHECKSUM)
		createEReference(commitEClass, COMMIT__CHANGES)
		createEReference(commitEClass, COMMIT__COMMITMESSAGE)
		createEReference(commitEClass, COMMIT__COMMITS_BRANCHED_FROM_THIS)
		createEReference(commitEClass, COMMIT__COMMITS_MERGED_FROM_THIS)
		createEAttribute(commitEClass, COMMIT__IDENTIFIER)
		commitMessageEClass = createEClass(COMMIT_MESSAGE)
		createEAttribute(commitMessageEClass, COMMIT_MESSAGE__DATE)
		createEAttribute(commitMessageEClass, COMMIT_MESSAGE__MESSAGE)
		createEReference(commitMessageEClass, COMMIT_MESSAGE__AUTHOR)
		initialCommitEClass = createEClass(INITIAL_COMMIT)
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
		var AuthorPackage theAuthorPackage = (EPackage.Registry.INSTANCE.getEPackage(
			AuthorPackage.eNS_URI) as AuthorPackage)
		var EChangePackage theEChangePackage = (EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) as EChangePackage)
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		mergeCommitEClass.getESuperTypes().add(this.getCommit())
		simpleCommitEClass.getESuperTypes().add(this.getCommit())
		commitEClass.getESuperTypes().add(theAuthorPackage.getSigned())
		initialCommitEClass.getESuperTypes().add(this.getCommit())
		// Initialize classes, features, and operations; add parameters
		initEClass(mergeCommitEClass, MergeCommit, "MergeCommit", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		initEReference(getMergeCommit_CommitsMergedToThis(), this.getCommit(), this.getCommit_CommitsMergedFromThis(),
			"commitsMergedToThis", null, 2, 2, MergeCommit, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(simpleCommitEClass, SimpleCommit, "SimpleCommit", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		initEReference(getSimpleCommit_Parent(), this.getCommit(), this.getCommit_CommitsBranchedFromThis(), "parent",
			null, 1, 1, SimpleCommit, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(commitEClass, Commit, "Commit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEAttribute(getCommit_Checksum(), ecorePackage.getELong(), "checksum", "1000", 1, 1, Commit, !IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED)
		initEReference(getCommit_Changes(), theEChangePackage.getEChange(), null, "changes", null, 1, -1, Commit,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED)
		initEReference(getCommit_Commitmessage(), this.getCommitMessage(), null, "commitmessage", null, 1, 1, Commit,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED)
		initEReference(getCommit_CommitsBranchedFromThis(), this.getSimpleCommit(), this.getSimpleCommit_Parent(),
			"commitsBranchedFromThis", null, 0, -1, Commit, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getCommit_CommitsMergedFromThis(), this.getMergeCommit(),
			this.getMergeCommit_CommitsMergedToThis(), "commitsMergedFromThis", null, 0, -1, Commit, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED)
			initEAttribute(getCommit_Identifier(), ecorePackage.getEInt(), "identifier", "2000", 1, 1, Commit,
				!IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEClass(commitMessageEClass, CommitMessage, "CommitMessage", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS)
			initEAttribute(getCommitMessage_Date(), ecorePackage.getEDate(), "date", null, 1, 1, CommitMessage,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEAttribute(getCommitMessage_Message(), ecorePackage.getEString(), "message", null, 1, 1, CommitMessage,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEReference(getCommitMessage_Author(), theAuthorPackage.getAuthor(), null, "author", null, 1, 1,
				CommitMessage, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
			initEClass(initialCommitEClass, InitialCommit, "InitialCommit", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS)
		} // CommitPackageImpl
	}
	