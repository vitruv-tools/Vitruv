/**
 */
package SimpleAtoBTest_SimplestA.impl;

import SimpleAtoBTest_SimplestA.AChild;
import SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAFactory;
import SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage;

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
public class SimpleAtoBTest_SimplestAPackageImpl extends EPackageImpl implements SimpleAtoBTest_SimplestAPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aChildEClass = null;

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
	 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SimpleAtoBTest_SimplestAPackageImpl() {
		super(eNS_URI, SimpleAtoBTest_SimplestAFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SimpleAtoBTest_SimplestAPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SimpleAtoBTest_SimplestAPackage init() {
		if (isInited) return (SimpleAtoBTest_SimplestAPackage)EPackage.Registry.INSTANCE.getEPackage(SimpleAtoBTest_SimplestAPackage.eNS_URI);

		// Obtain or create and register package
		SimpleAtoBTest_SimplestAPackageImpl theSimpleAtoBTest_SimplestAPackage = (SimpleAtoBTest_SimplestAPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SimpleAtoBTest_SimplestAPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SimpleAtoBTest_SimplestAPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theSimpleAtoBTest_SimplestAPackage.createPackageContents();

		// Initialize created meta-data
		theSimpleAtoBTest_SimplestAPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSimpleAtoBTest_SimplestAPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SimpleAtoBTest_SimplestAPackage.eNS_URI, theSimpleAtoBTest_SimplestAPackage);
		return theSimpleAtoBTest_SimplestAPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getA() {
		return aEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getA_A_name() {
		return (EAttribute)aEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getA_A_children() {
		return (EReference)aEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAChild() {
		return aChildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAChild_AChild_name() {
		return (EAttribute)aChildEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestAFactory getSimpleAtoBTest_SimplestAFactory() {
		return (SimpleAtoBTest_SimplestAFactory)getEFactoryInstance();
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
		aEClass = createEClass(A);
		createEAttribute(aEClass, A__ANAME);
		createEReference(aEClass, A__ACHILDREN);

		aChildEClass = createEClass(ACHILD);
		createEAttribute(aChildEClass, ACHILD__ACHILD_NAME);
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
		initEClass(aEClass, SimpleAtoBTest_SimplestA.A.class, "A", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getA_A_name(), ecorePackage.getEString(), "a_name", null, 0, 1, SimpleAtoBTest_SimplestA.A.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getA_A_children(), this.getAChild(), null, "a_children", null, 0, -1, SimpleAtoBTest_SimplestA.A.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(aChildEClass, AChild.class, "AChild", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAChild_AChild_name(), ecorePackage.getEString(), "aChild_name", null, 0, 1, AChild.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SimpleAtoBTest_SimplestAPackageImpl
