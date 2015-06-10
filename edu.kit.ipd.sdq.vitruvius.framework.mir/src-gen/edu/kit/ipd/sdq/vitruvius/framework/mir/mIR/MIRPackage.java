/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

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
   * The feature id for the '<em><b>Bundles</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__BUNDLES = 2;

  /**
   * The feature id for the '<em><b>Imports</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__IMPORTS = 3;

  /**
   * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE__MAPPINGS = 4;

  /**
   * The number of structural features of the '<em>File</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIR_FILE_FEATURE_COUNT = 5;

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
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BundleImpl <em>Bundle</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BundleImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getBundle()
   * @generated
   */
  int BUNDLE = 2;

  /**
   * The feature id for the '<em><b>Bundle FQN</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BUNDLE__BUNDLE_FQN = 0;

  /**
   * The number of structural features of the '<em>Bundle</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BUNDLE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl <em>Mapping Body</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMappingBody()
   * @generated
   */
  int MAPPING_BODY = 3;

  /**
   * The feature id for the '<em><b>Whenwhere</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BODY__WHENWHERE = 0;

  /**
   * The feature id for the '<em><b>With Blocks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BODY__WITH_BLOCKS = 1;

  /**
   * The feature id for the '<em><b>Withs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BODY__WITHS = 2;

  /**
   * The number of structural features of the '<em>Mapping Body</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_BODY_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl <em>Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMapping()
   * @generated
   */
  int MAPPING = 4;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__MAPPED_ELEMENTS = 0;

  /**
   * The feature id for the '<em><b>Constraints</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING__CONSTRAINTS = 1;

  /**
   * The number of structural features of the '<em>Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAPPING_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementImpl <em>Typed Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getTypedElement()
   * @generated
   */
  int TYPED_ELEMENT = 5;

  /**
   * The number of structural features of the '<em>Typed Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPED_ELEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementRefImpl <em>Typed Element Ref</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementRefImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getTypedElementRef()
   * @generated
   */
  int TYPED_ELEMENT_REF = 6;

  /**
   * The feature id for the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPED_ELEMENT_REF__REF = TYPED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Typed Element Ref</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPED_ELEMENT_REF_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ClassMappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getClassMapping()
   * @generated
   */
  int CLASS_MAPPING = 7;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_MAPPING__MAPPED_ELEMENTS = MAPPING__MAPPED_ELEMENTS;

  /**
   * The feature id for the '<em><b>Constraints</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_MAPPING__CONSTRAINTS = MAPPING__CONSTRAINTS;

  /**
   * The number of structural features of the '<em>Class Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getFeatureMapping()
   * @generated
   */
  int FEATURE_MAPPING = 8;

  /**
   * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_MAPPING__MAPPED_ELEMENTS = MAPPING__MAPPED_ELEMENTS;

  /**
   * The feature id for the '<em><b>Constraints</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_MAPPING__CONSTRAINTS = MAPPING__CONSTRAINTS;

  /**
   * The number of structural features of the '<em>Feature Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl <em>Named EClass</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getNamedEClass()
   * @generated
   */
  int NAMED_ECLASS = 9;

  /**
   * The feature id for the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS__TYPE = TYPED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS__NAME = TYPED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Named EClass</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ECLASS_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureCallImpl <em>Feature Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureCallImpl
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getFeatureCall()
   * @generated
   */
  int FEATURE_CALL = 10;

  /**
   * The feature id for the '<em><b>Ref</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__REF = TYPED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Tail</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__TAIL = TYPED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__TYPE = TYPED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__NAME = TYPED_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Feature Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 4;


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
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getBundles <em>Bundles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Bundles</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getBundles()
   * @see #getMIRFile()
   * @generated
   */
  EReference getMIRFile_Bundles();

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
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle <em>Bundle</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Bundle</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle
   * @generated
   */
  EClass getBundle();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle#getBundleFQN <em>Bundle FQN</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Bundle FQN</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle#getBundleFQN()
   * @see #getBundle()
   * @generated
   */
  EAttribute getBundle_BundleFQN();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody <em>Mapping Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mapping Body</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody
   * @generated
   */
  EClass getMappingBody();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWhenwhere <em>Whenwhere</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Whenwhere</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWhenwhere()
   * @see #getMappingBody()
   * @generated
   */
  EReference getMappingBody_Whenwhere();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWithBlocks <em>With Blocks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>With Blocks</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWithBlocks()
   * @see #getMappingBody()
   * @generated
   */
  EReference getMappingBody_WithBlocks();

  /**
   * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWiths <em>Withs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Withs</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWiths()
   * @see #getMappingBody()
   * @generated
   */
  EReference getMappingBody_Withs();

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
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getConstraints <em>Constraints</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Constraints</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getConstraints()
   * @see #getMapping()
   * @generated
   */
  EReference getMapping_Constraints();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement <em>Typed Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Typed Element</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
   * @generated
   */
  EClass getTypedElement();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef <em>Typed Element Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Typed Element Ref</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
   * @generated
   */
  EClass getTypedElementRef();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef#getRef <em>Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Ref</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef#getRef()
   * @see #getTypedElementRef()
   * @generated
   */
  EReference getTypedElementRef_Ref();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping <em>Class Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
   * @generated
   */
  EClass getClassMapping();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping <em>Feature Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Mapping</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
   * @generated
   */
  EClass getFeatureMapping();

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
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Type</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getType()
   * @see #getNamedEClass()
   * @generated
   */
  EReference getNamedEClass_Type();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getName()
   * @see #getNamedEClass()
   * @generated
   */
  EAttribute getNamedEClass_Name();

  /**
   * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall <em>Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Call</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
   * @generated
   */
  EClass getFeatureCall();

  /**
   * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getRef <em>Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Ref</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getRef()
   * @see #getFeatureCall()
   * @generated
   */
  EReference getFeatureCall_Ref();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getTail <em>Tail</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Tail</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getTail()
   * @see #getFeatureCall()
   * @generated
   */
  EReference getFeatureCall_Tail();

  /**
   * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Type</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getType()
   * @see #getFeatureCall()
   * @generated
   */
  EReference getFeatureCall_Type();

  /**
   * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getName()
   * @see #getFeatureCall()
   * @generated
   */
  EAttribute getFeatureCall_Name();

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
     * The meta object literal for the '<em><b>Bundles</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIR_FILE__BUNDLES = eINSTANCE.getMIRFile_Bundles();

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
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BundleImpl <em>Bundle</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BundleImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getBundle()
     * @generated
     */
    EClass BUNDLE = eINSTANCE.getBundle();

    /**
     * The meta object literal for the '<em><b>Bundle FQN</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BUNDLE__BUNDLE_FQN = eINSTANCE.getBundle_BundleFQN();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl <em>Mapping Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getMappingBody()
     * @generated
     */
    EClass MAPPING_BODY = eINSTANCE.getMappingBody();

    /**
     * The meta object literal for the '<em><b>Whenwhere</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BODY__WHENWHERE = eINSTANCE.getMappingBody_Whenwhere();

    /**
     * The meta object literal for the '<em><b>With Blocks</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BODY__WITH_BLOCKS = eINSTANCE.getMappingBody_WithBlocks();

    /**
     * The meta object literal for the '<em><b>Withs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING_BODY__WITHS = eINSTANCE.getMappingBody_Withs();

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
     * The meta object literal for the '<em><b>Constraints</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAPPING__CONSTRAINTS = eINSTANCE.getMapping_Constraints();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementImpl <em>Typed Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getTypedElement()
     * @generated
     */
    EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementRefImpl <em>Typed Element Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.TypedElementRefImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getTypedElementRef()
     * @generated
     */
    EClass TYPED_ELEMENT_REF = eINSTANCE.getTypedElementRef();

    /**
     * The meta object literal for the '<em><b>Ref</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPED_ELEMENT_REF__REF = eINSTANCE.getTypedElementRef_Ref();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ClassMappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getClassMapping()
     * @generated
     */
    EClass CLASS_MAPPING = eINSTANCE.getClassMapping();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getFeatureMapping()
     * @generated
     */
    EClass FEATURE_MAPPING = eINSTANCE.getFeatureMapping();

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
     * The meta object literal for the '<em><b>Type</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NAMED_ECLASS__TYPE = eINSTANCE.getNamedEClass_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NAMED_ECLASS__NAME = eINSTANCE.getNamedEClass_Name();

    /**
     * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureCallImpl <em>Feature Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureCallImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRPackageImpl#getFeatureCall()
     * @generated
     */
    EClass FEATURE_CALL = eINSTANCE.getFeatureCall();

    /**
     * The meta object literal for the '<em><b>Ref</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_CALL__REF = eINSTANCE.getFeatureCall_Ref();

    /**
     * The meta object literal for the '<em><b>Tail</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_CALL__TAIL = eINSTANCE.getFeatureCall_Tail();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_CALL__TYPE = eINSTANCE.getFeatureCall_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_CALL__NAME = eINSTANCE.getFeatureCall_Name();

  }

} //MIRPackage
