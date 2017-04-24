/**
 */
package model.impl;

import model.Author;
import model.ModelFactory;
import model.ModelPackage;
import model.Named;
import model.Signature;
import model.Signed;
import model.Tag;

import model.branch.BranchPackage;

import model.branch.impl.BranchPackageImpl;

import model.commit.CommitPackage;

import model.commit.impl.CommitPackageImpl;

import model.conflict.ConflictPackage;

import model.conflict.impl.ConflictPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
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
	private EClass namedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signatureEClass = null;

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
	 * @see model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ConflictPackageImpl theConflictPackage = (ConflictPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) instanceof ConflictPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) : ConflictPackage.eINSTANCE);
		CommitPackageImpl theCommitPackage = (CommitPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) instanceof CommitPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) : CommitPackage.eINSTANCE);
		BranchPackageImpl theBranchPackage = (BranchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) instanceof BranchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) : BranchPackage.eINSTANCE);
		org.eclipse.emf.ecore.impl.EcorePackageImpl theEcorePackage = (org.eclipse.emf.ecore.impl.EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(org.eclipse.emf.ecore.EcorePackage.eNS_URI) instanceof org.eclipse.emf.ecore.impl.EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(org.eclipse.emf.ecore.EcorePackage.eNS_URI) : org.eclipse.emf.ecore.EcorePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);

		// Create package meta-data objects
		theModelPackage.createPackageContents();
		theConflictPackage.createPackageContents();
		theCommitPackage.createPackageContents();
		theBranchPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theEChangePackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();
		theConflictPackage.initializePackageContents();
		theCommitPackage.initializePackageContents();
		theBranchPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
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
	public EClass getNamed() {
		return namedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamed_Name() {
		return (EAttribute)namedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTag() {
		return tagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTag_Commit() {
		return (EReference)tagEClass.getEStructuralFeatures().get(0);
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
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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

		namedEClass = createEClass(NAMED);
		createEAttribute(namedEClass, NAMED__NAME);

		tagEClass = createEClass(TAG);
		createEReference(tagEClass, TAG__COMMIT);

		signedEClass = createEClass(SIGNED);
		createEReference(signedEClass, SIGNED__SIGNATURE);

		signatureEClass = createEClass(SIGNATURE);
		createEReference(signatureEClass, SIGNATURE__SIGNER);
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
		ConflictPackage theConflictPackage = (ConflictPackage)EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI);
		CommitPackage theCommitPackage = (CommitPackage)EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI);
		BranchPackage theBranchPackage = (BranchPackage)EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theConflictPackage);
		getESubpackages().add(theCommitPackage);
		getESubpackages().add(theBranchPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		authorEClass.getESuperTypes().add(this.getNamed());
		tagEClass.getESuperTypes().add(this.getNamed());
		tagEClass.getESuperTypes().add(this.getSigned());

		// Initialize classes, features, and operations; add parameters
		initEClass(authorEClass, Author.class, "Author", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthor_Email(), ecorePackage.getEString(), "email", null, 1, 1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_OwnedBranches(), theBranchPackage.getBranch(), theBranchPackage.getBranch_Owner(), "ownedBranches", null, 0, -1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthor_ContributedBranches(), theBranchPackage.getBranch(), theBranchPackage.getBranch_Contributors(), "contributedBranches", null, 0, -1, Author.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedEClass, Named.class, "Named", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamed_Name(), ecorePackage.getEString(), "name", null, 1, 1, Named.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tagEClass, Tag.class, "Tag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTag_Commit(), theCommitPackage.getCommit(), null, "commit", null, 1, 1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signedEClass, Signed.class, "Signed", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSigned_Signature(), this.getSignature(), null, "signature", null, 0, 1, Signed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signatureEClass, Signature.class, "Signature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSignature_Signer(), this.getAuthor(), null, "signer", null, 1, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
