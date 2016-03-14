/**
 */
package edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl;

import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationFactory;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IntegrationPackageImpl extends EPackageImpl implements IntegrationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integrationCorrespondenceEClass = null;

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
	 * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IntegrationPackageImpl() {
		super(eNS_URI, IntegrationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IntegrationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IntegrationPackage init() {
		if (isInited) return (IntegrationPackage)EPackage.Registry.INSTANCE.getEPackage(IntegrationPackage.eNS_URI);

		// Obtain or create and register package
		IntegrationPackageImpl theIntegrationPackage = (IntegrationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IntegrationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IntegrationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		CorrespondencePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIntegrationPackage.createPackageContents();

		// Initialize created meta-data
		theIntegrationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIntegrationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IntegrationPackage.eNS_URI, theIntegrationPackage);
		return theIntegrationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegrationCorrespondence() {
		return integrationCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationFactory getIntegrationFactory() {
		return (IntegrationFactory)getEFactoryInstance();
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
		integrationCorrespondenceEClass = createEClass(INTEGRATION_CORRESPONDENCE);
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
		integrationCorrespondenceEClass.getESuperTypes().add(theCorrespondencePackage.getCorrespondence());

		// Initialize classes, features, and operations; add parameters
		initEClass(integrationCorrespondenceEClass, IntegrationCorrespondence.class, "IntegrationCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //IntegrationPackageImpl
