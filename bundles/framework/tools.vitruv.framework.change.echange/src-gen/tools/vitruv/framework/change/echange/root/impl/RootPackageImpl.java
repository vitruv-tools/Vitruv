/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.change.echange.root.RootEChange;
import tools.vitruv.framework.change.echange.root.RootFactory;
import tools.vitruv.framework.change.echange.root.RootPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RootPackageImpl extends EPackageImpl implements RootPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass insertRootEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeRootEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eObjEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourceEDataType = null;

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
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RootPackageImpl() {
		super(eNS_URI, RootFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RootPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RootPackage init() {
		if (isInited) return (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);

		// Obtain or create and register package
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RootPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EobjectPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRootPackage.createPackageContents();

		// Initialize created meta-data
		theRootPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRootPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RootPackage.eNS_URI, theRootPackage);
		return theRootPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootEChange() {
		return rootEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRootEChange_Uri() {
		return (EAttribute)rootEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRootEChange_Resource() {
		return (EAttribute)rootEChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRootEChange_Index() {
		return (EAttribute)rootEChangeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRootEChange__IsResolved() {
		return rootEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInsertRootEObject() {
		return insertRootEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInsertRootEObject__IsResolved() {
		return insertRootEObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveRootEObject() {
		return removeRootEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRemoveRootEObject__IsResolved() {
		return removeRootEObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEObj() {
		return eObjEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getResource() {
		return resourceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootFactory getRootFactory() {
		return (RootFactory)getEFactoryInstance();
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
		rootEChangeEClass = createEClass(ROOT_ECHANGE);
		createEAttribute(rootEChangeEClass, ROOT_ECHANGE__URI);
		createEAttribute(rootEChangeEClass, ROOT_ECHANGE__RESOURCE);
		createEAttribute(rootEChangeEClass, ROOT_ECHANGE__INDEX);
		createEOperation(rootEChangeEClass, ROOT_ECHANGE___IS_RESOLVED);

		insertRootEObjectEClass = createEClass(INSERT_ROOT_EOBJECT);
		createEOperation(insertRootEObjectEClass, INSERT_ROOT_EOBJECT___IS_RESOLVED);

		removeRootEObjectEClass = createEClass(REMOVE_ROOT_EOBJECT);
		createEOperation(removeRootEObjectEClass, REMOVE_ROOT_EOBJECT___IS_RESOLVED);

		// Create data types
		eObjEDataType = createEDataType(EOBJ);
		resourceEDataType = createEDataType(RESOURCE);
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
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		EobjectPackage theEobjectPackage = (EobjectPackage)EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI);

		// Create type parameters
		ETypeParameter insertRootEObjectEClass_T = addETypeParameter(insertRootEObjectEClass, "T");
		ETypeParameter removeRootEObjectEClass_T = addETypeParameter(removeRootEObjectEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getEObj());
		insertRootEObjectEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		removeRootEObjectEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		rootEChangeEClass.getESuperTypes().add(theEChangePackage.getAtomicEChange());
		g1 = createEGenericType(this.getRootEChange());
		insertRootEObjectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectAddedEChange());
		EGenericType g2 = createEGenericType(insertRootEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		insertRootEObjectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getRootEChange());
		removeRootEObjectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectSubtractedEChange());
		g2 = createEGenericType(removeRootEObjectEClass_T);
		g1.getETypeArguments().add(g2);
		removeRootEObjectEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(rootEChangeEClass, RootEChange.class, "RootEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRootEChange_Uri(), theEcorePackage.getEString(), "uri", null, 0, 1, RootEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRootEChange_Resource(), this.getResource(), "resource", null, 0, 1, RootEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRootEChange_Index(), theEcorePackage.getEInt(), "index", null, 0, 1, RootEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getRootEChange__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(insertRootEObjectEClass, InsertRootEObject.class, "InsertRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getInsertRootEObject__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(removeRootEObjectEClass, RemoveRootEObject.class, "RemoveRootEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getRemoveRootEObject__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize data types
		initEDataType(eObjEDataType, EObject.class, "EObj", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resourceEDataType, Resource.class, "Resource", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //RootPackageImpl
