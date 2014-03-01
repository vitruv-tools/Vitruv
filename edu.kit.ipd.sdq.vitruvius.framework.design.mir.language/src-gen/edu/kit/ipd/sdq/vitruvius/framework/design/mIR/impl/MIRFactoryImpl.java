/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.*;

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
public class MIRFactoryImpl extends EFactoryImpl implements MIRFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static MIRFactory init()
  {
    try
    {
      MIRFactory theMIRFactory = (MIRFactory)EPackage.Registry.INSTANCE.getEFactory(MIRPackage.eNS_URI);
      if (theMIRFactory != null)
      {
        return theMIRFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new MIRFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MIRFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case MIRPackage.MODEL: return createModel();
      case MIRPackage.IMPORT: return createImport();
      case MIRPackage.MM_NS_PREFIX: return createMMNsPrefix();
      case MIRPackage.MAPPING: return createMapping();
      case MIRPackage.MAPPING_BASE: return createMappingBase();
      case MIRPackage.WHEN: return createWhen();
      case MIRPackage.WITH: return createWith();
      case MIRPackage.INVARIANT: return createInvariant();
      case MIRPackage.PARAMETER: return createParameter();
      case MIRPackage.GLOBAL_VARIABLE: return createGlobalVariable();
      case MIRPackage.RESPONSE: return createResponse();
      case MIRPackage.OPERATION_RESTRICTION: return createOperationRestriction();
      case MIRPackage.FEATURE_OPERATION_RESTRICTION: return createFeatureOperationRestriction();
      case MIRPackage.INVARIANT_RESTRICTION: return createInvariantRestriction();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Model createModel()
  {
    ModelImpl model = new ModelImpl();
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Import createImport()
  {
    ImportImpl import_ = new ImportImpl();
    return import_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MMNsPrefix createMMNsPrefix()
  {
    MMNsPrefixImpl mmNsPrefix = new MMNsPrefixImpl();
    return mmNsPrefix;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Mapping createMapping()
  {
    MappingImpl mapping = new MappingImpl();
    return mapping;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MappingBase createMappingBase()
  {
    MappingBaseImpl mappingBase = new MappingBaseImpl();
    return mappingBase;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public When createWhen()
  {
    WhenImpl when = new WhenImpl();
    return when;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public With createWith()
  {
    WithImpl with = new WithImpl();
    return with;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Invariant createInvariant()
  {
    InvariantImpl invariant = new InvariantImpl();
    return invariant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalVariable createGlobalVariable()
  {
    GlobalVariableImpl globalVariable = new GlobalVariableImpl();
    return globalVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Response createResponse()
  {
    ResponseImpl response = new ResponseImpl();
    return response;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OperationRestriction createOperationRestriction()
  {
    OperationRestrictionImpl operationRestriction = new OperationRestrictionImpl();
    return operationRestriction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureOperationRestriction createFeatureOperationRestriction()
  {
    FeatureOperationRestrictionImpl featureOperationRestriction = new FeatureOperationRestrictionImpl();
    return featureOperationRestriction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvariantRestriction createInvariantRestriction()
  {
    InvariantRestrictionImpl invariantRestriction = new InvariantRestrictionImpl();
    return invariantRestriction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MIRPackage getMIRPackage()
  {
    return (MIRPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static MIRPackage getPackage()
  {
    return MIRPackage.eINSTANCE;
  }

} //MIRFactoryImpl
