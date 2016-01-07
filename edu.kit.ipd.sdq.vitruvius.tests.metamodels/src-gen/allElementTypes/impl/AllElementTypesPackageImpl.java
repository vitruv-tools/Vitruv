/**
 */
package allElementTypes.impl;

import allElementTypes.AllElementTypesFactory;
import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AllElementTypesPackageImpl extends EPackageImpl implements AllElementTypesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonRootObjectContainerHelperEClass = null;

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
	 * @see allElementTypes.AllElementTypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AllElementTypesPackageImpl() {
		super(eNS_URI, AllElementTypesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AllElementTypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AllElementTypesPackage init() {
		if (isInited) return (AllElementTypesPackage)EPackage.Registry.INSTANCE.getEPackage(AllElementTypesPackage.eNS_URI);

		// Obtain or create and register package
		AllElementTypesPackageImpl theAllElementTypesPackage = (AllElementTypesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AllElementTypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AllElementTypesPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAllElementTypesPackage.createPackageContents();

		// Initialize created meta-data
		theAllElementTypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAllElementTypesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AllElementTypesPackage.eNS_URI, theAllElementTypesPackage);
		return theAllElementTypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRoot() {
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoot_SingleValuedEAttribute() {
		return (EAttribute)rootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot_SingleValuedNonContainmentEReference() {
		return (EReference)rootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot_SingleValuedContainmentEReference() {
		return (EReference)rootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoot_MultiValuedEAttribute() {
		return (EAttribute)rootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot_MultiValuedNonContainmentEReference() {
		return (EReference)rootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot_MultiValuedContainmentEReference() {
		return (EReference)rootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoot_NonRootObjectContainerHelper() {
		return (EReference)rootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoot_Id() {
		return (EAttribute)rootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRoot__SingleValuedOperation() {
		return rootEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRoot__MultiValuedOperation() {
		return rootEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonRoot() {
		return nonRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonRoot_Id() {
		return (EAttribute)nonRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonRootObjectContainerHelper() {
		return nonRootObjectContainerHelperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNonRootObjectContainerHelper_NonRootObjectsContainment() {
		return (EReference)nonRootObjectContainerHelperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonRootObjectContainerHelper_Id() {
		return (EAttribute)nonRootObjectContainerHelperEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypesFactory getAllElementTypesFactory() {
		return (AllElementTypesFactory)getEFactoryInstance();
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
		rootEClass = createEClass(ROOT);
		createEAttribute(rootEClass, ROOT__SINGLE_VALUED_EATTRIBUTE);
		createEReference(rootEClass, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE);
		createEReference(rootEClass, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE);
		createEAttribute(rootEClass, ROOT__MULTI_VALUED_EATTRIBUTE);
		createEReference(rootEClass, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE);
		createEReference(rootEClass, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE);
		createEReference(rootEClass, ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER);
		createEAttribute(rootEClass, ROOT__ID);
		createEOperation(rootEClass, ROOT___SINGLE_VALUED_OPERATION);
		createEOperation(rootEClass, ROOT___MULTI_VALUED_OPERATION);

		nonRootEClass = createEClass(NON_ROOT);
		createEAttribute(nonRootEClass, NON_ROOT__ID);

		nonRootObjectContainerHelperEClass = createEClass(NON_ROOT_OBJECT_CONTAINER_HELPER);
		createEReference(nonRootObjectContainerHelperEClass, NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT);
		createEAttribute(nonRootObjectContainerHelperEClass, NON_ROOT_OBJECT_CONTAINER_HELPER__ID);
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
		initEClass(rootEClass, Root.class, "Root", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRoot_SingleValuedEAttribute(), ecorePackage.getEInt(), "singleValuedEAttribute", "0", 0, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot_SingleValuedNonContainmentEReference(), this.getNonRoot(), null, "singleValuedNonContainmentEReference", null, 0, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot_SingleValuedContainmentEReference(), this.getNonRoot(), null, "singleValuedContainmentEReference", null, 0, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoot_MultiValuedEAttribute(), ecorePackage.getEInt(), "multiValuedEAttribute", null, 0, -1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot_MultiValuedNonContainmentEReference(), this.getNonRoot(), null, "multiValuedNonContainmentEReference", null, 0, -1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot_MultiValuedContainmentEReference(), this.getNonRoot(), null, "multiValuedContainmentEReference", null, 0, -1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoot_NonRootObjectContainerHelper(), this.getNonRootObjectContainerHelper(), null, "nonRootObjectContainerHelper", null, 1, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoot_Id(), ecorePackage.getEString(), "id", null, 1, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getRoot__SingleValuedOperation(), null, "singleValuedOperation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getRoot__MultiValuedOperation(), ecorePackage.getEInt(), "multiValuedOperation", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(nonRootEClass, NonRoot.class, "NonRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNonRoot_Id(), ecorePackage.getEString(), "id", null, 1, 1, NonRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nonRootObjectContainerHelperEClass, NonRootObjectContainerHelper.class, "NonRootObjectContainerHelper", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNonRootObjectContainerHelper_NonRootObjectsContainment(), this.getNonRoot(), null, "nonRootObjectsContainment", null, 0, -1, NonRootObjectContainerHelper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNonRootObjectContainerHelper_Id(), ecorePackage.getEString(), "id", null, 1, 1, NonRootObjectContainerHelper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //AllElementTypesPackageImpl
