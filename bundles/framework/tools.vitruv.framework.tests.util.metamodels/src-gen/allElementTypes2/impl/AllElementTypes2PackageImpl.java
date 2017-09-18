/**
 */
package allElementTypes2.impl;

import allElementTypes2.AllElementTypes2Factory;
import allElementTypes2.AllElementTypes2Package;
import allElementTypes2.Identified2;
import allElementTypes2.NonRoot2;
import allElementTypes2.NonRootObjectContainerHelper2;
import allElementTypes2.Root2;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AllElementTypes2PackageImpl extends EPackageImpl implements AllElementTypes2Package {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass root2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonRoot2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonRootObjectContainerHelper2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identified2EClass = null;

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
	 * @see allElementTypes2.AllElementTypes2Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AllElementTypes2PackageImpl() {
		super(eNS_URI, AllElementTypes2Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AllElementTypes2Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AllElementTypes2Package init() {
		if (isInited) return (AllElementTypes2Package)EPackage.Registry.INSTANCE.getEPackage(AllElementTypes2Package.eNS_URI);

		// Obtain or create and register package
		AllElementTypes2PackageImpl theAllElementTypes2Package = (AllElementTypes2PackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AllElementTypes2PackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AllElementTypes2PackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAllElementTypes2Package.createPackageContents();

		// Initialize created meta-data
		theAllElementTypes2Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAllElementTypes2Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AllElementTypes2Package.eNS_URI, theAllElementTypes2Package);
		return theAllElementTypes2Package;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRoot2() {
		return root2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoot2_SingleValuedEAttribute2() {
		return (EAttribute)root2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot2_SingleValuedNonContainmentEReference2() {
		return (EReference)root2EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot2_SingleValuedContainmentEReference2() {
		return (EReference)root2EClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoot2_MultiValuedEAttribute2() {
		return (EAttribute)root2EClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot2_MultiValuedNonContainmentEReference2() {
		return (EReference)root2EClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot2_MultiValuedContainmentEReference2() {
		return (EReference)root2EClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot2_NonRootObjectContainerHelper() {
		return (EReference)root2EClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonRoot2() {
		return nonRoot2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonRootObjectContainerHelper2() {
		return nonRootObjectContainerHelper2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNonRootObjectContainerHelper2_NonRootObjectsContainment2() {
		return (EReference)nonRootObjectContainerHelper2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdentified2() {
		return identified2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentified2_Id2() {
		return (EAttribute)identified2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypes2Factory getAllElementTypes2Factory() {
		return (AllElementTypes2Factory)getEFactoryInstance();
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
		root2EClass = createEClass(ROOT2);
		createEAttribute(root2EClass, ROOT2__SINGLE_VALUED_EATTRIBUTE2);
		createEReference(root2EClass, ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2);
		createEReference(root2EClass, ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2);
		createEAttribute(root2EClass, ROOT2__MULTI_VALUED_EATTRIBUTE2);
		createEReference(root2EClass, ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2);
		createEReference(root2EClass, ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2);
		createEReference(root2EClass, ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER);

		nonRoot2EClass = createEClass(NON_ROOT2);

		nonRootObjectContainerHelper2EClass = createEClass(NON_ROOT_OBJECT_CONTAINER_HELPER2);
		createEReference(nonRootObjectContainerHelper2EClass, NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2);

		identified2EClass = createEClass(IDENTIFIED2);
		createEAttribute(identified2EClass, IDENTIFIED2__ID2);
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
		root2EClass.getESuperTypes().add(this.getIdentified2());
		nonRoot2EClass.getESuperTypes().add(this.getIdentified2());
		nonRootObjectContainerHelper2EClass.getESuperTypes().add(this.getIdentified2());

		// Initialize classes, features, and operations; add parameters
		initEClass(root2EClass, Root2.class, "Root2", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRoot2_SingleValuedEAttribute2(), ecorePackage.getEIntegerObject(), "singleValuedEAttribute2", "0", 0, 1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot2_SingleValuedNonContainmentEReference2(), this.getNonRoot2(), null, "singleValuedNonContainmentEReference2", null, 0, 1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot2_SingleValuedContainmentEReference2(), this.getNonRoot2(), null, "singleValuedContainmentEReference2", null, 0, 1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoot2_MultiValuedEAttribute2(), ecorePackage.getEIntegerObject(), "multiValuedEAttribute2", null, 0, -1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot2_MultiValuedNonContainmentEReference2(), this.getNonRoot2(), null, "multiValuedNonContainmentEReference2", null, 0, -1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot2_MultiValuedContainmentEReference2(), this.getNonRoot2(), null, "multiValuedContainmentEReference2", null, 0, -1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot2_NonRootObjectContainerHelper(), this.getNonRootObjectContainerHelper2(), null, "nonRootObjectContainerHelper", null, 1, 1, Root2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nonRoot2EClass, NonRoot2.class, "NonRoot2", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nonRootObjectContainerHelper2EClass, NonRootObjectContainerHelper2.class, "NonRootObjectContainerHelper2", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNonRootObjectContainerHelper2_NonRootObjectsContainment2(), this.getNonRoot2(), null, "nonRootObjectsContainment2", null, 0, -1, NonRootObjectContainerHelper2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identified2EClass, Identified2.class, "Identified2", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentified2_Id2(), ecorePackage.getEString(), "id2", null, 1, 1, Identified2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //AllElementTypes2PackageImpl
