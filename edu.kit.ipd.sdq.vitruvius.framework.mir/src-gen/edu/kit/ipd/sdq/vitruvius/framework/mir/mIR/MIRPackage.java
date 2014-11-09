/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFactory
 * @model kind="package"
 * @generated
 */
public interface MIRPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "mIR";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.kit.edu/ipd/sdq/vitruvius/framework/mir/MIR";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "mIR";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MIRPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl.init();

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl <em>File</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMIRFile()
   * @generated
   */
  int MIR_FILE = 0;

  /**
   * The feature id for the '<em><b>Generated Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__GENERATED_PACKAGE = 0;

  /**
   * The feature id for the '<em><b>Generated Class</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__GENERATED_CLASS = 1;

  /**
   * The feature id for the '<em><b>Imports</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__IMPORTS = 2;

  /**
   * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__MAPPINGS = 3;

  /**
   * The feature id for the '<em><b>Invariants</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__INVARIANTS = 4;

  /**
   * The feature id for the '<em><b>Responses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__RESPONSES = 5;

  /**
   * The number of structural features of the '<em>File</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ImportImpl <em>Import</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ImportImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getImport()
   * @generated
   */
  int IMPORT = 1;

  /**
   * The feature id for the '<em><b>Package</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT__PACKAGE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT__NAME = 1;

  /**
   * The number of structural features of the '<em>Import</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl <em>Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMapping()
   * @generated
   */
  int MAPPING = 2;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__MAPPED_ELEMENTS = 0;

  /**
   * The feature id for the '<em><b>Whens</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__WHENS = 1;

  /**
   * The feature id for the '<em><b>Withs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__WITHS = 2;

  /**
   * The feature id for the '<em><b>Wheres</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__WHERES = 3;

  /**
   * The number of structural features of the '<em>Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappableElementImpl <em>Mappable Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappableElementImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMappableElement()
   * @generated
   */
  int MAPPABLE_ELEMENT = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPABLE_ELEMENT__NAME = 0;

  /**
   * The number of structural features of the '<em>Mappable Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPABLE_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl <em>When</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getWhen()
   * @generated
   */
  int WHEN = 4;

  /**
   * The feature id for the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHEN__PREDICATE = 0;

  /**
   * The number of structural features of the '<em>When</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHEN_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhereImpl <em>Where</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhereImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getWhere()
   * @generated
   */
  int WHERE = 5;

  /**
   * The feature id for the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHERE__PREDICATE = 0;

  /**
   * The number of structural features of the '<em>Where</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHERE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.EClassParameterImpl <em>EClass Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.EClassParameterImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getEClassParameter()
   * @generated
   */
  int ECLASS_PARAMETER = 6;

  /**
   * The feature id for the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ECLASS_PARAMETER__TYPE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ECLASS_PARAMETER__NAME = 1;

  /**
   * The number of structural features of the '<em>EClass Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ECLASS_PARAMETER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.InvariantImpl <em>Invariant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.InvariantImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getInvariant()
   * @generated
   */
  int INVARIANT = 7;

  /**
   * The feature id for the '<em><b>Context</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__CONTEXT = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__NAME = 1;

  /**
   * The feature id for the '<em><b>Params</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__PARAMS = 2;

  /**
   * The feature id for the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__PREDICATE = 3;

  /**
   * The number of structural features of the '<em>Invariant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.OCLBlockImpl <em>OCL Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.OCLBlockImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getOCLBlock()
   * @generated
   */
  int OCL_BLOCK = 8;

  /**
   * The feature id for the '<em><b>Ocl String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OCL_BLOCK__OCL_STRING = 0;

  /**
   * The number of structural features of the '<em>OCL Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OCL_BLOCK_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl <em>Response</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getResponse()
   * @generated
   */
  int RESPONSE = 9;

  /**
   * The feature id for the '<em><b>Action</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__ACTION = 0;

  /**
   * The feature id for the '<em><b>Context</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__CONTEXT = 1;

  /**
   * The feature id for the '<em><b>Inv</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__INV = 2;

  /**
   * The feature id for the '<em><b>Restore Action</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__RESTORE_ACTION = 3;

  /**
   * The number of structural features of the '<em>Response</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BaseMappingImpl <em>Base Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BaseMappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getBaseMapping()
   * @generated
   */
  int BASE_MAPPING = 10;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASE_MAPPING__MAPPED_ELEMENTS = MAPPING__MAPPED_ELEMENTS;

  /**
   * The feature id for the '<em><b>Whens</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASE_MAPPING__WHENS = MAPPING__WHENS;

  /**
   * The feature id for the '<em><b>Withs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASE_MAPPING__WITHS = MAPPING__WITHS;

  /**
   * The feature id for the '<em><b>Wheres</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASE_MAPPING__WHERES = MAPPING__WHERES;

  /**
   * The number of structural features of the '<em>Base Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASE_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.SubMappingImpl <em>Sub Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.SubMappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getSubMapping()
   * @generated
   */
  int SUB_MAPPING = 11;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_MAPPING__MAPPED_ELEMENTS = MAPPING__MAPPED_ELEMENTS;

  /**
   * The feature id for the '<em><b>Whens</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_MAPPING__WHENS = MAPPING__WHENS;

  /**
   * The feature id for the '<em><b>Withs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_MAPPING__WITHS = MAPPING__WITHS;

  /**
   * The feature id for the '<em><b>Wheres</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_MAPPING__WHERES = MAPPING__WHERES;

  /**
   * The number of structural features of the '<em>Sub Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl <em>Named EClass</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getNamedEClass()
   * @generated
   */
  int NAMED_ECLASS = 12;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS__NAME = MAPPABLE_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Represented EClass</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS__REPRESENTED_ECLASS = MAPPABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Named EClass</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS_FEATURE_COUNT = MAPPABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl <em>Named Feature</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getNamedFeature()
   * @generated
   */
  int NAMED_FEATURE = 13;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE__NAME = MAPPABLE_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Containing Named EClass</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE__CONTAINING_NAMED_ECLASS = MAPPABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Represented Feature</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE__REPRESENTED_FEATURE = MAPPABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Named Feature</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE_FEATURE_COUNT = MAPPABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction <em>Response Action</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getResponseAction()
   * @generated
   */
  int RESPONSE_ACTION = 14;


  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile <em>File</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
   * @generated
   */
  EClass getMIRFile();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedPackage <em>Generated Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Generated Package</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedPackage()
   * @see #getMIRFile()
   * @generated
   */
  EAttribute getMIRFile_GeneratedPackage();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedClass <em>Generated Class</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Generated Class</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedClass()
   * @see #getMIRFile()
   * @generated
   */
  EAttribute getMIRFile_GeneratedClass();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getImports <em>Imports</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Imports</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getImports()
   * @see #getMIRFile()
   * @generated
   */
  EReference getMIRFile_Imports();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getMappings <em>Mappings</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mappings</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getMappings()
   * @see #getMIRFile()
   * @generated
   */
  EReference getMIRFile_Mappings();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getInvariants <em>Invariants</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invariants</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getInvariants()
   * @see #getMIRFile()
   * @generated
   */
  EReference getMIRFile_Invariants();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getResponses <em>Responses</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Responses</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getResponses()
   * @see #getMIRFile()
   * @generated
   */
  EReference getMIRFile_Responses();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import <em>Import</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import
   * @generated
   */
  EClass getImport();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import#getPackage <em>Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Package</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import#getPackage()
   * @see #getImport()
   * @generated
   */
  EReference getImport_Package();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import#getName()
   * @see #getImport()
   * @generated
   */
  EAttribute getImport_Name();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping <em>Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
   * @generated
   */
  EClass getMapping();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getMappedElements <em>Mapped Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mapped Elements</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getMappedElements()
   * @see #getMapping()
   * @generated
   */
  EReference getMapping_MappedElements();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWhens <em>Whens</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Whens</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWhens()
   * @see #getMapping()
   * @generated
   */
  EReference getMapping_Whens();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWiths <em>Withs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Withs</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWiths()
   * @see #getMapping()
   * @generated
   */
  EReference getMapping_Withs();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWheres <em>Wheres</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Wheres</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getWheres()
   * @see #getMapping()
   * @generated
   */
  EReference getMapping_Wheres();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement <em>Mappable Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mappable Element</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement
   * @generated
   */
  EClass getMappableElement();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement#getName()
   * @see #getMappableElement()
   * @generated
   */
  EAttribute getMappableElement_Name();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When <em>When</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>When</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When
   * @generated
   */
  EClass getWhen();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When#getPredicate <em>Predicate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Predicate</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When#getPredicate()
   * @see #getWhen()
   * @generated
   */
  EReference getWhen_Predicate();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where <em>Where</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Where</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where
   * @generated
   */
  EClass getWhere();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where#getPredicate <em>Predicate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Predicate</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where#getPredicate()
   * @see #getWhere()
   * @generated
   */
  EReference getWhere_Predicate();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter <em>EClass Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>EClass Parameter</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter
   * @generated
   */
  EClass getEClassParameter();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Type</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter#getType()
   * @see #getEClassParameter()
   * @generated
   */
  EReference getEClassParameter_Type();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter#getName()
   * @see #getEClassParameter()
   * @generated
   */
  EAttribute getEClassParameter_Name();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant <em>Invariant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invariant</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant
   * @generated
   */
  EClass getInvariant();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getContext <em>Context</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Context</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getContext()
   * @see #getInvariant()
   * @generated
   */
  EReference getInvariant_Context();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getName()
   * @see #getInvariant()
   * @generated
   */
  EAttribute getInvariant_Name();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getParams <em>Params</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Params</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getParams()
   * @see #getInvariant()
   * @generated
   */
  EReference getInvariant_Params();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getPredicate <em>Predicate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Predicate</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant#getPredicate()
   * @see #getInvariant()
   * @generated
   */
  EReference getInvariant_Predicate();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock <em>OCL Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>OCL Block</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock
   * @generated
   */
  EClass getOCLBlock();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock#getOclString <em>Ocl String</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ocl String</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock#getOclString()
   * @see #getOCLBlock()
   * @generated
   */
  EAttribute getOCLBlock_OclString();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response <em>Response</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Response</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response
   * @generated
   */
  EClass getResponse();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getAction <em>Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Action</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getAction()
   * @see #getResponse()
   * @generated
   */
  EAttribute getResponse_Action();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getContext <em>Context</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Context</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getContext()
   * @see #getResponse()
   * @generated
   */
  EReference getResponse_Context();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getInv <em>Inv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Inv</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getInv()
   * @see #getResponse()
   * @generated
   */
  EReference getResponse_Inv();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getRestoreAction <em>Restore Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Restore Action</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getRestoreAction()
   * @see #getResponse()
   * @generated
   */
  EReference getResponse_RestoreAction();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping <em>Base Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Base Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping
   * @generated
   */
  EClass getBaseMapping();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping <em>Sub Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Sub Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping
   * @generated
   */
  EClass getSubMapping();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass <em>Named EClass</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Named EClass</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
   * @generated
   */
  EClass getNamedEClass();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getRepresentedEClass <em>Represented EClass</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Represented EClass</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getRepresentedEClass()
   * @see #getNamedEClass()
   * @generated
   */
  EReference getNamedEClass_RepresentedEClass();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature <em>Named Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Named Feature</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature
   * @generated
   */
  EClass getNamedFeature();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getContainingNamedEClass <em>Containing Named EClass</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Containing Named EClass</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getContainingNamedEClass()
   * @see #getNamedFeature()
   * @generated
   */
  EReference getNamedFeature_ContainingNamedEClass();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getRepresentedFeature <em>Represented Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Represented Feature</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getRepresentedFeature()
   * @see #getNamedFeature()
   * @generated
   */
  EReference getNamedFeature_RepresentedFeature();

  /**
   * Returns the meta object for enum '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction <em>Response Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Response Action</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction
   * @generated
   */
  EEnum getResponseAction();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MIRFactory getMIRFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl <em>File</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMIRFile()
     * @generated
     */
    EClass MIR_FILE = eINSTANCE.getMIRFile();

    /**
     * The meta object literal for the '<em><b>Generated Package</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MIR_FILE__GENERATED_PACKAGE = eINSTANCE.getMIRFile_GeneratedPackage();

    /**
     * The meta object literal for the '<em><b>Generated Class</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MIR_FILE__GENERATED_CLASS = eINSTANCE.getMIRFile_GeneratedClass();

    /**
     * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIR_FILE__IMPORTS = eINSTANCE.getMIRFile_Imports();

    /**
     * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIR_FILE__MAPPINGS = eINSTANCE.getMIRFile_Mappings();

    /**
     * The meta object literal for the '<em><b>Invariants</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIR_FILE__INVARIANTS = eINSTANCE.getMIRFile_Invariants();

    /**
     * The meta object literal for the '<em><b>Responses</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIR_FILE__RESPONSES = eINSTANCE.getMIRFile_Responses();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ImportImpl <em>Import</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ImportImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getImport()
     * @generated
     */
    EClass IMPORT = eINSTANCE.getImport();

    /**
     * The meta object literal for the '<em><b>Package</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IMPORT__PACKAGE = eINSTANCE.getImport_Package();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT__NAME = eINSTANCE.getImport_Name();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl <em>Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMapping()
     * @generated
     */
    EClass MAPPING = eINSTANCE.getMapping();

    /**
     * The meta object literal for the '<em><b>Mapped Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING__MAPPED_ELEMENTS = eINSTANCE.getMapping_MappedElements();

    /**
     * The meta object literal for the '<em><b>Whens</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING__WHENS = eINSTANCE.getMapping_Whens();

    /**
     * The meta object literal for the '<em><b>Withs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING__WITHS = eINSTANCE.getMapping_Withs();

    /**
     * The meta object literal for the '<em><b>Wheres</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING__WHERES = eINSTANCE.getMapping_Wheres();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappableElementImpl <em>Mappable Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappableElementImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMappableElement()
     * @generated
     */
    EClass MAPPABLE_ELEMENT = eINSTANCE.getMappableElement();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MAPPABLE_ELEMENT__NAME = eINSTANCE.getMappableElement_Name();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl <em>When</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getWhen()
     * @generated
     */
    EClass WHEN = eINSTANCE.getWhen();

    /**
     * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHEN__PREDICATE = eINSTANCE.getWhen_Predicate();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhereImpl <em>Where</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhereImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getWhere()
     * @generated
     */
    EClass WHERE = eINSTANCE.getWhere();

    /**
     * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHERE__PREDICATE = eINSTANCE.getWhere_Predicate();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.EClassParameterImpl <em>EClass Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.EClassParameterImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getEClassParameter()
     * @generated
     */
    EClass ECLASS_PARAMETER = eINSTANCE.getEClassParameter();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ECLASS_PARAMETER__TYPE = eINSTANCE.getEClassParameter_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ECLASS_PARAMETER__NAME = eINSTANCE.getEClassParameter_Name();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.InvariantImpl <em>Invariant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.InvariantImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getInvariant()
     * @generated
     */
    EClass INVARIANT = eINSTANCE.getInvariant();

    /**
     * The meta object literal for the '<em><b>Context</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT__CONTEXT = eINSTANCE.getInvariant_Context();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVARIANT__NAME = eINSTANCE.getInvariant_Name();

    /**
     * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT__PARAMS = eINSTANCE.getInvariant_Params();

    /**
     * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT__PREDICATE = eINSTANCE.getInvariant_Predicate();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.OCLBlockImpl <em>OCL Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.OCLBlockImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getOCLBlock()
     * @generated
     */
    EClass OCL_BLOCK = eINSTANCE.getOCLBlock();

    /**
     * The meta object literal for the '<em><b>Ocl String</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OCL_BLOCK__OCL_STRING = eINSTANCE.getOCLBlock_OclString();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl <em>Response</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getResponse()
     * @generated
     */
    EClass RESPONSE = eINSTANCE.getResponse();

    /**
     * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RESPONSE__ACTION = eINSTANCE.getResponse_Action();

    /**
     * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESPONSE__CONTEXT = eINSTANCE.getResponse_Context();

    /**
     * The meta object literal for the '<em><b>Inv</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESPONSE__INV = eINSTANCE.getResponse_Inv();

    /**
     * The meta object literal for the '<em><b>Restore Action</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESPONSE__RESTORE_ACTION = eINSTANCE.getResponse_RestoreAction();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BaseMappingImpl <em>Base Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BaseMappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getBaseMapping()
     * @generated
     */
    EClass BASE_MAPPING = eINSTANCE.getBaseMapping();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.SubMappingImpl <em>Sub Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.SubMappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getSubMapping()
     * @generated
     */
    EClass SUB_MAPPING = eINSTANCE.getSubMapping();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl <em>Named EClass</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getNamedEClass()
     * @generated
     */
    EClass NAMED_ECLASS = eINSTANCE.getNamedEClass();

    /**
     * The meta object literal for the '<em><b>Represented EClass</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NAMED_ECLASS__REPRESENTED_ECLASS = eINSTANCE.getNamedEClass_RepresentedEClass();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl <em>Named Feature</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getNamedFeature()
     * @generated
     */
    EClass NAMED_FEATURE = eINSTANCE.getNamedFeature();

    /**
     * The meta object literal for the '<em><b>Containing Named EClass</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NAMED_FEATURE__CONTAINING_NAMED_ECLASS = eINSTANCE.getNamedFeature_ContainingNamedEClass();

    /**
     * The meta object literal for the '<em><b>Represented Feature</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NAMED_FEATURE__REPRESENTED_FEATURE = eINSTANCE.getNamedFeature_RepresentedFeature();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction <em>Response Action</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getResponseAction()
     * @generated
     */
    EEnum RESPONSE_ACTION = eINSTANCE.getResponseAction();

  }

} //MIRPackage
