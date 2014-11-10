/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
      case MIRPackage.MIR_FILE: return createMIRFile();
      case MIRPackage.IMPORT: return createImport();
      case MIRPackage.MAPPING: return createMapping();
      case MIRPackage.MAPPABLE_ELEMENT: return createMappableElement();
      case MIRPackage.ECLASS_PARAMETER: return createEClassParameter();
      case MIRPackage.INVARIANT: return createInvariant();
      case MIRPackage.OCL_BLOCK: return createOCLBlock();
      case MIRPackage.RESPONSE: return createResponse();
      case MIRPackage.BASE_MAPPING: return createBaseMapping();
      case MIRPackage.SUB_MAPPING: return createSubMapping();
      case MIRPackage.NAMED_ECLASS: return createNamedEClass();
      case MIRPackage.NAMED_FEATURE: return createNamedFeature();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case MIRPackage.RESPONSE_ACTION:
        return createResponseActionFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case MIRPackage.RESPONSE_ACTION:
        return convertResponseActionToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MIRFile createMIRFile()
  {
    MIRFileImpl mirFile = new MIRFileImpl();
    return mirFile;
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
  public MappableElement createMappableElement()
  {
    MappableElementImpl mappableElement = new MappableElementImpl();
    return mappableElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClassParameter createEClassParameter()
  {
    EClassParameterImpl eClassParameter = new EClassParameterImpl();
    return eClassParameter;
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
  public OCLBlock createOCLBlock()
  {
    OCLBlockImpl oclBlock = new OCLBlockImpl();
    return oclBlock;
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
  public BaseMapping createBaseMapping()
  {
    BaseMappingImpl baseMapping = new BaseMappingImpl();
    return baseMapping;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SubMapping createSubMapping()
  {
    SubMappingImpl subMapping = new SubMappingImpl();
    return subMapping;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedEClass createNamedEClass()
  {
    NamedEClassImpl namedEClass = new NamedEClassImpl();
    return namedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedFeature createNamedFeature()
  {
    NamedFeatureImpl namedFeature = new NamedFeatureImpl();
    return namedFeature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResponseAction createResponseActionFromString(EDataType eDataType, String initialValue)
  {
    ResponseAction result = ResponseAction.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertResponseActionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
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
