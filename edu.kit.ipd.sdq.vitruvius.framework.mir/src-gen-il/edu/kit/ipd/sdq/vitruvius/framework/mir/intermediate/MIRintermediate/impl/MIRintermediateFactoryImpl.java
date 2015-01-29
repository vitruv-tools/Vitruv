/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MIRintermediateFactoryImpl extends EFactoryImpl implements MIRintermediateFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MIRintermediateFactory init() {
		try {
			MIRintermediateFactory theMIRintermediateFactory = (MIRintermediateFactory)EPackage.Registry.INSTANCE.getEFactory(MIRintermediatePackage.eNS_URI);
			if (theMIRintermediateFactory != null) {
				return theMIRintermediateFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MIRintermediateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MIRintermediateFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MIRintermediatePackage.MIR: return createMIR();
			case MIRintermediatePackage.FEATURE_MAPPING: return createFeatureMapping();
			case MIRintermediatePackage.CLASSIFIER_MAPPING: return createClassifierMapping();
			case MIRintermediatePackage.JAVA_PREDICATE: return createJavaPredicate();
			case MIRintermediatePackage.FEATURE_ECLASSIFIER_CORRESPONDENCE: return createFeatureEClassifierCorrespondence();
			case MIRintermediatePackage.REVERSE_FEATURES_CORRESPOND_WITH_ECLASSIFIERS: return createReverseFeaturesCorrespondWithEClassifiers();
			case MIRintermediatePackage.JAVA_INITIALIZER: return createJavaInitializer();
			case MIRintermediatePackage.CREATE_CORRESPONDING: return createCreateCorresponding();
			case MIRintermediatePackage.CONFIGURATION: return createConfiguration();
			case MIRintermediatePackage.ECLASSIFIER_FEATURE: return createEClassifierFeature();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MIR createMIR() {
		MIRImpl mir = new MIRImpl();
		return mir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMapping createFeatureMapping() {
		FeatureMappingImpl featureMapping = new FeatureMappingImpl();
		return featureMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierMapping createClassifierMapping() {
		ClassifierMappingImpl classifierMapping = new ClassifierMappingImpl();
		return classifierMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaPredicate createJavaPredicate() {
		JavaPredicateImpl javaPredicate = new JavaPredicateImpl();
		return javaPredicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureEClassifierCorrespondence createFeatureEClassifierCorrespondence() {
		FeatureEClassifierCorrespondenceImpl featureEClassifierCorrespondence = new FeatureEClassifierCorrespondenceImpl();
		return featureEClassifierCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReverseFeaturesCorrespondWithEClassifiers createReverseFeaturesCorrespondWithEClassifiers() {
		ReverseFeaturesCorrespondWithEClassifiersImpl reverseFeaturesCorrespondWithEClassifiers = new ReverseFeaturesCorrespondWithEClassifiersImpl();
		return reverseFeaturesCorrespondWithEClassifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaInitializer createJavaInitializer() {
		JavaInitializerImpl javaInitializer = new JavaInitializerImpl();
		return javaInitializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreateCorresponding createCreateCorresponding() {
		CreateCorrespondingImpl createCorresponding = new CreateCorrespondingImpl();
		return createCorresponding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifierFeature createEClassifierFeature() {
		EClassifierFeatureImpl eClassifierFeature = new EClassifierFeatureImpl();
		return eClassifierFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MIRintermediatePackage getMIRintermediatePackage() {
		return (MIRintermediatePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MIRintermediatePackage getPackage() {
		return MIRintermediatePackage.eINSTANCE;
	}

} //MIRintermediateFactoryImpl
