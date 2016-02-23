/**
 */
package attribute_to_structure_attr.impl;

import attribute_to_structure_attr.Attribute_to_structure_attrFactory;
import attribute_to_structure_attr.Attribute_to_structure_attrPackage;
import attribute_to_structure_attr.Attributed;
import attribute_to_structure_attr.Identified;
import attribute_to_structure_attr.ModelA;

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
public class Attribute_to_structure_attrPackageImpl extends EPackageImpl implements Attribute_to_structure_attrPackage {
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
	private EClass modelAEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributedEClass = null;

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
	 * @see attribute_to_structure_attr.Attribute_to_structure_attrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Attribute_to_structure_attrPackageImpl() {
		super(eNS_URI, Attribute_to_structure_attrFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Attribute_to_structure_attrPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Attribute_to_structure_attrPackage init() {
		if (isInited) return (Attribute_to_structure_attrPackage)EPackage.Registry.INSTANCE.getEPackage(Attribute_to_structure_attrPackage.eNS_URI);

		// Obtain or create and register package
		Attribute_to_structure_attrPackageImpl theAttribute_to_structure_attrPackage = (Attribute_to_structure_attrPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Attribute_to_structure_attrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Attribute_to_structure_attrPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAttribute_to_structure_attrPackage.createPackageContents();

		// Initialize created meta-data
		theAttribute_to_structure_attrPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAttribute_to_structure_attrPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Attribute_to_structure_attrPackage.eNS_URI, theAttribute_to_structure_attrPackage);
		return theAttribute_to_structure_attrPackage;
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
	public EClass getModelA() {
		return modelAEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelA_Content() {
		return (EReference)modelAEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributed() {
		return attributedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributed_Name() {
		return (EAttribute)attributedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributed_IntAttr() {
		return (EAttribute)attributedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributed_StrAttr() {
		return (EAttribute)attributedEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributed_FloatAttr() {
		return (EAttribute)attributedEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_attrFactory getAttribute_to_structure_attrFactory() {
		return (Attribute_to_structure_attrFactory)getEFactoryInstance();
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

		modelAEClass = createEClass(MODEL_A);
		createEReference(modelAEClass, MODEL_A__CONTENT);

		attributedEClass = createEClass(ATTRIBUTED);
		createEAttribute(attributedEClass, ATTRIBUTED__NAME);
		createEAttribute(attributedEClass, ATTRIBUTED__INT_ATTR);
		createEAttribute(attributedEClass, ATTRIBUTED__STR_ATTR);
		createEAttribute(attributedEClass, ATTRIBUTED__FLOAT_ATTR);
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
		modelAEClass.getESuperTypes().add(this.getIdentified());
		attributedEClass.getESuperTypes().add(this.getIdentified());

		// Initialize classes, features, and operations; add parameters
		initEClass(identifiedEClass, Identified.class, "Identified", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentified_Id(), ecorePackage.getEString(), "id", null, 1, 1, Identified.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(modelAEClass, ModelA.class, "ModelA", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelA_Content(), this.getAttributed(), null, "content", null, 0, -1, ModelA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributedEClass, Attributed.class, "Attributed", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributed_Name(), ecorePackage.getEString(), "name", null, 0, 1, Attributed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributed_IntAttr(), ecorePackage.getEIntegerObject(), "intAttr", null, 0, 1, Attributed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributed_StrAttr(), ecorePackage.getEString(), "strAttr", null, 0, 1, Attributed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributed_FloatAttr(), ecorePackage.getEFloatObject(), "floatAttr", null, 0, 1, Attributed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Attribute_to_structure_attrPackageImpl
