/**
 */
package model.branch.impl;

import model.ModelPackage;

import model.branch.Branch;
import model.branch.BranchDiff;
import model.branch.BranchDiffCreator;
import model.branch.BranchFactory;
import model.branch.BranchPackage;

import model.commit.CommitPackage;

import model.commit.impl.CommitPackageImpl;

import model.conflict.ConflictPackage;

import model.conflict.impl.ConflictPackageImpl;

import model.impl.ModelPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
public class BranchPackageImpl extends EPackageImpl implements BranchPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchDiffCreatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchDiffEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchEClass = null;

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
	 * @see model.branch.BranchPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BranchPackageImpl() {
		super(eNS_URI, BranchFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BranchPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BranchPackage init() {
		if (isInited) return (BranchPackage)EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI);

		// Obtain or create and register package
		BranchPackageImpl theBranchPackage = (BranchPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BranchPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BranchPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ConflictPackageImpl theConflictPackage = (ConflictPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) instanceof ConflictPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) : ConflictPackage.eINSTANCE);
		CommitPackageImpl theCommitPackage = (CommitPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) instanceof CommitPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) : CommitPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);

		// Create package meta-data objects
		theBranchPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theConflictPackage.createPackageContents();
		theCommitPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theEChangePackage.createPackageContents();

		// Initialize created meta-data
		theBranchPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theConflictPackage.initializePackageContents();
		theCommitPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBranchPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BranchPackage.eNS_URI, theBranchPackage);
		return theBranchPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBranchDiffCreator() {
		return branchDiffCreatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiffCreator_Source() {
		return (EReference)branchDiffCreatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiffCreator_Target() {
		return (EReference)branchDiffCreatorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getBranchDiffCreator__CreateVersionDiff() {
		return branchDiffCreatorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBranchDiff() {
		return branchDiffEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiff_SourceCommits() {
		return (EReference)branchDiffEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiff_TargetCommits() {
		return (EReference)branchDiffEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiff_Source() {
		return (EReference)branchDiffEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiff_Target() {
		return (EReference)branchDiffEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranchDiff_LastCommonAncestor() {
		return (EReference)branchDiffEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBranch() {
		return branchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_CurrentHeadCommit() {
		return (EReference)branchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_Owner() {
		return (EReference)branchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_Contributors() {
		return (EReference)branchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_BranchedFrom() {
		return (EReference)branchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_ChildBranches() {
		return (EReference)branchEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchFactory getBranchFactory() {
		return (BranchFactory)getEFactoryInstance();
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
		branchDiffCreatorEClass = createEClass(BRANCH_DIFF_CREATOR);
		createEReference(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR__SOURCE);
		createEReference(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR__TARGET);
		createEOperation(branchDiffCreatorEClass, BRANCH_DIFF_CREATOR___CREATE_VERSION_DIFF);

		branchDiffEClass = createEClass(BRANCH_DIFF);
		createEReference(branchDiffEClass, BRANCH_DIFF__SOURCE_COMMITS);
		createEReference(branchDiffEClass, BRANCH_DIFF__TARGET_COMMITS);
		createEReference(branchDiffEClass, BRANCH_DIFF__SOURCE);
		createEReference(branchDiffEClass, BRANCH_DIFF__TARGET);
		createEReference(branchDiffEClass, BRANCH_DIFF__LAST_COMMON_ANCESTOR);

		branchEClass = createEClass(BRANCH);
		createEReference(branchEClass, BRANCH__CURRENT_HEAD_COMMIT);
		createEReference(branchEClass, BRANCH__OWNER);
		createEReference(branchEClass, BRANCH__CONTRIBUTORS);
		createEReference(branchEClass, BRANCH__BRANCHED_FROM);
		createEReference(branchEClass, BRANCH__CHILD_BRANCHES);
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
		CommitPackage theCommitPackage = (CommitPackage)EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		branchEClass.getESuperTypes().add(theModelPackage.getNamed());

		// Initialize classes, features, and operations; add parameters
		initEClass(branchDiffCreatorEClass, BranchDiffCreator.class, "BranchDiffCreator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBranchDiffCreator_Source(), this.getBranch(), null, "source", null, 1, 1, BranchDiffCreator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchDiffCreator_Target(), this.getBranch(), null, "target", null, 1, 1, BranchDiffCreator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getBranchDiffCreator__CreateVersionDiff(), this.getBranchDiff(), "createVersionDiff", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(branchDiffEClass, BranchDiff.class, "BranchDiff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBranchDiff_SourceCommits(), theCommitPackage.getCommit(), null, "sourceCommits", null, 0, -1, BranchDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchDiff_TargetCommits(), theCommitPackage.getCommit(), null, "targetCommits", null, 0, -1, BranchDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchDiff_Source(), this.getBranch(), null, "source", null, 1, 1, BranchDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchDiff_Target(), this.getBranch(), null, "target", null, 1, 1, BranchDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchDiff_LastCommonAncestor(), theCommitPackage.getCommit(), null, "lastCommonAncestor", null, 1, 1, BranchDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(branchEClass, Branch.class, "Branch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBranch_CurrentHeadCommit(), theCommitPackage.getCommit(), null, "currentHeadCommit", null, 1, 1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranch_Owner(), theModelPackage.getAuthor(), theModelPackage.getAuthor_OwnedBranches(), "owner", null, 1, 1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranch_Contributors(), theModelPackage.getAuthor(), theModelPackage.getAuthor_ContributedBranches(), "contributors", null, 1, -1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranch_BranchedFrom(), this.getBranch(), this.getBranch_ChildBranches(), "branchedFrom", null, 1, 1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranch_ChildBranches(), this.getBranch(), this.getBranch_BranchedFrom(), "childBranches", null, 0, -1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //BranchPackageImpl
