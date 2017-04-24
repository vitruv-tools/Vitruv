/**
 */
package model.commit.impl;

import model.ModelPackage;

import model.branch.BranchPackage;

import model.branch.impl.BranchPackageImpl;

import model.commit.Commit;
import model.commit.CommitFactory;
import model.commit.CommitMessage;
import model.commit.CommitPackage;
import model.commit.MergeCommit;
import model.commit.SimpleCommit;

import model.conflict.ConflictPackage;

import model.conflict.impl.ConflictPackageImpl;

import model.impl.ModelPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CommitPackageImpl extends EPackageImpl implements CommitPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeCommitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleCommitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commitMessageEClass = null;

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
	 * @see model.commit.CommitPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CommitPackageImpl() {
		super(eNS_URI, CommitFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CommitPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CommitPackage init() {
		if (isInited) return (CommitPackage)EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI);

		// Obtain or create and register package
		CommitPackageImpl theCommitPackage = (CommitPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CommitPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CommitPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ConflictPackageImpl theConflictPackage = (ConflictPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) instanceof ConflictPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) : ConflictPackage.eINSTANCE);
		BranchPackageImpl theBranchPackage = (BranchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) instanceof BranchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) : BranchPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);

		// Create package meta-data objects
		theCommitPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theConflictPackage.createPackageContents();
		theBranchPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theEChangePackage.createPackageContents();

		// Initialize created meta-data
		theCommitPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theConflictPackage.initializePackageContents();
		theBranchPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommitPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommitPackage.eNS_URI, theCommitPackage);
		return theCommitPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeCommit() {
		return mergeCommitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeCommit_CommitsMergedToThis() {
		return (EReference)mergeCommitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleCommit() {
		return simpleCommitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleCommit_Parent() {
		return (EReference)simpleCommitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommit() {
		return commitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Checksum() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_Changes() {
		return (EReference)commitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_Commitmessage() {
		return (EReference)commitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_CommitsBranchedFromThis() {
		return (EReference)commitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_CommitsMergedFromThis() {
		return (EReference)commitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Identifier() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommitMessage() {
		return commitMessageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommitMessage_Date() {
		return (EAttribute)commitMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommitMessage_Message() {
		return (EAttribute)commitMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommitMessage_Author() {
		return (EReference)commitMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitFactory getCommitFactory() {
		return (CommitFactory)getEFactoryInstance();
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
		mergeCommitEClass = createEClass(MERGE_COMMIT);
		createEReference(mergeCommitEClass, MERGE_COMMIT__COMMITS_MERGED_TO_THIS);

		simpleCommitEClass = createEClass(SIMPLE_COMMIT);
		createEReference(simpleCommitEClass, SIMPLE_COMMIT__PARENT);

		commitEClass = createEClass(COMMIT);
		createEAttribute(commitEClass, COMMIT__CHECKSUM);
		createEReference(commitEClass, COMMIT__CHANGES);
		createEReference(commitEClass, COMMIT__COMMITMESSAGE);
		createEReference(commitEClass, COMMIT__COMMITS_BRANCHED_FROM_THIS);
		createEReference(commitEClass, COMMIT__COMMITS_MERGED_FROM_THIS);
		createEAttribute(commitEClass, COMMIT__IDENTIFIER);

		commitMessageEClass = createEClass(COMMIT_MESSAGE);
		createEAttribute(commitMessageEClass, COMMIT_MESSAGE__DATE);
		createEAttribute(commitMessageEClass, COMMIT_MESSAGE__MESSAGE);
		createEReference(commitMessageEClass, COMMIT_MESSAGE__AUTHOR);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		mergeCommitEClass.getESuperTypes().add(this.getCommit());
		simpleCommitEClass.getESuperTypes().add(this.getCommit());
		commitEClass.getESuperTypes().add(theModelPackage.getSigned());

		// Initialize classes, features, and operations; add parameters
		initEClass(mergeCommitEClass, MergeCommit.class, "MergeCommit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMergeCommit_CommitsMergedToThis(), this.getCommit(), this.getCommit_CommitsMergedFromThis(), "commitsMergedToThis", null, 2, 2, MergeCommit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleCommitEClass, SimpleCommit.class, "SimpleCommit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleCommit_Parent(), this.getCommit(), null, "parent", null, 1, 1, SimpleCommit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commitEClass, Commit.class, "Commit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommit_Checksum(), ecorePackage.getELong(), "checksum", null, 1, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_Changes(), theEChangePackage.getEChange(), null, "changes", null, 1, -1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_Commitmessage(), this.getCommitMessage(), null, "commitmessage", null, 1, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_CommitsBranchedFromThis(), this.getCommit(), null, "commitsBranchedFromThis", null, 0, -1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_CommitsMergedFromThis(), this.getMergeCommit(), this.getMergeCommit_CommitsMergedToThis(), "commitsMergedFromThis", null, 0, -1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommit_Identifier(), ecorePackage.getEInt(), "identifier", null, 1, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commitMessageEClass, CommitMessage.class, "CommitMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommitMessage_Date(), ecorePackage.getEDate(), "date", null, 1, 1, CommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommitMessage_Message(), ecorePackage.getEString(), "message", null, 1, 1, CommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommitMessage_Author(), theModelPackage.getAuthor(), null, "author", null, 1, 1, CommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //CommitPackageImpl
