/**
 */
package tools.vitruv.framework.change.uuid.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import tools.vitruv.framework.change.uuid.UuidFactory;
import tools.vitruv.framework.change.uuid.UuidPackage;
import tools.vitruv.framework.change.uuid.UuidToEObjectRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UuidPackageImpl extends EPackageImpl implements UuidPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uuidToEObjectRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uuidToEObjectMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectToUuidMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType uuidEDataType = null;

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
	 * @see tools.vitruv.framework.change.uuid.UuidPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UuidPackageImpl() {
		super(eNS_URI, UuidFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link UuidPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UuidPackage init() {
		if (isInited) return (UuidPackage)EPackage.Registry.INSTANCE.getEPackage(UuidPackage.eNS_URI);

		// Obtain or create and register package
		UuidPackageImpl theUuidPackage = (UuidPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UuidPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UuidPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theUuidPackage.createPackageContents();

		// Initialize created meta-data
		theUuidPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUuidPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UuidPackage.eNS_URI, theUuidPackage);
		return theUuidPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUuidToEObjectRepository() {
		return uuidToEObjectRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUuidToEObjectRepository_UuidToEObject() {
		return (EReference)uuidToEObjectRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUuidToEObjectRepository_EObjectToUuid() {
		return (EReference)uuidToEObjectRepositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUuidToEObjectMapEntry() {
		return uuidToEObjectMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUuidToEObjectMapEntry_Key() {
		return (EAttribute)uuidToEObjectMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUuidToEObjectMapEntry_Value() {
		return (EReference)uuidToEObjectMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectToUuidMapEntry() {
		return eObjectToUuidMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEObjectToUuidMapEntry_Value() {
		return (EAttribute)eObjectToUuidMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToUuidMapEntry_Key() {
		return (EReference)eObjectToUuidMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getUuid() {
		return uuidEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UuidFactory getUuidFactory() {
		return (UuidFactory)getEFactoryInstance();
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
		uuidToEObjectRepositoryEClass = createEClass(UUID_TO_EOBJECT_REPOSITORY);
		createEReference(uuidToEObjectRepositoryEClass, UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT);
		createEReference(uuidToEObjectRepositoryEClass, UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID);

		uuidToEObjectMapEntryEClass = createEClass(UUID_TO_EOBJECT_MAP_ENTRY);
		createEAttribute(uuidToEObjectMapEntryEClass, UUID_TO_EOBJECT_MAP_ENTRY__KEY);
		createEReference(uuidToEObjectMapEntryEClass, UUID_TO_EOBJECT_MAP_ENTRY__VALUE);

		eObjectToUuidMapEntryEClass = createEClass(EOBJECT_TO_UUID_MAP_ENTRY);
		createEAttribute(eObjectToUuidMapEntryEClass, EOBJECT_TO_UUID_MAP_ENTRY__VALUE);
		createEReference(eObjectToUuidMapEntryEClass, EOBJECT_TO_UUID_MAP_ENTRY__KEY);

		// Create data types
		uuidEDataType = createEDataType(UUID);
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
		initEClass(uuidToEObjectRepositoryEClass, UuidToEObjectRepository.class, "UuidToEObjectRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUuidToEObjectRepository_UuidToEObject(), this.getUuidToEObjectMapEntry(), null, "uuidToEObject", null, 0, -1, UuidToEObjectRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUuidToEObjectRepository_EObjectToUuid(), this.getEObjectToUuidMapEntry(), null, "eObjectToUuid", null, 0, -1, UuidToEObjectRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uuidToEObjectMapEntryEClass, Map.Entry.class, "UuidToEObjectMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUuidToEObjectMapEntry_Key(), this.getUuid(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUuidToEObjectMapEntry_Value(), ecorePackage.getEObject(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectToUuidMapEntryEClass, Map.Entry.class, "EObjectToUuidMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEObjectToUuidMapEntry_Value(), this.getUuid(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEObjectToUuidMapEntry_Key(), ecorePackage.getEObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(uuidEDataType, String.class, "Uuid", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //UuidPackageImpl
