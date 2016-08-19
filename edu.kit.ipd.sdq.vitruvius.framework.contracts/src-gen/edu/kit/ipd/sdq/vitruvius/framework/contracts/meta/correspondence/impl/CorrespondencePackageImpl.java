/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.ManualCorrespondence;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CorrespondencePackageImpl extends EPackageImpl implements CorrespondencePackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass correspondencesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass correspondenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass manualCorrespondenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EDataType correspondenceModelEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EDataType tuidEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI
     * value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init
     * init()}, which also performs initialization of the package, or returns the registered
     * package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CorrespondencePackageImpl() {
        super(eNS_URI, CorrespondenceFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others
     * upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link CorrespondencePackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CorrespondencePackage init() {
        if (isInited)
            return (CorrespondencePackage) EPackage.Registry.INSTANCE.getEPackage(CorrespondencePackage.eNS_URI);

        // Obtain or create and register package
        CorrespondencePackageImpl theCorrespondencePackage = (CorrespondencePackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof CorrespondencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new CorrespondencePackageImpl());

        isInited = true;

        // Create package meta-data objects
        theCorrespondencePackage.createPackageContents();

        // Initialize created meta-data
        theCorrespondencePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCorrespondencePackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, theCorrespondencePackage);
        return theCorrespondencePackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCorrespondences() {
        return this.correspondencesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCorrespondences_Correspondences() {
        return (EReference) this.correspondencesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getCorrespondences_CorrespondenceModel() {
        return (EAttribute) this.correspondencesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCorrespondence() {
        return this.correspondenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCorrespondence_Parent() {
        return (EReference) this.correspondenceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCorrespondence_DependsOn() {
        return (EReference) this.correspondenceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCorrespondence_DependedOnBy() {
        return (EReference) this.correspondenceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getCorrespondence_ATUIDs() {
        return (EAttribute) this.correspondenceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getCorrespondence_BTUIDs() {
        return (EAttribute) this.correspondenceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getManualCorrespondence() {
        return this.manualCorrespondenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EDataType getCorrespondenceModel() {
        return this.correspondenceModelEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EDataType getTUID() {
        return this.tuidEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CorrespondenceFactory getCorrespondenceFactory() {
        return (CorrespondenceFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on
     * any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.correspondencesEClass = createEClass(CORRESPONDENCES);
        createEReference(this.correspondencesEClass, CORRESPONDENCES__CORRESPONDENCES);
        createEAttribute(this.correspondencesEClass, CORRESPONDENCES__CORRESPONDENCE_MODEL);

        this.correspondenceEClass = createEClass(CORRESPONDENCE);
        createEReference(this.correspondenceEClass, CORRESPONDENCE__PARENT);
        createEReference(this.correspondenceEClass, CORRESPONDENCE__DEPENDS_ON);
        createEReference(this.correspondenceEClass, CORRESPONDENCE__DEPENDED_ON_BY);
        createEAttribute(this.correspondenceEClass, CORRESPONDENCE__ATUI_DS);
        createEAttribute(this.correspondenceEClass, CORRESPONDENCE__BTUI_DS);

        this.manualCorrespondenceEClass = createEClass(MANUAL_CORRESPONDENCE);

        // Create data types
        this.correspondenceModelEDataType = createEDataType(CORRESPONDENCE_MODEL);
        this.tuidEDataType = createEDataType(TUID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have
     * no affect on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.manualCorrespondenceEClass.getESuperTypes().add(getCorrespondence());

        // Initialize classes and features; add operations and parameters
        initEClass(this.correspondencesEClass, Correspondences.class, "Correspondences", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCorrespondences_Correspondences(), getCorrespondence(), getCorrespondence_Parent(),
                "correspondences", null, 0, -1, Correspondences.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCorrespondences_CorrespondenceModel(), getCorrespondenceModel(), "correspondenceModel", null,
                1, 1, Correspondences.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(this.correspondenceEClass, Correspondence.class, "Correspondence", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCorrespondence_Parent(), getCorrespondences(), getCorrespondences_Correspondences(), "parent",
                null, 1, 1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCorrespondence_DependsOn(), getCorrespondence(), getCorrespondence_DependedOnBy(),
                "dependsOn", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        initEReference(getCorrespondence_DependedOnBy(), getCorrespondence(), getCorrespondence_DependsOn(),
                "dependedOnBy", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        initEAttribute(getCorrespondence_ATUIDs(), getTUID(), "aTUIDs", null, 0, -1, Correspondence.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCorrespondence_BTUIDs(), getTUID(), "bTUIDs", null, 0, -1, Correspondence.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        addEOperation(this.correspondenceEClass, this.ecorePackage.getEObject(), "getAs", 0, -1, IS_UNIQUE, IS_ORDERED);

        addEOperation(this.correspondenceEClass, this.ecorePackage.getEObject(), "getBs", 0, -1, IS_UNIQUE, IS_ORDERED);

        addEOperation(this.correspondenceEClass, getTUID(), "getElementATUID", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(this.correspondenceEClass, getTUID(), "getElementBTUID", 0, 1, IS_UNIQUE, IS_ORDERED);

        EOperation op = addEOperation(this.correspondenceEClass, this.ecorePackage.getEObject(),
                "getElementsForMetamodel", 0, -1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.ecorePackage.getEString(), "metamodelNamespaceUri", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(this.manualCorrespondenceEClass, ManualCorrespondence.class, "ManualCorrespondence", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Initialize data types
        initEDataType(this.correspondenceModelEDataType, CorrespondenceModel.class, "CorrespondenceModel",
                !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(this.tuidEDataType, edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID.class, "TUID",
                IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} // CorrespondencePackageImpl
