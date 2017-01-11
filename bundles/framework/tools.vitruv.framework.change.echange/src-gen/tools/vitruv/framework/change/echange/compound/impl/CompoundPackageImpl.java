/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundAddition;
import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundFactory;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;
import tools.vitruv.framework.change.echange.compound.MoveEObject;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot;
import tools.vitruv.framework.change.echange.compound.ReplaceInEList;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;

import tools.vitruv.framework.change.echange.root.RootPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundPackageImpl extends EPackageImpl implements CompoundPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moveEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass explicitUnsetEFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceInEListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundSubtractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundAdditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createAndInsertRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeAndDeleteRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createAndInsertNonRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeAndDeleteNonRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createAndReplaceAndDeleteNonRootEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CompoundPackageImpl() {
		super(eNS_URI, CompoundFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CompoundPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CompoundPackage init() {
		if (isInited) return (CompoundPackage)EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI);

		// Obtain or create and register package
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CompoundPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ReferencePackage.eINSTANCE.eClass();
		RootPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCompoundPackage.createPackageContents();

		// Initialize created meta-data
		theCompoundPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompoundPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CompoundPackage.eNS_URI, theCompoundPackage);
		return theCompoundPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundEChange() {
		return compoundEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundEChange__GetAtomicChanges() {
		return compoundEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMoveEObject() {
		return moveEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveEObject_SubtractWhereChange() {
		return (EReference)moveEObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveEObject_SubtractWhatChange() {
		return (EReference)moveEObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveEObject_AddWhereChange() {
		return (EReference)moveEObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveEObject_AddWhatChange() {
		return (EReference)moveEObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMoveEObject__GetAtomicChanges() {
		return moveEObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExplicitUnsetEFeature() {
		return explicitUnsetEFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExplicitUnsetEFeature_FeatureChange() {
		return (EReference)explicitUnsetEFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceInEList() {
		return replaceInEListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReplaceInEList_RemoveChange() {
		return (EReference)replaceInEListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReplaceInEList_InsertChange() {
		return (EReference)replaceInEListEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceInEList__GetAtomicChanges() {
		return replaceInEListEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundSubtraction() {
		return compoundSubtractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundSubtraction_SubtractiveChanges() {
		return (EReference)compoundSubtractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundSubtraction__GetAtomicChanges() {
		return compoundSubtractionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundAddition() {
		return compoundAdditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundAddition_AdditiveChanges() {
		return (EReference)compoundAdditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundAddition__GetAtomicChanges() {
		return compoundAdditionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateAndInsertRoot() {
		return createAndInsertRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertRoot_CreateChange() {
		return (EReference)createAndInsertRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertRoot_InsertChange() {
		return (EReference)createAndInsertRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveAndDeleteRoot() {
		return removeAndDeleteRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteRoot_RemoveChange() {
		return (EReference)removeAndDeleteRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteRoot_DeleteChange() {
		return (EReference)removeAndDeleteRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateAndInsertNonRoot() {
		return createAndInsertNonRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertNonRoot_CreateChange() {
		return (EReference)createAndInsertNonRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertNonRoot_InsertChange() {
		return (EReference)createAndInsertNonRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveAndDeleteNonRoot() {
		return removeAndDeleteNonRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteNonRoot_RemoveChange() {
		return (EReference)removeAndDeleteNonRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteNonRoot_DeleteChange() {
		return (EReference)removeAndDeleteNonRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateAndReplaceAndDeleteNonRoot() {
		return createAndReplaceAndDeleteNonRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndReplaceAndDeleteNonRoot_CreateChange() {
		return (EReference)createAndReplaceAndDeleteNonRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndReplaceAndDeleteNonRoot_ReplaceChange() {
		return (EReference)createAndReplaceAndDeleteNonRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndReplaceAndDeleteNonRoot_DeleteChange() {
		return (EReference)createAndReplaceAndDeleteNonRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundFactory getCompoundFactory() {
		return (CompoundFactory)getEFactoryInstance();
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
		compoundEChangeEClass = createEClass(COMPOUND_ECHANGE);
		createEOperation(compoundEChangeEClass, COMPOUND_ECHANGE___GET_ATOMIC_CHANGES);

		moveEObjectEClass = createEClass(MOVE_EOBJECT);
		createEReference(moveEObjectEClass, MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE);
		createEReference(moveEObjectEClass, MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE);
		createEReference(moveEObjectEClass, MOVE_EOBJECT__ADD_WHERE_CHANGE);
		createEReference(moveEObjectEClass, MOVE_EOBJECT__ADD_WHAT_CHANGE);
		createEOperation(moveEObjectEClass, MOVE_EOBJECT___GET_ATOMIC_CHANGES);

		explicitUnsetEFeatureEClass = createEClass(EXPLICIT_UNSET_EFEATURE);
		createEReference(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE__FEATURE_CHANGE);

		replaceInEListEClass = createEClass(REPLACE_IN_ELIST);
		createEReference(replaceInEListEClass, REPLACE_IN_ELIST__REMOVE_CHANGE);
		createEReference(replaceInEListEClass, REPLACE_IN_ELIST__INSERT_CHANGE);
		createEOperation(replaceInEListEClass, REPLACE_IN_ELIST___GET_ATOMIC_CHANGES);

		compoundSubtractionEClass = createEClass(COMPOUND_SUBTRACTION);
		createEReference(compoundSubtractionEClass, COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES);
		createEOperation(compoundSubtractionEClass, COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES);

		compoundAdditionEClass = createEClass(COMPOUND_ADDITION);
		createEReference(compoundAdditionEClass, COMPOUND_ADDITION__ADDITIVE_CHANGES);
		createEOperation(compoundAdditionEClass, COMPOUND_ADDITION___GET_ATOMIC_CHANGES);

		createAndInsertRootEClass = createEClass(CREATE_AND_INSERT_ROOT);
		createEReference(createAndInsertRootEClass, CREATE_AND_INSERT_ROOT__CREATE_CHANGE);
		createEReference(createAndInsertRootEClass, CREATE_AND_INSERT_ROOT__INSERT_CHANGE);

		removeAndDeleteRootEClass = createEClass(REMOVE_AND_DELETE_ROOT);
		createEReference(removeAndDeleteRootEClass, REMOVE_AND_DELETE_ROOT__REMOVE_CHANGE);
		createEReference(removeAndDeleteRootEClass, REMOVE_AND_DELETE_ROOT__DELETE_CHANGE);

		createAndInsertNonRootEClass = createEClass(CREATE_AND_INSERT_NON_ROOT);
		createEReference(createAndInsertNonRootEClass, CREATE_AND_INSERT_NON_ROOT__CREATE_CHANGE);
		createEReference(createAndInsertNonRootEClass, CREATE_AND_INSERT_NON_ROOT__INSERT_CHANGE);

		removeAndDeleteNonRootEClass = createEClass(REMOVE_AND_DELETE_NON_ROOT);
		createEReference(removeAndDeleteNonRootEClass, REMOVE_AND_DELETE_NON_ROOT__REMOVE_CHANGE);
		createEReference(removeAndDeleteNonRootEClass, REMOVE_AND_DELETE_NON_ROOT__DELETE_CHANGE);

		createAndReplaceAndDeleteNonRootEClass = createEClass(CREATE_AND_REPLACE_AND_DELETE_NON_ROOT);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		ReferencePackage theReferencePackage = (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);
		EobjectPackage theEobjectPackage = (EobjectPackage)EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
		RootPackage theRootPackage = (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);

		// Create type parameters
		ETypeParameter moveEObjectEClass_A = addETypeParameter(moveEObjectEClass, "A");
		ETypeParameter moveEObjectEClass_B = addETypeParameter(moveEObjectEClass, "B");
		ETypeParameter moveEObjectEClass_T = addETypeParameter(moveEObjectEClass, "T");
		ETypeParameter explicitUnsetEFeatureEClass_A = addETypeParameter(explicitUnsetEFeatureEClass, "A");
		ETypeParameter explicitUnsetEFeatureEClass_F = addETypeParameter(explicitUnsetEFeatureEClass, "F");
		ETypeParameter explicitUnsetEFeatureEClass_T = addETypeParameter(explicitUnsetEFeatureEClass, "T");
		ETypeParameter explicitUnsetEFeatureEClass_S = addETypeParameter(explicitUnsetEFeatureEClass, "S");
		ETypeParameter replaceInEListEClass_A = addETypeParameter(replaceInEListEClass, "A");
		ETypeParameter replaceInEListEClass_F = addETypeParameter(replaceInEListEClass, "F");
		ETypeParameter replaceInEListEClass_T = addETypeParameter(replaceInEListEClass, "T");
		ETypeParameter replaceInEListEClass_R = addETypeParameter(replaceInEListEClass, "R");
		ETypeParameter replaceInEListEClass_I = addETypeParameter(replaceInEListEClass, "I");
		ETypeParameter compoundSubtractionEClass_T = addETypeParameter(compoundSubtractionEClass, "T");
		ETypeParameter compoundAdditionEClass_T = addETypeParameter(compoundAdditionEClass, "T");
		ETypeParameter createAndInsertRootEClass_T = addETypeParameter(createAndInsertRootEClass, "T");
		ETypeParameter removeAndDeleteRootEClass_T = addETypeParameter(removeAndDeleteRootEClass, "T");
		ETypeParameter createAndInsertNonRootEClass_A = addETypeParameter(createAndInsertNonRootEClass, "A");
		ETypeParameter createAndInsertNonRootEClass_T = addETypeParameter(createAndInsertNonRootEClass, "T");
		ETypeParameter removeAndDeleteNonRootEClass_A = addETypeParameter(removeAndDeleteNonRootEClass, "A");
		ETypeParameter removeAndDeleteNonRootEClass_T = addETypeParameter(removeAndDeleteNonRootEClass, "T");
		ETypeParameter createAndReplaceAndDeleteNonRootEClass_A = addETypeParameter(createAndReplaceAndDeleteNonRootEClass, "A");
		ETypeParameter createAndReplaceAndDeleteNonRootEClass_T = addETypeParameter(createAndReplaceAndDeleteNonRootEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theEcorePackage.getEObject());
		moveEObjectEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		moveEObjectEClass_B.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		moveEObjectEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		explicitUnsetEFeatureEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEStructuralFeature());
		explicitUnsetEFeatureEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		explicitUnsetEFeatureEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
		EGenericType g2 = createEGenericType(explicitUnsetEFeatureEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(explicitUnsetEFeatureEClass_F);
		g1.getETypeArguments().add(g2);
		explicitUnsetEFeatureEClass_S.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(explicitUnsetEFeatureEClass_T);
		g1.getETypeArguments().add(g2);
		explicitUnsetEFeatureEClass_S.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceInEListEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEStructuralFeature());
		replaceInEListEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceInEListEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromListEChange());
		g2 = createEGenericType(replaceInEListEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_F);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_R.getEBounds().add(g1);
		g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
		g2 = createEGenericType(replaceInEListEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_F);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_R.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(replaceInEListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_R.getEBounds().add(g1);
		g1 = createEGenericType(theListPackage.getInsertInListEChange());
		g2 = createEGenericType(replaceInEListEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_F);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_I.getEBounds().add(g1);
		g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
		g2 = createEGenericType(replaceInEListEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceInEListEClass_F);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_I.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(replaceInEListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceInEListEClass_I.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		compoundSubtractionEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		compoundAdditionEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndInsertRootEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeAndDeleteRootEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndInsertNonRootEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndInsertNonRootEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeAndDeleteNonRootEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeAndDeleteNonRootEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndReplaceAndDeleteNonRootEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndReplaceAndDeleteNonRootEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		compoundEChangeEClass.getESuperTypes().add(theEChangePackage.getEChange());
		moveEObjectEClass.getESuperTypes().add(this.getCompoundEChange());
		g1 = createEGenericType(this.getCompoundSubtraction());
		g2 = createEGenericType(explicitUnsetEFeatureEClass_T);
		g1.getETypeArguments().add(g2);
		explicitUnsetEFeatureEClass.getEGenericSuperTypes().add(g1);
		replaceInEListEClass.getESuperTypes().add(this.getCompoundEChange());
		compoundSubtractionEClass.getESuperTypes().add(this.getCompoundEChange());
		compoundAdditionEClass.getESuperTypes().add(this.getCompoundEChange());
		createAndInsertRootEClass.getESuperTypes().add(this.getCompoundEChange());
		removeAndDeleteRootEClass.getESuperTypes().add(this.getCompoundEChange());
		createAndInsertNonRootEClass.getESuperTypes().add(this.getCompoundEChange());
		removeAndDeleteNonRootEClass.getESuperTypes().add(this.getCompoundEChange());
		createAndReplaceAndDeleteNonRootEClass.getESuperTypes().add(this.getCompoundEChange());

		// Initialize classes, features, and operations; add parameters
		initEClass(compoundEChangeEClass, CompoundEChange.class, "CompoundEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getCompoundEChange__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(moveEObjectEClass, MoveEObject.class, "MoveEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theReferencePackage.getUpdateReferenceEChange());
		g2 = createEGenericType(moveEObjectEClass_A);
		g1.getETypeArguments().add(g2);
		initEReference(getMoveEObject_SubtractWhereChange(), g1, null, "subtractWhereChange", null, 0, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getEObjectSubtractedEChange());
		g2 = createEGenericType(moveEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getMoveEObject_SubtractWhatChange(), g1, null, "subtractWhatChange", null, 1, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theReferencePackage.getUpdateReferenceEChange());
		g2 = createEGenericType(moveEObjectEClass_B);
		g1.getETypeArguments().add(g2);
		initEReference(getMoveEObject_AddWhereChange(), g1, null, "addWhereChange", null, 0, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getEObjectAddedEChange());
		g2 = createEGenericType(moveEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getMoveEObject_AddWhatChange(), g1, null, "addWhatChange", null, 1, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getMoveEObject__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(explicitUnsetEFeatureEClass, ExplicitUnsetEFeature.class, "ExplicitUnsetEFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(explicitUnsetEFeatureEClass_S);
		initEReference(getExplicitUnsetEFeature_FeatureChange(), g1, null, "featureChange", null, 1, -1, ExplicitUnsetEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(replaceInEListEClass, ReplaceInEList.class, "ReplaceInEList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(replaceInEListEClass_R);
		initEReference(getReplaceInEList_RemoveChange(), g1, null, "removeChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(replaceInEListEClass_I);
		initEReference(getReplaceInEList_InsertChange(), g1, null, "insertChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getReplaceInEList__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundSubtractionEClass, CompoundSubtraction.class, "CompoundSubtraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(compoundSubtractionEClass_T);
		g2.setEUpperBound(g3);
		initEReference(getCompoundSubtraction_SubtractiveChanges(), g1, null, "subtractiveChanges", null, 1, -1, CompoundSubtraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundSubtraction__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundAdditionEClass, CompoundAddition.class, "CompoundAddition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(compoundAdditionEClass_T);
		g2.setEUpperBound(g3);
		initEReference(getCompoundAddition_AdditiveChanges(), g1, null, "additiveChanges", null, 1, -1, CompoundAddition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundAddition__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(createAndInsertRootEClass, CreateAndInsertRoot.class, "CreateAndInsertRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEobjectPackage.getCreateEObject());
		g2 = createEGenericType(createAndInsertRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndInsertRoot_CreateChange(), g1, null, "createChange", null, 1, 1, CreateAndInsertRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theRootPackage.getInsertRootEObject());
		g2 = createEGenericType(createAndInsertRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndInsertRoot_InsertChange(), g1, null, "insertChange", null, 1, 1, CreateAndInsertRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(removeAndDeleteRootEClass, RemoveAndDeleteRoot.class, "RemoveAndDeleteRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theRootPackage.getRemoveRootEObject());
		g2 = createEGenericType(removeAndDeleteRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getRemoveAndDeleteRoot_RemoveChange(), g1, null, "removeChange", null, 1, 1, RemoveAndDeleteRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getDeleteEObject());
		g2 = createEGenericType(removeAndDeleteRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getRemoveAndDeleteRoot_DeleteChange(), g1, null, "deleteChange", null, 1, 1, RemoveAndDeleteRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createAndInsertNonRootEClass, CreateAndInsertNonRoot.class, "CreateAndInsertNonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEobjectPackage.getCreateEObject());
		g2 = createEGenericType(createAndInsertNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndInsertNonRoot_CreateChange(), g1, null, "createChange", null, 1, 1, CreateAndInsertNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theReferencePackage.getInsertEReference());
		g2 = createEGenericType(createAndInsertNonRootEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(createAndInsertNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndInsertNonRoot_InsertChange(), g1, null, "insertChange", null, 1, 1, CreateAndInsertNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(removeAndDeleteNonRootEClass, RemoveAndDeleteNonRoot.class, "RemoveAndDeleteNonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theReferencePackage.getRemoveEReference());
		g2 = createEGenericType(removeAndDeleteNonRootEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getRemoveAndDeleteNonRoot_RemoveChange(), g1, null, "removeChange", null, 1, 1, RemoveAndDeleteNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getDeleteEObject());
		g2 = createEGenericType(removeAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getRemoveAndDeleteNonRoot_DeleteChange(), g1, null, "deleteChange", null, 1, 1, RemoveAndDeleteNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createAndReplaceAndDeleteNonRootEClass, CreateAndReplaceAndDeleteNonRoot.class, "CreateAndReplaceAndDeleteNonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEobjectPackage.getCreateEObject());
		g2 = createEGenericType(createAndReplaceAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndReplaceAndDeleteNonRoot_CreateChange(), g1, null, "createChange", null, 1, 1, CreateAndReplaceAndDeleteNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theReferencePackage.getReplaceSingleValuedEReference());
		g2 = createEGenericType(createAndReplaceAndDeleteNonRootEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(createAndReplaceAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndReplaceAndDeleteNonRoot_ReplaceChange(), g1, null, "replaceChange", null, 1, 1, CreateAndReplaceAndDeleteNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getDeleteEObject());
		g2 = createEGenericType(createAndReplaceAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndReplaceAndDeleteNonRoot_DeleteChange(), g1, null, "deleteChange", null, 1, 1, CreateAndReplaceAndDeleteNonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompoundPackageImpl
