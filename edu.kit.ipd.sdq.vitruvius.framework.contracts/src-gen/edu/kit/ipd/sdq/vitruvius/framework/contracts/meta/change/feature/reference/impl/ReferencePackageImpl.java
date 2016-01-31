/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferencePackageImpl extends EPackageImpl implements ReferencePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass updateEReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass replaceSingleValuedEReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass insertEReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass removeEReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass permuteEReferenceValuesEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ReferencePackageImpl() {
        super(eNS_URI, ReferenceFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ReferencePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ReferencePackage init() {
        if (isInited) return (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);

        // Obtain or create and register package
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ReferencePackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
        FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);
        RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
        CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

        // Create package meta-data objects
        theReferencePackage.createPackageContents();
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theObjectPackage.createPackageContents();
        theRootPackage.createPackageContents();
        theCompoundPackage.createPackageContents();

        // Initialize created meta-data
        theReferencePackage.initializePackageContents();
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theObjectPackage.initializePackageContents();
        theRootPackage.initializePackageContents();
        theCompoundPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theReferencePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ReferencePackage.eNS_URI, theReferencePackage);
        return theReferencePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUpdateEReference() {
        return updateEReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getUpdateEReference__IsContainment() {
        return updateEReferenceEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReplaceSingleValuedEReference() {
        return replaceSingleValuedEReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getInsertEReference() {
        return insertEReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRemoveEReference() {
        return removeEReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPermuteEReferenceValues() {
        return permuteEReferenceValuesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReferenceFactory getReferenceFactory() {
        return (ReferenceFactory)getEFactoryInstance();
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
        updateEReferenceEClass = createEClass(UPDATE_EREFERENCE);
        createEOperation(updateEReferenceEClass, UPDATE_EREFERENCE___IS_CONTAINMENT);

        replaceSingleValuedEReferenceEClass = createEClass(REPLACE_SINGLE_VALUED_EREFERENCE);

        insertEReferenceEClass = createEClass(INSERT_EREFERENCE);

        removeEReferenceEClass = createEClass(REMOVE_EREFERENCE);

        permuteEReferenceValuesEClass = createEClass(PERMUTE_EREFERENCE_VALUES);
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
        ChangePackage theChangePackage = (ChangePackage)EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI);
        ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);

        // Create type parameters
        ETypeParameter updateEReferenceEClass_T = addETypeParameter(updateEReferenceEClass, "T");
        ETypeParameter replaceSingleValuedEReferenceEClass_T = addETypeParameter(replaceSingleValuedEReferenceEClass, "T");
        ETypeParameter insertEReferenceEClass_T = addETypeParameter(insertEReferenceEClass, "T");
        ETypeParameter removeEReferenceEClass_T = addETypeParameter(removeEReferenceEClass, "T");
        ETypeParameter permuteEReferenceValuesEClass_T = addETypeParameter(permuteEReferenceValuesEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEObject());
        updateEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        replaceSingleValuedEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        insertEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        removeEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        permuteEReferenceValuesEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        g1 = createEGenericType(theFeaturePackage.getEFeatureChange());
        EGenericType g2 = createEGenericType(ecorePackage.getEReference());
        g1.getETypeArguments().add(g2);
        updateEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theFeaturePackage.getUpdateSingleValuedEFeature());
        g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEReference());
        g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theChangePackage.getReplaciveReferenceChange());
        g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theListPackage.getInsertInEList());
        g2 = createEGenericType(insertEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        insertEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEReference());
        g2 = createEGenericType(insertEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        insertEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theChangePackage.getAdditiveReferenceChange());
        g2 = createEGenericType(insertEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        insertEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theListPackage.getRemoveFromEList());
        g2 = createEGenericType(removeEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        removeEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEReference());
        g2 = createEGenericType(removeEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        removeEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theChangePackage.getSubtractiveReferenceChange());
        removeEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theListPackage.getPermuteEList());
        g2 = createEGenericType(permuteEReferenceValuesEClass_T);
        g1.getETypeArguments().add(g2);
        permuteEReferenceValuesEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEReference());
        g2 = createEGenericType(permuteEReferenceValuesEClass_T);
        g1.getETypeArguments().add(g2);
        permuteEReferenceValuesEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes, features, and operations; add parameters
        initEClass(updateEReferenceEClass, UpdateEReference.class, "UpdateEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEOperation(getUpdateEReference__IsContainment(), ecorePackage.getEBoolean(), "isContainment", 1, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(replaceSingleValuedEReferenceEClass, ReplaceSingleValuedEReference.class, "ReplaceSingleValuedEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(insertEReferenceEClass, InsertEReference.class, "InsertEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(removeEReferenceEClass, RemoveEReference.class, "RemoveEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(permuteEReferenceValuesEClass, PermuteEReferenceValues.class, "PermuteEReferenceValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    }

} //ReferencePackageImpl
