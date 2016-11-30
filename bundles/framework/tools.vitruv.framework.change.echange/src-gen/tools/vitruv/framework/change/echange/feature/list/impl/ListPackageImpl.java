/**
 */
package tools.vitruv.framework.change.echange.feature.list.impl;

import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;
import tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl;
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruv.framework.change.echange.feature.list.ListFactory;
import tools.vitruv.framework.change.echange.feature.list.ListPackage;
import tools.vitruv.framework.change.echange.feature.list.PermuteListEChange;
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.impl.ReferencePackageImpl;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;
import tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl;
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;
import tools.vitruv.framework.change.echange.root.RootPackage;
import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ListPackageImpl extends EPackageImpl implements ListPackage {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass updateSingleListEntryEChangeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass insertInListEChangeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass removeFromListEChangeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass permuteListEChangeEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
    private ListPackageImpl() {
		super(eNS_URI, ListFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ListPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static ListPackage init() {
		if (isInited) return (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);

		// Obtain or create and register package
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ListPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) : SinglePackage.eINSTANCE);
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

		// Create package meta-data objects
		theListPackage.createPackageContents();
		theEChangePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theRootPackage.createPackageContents();
		theCompoundPackage.createPackageContents();

		// Initialize created meta-data
		theListPackage.initializePackageContents();
		theEChangePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theRootPackage.initializePackageContents();
		theCompoundPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theListPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ListPackage.eNS_URI, theListPackage);
		return theListPackage;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUpdateSingleListEntryEChange() {
		return updateSingleListEntryEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUpdateSingleListEntryEChange_Index() {
		return (EAttribute)updateSingleListEntryEChangeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getInsertInListEChange() {
		return insertInListEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getRemoveFromListEChange() {
		return removeFromListEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getPermuteListEChange() {
		return permuteListEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPermuteListEChange_NewIndicesForElementsAtOldIndices() {
		return (EAttribute)permuteListEChangeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ListFactory getListFactory() {
		return (ListFactory)getEFactoryInstance();
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
		updateSingleListEntryEChangeEClass = createEClass(UPDATE_SINGLE_LIST_ENTRY_ECHANGE);
		createEAttribute(updateSingleListEntryEChangeEClass, UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX);

		insertInListEChangeEClass = createEClass(INSERT_IN_LIST_ECHANGE);

		removeFromListEChangeEClass = createEClass(REMOVE_FROM_LIST_ECHANGE);

		permuteListEChangeEClass = createEClass(PERMUTE_LIST_ECHANGE);
		createEAttribute(permuteListEChangeEClass, PERMUTE_LIST_ECHANGE__NEW_INDICES_FOR_ELEMENTS_AT_OLD_INDICES);
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
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Create type parameters
		ETypeParameter updateSingleListEntryEChangeEClass_A = addETypeParameter(updateSingleListEntryEChangeEClass, "A");
		ETypeParameter updateSingleListEntryEChangeEClass_F = addETypeParameter(updateSingleListEntryEChangeEClass, "F");
		ETypeParameter insertInListEChangeEClass_A = addETypeParameter(insertInListEChangeEClass, "A");
		ETypeParameter insertInListEChangeEClass_F = addETypeParameter(insertInListEChangeEClass, "F");
		ETypeParameter insertInListEChangeEClass_T = addETypeParameter(insertInListEChangeEClass, "T");
		ETypeParameter removeFromListEChangeEClass_A = addETypeParameter(removeFromListEChangeEClass, "A");
		ETypeParameter removeFromListEChangeEClass_F = addETypeParameter(removeFromListEChangeEClass, "F");
		ETypeParameter removeFromListEChangeEClass_T = addETypeParameter(removeFromListEChangeEClass, "T");
		ETypeParameter permuteListEChangeEClass_A = addETypeParameter(permuteListEChangeEClass, "A");
		ETypeParameter permuteListEChangeEClass_F = addETypeParameter(permuteListEChangeEClass, "F");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEObject());
		updateSingleListEntryEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		updateSingleListEntryEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		insertInListEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		insertInListEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEJavaObject());
		insertInListEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		removeFromListEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		removeFromListEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEJavaObject());
		removeFromListEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		permuteListEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		permuteListEChangeEClass_F.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theFeaturePackage.getUpdateMultiValuedFeatureEChange());
		EGenericType g2 = createEGenericType(updateSingleListEntryEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(updateSingleListEntryEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		updateSingleListEntryEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateSingleListEntryEChange());
		g2 = createEGenericType(insertInListEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(insertInListEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		insertInListEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(insertInListEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		insertInListEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateSingleListEntryEChange());
		g2 = createEGenericType(removeFromListEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeFromListEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		removeFromListEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(removeFromListEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		removeFromListEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFeaturePackage.getUpdateMultiValuedFeatureEChange());
		g2 = createEGenericType(permuteListEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(permuteListEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		permuteListEChangeEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(updateSingleListEntryEChangeEClass, UpdateSingleListEntryEChange.class, "UpdateSingleListEntryEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUpdateSingleListEntryEChange_Index(), ecorePackage.getEInt(), "index", "0", 1, 1, UpdateSingleListEntryEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(insertInListEChangeEClass, InsertInListEChange.class, "InsertInListEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeFromListEChangeEClass, RemoveFromListEChange.class, "RemoveFromListEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(permuteListEChangeEClass, PermuteListEChange.class, "PermuteListEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPermuteListEChange_NewIndicesForElementsAtOldIndices(), ecorePackage.getEInt(), "newIndicesForElementsAtOldIndices", null, 1, -1, PermuteListEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ListPackageImpl
