/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EEnum
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
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.conflict.ConflictDetector
import tools.vitruv.framework.versioning.conflict.ConflictFactory
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.ConflictSolvability
import tools.vitruv.framework.versioning.conflict.ConflictType
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict
import tools.vitruv.framework.versioning.impl.VersioningPackageImpl
import tools.vitruv.framework.versioning.repository.RepositoryPackage
import tools.vitruv.framework.versioning.repository.impl.RepositoryPackageImpl

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class ConflictPackageImpl extends EPackageImpl implements ConflictPackage {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass simpleChangeConflictEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass multiChangeConflictEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass conflictDetectorEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass conflictEClass = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EEnum conflictTypeEEnum = null
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EEnum conflictSolvabilityEEnum = null

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
	 * @see tools.vitruv.framework.versioning.conflict.ConflictPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private new() {
		super(eNS_URI, ConflictFactory.eINSTANCE)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	static boolean isInited = false

	/** 
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>This method is used to initialize {@link ConflictPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	def static ConflictPackage init() {
		if (isInited) return (EPackage.Registry.INSTANCE.getEPackage(ConflictPackage.eNS_URI) as ConflictPackage)
		// Obtain or create and register package
		var ConflictPackageImpl theConflictPackage = ((if (EPackage.Registry.INSTANCE.get(
			eNS_URI) instanceof ConflictPackageImpl) EPackage.Registry.INSTANCE.get(
			eNS_URI) else new ConflictPackageImpl() ) as ConflictPackageImpl)
		isInited = true
		// Obtain or create and register interdependencies
		var VersioningPackageImpl theVersioningPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) instanceof VersioningPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			VersioningPackage.eNS_URI) else VersioningPackage.eINSTANCE ) as VersioningPackageImpl)
		var CommitPackageImpl theCommitPackage = ((if (EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) instanceof CommitPackageImpl) EPackage.Registry.INSTANCE.getEPackage(
			CommitPackage.eNS_URI) else CommitPackage.eINSTANCE ) as CommitPackageImpl)
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
		theConflictPackage.createPackageContents()
		theVersioningPackage.createPackageContents()
		theCommitPackage.createPackageContents()
		theBranchPackage.createPackageContents()
		theAuthorPackage.createPackageContents()
		theRepositoryPackage.createPackageContents()
		theEcorePackage.createPackageContents()
		theEChangePackage.createPackageContents()
		// Initialize created meta-data
		theConflictPackage.initializePackageContents()
		theVersioningPackage.initializePackageContents()
		theCommitPackage.initializePackageContents()
		theBranchPackage.initializePackageContents()
		theAuthorPackage.initializePackageContents()
		theRepositoryPackage.initializePackageContents()
		theEcorePackage.initializePackageContents()
		theEChangePackage.initializePackageContents()
		// Mark meta-data to indicate it can't be changed
		theConflictPackage.freeze()
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConflictPackage.eNS_URI, theConflictPackage)
		return theConflictPackage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getSimpleChangeConflict() {
		return simpleChangeConflictEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getSimpleChangeConflict_SourceChange() {
		return (simpleChangeConflictEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getSimpleChangeConflict_TargetChange() {
		return (simpleChangeConflictEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getMultiChangeConflict() {
		return multiChangeConflictEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getMultiChangeConflict_SourceChanges() {
		return (multiChangeConflictEClass.getEStructuralFeatures().get(0) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EReference getMultiChangeConflict_TargetChanges() {
		return (multiChangeConflictEClass.getEStructuralFeatures().get(1) as EReference)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getConflictDetector() {
		return conflictDetectorEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EOperation getConflictDetector__DetectConflicts__BranchDiff() {
		return conflictDetectorEClass.getEOperations().get(0)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EClass getConflict() {
		return conflictEClass
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getConflict_Type() {
		return (conflictEClass.getEStructuralFeatures().get(0) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EAttribute getConflict_Solvability() {
		return (conflictEClass.getEStructuralFeatures().get(1) as EAttribute)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EEnum getConflictType() {
		return conflictTypeEEnum
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EEnum getConflictSolvability() {
		return conflictSolvabilityEEnum
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictFactory getConflictFactory() {
		return (getEFactoryInstance() as ConflictFactory)
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
		simpleChangeConflictEClass = createEClass(SIMPLE_CHANGE_CONFLICT)
		createEReference(simpleChangeConflictEClass, SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE)
		createEReference(simpleChangeConflictEClass, SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE)
		multiChangeConflictEClass = createEClass(MULTI_CHANGE_CONFLICT)
		createEReference(multiChangeConflictEClass, MULTI_CHANGE_CONFLICT__SOURCE_CHANGES)
		createEReference(multiChangeConflictEClass, MULTI_CHANGE_CONFLICT__TARGET_CHANGES)
		conflictDetectorEClass = createEClass(CONFLICT_DETECTOR)
		createEOperation(conflictDetectorEClass, CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF)
		conflictEClass = createEClass(CONFLICT)
		createEAttribute(conflictEClass, CONFLICT__TYPE)
		createEAttribute(conflictEClass, CONFLICT__SOLVABILITY)
		// Create enums
		conflictTypeEEnum = createEEnum(CONFLICT_TYPE)
		conflictSolvabilityEEnum = createEEnum(CONFLICT_SOLVABILITY)
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
		var EChangePackage theEChangePackage = (EPackage.Registry.INSTANCE.getEPackage(
			EChangePackage.eNS_URI) as EChangePackage)
		var BranchPackage theBranchPackage = (EPackage.Registry.INSTANCE.getEPackage(
			BranchPackage.eNS_URI) as BranchPackage)
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		simpleChangeConflictEClass.getESuperTypes().add(this.getConflict())
		multiChangeConflictEClass.getESuperTypes().add(this.getConflict())
		// Initialize classes, features, and operations; add parameters
		initEClass(simpleChangeConflictEClass, SimpleChangeConflict, "SimpleChangeConflict", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEReference(getSimpleChangeConflict_SourceChange(), theEChangePackage.getEChange(), null, "sourceChange",
			null, 1, 1, SimpleChangeConflict, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getSimpleChangeConflict_TargetChange(), theEChangePackage.getEChange(), null, "targetChange",
			null, 1, 1, SimpleChangeConflict, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(multiChangeConflictEClass, MultiChangeConflict, "MultiChangeConflict", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		initEReference(getMultiChangeConflict_SourceChanges(), theEChangePackage.getEChange(), null, "sourceChanges",
			null, 1, -1, MultiChangeConflict, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEReference(getMultiChangeConflict_TargetChanges(), theEChangePackage.getEChange(), null, "targetChanges",
			null, 1, -1, MultiChangeConflict, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEClass(conflictDetectorEClass, ConflictDetector, "ConflictDetector", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS)
		var EOperation op = initEOperation(getConflictDetector__DetectConflicts__BranchDiff(), this.getConflict(),
			"detectConflicts", 0, -1, IS_UNIQUE, IS_ORDERED)
		addEParameter(op, theBranchPackage.getBranchDiff(), "diff", 0, 1, IS_UNIQUE, IS_ORDERED)
		initEClass(conflictEClass, Conflict, "Conflict", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS)
		initEAttribute(getConflict_Type(), this.getConflictType(), "type", null, 0, 1, Conflict, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		initEAttribute(getConflict_Solvability(), this.getConflictSolvability(), "solvability", null, 0, 1, Conflict,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED)
		// Initialize enums and add enum literals
		initEEnum(conflictTypeEEnum, ConflictType, "ConflictType")
		addEEnumLiteral(conflictTypeEEnum, ConflictType.NAMING)
		addEEnumLiteral(conflictTypeEEnum, ConflictType.MULTIPLICITY)
		addEEnumLiteral(conflictTypeEEnum, ConflictType.VISIBILITY)
		initEEnum(conflictSolvabilityEEnum, ConflictSolvability, "ConflictSolvability")
		addEEnumLiteral(conflictSolvabilityEEnum, ConflictSolvability.AUTOMATICALLY)
		addEEnumLiteral(conflictSolvabilityEEnum, ConflictSolvability.MANUAL)
	} // ConflictPackageImpl
}
