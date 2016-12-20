/**
 */
package tools.vitruv.framework.change.echange.feature.single.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.single.SingleFactory;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SinglePackageImpl extends EPackageImpl implements SinglePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceSingleValuedFeatureEChangeEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.feature.single.SinglePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SinglePackageImpl() {
		super(eNS_URI, SingleFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SinglePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SinglePackage init() {
		if (isInited) return (SinglePackage)EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI);

		// Obtain or create and register package
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SinglePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		FeaturePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSinglePackage.createPackageContents();

		// Initialize created meta-data
		theSinglePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSinglePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SinglePackage.eNS_URI, theSinglePackage);
		return theSinglePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceSingleValuedFeatureEChange() {
		return replaceSingleValuedFeatureEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceSingleValuedFeatureEChange__IsFromNonDefaultValue() {
		return replaceSingleValuedFeatureEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceSingleValuedFeatureEChange__IsToNonDefaultValue() {
		return replaceSingleValuedFeatureEChangeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleFactory getSingleFactory() {
		return (SingleFactory)getEFactoryInstance();
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
		replaceSingleValuedFeatureEChangeEClass = createEClass(REPLACE_SINGLE_VALUED_FEATURE_ECHANGE);
		createEOperation(replaceSingleValuedFeatureEChangeEClass, REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE);
		createEOperation(replaceSingleValuedFeatureEChangeEClass, REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Create type parameters
		ETypeParameter replaceSingleValuedFeatureEChangeEClass_A = addETypeParameter(replaceSingleValuedFeatureEChangeEClass, "A");
		ETypeParameter replaceSingleValuedFeatureEChangeEClass_F = addETypeParameter(replaceSingleValuedFeatureEChangeEClass, "F");
		ETypeParameter replaceSingleValuedFeatureEChangeEClass_T = addETypeParameter(replaceSingleValuedFeatureEChangeEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theEcorePackage.getEObject());
		replaceSingleValuedFeatureEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEStructuralFeature());
		replaceSingleValuedFeatureEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		replaceSingleValuedFeatureEChangeEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theFeaturePackage.getUpdateSingleValuedFeatureEChange());
		EGenericType g2 = createEGenericType(replaceSingleValuedFeatureEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedFeatureEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedFeatureEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(replaceSingleValuedFeatureEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedFeatureEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(replaceSingleValuedFeatureEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedFeatureEChangeEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(replaceSingleValuedFeatureEChangeEClass, ReplaceSingleValuedFeatureEChange.class, "ReplaceSingleValuedFeatureEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getReplaceSingleValuedFeatureEChange__IsFromNonDefaultValue(), theEcorePackage.getEBoolean(), "isFromNonDefaultValue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getReplaceSingleValuedFeatureEChange__IsToNonDefaultValue(), theEcorePackage.getEBoolean(), "isToNonDefaultValue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SinglePackageImpl
