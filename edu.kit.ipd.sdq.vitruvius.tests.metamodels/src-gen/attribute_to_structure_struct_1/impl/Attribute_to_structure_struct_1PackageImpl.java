/**
 */
package attribute_to_structure_struct_1.impl;

import attribute_to_structure_struct_1.Attribute_to_structure_struct_1Factory;
import attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package;
import attribute_to_structure_struct_1.FloatContainer;
import attribute_to_structure_struct_1.Identified;
import attribute_to_structure_struct_1.IntContainer;
import attribute_to_structure_struct_1.ModelB;
import attribute_to_structure_struct_1.StrContainer;
import attribute_to_structure_struct_1.Structured;

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
public class Attribute_to_structure_struct_1PackageImpl extends EPackageImpl implements Attribute_to_structure_struct_1Package {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifiedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelBEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass strContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatContainerEClass = null;

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
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Attribute_to_structure_struct_1PackageImpl() {
		super(eNS_URI, Attribute_to_structure_struct_1Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Attribute_to_structure_struct_1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Attribute_to_structure_struct_1Package init() {
		if (isInited) return (Attribute_to_structure_struct_1Package)EPackage.Registry.INSTANCE.getEPackage(Attribute_to_structure_struct_1Package.eNS_URI);

		// Obtain or create and register package
		Attribute_to_structure_struct_1PackageImpl theAttribute_to_structure_struct_1Package = (Attribute_to_structure_struct_1PackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Attribute_to_structure_struct_1PackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Attribute_to_structure_struct_1PackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAttribute_to_structure_struct_1Package.createPackageContents();

		// Initialize created meta-data
		theAttribute_to_structure_struct_1Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAttribute_to_structure_struct_1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Attribute_to_structure_struct_1Package.eNS_URI, theAttribute_to_structure_struct_1Package);
		return theAttribute_to_structure_struct_1Package;
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
	public EClass getModelB() {
		return modelBEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelB_Content() {
		return (EReference)modelBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructured() {
		return structuredEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStructured_Name() {
		return (EAttribute)structuredEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructured_IntContainer() {
		return (EReference)structuredEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructured_StrContainer() {
		return (EReference)structuredEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructured_FloatContainer() {
		return (EReference)structuredEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntContainer() {
		return intContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntContainer_Value() {
		return (EAttribute)intContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStrContainer() {
		return strContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStrContainer_Value() {
		return (EAttribute)strContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFloatContainer() {
		return floatContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFloatContainer_Value() {
		return (EAttribute)floatContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_struct_1Factory getAttribute_to_structure_struct_1Factory() {
		return (Attribute_to_structure_struct_1Factory)getEFactoryInstance();
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
		identifiedEClass = createEClass(IDENTIFIED);
		createEAttribute(identifiedEClass, IDENTIFIED__ID);

		modelBEClass = createEClass(MODEL_B);
		createEReference(modelBEClass, MODEL_B__CONTENT);

		structuredEClass = createEClass(STRUCTURED);
		createEAttribute(structuredEClass, STRUCTURED__NAME);
		createEReference(structuredEClass, STRUCTURED__INT_CONTAINER);
		createEReference(structuredEClass, STRUCTURED__STR_CONTAINER);
		createEReference(structuredEClass, STRUCTURED__FLOAT_CONTAINER);

		intContainerEClass = createEClass(INT_CONTAINER);
		createEAttribute(intContainerEClass, INT_CONTAINER__VALUE);

		strContainerEClass = createEClass(STR_CONTAINER);
		createEAttribute(strContainerEClass, STR_CONTAINER__VALUE);

		floatContainerEClass = createEClass(FLOAT_CONTAINER);
		createEAttribute(floatContainerEClass, FLOAT_CONTAINER__VALUE);
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
		modelBEClass.getESuperTypes().add(this.getIdentified());
		structuredEClass.getESuperTypes().add(this.getIdentified());
		intContainerEClass.getESuperTypes().add(this.getIdentified());
		strContainerEClass.getESuperTypes().add(this.getIdentified());
		floatContainerEClass.getESuperTypes().add(this.getIdentified());

		// Initialize classes, features, and operations; add parameters
		initEClass(identifiedEClass, Identified.class, "Identified", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentified_Id(), ecorePackage.getEString(), "id", null, 1, 1, Identified.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(modelBEClass, ModelB.class, "ModelB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelB_Content(), this.getStructured(), null, "content", null, 0, -1, ModelB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structuredEClass, Structured.class, "Structured", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStructured_Name(), ecorePackage.getEString(), "name", null, 0, 1, Structured.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructured_IntContainer(), this.getIntContainer(), null, "intContainer", null, 0, 1, Structured.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructured_StrContainer(), this.getStrContainer(), null, "strContainer", null, 0, 1, Structured.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructured_FloatContainer(), this.getFloatContainer(), null, "floatContainer", null, 0, 1, Structured.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(intContainerEClass, IntContainer.class, "IntContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntContainer_Value(), ecorePackage.getEInt(), "value", null, 0, 1, IntContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(strContainerEClass, StrContainer.class, "StrContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStrContainer_Value(), ecorePackage.getEString(), "value", null, 0, 1, StrContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(floatContainerEClass, FloatContainer.class, "FloatContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloatContainer_Value(), ecorePackage.getEFloat(), "value", null, 0, 1, FloatContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Attribute_to_structure_struct_1PackageImpl
