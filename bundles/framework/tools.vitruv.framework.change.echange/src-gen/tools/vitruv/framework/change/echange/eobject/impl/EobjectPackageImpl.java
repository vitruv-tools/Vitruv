/**
 */
package tools.vitruv.framework.change.echange.eobject.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectFactory;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

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
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;
import tools.vitruv.framework.change.echange.resolve.StagingArea;
import tools.vitruv.framework.change.echange.root.RootPackage;
import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EobjectPackageImpl extends EPackageImpl implements EobjectPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectAddedEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectSubtractedEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectExistenceEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deleteEObjectEClass = null;

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
	private EDataType stagingAreaEDataType = null;

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
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EobjectPackageImpl() {
		super(eNS_URI, EobjectFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EobjectPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EobjectPackage init() {
		if (isInited) return (EobjectPackage)EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI);

		// Obtain or create and register package
		EobjectPackageImpl theEobjectPackage = (EobjectPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EobjectPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EobjectPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) : SinglePackage.eINSTANCE);
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);

		// Create package meta-data objects
		theEobjectPackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theEChangePackage.createPackageContents();
		theListPackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theCompoundPackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theRootPackage.createPackageContents();

		// Initialize created meta-data
		theEobjectPackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theCompoundPackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theRootPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEobjectPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EobjectPackage.eNS_URI, theEobjectPackage);
		return theEobjectPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectAddedEChange() {
		return eObjectAddedEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectAddedEChange_NewValue() {
		return (EReference)eObjectAddedEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectSubtractedEChange() {
		return eObjectSubtractedEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectSubtractedEChange_OldValue() {
		return (EReference)eObjectSubtractedEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectExistenceEChange() {
		return eObjectExistenceEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectExistenceEChange_AffectedEObject() {
		return (EReference)eObjectExistenceEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEObjectExistenceEChange_StagingArea() {
		return (EAttribute)eObjectExistenceEChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateEObject() {
		return createEObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeleteEObject() {
		return deleteEObjectEClass;
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
	public EDataType getStagingArea() {
		return stagingAreaEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EobjectFactory getEobjectFactory() {
		return (EobjectFactory)getEFactoryInstance();
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
		eObjectAddedEChangeEClass = createEClass(EOBJECT_ADDED_ECHANGE);
		createEReference(eObjectAddedEChangeEClass, EOBJECT_ADDED_ECHANGE__NEW_VALUE);

		eObjectSubtractedEChangeEClass = createEClass(EOBJECT_SUBTRACTED_ECHANGE);
		createEReference(eObjectSubtractedEChangeEClass, EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE);

		eObjectExistenceEChangeEClass = createEClass(EOBJECT_EXISTENCE_ECHANGE);
		createEReference(eObjectExistenceEChangeEClass, EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT);
		createEAttribute(eObjectExistenceEChangeEClass, EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA);

		createEObjectEClass = createEClass(CREATE_EOBJECT);

		deleteEObjectEClass = createEClass(DELETE_EOBJECT);

		// Create data types
		eObjEDataType = createEDataType(EOBJ);
		stagingAreaEDataType = createEDataType(STAGING_AREA);
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

		// Create type parameters
		ETypeParameter eObjectAddedEChangeEClass_T = addETypeParameter(eObjectAddedEChangeEClass, "T");
		ETypeParameter eObjectSubtractedEChangeEClass_T = addETypeParameter(eObjectSubtractedEChangeEClass, "T");
		ETypeParameter eObjectExistenceEChangeEClass_A = addETypeParameter(eObjectExistenceEChangeEClass, "A");
		ETypeParameter createEObjectEClass_A = addETypeParameter(createEObjectEClass, "A");
		ETypeParameter deleteEObjectEClass_A = addETypeParameter(deleteEObjectEClass, "A");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getEObj());
		eObjectAddedEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		eObjectSubtractedEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		eObjectExistenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		createEObjectEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		deleteEObjectEClass_A.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		EGenericType g2 = createEGenericType(eObjectAddedEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		eObjectAddedEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(eObjectSubtractedEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		eObjectSubtractedEChangeEClass.getEGenericSuperTypes().add(g1);
		eObjectExistenceEChangeEClass.getESuperTypes().add(theEChangePackage.getAtomicEChange());
		g1 = createEGenericType(this.getEObjectExistenceEChange());
		g2 = createEGenericType(createEObjectEClass_A);
		g1.getETypeArguments().add(g2);
		createEObjectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getEObjectExistenceEChange());
		g2 = createEGenericType(deleteEObjectEClass_A);
		g1.getETypeArguments().add(g2);
		deleteEObjectEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(eObjectAddedEChangeEClass, EObjectAddedEChange.class, "EObjectAddedEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(eObjectAddedEChangeEClass_T);
		initEReference(getEObjectAddedEChange_NewValue(), g1, null, "newValue", null, 1, 1, EObjectAddedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectSubtractedEChangeEClass, EObjectSubtractedEChange.class, "EObjectSubtractedEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(eObjectSubtractedEChangeEClass_T);
		initEReference(getEObjectSubtractedEChange_OldValue(), g1, null, "oldValue", null, 1, 1, EObjectSubtractedEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectExistenceEChangeEClass, EObjectExistenceEChange.class, "EObjectExistenceEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(eObjectExistenceEChangeEClass_A);
		initEReference(getEObjectExistenceEChange_AffectedEObject(), g1, null, "affectedEObject", null, 1, 1, EObjectExistenceEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEObjectExistenceEChange_StagingArea(), this.getStagingArea(), "stagingArea", null, 0, 1, EObjectExistenceEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createEObjectEClass, CreateEObject.class, "CreateEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteEObjectEClass, DeleteEObject.class, "DeleteEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize data types
		initEDataType(eObjEDataType, EObject.class, "EObj", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(stagingAreaEDataType, StagingArea.class, "StagingArea", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //EobjectPackageImpl
