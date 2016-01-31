/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.EObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl;

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
public class ObjectPackageImpl extends EPackageImpl implements ObjectPackage {
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
    private EClass createEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteEObjectEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ObjectPackageImpl() {
        super(eNS_URI, ObjectFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ObjectPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ObjectPackage init() {
        if (isInited) return (ObjectPackage)EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI);

        // Obtain or create and register package
        ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ObjectPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
        FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
        RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
        CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

        // Create package meta-data objects
        theObjectPackage.createPackageContents();
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theRootPackage.createPackageContents();
        theCompoundPackage.createPackageContents();

        // Initialize created meta-data
        theObjectPackage.initializePackageContents();
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theRootPackage.initializePackageContents();
        theCompoundPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theObjectPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ObjectPackage.eNS_URI, theObjectPackage);
        return theObjectPackage;
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
    public EClass getCreateEObject() {
        return createEObjectEClass;
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
    public ObjectFactory getObjectFactory() {
        return (ObjectFactory)getEFactoryInstance();
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
        eObjectChangeEClass = createEClass(EOBJECT_CHANGE);

        createEObjectEClass = createEClass(CREATE_EOBJECT);

        deleteEObjectEClass = createEClass(DELETE_EOBJECT);
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
        ChangePackage theChangePackage = (ChangePackage)EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI);

        // Create type parameters
        ETypeParameter createEObjectEClass_T = addETypeParameter(createEObjectEClass, "T");
        ETypeParameter deleteEObjectEClass_T = addETypeParameter(deleteEObjectEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEObject());
        createEObjectEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        deleteEObjectEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        eObjectChangeEClass.getESuperTypes().add(theChangePackage.getEChange());
        g1 = createEGenericType(this.getEObjectChange());
        createEObjectEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theChangePackage.getAdditiveReferenceChange());
        EGenericType g2 = createEGenericType(createEObjectEClass_T);
        g1.getETypeArguments().add(g2);
        createEObjectEClass.getEGenericSuperTypes().add(g1);
        deleteEObjectEClass.getESuperTypes().add(this.getEObjectChange());
        deleteEObjectEClass.getESuperTypes().add(theChangePackage.getSubtractiveReferenceChange());

        // Initialize classes, features, and operations; add parameters
        initEClass(eObjectChangeEClass, EObjectChange.class, "EObjectChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(createEObjectEClass, CreateEObject.class, "CreateEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(deleteEObjectEClass, DeleteEObject.class, "DeleteEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    }

} //ObjectPackageImpl
