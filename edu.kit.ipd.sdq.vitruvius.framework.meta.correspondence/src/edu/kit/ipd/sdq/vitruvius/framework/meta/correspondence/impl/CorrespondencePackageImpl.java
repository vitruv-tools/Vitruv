/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CorrespondencePackageImpl extends EPackageImpl implements CorrespondencePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correspondencesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correspondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sameTypeCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eFeatureCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAttributeCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eReferenceCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eContainmentReferenceCorrespondenceEClass = null;

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CorrespondencePackageImpl() {
		super(eNS_URI, CorrespondenceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CorrespondencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CorrespondencePackage init() {
		if (isInited) return (CorrespondencePackage)EPackage.Registry.INSTANCE.getEPackage(CorrespondencePackage.eNS_URI);

		// Obtain or create and register package
		CorrespondencePackageImpl theCorrespondencePackage = (CorrespondencePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CorrespondencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CorrespondencePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCorrespondencePackage.createPackageContents();

		// Initialize created meta-data
		theCorrespondencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCorrespondencePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, theCorrespondencePackage);
		return theCorrespondencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorrespondences() {
		return correspondencesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondences_Correspondences() {
		return (EReference)correspondencesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorrespondence() {
		return correspondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_DependentCorrespondences() {
		return (EReference)correspondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_Parent() {
		return (EReference)correspondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSameTypeCorrespondence() {
		return sameTypeCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSameTypeCorrespondence_ElementA() {
		return (EReference)sameTypeCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSameTypeCorrespondence_ElementB() {
		return (EReference)sameTypeCorrespondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectCorrespondence() {
		return eObjectCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFeatureCorrespondence() {
		return eFeatureCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEFeatureCorrespondence_MappedFeature() {
		return (EReference)eFeatureCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEAttributeCorrespondence() {
		return eAttributeCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEReferenceCorrespondence() {
		return eReferenceCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEContainmentReferenceCorrespondence() {
		return eContainmentReferenceCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceFactory getCorrespondenceFactory() {
		return (CorrespondenceFactory)getEFactoryInstance();
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
		correspondencesEClass = createEClass(CORRESPONDENCES);
		createEReference(correspondencesEClass, CORRESPONDENCES__CORRESPONDENCES);

		correspondenceEClass = createEClass(CORRESPONDENCE);
		createEReference(correspondenceEClass, CORRESPONDENCE__DEPENDENT_CORRESPONDENCES);
		createEReference(correspondenceEClass, CORRESPONDENCE__PARENT);

		sameTypeCorrespondenceEClass = createEClass(SAME_TYPE_CORRESPONDENCE);
		createEReference(sameTypeCorrespondenceEClass, SAME_TYPE_CORRESPONDENCE__ELEMENT_A);
		createEReference(sameTypeCorrespondenceEClass, SAME_TYPE_CORRESPONDENCE__ELEMENT_B);

		eObjectCorrespondenceEClass = createEClass(EOBJECT_CORRESPONDENCE);

		eFeatureCorrespondenceEClass = createEClass(EFEATURE_CORRESPONDENCE);
		createEReference(eFeatureCorrespondenceEClass, EFEATURE_CORRESPONDENCE__MAPPED_FEATURE);

		eAttributeCorrespondenceEClass = createEClass(EATTRIBUTE_CORRESPONDENCE);

		eReferenceCorrespondenceEClass = createEClass(EREFERENCE_CORRESPONDENCE);

		eContainmentReferenceCorrespondenceEClass = createEClass(ECONTAINMENT_REFERENCE_CORRESPONDENCE);
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

		// Create type parameters
		ETypeParameter sameTypeCorrespondenceEClass_T = addETypeParameter(sameTypeCorrespondenceEClass, "T");
		ETypeParameter eFeatureCorrespondenceEClass_T = addETypeParameter(eFeatureCorrespondenceEClass, "T");
		ETypeParameter eFeatureCorrespondenceEClass_TFeature = addETypeParameter(eFeatureCorrespondenceEClass, "TFeature");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
		sameTypeCorrespondenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEJavaObject());
		eFeatureCorrespondenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		eFeatureCorrespondenceEClass_TFeature.getEBounds().add(g1);

		// Add supertypes to classes
		sameTypeCorrespondenceEClass.getESuperTypes().add(this.getCorrespondence());
		g1 = createEGenericType(this.getSameTypeCorrespondence());
		EGenericType g2 = createEGenericType(ecorePackage.getEObject());
		g1.getETypeArguments().add(g2);
		eObjectCorrespondenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSameTypeCorrespondence());
		g2 = createEGenericType(eFeatureCorrespondenceEClass_T);
		g1.getETypeArguments().add(g2);
		eFeatureCorrespondenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getEFeatureCorrespondence());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		eAttributeCorrespondenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getEFeatureCorrespondence());
		g2 = createEGenericType(theEcorePackage.getEObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		eReferenceCorrespondenceEClass.getEGenericSuperTypes().add(g1);
		eContainmentReferenceCorrespondenceEClass.getESuperTypes().add(this.getEReferenceCorrespondence());

		// Initialize classes and features; add operations and parameters
		initEClass(correspondencesEClass, Correspondences.class, "Correspondences", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrespondences_Correspondences(), this.getCorrespondence(), null, "correspondences", null, 0, -1, Correspondences.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(correspondenceEClass, Correspondence.class, "Correspondence", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrespondence_DependentCorrespondences(), this.getCorrespondence(), null, "dependentCorrespondences", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorrespondence_Parent(), this.getCorrespondence(), null, "parent", null, 0, 1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(correspondenceEClass, ecorePackage.getEObject(), "getAllInvolvedEObjects", 2, -1, IS_UNIQUE, !IS_ORDERED);

		initEClass(sameTypeCorrespondenceEClass, SameTypeCorrespondence.class, "SameTypeCorrespondence", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(sameTypeCorrespondenceEClass_T);
		initEReference(getSameTypeCorrespondence_ElementA(), g1, null, "elementA", null, 1, 1, SameTypeCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(sameTypeCorrespondenceEClass_T);
		initEReference(getSameTypeCorrespondence_ElementB(), g1, null, "elementB", null, 1, 1, SameTypeCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectCorrespondenceEClass, EObjectCorrespondence.class, "EObjectCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eFeatureCorrespondenceEClass, EFeatureCorrespondence.class, "EFeatureCorrespondence", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(eFeatureCorrespondenceEClass_TFeature);
		initEReference(getEFeatureCorrespondence_MappedFeature(), g1, null, "mappedFeature", null, 0, 1, EFeatureCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eAttributeCorrespondenceEClass, EAttributeCorrespondence.class, "EAttributeCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eReferenceCorrespondenceEClass, EReferenceCorrespondence.class, "EReferenceCorrespondence", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eContainmentReferenceCorrespondenceEClass, EContainmentReferenceCorrespondence.class, "EContainmentReferenceCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //CorrespondencePackageImpl
