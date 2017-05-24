/**
 */
package tools.vitruv.framework.versioning.author.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;

import tools.vitruv.framework.versioning.VersioningPackage;

import tools.vitruv.framework.versioning.author.Author;
import tools.vitruv.framework.versioning.author.AuthorFactory;
import tools.vitruv.framework.versioning.author.AuthorPackage;
import tools.vitruv.framework.versioning.author.Signature;
import tools.vitruv.framework.versioning.author.Signed;

import tools.vitruv.framework.versioning.branch.BranchPackage;

import tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl;

import tools.vitruv.framework.versioning.commit.CommitPackage;

import tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl;

import tools.vitruv.framework.versioning.conflict.ConflictPackage;

import tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl;

import tools.vitruv.framework.versioning.impl.VersioningPackageImpl;

import tools.vitruv.framework.versioning.repository.RepositoryPackage;

import tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AuthorPackageImpl extends EPackageImpl implements AuthorPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signedEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
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
	private AuthorPackageImpl() {
		super(eNS_URI, AuthorFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link AuthorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AuthorPackage init() {
		if (isInited) return (AuthorPackage)EPackage.Registry.INSTANCE.getEPackage(AuthorPackage.eNS_URI);

		// Obtain or create and register package
		AuthorPackageImpl theAuthorPackage = (AuthorPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AuthorPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AuthorPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		VersioningPackageImpl theVersioningPackage = (VersioningPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI) instanceof VersioningPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI) : VersioningPackage.eINSTANCE);
		ConflictPackageImpl theConflictPackage = (ConflictPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) instanceof ConflictPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) : ConflictPackage.eINSTANCE);
		CommitPackageImpl theCommitPackage = (CommitPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) instanceof CommitPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) : CommitPackage.eINSTANCE);
		BranchPackageImpl theBranchPackage = (BranchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) instanceof BranchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) : BranchPackage.eINSTANCE);
		RepositoryPackageImpl theRepositoryPackage = (RepositoryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI) instanceof RepositoryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI) : RepositoryPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);

		// Create package meta-data objects
		theAuthorPackage.createPackageContents();
		theVersioningPackage.createPackageContents();
		theConflictPackage.createPackageContents();
		theCommitPackage.createPackageContents();
		theBranchPackage.createPackageContents();
		theRepositoryPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theEChangePackage.createPackageContents();

		// Initialize created meta-data
		theAuthorPackage.initializePackageContents();
		theVersioningPackage.initializePackageContents();
		theConflictPackage.initializePackageContents();
		theCommitPackage.initializePackageContents();
		theBranchPackage.initializePackageContents();
		theRepositoryPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAuthorPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AuthorPackage.eNS_URI, theAuthorPackage);
		return theAuthorPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthor() {
		return authorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthor_Email() {
		return (EAttribute)authorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthor_OwnedBranches() {
		return (EReference)authorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthor_ContributedBranches() {
		return (EReference)authorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthor_Commits() {
		return (EReference)authorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthor_CurrentRepository() {
		return (EReference)authorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthor_CurrentBranch() {
		return (EReference)authorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAuthor__CreateSimpleCommit__String_Commit_EList() {
		return authorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAuthor__CreateBranch__String() {
		return authorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAuthor__SwitchToBranch__Branch() {
		return authorEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAuthor__SwitchToRepository__Repository() {
		return authorEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSignature() {
		return signatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSignature_Signer() {
		return (EReference)signatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSigned() {
		return signedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSigned_Signature() {
		return (EReference)signedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthorFactory getAuthorFactory() {
		return (AuthorFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		authorEClass = createEClass(AUTHOR);
		createEAttribute(authorEClass, AUTHOR__EMAIL);
		createEReference(authorEClass, AUTHOR__OWNED_BRANCHES);
		createEReference(authorEClass, AUTHOR__CONTRIBUTED_BRANCHES);
		createEReference(authorEClass, AUTHOR__COMMITS);
		createEReference(authorEClass, AUTHOR__CURRENT_REPOSITORY);
		createEReference(authorEClass, AUTHOR__CURRENT_BRANCH);
		createEOperation(authorEClass, AUTHOR___CREATE_SIMPLE_COMMIT__STRING_COMMIT_ELIST);
		createEOperation(authorEClass, AUTHOR___CREATE_BRANCH__STRING);
		createEOperation(authorEClass, AUTHOR___SWITCH_TO_BRANCH__BRANCH);
		createEOperation(authorEClass, AUTHOR___SWITCH_TO_REPOSITORY__REPOSITORY);

		signatureEClass = createEClass(SIGNATURE);
		createEReference(signatureEClass, SIGNATURE__SIGNER);

		signedEClass = createEClass(SIGNED);
		createEReference(signedEClass, SIGNED__SIGNATURE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		VersioningPackage theVersioningPackage = (VersioningPackage)EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI);
		BranchPackage theBranchPackage = (BranchPackage)EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI);
		CommitPackage theCommitPackage = (CommitPackage)EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI);
		RepositoryPackage theRepositoryPackage = (RepositoryPackage)EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		authorEClass.getESuperTypes().add(theVersioningPackage.getNamed());

		// Initialize classes, features, and operations; add parameters
		initEClass(authorEClass, Author.class, "Author", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthor_Email(), ecorePackage.getEString(), "email", null, 1, 1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_OwnedBranches(), theBranchPackage.getUserBranch(), theBranchPackage.getUserBranch_Owner(), "ownedBranches", null, 0, -1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_ContributedBranches(), theBranchPackage.getBranch(), theBranchPackage.getBranch_Contributors(), "contributedBranches", null, 0, -1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_Commits(), theCommitPackage.getCommit(), null, "commits", null, 0, -1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_CurrentRepository(), theRepositoryPackage.getRepository(), null, "currentRepository", null, 0, 1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_CurrentBranch(), theBranchPackage.getBranch(), null, "currentBranch", null, 0, 1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getAuthor__CreateSimpleCommit__String_Commit_EList(), theCommitPackage.getSimpleCommit(), "createSimpleCommit", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "message", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCommitPackage.getCommit(), "parent", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEChangePackage.getEChange(), "changes", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuthor__CreateBranch__String(), theBranchPackage.getUserBranch(), "createBranch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "branchName", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuthor__SwitchToBranch__Branch(), null, "switchToBranch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theBranchPackage.getBranch(), "targetBranch", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuthor__SwitchToRepository__Repository(), null, "switchToRepository", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRepositoryPackage.getRepository(), "repository", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(signatureEClass, Signature.class, "Signature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSignature_Signer(), this.getAuthor(), null, "signer", null, 1, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signedEClass, Signed.class, "Signed", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSigned_Signature(), this.getSignature(), null, "signature", null, 0, 1, Signed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //AuthorPackageImpl
