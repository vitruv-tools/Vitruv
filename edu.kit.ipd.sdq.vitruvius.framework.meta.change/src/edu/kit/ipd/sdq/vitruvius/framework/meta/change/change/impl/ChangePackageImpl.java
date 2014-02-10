/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEReference;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
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
    private EClass eObjectChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eFeatureChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eAttributeChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass eReferenceChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass createEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass createNonRootEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass createRootEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass updateEFeatureEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass unsetEFeatureEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass updateEAttributeEClass = null;

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
    private EClass updateEContainmentReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteNonRootEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteRootEObjectEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#eNS_URI
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

        // Create package meta-data objects
        theChangePackage.createPackageContents();

        // Initialize created meta-data
        theChangePackage.initializePackageContents();

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
    public EClass getEObjectChange() {
        return eObjectChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEObjectChange_ChangedEObject() {
        return (EReference)eObjectChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEFeatureChange() {
        return eFeatureChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEFeatureChange_AffectedFeature() {
        return (EReference)eFeatureChangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEFeatureChange_AffectedEObject() {
        return (EReference)eFeatureChangeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEAttributeChange() {
        return eAttributeChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEReferenceChange() {
        return eReferenceChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCreateEObject() {
        return createEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCreateNonRootEObject() {
        return createNonRootEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCreateRootEObject() {
        return createRootEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUpdateEFeature() {
        return updateEFeatureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getUpdateEFeature_NewValue() {
        return (EReference)updateEFeatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnsetEFeature() {
        return unsetEFeatureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUpdateEAttribute() {
        return updateEAttributeEClass;
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
    public EClass getUpdateEContainmentReference() {
        return updateEContainmentReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteEObject() {
        return deleteEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteNonRootEObject() {
        return deleteNonRootEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteRootEObject() {
        return deleteRootEObjectEClass;
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

        eObjectChangeEClass = createEClass(EOBJECT_CHANGE);
        createEReference(eObjectChangeEClass, EOBJECT_CHANGE__CHANGED_EOBJECT);

        eFeatureChangeEClass = createEClass(EFEATURE_CHANGE);
        createEReference(eFeatureChangeEClass, EFEATURE_CHANGE__AFFECTED_FEATURE);
        createEReference(eFeatureChangeEClass, EFEATURE_CHANGE__AFFECTED_EOBJECT);

        eAttributeChangeEClass = createEClass(EATTRIBUTE_CHANGE);

        eReferenceChangeEClass = createEClass(EREFERENCE_CHANGE);

        createEObjectEClass = createEClass(CREATE_EOBJECT);

        createNonRootEObjectEClass = createEClass(CREATE_NON_ROOT_EOBJECT);

        createRootEObjectEClass = createEClass(CREATE_ROOT_EOBJECT);

        updateEFeatureEClass = createEClass(UPDATE_EFEATURE);
        createEReference(updateEFeatureEClass, UPDATE_EFEATURE__NEW_VALUE);

        unsetEFeatureEClass = createEClass(UNSET_EFEATURE);

        updateEAttributeEClass = createEClass(UPDATE_EATTRIBUTE);

        updateEReferenceEClass = createEClass(UPDATE_EREFERENCE);

        updateEContainmentReferenceEClass = createEClass(UPDATE_ECONTAINMENT_REFERENCE);

        deleteEObjectEClass = createEClass(DELETE_EOBJECT);

        deleteNonRootEObjectEClass = createEClass(DELETE_NON_ROOT_EOBJECT);

        deleteRootEObjectEClass = createEClass(DELETE_ROOT_EOBJECT);
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

        // Create type parameters
        ETypeParameter eFeatureChangeEClass_T = addETypeParameter(eFeatureChangeEClass, "T");
        ETypeParameter eAttributeChangeEClass_T = addETypeParameter(eAttributeChangeEClass, "T");
        ETypeParameter eReferenceChangeEClass_T = addETypeParameter(eReferenceChangeEClass, "T");
        ETypeParameter createNonRootEObjectEClass_T = addETypeParameter(createNonRootEObjectEClass, "T");
        ETypeParameter createNonRootEObjectEClass_U = addETypeParameter(createNonRootEObjectEClass, "U");
        ETypeParameter updateEFeatureEClass_T = addETypeParameter(updateEFeatureEClass, "T");
        ETypeParameter unsetEFeatureEClass_T = addETypeParameter(unsetEFeatureEClass, "T");
        ETypeParameter updateEAttributeEClass_T = addETypeParameter(updateEAttributeEClass, "T");
        ETypeParameter updateEAttributeEClass_U = addETypeParameter(updateEAttributeEClass, "U");
        ETypeParameter updateEReferenceEClass_T = addETypeParameter(updateEReferenceEClass, "T");
        ETypeParameter updateEReferenceEClass_U = addETypeParameter(updateEReferenceEClass, "U");
        ETypeParameter updateEContainmentReferenceEClass_T = addETypeParameter(updateEContainmentReferenceEClass, "T");
        ETypeParameter updateEContainmentReferenceEClass_U = addETypeParameter(updateEContainmentReferenceEClass, "U");
        ETypeParameter deleteNonRootEObjectEClass_T = addETypeParameter(deleteNonRootEObjectEClass, "T");
        ETypeParameter deleteNonRootEObjectEClass_U = addETypeParameter(deleteNonRootEObjectEClass, "U");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        eFeatureChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEAttribute());
        eAttributeChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEReference());
        eReferenceChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        createNonRootEObjectEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEReference());
        createNonRootEObjectEClass_U.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        unsetEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateEAttributeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEAttribute());
        updateEAttributeEClass_U.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        updateEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEReference());
        updateEReferenceEClass_U.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        updateEContainmentReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEReference());
        updateEContainmentReferenceEClass_U.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        deleteNonRootEObjectEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEReference());
        deleteNonRootEObjectEClass_U.getEBounds().add(g1);

        // Add supertypes to classes
        eObjectChangeEClass.getESuperTypes().add(this.getEChange());
        eFeatureChangeEClass.getESuperTypes().add(this.getEChange());
        g1 = createEGenericType(this.getEFeatureChange());
        EGenericType g2 = createEGenericType(eAttributeChangeEClass_T);
        g1.getETypeArguments().add(g2);
        eAttributeChangeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getEFeatureChange());
        g2 = createEGenericType(eReferenceChangeEClass_T);
        g1.getETypeArguments().add(g2);
        eReferenceChangeEClass.getEGenericSuperTypes().add(g1);
        createEObjectEClass.getESuperTypes().add(this.getEObjectChange());
        g1 = createEGenericType(this.getCreateEObject());
        createNonRootEObjectEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEContainmentReference());
        g2 = createEGenericType(createNonRootEObjectEClass_T);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(createNonRootEObjectEClass_U);
        g1.getETypeArguments().add(g2);
        createNonRootEObjectEClass.getEGenericSuperTypes().add(g1);
        createRootEObjectEClass.getESuperTypes().add(this.getCreateEObject());
        g1 = createEGenericType(this.getEFeatureChange());
        g2 = createEGenericType(unsetEFeatureEClass_T);
        g1.getETypeArguments().add(g2);
        unsetEFeatureEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEFeature());
        g2 = createEGenericType(updateEAttributeEClass_T);
        g1.getETypeArguments().add(g2);
        updateEAttributeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getEAttributeChange());
        g2 = createEGenericType(updateEAttributeEClass_U);
        g1.getETypeArguments().add(g2);
        updateEAttributeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEFeature());
        g2 = createEGenericType(updateEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        updateEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getEReferenceChange());
        g2 = createEGenericType(updateEReferenceEClass_U);
        g1.getETypeArguments().add(g2);
        updateEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEReference());
        g2 = createEGenericType(updateEContainmentReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(updateEContainmentReferenceEClass_U);
        g1.getETypeArguments().add(g2);
        updateEContainmentReferenceEClass.getEGenericSuperTypes().add(g1);
        deleteEObjectEClass.getESuperTypes().add(this.getEObjectChange());
        g1 = createEGenericType(this.getDeleteRootEObject());
        deleteNonRootEObjectEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEContainmentReference());
        g2 = createEGenericType(deleteNonRootEObjectEClass_T);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(deleteNonRootEObjectEClass_U);
        g1.getETypeArguments().add(g2);
        deleteNonRootEObjectEClass.getEGenericSuperTypes().add(g1);
        deleteRootEObjectEClass.getESuperTypes().add(this.getDeleteEObject());

        // Initialize classes and features; add operations and parameters
        initEClass(eChangeEClass, EChange.class, "EChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(eObjectChangeEClass, EObjectChange.class, "EObjectChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getEObjectChange_ChangedEObject(), ecorePackage.getEObject(), null, "changedEObject", null, 1, 1, EObjectChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(eFeatureChangeEClass, EFeatureChange.class, "EFeatureChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(eFeatureChangeEClass_T);
        initEReference(getEFeatureChange_AffectedFeature(), g1, null, "affectedFeature", null, 1, 1, EFeatureChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEFeatureChange_AffectedEObject(), ecorePackage.getEObject(), null, "affectedEObject", null, 1, 1, EFeatureChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(eAttributeChangeEClass, EAttributeChange.class, "EAttributeChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(eReferenceChangeEClass, EReferenceChange.class, "EReferenceChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(createEObjectEClass, CreateEObject.class, "CreateEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(createNonRootEObjectEClass, CreateNonRootEObject.class, "CreateNonRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(createRootEObjectEClass, CreateRootEObject.class, "CreateRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateEFeatureEClass, UpdateEFeature.class, "UpdateEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(updateEFeatureEClass_T);
        initEReference(getUpdateEFeature_NewValue(), g1, null, "newValue", null, 1, 1, UpdateEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unsetEFeatureEClass, UnsetEFeature.class, "UnsetEFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateEAttributeEClass, UpdateEAttribute.class, "UpdateEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateEReferenceEClass, UpdateEReference.class, "UpdateEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateEContainmentReferenceEClass, UpdateEContainmentReference.class, "UpdateEContainmentReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(deleteEObjectEClass, DeleteEObject.class, "DeleteEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(deleteNonRootEObjectEClass, DeleteNonRootEObject.class, "DeleteNonRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(deleteRootEObjectEClass, DeleteRootEObject.class, "DeleteRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //ChangePackageImpl
