/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage
 * @generated
 */
public interface MIRintermediateFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MIRintermediateFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRintermediateFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>MIR</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MIR</em>'.
	 * @generated
	 */
	MIR createMIR();

	/**
	 * Returns a new object of class '<em>Feature Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Mapping</em>'.
	 * @generated
	 */
	FeatureMapping createFeatureMapping();

	/**
	 * Returns a new object of class '<em>Classifier Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Classifier Mapping</em>'.
	 * @generated
	 */
	ClassifierMapping createClassifierMapping();

	/**
	 * Returns a new object of class '<em>Java Predicate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Predicate</em>'.
	 * @generated
	 */
	JavaPredicate createJavaPredicate();

	/**
	 * Returns a new object of class '<em>Feature EClassifier Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature EClassifier Correspondence</em>'.
	 * @generated
	 */
	FeatureEClassifierCorrespondence createFeatureEClassifierCorrespondence();

	/**
	 * Returns a new object of class '<em>Reverse Features Correspond With EClassifiers</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reverse Features Correspond With EClassifiers</em>'.
	 * @generated
	 */
	ReverseFeaturesCorrespondWithEClassifiers createReverseFeaturesCorrespondWithEClassifiers();

	/**
	 * Returns a new object of class '<em>Java Initializer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Initializer</em>'.
	 * @generated
	 */
	JavaInitializer createJavaInitializer();

	/**
	 * Returns a new object of class '<em>Create Corresponding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Corresponding</em>'.
	 * @generated
	 */
	CreateCorresponding createCreateCorresponding();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	Configuration createConfiguration();

	/**
	 * Returns a new object of class '<em>EClassifier Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EClassifier Feature</em>'.
	 * @generated
	 */
	EClassifierFeature createEClassifierFeature();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MIRintermediatePackage getMIRintermediatePackage();

} //MIRintermediateFactory
