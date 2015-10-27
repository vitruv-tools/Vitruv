/**
 */
package multicontainment_b.impl;

import multicontainment_b.ChildB1;
import multicontainment_b.ChildB2;
import multicontainment_b.Identified;
import multicontainment_b.Multicontainment_bFactory;
import multicontainment_b.Multicontainment_bPackage;
import multicontainment_b.RootB;

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
public class Multicontainment_bPackageImpl extends EPackageImpl implements Multicontainment_bPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootBEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childB1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childB2EClass = null;

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
	 * @see multicontainment_b.Multicontainment_bPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Multicontainment_bPackageImpl() {
		super(eNS_URI, Multicontainment_bFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Multicontainment_bPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Multicontainment_bPackage init() {
		if (isInited) return (Multicontainment_bPackage)EPackage.Registry.INSTANCE.getEPackage(Multicontainment_bPackage.eNS_URI);

		// Obtain or create and register package
		Multicontainment_bPackageImpl theMulticontainment_bPackage = (Multicontainment_bPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Multicontainment_bPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Multicontainment_bPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMulticontainment_bPackage.createPackageContents();

		// Initialize created meta-data
		theMulticontainment_bPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMulticontainment_bPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Multicontainment_bPackage.eNS_URI, theMulticontainment_bPackage);
		return theMulticontainment_bPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootB() {
		return rootBEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootB_ChildrenB1a() {
		return (EReference)rootBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootB_ChildrenB1b() {
		return (EReference)rootBEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootB_ChildrenB2a() {
		return (EReference)rootBEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildB1() {
		return childB1EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildB1_Name() {
		return (EAttribute)childB1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildB2() {
		return childB2EClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildB2_Name() {
		return (EAttribute)childB2EClass.getEStructuralFeatures().get(0);
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
	public Multicontainment_bFactory getMulticontainment_bFactory() {
		return (Multicontainment_bFactory)getEFactoryInstance();
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
		rootBEClass = createEClass(ROOT_B);
		createEReference(rootBEClass, ROOT_B__CHILDREN_B1A);
		createEReference(rootBEClass, ROOT_B__CHILDREN_B1B);
		createEReference(rootBEClass, ROOT_B__CHILDREN_B2A);

		childB1EClass = createEClass(CHILD_B1);
		createEAttribute(childB1EClass, CHILD_B1__NAME);

		childB2EClass = createEClass(CHILD_B2);
		createEAttribute(childB2EClass, CHILD_B2__NAME);

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
		rootBEClass.getESuperTypes().add(this.getIdentified());
		childB1EClass.getESuperTypes().add(this.getIdentified());
		childB2EClass.getESuperTypes().add(this.getIdentified());

		// Initialize classes, features, and operations; add parameters
		initEClass(rootBEClass, RootB.class, "RootB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRootB_ChildrenB1a(), this.getChildB1(), null, "childrenB1a", null, 0, 1, RootB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootB_ChildrenB1b(), this.getChildB1(), null, "childrenB1b", null, 0, 1, RootB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootB_ChildrenB2a(), this.getChildB2(), null, "childrenB2a", null, 0, 1, RootB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childB1EClass, ChildB1.class, "ChildB1", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChildB1_Name(), ecorePackage.getEString(), "name", null, 0, 1, ChildB1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childB2EClass, ChildB2.class, "ChildB2", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChildB2_Name(), ecorePackage.getEString(), "name", null, 0, 1, ChildB2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identifiedEClass, Identified.class, "Identified", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentified_Id(), ecorePackage.getEString(), "id", null, 1, 1, Identified.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Multicontainment_bPackageImpl
