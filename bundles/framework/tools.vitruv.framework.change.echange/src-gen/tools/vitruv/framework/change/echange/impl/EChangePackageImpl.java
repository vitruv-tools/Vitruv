/**
 */
package tools.vitruv.framework.change.echange.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangeFactory;
import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;

import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;

import tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;

import tools.vitruv.framework.change.echange.feature.reference.impl.ReferencePackageImpl;

import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

import tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl;

import tools.vitruv.framework.change.echange.root.RootPackage;

import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

import tools.vitruv.framework.change.uuid.UuidPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EChangePackageImpl extends EPackageImpl implements EChangePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass additiveEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subtractiveEChangeEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.EChangePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EChangePackageImpl() {
		super(eNS_URI, EChangeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EChangePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EChangePackage init() {
		if (isInited) return (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Obtain or create and register package
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EChangePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		UuidPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) : SinglePackage.eINSTANCE);
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		EobjectPackageImpl theEobjectPackage = (EobjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) instanceof EobjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) : EobjectPackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);

		// Create package meta-data objects
		theEChangePackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theListPackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theEobjectPackage.createPackageContents();
		theRootPackage.createPackageContents();

		// Initialize created meta-data
		theEChangePackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theEobjectPackage.initializePackageContents();
		theRootPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEChangePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EChangePackage.eNS_URI, theEChangePackage);
		return theEChangePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEChange() {
		return eChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEChange__IsResolved() {
		return eChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEChange__GetInvolvedEObjects() {
		return eChangeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdditiveEChange() {
		return additiveEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAdditiveEChange__GetNewValue() {
		return additiveEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubtractiveEChange() {
		return subtractiveEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSubtractiveEChange__GetOldValue() {
		return subtractiveEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChangeFactory getEChangeFactory() {
		return (EChangeFactory)getEFactoryInstance();
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
		eChangeEClass = createEClass(ECHANGE);
		createEOperation(eChangeEClass, ECHANGE___IS_RESOLVED);
		createEOperation(eChangeEClass, ECHANGE___GET_INVOLVED_EOBJECTS);

		additiveEChangeEClass = createEClass(ADDITIVE_ECHANGE);
		createEOperation(additiveEChangeEClass, ADDITIVE_ECHANGE___GET_NEW_VALUE);

		subtractiveEChangeEClass = createEClass(SUBTRACTIVE_ECHANGE);
		createEOperation(subtractiveEChangeEClass, SUBTRACTIVE_ECHANGE___GET_OLD_VALUE);
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
		ETypeParameter additiveEChangeEClass_T = addETypeParameter(additiveEChangeEClass, "T");
		ETypeParameter subtractiveEChangeEClass_T = addETypeParameter(subtractiveEChangeEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
		additiveEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEJavaObject());
		subtractiveEChangeEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		additiveEChangeEClass.getESuperTypes().add(this.getEChange());
		subtractiveEChangeEClass.getESuperTypes().add(this.getEChange());

		// Initialize classes, features, and operations; add parameters
		initEClass(eChangeEClass, EChange.class, "EChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getEChange__IsResolved(), ecorePackage.getEBoolean(), "isResolved", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getEChange__GetInvolvedEObjects(), ecorePackage.getEObject(), "getInvolvedEObjects", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(additiveEChangeEClass, AdditiveEChange.class, "AdditiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getAdditiveEChange__GetNewValue(), null, "getNewValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(additiveEChangeEClass_T);
		initEOperation(op, g1);

		initEClass(subtractiveEChangeEClass, SubtractiveEChange.class, "SubtractiveEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getSubtractiveEChange__GetOldValue(), null, "getOldValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(subtractiveEChangeEClass_T);
		initEOperation(op, g1);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore"
		   });
	}

} //EChangePackageImpl
