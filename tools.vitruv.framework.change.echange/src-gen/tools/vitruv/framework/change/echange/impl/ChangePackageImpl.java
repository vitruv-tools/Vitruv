/**
 */
package tools.vitruv.framework.change.echange.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.ChangeFactory;
import tools.vitruv.framework.change.echange.ChangePackage;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;
import tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl;
import tools.vitruv.framework.change.echange.feature.list.ListPackage;
import tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.impl.ReferencePackageImpl;
import tools.vitruv.framework.change.echange.root.RootPackage;
import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChangePackageImpl extends EPackageImpl implements ChangePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass atomicEChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass additiveEChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveEChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eObjectAddedEChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eObjectSubtractedEChangeEClass = null;

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
     * @see tools.vitruv.framework.change.echange.ChangePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ChangePackageImpl() {
        super(eNS_URI, ChangeFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ChangePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ChangePackage init() {
        if (isInited) return (ChangePackage)EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI);

        // Obtain or create and register package
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChangePackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
        RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
        CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

        // Create package meta-data objects
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theRootPackage.createPackageContents();
        theCompoundPackage.createPackageContents();

        // Initialize created meta-data
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theRootPackage.initializePackageContents();
        theCompoundPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theChangePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ChangePackage.eNS_URI, theChangePackage);
        return theChangePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEChange() {
        return eChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAtomicEChange() {
        return atomicEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAdditiveEChange() {
        return additiveEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getAdditiveEChange__GetNewValue() {
        return additiveEChangeEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveEChange() {
        return subtractiveEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getSubtractiveEChange__GetOldValue() {
        return subtractiveEChangeEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEObjectAddedEChange() {
        return eObjectAddedEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEObjectAddedEChange_NewValue() {
        return (EReference)eObjectAddedEChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEObjectAddedEChange_IsCreate() {
        return (EAttribute)eObjectAddedEChangeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEObjectSubtractedEChange() {
        return eObjectSubtractedEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEObjectSubtractedEChange_OldValue() {
        return (EReference)eObjectSubtractedEChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEObjectSubtractedEChange_IsDelete() {
        return (EAttribute)eObjectSubtractedEChangeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChangeFactory getChangeFactory() {
        return (ChangeFactory)getEFactoryInstance();
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
        eChangeEClass = createEClass(ECHANGE);

        atomicEChangeEClass = createEClass(ATOMIC_ECHANGE);

        additiveEChangeEClass = createEClass(ADDITIVE_ECHANGE);
        createEOperation(additiveEChangeEClass, ADDITIVE_ECHANGE___GET_NEW_VALUE);

        subtractiveEChangeEClass = createEClass(SUBTRACTIVE_ECHANGE);
        createEOperation(subtractiveEChangeEClass, SUBTRACTIVE_ECHANGE___GET_OLD_VALUE);

        eObjectAddedEChangeEClass = createEClass(EOBJECT_ADDED_ECHANGE);
        createEReference(eObjectAddedEChangeEClass, EOBJECT_ADDED_ECHANGE__NEW_VALUE);
        createEAttribute(eObjectAddedEChangeEClass, EOBJECT_ADDED_ECHANGE__IS_CREATE);

        eObjectSubtractedEChangeEClass = createEClass(EOBJECT_SUBTRACTED_ECHANGE);
        createEReference(eObjectSubtractedEChangeEClass, EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE);
        createEAttribute(eObjectSubtractedEChangeEClass, EOBJECT_SUBTRACTED_ECHANGE__IS_DELETE);
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
        RootPackage theRootPackage = (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);
        CompoundPackage theCompoundPackage = (CompoundPackage)EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI);

        // Add subpackages
        getESubpackages().add(theFeaturePackage);
        getESubpackages().add(theRootPackage);
        getESubpackages().add(theCompoundPackage);

        // Create type parameters
        ETypeParameter additiveEChangeEClass_T = addETypeParameter(additiveEChangeEClass, "T");
        ETypeParameter subtractiveEChangeEClass_T = addETypeParameter(subtractiveEChangeEClass, "T");
        ETypeParameter eObjectAddedEChangeEClass_T = addETypeParameter(eObjectAddedEChangeEClass, "T");
        ETypeParameter eObjectSubtractedEChangeEClass_T = addETypeParameter(eObjectSubtractedEChangeEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
        additiveEChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        subtractiveEChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        eObjectAddedEChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        eObjectSubtractedEChangeEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        atomicEChangeEClass.getESuperTypes().add(this.getEChange());
        additiveEChangeEClass.getESuperTypes().add(this.getAtomicEChange());
        subtractiveEChangeEClass.getESuperTypes().add(this.getAtomicEChange());
        g1 = createEGenericType(this.getAdditiveEChange());
        EGenericType g2 = createEGenericType(eObjectAddedEChangeEClass_T);
        g1.getETypeArguments().add(g2);
        eObjectAddedEChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveEChange());
        g2 = createEGenericType(eObjectSubtractedEChangeEClass_T);
        g1.getETypeArguments().add(g2);
        eObjectSubtractedEChangeEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes, features, and operations; add parameters
        initEClass(eChangeEClass, EChange.class, "EChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(atomicEChangeEClass, AtomicEChange.class, "AtomicEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(additiveEChangeEClass, AdditiveEChange.class, "AdditiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        EOperation op = initEOperation(getAdditiveEChange__GetNewValue(), null, "getNewValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(additiveEChangeEClass_T);
        initEOperation(op, g1);

        initEClass(subtractiveEChangeEClass, SubtractiveEChange.class, "SubtractiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        op = initEOperation(getSubtractiveEChange__GetOldValue(), null, "getOldValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(subtractiveEChangeEClass_T);
        initEOperation(op, g1);

        initEClass(eObjectAddedEChangeEClass, EObjectAddedEChange.class, "EObjectAddedEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(eObjectAddedEChangeEClass_T);
        initEReference(getEObjectAddedEChange_NewValue(), g1, null, "newValue", null, 1, 1, EObjectAddedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getEObjectAddedEChange_IsCreate(), ecorePackage.getEBoolean(), "isCreate", null, 1, 1, EObjectAddedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(eObjectSubtractedEChangeEClass, EObjectSubtractedEChange.class, "EObjectSubtractedEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(eObjectSubtractedEChangeEClass_T);
        initEReference(getEObjectSubtractedEChange_OldValue(), g1, null, "oldValue", null, 1, 1, EObjectSubtractedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getEObjectSubtractedEChange_IsDelete(), ecorePackage.getEBoolean(), "isDelete", null, 1, 1, EObjectSubtractedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //ChangePackageImpl
