/**
 */
package multicontainment_a.impl;

import multicontainment_a.ChildA1;
import multicontainment_a.ChildA2;
import multicontainment_a.Identified;
import multicontainment_a.Multicontainment_aFactory;
import multicontainment_a.Multicontainment_aPackage;
import multicontainment_a.RootA;

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
public class Multicontainment_aPackageImpl extends EPackageImpl implements Multicontainment_aPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootAEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childA1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childA2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifiedEClass = null;

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
	 * @see multicontainment_a.Multicontainment_aPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Multicontainment_aPackageImpl() {
		super(eNS_URI, Multicontainment_aFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Multicontainment_aPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Multicontainment_aPackage init() {
		if (isInited) return (Multicontainment_aPackage)EPackage.Registry.INSTANCE.getEPackage(Multicontainment_aPackage.eNS_URI);

		// Obtain or create and register package
		Multicontainment_aPackageImpl theMulticontainment_aPackage = (Multicontainment_aPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Multicontainment_aPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Multicontainment_aPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMulticontainment_aPackage.createPackageContents();

		// Initialize created meta-data
		theMulticontainment_aPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMulticontainment_aPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Multicontainment_aPackage.eNS_URI, theMulticontainment_aPackage);
		return theMulticontainment_aPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootA() {
		return rootAEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootA_ChildrenA1a() {
		return (EReference)rootAEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootA_ChildrenA1b() {
		return (EReference)rootAEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootA_ChildrenA2a() {
		return (EReference)rootAEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildA1() {
		return childA1EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildA1_Name() {
		return (EAttribute)childA1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildA2() {
		return childA2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildA2_Name() {
		return (EAttribute)childA2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdentified() {
		return identifiedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentified_Id() {
		return (EAttribute)identifiedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_aFactory getMulticontainment_aFactory() {
		return (Multicontainment_aFactory)getEFactoryInstance();
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
		rootAEClass = createEClass(ROOT_A);
		createEReference(rootAEClass, ROOT_A__CHILDREN_A1A);
		createEReference(rootAEClass, ROOT_A__CHILDREN_A1B);
		createEReference(rootAEClass, ROOT_A__CHILDREN_A2A);

		childA1EClass = createEClass(CHILD_A1);
		createEAttribute(childA1EClass, CHILD_A1__NAME);

		childA2EClass = createEClass(CHILD_A2);
		createEAttribute(childA2EClass, CHILD_A2__NAME);

		identifiedEClass = createEClass(IDENTIFIED);
		createEAttribute(identifiedEClass, IDENTIFIED__ID);
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
		rootAEClass.getESuperTypes().add(this.getIdentified());
		childA1EClass.getESuperTypes().add(this.getIdentified());
		childA2EClass.getESuperTypes().add(this.getIdentified());

		// Initialize classes, features, and operations; add parameters
		initEClass(rootAEClass, RootA.class, "RootA", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRootA_ChildrenA1a(), this.getChildA1(), null, "childrenA1a", null, 0, 1, RootA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootA_ChildrenA1b(), this.getChildA1(), null, "childrenA1b", null, 0, 1, RootA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootA_ChildrenA2a(), this.getChildA2(), null, "childrenA2a", null, 0, 1, RootA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childA1EClass, ChildA1.class, "ChildA1", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChildA1_Name(), ecorePackage.getEString(), "name", null, 0, 1, ChildA1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childA2EClass, ChildA2.class, "ChildA2", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChildA2_Name(), ecorePackage.getEString(), "name", null, 0, 1, ChildA2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identifiedEClass, Identified.class, "Identified", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentified_Id(), ecorePackage.getEString(), "id", null, 1, 1, Identified.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Multicontainment_aPackageImpl
