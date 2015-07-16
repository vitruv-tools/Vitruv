/**
 */
package SimpleAtoBTest_SimplestB.impl;

import SimpleAtoBTest_SimplestB.BChild;
import SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBFactory;
import SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage;

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
public class SimpleAtoBTest_SimplestBPackageImpl extends EPackageImpl implements SimpleAtoBTest_SimplestBPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bChildEClass = null;

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
	 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SimpleAtoBTest_SimplestBPackageImpl() {
		super(eNS_URI, SimpleAtoBTest_SimplestBFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SimpleAtoBTest_SimplestBPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SimpleAtoBTest_SimplestBPackage init() {
		if (isInited) return (SimpleAtoBTest_SimplestBPackage)EPackage.Registry.INSTANCE.getEPackage(SimpleAtoBTest_SimplestBPackage.eNS_URI);

		// Obtain or create and register package
		SimpleAtoBTest_SimplestBPackageImpl theSimpleAtoBTest_SimplestBPackage = (SimpleAtoBTest_SimplestBPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SimpleAtoBTest_SimplestBPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SimpleAtoBTest_SimplestBPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theSimpleAtoBTest_SimplestBPackage.createPackageContents();

		// Initialize created meta-data
		theSimpleAtoBTest_SimplestBPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSimpleAtoBTest_SimplestBPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SimpleAtoBTest_SimplestBPackage.eNS_URI, theSimpleAtoBTest_SimplestBPackage);
		return theSimpleAtoBTest_SimplestBPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getB() {
		return bEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getB_B_name() {
		return (EAttribute)bEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getB_B_children() {
		return (EReference)bEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBChild() {
		return bChildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBChild_BChild_name() {
		return (EAttribute)bChildEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestBFactory getSimpleAtoBTest_SimplestBFactory() {
		return (SimpleAtoBTest_SimplestBFactory)getEFactoryInstance();
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
		bEClass = createEClass(B);
		createEAttribute(bEClass, B__BNAME);
		createEReference(bEClass, B__BCHILDREN);

		bChildEClass = createEClass(BCHILD);
		createEAttribute(bChildEClass, BCHILD__BCHILD_NAME);
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
		initEClass(bEClass, SimpleAtoBTest_SimplestB.B.class, "B", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getB_B_name(), ecorePackage.getEString(), "b_name", null, 0, 1, SimpleAtoBTest_SimplestB.B.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getB_B_children(), this.getBChild(), null, "b_children", null, 0, -1, SimpleAtoBTest_SimplestB.B.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bChildEClass, BChild.class, "BChild", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBChild_BChild_name(), ecorePackage.getEString(), "bChild_name", null, 0, 1, BChild.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SimpleAtoBTest_SimplestBPackageImpl
