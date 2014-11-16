/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
 * @model kind="package"
 * @generated
 */
public interface MIRintermediatePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "MIRintermediate";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.kit.edu/ipd/sdq/vitruvius/framework/mir/MIRintermediate";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "MIRintermediate";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MIRintermediatePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl <em>MIR</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getMIR()
	 * @generated
	 */
	int MIR = 0;

	/**
	 * The feature id for the '<em><b>Class Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR__CLASS_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Feature Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR__FEATURE_MAPPINGS = 1;

	/**
	 * The feature id for the '<em><b>Predicates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR__PREDICATES = 2;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR__CONFIGURATION = 3;

	/**
	 * The number of structural features of the '<em>MIR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>MIR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getFeatureMapping()
	 * @generated
	 */
	int FEATURE_MAPPING = 1;

	/**
	 * The feature id for the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__LEFT = 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__RIGHT = 1;

	/**
	 * The feature id for the '<em><b>Predicates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__PREDICATES = 2;

	/**
	 * The number of structural features of the '<em>Feature Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Feature Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getClassMapping()
	 * @generated
	 */
	int CLASS_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__LEFT = 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__RIGHT = 1;

	/**
	 * The feature id for the '<em><b>Predicates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__PREDICATES = 2;

	/**
	 * The feature id for the '<em><b>Initializer</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__INITIALIZER = 3;

	/**
	 * The number of structural features of the '<em>Class Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Class Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl <em>Predicate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getPredicate()
	 * @generated
	 */
	int PREDICATE = 3;

	/**
	 * The feature id for the '<em><b>Check Statement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE__CHECK_STATEMENT = 0;

	/**
	 * The number of structural features of the '<em>Predicate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Predicate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.InitializerImpl <em>Initializer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.InitializerImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getInitializer()
	 * @generated
	 */
	int INITIALIZER = 4;

	/**
	 * The feature id for the '<em><b>Call Statement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZER__CALL_STATEMENT = 0;

	/**
	 * The number of structural features of the '<em>Initializer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Initializer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZER_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ConfigurationImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 5;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__PACKAGE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR <em>MIR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MIR</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
	 * @generated
	 */
	EClass getMIR();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getClassMappings <em>Class Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Mappings</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getClassMappings()
	 * @see #getMIR()
	 * @generated
	 */
	EReference getMIR_ClassMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getFeatureMappings <em>Feature Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature Mappings</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getFeatureMappings()
	 * @see #getMIR()
	 * @generated
	 */
	EReference getMIR_FeatureMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getPredicates <em>Predicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Predicates</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getPredicates()
	 * @see #getMIR()
	 * @generated
	 */
	EReference getMIR_Predicates();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Configuration</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getConfiguration()
	 * @see #getMIR()
	 * @generated
	 */
	EReference getMIR_Configuration();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping <em>Feature Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Mapping</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping
	 * @generated
	 */
	EClass getFeatureMapping();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Left</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getLeft()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EReference getFeatureMapping_Left();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Right</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getRight()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EReference getFeatureMapping_Right();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getPredicates <em>Predicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Predicates</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping#getPredicates()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EReference getFeatureMapping_Predicates();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping <em>Class Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Mapping</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping
	 * @generated
	 */
	EClass getClassMapping();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Left</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getLeft()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_Left();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Right</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getRight()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_Right();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getPredicates <em>Predicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Predicates</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getPredicates()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_Predicates();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getInitializer <em>Initializer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Initializer</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping#getInitializer()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_Initializer();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate <em>Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Predicate</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate
	 * @generated
	 */
	EClass getPredicate();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getCheckStatement <em>Check Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Check Statement</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getCheckStatement()
	 * @see #getPredicate()
	 * @generated
	 */
	EAttribute getPredicate_CheckStatement();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer <em>Initializer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initializer</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer
	 * @generated
	 */
	EClass getInitializer();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer#getCallStatement <em>Call Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Call Statement</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer#getCallStatement()
	 * @see #getInitializer()
	 * @generated
	 */
	EAttribute getInitializer_CallStatement();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration#getPackage()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Package();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration#getType()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MIRintermediateFactory getMIRintermediateFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl <em>MIR</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getMIR()
		 * @generated
		 */
		EClass MIR = eINSTANCE.getMIR();

		/**
		 * The meta object literal for the '<em><b>Class Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIR__CLASS_MAPPINGS = eINSTANCE.getMIR_ClassMappings();

		/**
		 * The meta object literal for the '<em><b>Feature Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIR__FEATURE_MAPPINGS = eINSTANCE.getMIR_FeatureMappings();

		/**
		 * The meta object literal for the '<em><b>Predicates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIR__PREDICATES = eINSTANCE.getMIR_Predicates();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIR__CONFIGURATION = eINSTANCE.getMIR_Configuration();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getFeatureMapping()
		 * @generated
		 */
		EClass FEATURE_MAPPING = eINSTANCE.getFeatureMapping();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MAPPING__LEFT = eINSTANCE.getFeatureMapping_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MAPPING__RIGHT = eINSTANCE.getFeatureMapping_Right();

		/**
		 * The meta object literal for the '<em><b>Predicates</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MAPPING__PREDICATES = eINSTANCE.getFeatureMapping_Predicates();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getClassMapping()
		 * @generated
		 */
		EClass CLASS_MAPPING = eINSTANCE.getClassMapping();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__LEFT = eINSTANCE.getClassMapping_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__RIGHT = eINSTANCE.getClassMapping_Right();

		/**
		 * The meta object literal for the '<em><b>Predicates</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__PREDICATES = eINSTANCE.getClassMapping_Predicates();

		/**
		 * The meta object literal for the '<em><b>Initializer</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__INITIALIZER = eINSTANCE.getClassMapping_Initializer();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl <em>Predicate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getPredicate()
		 * @generated
		 */
		EClass PREDICATE = eINSTANCE.getPredicate();

		/**
		 * The meta object literal for the '<em><b>Check Statement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREDICATE__CHECK_STATEMENT = eINSTANCE.getPredicate_CheckStatement();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.InitializerImpl <em>Initializer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.InitializerImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getInitializer()
		 * @generated
		 */
		EClass INITIALIZER = eINSTANCE.getInitializer();

		/**
		 * The meta object literal for the '<em><b>Call Statement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INITIALIZER__CALL_STATEMENT = eINSTANCE.getInitializer_CallStatement();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ConfigurationImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__PACKAGE = eINSTANCE.getConfiguration_Package();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__TYPE = eINSTANCE.getConfiguration_Type();

	}

} //MIRintermediatePackage
