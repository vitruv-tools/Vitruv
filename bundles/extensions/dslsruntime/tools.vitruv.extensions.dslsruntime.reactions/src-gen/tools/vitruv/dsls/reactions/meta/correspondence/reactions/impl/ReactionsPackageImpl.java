/**
 */
package tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence;
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsFactory;
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsPackage;

import tools.vitruv.framework.correspondence.CorrespondencePackage;

import tools.vitruv.framework.uuid.UuidPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReactionsPackageImpl extends EPackageImpl implements ReactionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reactionsCorrespondenceEClass = null;

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
	 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ReactionsPackageImpl() {
		super(eNS_URI, ReactionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ReactionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ReactionsPackage init() {
		if (isInited) return (ReactionsPackage)EPackage.Registry.INSTANCE.getEPackage(ReactionsPackage.eNS_URI);

		// Obtain or create and register package
		ReactionsPackageImpl theReactionsPackage = (ReactionsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ReactionsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ReactionsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		CorrespondencePackage.eINSTANCE.eClass();
		UuidPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theReactionsPackage.createPackageContents();

		// Initialize created meta-data
		theReactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theReactionsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ReactionsPackage.eNS_URI, theReactionsPackage);
		return theReactionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReactionsCorrespondence() {
		return reactionsCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReactionsFactory getReactionsFactory() {
		return (ReactionsFactory)getEFactoryInstance();
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
		reactionsCorrespondenceEClass = createEClass(REACTIONS_CORRESPONDENCE);
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
		CorrespondencePackage theCorrespondencePackage = (CorrespondencePackage)EPackage.Registry.INSTANCE.getEPackage(CorrespondencePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		reactionsCorrespondenceEClass.getESuperTypes().add(theCorrespondencePackage.getCorrespondence());

		// Initialize classes and features; add operations and parameters
		initEClass(reactionsCorrespondenceEClass, ReactionsCorrespondence.class, "ReactionsCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ReactionsPackageImpl
