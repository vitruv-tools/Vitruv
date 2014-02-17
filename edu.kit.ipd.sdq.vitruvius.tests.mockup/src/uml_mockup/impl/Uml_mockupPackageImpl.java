/**
 */
package uml_mockup.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import uml_mockup.Interface;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;
import uml_mockup.Uml_mockupPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Uml_mockupPackageImpl extends EPackageImpl implements Uml_mockupPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass uPackageEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass interfaceEClass = null;

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
     * @see uml_mockup.Uml_mockupPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private Uml_mockupPackageImpl() {
        super(eNS_URI, Uml_mockupFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link Uml_mockupPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static Uml_mockupPackage init() {
        if (isInited) return (Uml_mockupPackage)EPackage.Registry.INSTANCE.getEPackage(Uml_mockupPackage.eNS_URI);

        // Obtain or create and register package
        Uml_mockupPackageImpl theUml_mockupPackage = (Uml_mockupPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Uml_mockupPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Uml_mockupPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theUml_mockupPackage.createPackageContents();

        // Initialize created meta-data
        theUml_mockupPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theUml_mockupPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(Uml_mockupPackage.eNS_URI, theUml_mockupPackage);
        return theUml_mockupPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUPackage() {
        return uPackageEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getUPackage_Interfaces() {
        return (EReference)uPackageEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getInterface() {
        return interfaceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Uml_mockupFactory getUml_mockupFactory() {
        return (Uml_mockupFactory)getEFactoryInstance();
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
        uPackageEClass = createEClass(UPACKAGE);
        createEReference(uPackageEClass, UPACKAGE__INTERFACES);

        interfaceEClass = createEClass(INTERFACE);
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

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes, features, and operations; add parameters
        initEClass(uPackageEClass, UPackage.class, "UPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getUPackage_Interfaces(), this.getInterface(), null, "interfaces", null, 0, -1, UPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(interfaceEClass, Interface.class, "Interface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //Uml_mockupPackageImpl
