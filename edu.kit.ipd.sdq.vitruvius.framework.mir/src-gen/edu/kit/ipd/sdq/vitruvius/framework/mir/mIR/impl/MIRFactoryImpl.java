/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.*;

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
      case MIRPackage.MIR_FILE: return createMIRFile();
      case MIRPackage.IMPORT: return createImport();
      case MIRPackage.BUNDLE: return createBundle();
      case MIRPackage.MAPPING_BODY: return createMappingBody();
      case MIRPackage.MAPPING: return createMapping();
      case MIRPackage.TYPED_ELEMENT: return createTypedElement();
      case MIRPackage.TYPED_ELEMENT_REF: return createTypedElementRef();
      case MIRPackage.CLASS_MAPPING: return createClassMapping();
      case MIRPackage.FEATURE_MAPPING: return createFeatureMapping();
      case MIRPackage.NAMED_ECLASS: return createNamedEClass();
      case MIRPackage.FEATURE_CALL: return createFeatureCall();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
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
  public Bundle createBundle()
  {
    BundleImpl bundle = new BundleImpl();
    return bundle;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MappingBody createMappingBody()
  {
    MappingBodyImpl mappingBody = new MappingBodyImpl();
    return mappingBody;
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
  public TypedElement createTypedElement()
  {
    TypedElementImpl typedElement = new TypedElementImpl();
    return typedElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypedElementRef createTypedElementRef()
  {
    TypedElementRefImpl typedElementRef = new TypedElementRefImpl();
    return typedElementRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassMapping createClassMapping()
  {
    ClassMappingImpl classMapping = new ClassMappingImpl();
    return classMapping;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMapping createFeatureMapping()
  {
    FeatureMappingImpl featureMapping = new FeatureMappingImpl();
    return featureMapping;
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
  public FeatureCall createFeatureCall()
  {
    FeatureCallImpl featureCall = new FeatureCallImpl();
    return featureCall;
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
