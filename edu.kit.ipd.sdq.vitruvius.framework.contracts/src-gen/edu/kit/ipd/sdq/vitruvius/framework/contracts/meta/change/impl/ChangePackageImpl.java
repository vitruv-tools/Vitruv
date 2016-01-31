/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
    private EClass additiveChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass replaciveChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass additiveAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass replaciveAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass additiveReferenceChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass subtractiveReferenceChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass replaciveReferenceChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType tuidEDataType = null;

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
        ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);
        RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
        CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

        // Create package meta-data objects
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theObjectPackage.createPackageContents();
        theRootPackage.createPackageContents();
        theCompoundPackage.createPackageContents();

        // Initialize created meta-data
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theObjectPackage.initializePackageContents();
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
    public EClass getAdditiveChange() {
        return additiveChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getAdditiveChange__GetNewValue() {
        return additiveChangeEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveChange() {
        return subtractiveChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getSubtractiveChange__GetOldValue() {
        return subtractiveChangeEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReplaciveChange() {
        return replaciveChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAdditiveAttributeChange() {
        return additiveAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAdditiveAttributeChange_NewValue() {
        return (EAttribute)additiveAttributeChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveAttributeChange() {
        return subtractiveAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubtractiveAttributeChange_OldValue() {
        return (EAttribute)subtractiveAttributeChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReplaciveAttributeChange() {
        return replaciveAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAdditiveReferenceChange() {
        return additiveReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAdditiveReferenceChange_NewValue() {
        return (EReference)additiveReferenceChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubtractiveReferenceChange() {
        return subtractiveReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubtractiveReferenceChange_OldTUID() {
        return (EAttribute)subtractiveReferenceChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubtractiveReferenceChange_FeatureName2OldValueMap() {
        return (EAttribute)subtractiveReferenceChangeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReplaciveReferenceChange() {
        return replaciveReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTUID() {
        return tuidEDataType;
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

        additiveChangeEClass = createEClass(ADDITIVE_CHANGE);
        createEOperation(additiveChangeEClass, ADDITIVE_CHANGE___GET_NEW_VALUE);

        subtractiveChangeEClass = createEClass(SUBTRACTIVE_CHANGE);
        createEOperation(subtractiveChangeEClass, SUBTRACTIVE_CHANGE___GET_OLD_VALUE);

        replaciveChangeEClass = createEClass(REPLACIVE_CHANGE);

        additiveAttributeChangeEClass = createEClass(ADDITIVE_ATTRIBUTE_CHANGE);
        createEAttribute(additiveAttributeChangeEClass, ADDITIVE_ATTRIBUTE_CHANGE__NEW_VALUE);

        subtractiveAttributeChangeEClass = createEClass(SUBTRACTIVE_ATTRIBUTE_CHANGE);
        createEAttribute(subtractiveAttributeChangeEClass, SUBTRACTIVE_ATTRIBUTE_CHANGE__OLD_VALUE);

        replaciveAttributeChangeEClass = createEClass(REPLACIVE_ATTRIBUTE_CHANGE);

        additiveReferenceChangeEClass = createEClass(ADDITIVE_REFERENCE_CHANGE);
        createEReference(additiveReferenceChangeEClass, ADDITIVE_REFERENCE_CHANGE__NEW_VALUE);

        subtractiveReferenceChangeEClass = createEClass(SUBTRACTIVE_REFERENCE_CHANGE);
        createEAttribute(subtractiveReferenceChangeEClass, SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID);
        createEAttribute(subtractiveReferenceChangeEClass, SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP);

        replaciveReferenceChangeEClass = createEClass(REPLACIVE_REFERENCE_CHANGE);

        // Create data types
        tuidEDataType = createEDataType(TUID);
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
        ObjectPackage theObjectPackage = (ObjectPackage)EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI);
        RootPackage theRootPackage = (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);
        CompoundPackage theCompoundPackage = (CompoundPackage)EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI);

        // Add subpackages
        getESubpackages().add(theFeaturePackage);
        getESubpackages().add(theObjectPackage);
        getESubpackages().add(theRootPackage);
        getESubpackages().add(theCompoundPackage);

        // Create type parameters
        ETypeParameter additiveChangeEClass_T = addETypeParameter(additiveChangeEClass, "T");
        ETypeParameter subtractiveChangeEClass_T = addETypeParameter(subtractiveChangeEClass, "T");
        ETypeParameter replaciveChangeEClass_T = addETypeParameter(replaciveChangeEClass, "T");
        ETypeParameter additiveAttributeChangeEClass_T = addETypeParameter(additiveAttributeChangeEClass, "T");
        ETypeParameter subtractiveAttributeChangeEClass_T = addETypeParameter(subtractiveAttributeChangeEClass, "T");
        ETypeParameter replaciveAttributeChangeEClass_T = addETypeParameter(replaciveAttributeChangeEClass, "T");
        ETypeParameter additiveReferenceChangeEClass_T = addETypeParameter(additiveReferenceChangeEClass, "T");
        ETypeParameter replaciveReferenceChangeEClass_T = addETypeParameter(replaciveReferenceChangeEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
        additiveChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        subtractiveChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        replaciveChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        additiveAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        subtractiveAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        replaciveAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        additiveReferenceChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        replaciveReferenceChangeEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        additiveChangeEClass.getESuperTypes().add(this.getEChange());
        subtractiveChangeEClass.getESuperTypes().add(this.getEChange());
        g1 = createEGenericType(this.getSubtractiveChange());
        EGenericType g2 = createEGenericType(replaciveChangeEClass_T);
        g1.getETypeArguments().add(g2);
        replaciveChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveChange());
        g2 = createEGenericType(replaciveChangeEClass_T);
        g1.getETypeArguments().add(g2);
        replaciveChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveChange());
        g2 = createEGenericType(additiveAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        additiveAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveChange());
        g2 = createEGenericType(subtractiveAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        subtractiveAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveAttributeChange());
        g2 = createEGenericType(replaciveAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        replaciveAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveAttributeChange());
        g2 = createEGenericType(replaciveAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        replaciveAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveChange());
        g2 = createEGenericType(additiveReferenceChangeEClass_T);
        g1.getETypeArguments().add(g2);
        additiveReferenceChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveChange());
        g2 = createEGenericType(this.getTUID());
        g1.getETypeArguments().add(g2);
        subtractiveReferenceChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getSubtractiveReferenceChange());
        replaciveReferenceChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getAdditiveReferenceChange());
        g2 = createEGenericType(replaciveReferenceChangeEClass_T);
        g1.getETypeArguments().add(g2);
        replaciveReferenceChangeEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes, features, and operations; add parameters
        initEClass(eChangeEClass, EChange.class, "EChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(additiveChangeEClass, AdditiveChange.class, "AdditiveChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        EOperation op = initEOperation(getAdditiveChange__GetNewValue(), null, "getNewValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(additiveChangeEClass_T);
        initEOperation(op, g1);

        initEClass(subtractiveChangeEClass, SubtractiveChange.class, "SubtractiveChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        op = initEOperation(getSubtractiveChange__GetOldValue(), null, "getOldValue", 1, 1, IS_UNIQUE, IS_ORDERED);
        g1 = createEGenericType(subtractiveChangeEClass_T);
        initEOperation(op, g1);

        initEClass(replaciveChangeEClass, ReplaciveChange.class, "ReplaciveChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(additiveAttributeChangeEClass, AdditiveAttributeChange.class, "AdditiveAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(additiveAttributeChangeEClass_T);
        initEAttribute(getAdditiveAttributeChange_NewValue(), g1, "newValue", null, 1, 1, AdditiveAttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(subtractiveAttributeChangeEClass, SubtractiveAttributeChange.class, "SubtractiveAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(subtractiveAttributeChangeEClass_T);
        initEAttribute(getSubtractiveAttributeChange_OldValue(), g1, "oldValue", null, 1, 1, SubtractiveAttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(replaciveAttributeChangeEClass, ReplaciveAttributeChange.class, "ReplaciveAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(additiveReferenceChangeEClass, AdditiveReferenceChange.class, "AdditiveReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(additiveReferenceChangeEClass_T);
        initEReference(getAdditiveReferenceChange_NewValue(), g1, null, "newValue", null, 1, 1, AdditiveReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(subtractiveReferenceChangeEClass, SubtractiveReferenceChange.class, "SubtractiveReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSubtractiveReferenceChange_OldTUID(), this.getTUID(), "oldTUID", null, 1, 1, SubtractiveReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(ecorePackage.getEMap());
        g2 = createEGenericType(ecorePackage.getEString());
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        initEAttribute(getSubtractiveReferenceChange_FeatureName2OldValueMap(), g1, "featureName2OldValueMap", null, 1, 1, SubtractiveReferenceChange.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(replaciveReferenceChangeEClass, ReplaciveReferenceChange.class, "ReplaciveReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Initialize data types
        initEDataType(tuidEDataType, edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID.class, "TUID", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //ChangePackageImpl
