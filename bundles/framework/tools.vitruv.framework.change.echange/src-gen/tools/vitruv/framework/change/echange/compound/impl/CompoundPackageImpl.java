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
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;
import tools.vitruv.framework.change.echange.compound.MoveEObject;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot;
import tools.vitruv.framework.change.echange.compound.ReplaceInEList;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;

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
	private EClass createAndInsertEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeAndDeleteEObjectEClass = null;

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
		AttributePackage.eINSTANCE.eClass();
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
	public EOperation getExplicitUnsetEFeature__GetAtomicChanges() {
		return explicitUnsetEFeatureEClass.getEOperations().get(0);
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
	public EClass getCreateAndInsertEObject() {
		return createAndInsertEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertEObject_CreateChange() {
		return (EReference)createAndInsertEObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateAndInsertEObject_InsertChange() {
		return (EReference)createAndInsertEObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCreateAndInsertEObject__GetAtomicChanges() {
		return createAndInsertEObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveAndDeleteEObject() {
		return removeAndDeleteEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteEObject_RemoveChange() {
		return (EReference)removeAndDeleteEObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveAndDeleteEObject_DeleteChange() {
		return (EReference)removeAndDeleteEObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRemoveAndDeleteEObject__GetAtomicChanges() {
		return removeAndDeleteEObjectEClass.getEOperations().get(0);
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
	public EClass getRemoveAndDeleteRoot() {
		return removeAndDeleteRootEClass;
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
	public EClass getRemoveAndDeleteNonRoot() {
		return removeAndDeleteNonRootEClass;
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
	public EOperation getCreateAndReplaceAndDeleteNonRoot__GetAtomicChanges() {
		return createAndReplaceAndDeleteNonRootEClass.getEOperations().get(0);
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
		createEOperation(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES);

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

		createAndInsertEObjectEClass = createEClass(CREATE_AND_INSERT_EOBJECT);
		createEReference(createAndInsertEObjectEClass, CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE);
		createEReference(createAndInsertEObjectEClass, CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE);
		createEOperation(createAndInsertEObjectEClass, CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES);

		removeAndDeleteEObjectEClass = createEClass(REMOVE_AND_DELETE_EOBJECT);
		createEReference(removeAndDeleteEObjectEClass, REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE);
		createEReference(removeAndDeleteEObjectEClass, REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE);
		createEOperation(removeAndDeleteEObjectEClass, REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES);

		createAndInsertRootEClass = createEClass(CREATE_AND_INSERT_ROOT);

		removeAndDeleteRootEClass = createEClass(REMOVE_AND_DELETE_ROOT);

		createAndInsertNonRootEClass = createEClass(CREATE_AND_INSERT_NON_ROOT);

		removeAndDeleteNonRootEClass = createEClass(REMOVE_AND_DELETE_NON_ROOT);

		createAndReplaceAndDeleteNonRootEClass = createEClass(CREATE_AND_REPLACE_AND_DELETE_NON_ROOT);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE);
		createEReference(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE);
		createEOperation(createAndReplaceAndDeleteNonRootEClass, CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES);
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
		AttributePackage theAttributePackage = (AttributePackage)EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		RootPackage theRootPackage = (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);

		// Create type parameters
		ETypeParameter moveEObjectEClass_A = addETypeParameter(moveEObjectEClass, "A");
		ETypeParameter moveEObjectEClass_B = addETypeParameter(moveEObjectEClass, "B");
		ETypeParameter moveEObjectEClass_T = addETypeParameter(moveEObjectEClass, "T");
		ETypeParameter explicitUnsetEFeatureEClass_A = addETypeParameter(explicitUnsetEFeatureEClass, "A");
		ETypeParameter explicitUnsetEFeatureEClass_T = addETypeParameter(explicitUnsetEFeatureEClass, "T");
		ETypeParameter replaceInEListEClass_A = addETypeParameter(replaceInEListEClass, "A");
		ETypeParameter replaceInEListEClass_F = addETypeParameter(replaceInEListEClass, "F");
		ETypeParameter replaceInEListEClass_T = addETypeParameter(replaceInEListEClass, "T");
		ETypeParameter replaceInEListEClass_R = addETypeParameter(replaceInEListEClass, "R");
		ETypeParameter replaceInEListEClass_I = addETypeParameter(replaceInEListEClass, "I");
		ETypeParameter compoundSubtractionEClass_T = addETypeParameter(compoundSubtractionEClass, "T");
		ETypeParameter compoundSubtractionEClass_S = addETypeParameter(compoundSubtractionEClass, "S");
		ETypeParameter compoundAdditionEClass_T = addETypeParameter(compoundAdditionEClass, "T");
		ETypeParameter compoundAdditionEClass_S = addETypeParameter(compoundAdditionEClass, "S");
		ETypeParameter createAndInsertEObjectEClass_T = addETypeParameter(createAndInsertEObjectEClass, "T");
		ETypeParameter createAndInsertEObjectEClass_C = addETypeParameter(createAndInsertEObjectEClass, "C");
		ETypeParameter removeAndDeleteEObjectEClass_T = addETypeParameter(removeAndDeleteEObjectEClass, "T");
		ETypeParameter removeAndDeleteEObjectEClass_C = addETypeParameter(removeAndDeleteEObjectEClass, "C");
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
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		explicitUnsetEFeatureEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceInEListEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEStructuralFeature());
		replaceInEListEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceInEListEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromListEChange());
		EGenericType g2 = createEGenericType(replaceInEListEClass_A);
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
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(compoundSubtractionEClass_T);
		g1.getETypeArguments().add(g2);
		compoundSubtractionEClass_S.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		compoundAdditionEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(compoundAdditionEClass_T);
		g1.getETypeArguments().add(g2);
		compoundAdditionEClass_S.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		createAndInsertEObjectEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectAddedEChange());
		g2 = createEGenericType(createAndInsertEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		createAndInsertEObjectEClass_C.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeAndDeleteEObjectEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectSubtractedEChange());
		g2 = createEGenericType(removeAndDeleteEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		removeAndDeleteEObjectEClass_C.getEBounds().add(g1);
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
		g2 = createEGenericType(theAttributePackage.getSubtractiveAttributeEChange());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(explicitUnsetEFeatureEClass_A);
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType(explicitUnsetEFeatureEClass_T);
		g2.getETypeArguments().add(g3);
		explicitUnsetEFeatureEClass.getEGenericSuperTypes().add(g1);
		replaceInEListEClass.getESuperTypes().add(this.getCompoundEChange());
		compoundSubtractionEClass.getESuperTypes().add(this.getCompoundEChange());
		compoundAdditionEClass.getESuperTypes().add(this.getCompoundEChange());
		createAndInsertEObjectEClass.getESuperTypes().add(this.getCompoundEChange());
		removeAndDeleteEObjectEClass.getESuperTypes().add(this.getCompoundEChange());
		g1 = createEGenericType(this.getCreateAndInsertEObject());
		g2 = createEGenericType(createAndInsertRootEClass_T);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theRootPackage.getInsertRootEObject());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(createAndInsertRootEClass_T);
		g2.getETypeArguments().add(g3);
		createAndInsertRootEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getRemoveAndDeleteEObject());
		g2 = createEGenericType(removeAndDeleteRootEClass_T);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theRootPackage.getRemoveRootEObject());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(removeAndDeleteRootEClass_T);
		g2.getETypeArguments().add(g3);
		removeAndDeleteRootEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getCreateAndInsertEObject());
		g2 = createEGenericType(createAndInsertNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theReferencePackage.getInsertEReference());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(createAndInsertNonRootEClass_A);
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType(createAndInsertNonRootEClass_T);
		g2.getETypeArguments().add(g3);
		createAndInsertNonRootEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getRemoveAndDeleteEObject());
		g2 = createEGenericType(removeAndDeleteNonRootEClass_T);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theReferencePackage.getRemoveEReference());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(removeAndDeleteNonRootEClass_A);
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType(removeAndDeleteNonRootEClass_T);
		g2.getETypeArguments().add(g3);
		removeAndDeleteNonRootEClass.getEGenericSuperTypes().add(g1);
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

		initEOperation(getExplicitUnsetEFeature__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(replaceInEListEClass, ReplaceInEList.class, "ReplaceInEList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(replaceInEListEClass_R);
		initEReference(getReplaceInEList_RemoveChange(), g1, null, "removeChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(replaceInEListEClass_I);
		initEReference(getReplaceInEList_InsertChange(), g1, null, "insertChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getReplaceInEList__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundSubtractionEClass, CompoundSubtraction.class, "CompoundSubtraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(compoundSubtractionEClass_S);
		initEReference(getCompoundSubtraction_SubtractiveChanges(), g1, null, "subtractiveChanges", null, 1, -1, CompoundSubtraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundSubtraction__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundAdditionEClass, CompoundAddition.class, "CompoundAddition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(compoundAdditionEClass_S);
		initEReference(getCompoundAddition_AdditiveChanges(), g1, null, "additiveChanges", null, 1, -1, CompoundAddition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundAddition__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(createAndInsertEObjectEClass, CreateAndInsertEObject.class, "CreateAndInsertEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEobjectPackage.getCreateEObject());
		g2 = createEGenericType(createAndInsertEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getCreateAndInsertEObject_CreateChange(), g1, null, "createChange", null, 1, 1, CreateAndInsertEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(createAndInsertEObjectEClass_C);
		initEReference(getCreateAndInsertEObject_InsertChange(), g1, null, "insertChange", null, 1, 1, CreateAndInsertEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCreateAndInsertEObject__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(removeAndDeleteEObjectEClass, RemoveAndDeleteEObject.class, "RemoveAndDeleteEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(removeAndDeleteEObjectEClass_C);
		initEReference(getRemoveAndDeleteEObject_RemoveChange(), g1, null, "removeChange", null, 1, 1, RemoveAndDeleteEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theEobjectPackage.getDeleteEObject());
		g2 = createEGenericType(removeAndDeleteEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		initEReference(getRemoveAndDeleteEObject_DeleteChange(), g1, null, "deleteChange", null, 1, 1, RemoveAndDeleteEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getRemoveAndDeleteEObject__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(createAndInsertRootEClass, CreateAndInsertRoot.class, "CreateAndInsertRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeAndDeleteRootEClass, RemoveAndDeleteRoot.class, "RemoveAndDeleteRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createAndInsertNonRootEClass, CreateAndInsertNonRoot.class, "CreateAndInsertNonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeAndDeleteNonRootEClass, RemoveAndDeleteNonRoot.class, "RemoveAndDeleteNonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

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

		initEOperation(getCreateAndReplaceAndDeleteNonRoot__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompoundPackageImpl
