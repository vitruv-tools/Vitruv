/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRFactory
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
  String eNS_URI = "http://www.kit.edu/ipd/sdq/vitruvius/framework/design/MIR";

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
  MIRPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl.init();

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getModel()
   * @generated
   */
  int MODEL = 0;

  /**
   * The feature id for the '<em><b>Mm Imports</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__MM_IMPORTS = 0;

  /**
   * The feature id for the '<em><b>Java Import Section</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__JAVA_IMPORT_SECTION = 1;

  /**
   * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__MAPPINGS = 2;

  /**
   * The feature id for the '<em><b>Invariants</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__INVARIANTS = 3;

  /**
   * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__GLOBAL_VARIABLES = 4;

  /**
   * The feature id for the '<em><b>Respones</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__RESPONES = 5;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ImportImpl <em>Import</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ImportImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getImport()
   * @generated
   */
  int IMPORT = 1;

  /**
   * The feature id for the '<em><b>Ns URI</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT__NS_URI = 0;

  /**
   * The feature id for the '<em><b>Mm Ns Prefix</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT__MM_NS_PREFIX = 1;

  /**
   * The number of structural features of the '<em>Import</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MMNsPrefixImpl <em>MM Ns Prefix</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MMNsPrefixImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMMNsPrefix()
   * @generated
   */
  int MM_NS_PREFIX = 2;

  /**
   * The feature id for the '<em><b>Ns Prefix</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MM_NS_PREFIX__NS_PREFIX = 0;

  /**
   * The number of structural features of the '<em>MM Ns Prefix</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MM_NS_PREFIX_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingImpl <em>Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMapping()
   * @generated
   */
  int MAPPING = 3;

  /**
   * The number of structural features of the '<em>Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl <em>Mapping Base</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMappingBase()
   * @generated
   */
  int MAPPING_BASE = 4;

  /**
   * The feature id for the '<em><b>Name A</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__NAME_A = MAPPING_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Metaclass A</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__METACLASS_A = MAPPING_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Name B</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__NAME_B = MAPPING_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Metaclass B</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__METACLASS_B = MAPPING_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Whens</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__WHENS = MAPPING_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Withs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE__WITHS = MAPPING_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Mapping Base</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BASE_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WhenImpl <em>When</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WhenImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getWhen()
   * @generated
   */
  int WHEN = 5;

  /**
   * The feature id for the '<em><b>Xor Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHEN__XOR_EXPRESSION = 0;

  /**
   * The number of structural features of the '<em>When</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHEN_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WithImpl <em>With</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WithImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getWith()
   * @generated
   */
  int WITH = 6;

  /**
   * The number of structural features of the '<em>With</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WITH_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantImpl <em>Invariant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getInvariant()
   * @generated
   */
  int INVARIANT = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__PARAMETERS = 1;

  /**
   * The feature id for the '<em><b>Xor Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT__XOR_EXPRESSION = 2;

  /**
   * The number of structural features of the '<em>Invariant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ParameterImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__NAME = 0;

  /**
   * The feature id for the '<em><b>Metaclass</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__METACLASS = 1;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl <em>Global Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getGlobalVariable()
   * @generated
   */
  int GLOBAL_VARIABLE = 9;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_VARIABLE__NAME = 0;

  /**
   * The feature id for the '<em><b>Jvm Type Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_VARIABLE__JVM_TYPE_REFERENCE = 1;

  /**
   * The number of structural features of the '<em>Global Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_VARIABLE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl <em>Response</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getResponse()
   * @generated
   */
  int RESPONSE = 10;

  /**
   * The feature id for the '<em><b>Operation Restriction</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__OPERATION_RESTRICTION = 0;

  /**
   * The feature id for the '<em><b>Invariant Restriction</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__INVARIANT_RESTRICTION = 1;

  /**
   * The feature id for the '<em><b>Xtend Stmt</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE__XTEND_STMT = 2;

  /**
   * The number of structural features of the '<em>Response</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESPONSE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.OperationRestrictionImpl <em>Operation Restriction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.OperationRestrictionImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getOperationRestriction()
   * @generated
   */
  int OPERATION_RESTRICTION = 11;

  /**
   * The number of structural features of the '<em>Operation Restriction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION_RESTRICTION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.FeatureOperationRestrictionImpl <em>Feature Operation Restriction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.FeatureOperationRestrictionImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getFeatureOperationRestriction()
   * @generated
   */
  int FEATURE_OPERATION_RESTRICTION = 12;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_OPERATION_RESTRICTION__FEATURE = OPERATION_RESTRICTION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Feature Operation Restriction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_OPERATION_RESTRICTION_FEATURE_COUNT = OPERATION_RESTRICTION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantRestrictionImpl <em>Invariant Restriction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantRestrictionImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getInvariantRestriction()
   * @generated
   */
  int INVARIANT_RESTRICTION = 13;

  /**
   * The feature id for the '<em><b>Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT_RESTRICTION__NAME = 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT_RESTRICTION__PARAMETERS = 1;

  /**
   * The number of structural features of the '<em>Invariant Restriction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVARIANT_RESTRICTION_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMmImports <em>Mm Imports</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mm Imports</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMmImports()
   * @see #getModel()
   * @generated
   */
  EReference getModel_MmImports();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getJavaImportSection <em>Java Import Section</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Java Import Section</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getJavaImportSection()
   * @see #getModel()
   * @generated
   */
  EReference getModel_JavaImportSection();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMappings <em>Mappings</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mappings</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMappings()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Mappings();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getInvariants <em>Invariants</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invariants</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getInvariants()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Invariants();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getGlobalVariables <em>Global Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Global Variables</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getGlobalVariables()
   * @see #getModel()
   * @generated
   */
  EReference getModel_GlobalVariables();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getRespones <em>Respones</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Respones</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getRespones()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Respones();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import <em>Import</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import
   * @generated
   */
  EClass getImport();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import#getNsURI <em>Ns URI</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ns URI</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import#getNsURI()
   * @see #getImport()
   * @generated
   */
  EAttribute getImport_NsURI();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import#getMmNsPrefix <em>Mm Ns Prefix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Mm Ns Prefix</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import#getMmNsPrefix()
   * @see #getImport()
   * @generated
   */
  EReference getImport_MmNsPrefix();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MMNsPrefix <em>MM Ns Prefix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>MM Ns Prefix</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MMNsPrefix
   * @generated
   */
  EClass getMMNsPrefix();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MMNsPrefix#getNsPrefix <em>Ns Prefix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ns Prefix</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MMNsPrefix#getNsPrefix()
   * @see #getMMNsPrefix()
   * @generated
   */
  EAttribute getMMNsPrefix_NsPrefix();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Mapping <em>Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Mapping
   * @generated
   */
  EClass getMapping();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase <em>Mapping Base</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mapping Base</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase
   * @generated
   */
  EClass getMappingBase();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameA <em>Name A</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name A</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameA()
   * @see #getMappingBase()
   * @generated
   */
  EAttribute getMappingBase_NameA();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassA <em>Metaclass A</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Metaclass A</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassA()
   * @see #getMappingBase()
   * @generated
   */
  EReference getMappingBase_MetaclassA();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameB <em>Name B</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name B</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameB()
   * @see #getMappingBase()
   * @generated
   */
  EAttribute getMappingBase_NameB();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassB <em>Metaclass B</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Metaclass B</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassB()
   * @see #getMappingBase()
   * @generated
   */
  EReference getMappingBase_MetaclassB();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWhens <em>Whens</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Whens</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWhens()
   * @see #getMappingBase()
   * @generated
   */
  EReference getMappingBase_Whens();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWiths <em>Withs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Withs</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWiths()
   * @see #getMappingBase()
   * @generated
   */
  EReference getMappingBase_Withs();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When <em>When</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>When</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When
   * @generated
   */
  EClass getWhen();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When#getXorExpression <em>Xor Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Xor Expression</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When#getXorExpression()
   * @see #getWhen()
   * @generated
   */
  EReference getWhen_XorExpression();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.With <em>With</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>With</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.With
   * @generated
   */
  EClass getWith();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant <em>Invariant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invariant</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant
   * @generated
   */
  EClass getInvariant();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getName()
   * @see #getInvariant()
   * @generated
   */
  EAttribute getInvariant_Name();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getParameters()
   * @see #getInvariant()
   * @generated
   */
  EReference getInvariant_Parameters();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getXorExpression <em>Xor Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Xor Expression</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant#getXorExpression()
   * @see #getInvariant()
   * @generated
   */
  EReference getInvariant_XorExpression();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter#getName()
   * @see #getParameter()
   * @generated
   */
  EAttribute getParameter_Name();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter#getMetaclass <em>Metaclass</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Metaclass</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Parameter#getMetaclass()
   * @see #getParameter()
   * @generated
   */
  EReference getParameter_Metaclass();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable <em>Global Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Variable</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable
   * @generated
   */
  EClass getGlobalVariable();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getName()
   * @see #getGlobalVariable()
   * @generated
   */
  EAttribute getGlobalVariable_Name();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getJvmTypeReference <em>Jvm Type Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Jvm Type Reference</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getJvmTypeReference()
   * @see #getGlobalVariable()
   * @generated
   */
  EReference getGlobalVariable_JvmTypeReference();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response <em>Response</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Response</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response
   * @generated
   */
  EClass getResponse();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getOperationRestriction <em>Operation Restriction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operation Restriction</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getOperationRestriction()
   * @see #getResponse()
   * @generated
   */
  EReference getResponse_OperationRestriction();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getInvariantRestriction <em>Invariant Restriction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Invariant Restriction</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getInvariantRestriction()
   * @see #getResponse()
   * @generated
   */
  EReference getResponse_InvariantRestriction();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getXtendStmt <em>Xtend Stmt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Xtend Stmt</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getXtendStmt()
   * @see #getResponse()
   * @generated
   */
  EAttribute getResponse_XtendStmt();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.OperationRestriction <em>Operation Restriction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operation Restriction</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.OperationRestriction
   * @generated
   */
  EClass getOperationRestriction();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction <em>Feature Operation Restriction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Operation Restriction</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction
   * @generated
   */
  EClass getFeatureOperationRestriction();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction#getFeature()
   * @see #getFeatureOperationRestriction()
   * @generated
   */
  EAttribute getFeatureOperationRestriction_Feature();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction <em>Invariant Restriction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invariant Restriction</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction
   * @generated
   */
  EClass getInvariantRestriction();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction#getName()
   * @see #getInvariantRestriction()
   * @generated
   */
  EReference getInvariantRestriction_Name();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction#getParameters()
   * @see #getInvariantRestriction()
   * @generated
   */
  EReference getInvariantRestriction_Parameters();

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
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Mm Imports</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__MM_IMPORTS = eINSTANCE.getModel_MmImports();

    /**
     * The meta object literal for the '<em><b>Java Import Section</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__JAVA_IMPORT_SECTION = eINSTANCE.getModel_JavaImportSection();

    /**
     * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__MAPPINGS = eINSTANCE.getModel_Mappings();

    /**
     * The meta object literal for the '<em><b>Invariants</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__INVARIANTS = eINSTANCE.getModel_Invariants();

    /**
     * The meta object literal for the '<em><b>Global Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__GLOBAL_VARIABLES = eINSTANCE.getModel_GlobalVariables();

    /**
     * The meta object literal for the '<em><b>Respones</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__RESPONES = eINSTANCE.getModel_Respones();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ImportImpl <em>Import</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ImportImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getImport()
     * @generated
     */
    EClass IMPORT = eINSTANCE.getImport();

    /**
     * The meta object literal for the '<em><b>Ns URI</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT__NS_URI = eINSTANCE.getImport_NsURI();

    /**
     * The meta object literal for the '<em><b>Mm Ns Prefix</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IMPORT__MM_NS_PREFIX = eINSTANCE.getImport_MmNsPrefix();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MMNsPrefixImpl <em>MM Ns Prefix</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MMNsPrefixImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMMNsPrefix()
     * @generated
     */
    EClass MM_NS_PREFIX = eINSTANCE.getMMNsPrefix();

    /**
     * The meta object literal for the '<em><b>Ns Prefix</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MM_NS_PREFIX__NS_PREFIX = eINSTANCE.getMMNsPrefix_NsPrefix();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingImpl <em>Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMapping()
     * @generated
     */
    EClass MAPPING = eINSTANCE.getMapping();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl <em>Mapping Base</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getMappingBase()
     * @generated
     */
    EClass MAPPING_BASE = eINSTANCE.getMappingBase();

    /**
     * The meta object literal for the '<em><b>Name A</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MAPPING_BASE__NAME_A = eINSTANCE.getMappingBase_NameA();

    /**
     * The meta object literal for the '<em><b>Metaclass A</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BASE__METACLASS_A = eINSTANCE.getMappingBase_MetaclassA();

    /**
     * The meta object literal for the '<em><b>Name B</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MAPPING_BASE__NAME_B = eINSTANCE.getMappingBase_NameB();

    /**
     * The meta object literal for the '<em><b>Metaclass B</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BASE__METACLASS_B = eINSTANCE.getMappingBase_MetaclassB();

    /**
     * The meta object literal for the '<em><b>Whens</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BASE__WHENS = eINSTANCE.getMappingBase_Whens();

    /**
     * The meta object literal for the '<em><b>Withs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BASE__WITHS = eINSTANCE.getMappingBase_Withs();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WhenImpl <em>When</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WhenImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getWhen()
     * @generated
     */
    EClass WHEN = eINSTANCE.getWhen();

    /**
     * The meta object literal for the '<em><b>Xor Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHEN__XOR_EXPRESSION = eINSTANCE.getWhen_XorExpression();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WithImpl <em>With</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.WithImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getWith()
     * @generated
     */
    EClass WITH = eINSTANCE.getWith();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantImpl <em>Invariant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getInvariant()
     * @generated
     */
    EClass INVARIANT = eINSTANCE.getInvariant();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVARIANT__NAME = eINSTANCE.getInvariant_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT__PARAMETERS = eINSTANCE.getInvariant_Parameters();

    /**
     * The meta object literal for the '<em><b>Xor Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT__XOR_EXPRESSION = eINSTANCE.getInvariant_XorExpression();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ParameterImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

    /**
     * The meta object literal for the '<em><b>Metaclass</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARAMETER__METACLASS = eINSTANCE.getParameter_Metaclass();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl <em>Global Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getGlobalVariable()
     * @generated
     */
    EClass GLOBAL_VARIABLE = eINSTANCE.getGlobalVariable();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_VARIABLE__NAME = eINSTANCE.getGlobalVariable_Name();

    /**
     * The meta object literal for the '<em><b>Jvm Type Reference</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_VARIABLE__JVM_TYPE_REFERENCE = eINSTANCE.getGlobalVariable_JvmTypeReference();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl <em>Response</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getResponse()
     * @generated
     */
    EClass RESPONSE = eINSTANCE.getResponse();

    /**
     * The meta object literal for the '<em><b>Operation Restriction</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESPONSE__OPERATION_RESTRICTION = eINSTANCE.getResponse_OperationRestriction();

    /**
     * The meta object literal for the '<em><b>Invariant Restriction</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESPONSE__INVARIANT_RESTRICTION = eINSTANCE.getResponse_InvariantRestriction();

    /**
     * The meta object literal for the '<em><b>Xtend Stmt</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RESPONSE__XTEND_STMT = eINSTANCE.getResponse_XtendStmt();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.OperationRestrictionImpl <em>Operation Restriction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.OperationRestrictionImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getOperationRestriction()
     * @generated
     */
    EClass OPERATION_RESTRICTION = eINSTANCE.getOperationRestriction();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.FeatureOperationRestrictionImpl <em>Feature Operation Restriction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.FeatureOperationRestrictionImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getFeatureOperationRestriction()
     * @generated
     */
    EClass FEATURE_OPERATION_RESTRICTION = eINSTANCE.getFeatureOperationRestriction();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_OPERATION_RESTRICTION__FEATURE = eINSTANCE.getFeatureOperationRestriction_Feature();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantRestrictionImpl <em>Invariant Restriction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.InvariantRestrictionImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MIRPackageImpl#getInvariantRestriction()
     * @generated
     */
    EClass INVARIANT_RESTRICTION = eINSTANCE.getInvariantRestriction();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT_RESTRICTION__NAME = eINSTANCE.getInvariantRestriction_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVARIANT_RESTRICTION__PARAMETERS = eINSTANCE.getInvariantRestriction_Parameters();

  }

} //MIRPackage
