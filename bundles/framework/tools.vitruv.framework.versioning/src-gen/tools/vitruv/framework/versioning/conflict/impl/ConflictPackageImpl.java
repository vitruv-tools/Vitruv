/**
 */
package tools.vitruv.framework.versioning.conflict.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;

import tools.vitruv.framework.versioning.VersioningPackage;

import tools.vitruv.framework.versioning.author.AuthorPackage;
import tools.vitruv.framework.versioning.author.impl.AuthorPackageImpl;
import tools.vitruv.framework.versioning.branch.BranchPackage;

import tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl;

import tools.vitruv.framework.versioning.commit.CommitPackage;

import tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl;

import tools.vitruv.framework.versioning.conflict.Conflict;
import tools.vitruv.framework.versioning.conflict.ConflictDetector;
import tools.vitruv.framework.versioning.conflict.ConflictFactory;
import tools.vitruv.framework.versioning.conflict.ConflictPackage;
import tools.vitruv.framework.versioning.conflict.ConflictSolvability;
import tools.vitruv.framework.versioning.conflict.ConflictType;
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict;
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict;

import tools.vitruv.framework.versioning.impl.VersioningPackageImpl;
import tools.vitruv.framework.versioning.repository.RepositoryPackage;
import tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConflictPackageImpl extends EPackageImpl implements ConflictPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleChangeConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiChangeConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conflictDetectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum conflictTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum conflictSolvabilityEEnum = null;

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
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConflictPackageImpl() {
		super(eNS_URI, ConflictFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConflictPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConflictPackage init() {
		if (isInited) return (ConflictPackage)EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI);

		// Obtain or create and register package
		ConflictPackageImpl theConflictPackage = (ConflictPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConflictPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConflictPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		VersioningPackageImpl theVersioningPackage = (VersioningPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI) instanceof VersioningPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI) : VersioningPackage.eINSTANCE);
		CommitPackageImpl theCommitPackage = (CommitPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) instanceof CommitPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommitPackage.eNS_URI) : CommitPackage.eINSTANCE);
		BranchPackageImpl theBranchPackage = (BranchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) instanceof BranchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI) : BranchPackage.eINSTANCE);
		AuthorPackageImpl theAuthorPackage = (AuthorPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AuthorPackage.eNS_URI) instanceof AuthorPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AuthorPackage.eNS_URI) : AuthorPackage.eINSTANCE);
		RepositoryPackageImpl theRepositoryPackage = (RepositoryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI) instanceof RepositoryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI) : RepositoryPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) : EcorePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);

		// Create package meta-data objects
		theConflictPackage.createPackageContents();
		theVersioningPackage.createPackageContents();
		theCommitPackage.createPackageContents();
		theBranchPackage.createPackageContents();
		theAuthorPackage.createPackageContents();
		theRepositoryPackage.createPackageContents();
		theEcorePackage.createPackageContents();
		theEChangePackage.createPackageContents();

		// Initialize created meta-data
		theConflictPackage.initializePackageContents();
		theVersioningPackage.initializePackageContents();
		theCommitPackage.initializePackageContents();
		theBranchPackage.initializePackageContents();
		theAuthorPackage.initializePackageContents();
		theRepositoryPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConflictPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConflictPackage.eNS_URI, theConflictPackage);
		return theConflictPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleChangeConflict() {
		return simpleChangeConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleChangeConflict_SourceChange() {
		return (EReference)simpleChangeConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleChangeConflict_TargetChange() {
		return (EReference)simpleChangeConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiChangeConflict() {
		return multiChangeConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiChangeConflict_SourceChanges() {
		return (EReference)multiChangeConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiChangeConflict_TargetChanges() {
		return (EReference)multiChangeConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConflictDetector() {
		return conflictDetectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getConflictDetector__DetectConflicts__BranchDiff() {
		return conflictDetectorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConflict() {
		return conflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConflict_Type() {
		return (EAttribute)conflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConflict_Solvability() {
		return (EAttribute)conflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getConflictType() {
		return conflictTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getConflictSolvability() {
		return conflictSolvabilityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictFactory getConflictFactory() {
		return (ConflictFactory)getEFactoryInstance();
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
		simpleChangeConflictEClass = createEClass(SIMPLE_CHANGE_CONFLICT);
		createEReference(simpleChangeConflictEClass, SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE);
		createEReference(simpleChangeConflictEClass, SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE);

		multiChangeConflictEClass = createEClass(MULTI_CHANGE_CONFLICT);
		createEReference(multiChangeConflictEClass, MULTI_CHANGE_CONFLICT__SOURCE_CHANGES);
		createEReference(multiChangeConflictEClass, MULTI_CHANGE_CONFLICT__TARGET_CHANGES);

		conflictDetectorEClass = createEClass(CONFLICT_DETECTOR);
		createEOperation(conflictDetectorEClass, CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF);

		conflictEClass = createEClass(CONFLICT);
		createEAttribute(conflictEClass, CONFLICT__TYPE);
		createEAttribute(conflictEClass, CONFLICT__SOLVABILITY);

		// Create enums
		conflictTypeEEnum = createEEnum(CONFLICT_TYPE);
		conflictSolvabilityEEnum = createEEnum(CONFLICT_SOLVABILITY);
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
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);
		BranchPackage theBranchPackage = (BranchPackage)EPackage.Registry.INSTANCE.getEPackage(BranchPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		simpleChangeConflictEClass.getESuperTypes().add(this.getConflict());
		multiChangeConflictEClass.getESuperTypes().add(this.getConflict());

		// Initialize classes, features, and operations; add parameters
		initEClass(simpleChangeConflictEClass, SimpleChangeConflict.class, "SimpleChangeConflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleChangeConflict_SourceChange(), theEChangePackage.getEChange(), null, "sourceChange", null, 1, 1, SimpleChangeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimpleChangeConflict_TargetChange(), theEChangePackage.getEChange(), null, "targetChange", null, 1, 1, SimpleChangeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiChangeConflictEClass, MultiChangeConflict.class, "MultiChangeConflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiChangeConflict_SourceChanges(), theEChangePackage.getEChange(), null, "sourceChanges", null, 1, -1, MultiChangeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiChangeConflict_TargetChanges(), theEChangePackage.getEChange(), null, "targetChanges", null, 1, -1, MultiChangeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conflictDetectorEClass, ConflictDetector.class, "ConflictDetector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getConflictDetector__DetectConflicts__BranchDiff(), this.getConflict(), "detectConflicts", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theBranchPackage.getBranchDiff(), "diff", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(conflictEClass, Conflict.class, "Conflict", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConflict_Type(), this.getConflictType(), "type", null, 0, 1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConflict_Solvability(), this.getConflictSolvability(), "solvability", null, 0, 1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(conflictTypeEEnum, ConflictType.class, "ConflictType");
		addEEnumLiteral(conflictTypeEEnum, ConflictType.NAMING);
		addEEnumLiteral(conflictTypeEEnum, ConflictType.MULTIPLICITY);
		addEEnumLiteral(conflictTypeEEnum, ConflictType.VISIBILITY);

		initEEnum(conflictSolvabilityEEnum, ConflictSolvability.class, "ConflictSolvability");
		addEEnumLiteral(conflictSolvabilityEEnum, ConflictSolvability.AUTOMATICALLY);
		addEEnumLiteral(conflictSolvabilityEEnum, ConflictSolvability.MANUAL);
	}

} //ConflictPackageImpl
