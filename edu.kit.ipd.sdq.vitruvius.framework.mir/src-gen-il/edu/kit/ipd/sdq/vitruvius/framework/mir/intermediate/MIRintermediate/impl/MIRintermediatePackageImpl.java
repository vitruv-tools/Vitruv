/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.CreateCorresponding;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.EClassifierFeature;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureEClassifierCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaInitializer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaPredicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ReverseFeaturesCorrespondWithEClassifiers;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MIRintermediatePackageImpl extends EPackageImpl implements MIRintermediatePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mirEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass predicateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaPredicateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureEClassifierCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reverseFeaturesCorrespondWithEClassifiersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initializerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaInitializerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createCorrespondingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eClassifierFeatureEClass = null;

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MIRintermediatePackageImpl() {
		super(eNS_URI, MIRintermediateFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MIRintermediatePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MIRintermediatePackage init() {
		if (isInited) return (MIRintermediatePackage)EPackage.Registry.INSTANCE.getEPackage(MIRintermediatePackage.eNS_URI);

		// Obtain or create and register package
		MIRintermediatePackageImpl theMIRintermediatePackage = (MIRintermediatePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MIRintermediatePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MIRintermediatePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMIRintermediatePackage.createPackageContents();

		// Initialize created meta-data
		theMIRintermediatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMIRintermediatePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MIRintermediatePackage.eNS_URI, theMIRintermediatePackage);
		return theMIRintermediatePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMIR() {
		return mirEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMIR_ClassMappings() {
		return (EReference)mirEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMIR_FeatureMappings() {
		return (EReference)mirEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMIR_Predicates() {
		return (EReference)mirEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMIR_Configuration() {
		return (EReference)mirEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapping() {
		return mappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapping_Predicates() {
		return (EReference)mappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFeatureMapping() {
		return featureMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureMapping_Left() {
		return (EReference)featureMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureMapping_Right() {
		return (EReference)featureMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureMapping_ClassifierMapping() {
		return (EReference)featureMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierMapping() {
		return classifierMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifierMapping_Left() {
		return (EReference)classifierMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifierMapping_Right() {
		return (EReference)classifierMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifierMapping_Initializer() {
		return (EReference)classifierMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifierMapping_FeatureMapping() {
		return (EReference)classifierMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPredicate() {
		return predicateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaPredicate() {
		return javaPredicateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaPredicate_CheckStatement() {
		return (EAttribute)javaPredicateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFeatureEClassifierCorrespondence() {
		return featureEClassifierCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureEClassifierCorrespondence_Feature() {
		return (EReference)featureEClassifierCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureEClassifierCorrespondence_EClassifier() {
		return (EReference)featureEClassifierCorrespondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReverseFeaturesCorrespondWithEClassifiers() {
		return reverseFeaturesCorrespondWithEClassifiersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReverseFeaturesCorrespondWithEClassifiers_Correspondences() {
		return (EReference)reverseFeaturesCorrespondWithEClassifiersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInitializer() {
		return initializerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaInitializer() {
		return javaInitializerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaInitializer_CallStatement() {
		return (EAttribute)javaInitializerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateCorresponding() {
		return createCorrespondingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateCorresponding_FeatureLeft() {
		return (EReference)createCorrespondingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateCorresponding_FeatureRight() {
		return (EReference)createCorrespondingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfiguration() {
		return configurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfiguration_Package() {
		return (EAttribute)configurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfiguration_Type() {
		return (EAttribute)configurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEClassifierFeature() {
		return eClassifierFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassifierFeature_EClassifier() {
		return (EReference)eClassifierFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassifierFeature_Feature() {
		return (EReference)eClassifierFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MIRintermediateFactory getMIRintermediateFactory() {
		return (MIRintermediateFactory)getEFactoryInstance();
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
		mirEClass = createEClass(MIR);
		createEReference(mirEClass, MIR__CLASS_MAPPINGS);
		createEReference(mirEClass, MIR__FEATURE_MAPPINGS);
		createEReference(mirEClass, MIR__PREDICATES);
		createEReference(mirEClass, MIR__CONFIGURATION);

		mappingEClass = createEClass(MAPPING);
		createEReference(mappingEClass, MAPPING__PREDICATES);

		featureMappingEClass = createEClass(FEATURE_MAPPING);
		createEReference(featureMappingEClass, FEATURE_MAPPING__LEFT);
		createEReference(featureMappingEClass, FEATURE_MAPPING__RIGHT);
		createEReference(featureMappingEClass, FEATURE_MAPPING__CLASSIFIER_MAPPING);

		classifierMappingEClass = createEClass(CLASSIFIER_MAPPING);
		createEReference(classifierMappingEClass, CLASSIFIER_MAPPING__LEFT);
		createEReference(classifierMappingEClass, CLASSIFIER_MAPPING__RIGHT);
		createEReference(classifierMappingEClass, CLASSIFIER_MAPPING__INITIALIZER);
		createEReference(classifierMappingEClass, CLASSIFIER_MAPPING__FEATURE_MAPPING);

		predicateEClass = createEClass(PREDICATE);

		javaPredicateEClass = createEClass(JAVA_PREDICATE);
		createEAttribute(javaPredicateEClass, JAVA_PREDICATE__CHECK_STATEMENT);

		featureEClassifierCorrespondenceEClass = createEClass(FEATURE_ECLASSIFIER_CORRESPONDENCE);
		createEReference(featureEClassifierCorrespondenceEClass, FEATURE_ECLASSIFIER_CORRESPONDENCE__FEATURE);
		createEReference(featureEClassifierCorrespondenceEClass, FEATURE_ECLASSIFIER_CORRESPONDENCE__ECLASSIFIER);

		reverseFeaturesCorrespondWithEClassifiersEClass = createEClass(REVERSE_FEATURES_CORRESPOND_WITH_ECLASSIFIERS);
		createEReference(reverseFeaturesCorrespondWithEClassifiersEClass, REVERSE_FEATURES_CORRESPOND_WITH_ECLASSIFIERS__CORRESPONDENCES);

		initializerEClass = createEClass(INITIALIZER);

		javaInitializerEClass = createEClass(JAVA_INITIALIZER);
		createEAttribute(javaInitializerEClass, JAVA_INITIALIZER__CALL_STATEMENT);

		createCorrespondingEClass = createEClass(CREATE_CORRESPONDING);
		createEReference(createCorrespondingEClass, CREATE_CORRESPONDING__FEATURE_LEFT);
		createEReference(createCorrespondingEClass, CREATE_CORRESPONDING__FEATURE_RIGHT);

		configurationEClass = createEClass(CONFIGURATION);
		createEAttribute(configurationEClass, CONFIGURATION__PACKAGE);
		createEAttribute(configurationEClass, CONFIGURATION__TYPE);

		eClassifierFeatureEClass = createEClass(ECLASSIFIER_FEATURE);
		createEReference(eClassifierFeatureEClass, ECLASSIFIER_FEATURE__ECLASSIFIER);
		createEReference(eClassifierFeatureEClass, ECLASSIFIER_FEATURE__FEATURE);
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

		// Set bounds for type parameters

		// Add supertypes to classes
		featureMappingEClass.getESuperTypes().add(this.getMapping());
		classifierMappingEClass.getESuperTypes().add(this.getMapping());
		javaPredicateEClass.getESuperTypes().add(this.getPredicate());
		reverseFeaturesCorrespondWithEClassifiersEClass.getESuperTypes().add(this.getPredicate());
		javaInitializerEClass.getESuperTypes().add(this.getInitializer());
		createCorrespondingEClass.getESuperTypes().add(this.getInitializer());

		// Initialize classes, features, and operations; add parameters
		initEClass(mirEClass, edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR.class, "MIR", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMIR_ClassMappings(), this.getClassifierMapping(), null, "classMappings", null, 0, -1, edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMIR_FeatureMappings(), this.getFeatureMapping(), null, "featureMappings", null, 0, -1, edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMIR_Predicates(), this.getPredicate(), null, "predicates", null, 0, -1, edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMIR_Configuration(), this.getConfiguration(), null, "configuration", null, 1, 1, edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingEClass, Mapping.class, "Mapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapping_Predicates(), this.getPredicate(), null, "predicates", null, 0, -1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureMappingEClass, FeatureMapping.class, "FeatureMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureMapping_Left(), this.getEClassifierFeature(), null, "left", null, 0, -1, FeatureMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureMapping_Right(), this.getEClassifierFeature(), null, "right", null, 0, -1, FeatureMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureMapping_ClassifierMapping(), this.getClassifierMapping(), this.getClassifierMapping_FeatureMapping(), "classifierMapping", null, 0, 1, FeatureMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classifierMappingEClass, ClassifierMapping.class, "ClassifierMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getClassifierMapping_Left(), theEcorePackage.getEClassifier(), null, "left", null, 1, 1, ClassifierMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClassifierMapping_Right(), theEcorePackage.getEClassifier(), null, "right", null, 1, 1, ClassifierMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClassifierMapping_Initializer(), this.getInitializer(), null, "initializer", null, 0, -1, ClassifierMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClassifierMapping_FeatureMapping(), this.getFeatureMapping(), this.getFeatureMapping_ClassifierMapping(), "featureMapping", null, 0, 1, ClassifierMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(predicateEClass, Predicate.class, "Predicate", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(javaPredicateEClass, JavaPredicate.class, "JavaPredicate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaPredicate_CheckStatement(), ecorePackage.getEString(), "checkStatement", "", 0, 1, JavaPredicate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureEClassifierCorrespondenceEClass, FeatureEClassifierCorrespondence.class, "FeatureEClassifierCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureEClassifierCorrespondence_Feature(), theEcorePackage.getEStructuralFeature(), null, "feature", null, 1, 1, FeatureEClassifierCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureEClassifierCorrespondence_EClassifier(), theEcorePackage.getEClassifier(), null, "eClassifier", null, 1, 1, FeatureEClassifierCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reverseFeaturesCorrespondWithEClassifiersEClass, ReverseFeaturesCorrespondWithEClassifiers.class, "ReverseFeaturesCorrespondWithEClassifiers", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReverseFeaturesCorrespondWithEClassifiers_Correspondences(), this.getFeatureEClassifierCorrespondence(), null, "correspondences", null, 1, -1, ReverseFeaturesCorrespondWithEClassifiers.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(initializerEClass, Initializer.class, "Initializer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(javaInitializerEClass, JavaInitializer.class, "JavaInitializer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaInitializer_CallStatement(), ecorePackage.getEString(), "callStatement", "", 0, 1, JavaInitializer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createCorrespondingEClass, CreateCorresponding.class, "CreateCorresponding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCreateCorresponding_FeatureLeft(), theEcorePackage.getEStructuralFeature(), null, "featureLeft", null, 1, -1, CreateCorresponding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCreateCorresponding_FeatureRight(), theEcorePackage.getEStructuralFeature(), null, "featureRight", null, 1, -1, CreateCorresponding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationEClass, Configuration.class, "Configuration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfiguration_Package(), ecorePackage.getEString(), "package", null, 1, 1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfiguration_Type(), ecorePackage.getEString(), "type", null, 1, 1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eClassifierFeatureEClass, EClassifierFeature.class, "EClassifierFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEClassifierFeature_EClassifier(), theEcorePackage.getEClassifier(), null, "eClassifier", null, 1, 1, EClassifierFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassifierFeature_Feature(), theEcorePackage.getEStructuralFeature(), null, "feature", null, 1, 1, EClassifierFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MIRintermediatePackageImpl
