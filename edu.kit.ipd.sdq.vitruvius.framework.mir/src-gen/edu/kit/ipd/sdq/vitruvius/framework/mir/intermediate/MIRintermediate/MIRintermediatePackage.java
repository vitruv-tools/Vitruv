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
	 * The number of structural features of the '<em>MIR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIR_FEATURE_COUNT = 3;

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
	 * The number of structural features of the '<em>Class Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING_FEATURE_COUNT = 3;

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
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE__CLASS_NAME = 0;

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
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate <em>Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Predicate</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate
	 * @generated
	 */
	EClass getPredicate();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getClassName()
	 * @see #getPredicate()
	 * @generated
	 */
	EAttribute getPredicate_ClassName();

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
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl <em>Predicate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.PredicateImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediatePackageImpl#getPredicate()
		 * @generated
		 */
		EClass PREDICATE = eINSTANCE.getPredicate();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREDICATE__CLASS_NAME = eINSTANCE.getPredicate_ClassName();

	}

} //MIRintermediatePackage
