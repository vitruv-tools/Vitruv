/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

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
    private EClass eAtomicChangeEClass = null;

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
    private EClass additiveEAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveEAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass additiveEReferenceChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveEReferenceChangeEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#eNS_URI
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
    public EClass getEAtomicChange() {
        return eAtomicChangeEClass;
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
    public EClass getAdditiveEAttributeChange() {
        return additiveEAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAdditiveEAttributeChange_NewValue() {
        return (EAttribute)additiveEAttributeChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveEAttributeChange() {
        return subtractiveEAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubtractiveEAttributeChange_OldValue() {
        return (EAttribute)subtractiveEAttributeChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAdditiveEReferenceChange() {
        return additiveEReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAdditiveEReferenceChange_NewValue() {
        return (EReference)additiveEReferenceChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAdditiveEReferenceChange_IsCreate() {
        return (EAttribute)additiveEReferenceChangeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveEReferenceChange() {
        return subtractiveEReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSubtractiveEReferenceChange_OldValue() {
        return (EReference)subtractiveEReferenceChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubtractiveEReferenceChange_IsDelete() {
        return (EAttribute)subtractiveEReferenceChangeEClass.getEStructuralFeatures().get(1);
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

        eAtomicChangeEClass = createEClass(EATOMIC_CHANGE);

        additiveEChangeEClass = createEClass(ADDITIVE_ECHANGE);
        createEOperation(additiveEChangeEClass, ADDITIVE_ECHANGE___GET_NEW_VALUE);

        subtractiveEChangeEClass = createEClass(SUBTRACTIVE_ECHANGE);
        createEOperation(subtractiveEChangeEClass, SUBTRACTIVE_ECHANGE___GET_OLD_VALUE);

        additiveEAttributeChangeEClass = createEClass(ADDITIVE_EATTRIBUTE_CHANGE);
        createEAttribute(additiveEAttributeChangeEClass, ADDITIVE_EATTRIBUTE_CHANGE__NEW_VALUE);

        subtractiveEAttributeChangeEClass = createEClass(SUBTRACTIVE_EATTRIBUTE_CHANGE);
        createEAttribute(subtractiveEAttributeChangeEClass, SUBTRACTIVE_EATTRIBUTE_CHANGE__OLD_VALUE);

        additiveEReferenceChangeEClass = createEClass(ADDITIVE_EREFERENCE_CHANGE);
        createEReference(additiveEReferenceChangeEClass, ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE);
        createEAttribute(additiveEReferenceChangeEClass, ADDITIVE_EREFERENCE_CHANGE__IS_CREATE);

        subtractiveEReferenceChangeEClass = createEClass(SUBTRACTIVE_EREFERENCE_CHANGE);
        createEReference(subtractiveEReferenceChangeEClass, SUBTRACTIVE_EREFERENCE_CHANGE__OLD_VALUE);
        createEAttribute(subtractiveEReferenceChangeEClass, SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE);
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
        ETypeParameter additiveEAttributeChangeEClass_T = addETypeParameter(additiveEAttributeChangeEClass, "T");
        ETypeParameter subtractiveEAttributeChangeEClass_T = addETypeParameter(subtractiveEAttributeChangeEClass, "T");
        ETypeParameter additiveEReferenceChangeEClass_T = addETypeParameter(additiveEReferenceChangeEClass, "T");
        ETypeParameter subtractiveEReferenceChangeEClass_T = addETypeParameter(subtractiveEReferenceChangeEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
        additiveEChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        subtractiveEChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        additiveEAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        subtractiveEAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        additiveEReferenceChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        subtractiveEReferenceChangeEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        eAtomicChangeEClass.getESuperTypes().add(this.getEChange());
        additiveEChangeEClass.getESuperTypes().add(this.getEAtomicChange());
        subtractiveEChangeEClass.getESuperTypes().add(this.getEAtomicChange());
        g1 = createEGenericType(this.getAdditiveEChange());
        EGenericType g2 = createEGenericType(additiveEAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        additiveEAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveEChange());
        g2 = createEGenericType(subtractiveEAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        subtractiveEAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveEChange());
        g2 = createEGenericType(additiveEReferenceChangeEClass_T);
        g1.getETypeArguments().add(g2);
        additiveEReferenceChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveEChange());
        g2 = createEGenericType(subtractiveEReferenceChangeEClass_T);
        g1.getETypeArguments().add(g2);
        subtractiveEReferenceChangeEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes, features, and operations; add parameters
        initEClass(eChangeEClass, EChange.class, "EChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(eAtomicChangeEClass, EAtomicChange.class, "EAtomicChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(additiveEChangeEClass, AdditiveEChange.class, "AdditiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        EOperation op = initEOperation(getAdditiveEChange__GetNewValue(), null, "getNewValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(additiveEChangeEClass_T);
        initEOperation(op, g1);

        initEClass(subtractiveEChangeEClass, SubtractiveEChange.class, "SubtractiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        op = initEOperation(getSubtractiveEChange__GetOldValue(), null, "getOldValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(subtractiveEChangeEClass_T);
        initEOperation(op, g1);

        initEClass(additiveEAttributeChangeEClass, AdditiveEAttributeChange.class, "AdditiveEAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(additiveEAttributeChangeEClass_T);
        initEAttribute(getAdditiveEAttributeChange_NewValue(), g1, "newValue", null, 1, 1, AdditiveEAttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(subtractiveEAttributeChangeEClass, SubtractiveEAttributeChange.class, "SubtractiveEAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(subtractiveEAttributeChangeEClass_T);
        initEAttribute(getSubtractiveEAttributeChange_OldValue(), g1, "oldValue", null, 1, 1, SubtractiveEAttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(additiveEReferenceChangeEClass, AdditiveEReferenceChange.class, "AdditiveEReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(additiveEReferenceChangeEClass_T);
        initEReference(getAdditiveEReferenceChange_NewValue(), g1, null, "newValue", null, 1, 1, AdditiveEReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAdditiveEReferenceChange_IsCreate(), ecorePackage.getEBoolean(), "isCreate", null, 1, 1, AdditiveEReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(subtractiveEReferenceChangeEClass, SubtractiveEReferenceChange.class, "SubtractiveEReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(subtractiveEReferenceChangeEClass_T);
        initEReference(getSubtractiveEReferenceChange_OldValue(), g1, null, "oldValue", null, 1, 1, SubtractiveEReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSubtractiveEReferenceChange_IsDelete(), ecorePackage.getEBoolean(), "isDelete", null, 1, 1, SubtractiveEReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //ChangePackageImpl
