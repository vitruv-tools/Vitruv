/**
 */
package tools.vitruvius.domains.jml.language.jML;

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
 * @see tools.vitruvius.domains.jml.language.jML.JMLFactory
 * @model kind="package"
 * @generated
 */
public interface JMLPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "jML";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.kit.edu/ipd/sdq/vitruvius/domains/jml/language/JML";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "jML";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  JMLPackage eINSTANCE = tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl.init();

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl <em>Compilation Unit</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCompilationUnit()
   * @generated
   */
  int COMPILATION_UNIT = 0;

  /**
   * The feature id for the '<em><b>Packagedeclaration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPILATION_UNIT__PACKAGEDECLARATION = 0;

  /**
   * The feature id for the '<em><b>Importdeclaration</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPILATION_UNIT__IMPORTDECLARATION = 1;

  /**
   * The feature id for the '<em><b>Typedeclaration</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPILATION_UNIT__TYPEDECLARATION = 2;

  /**
   * The number of structural features of the '<em>Compilation Unit</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPILATION_UNIT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PackageDeclarationImpl <em>Package Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.PackageDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPackageDeclaration()
   * @generated
   */
  int PACKAGE_DECLARATION = 1;

  /**
   * The feature id for the '<em><b>Qualifiedname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_DECLARATION__QUALIFIEDNAME = 0;

  /**
   * The number of structural features of the '<em>Package Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_DECLARATION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl <em>Import Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getImportDeclaration()
   * @generated
   */
  int IMPORT_DECLARATION = 2;

  /**
   * The feature id for the '<em><b>Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECLARATION__STATIC = 0;

  /**
   * The feature id for the '<em><b>Qualifiedname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECLARATION__QUALIFIEDNAME = 1;

  /**
   * The feature id for the '<em><b>Wildcard</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECLARATION__WILDCARD = 2;

  /**
   * The number of structural features of the '<em>Import Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECLARATION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ModifiableImpl <em>Modifiable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ModifiableImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifiable()
   * @generated
   */
  int MODIFIABLE = 70;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODIFIABLE__MODIFIERS = 0;

  /**
   * The number of structural features of the '<em>Modifiable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODIFIABLE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassifierDeclarationWithModifierImpl <em>Classifier Declaration With Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassifierDeclarationWithModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassifierDeclarationWithModifier()
   * @generated
   */
  int CLASSIFIER_DECLARATION_WITH_MODIFIER = 3;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_DECLARATION_WITH_MODIFIER__MODIFIERS = MODIFIABLE__MODIFIERS;

  /**
   * The feature id for the '<em><b>Class Or Interface Declaration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION = MODIFIABLE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Classifier Declaration With Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_DECLARATION_WITH_MODIFIER_FEATURE_COUNT = MODIFIABLE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceDeclarationImpl <em>Class Or Interface Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceDeclaration()
   * @generated
   */
  int CLASS_OR_INTERFACE_DECLARATION = 4;

  /**
   * The number of structural features of the '<em>Class Or Interface Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_DECLARATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ModifierImpl <em>Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifier()
   * @generated
   */
  int MODIFIER = 5;

  /**
   * The number of structural features of the '<em>Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODIFIER_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.RegularModifierImpl <em>Regular Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.RegularModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getRegularModifier()
   * @generated
   */
  int REGULAR_MODIFIER = 6;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_MODIFIER__MODIFIER = MODIFIER_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Regular Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_MODIFIER_FEATURE_COUNT = MODIFIER_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassDeclarationImpl <em>Class Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassDeclaration()
   * @generated
   */
  int CLASS_DECLARATION = 7;

  /**
   * The number of structural features of the '<em>Class Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_DECLARATION_FEATURE_COUNT = CLASS_OR_INTERFACE_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NormalClassDeclarationImpl <em>Normal Class Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.NormalClassDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNormalClassDeclaration()
   * @generated
   */
  int NORMAL_CLASS_DECLARATION = 8;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION__IDENTIFIER = CLASS_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Typeparameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION__TYPEPARAMETERS = CLASS_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Super Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION__SUPER_TYPE = CLASS_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Implemented Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION__IMPLEMENTED_TYPES = CLASS_DECLARATION_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Body Declarations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION__BODY_DECLARATIONS = CLASS_DECLARATION_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Normal Class Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_CLASS_DECLARATION_FEATURE_COUNT = CLASS_DECLARATION_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclOldImpl <em>Generic Method Or Constructor Decl Old</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclOldImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getGenericMethodOrConstructorDeclOld()
   * @generated
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD = 53;

  /**
   * The feature id for the '<em><b>Constructor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR = 0;

  /**
   * The number of structural features of the '<em>Generic Method Or Constructor Decl Old</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl <em>Type Parameters</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeParameters()
   * @generated
   */
  int TYPE_PARAMETERS = 9;

  /**
   * The feature id for the '<em><b>Constructor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__CONSTRUCTOR = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR;

  /**
   * The feature id for the '<em><b>Typeparameter</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__TYPEPARAMETER = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__TYPE = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__IDENTIFIER = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__PARAMETERS = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Exceptions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__EXCEPTIONS = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Methodbody</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS__METHODBODY = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Type Parameters</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETERS_FEATURE_COUNT = GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParameterImpl <em>Type Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeParameterImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeParameter()
   * @generated
   */
  int TYPE_PARAMETER = 10;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETER__IDENTIFIER = 0;

  /**
   * The feature id for the '<em><b>Typebound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETER__TYPEBOUND = 1;

  /**
   * The number of structural features of the '<em>Type Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_PARAMETER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeBoundImpl <em>Type Bound</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeBoundImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeBound()
   * @generated
   */
  int TYPE_BOUND = 11;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_BOUND__TYPE = 0;

  /**
   * The number of structural features of the '<em>Type Bound</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_BOUND_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl <em>Enum Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumDeclaration()
   * @generated
   */
  int ENUM_DECLARATION = 12;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_DECLARATION__IDENTIFIER = CLASS_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Implemented Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_DECLARATION__IMPLEMENTED_TYPES = CLASS_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Enumconstants</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_DECLARATION__ENUMCONSTANTS = CLASS_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Body Declarations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_DECLARATION__BODY_DECLARATIONS = CLASS_DECLARATION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Enum Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_DECLARATION_FEATURE_COUNT = CLASS_DECLARATION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantsImpl <em>Enum Constants</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.EnumConstantsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumConstants()
   * @generated
   */
  int ENUM_CONSTANTS = 13;

  /**
   * The feature id for the '<em><b>Enumconstant</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANTS__ENUMCONSTANT = 0;

  /**
   * The number of structural features of the '<em>Enum Constants</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl <em>Enum Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumConstant()
   * @generated
   */
  int ENUM_CONSTANT = 14;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANT__ANNOTATIONS = 0;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANT__IDENTIFIER = 1;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANT__ARGUMENTS = 2;

  /**
   * The feature id for the '<em><b>Classbodydeclaration</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANT__CLASSBODYDECLARATION = 3;

  /**
   * The number of structural features of the '<em>Enum Constant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_CONSTANT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumBodyDeclarationsImpl <em>Enum Body Declarations</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.EnumBodyDeclarationsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumBodyDeclarations()
   * @generated
   */
  int ENUM_BODY_DECLARATIONS = 15;

  /**
   * The feature id for the '<em><b>Classbodydeclaration</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION = 0;

  /**
   * The number of structural features of the '<em>Enum Body Declarations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENUM_BODY_DECLARATIONS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ArgumentsImpl <em>Arguments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ArgumentsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getArguments()
   * @generated
   */
  int ARGUMENTS = 16;

  /**
   * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENTS__EXPRESSIONS = 0;

  /**
   * The number of structural features of the '<em>Arguments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.InterfaceDeclarationImpl <em>Interface Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.InterfaceDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getInterfaceDeclaration()
   * @generated
   */
  int INTERFACE_DECLARATION = 17;

  /**
   * The number of structural features of the '<em>Interface Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE_DECLARATION_FEATURE_COUNT = CLASS_OR_INTERFACE_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl <em>Normal Interface Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNormalInterfaceDeclaration()
   * @generated
   */
  int NORMAL_INTERFACE_DECLARATION = 18;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_INTERFACE_DECLARATION__IDENTIFIER = INTERFACE_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Typeparameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS = INTERFACE_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Implemented Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES = INTERFACE_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Body Declarations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS = INTERFACE_DECLARATION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Normal Interface Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NORMAL_INTERFACE_DECLARATION_FEATURE_COUNT = INTERFACE_DECLARATION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassBodyDeclarationImpl <em>Class Body Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassBodyDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassBodyDeclaration()
   * @generated
   */
  int CLASS_BODY_DECLARATION = 19;

  /**
   * The number of structural features of the '<em>Class Body Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_BODY_DECLARATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.StaticBlockImpl <em>Static Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.StaticBlockImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getStaticBlock()
   * @generated
   */
  int STATIC_BLOCK = 20;

  /**
   * The feature id for the '<em><b>Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATIC_BLOCK__STATIC = CLASS_BODY_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATIC_BLOCK__BLOCK = CLASS_BODY_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Static Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATIC_BLOCK_FEATURE_COUNT = CLASS_BODY_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl <em>Specified Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecifiedElement()
   * @generated
   */
  int JML_SPECIFIED_ELEMENT = 21;

  /**
   * The feature id for the '<em><b>Jml Type Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS = CLASS_BODY_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Jml Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS = CLASS_BODY_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFIED_ELEMENT__ELEMENT = CLASS_BODY_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Specified Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFIED_ELEMENT_FEATURE_COUNT = CLASS_BODY_DECLARATION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMultilineSpecImpl <em>Multiline Spec</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMultilineSpecImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMultilineSpec()
   * @generated
   */
  int JML_MULTILINE_SPEC = 22;

  /**
   * The feature id for the '<em><b>Jml Type Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MULTILINE_SPEC__JML_TYPE_SPECIFICATIONS = JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS;

  /**
   * The feature id for the '<em><b>Jml Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MULTILINE_SPEC__JML_SPECIFICATIONS = JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MULTILINE_SPEC__ELEMENT = JML_SPECIFIED_ELEMENT__ELEMENT;

  /**
   * The feature id for the '<em><b>Model Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MULTILINE_SPEC__MODEL_ELEMENT = JML_SPECIFIED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Multiline Spec</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MULTILINE_SPEC_FEATURE_COUNT = JML_SPECIFIED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSinglelineSpecImpl <em>Singleline Spec</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSinglelineSpecImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSinglelineSpec()
   * @generated
   */
  int JML_SINGLELINE_SPEC = 23;

  /**
   * The feature id for the '<em><b>Jml Type Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SINGLELINE_SPEC__JML_TYPE_SPECIFICATIONS = JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS;

  /**
   * The feature id for the '<em><b>Jml Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SINGLELINE_SPEC__JML_SPECIFICATIONS = JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SINGLELINE_SPEC__ELEMENT = JML_SPECIFIED_ELEMENT__ELEMENT;

  /**
   * The number of structural features of the '<em>Singleline Spec</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SINGLELINE_SPEC_FEATURE_COUNT = JML_SPECIFIED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLExpressionHavingImpl <em>Expression Having</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLExpressionHavingImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLExpressionHaving()
   * @generated
   */
  int JML_EXPRESSION_HAVING = 24;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_EXPRESSION_HAVING__EXPR = 0;

  /**
   * The number of structural features of the '<em>Expression Having</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_EXPRESSION_HAVING_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.VisiblityModifierImpl <em>Visiblity Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.VisiblityModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVisiblityModifier()
   * @generated
   */
  int VISIBLITY_MODIFIER = 25;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISIBLITY_MODIFIER__MODIFIER = 0;

  /**
   * The number of structural features of the '<em>Visiblity Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISIBLITY_MODIFIER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl <em>Method Specification With Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifier()
   * @generated
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER = 26;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER = 0;

  /**
   * The feature id for the '<em><b>Spec</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC = 1;

  /**
   * The number of structural features of the '<em>Method Specification With Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierRegularImpl <em>Method Specification With Modifier Regular</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierRegularImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifierRegular()
   * @generated
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR = 27;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR__MODIFIER = JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER;

  /**
   * The feature id for the '<em><b>Spec</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR__SPEC = JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC;

  /**
   * The number of structural features of the '<em>Method Specification With Modifier Regular</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR_FEATURE_COUNT = JML_METHOD_SPECIFICATION_WITH_MODIFIER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierExtendedImpl <em>Method Specification With Modifier Extended</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierExtendedImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifierExtended()
   * @generated
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED = 28;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED__MODIFIER = JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER;

  /**
   * The feature id for the '<em><b>Spec</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED__SPEC = JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC;

  /**
   * The number of structural features of the '<em>Method Specification With Modifier Extended</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED_FEATURE_COUNT = JML_METHOD_SPECIFICATION_WITH_MODIFIER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationImpl <em>Method Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecification()
   * @generated
   */
  int JML_METHOD_SPECIFICATION = 29;

  /**
   * The number of structural features of the '<em>Method Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_SPECIFICATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodBehaviorImpl <em>Method Behavior</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodBehaviorImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodBehavior()
   * @generated
   */
  int JML_METHOD_BEHAVIOR = 30;

  /**
   * The feature id for the '<em><b>Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_BEHAVIOR__SPECIFICATIONS = JML_METHOD_SPECIFICATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Method Behavior</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_BEHAVIOR_FEATURE_COUNT = JML_METHOD_SPECIFICATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLExceptionalBehaviorBlockImpl <em>Exceptional Behavior Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLExceptionalBehaviorBlockImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLExceptionalBehaviorBlock()
   * @generated
   */
  int JML_EXCEPTIONAL_BEHAVIOR_BLOCK = 31;

  /**
   * The feature id for the '<em><b>Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_EXCEPTIONAL_BEHAVIOR_BLOCK__SPECIFICATIONS = JML_METHOD_BEHAVIOR__SPECIFICATIONS;

  /**
   * The number of structural features of the '<em>Exceptional Behavior Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_EXCEPTIONAL_BEHAVIOR_BLOCK_FEATURE_COUNT = JML_METHOD_BEHAVIOR_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLNormalBehaviorBlockImpl <em>Normal Behavior Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLNormalBehaviorBlockImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLNormalBehaviorBlock()
   * @generated
   */
  int JML_NORMAL_BEHAVIOR_BLOCK = 32;

  /**
   * The feature id for the '<em><b>Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_NORMAL_BEHAVIOR_BLOCK__SPECIFICATIONS = JML_METHOD_BEHAVIOR__SPECIFICATIONS;

  /**
   * The number of structural features of the '<em>Normal Behavior Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_NORMAL_BEHAVIOR_BLOCK_FEATURE_COUNT = JML_METHOD_BEHAVIOR_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLBehaviorBlockImpl <em>Behavior Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLBehaviorBlockImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLBehaviorBlock()
   * @generated
   */
  int JML_BEHAVIOR_BLOCK = 33;

  /**
   * The feature id for the '<em><b>Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_BEHAVIOR_BLOCK__SPECIFICATIONS = JML_METHOD_BEHAVIOR__SPECIFICATIONS;

  /**
   * The number of structural features of the '<em>Behavior Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_BEHAVIOR_BLOCK_FEATURE_COUNT = JML_METHOD_BEHAVIOR_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodExpressionImpl <em>Method Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodExpression()
   * @generated
   */
  int JML_METHOD_EXPRESSION = 34;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_EXPRESSION__EXPR = JML_EXPRESSION_HAVING__EXPR;

  /**
   * The number of structural features of the '<em>Method Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_METHOD_EXPRESSION_FEATURE_COUNT = JML_EXPRESSION_HAVING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLEnsuresExpressionImpl <em>Ensures Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLEnsuresExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLEnsuresExpression()
   * @generated
   */
  int JML_ENSURES_EXPRESSION = 35;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_ENSURES_EXPRESSION__EXPR = JML_METHOD_EXPRESSION__EXPR;

  /**
   * The number of structural features of the '<em>Ensures Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_ENSURES_EXPRESSION_FEATURE_COUNT = JML_METHOD_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLRequiresExpressionImpl <em>Requires Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLRequiresExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLRequiresExpression()
   * @generated
   */
  int JML_REQUIRES_EXPRESSION = 36;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_REQUIRES_EXPRESSION__EXPR = JML_METHOD_EXPRESSION__EXPR;

  /**
   * The number of structural features of the '<em>Requires Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_REQUIRES_EXPRESSION_FEATURE_COUNT = JML_METHOD_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl <em>Specification Only Element With Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecificationOnlyElementWithModifier()
   * @generated
   */
  int JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER = 37;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER = 0;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT = 1;

  /**
   * The number of structural features of the '<em>Specification Only Element With Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl <em>Specification Only Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecificationOnlyElement()
   * @generated
   */
  int JML_SPECIFICATION_ONLY_ELEMENT = 38;

  /**
   * The feature id for the '<em><b>Instance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE = 0;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT = 1;

  /**
   * The number of structural features of the '<em>Specification Only Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_SPECIFICATION_ONLY_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLModelElementImpl <em>Model Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLModelElementImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLModelElement()
   * @generated
   */
  int JML_MODEL_ELEMENT = 39;

  /**
   * The feature id for the '<em><b>Instance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MODEL_ELEMENT__INSTANCE = JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MODEL_ELEMENT__ELEMENT = JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT;

  /**
   * The number of structural features of the '<em>Model Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MODEL_ELEMENT_FEATURE_COUNT = JML_SPECIFICATION_ONLY_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLGhostElementImpl <em>Ghost Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLGhostElementImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLGhostElement()
   * @generated
   */
  int JML_GHOST_ELEMENT = 40;

  /**
   * The feature id for the '<em><b>Instance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_GHOST_ELEMENT__INSTANCE = JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_GHOST_ELEMENT__ELEMENT = JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT;

  /**
   * The number of structural features of the '<em>Ghost Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_GHOST_ELEMENT_FEATURE_COUNT = JML_SPECIFICATION_ONLY_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionWithModifierImpl <em>Type Expression With Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionWithModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLTypeExpressionWithModifier()
   * @generated
   */
  int JML_TYPE_EXPRESSION_WITH_MODIFIER = 41;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_TYPE_EXPRESSION_WITH_MODIFIER__MODIFIER = 0;

  /**
   * The feature id for the '<em><b>Spec</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_TYPE_EXPRESSION_WITH_MODIFIER__SPEC = 1;

  /**
   * The number of structural features of the '<em>Type Expression With Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_TYPE_EXPRESSION_WITH_MODIFIER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionImpl <em>Type Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLTypeExpression()
   * @generated
   */
  int JML_TYPE_EXPRESSION = 42;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_TYPE_EXPRESSION__EXPR = JML_EXPRESSION_HAVING__EXPR;

  /**
   * The number of structural features of the '<em>Type Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_TYPE_EXPRESSION_FEATURE_COUNT = JML_EXPRESSION_HAVING_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLInvariantExpressionImpl <em>Invariant Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLInvariantExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLInvariantExpression()
   * @generated
   */
  int JML_INVARIANT_EXPRESSION = 43;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_INVARIANT_EXPRESSION__EXPR = JML_TYPE_EXPRESSION__EXPR;

  /**
   * The number of structural features of the '<em>Invariant Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_INVARIANT_EXPRESSION_FEATURE_COUNT = JML_TYPE_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLConstraintExpressionImpl <em>Constraint Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLConstraintExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLConstraintExpression()
   * @generated
   */
  int JML_CONSTRAINT_EXPRESSION = 44;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_CONSTRAINT_EXPRESSION__EXPR = JML_TYPE_EXPRESSION__EXPR;

  /**
   * The number of structural features of the '<em>Constraint Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_CONSTRAINT_EXPRESSION_FEATURE_COUNT = JML_TYPE_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLAxiomExpressionImpl <em>Axiom Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLAxiomExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLAxiomExpression()
   * @generated
   */
  int JML_AXIOM_EXPRESSION = 45;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_AXIOM_EXPRESSION__EXPR = JML_TYPE_EXPRESSION__EXPR;

  /**
   * The number of structural features of the '<em>Axiom Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_AXIOM_EXPRESSION_FEATURE_COUNT = JML_TYPE_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMemberModifierImpl <em>Member Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMemberModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMemberModifier()
   * @generated
   */
  int JML_MEMBER_MODIFIER = 46;

  /**
   * The feature id for the '<em><b>Modifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MEMBER_MODIFIER__MODIFIER = 0;

  /**
   * The number of structural features of the '<em>Member Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_MEMBER_MODIFIER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl <em>Member Decl With Modifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifier()
   * @generated
   */
  int MEMBER_DECL_WITH_MODIFIER = 47;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER__MODIFIERS = MODIFIABLE__MODIFIERS;

  /**
   * The feature id for the '<em><b>Jml Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS = MODIFIABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Memberdecl</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER__MEMBERDECL = MODIFIABLE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Member Decl With Modifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_FEATURE_COUNT = MODIFIABLE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierRegularImpl <em>Member Decl With Modifier Regular</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierRegularImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifierRegular()
   * @generated
   */
  int MEMBER_DECL_WITH_MODIFIER_REGULAR = 48;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_REGULAR__MODIFIERS = MEMBER_DECL_WITH_MODIFIER__MODIFIERS;

  /**
   * The feature id for the '<em><b>Jml Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_REGULAR__JML_MODIFIERS = MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS;

  /**
   * The feature id for the '<em><b>Memberdecl</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_REGULAR__MEMBERDECL = MEMBER_DECL_WITH_MODIFIER__MEMBERDECL;

  /**
   * The number of structural features of the '<em>Member Decl With Modifier Regular</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_REGULAR_FEATURE_COUNT = MEMBER_DECL_WITH_MODIFIER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierSpecImpl <em>Member Decl With Modifier Spec</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierSpecImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifierSpec()
   * @generated
   */
  int MEMBER_DECL_WITH_MODIFIER_SPEC = 49;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_SPEC__MODIFIERS = MEMBER_DECL_WITH_MODIFIER__MODIFIERS;

  /**
   * The feature id for the '<em><b>Jml Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_SPEC__JML_MODIFIERS = MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS;

  /**
   * The feature id for the '<em><b>Memberdecl</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_SPEC__MEMBERDECL = MEMBER_DECL_WITH_MODIFIER__MEMBERDECL;

  /**
   * The number of structural features of the '<em>Member Decl With Modifier Spec</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_WITH_MODIFIER_SPEC_FEATURE_COUNT = MEMBER_DECL_WITH_MODIFIER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclImpl <em>Member Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDecl()
   * @generated
   */
  int MEMBER_DECL = 50;

  /**
   * The number of structural features of the '<em>Member Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECL_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl <em>Constructor</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructor()
   * @generated
   */
  int CONSTRUCTOR = 51;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR__IDENTIFIER = MEMBER_DECL_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR__PARAMETERS = MEMBER_DECL_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Exceptions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR__EXCEPTIONS = MEMBER_DECL_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Constructorbody</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR__CONSTRUCTORBODY = MEMBER_DECL_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Constructor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_FEATURE_COUNT = MEMBER_DECL_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl <em>Member Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclaration()
   * @generated
   */
  int MEMBER_DECLARATION = 52;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECLARATION__TYPE = MEMBER_DECL_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECLARATION__METHOD = MEMBER_DECL_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Field</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECLARATION__FIELD = MEMBER_DECL_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Member Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_DECLARATION_FEATURE_COUNT = MEMBER_DECL_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl <em>Generic Method Or Constructor Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getGenericMethodOrConstructorDecl()
   * @generated
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL = 54;

  /**
   * The feature id for the '<em><b>Type Parameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS = MEMBER_DECL_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE = MEMBER_DECL_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD = MEMBER_DECL_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Constructor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR = MEMBER_DECL_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Generic Method Or Constructor Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_METHOD_OR_CONSTRUCTOR_DECL_FEATURE_COUNT = MEMBER_DECL_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.IdentifierHavingImpl <em>Identifier Having</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.IdentifierHavingImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getIdentifierHaving()
   * @generated
   */
  int IDENTIFIER_HAVING = 87;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTIFIER_HAVING__IDENTIFIER = 0;

  /**
   * The number of structural features of the '<em>Identifier Having</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTIFIER_HAVING_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl <em>Method Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMethodDeclaration()
   * @generated
   */
  int METHOD_DECLARATION = 55;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_DECLARATION__IDENTIFIER = IDENTIFIER_HAVING__IDENTIFIER;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_DECLARATION__PARAMETERS = IDENTIFIER_HAVING_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Exceptions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_DECLARATION__EXCEPTIONS = IDENTIFIER_HAVING_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Methodbody</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_DECLARATION__METHODBODY = IDENTIFIER_HAVING_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Method Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_DECLARATION_FEATURE_COUNT = IDENTIFIER_HAVING_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FieldDeclarationImpl <em>Field Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.FieldDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFieldDeclaration()
   * @generated
   */
  int FIELD_DECLARATION = 56;

  /**
   * The feature id for the '<em><b>Variabledeclarator</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_DECLARATION__VARIABLEDECLARATOR = 0;

  /**
   * The number of structural features of the '<em>Field Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_DECLARATION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DeclaredExceptionImpl <em>Declared Exception</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.DeclaredExceptionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDeclaredException()
   * @generated
   */
  int DECLARED_EXCEPTION = 57;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECLARED_EXCEPTION__NAME = 0;

  /**
   * The number of structural features of the '<em>Declared Exception</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECLARED_EXCEPTION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.VariableDeclaratorImpl <em>Variable Declarator</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.VariableDeclaratorImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVariableDeclarator()
   * @generated
   */
  int VARIABLE_DECLARATOR = 58;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECLARATOR__IDENTIFIER = IDENTIFIER_HAVING__IDENTIFIER;

  /**
   * The feature id for the '<em><b>Brackets</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECLARATOR__BRACKETS = IDENTIFIER_HAVING_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECLARATOR__EXPRESSION = IDENTIFIER_HAVING_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Variable Declarator</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECLARATOR_FEATURE_COUNT = IDENTIFIER_HAVING_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeImpl <em>Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getType()
   * @generated
   */
  int TYPE = 59;

  /**
   * The feature id for the '<em><b>Brackets</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE__BRACKETS = 0;

  /**
   * The number of structural features of the '<em>Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeWithBracketsImpl <em>Class Or Interface Type With Brackets</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeWithBracketsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceTypeWithBrackets()
   * @generated
   */
  int CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS = 60;

  /**
   * The feature id for the '<em><b>Brackets</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS__BRACKETS = TYPE__BRACKETS;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS__TYPE = TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Class Or Interface Type With Brackets</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl <em>Primitive Type With Brackets</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPrimitiveTypeWithBrackets()
   * @generated
   */
  int PRIMITIVE_TYPE_WITH_BRACKETS = 61;

  /**
   * The feature id for the '<em><b>Brackets</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRIMITIVE_TYPE_WITH_BRACKETS__BRACKETS = TYPE__BRACKETS;

  /**
   * The feature id for the '<em><b>Primitivetype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE = TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Primitive Type With Brackets</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRIMITIVE_TYPE_WITH_BRACKETS_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BracketsImpl <em>Brackets</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BracketsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBrackets()
   * @generated
   */
  int BRACKETS = 62;

  /**
   * The number of structural features of the '<em>Brackets</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRACKETS_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeImpl <em>Class Or Interface Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceType()
   * @generated
   */
  int CLASS_OR_INTERFACE_TYPE = 63;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_TYPE__TYPE = 0;

  /**
   * The number of structural features of the '<em>Class Or Interface Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_OR_INTERFACE_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassifierTypeImpl <em>Classifier Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClassifierTypeImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassifierType()
   * @generated
   */
  int CLASSIFIER_TYPE = 64;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_TYPE__IDENTIFIER = 0;

  /**
   * The feature id for the '<em><b>Typearguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_TYPE__TYPEARGUMENTS = 1;

  /**
   * The number of structural features of the '<em>Classifier Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFIER_TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentsImpl <em>Type Arguments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeArguments()
   * @generated
   */
  int TYPE_ARGUMENTS = 65;

  /**
   * The feature id for the '<em><b>Typeargument</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENTS__TYPEARGUMENT = 0;

  /**
   * The number of structural features of the '<em>Type Arguments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeArgument()
   * @generated
   */
  int TYPE_ARGUMENT = 66;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENT__TYPE = 0;

  /**
   * The feature id for the '<em><b>Wildcard</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENT__WILDCARD = 1;

  /**
   * The feature id for the '<em><b>Extends</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENT__EXTENDS = 2;

  /**
   * The feature id for the '<em><b>Super</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENT__SUPER = 3;

  /**
   * The number of structural features of the '<em>Type Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_ARGUMENT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl <em>Formal Parameter Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFormalParameterDecl()
   * @generated
   */
  int FORMAL_PARAMETER_DECL = 67;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_PARAMETER_DECL__MODIFIERS = MODIFIABLE__MODIFIERS;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_PARAMETER_DECL__TYPE = MODIFIABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Varargs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_PARAMETER_DECL__VARARGS = MODIFIABLE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_PARAMETER_DECL__IDENTIFIER = MODIFIABLE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Formal Parameter Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_PARAMETER_DECL_FEATURE_COUNT = MODIFIABLE_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MethodBodyImpl <em>Method Body</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MethodBodyImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMethodBody()
   * @generated
   */
  int METHOD_BODY = 68;

  /**
   * The number of structural features of the '<em>Method Body</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_BODY_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorBodyImpl <em>Constructor Body</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorBodyImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructorBody()
   * @generated
   */
  int CONSTRUCTOR_BODY = 69;

  /**
   * The feature id for the '<em><b>Blockstatement</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_BODY__BLOCKSTATEMENT = 0;

  /**
   * The number of structural features of the '<em>Constructor Body</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_BODY_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypedImpl <em>Typed</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TypedImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTyped()
   * @generated
   */
  int TYPED = 71;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPED__TYPE = 0;

  /**
   * The number of structural features of the '<em>Typed</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationsImpl <em>Annotations</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotations()
   * @generated
   */
  int ANNOTATIONS = 72;

  /**
   * The feature id for the '<em><b>Annotation</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATIONS__ANNOTATION = 0;

  /**
   * The number of structural features of the '<em>Annotations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATIONS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl <em>Annotation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotation()
   * @generated
   */
  int ANNOTATION = 73;

  /**
   * The feature id for the '<em><b>Annotationname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION__ANNOTATIONNAME = MODIFIER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Elementvaluepairs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION__ELEMENTVALUEPAIRS = MODIFIER_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Elementvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION__ELEMENTVALUE = MODIFIER_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Annotation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_FEATURE_COUNT = MODIFIER_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairsImpl <em>Element Value Pairs</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairsImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValuePairs()
   * @generated
   */
  int ELEMENT_VALUE_PAIRS = 74;

  /**
   * The feature id for the '<em><b>Elementvaluepair</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR = 0;

  /**
   * The number of structural features of the '<em>Element Value Pairs</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_PAIRS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairImpl <em>Element Value Pair</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValuePair()
   * @generated
   */
  int ELEMENT_VALUE_PAIR = 75;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_PAIR__IDENTIFIER = 0;

  /**
   * The feature id for the '<em><b>Elementvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_PAIR__ELEMENTVALUE = 1;

  /**
   * The number of structural features of the '<em>Element Value Pair</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_PAIR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValueImpl <em>Element Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValueImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValue()
   * @generated
   */
  int ELEMENT_VALUE = 76;

  /**
   * The number of structural features of the '<em>Element Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValueArrayInitializerImpl <em>Element Value Array Initializer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValueArrayInitializerImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValueArrayInitializer()
   * @generated
   */
  int ELEMENT_VALUE_ARRAY_INITIALIZER = 77;

  /**
   * The feature id for the '<em><b>Elementvalue</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE = ELEMENT_VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Element Value Array Initializer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_VALUE_ARRAY_INITIALIZER_FEATURE_COUNT = ELEMENT_VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl <em>Annotation Type Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeDeclaration()
   * @generated
   */
  int ANNOTATION_TYPE_DECLARATION = 78;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_DECLARATION__IDENTIFIER = INTERFACE_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Annotationtypeelementdeclaration</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION = INTERFACE_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Annotation Type Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_DECLARATION_FEATURE_COUNT = INTERFACE_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementDeclarationImpl <em>Annotation Type Element Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementDeclarationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeElementDeclaration()
   * @generated
   */
  int ANNOTATION_TYPE_ELEMENT_DECLARATION = 79;

  /**
   * The feature id for the '<em><b>Modifiers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_ELEMENT_DECLARATION__MODIFIERS = MODIFIABLE__MODIFIERS;

  /**
   * The feature id for the '<em><b>Annotationtypeelementrest</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST = MODIFIABLE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Annotation Type Element Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_ELEMENT_DECLARATION_FEATURE_COUNT = MODIFIABLE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementRestImpl <em>Annotation Type Element Rest</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementRestImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeElementRest()
   * @generated
   */
  int ANNOTATION_TYPE_ELEMENT_REST = 80;

  /**
   * The number of structural features of the '<em>Annotation Type Element Rest</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_TYPE_ELEMENT_REST_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl <em>Annotation Method Or Constant Rest</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationMethodOrConstantRest()
   * @generated
   */
  int ANNOTATION_METHOD_OR_CONSTANT_REST = 81;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_OR_CONSTANT_REST__TYPE = TYPED__TYPE;

  /**
   * The feature id for the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD = TYPED_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Constant</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT = TYPED_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Annotation Method Or Constant Rest</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_OR_CONSTANT_REST_FEATURE_COUNT = TYPED_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl <em>Annotation Method Rest</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationMethodRest()
   * @generated
   */
  int ANNOTATION_METHOD_REST = 82;

  /**
   * The feature id for the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_REST__IDENTIFIER = 0;

  /**
   * The feature id for the '<em><b>Defaultvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_REST__DEFAULTVALUE = 1;

  /**
   * The number of structural features of the '<em>Annotation Method Rest</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_METHOD_REST_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationConstantRestImpl <em>Annotation Constant Rest</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationConstantRestImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationConstantRest()
   * @generated
   */
  int ANNOTATION_CONSTANT_REST = 83;

  /**
   * The feature id for the '<em><b>Variabledeclarator</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_CONSTANT_REST__VARIABLEDECLARATOR = 0;

  /**
   * The number of structural features of the '<em>Annotation Constant Rest</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_CONSTANT_REST_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DefaultValueImpl <em>Default Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.DefaultValueImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDefaultValue()
   * @generated
   */
  int DEFAULT_VALUE = 84;

  /**
   * The feature id for the '<em><b>Elementvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT_VALUE__ELEMENTVALUE = 0;

  /**
   * The number of structural features of the '<em>Default Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT_VALUE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockImpl <em>Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BlockImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlock()
   * @generated
   */
  int BLOCK = 85;

  /**
   * The feature id for the '<em><b>Blockstatement</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK__BLOCKSTATEMENT = METHOD_BODY_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_FEATURE_COUNT = METHOD_BODY_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockStatementImpl <em>Block Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BlockStatementImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlockStatement()
   * @generated
   */
  int BLOCK_STATEMENT = 86;

  /**
   * The number of structural features of the '<em>Block Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_STATEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ExpressionImpl <em>Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getExpression()
   * @generated
   */
  int EXPRESSION = 88;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__TYPE = BLOCK_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__NAME = BLOCK_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__RIGHT = BLOCK_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_FEATURE_COUNT = BLOCK_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ParenthesisExpressionImpl <em>Parenthesis Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ParenthesisExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getParenthesisExpression()
   * @generated
   */
  int PARENTHESIS_EXPRESSION = 89;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARENTHESIS_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARENTHESIS_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARENTHESIS_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARENTHESIS_EXPRESSION__EXPR = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Parenthesis Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARENTHESIS_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CollectionLiteralImpl <em>Collection Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.CollectionLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCollectionLiteral()
   * @generated
   */
  int COLLECTION_LITERAL = 90;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTION_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTION_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTION_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTION_LITERAL__ELEMENTS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Collection Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTION_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SetLiteralImpl <em>Set Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.SetLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSetLiteral()
   * @generated
   */
  int SET_LITERAL = 91;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_LITERAL__TYPE = COLLECTION_LITERAL__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_LITERAL__NAME = COLLECTION_LITERAL__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_LITERAL__RIGHT = COLLECTION_LITERAL__RIGHT;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_LITERAL__ELEMENTS = COLLECTION_LITERAL__ELEMENTS;

  /**
   * The number of structural features of the '<em>Set Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_LITERAL_FEATURE_COUNT = COLLECTION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ListLiteralImpl <em>List Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ListLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getListLiteral()
   * @generated
   */
  int LIST_LITERAL = 92;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_LITERAL__TYPE = COLLECTION_LITERAL__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_LITERAL__NAME = COLLECTION_LITERAL__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_LITERAL__RIGHT = COLLECTION_LITERAL__RIGHT;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_LITERAL__ELEMENTS = COLLECTION_LITERAL__ELEMENTS;

  /**
   * The number of structural features of the '<em>List Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_LITERAL_FEATURE_COUNT = COLLECTION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl <em>Case Part</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCasePart()
   * @generated
   */
  int CASE_PART = 93;

  /**
   * The feature id for the '<em><b>Type Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CASE_PART__TYPE_GUARD = 0;

  /**
   * The feature id for the '<em><b>Case</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CASE_PART__CASE = 1;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CASE_PART__THEN = 2;

  /**
   * The number of structural features of the '<em>Case Part</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CASE_PART_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ValidIDImpl <em>Valid ID</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ValidIDImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getValidID()
   * @generated
   */
  int VALID_ID = 94;

  /**
   * The feature id for the '<em><b>Parameter Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALID_ID__PARAMETER_TYPE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALID_ID__NAME = 1;

  /**
   * The number of structural features of the '<em>Valid ID</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALID_ID_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl <em>Catch Clause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCatchClause()
   * @generated
   */
  int CATCH_CLAUSE = 95;

  /**
   * The feature id for the '<em><b>Declared Param</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CATCH_CLAUSE__DECLARED_PARAM = 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CATCH_CLAUSE__EXPRESSION = 1;

  /**
   * The number of structural features of the '<em>Catch Clause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CATCH_CLAUSE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl <em>Assignment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAssignment()
   * @generated
   */
  int ASSIGNMENT = 96;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Type For Variable Declaration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__FEATURE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__VALUE = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Assignable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__ASSIGNABLE = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Assignment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BinaryOperationImpl <em>Binary Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BinaryOperationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBinaryOperation()
   * @generated
   */
  int BINARY_OPERATION = 97;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__LEFT_OPERAND = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__FEATURE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION__RIGHT_OPERAND = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Binary Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_OPERATION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.InstanceOfExpressionImpl <em>Instance Of Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.InstanceOfExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getInstanceOfExpression()
   * @generated
   */
  int INSTANCE_OF_EXPRESSION = 98;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_OF_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_OF_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_OF_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_OF_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Instance Of Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_OF_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl <em>Unary Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getUnaryOperation()
   * @generated
   */
  int UNARY_OPERATION = 99;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION__FEATURE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operand</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION__OPERAND = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Unary Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_OPERATION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PostfixOperationImpl <em>Postfix Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.PostfixOperationImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPostfixOperation()
   * @generated
   */
  int POSTFIX_OPERATION = 100;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Operand</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION__OPERAND = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION__FEATURE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Postfix Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSTFIX_OPERATION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl <em>Member Feature Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberFeatureCall()
   * @generated
   */
  int MEMBER_FEATURE_CALL = 101;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Member Call Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Null Safe</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__NULL_SAFE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Explicit Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__EXPLICIT_STATIC = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Type Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__TYPE_ARGUMENTS = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__FEATURE = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Explicit Operation Call</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL = EXPRESSION_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Member Call Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS = EXPRESSION_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Member Feature Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_CALL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 7;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLOldExpressionImpl <em>Old Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLOldExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLOldExpression()
   * @generated
   */
  int JML_OLD_EXPRESSION = 102;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_OLD_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_OLD_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_OLD_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_OLD_EXPRESSION__EXPR = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Old Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_OLD_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLFreshExpressionImpl <em>Fresh Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLFreshExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLFreshExpression()
   * @generated
   */
  int JML_FRESH_EXPRESSION = 103;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FRESH_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FRESH_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FRESH_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FRESH_EXPRESSION__EXPR = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Fresh Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FRESH_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLResultExpressionImpl <em>Result Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLResultExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLResultExpression()
   * @generated
   */
  int JML_RESULT_EXPRESSION = 104;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_RESULT_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_RESULT_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_RESULT_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The number of structural features of the '<em>Result Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_RESULT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl <em>For All Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLForAllExpression()
   * @generated
   */
  int JML_FOR_ALL_EXPRESSION = 105;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Init Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Update Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>For All Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JML_FOR_ALL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl <em>Closure</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClosure()
   * @generated
   */
  int CLOSURE = 106;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Declared Formal Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__DECLARED_FORMAL_PARAMETERS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Explicit Syntax</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__EXPLICIT_SYNTAX = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE__EXPRESSION = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Closure</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSURE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockExpressionImpl <em>Block Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BlockExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlockExpression()
   * @generated
   */
  int BLOCK_EXPRESSION = 107;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_EXPRESSION__EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Block Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.IfExpressionImpl <em>If Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.IfExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getIfExpression()
   * @generated
   */
  int IF_EXPRESSION = 108;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>If</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__IF = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__THEN = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Else</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION__ELSE = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>If Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SwitchExpressionImpl <em>Switch Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.SwitchExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSwitchExpression()
   * @generated
   */
  int SWITCH_EXPRESSION = 109;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Declared Param</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__DECLARED_PARAM = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Switch</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__SWITCH = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Cases</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__CASES = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Default</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION__DEFAULT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Switch Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWITCH_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl <em>For Loop Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getForLoopExpression()
   * @generated
   */
  int FOR_LOOP_EXPRESSION = 110;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Declared Param</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__DECLARED_PARAM = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>For Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__FOR_EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Each Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION__EACH_EXPRESSION = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>For Loop Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_LOOP_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl <em>Basic For Loop Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBasicForLoopExpression()
   * @generated
   */
  int BASIC_FOR_LOOP_EXPRESSION = 111;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Init Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Update Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Each Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Basic For Loop Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_FOR_LOOP_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.WhileExpressionImpl <em>While Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.WhileExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getWhileExpression()
   * @generated
   */
  int WHILE_EXPRESSION = 112;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION__PREDICATE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION__BODY = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>While Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DoWhileExpressionImpl <em>Do While Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.DoWhileExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDoWhileExpression()
   * @generated
   */
  int DO_WHILE_EXPRESSION = 113;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION__BODY = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION__PREDICATE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Do While Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DO_WHILE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FeatureCallImpl <em>Feature Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.FeatureCallImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFeatureCall()
   * @generated
   */
  int FEATURE_CALL = 114;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Type Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__TYPE_ARGUMENTS = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__FEATURE = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Explicit Operation Call</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__EXPLICIT_OPERATION_CALL = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Feature Call Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL__FEATURE_CALL_ARGUMENTS = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Feature Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CALL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl <em>Constructor Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructorCall()
   * @generated
   */
  int CONSTRUCTOR_CALL = 115;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Constructor</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__CONSTRUCTOR = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Type Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__TYPE_ARGUMENTS = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Explicit Constructor Call</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Constructor Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTRUCTOR_CALL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.BooleanLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBooleanLiteral()
   * @generated
   */
  int BOOLEAN_LITERAL = 116;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Is True</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_LITERAL__IS_TRUE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Boolean Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NullLiteralImpl <em>Null Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.NullLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNullLiteral()
   * @generated
   */
  int NULL_LITERAL = 117;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The number of structural features of the '<em>Null Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NumberLiteralImpl <em>Number Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.NumberLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNumberLiteral()
   * @generated
   */
  int NUMBER_LITERAL = 118;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_LITERAL__VALUE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Number Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.StringLiteralImpl <em>String Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.StringLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getStringLiteral()
   * @generated
   */
  int STRING_LITERAL = 119;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_LITERAL__VALUE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>String Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CharLiteralImpl <em>Char Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.CharLiteralImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCharLiteral()
   * @generated
   */
  int CHAR_LITERAL = 120;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAR_LITERAL__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAR_LITERAL__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAR_LITERAL__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAR_LITERAL__VALUE = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Char Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAR_LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ThrowExpressionImpl <em>Throw Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ThrowExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getThrowExpression()
   * @generated
   */
  int THROW_EXPRESSION = 121;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int THROW_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int THROW_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int THROW_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int THROW_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Throw Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int THROW_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ReturnExpressionImpl <em>Return Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.ReturnExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getReturnExpression()
   * @generated
   */
  int RETURN_EXPRESSION = 122;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Return Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl <em>Try Catch Finally Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTryCatchFinallyExpression()
   * @generated
   */
  int TRY_CATCH_FINALLY_EXPRESSION = 123;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Catch Clauses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Finally Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Try Catch Finally Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRY_CATCH_FINALLY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SynchronizedExpressionImpl <em>Synchronized Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.impl.SynchronizedExpressionImpl
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSynchronizedExpression()
   * @generated
   */
  int SYNCHRONIZED_EXPRESSION = 124;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION__TYPE = EXPRESSION__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION__NAME = EXPRESSION__NAME;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION__RIGHT = EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Param</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION__PARAM = EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Synchronized Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNCHRONIZED_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.ModifierValue <em>Modifier Value</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.ModifierValue
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifierValue()
   * @generated
   */
  int MODIFIER_VALUE = 125;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue <em>Visibility Modifier Value</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVisibilityModifierValue()
   * @generated
   */
  int VISIBILITY_MODIFIER_VALUE = 126;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier <em>Spec Member Modifier</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecMemberModifier()
   * @generated
   */
  int JML_SPEC_MEMBER_MODIFIER = 127;

  /**
   * The meta object id for the '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveType <em>Primitive Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveType
   * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPrimitiveType()
   * @generated
   */
  int PRIMITIVE_TYPE = 128;


  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit <em>Compilation Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Compilation Unit</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CompilationUnit
   * @generated
   */
  EClass getCompilationUnit();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getPackagedeclaration <em>Packagedeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Packagedeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CompilationUnit#getPackagedeclaration()
   * @see #getCompilationUnit()
   * @generated
   */
  EReference getCompilationUnit_Packagedeclaration();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getImportdeclaration <em>Importdeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Importdeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CompilationUnit#getImportdeclaration()
   * @see #getCompilationUnit()
   * @generated
   */
  EReference getCompilationUnit_Importdeclaration();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getTypedeclaration <em>Typedeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Typedeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CompilationUnit#getTypedeclaration()
   * @see #getCompilationUnit()
   * @generated
   */
  EReference getCompilationUnit_Typedeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.PackageDeclaration <em>Package Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Package Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PackageDeclaration
   * @generated
   */
  EClass getPackageDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.PackageDeclaration#getQualifiedname <em>Qualifiedname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Qualifiedname</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PackageDeclaration#getQualifiedname()
   * @see #getPackageDeclaration()
   * @generated
   */
  EAttribute getPackageDeclaration_Qualifiedname();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration <em>Import Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ImportDeclaration
   * @generated
   */
  EClass getImportDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isStatic <em>Static</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Static</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isStatic()
   * @see #getImportDeclaration()
   * @generated
   */
  EAttribute getImportDeclaration_Static();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#getQualifiedname <em>Qualifiedname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Qualifiedname</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ImportDeclaration#getQualifiedname()
   * @see #getImportDeclaration()
   * @generated
   */
  EAttribute getImportDeclaration_Qualifiedname();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isWildcard <em>Wildcard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Wildcard</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isWildcard()
   * @see #getImportDeclaration()
   * @generated
   */
  EAttribute getImportDeclaration_Wildcard();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier <em>Classifier Declaration With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Classifier Declaration With Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
   * @generated
   */
  EClass getClassifierDeclarationWithModifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier#getClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Class Or Interface Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier#getClassOrInterfaceDeclaration()
   * @see #getClassifierDeclarationWithModifier()
   * @generated
   */
  EReference getClassifierDeclarationWithModifier_ClassOrInterfaceDeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Or Interface Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
   * @generated
   */
  EClass getClassOrInterfaceDeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Modifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Modifier
   * @generated
   */
  EClass getModifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.RegularModifier <em>Regular Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Regular Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.RegularModifier
   * @generated
   */
  EClass getRegularModifier();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.RegularModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.RegularModifier#getModifier()
   * @see #getRegularModifier()
   * @generated
   */
  EAttribute getRegularModifier_Modifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassDeclaration <em>Class Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassDeclaration
   * @generated
   */
  EClass getClassDeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration <em>Normal Class Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Normal Class Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration
   * @generated
   */
  EClass getNormalClassDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getIdentifier()
   * @see #getNormalClassDeclaration()
   * @generated
   */
  EAttribute getNormalClassDeclaration_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getTypeparameters <em>Typeparameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Typeparameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getTypeparameters()
   * @see #getNormalClassDeclaration()
   * @generated
   */
  EReference getNormalClassDeclaration_Typeparameters();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getSuperType <em>Super Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Super Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getSuperType()
   * @see #getNormalClassDeclaration()
   * @generated
   */
  EReference getNormalClassDeclaration_SuperType();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getImplementedTypes <em>Implemented Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Implemented Types</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getImplementedTypes()
   * @see #getNormalClassDeclaration()
   * @generated
   */
  EReference getNormalClassDeclaration_ImplementedTypes();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getBodyDeclarations <em>Body Declarations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Body Declarations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration#getBodyDeclarations()
   * @see #getNormalClassDeclaration()
   * @generated
   */
  EReference getNormalClassDeclaration_BodyDeclarations();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters <em>Type Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters
   * @generated
   */
  EClass getTypeParameters();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getTypeparameter <em>Typeparameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Typeparameter</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getTypeparameter()
   * @see #getTypeParameters()
   * @generated
   */
  EReference getTypeParameters_Typeparameter();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getType()
   * @see #getTypeParameters()
   * @generated
   */
  EReference getTypeParameters_Type();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getIdentifier()
   * @see #getTypeParameters()
   * @generated
   */
  EAttribute getTypeParameters_Identifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getParameters()
   * @see #getTypeParameters()
   * @generated
   */
  EReference getTypeParameters_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getExceptions <em>Exceptions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exceptions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getExceptions()
   * @see #getTypeParameters()
   * @generated
   */
  EReference getTypeParameters_Exceptions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getMethodbody <em>Methodbody</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Methodbody</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters#getMethodbody()
   * @see #getTypeParameters()
   * @generated
   */
  EReference getTypeParameters_Methodbody();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TypeParameter <em>Type Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Parameter</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameter
   * @generated
   */
  EClass getTypeParameter();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.TypeParameter#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameter#getIdentifier()
   * @see #getTypeParameter()
   * @generated
   */
  EAttribute getTypeParameter_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TypeParameter#getTypebound <em>Typebound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Typebound</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameter#getTypebound()
   * @see #getTypeParameter()
   * @generated
   */
  EReference getTypeParameter_Typebound();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TypeBound <em>Type Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Bound</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeBound
   * @generated
   */
  EClass getTypeBound();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TypeBound#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeBound#getType()
   * @see #getTypeBound()
   * @generated
   */
  EReference getTypeBound_Type();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration <em>Enum Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enum Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumDeclaration
   * @generated
   */
  EClass getEnumDeclaration();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getImplementedTypes <em>Implemented Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Implemented Types</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getImplementedTypes()
   * @see #getEnumDeclaration()
   * @generated
   */
  EReference getEnumDeclaration_ImplementedTypes();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getEnumconstants <em>Enumconstants</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Enumconstants</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getEnumconstants()
   * @see #getEnumDeclaration()
   * @generated
   */
  EReference getEnumDeclaration_Enumconstants();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getBodyDeclarations <em>Body Declarations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body Declarations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getBodyDeclarations()
   * @see #getEnumDeclaration()
   * @generated
   */
  EReference getEnumDeclaration_BodyDeclarations();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.EnumConstants <em>Enum Constants</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enum Constants</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstants
   * @generated
   */
  EClass getEnumConstants();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.EnumConstants#getEnumconstant <em>Enumconstant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Enumconstant</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstants#getEnumconstant()
   * @see #getEnumConstants()
   * @generated
   */
  EReference getEnumConstants_Enumconstant();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant <em>Enum Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enum Constant</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant
   * @generated
   */
  EClass getEnumConstant();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Annotations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant#getAnnotations()
   * @see #getEnumConstant()
   * @generated
   */
  EReference getEnumConstant_Annotations();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant#getIdentifier()
   * @see #getEnumConstant()
   * @generated
   */
  EAttribute getEnumConstant_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant#getArguments()
   * @see #getEnumConstant()
   * @generated
   */
  EReference getEnumConstant_Arguments();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getClassbodydeclaration <em>Classbodydeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Classbodydeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant#getClassbodydeclaration()
   * @see #getEnumConstant()
   * @generated
   */
  EReference getEnumConstant_Classbodydeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations <em>Enum Body Declarations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enum Body Declarations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations
   * @generated
   */
  EClass getEnumBodyDeclarations();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations#getClassbodydeclaration <em>Classbodydeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Classbodydeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations#getClassbodydeclaration()
   * @see #getEnumBodyDeclarations()
   * @generated
   */
  EReference getEnumBodyDeclarations_Classbodydeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Arguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Arguments
   * @generated
   */
  EClass getArguments();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Arguments#getExpressions <em>Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Arguments#getExpressions()
   * @see #getArguments()
   * @generated
   */
  EReference getArguments_Expressions();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration <em>Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interface Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration
   * @generated
   */
  EClass getInterfaceDeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration <em>Normal Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Normal Interface Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration
   * @generated
   */
  EClass getNormalInterfaceDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getIdentifier()
   * @see #getNormalInterfaceDeclaration()
   * @generated
   */
  EAttribute getNormalInterfaceDeclaration_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getTypeparameters <em>Typeparameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Typeparameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getTypeparameters()
   * @see #getNormalInterfaceDeclaration()
   * @generated
   */
  EReference getNormalInterfaceDeclaration_Typeparameters();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getImplementedTypes <em>Implemented Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Implemented Types</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getImplementedTypes()
   * @see #getNormalInterfaceDeclaration()
   * @generated
   */
  EReference getNormalInterfaceDeclaration_ImplementedTypes();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getBodyDeclarations <em>Body Declarations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Body Declarations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getBodyDeclarations()
   * @see #getNormalInterfaceDeclaration()
   * @generated
   */
  EReference getNormalInterfaceDeclaration_BodyDeclarations();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration <em>Class Body Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Body Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration
   * @generated
   */
  EClass getClassBodyDeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock <em>Static Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Static Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.StaticBlock
   * @generated
   */
  EClass getStaticBlock();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#isStatic <em>Static</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Static</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.StaticBlock#isStatic()
   * @see #getStaticBlock()
   * @generated
   */
  EAttribute getStaticBlock_Static();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#getBlock <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.StaticBlock#getBlock()
   * @see #getStaticBlock()
   * @generated
   */
  EReference getStaticBlock_Block();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement <em>Specified Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Specified Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
   * @generated
   */
  EClass getJMLSpecifiedElement();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getJmlTypeSpecifications <em>Jml Type Specifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Jml Type Specifications</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getJmlTypeSpecifications()
   * @see #getJMLSpecifiedElement()
   * @generated
   */
  EReference getJMLSpecifiedElement_JmlTypeSpecifications();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getJmlSpecifications <em>Jml Specifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Jml Specifications</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getJmlSpecifications()
   * @see #getJMLSpecifiedElement()
   * @generated
   */
  EReference getJMLSpecifiedElement_JmlSpecifications();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getElement <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement#getElement()
   * @see #getJMLSpecifiedElement()
   * @generated
   */
  EReference getJMLSpecifiedElement_Element();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec <em>Multiline Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Multiline Spec</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec
   * @generated
   */
  EClass getJMLMultilineSpec();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec#getModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Model Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec#getModelElement()
   * @see #getJMLMultilineSpec()
   * @generated
   */
  EReference getJMLMultilineSpec_ModelElement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec <em>Singleline Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Singleline Spec</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec
   * @generated
   */
  EClass getJMLSinglelineSpec();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving <em>Expression Having</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Having</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving
   * @generated
   */
  EClass getJMLExpressionHaving();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving#getExpr()
   * @see #getJMLExpressionHaving()
   * @generated
   */
  EReference getJMLExpressionHaving_Expr();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.VisiblityModifier <em>Visiblity Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Visiblity Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VisiblityModifier
   * @generated
   */
  EClass getVisiblityModifier();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.VisiblityModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VisiblityModifier#getModifier()
   * @see #getVisiblityModifier()
   * @generated
   */
  EAttribute getVisiblityModifier_Modifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier <em>Method Specification With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Specification With Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier
   * @generated
   */
  EClass getJMLMethodSpecificationWithModifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getModifier()
   * @see #getJMLMethodSpecificationWithModifier()
   * @generated
   */
  EReference getJMLMethodSpecificationWithModifier_Modifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getSpec <em>Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Spec</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getSpec()
   * @see #getJMLMethodSpecificationWithModifier()
   * @generated
   */
  EReference getJMLMethodSpecificationWithModifier_Spec();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierRegular <em>Method Specification With Modifier Regular</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Specification With Modifier Regular</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierRegular
   * @generated
   */
  EClass getJMLMethodSpecificationWithModifierRegular();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierExtended <em>Method Specification With Modifier Extended</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Specification With Modifier Extended</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierExtended
   * @generated
   */
  EClass getJMLMethodSpecificationWithModifierExtended();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification <em>Method Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Specification</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification
   * @generated
   */
  EClass getJMLMethodSpecification();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior <em>Method Behavior</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Behavior</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior
   * @generated
   */
  EClass getJMLMethodBehavior();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior#getSpecifications <em>Specifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Specifications</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior#getSpecifications()
   * @see #getJMLMethodBehavior()
   * @generated
   */
  EReference getJMLMethodBehavior_Specifications();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLExceptionalBehaviorBlock <em>Exceptional Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Exceptional Behavior Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLExceptionalBehaviorBlock
   * @generated
   */
  EClass getJMLExceptionalBehaviorBlock();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLNormalBehaviorBlock <em>Normal Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Normal Behavior Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLNormalBehaviorBlock
   * @generated
   */
  EClass getJMLNormalBehaviorBlock();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLBehaviorBlock <em>Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Behavior Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLBehaviorBlock
   * @generated
   */
  EClass getJMLBehaviorBlock();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodExpression <em>Method Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodExpression
   * @generated
   */
  EClass getJMLMethodExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLEnsuresExpression <em>Ensures Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ensures Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLEnsuresExpression
   * @generated
   */
  EClass getJMLEnsuresExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLRequiresExpression <em>Requires Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Requires Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLRequiresExpression
   * @generated
   */
  EClass getJMLRequiresExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier <em>Specification Only Element With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Specification Only Element With Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier
   * @generated
   */
  EClass getJMLSpecificationOnlyElementWithModifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier#getModifier()
   * @see #getJMLSpecificationOnlyElementWithModifier()
   * @generated
   */
  EReference getJMLSpecificationOnlyElementWithModifier_Modifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier#getElement <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier#getElement()
   * @see #getJMLSpecificationOnlyElementWithModifier()
   * @generated
   */
  EReference getJMLSpecificationOnlyElementWithModifier_Element();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement <em>Specification Only Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Specification Only Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement
   * @generated
   */
  EClass getJMLSpecificationOnlyElement();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#isInstance <em>Instance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Instance</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#isInstance()
   * @see #getJMLSpecificationOnlyElement()
   * @generated
   */
  EAttribute getJMLSpecificationOnlyElement_Instance();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#getElement <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#getElement()
   * @see #getJMLSpecificationOnlyElement()
   * @generated
   */
  EReference getJMLSpecificationOnlyElement_Element();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLModelElement
   * @generated
   */
  EClass getJMLModelElement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLGhostElement <em>Ghost Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ghost Element</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLGhostElement
   * @generated
   */
  EClass getJMLGhostElement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier <em>Type Expression With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Expression With Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier
   * @generated
   */
  EClass getJMLTypeExpressionWithModifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getModifier()
   * @see #getJMLTypeExpressionWithModifier()
   * @generated
   */
  EReference getJMLTypeExpressionWithModifier_Modifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getSpec <em>Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Spec</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getSpec()
   * @see #getJMLTypeExpressionWithModifier()
   * @generated
   */
  EReference getJMLTypeExpressionWithModifier_Spec();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpression <em>Type Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpression
   * @generated
   */
  EClass getJMLTypeExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression <em>Invariant Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invariant Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression
   * @generated
   */
  EClass getJMLInvariantExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLConstraintExpression <em>Constraint Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constraint Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLConstraintExpression
   * @generated
   */
  EClass getJMLConstraintExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLAxiomExpression <em>Axiom Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Axiom Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLAxiomExpression
   * @generated
   */
  EClass getJMLAxiomExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLMemberModifier <em>Member Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMemberModifier
   * @generated
   */
  EClass getJMLMemberModifier();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.JMLMemberModifier#getModifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMemberModifier#getModifier()
   * @see #getJMLMemberModifier()
   * @generated
   */
  EAttribute getJMLMemberModifier_Modifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier <em>Member Decl With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Decl With Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
   * @generated
   */
  EClass getMemberDeclWithModifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier#getJmlModifiers <em>Jml Modifiers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Jml Modifiers</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier#getJmlModifiers()
   * @see #getMemberDeclWithModifier()
   * @generated
   */
  EReference getMemberDeclWithModifier_JmlModifiers();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier#getMemberdecl <em>Memberdecl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Memberdecl</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier#getMemberdecl()
   * @see #getMemberDeclWithModifier()
   * @generated
   */
  EReference getMemberDeclWithModifier_Memberdecl();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular <em>Member Decl With Modifier Regular</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Decl With Modifier Regular</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular
   * @generated
   */
  EClass getMemberDeclWithModifierRegular();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec <em>Member Decl With Modifier Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Decl With Modifier Spec</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec
   * @generated
   */
  EClass getMemberDeclWithModifierSpec();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberDecl <em>Member Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Decl</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDecl
   * @generated
   */
  EClass getMemberDecl();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Constructor <em>Constructor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constructor</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor
   * @generated
   */
  EClass getConstructor();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor#getIdentifier()
   * @see #getConstructor()
   * @generated
   */
  EAttribute getConstructor_Identifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor#getParameters()
   * @see #getConstructor()
   * @generated
   */
  EReference getConstructor_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getExceptions <em>Exceptions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exceptions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor#getExceptions()
   * @see #getConstructor()
   * @generated
   */
  EReference getConstructor_Exceptions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getConstructorbody <em>Constructorbody</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Constructorbody</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor#getConstructorbody()
   * @see #getConstructor()
   * @generated
   */
  EReference getConstructor_Constructorbody();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration <em>Member Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclaration
   * @generated
   */
  EClass getMemberDeclaration();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getMethod <em>Method</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Method</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getMethod()
   * @see #getMemberDeclaration()
   * @generated
   */
  EReference getMemberDeclaration_Method();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Field</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getField()
   * @see #getMemberDeclaration()
   * @generated
   */
  EReference getMemberDeclaration_Field();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld <em>Generic Method Or Constructor Decl Old</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Generic Method Or Constructor Decl Old</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld
   * @generated
   */
  EClass getGenericMethodOrConstructorDeclOld();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld#getConstructor <em>Constructor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Constructor</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld#getConstructor()
   * @see #getGenericMethodOrConstructorDeclOld()
   * @generated
   */
  EReference getGenericMethodOrConstructorDeclOld_Constructor();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl <em>Generic Method Or Constructor Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Generic Method Or Constructor Decl</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
   * @generated
   */
  EClass getGenericMethodOrConstructorDecl();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getTypeParameters <em>Type Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getTypeParameters()
   * @see #getGenericMethodOrConstructorDecl()
   * @generated
   */
  EReference getGenericMethodOrConstructorDecl_TypeParameters();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getType()
   * @see #getGenericMethodOrConstructorDecl()
   * @generated
   */
  EReference getGenericMethodOrConstructorDecl_Type();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getMethod <em>Method</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Method</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getMethod()
   * @see #getGenericMethodOrConstructorDecl()
   * @generated
   */
  EReference getGenericMethodOrConstructorDecl_Method();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getConstructor <em>Constructor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Constructor</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getConstructor()
   * @see #getGenericMethodOrConstructorDecl()
   * @generated
   */
  EReference getGenericMethodOrConstructorDecl_Constructor();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration <em>Method Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MethodDeclaration
   * @generated
   */
  EClass getMethodDeclaration();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getParameters()
   * @see #getMethodDeclaration()
   * @generated
   */
  EReference getMethodDeclaration_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getExceptions <em>Exceptions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exceptions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getExceptions()
   * @see #getMethodDeclaration()
   * @generated
   */
  EReference getMethodDeclaration_Exceptions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getMethodbody <em>Methodbody</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Methodbody</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getMethodbody()
   * @see #getMethodDeclaration()
   * @generated
   */
  EReference getMethodDeclaration_Methodbody();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.FieldDeclaration <em>Field Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FieldDeclaration
   * @generated
   */
  EClass getFieldDeclaration();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.FieldDeclaration#getVariabledeclarator <em>Variabledeclarator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variabledeclarator</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FieldDeclaration#getVariabledeclarator()
   * @see #getFieldDeclaration()
   * @generated
   */
  EReference getFieldDeclaration_Variabledeclarator();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.DeclaredException <em>Declared Exception</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Declared Exception</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DeclaredException
   * @generated
   */
  EClass getDeclaredException();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.DeclaredException#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DeclaredException#getName()
   * @see #getDeclaredException()
   * @generated
   */
  EAttribute getDeclaredException_Name();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.VariableDeclarator <em>Variable Declarator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable Declarator</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VariableDeclarator
   * @generated
   */
  EClass getVariableDeclarator();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.VariableDeclarator#getBrackets <em>Brackets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Brackets</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VariableDeclarator#getBrackets()
   * @see #getVariableDeclarator()
   * @generated
   */
  EReference getVariableDeclarator_Brackets();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.VariableDeclarator#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VariableDeclarator#getExpression()
   * @see #getVariableDeclarator()
   * @generated
   */
  EReference getVariableDeclarator_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Type
   * @generated
   */
  EClass getType();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Type#getBrackets <em>Brackets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Brackets</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Type#getBrackets()
   * @see #getType()
   * @generated
   */
  EReference getType_Brackets();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets <em>Class Or Interface Type With Brackets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Or Interface Type With Brackets</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets
   * @generated
   */
  EClass getClassOrInterfaceTypeWithBrackets();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets#getType()
   * @see #getClassOrInterfaceTypeWithBrackets()
   * @generated
   */
  EReference getClassOrInterfaceTypeWithBrackets_Type();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets <em>Primitive Type With Brackets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Primitive Type With Brackets</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets
   * @generated
   */
  EClass getPrimitiveTypeWithBrackets();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets#getPrimitivetype <em>Primitivetype</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Primitivetype</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets#getPrimitivetype()
   * @see #getPrimitiveTypeWithBrackets()
   * @generated
   */
  EAttribute getPrimitiveTypeWithBrackets_Primitivetype();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Brackets <em>Brackets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Brackets</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Brackets
   * @generated
   */
  EClass getBrackets();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType <em>Class Or Interface Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Or Interface Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType
   * @generated
   */
  EClass getClassOrInterfaceType();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType#getType()
   * @see #getClassOrInterfaceType()
   * @generated
   */
  EReference getClassOrInterfaceType_Type();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ClassifierType <em>Classifier Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Classifier Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierType
   * @generated
   */
  EClass getClassifierType();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ClassifierType#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierType#getIdentifier()
   * @see #getClassifierType()
   * @generated
   */
  EAttribute getClassifierType_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ClassifierType#getTypearguments <em>Typearguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Typearguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierType#getTypearguments()
   * @see #getClassifierType()
   * @generated
   */
  EReference getClassifierType_Typearguments();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TypeArguments <em>Type Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArguments
   * @generated
   */
  EClass getTypeArguments();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TypeArguments#getTypeargument <em>Typeargument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Typeargument</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArguments#getTypeargument()
   * @see #getTypeArguments()
   * @generated
   */
  EReference getTypeArguments_Typeargument();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument <em>Type Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Argument</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument
   * @generated
   */
  EClass getTypeArgument();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument#getType()
   * @see #getTypeArgument()
   * @generated
   */
  EReference getTypeArgument_Type();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isWildcard <em>Wildcard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Wildcard</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument#isWildcard()
   * @see #getTypeArgument()
   * @generated
   */
  EAttribute getTypeArgument_Wildcard();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isExtends <em>Extends</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Extends</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument#isExtends()
   * @see #getTypeArgument()
   * @generated
   */
  EAttribute getTypeArgument_Extends();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isSuper <em>Super</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Super</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument#isSuper()
   * @see #getTypeArgument()
   * @generated
   */
  EAttribute getTypeArgument_Super();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl <em>Formal Parameter Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Formal Parameter Decl</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FormalParameterDecl
   * @generated
   */
  EClass getFormalParameterDecl();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#isVarargs <em>Varargs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Varargs</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#isVarargs()
   * @see #getFormalParameterDecl()
   * @generated
   */
  EAttribute getFormalParameterDecl_Varargs();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#getIdentifier()
   * @see #getFormalParameterDecl()
   * @generated
   */
  EAttribute getFormalParameterDecl_Identifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MethodBody <em>Method Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Body</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MethodBody
   * @generated
   */
  EClass getMethodBody();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ConstructorBody <em>Constructor Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constructor Body</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorBody
   * @generated
   */
  EClass getConstructorBody();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ConstructorBody#getBlockstatement <em>Blockstatement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Blockstatement</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorBody#getBlockstatement()
   * @see #getConstructorBody()
   * @generated
   */
  EReference getConstructorBody_Blockstatement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Modifiable <em>Modifiable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Modifiable</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Modifiable
   * @generated
   */
  EClass getModifiable();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Modifiable#getModifiers <em>Modifiers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Modifiers</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Modifiable#getModifiers()
   * @see #getModifiable()
   * @generated
   */
  EReference getModifiable_Modifiers();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Typed <em>Typed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Typed</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Typed
   * @generated
   */
  EClass getTyped();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Typed#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Typed#getType()
   * @see #getTyped()
   * @generated
   */
  EReference getTyped_Type();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Annotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotations</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotations
   * @generated
   */
  EClass getAnnotations();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Annotations#getAnnotation <em>Annotation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotation</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotations#getAnnotation()
   * @see #getAnnotations()
   * @generated
   */
  EReference getAnnotations_Annotation();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Annotation <em>Annotation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotation
   * @generated
   */
  EClass getAnnotation();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getAnnotationname <em>Annotationname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Annotationname</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotation#getAnnotationname()
   * @see #getAnnotation()
   * @generated
   */
  EAttribute getAnnotation_Annotationname();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvaluepairs <em>Elementvaluepairs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Elementvaluepairs</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotation#getElementvaluepairs()
   * @see #getAnnotation()
   * @generated
   */
  EReference getAnnotation_Elementvaluepairs();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvalue <em>Elementvalue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Elementvalue</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Annotation#getElementvalue()
   * @see #getAnnotation()
   * @generated
   */
  EReference getAnnotation_Elementvalue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePairs <em>Element Value Pairs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element Value Pairs</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePairs
   * @generated
   */
  EClass getElementValuePairs();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePairs#getElementvaluepair <em>Elementvaluepair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elementvaluepair</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePairs#getElementvaluepair()
   * @see #getElementValuePairs()
   * @generated
   */
  EReference getElementValuePairs_Elementvaluepair();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePair <em>Element Value Pair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element Value Pair</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePair
   * @generated
   */
  EClass getElementValuePair();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePair#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePair#getIdentifier()
   * @see #getElementValuePair()
   * @generated
   */
  EAttribute getElementValuePair_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePair#getElementvalue <em>Elementvalue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Elementvalue</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePair#getElementvalue()
   * @see #getElementValuePair()
   * @generated
   */
  EReference getElementValuePair_Elementvalue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ElementValue <em>Element Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValue
   * @generated
   */
  EClass getElementValue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer <em>Element Value Array Initializer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element Value Array Initializer</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer
   * @generated
   */
  EClass getElementValueArrayInitializer();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer#getElementvalue <em>Elementvalue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elementvalue</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer#getElementvalue()
   * @see #getElementValueArrayInitializer()
   * @generated
   */
  EReference getElementValueArrayInitializer_Elementvalue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration <em>Annotation Type Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Type Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration
   * @generated
   */
  EClass getAnnotationTypeDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration#getIdentifier()
   * @see #getAnnotationTypeDeclaration()
   * @generated
   */
  EAttribute getAnnotationTypeDeclaration_Identifier();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration#getAnnotationtypeelementdeclaration <em>Annotationtypeelementdeclaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotationtypeelementdeclaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration#getAnnotationtypeelementdeclaration()
   * @see #getAnnotationTypeDeclaration()
   * @generated
   */
  EReference getAnnotationTypeDeclaration_Annotationtypeelementdeclaration();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration <em>Annotation Type Element Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Type Element Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration
   * @generated
   */
  EClass getAnnotationTypeElementDeclaration();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration#getAnnotationtypeelementrest <em>Annotationtypeelementrest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Annotationtypeelementrest</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration#getAnnotationtypeelementrest()
   * @see #getAnnotationTypeElementDeclaration()
   * @generated
   */
  EReference getAnnotationTypeElementDeclaration_Annotationtypeelementrest();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest <em>Annotation Type Element Rest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Type Element Rest</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest
   * @generated
   */
  EClass getAnnotationTypeElementRest();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest <em>Annotation Method Or Constant Rest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Method Or Constant Rest</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest
   * @generated
   */
  EClass getAnnotationMethodOrConstantRest();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getMethod <em>Method</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Method</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getMethod()
   * @see #getAnnotationMethodOrConstantRest()
   * @generated
   */
  EReference getAnnotationMethodOrConstantRest_Method();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getConstant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Constant</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getConstant()
   * @see #getAnnotationMethodOrConstantRest()
   * @generated
   */
  EReference getAnnotationMethodOrConstantRest_Constant();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest <em>Annotation Method Rest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Method Rest</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest
   * @generated
   */
  EClass getAnnotationMethodRest();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest#getIdentifier()
   * @see #getAnnotationMethodRest()
   * @generated
   */
  EAttribute getAnnotationMethodRest_Identifier();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest#getDefaultvalue <em>Defaultvalue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Defaultvalue</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest#getDefaultvalue()
   * @see #getAnnotationMethodRest()
   * @generated
   */
  EReference getAnnotationMethodRest_Defaultvalue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest <em>Annotation Constant Rest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Constant Rest</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest
   * @generated
   */
  EClass getAnnotationConstantRest();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest#getVariabledeclarator <em>Variabledeclarator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variabledeclarator</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest#getVariabledeclarator()
   * @see #getAnnotationConstantRest()
   * @generated
   */
  EReference getAnnotationConstantRest_Variabledeclarator();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.DefaultValue <em>Default Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Default Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DefaultValue
   * @generated
   */
  EClass getDefaultValue();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.DefaultValue#getElementvalue <em>Elementvalue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Elementvalue</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DefaultValue#getElementvalue()
   * @see #getDefaultValue()
   * @generated
   */
  EReference getDefaultValue_Elementvalue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Block <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Block</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Block
   * @generated
   */
  EClass getBlock();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Block#getBlockstatement <em>Blockstatement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Blockstatement</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Block#getBlockstatement()
   * @see #getBlock()
   * @generated
   */
  EReference getBlock_Blockstatement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.BlockStatement <em>Block Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Block Statement</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BlockStatement
   * @generated
   */
  EClass getBlockStatement();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.IdentifierHaving <em>Identifier Having</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Identifier Having</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IdentifierHaving
   * @generated
   */
  EClass getIdentifierHaving();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.IdentifierHaving#getIdentifier <em>Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Identifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IdentifierHaving#getIdentifier()
   * @see #getIdentifierHaving()
   * @generated
   */
  EAttribute getIdentifierHaving_Identifier();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Expression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Expression
   * @generated
   */
  EClass getExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Expression#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Expression#getType()
   * @see #getExpression()
   * @generated
   */
  EReference getExpression_Type();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Expression#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Expression#getName()
   * @see #getExpression()
   * @generated
   */
  EAttribute getExpression_Name();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Expression#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Expression#getRight()
   * @see #getExpression()
   * @generated
   */
  EReference getExpression_Right();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ParenthesisExpression <em>Parenthesis Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parenthesis Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ParenthesisExpression
   * @generated
   */
  EClass getParenthesisExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ParenthesisExpression#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ParenthesisExpression#getExpr()
   * @see #getParenthesisExpression()
   * @generated
   */
  EReference getParenthesisExpression_Expr();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.CollectionLiteral <em>Collection Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Collection Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CollectionLiteral
   * @generated
   */
  EClass getCollectionLiteral();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.CollectionLiteral#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CollectionLiteral#getElements()
   * @see #getCollectionLiteral()
   * @generated
   */
  EReference getCollectionLiteral_Elements();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.SetLiteral <em>Set Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Set Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SetLiteral
   * @generated
   */
  EClass getSetLiteral();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ListLiteral <em>List Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>List Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ListLiteral
   * @generated
   */
  EClass getListLiteral();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.CasePart <em>Case Part</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Case Part</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CasePart
   * @generated
   */
  EClass getCasePart();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getTypeGuard <em>Type Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type Guard</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CasePart#getTypeGuard()
   * @see #getCasePart()
   * @generated
   */
  EReference getCasePart_TypeGuard();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getCase <em>Case</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Case</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CasePart#getCase()
   * @see #getCasePart()
   * @generated
   */
  EReference getCasePart_Case();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Then</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CasePart#getThen()
   * @see #getCasePart()
   * @generated
   */
  EReference getCasePart_Then();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ValidID <em>Valid ID</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Valid ID</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ValidID
   * @generated
   */
  EClass getValidID();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ValidID#getParameterType <em>Parameter Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parameter Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ValidID#getParameterType()
   * @see #getValidID()
   * @generated
   */
  EAttribute getValidID_ParameterType();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ValidID#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ValidID#getName()
   * @see #getValidID()
   * @generated
   */
  EAttribute getValidID_Name();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.CatchClause <em>Catch Clause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Catch Clause</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CatchClause
   * @generated
   */
  EClass getCatchClause();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getDeclaredParam <em>Declared Param</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Declared Param</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CatchClause#getDeclaredParam()
   * @see #getCatchClause()
   * @generated
   */
  EReference getCatchClause_DeclaredParam();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CatchClause#getExpression()
   * @see #getCatchClause()
   * @generated
   */
  EReference getCatchClause_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Assignment <em>Assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignment</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment
   * @generated
   */
  EClass getAssignment();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getTypeForVariableDeclaration <em>Type For Variable Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type For Variable Declaration</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment#getTypeForVariableDeclaration()
   * @see #getAssignment()
   * @generated
   */
  EAttribute getAssignment_TypeForVariableDeclaration();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment#getFeature()
   * @see #getAssignment()
   * @generated
   */
  EAttribute getAssignment_Feature();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment#getValue()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Value();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getAssignable <em>Assignable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Assignable</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment#getAssignable()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Assignable();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.BinaryOperation <em>Binary Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Binary Operation</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BinaryOperation
   * @generated
   */
  EClass getBinaryOperation();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.BinaryOperation#getLeftOperand <em>Left Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left Operand</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BinaryOperation#getLeftOperand()
   * @see #getBinaryOperation()
   * @generated
   */
  EReference getBinaryOperation_LeftOperand();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.BinaryOperation#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BinaryOperation#getFeature()
   * @see #getBinaryOperation()
   * @generated
   */
  EAttribute getBinaryOperation_Feature();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.BinaryOperation#getRightOperand <em>Right Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right Operand</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BinaryOperation#getRightOperand()
   * @see #getBinaryOperation()
   * @generated
   */
  EReference getBinaryOperation_RightOperand();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.InstanceOfExpression <em>Instance Of Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Instance Of Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.InstanceOfExpression
   * @generated
   */
  EClass getInstanceOfExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.InstanceOfExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.InstanceOfExpression#getExpression()
   * @see #getInstanceOfExpression()
   * @generated
   */
  EReference getInstanceOfExpression_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.UnaryOperation <em>Unary Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unary Operation</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.UnaryOperation
   * @generated
   */
  EClass getUnaryOperation();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.UnaryOperation#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.UnaryOperation#getFeature()
   * @see #getUnaryOperation()
   * @generated
   */
  EAttribute getUnaryOperation_Feature();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.UnaryOperation#getOperand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operand</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.UnaryOperation#getOperand()
   * @see #getUnaryOperation()
   * @generated
   */
  EReference getUnaryOperation_Operand();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.PostfixOperation <em>Postfix Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Postfix Operation</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PostfixOperation
   * @generated
   */
  EClass getPostfixOperation();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.PostfixOperation#getOperand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operand</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PostfixOperation#getOperand()
   * @see #getPostfixOperation()
   * @generated
   */
  EReference getPostfixOperation_Operand();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.PostfixOperation#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PostfixOperation#getFeature()
   * @see #getPostfixOperation()
   * @generated
   */
  EAttribute getPostfixOperation_Feature();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall <em>Member Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member Feature Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall
   * @generated
   */
  EClass getMemberFeatureCall();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallTarget <em>Member Call Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Member Call Target</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallTarget()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EReference getMemberFeatureCall_MemberCallTarget();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isNullSafe <em>Null Safe</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Null Safe</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isNullSafe()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EAttribute getMemberFeatureCall_NullSafe();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitStatic <em>Explicit Static</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Explicit Static</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitStatic()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EAttribute getMemberFeatureCall_ExplicitStatic();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getTypeArguments <em>Type Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getTypeArguments()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EReference getMemberFeatureCall_TypeArguments();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getFeature()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EAttribute getMemberFeatureCall_Feature();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Explicit Operation Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitOperationCall()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EAttribute getMemberFeatureCall_ExplicitOperationCall();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallArguments <em>Member Call Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Member Call Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallArguments()
   * @see #getMemberFeatureCall()
   * @generated
   */
  EReference getMemberFeatureCall_MemberCallArguments();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLOldExpression <em>Old Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Old Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLOldExpression
   * @generated
   */
  EClass getJMLOldExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLOldExpression#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLOldExpression#getExpr()
   * @see #getJMLOldExpression()
   * @generated
   */
  EReference getJMLOldExpression_Expr();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLFreshExpression <em>Fresh Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fresh Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLFreshExpression
   * @generated
   */
  EClass getJMLFreshExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLFreshExpression#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLFreshExpression#getExpr()
   * @see #getJMLFreshExpression()
   * @generated
   */
  EReference getJMLFreshExpression_Expr();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLResultExpression <em>Result Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Result Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLResultExpression
   * @generated
   */
  EClass getJMLResultExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.JMLForAllExpression <em>For All Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>For All Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLForAllExpression
   * @generated
   */
  EClass getJMLForAllExpression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getInitExpressions <em>Init Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Init Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getInitExpressions()
   * @see #getJMLForAllExpression()
   * @generated
   */
  EReference getJMLForAllExpression_InitExpressions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getExpression()
   * @see #getJMLForAllExpression()
   * @generated
   */
  EReference getJMLForAllExpression_Expression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getUpdateExpressions <em>Update Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Update Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLForAllExpression#getUpdateExpressions()
   * @see #getJMLForAllExpression()
   * @generated
   */
  EReference getJMLForAllExpression_UpdateExpressions();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.Closure <em>Closure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Closure</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Closure
   * @generated
   */
  EClass getClosure();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.Closure#getDeclaredFormalParameters <em>Declared Formal Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Declared Formal Parameters</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Closure#getDeclaredFormalParameters()
   * @see #getClosure()
   * @generated
   */
  EReference getClosure_DeclaredFormalParameters();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.Closure#isExplicitSyntax <em>Explicit Syntax</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Explicit Syntax</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Closure#isExplicitSyntax()
   * @see #getClosure()
   * @generated
   */
  EAttribute getClosure_ExplicitSyntax();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.Closure#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.Closure#getExpression()
   * @see #getClosure()
   * @generated
   */
  EReference getClosure_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.BlockExpression <em>Block Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Block Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BlockExpression
   * @generated
   */
  EClass getBlockExpression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.BlockExpression#getExpressions <em>Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BlockExpression#getExpressions()
   * @see #getBlockExpression()
   * @generated
   */
  EReference getBlockExpression_Expressions();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.IfExpression <em>If Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>If Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IfExpression
   * @generated
   */
  EClass getIfExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.IfExpression#getIf <em>If</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>If</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IfExpression#getIf()
   * @see #getIfExpression()
   * @generated
   */
  EReference getIfExpression_If();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.IfExpression#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Then</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IfExpression#getThen()
   * @see #getIfExpression()
   * @generated
   */
  EReference getIfExpression_Then();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.IfExpression#getElse <em>Else</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Else</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.IfExpression#getElse()
   * @see #getIfExpression()
   * @generated
   */
  EReference getIfExpression_Else();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression <em>Switch Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Switch Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression
   * @generated
   */
  EClass getSwitchExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression#getDeclaredParam <em>Declared Param</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Declared Param</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression#getDeclaredParam()
   * @see #getSwitchExpression()
   * @generated
   */
  EReference getSwitchExpression_DeclaredParam();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression#getSwitch <em>Switch</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Switch</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression#getSwitch()
   * @see #getSwitchExpression()
   * @generated
   */
  EReference getSwitchExpression_Switch();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression#getCases <em>Cases</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Cases</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression#getCases()
   * @see #getSwitchExpression()
   * @generated
   */
  EReference getSwitchExpression_Cases();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression#getDefault <em>Default</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Default</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression#getDefault()
   * @see #getSwitchExpression()
   * @generated
   */
  EReference getSwitchExpression_Default();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression <em>For Loop Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>For Loop Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ForLoopExpression
   * @generated
   */
  EClass getForLoopExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getDeclaredParam <em>Declared Param</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Declared Param</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getDeclaredParam()
   * @see #getForLoopExpression()
   * @generated
   */
  EReference getForLoopExpression_DeclaredParam();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getForExpression <em>For Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>For Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getForExpression()
   * @see #getForLoopExpression()
   * @generated
   */
  EReference getForLoopExpression_ForExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getEachExpression <em>Each Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Each Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getEachExpression()
   * @see #getForLoopExpression()
   * @generated
   */
  EReference getForLoopExpression_EachExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression <em>Basic For Loop Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Basic For Loop Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression
   * @generated
   */
  EClass getBasicForLoopExpression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getInitExpressions <em>Init Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Init Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getInitExpressions()
   * @see #getBasicForLoopExpression()
   * @generated
   */
  EReference getBasicForLoopExpression_InitExpressions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getExpression()
   * @see #getBasicForLoopExpression()
   * @generated
   */
  EReference getBasicForLoopExpression_Expression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getUpdateExpressions <em>Update Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Update Expressions</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getUpdateExpressions()
   * @see #getBasicForLoopExpression()
   * @generated
   */
  EReference getBasicForLoopExpression_UpdateExpressions();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getEachExpression <em>Each Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Each Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression#getEachExpression()
   * @see #getBasicForLoopExpression()
   * @generated
   */
  EReference getBasicForLoopExpression_EachExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.WhileExpression <em>While Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>While Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.WhileExpression
   * @generated
   */
  EClass getWhileExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.WhileExpression#getPredicate <em>Predicate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Predicate</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.WhileExpression#getPredicate()
   * @see #getWhileExpression()
   * @generated
   */
  EReference getWhileExpression_Predicate();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.WhileExpression#getBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.WhileExpression#getBody()
   * @see #getWhileExpression()
   * @generated
   */
  EReference getWhileExpression_Body();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression <em>Do While Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Do While Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DoWhileExpression
   * @generated
   */
  EClass getDoWhileExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getBody()
   * @see #getDoWhileExpression()
   * @generated
   */
  EReference getDoWhileExpression_Body();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getPredicate <em>Predicate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Predicate</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getPredicate()
   * @see #getDoWhileExpression()
   * @generated
   */
  EReference getDoWhileExpression_Predicate();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall <em>Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall
   * @generated
   */
  EClass getFeatureCall();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getTypeArguments <em>Type Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall#getTypeArguments()
   * @see #getFeatureCall()
   * @generated
   */
  EReference getFeatureCall_TypeArguments();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeature()
   * @see #getFeatureCall()
   * @generated
   */
  EAttribute getFeatureCall_Feature();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Explicit Operation Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall#isExplicitOperationCall()
   * @see #getFeatureCall()
   * @generated
   */
  EAttribute getFeatureCall_ExplicitOperationCall();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeatureCallArguments <em>Feature Call Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Feature Call Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeatureCallArguments()
   * @see #getFeatureCall()
   * @generated
   */
  EReference getFeatureCall_FeatureCallArguments();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall <em>Constructor Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constructor Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall
   * @generated
   */
  EClass getConstructorCall();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getConstructor <em>Constructor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Constructor</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall#getConstructor()
   * @see #getConstructorCall()
   * @generated
   */
  EAttribute getConstructorCall_Constructor();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getTypeArguments <em>Type Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall#getTypeArguments()
   * @see #getConstructorCall()
   * @generated
   */
  EReference getConstructorCall_TypeArguments();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#isExplicitConstructorCall <em>Explicit Constructor Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Explicit Constructor Call</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall#isExplicitConstructorCall()
   * @see #getConstructorCall()
   * @generated
   */
  EAttribute getConstructorCall_ExplicitConstructorCall();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall#getArguments()
   * @see #getConstructorCall()
   * @generated
   */
  EReference getConstructorCall_Arguments();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.BooleanLiteral <em>Boolean Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BooleanLiteral
   * @generated
   */
  EClass getBooleanLiteral();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.BooleanLiteral#isIsTrue <em>Is True</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is True</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.BooleanLiteral#isIsTrue()
   * @see #getBooleanLiteral()
   * @generated
   */
  EAttribute getBooleanLiteral_IsTrue();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.NullLiteral <em>Null Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Null Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NullLiteral
   * @generated
   */
  EClass getNullLiteral();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.NumberLiteral <em>Number Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Number Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NumberLiteral
   * @generated
   */
  EClass getNumberLiteral();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.NumberLiteral#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.NumberLiteral#getValue()
   * @see #getNumberLiteral()
   * @generated
   */
  EAttribute getNumberLiteral_Value();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.StringLiteral <em>String Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>String Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.StringLiteral
   * @generated
   */
  EClass getStringLiteral();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.StringLiteral#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.StringLiteral#getValue()
   * @see #getStringLiteral()
   * @generated
   */
  EAttribute getStringLiteral_Value();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.CharLiteral <em>Char Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Char Literal</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CharLiteral
   * @generated
   */
  EClass getCharLiteral();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruvius.domains.jml.language.jML.CharLiteral#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.CharLiteral#getValue()
   * @see #getCharLiteral()
   * @generated
   */
  EAttribute getCharLiteral_Value();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ThrowExpression <em>Throw Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Throw Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ThrowExpression
   * @generated
   */
  EClass getThrowExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ThrowExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ThrowExpression#getExpression()
   * @see #getThrowExpression()
   * @generated
   */
  EReference getThrowExpression_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.ReturnExpression <em>Return Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Return Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ReturnExpression
   * @generated
   */
  EClass getReturnExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.ReturnExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ReturnExpression#getExpression()
   * @see #getReturnExpression()
   * @generated
   */
  EReference getReturnExpression_Expression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression <em>Try Catch Finally Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Try Catch Finally Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression
   * @generated
   */
  EClass getTryCatchFinallyExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getExpression()
   * @see #getTryCatchFinallyExpression()
   * @generated
   */
  EReference getTryCatchFinallyExpression_Expression();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getCatchClauses <em>Catch Clauses</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Catch Clauses</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getCatchClauses()
   * @see #getTryCatchFinallyExpression()
   * @generated
   */
  EReference getTryCatchFinallyExpression_CatchClauses();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getFinallyExpression <em>Finally Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Finally Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getFinallyExpression()
   * @see #getTryCatchFinallyExpression()
   * @generated
   */
  EReference getTryCatchFinallyExpression_FinallyExpression();

  /**
   * Returns the meta object for class '{@link tools.vitruvius.domains.jml.language.jML.SynchronizedExpression <em>Synchronized Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Synchronized Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SynchronizedExpression
   * @generated
   */
  EClass getSynchronizedExpression();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.SynchronizedExpression#getParam <em>Param</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Param</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SynchronizedExpression#getParam()
   * @see #getSynchronizedExpression()
   * @generated
   */
  EReference getSynchronizedExpression_Param();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruvius.domains.jml.language.jML.SynchronizedExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.SynchronizedExpression#getExpression()
   * @see #getSynchronizedExpression()
   * @generated
   */
  EReference getSynchronizedExpression_Expression();

  /**
   * Returns the meta object for enum '{@link tools.vitruvius.domains.jml.language.jML.ModifierValue <em>Modifier Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Modifier Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.ModifierValue
   * @generated
   */
  EEnum getModifierValue();

  /**
   * Returns the meta object for enum '{@link tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue <em>Visibility Modifier Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Visibility Modifier Value</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue
   * @generated
   */
  EEnum getVisibilityModifierValue();

  /**
   * Returns the meta object for enum '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier <em>Spec Member Modifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Spec Member Modifier</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
   * @generated
   */
  EEnum getJMLSpecMemberModifier();

  /**
   * Returns the meta object for enum '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveType <em>Primitive Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Primitive Type</em>'.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveType
   * @generated
   */
  EEnum getPrimitiveType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  JMLFactory getJMLFactory();

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
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl <em>Compilation Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCompilationUnit()
     * @generated
     */
    EClass COMPILATION_UNIT = eINSTANCE.getCompilationUnit();

    /**
     * The meta object literal for the '<em><b>Packagedeclaration</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPILATION_UNIT__PACKAGEDECLARATION = eINSTANCE.getCompilationUnit_Packagedeclaration();

    /**
     * The meta object literal for the '<em><b>Importdeclaration</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPILATION_UNIT__IMPORTDECLARATION = eINSTANCE.getCompilationUnit_Importdeclaration();

    /**
     * The meta object literal for the '<em><b>Typedeclaration</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPILATION_UNIT__TYPEDECLARATION = eINSTANCE.getCompilationUnit_Typedeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PackageDeclarationImpl <em>Package Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.PackageDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPackageDeclaration()
     * @generated
     */
    EClass PACKAGE_DECLARATION = eINSTANCE.getPackageDeclaration();

    /**
     * The meta object literal for the '<em><b>Qualifiedname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PACKAGE_DECLARATION__QUALIFIEDNAME = eINSTANCE.getPackageDeclaration_Qualifiedname();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl <em>Import Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getImportDeclaration()
     * @generated
     */
    EClass IMPORT_DECLARATION = eINSTANCE.getImportDeclaration();

    /**
     * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_DECLARATION__STATIC = eINSTANCE.getImportDeclaration_Static();

    /**
     * The meta object literal for the '<em><b>Qualifiedname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_DECLARATION__QUALIFIEDNAME = eINSTANCE.getImportDeclaration_Qualifiedname();

    /**
     * The meta object literal for the '<em><b>Wildcard</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_DECLARATION__WILDCARD = eINSTANCE.getImportDeclaration_Wildcard();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassifierDeclarationWithModifierImpl <em>Classifier Declaration With Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassifierDeclarationWithModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassifierDeclarationWithModifier()
     * @generated
     */
    EClass CLASSIFIER_DECLARATION_WITH_MODIFIER = eINSTANCE.getClassifierDeclarationWithModifier();

    /**
     * The meta object literal for the '<em><b>Class Or Interface Declaration</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION = eINSTANCE.getClassifierDeclarationWithModifier_ClassOrInterfaceDeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceDeclarationImpl <em>Class Or Interface Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceDeclaration()
     * @generated
     */
    EClass CLASS_OR_INTERFACE_DECLARATION = eINSTANCE.getClassOrInterfaceDeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ModifierImpl <em>Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifier()
     * @generated
     */
    EClass MODIFIER = eINSTANCE.getModifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.RegularModifierImpl <em>Regular Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.RegularModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getRegularModifier()
     * @generated
     */
    EClass REGULAR_MODIFIER = eINSTANCE.getRegularModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REGULAR_MODIFIER__MODIFIER = eINSTANCE.getRegularModifier_Modifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassDeclarationImpl <em>Class Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassDeclaration()
     * @generated
     */
    EClass CLASS_DECLARATION = eINSTANCE.getClassDeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NormalClassDeclarationImpl <em>Normal Class Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.NormalClassDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNormalClassDeclaration()
     * @generated
     */
    EClass NORMAL_CLASS_DECLARATION = eINSTANCE.getNormalClassDeclaration();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NORMAL_CLASS_DECLARATION__IDENTIFIER = eINSTANCE.getNormalClassDeclaration_Identifier();

    /**
     * The meta object literal for the '<em><b>Typeparameters</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_CLASS_DECLARATION__TYPEPARAMETERS = eINSTANCE.getNormalClassDeclaration_Typeparameters();

    /**
     * The meta object literal for the '<em><b>Super Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_CLASS_DECLARATION__SUPER_TYPE = eINSTANCE.getNormalClassDeclaration_SuperType();

    /**
     * The meta object literal for the '<em><b>Implemented Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_CLASS_DECLARATION__IMPLEMENTED_TYPES = eINSTANCE.getNormalClassDeclaration_ImplementedTypes();

    /**
     * The meta object literal for the '<em><b>Body Declarations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_CLASS_DECLARATION__BODY_DECLARATIONS = eINSTANCE.getNormalClassDeclaration_BodyDeclarations();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl <em>Type Parameters</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeParameters()
     * @generated
     */
    EClass TYPE_PARAMETERS = eINSTANCE.getTypeParameters();

    /**
     * The meta object literal for the '<em><b>Typeparameter</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETERS__TYPEPARAMETER = eINSTANCE.getTypeParameters_Typeparameter();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETERS__TYPE = eINSTANCE.getTypeParameters_Type();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_PARAMETERS__IDENTIFIER = eINSTANCE.getTypeParameters_Identifier();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETERS__PARAMETERS = eINSTANCE.getTypeParameters_Parameters();

    /**
     * The meta object literal for the '<em><b>Exceptions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETERS__EXCEPTIONS = eINSTANCE.getTypeParameters_Exceptions();

    /**
     * The meta object literal for the '<em><b>Methodbody</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETERS__METHODBODY = eINSTANCE.getTypeParameters_Methodbody();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParameterImpl <em>Type Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeParameterImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeParameter()
     * @generated
     */
    EClass TYPE_PARAMETER = eINSTANCE.getTypeParameter();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_PARAMETER__IDENTIFIER = eINSTANCE.getTypeParameter_Identifier();

    /**
     * The meta object literal for the '<em><b>Typebound</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_PARAMETER__TYPEBOUND = eINSTANCE.getTypeParameter_Typebound();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeBoundImpl <em>Type Bound</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeBoundImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeBound()
     * @generated
     */
    EClass TYPE_BOUND = eINSTANCE.getTypeBound();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_BOUND__TYPE = eINSTANCE.getTypeBound_Type();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl <em>Enum Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumDeclaration()
     * @generated
     */
    EClass ENUM_DECLARATION = eINSTANCE.getEnumDeclaration();

    /**
     * The meta object literal for the '<em><b>Implemented Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_DECLARATION__IMPLEMENTED_TYPES = eINSTANCE.getEnumDeclaration_ImplementedTypes();

    /**
     * The meta object literal for the '<em><b>Enumconstants</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_DECLARATION__ENUMCONSTANTS = eINSTANCE.getEnumDeclaration_Enumconstants();

    /**
     * The meta object literal for the '<em><b>Body Declarations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_DECLARATION__BODY_DECLARATIONS = eINSTANCE.getEnumDeclaration_BodyDeclarations();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantsImpl <em>Enum Constants</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.EnumConstantsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumConstants()
     * @generated
     */
    EClass ENUM_CONSTANTS = eINSTANCE.getEnumConstants();

    /**
     * The meta object literal for the '<em><b>Enumconstant</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_CONSTANTS__ENUMCONSTANT = eINSTANCE.getEnumConstants_Enumconstant();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl <em>Enum Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumConstant()
     * @generated
     */
    EClass ENUM_CONSTANT = eINSTANCE.getEnumConstant();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_CONSTANT__ANNOTATIONS = eINSTANCE.getEnumConstant_Annotations();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENUM_CONSTANT__IDENTIFIER = eINSTANCE.getEnumConstant_Identifier();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_CONSTANT__ARGUMENTS = eINSTANCE.getEnumConstant_Arguments();

    /**
     * The meta object literal for the '<em><b>Classbodydeclaration</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_CONSTANT__CLASSBODYDECLARATION = eINSTANCE.getEnumConstant_Classbodydeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.EnumBodyDeclarationsImpl <em>Enum Body Declarations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.EnumBodyDeclarationsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getEnumBodyDeclarations()
     * @generated
     */
    EClass ENUM_BODY_DECLARATIONS = eINSTANCE.getEnumBodyDeclarations();

    /**
     * The meta object literal for the '<em><b>Classbodydeclaration</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION = eINSTANCE.getEnumBodyDeclarations_Classbodydeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ArgumentsImpl <em>Arguments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ArgumentsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getArguments()
     * @generated
     */
    EClass ARGUMENTS = eINSTANCE.getArguments();

    /**
     * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARGUMENTS__EXPRESSIONS = eINSTANCE.getArguments_Expressions();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.InterfaceDeclarationImpl <em>Interface Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.InterfaceDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getInterfaceDeclaration()
     * @generated
     */
    EClass INTERFACE_DECLARATION = eINSTANCE.getInterfaceDeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl <em>Normal Interface Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNormalInterfaceDeclaration()
     * @generated
     */
    EClass NORMAL_INTERFACE_DECLARATION = eINSTANCE.getNormalInterfaceDeclaration();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NORMAL_INTERFACE_DECLARATION__IDENTIFIER = eINSTANCE.getNormalInterfaceDeclaration_Identifier();

    /**
     * The meta object literal for the '<em><b>Typeparameters</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS = eINSTANCE.getNormalInterfaceDeclaration_Typeparameters();

    /**
     * The meta object literal for the '<em><b>Implemented Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES = eINSTANCE.getNormalInterfaceDeclaration_ImplementedTypes();

    /**
     * The meta object literal for the '<em><b>Body Declarations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS = eINSTANCE.getNormalInterfaceDeclaration_BodyDeclarations();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassBodyDeclarationImpl <em>Class Body Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassBodyDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassBodyDeclaration()
     * @generated
     */
    EClass CLASS_BODY_DECLARATION = eINSTANCE.getClassBodyDeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.StaticBlockImpl <em>Static Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.StaticBlockImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getStaticBlock()
     * @generated
     */
    EClass STATIC_BLOCK = eINSTANCE.getStaticBlock();

    /**
     * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATIC_BLOCK__STATIC = eINSTANCE.getStaticBlock_Static();

    /**
     * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATIC_BLOCK__BLOCK = eINSTANCE.getStaticBlock_Block();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl <em>Specified Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecifiedElement()
     * @generated
     */
    EClass JML_SPECIFIED_ELEMENT = eINSTANCE.getJMLSpecifiedElement();

    /**
     * The meta object literal for the '<em><b>Jml Type Specifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS = eINSTANCE.getJMLSpecifiedElement_JmlTypeSpecifications();

    /**
     * The meta object literal for the '<em><b>Jml Specifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS = eINSTANCE.getJMLSpecifiedElement_JmlSpecifications();

    /**
     * The meta object literal for the '<em><b>Element</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFIED_ELEMENT__ELEMENT = eINSTANCE.getJMLSpecifiedElement_Element();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMultilineSpecImpl <em>Multiline Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMultilineSpecImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMultilineSpec()
     * @generated
     */
    EClass JML_MULTILINE_SPEC = eINSTANCE.getJMLMultilineSpec();

    /**
     * The meta object literal for the '<em><b>Model Element</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_MULTILINE_SPEC__MODEL_ELEMENT = eINSTANCE.getJMLMultilineSpec_ModelElement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSinglelineSpecImpl <em>Singleline Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSinglelineSpecImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSinglelineSpec()
     * @generated
     */
    EClass JML_SINGLELINE_SPEC = eINSTANCE.getJMLSinglelineSpec();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLExpressionHavingImpl <em>Expression Having</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLExpressionHavingImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLExpressionHaving()
     * @generated
     */
    EClass JML_EXPRESSION_HAVING = eINSTANCE.getJMLExpressionHaving();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_EXPRESSION_HAVING__EXPR = eINSTANCE.getJMLExpressionHaving_Expr();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.VisiblityModifierImpl <em>Visiblity Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.VisiblityModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVisiblityModifier()
     * @generated
     */
    EClass VISIBLITY_MODIFIER = eINSTANCE.getVisiblityModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VISIBLITY_MODIFIER__MODIFIER = eINSTANCE.getVisiblityModifier_Modifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl <em>Method Specification With Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifier()
     * @generated
     */
    EClass JML_METHOD_SPECIFICATION_WITH_MODIFIER = eINSTANCE.getJMLMethodSpecificationWithModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER = eINSTANCE.getJMLMethodSpecificationWithModifier_Modifier();

    /**
     * The meta object literal for the '<em><b>Spec</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC = eINSTANCE.getJMLMethodSpecificationWithModifier_Spec();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierRegularImpl <em>Method Specification With Modifier Regular</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierRegularImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifierRegular()
     * @generated
     */
    EClass JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR = eINSTANCE.getJMLMethodSpecificationWithModifierRegular();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierExtendedImpl <em>Method Specification With Modifier Extended</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierExtendedImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecificationWithModifierExtended()
     * @generated
     */
    EClass JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED = eINSTANCE.getJMLMethodSpecificationWithModifierExtended();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationImpl <em>Method Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodSpecification()
     * @generated
     */
    EClass JML_METHOD_SPECIFICATION = eINSTANCE.getJMLMethodSpecification();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodBehaviorImpl <em>Method Behavior</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodBehaviorImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodBehavior()
     * @generated
     */
    EClass JML_METHOD_BEHAVIOR = eINSTANCE.getJMLMethodBehavior();

    /**
     * The meta object literal for the '<em><b>Specifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_METHOD_BEHAVIOR__SPECIFICATIONS = eINSTANCE.getJMLMethodBehavior_Specifications();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLExceptionalBehaviorBlockImpl <em>Exceptional Behavior Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLExceptionalBehaviorBlockImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLExceptionalBehaviorBlock()
     * @generated
     */
    EClass JML_EXCEPTIONAL_BEHAVIOR_BLOCK = eINSTANCE.getJMLExceptionalBehaviorBlock();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLNormalBehaviorBlockImpl <em>Normal Behavior Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLNormalBehaviorBlockImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLNormalBehaviorBlock()
     * @generated
     */
    EClass JML_NORMAL_BEHAVIOR_BLOCK = eINSTANCE.getJMLNormalBehaviorBlock();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLBehaviorBlockImpl <em>Behavior Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLBehaviorBlockImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLBehaviorBlock()
     * @generated
     */
    EClass JML_BEHAVIOR_BLOCK = eINSTANCE.getJMLBehaviorBlock();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodExpressionImpl <em>Method Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMethodExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMethodExpression()
     * @generated
     */
    EClass JML_METHOD_EXPRESSION = eINSTANCE.getJMLMethodExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLEnsuresExpressionImpl <em>Ensures Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLEnsuresExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLEnsuresExpression()
     * @generated
     */
    EClass JML_ENSURES_EXPRESSION = eINSTANCE.getJMLEnsuresExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLRequiresExpressionImpl <em>Requires Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLRequiresExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLRequiresExpression()
     * @generated
     */
    EClass JML_REQUIRES_EXPRESSION = eINSTANCE.getJMLRequiresExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl <em>Specification Only Element With Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecificationOnlyElementWithModifier()
     * @generated
     */
    EClass JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER = eINSTANCE.getJMLSpecificationOnlyElementWithModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER = eINSTANCE.getJMLSpecificationOnlyElementWithModifier_Modifier();

    /**
     * The meta object literal for the '<em><b>Element</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT = eINSTANCE.getJMLSpecificationOnlyElementWithModifier_Element();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl <em>Specification Only Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecificationOnlyElement()
     * @generated
     */
    EClass JML_SPECIFICATION_ONLY_ELEMENT = eINSTANCE.getJMLSpecificationOnlyElement();

    /**
     * The meta object literal for the '<em><b>Instance</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE = eINSTANCE.getJMLSpecificationOnlyElement_Instance();

    /**
     * The meta object literal for the '<em><b>Element</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT = eINSTANCE.getJMLSpecificationOnlyElement_Element();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLModelElementImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLModelElement()
     * @generated
     */
    EClass JML_MODEL_ELEMENT = eINSTANCE.getJMLModelElement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLGhostElementImpl <em>Ghost Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLGhostElementImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLGhostElement()
     * @generated
     */
    EClass JML_GHOST_ELEMENT = eINSTANCE.getJMLGhostElement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionWithModifierImpl <em>Type Expression With Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionWithModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLTypeExpressionWithModifier()
     * @generated
     */
    EClass JML_TYPE_EXPRESSION_WITH_MODIFIER = eINSTANCE.getJMLTypeExpressionWithModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_TYPE_EXPRESSION_WITH_MODIFIER__MODIFIER = eINSTANCE.getJMLTypeExpressionWithModifier_Modifier();

    /**
     * The meta object literal for the '<em><b>Spec</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_TYPE_EXPRESSION_WITH_MODIFIER__SPEC = eINSTANCE.getJMLTypeExpressionWithModifier_Spec();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionImpl <em>Type Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLTypeExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLTypeExpression()
     * @generated
     */
    EClass JML_TYPE_EXPRESSION = eINSTANCE.getJMLTypeExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLInvariantExpressionImpl <em>Invariant Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLInvariantExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLInvariantExpression()
     * @generated
     */
    EClass JML_INVARIANT_EXPRESSION = eINSTANCE.getJMLInvariantExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLConstraintExpressionImpl <em>Constraint Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLConstraintExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLConstraintExpression()
     * @generated
     */
    EClass JML_CONSTRAINT_EXPRESSION = eINSTANCE.getJMLConstraintExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLAxiomExpressionImpl <em>Axiom Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLAxiomExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLAxiomExpression()
     * @generated
     */
    EClass JML_AXIOM_EXPRESSION = eINSTANCE.getJMLAxiomExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMemberModifierImpl <em>Member Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLMemberModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLMemberModifier()
     * @generated
     */
    EClass JML_MEMBER_MODIFIER = eINSTANCE.getJMLMemberModifier();

    /**
     * The meta object literal for the '<em><b>Modifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JML_MEMBER_MODIFIER__MODIFIER = eINSTANCE.getJMLMemberModifier_Modifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl <em>Member Decl With Modifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifier()
     * @generated
     */
    EClass MEMBER_DECL_WITH_MODIFIER = eINSTANCE.getMemberDeclWithModifier();

    /**
     * The meta object literal for the '<em><b>Jml Modifiers</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS = eINSTANCE.getMemberDeclWithModifier_JmlModifiers();

    /**
     * The meta object literal for the '<em><b>Memberdecl</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_DECL_WITH_MODIFIER__MEMBERDECL = eINSTANCE.getMemberDeclWithModifier_Memberdecl();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierRegularImpl <em>Member Decl With Modifier Regular</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierRegularImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifierRegular()
     * @generated
     */
    EClass MEMBER_DECL_WITH_MODIFIER_REGULAR = eINSTANCE.getMemberDeclWithModifierRegular();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierSpecImpl <em>Member Decl With Modifier Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierSpecImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclWithModifierSpec()
     * @generated
     */
    EClass MEMBER_DECL_WITH_MODIFIER_SPEC = eINSTANCE.getMemberDeclWithModifierSpec();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclImpl <em>Member Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDecl()
     * @generated
     */
    EClass MEMBER_DECL = eINSTANCE.getMemberDecl();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl <em>Constructor</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructor()
     * @generated
     */
    EClass CONSTRUCTOR = eINSTANCE.getConstructor();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTRUCTOR__IDENTIFIER = eINSTANCE.getConstructor_Identifier();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR__PARAMETERS = eINSTANCE.getConstructor_Parameters();

    /**
     * The meta object literal for the '<em><b>Exceptions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR__EXCEPTIONS = eINSTANCE.getConstructor_Exceptions();

    /**
     * The meta object literal for the '<em><b>Constructorbody</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR__CONSTRUCTORBODY = eINSTANCE.getConstructor_Constructorbody();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl <em>Member Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberDeclaration()
     * @generated
     */
    EClass MEMBER_DECLARATION = eINSTANCE.getMemberDeclaration();

    /**
     * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_DECLARATION__METHOD = eINSTANCE.getMemberDeclaration_Method();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_DECLARATION__FIELD = eINSTANCE.getMemberDeclaration_Field();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclOldImpl <em>Generic Method Or Constructor Decl Old</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclOldImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getGenericMethodOrConstructorDeclOld()
     * @generated
     */
    EClass GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD = eINSTANCE.getGenericMethodOrConstructorDeclOld();

    /**
     * The meta object literal for the '<em><b>Constructor</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR = eINSTANCE.getGenericMethodOrConstructorDeclOld_Constructor();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl <em>Generic Method Or Constructor Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getGenericMethodOrConstructorDecl()
     * @generated
     */
    EClass GENERIC_METHOD_OR_CONSTRUCTOR_DECL = eINSTANCE.getGenericMethodOrConstructorDecl();

    /**
     * The meta object literal for the '<em><b>Type Parameters</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS = eINSTANCE.getGenericMethodOrConstructorDecl_TypeParameters();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE = eINSTANCE.getGenericMethodOrConstructorDecl_Type();

    /**
     * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD = eINSTANCE.getGenericMethodOrConstructorDecl_Method();

    /**
     * The meta object literal for the '<em><b>Constructor</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR = eINSTANCE.getGenericMethodOrConstructorDecl_Constructor();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl <em>Method Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMethodDeclaration()
     * @generated
     */
    EClass METHOD_DECLARATION = eINSTANCE.getMethodDeclaration();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METHOD_DECLARATION__PARAMETERS = eINSTANCE.getMethodDeclaration_Parameters();

    /**
     * The meta object literal for the '<em><b>Exceptions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METHOD_DECLARATION__EXCEPTIONS = eINSTANCE.getMethodDeclaration_Exceptions();

    /**
     * The meta object literal for the '<em><b>Methodbody</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METHOD_DECLARATION__METHODBODY = eINSTANCE.getMethodDeclaration_Methodbody();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FieldDeclarationImpl <em>Field Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.FieldDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFieldDeclaration()
     * @generated
     */
    EClass FIELD_DECLARATION = eINSTANCE.getFieldDeclaration();

    /**
     * The meta object literal for the '<em><b>Variabledeclarator</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD_DECLARATION__VARIABLEDECLARATOR = eINSTANCE.getFieldDeclaration_Variabledeclarator();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DeclaredExceptionImpl <em>Declared Exception</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.DeclaredExceptionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDeclaredException()
     * @generated
     */
    EClass DECLARED_EXCEPTION = eINSTANCE.getDeclaredException();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECLARED_EXCEPTION__NAME = eINSTANCE.getDeclaredException_Name();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.VariableDeclaratorImpl <em>Variable Declarator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.VariableDeclaratorImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVariableDeclarator()
     * @generated
     */
    EClass VARIABLE_DECLARATOR = eINSTANCE.getVariableDeclarator();

    /**
     * The meta object literal for the '<em><b>Brackets</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_DECLARATOR__BRACKETS = eINSTANCE.getVariableDeclarator_Brackets();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_DECLARATOR__EXPRESSION = eINSTANCE.getVariableDeclarator_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getType()
     * @generated
     */
    EClass TYPE = eINSTANCE.getType();

    /**
     * The meta object literal for the '<em><b>Brackets</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE__BRACKETS = eINSTANCE.getType_Brackets();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeWithBracketsImpl <em>Class Or Interface Type With Brackets</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeWithBracketsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceTypeWithBrackets()
     * @generated
     */
    EClass CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS = eINSTANCE.getClassOrInterfaceTypeWithBrackets();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS__TYPE = eINSTANCE.getClassOrInterfaceTypeWithBrackets_Type();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl <em>Primitive Type With Brackets</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPrimitiveTypeWithBrackets()
     * @generated
     */
    EClass PRIMITIVE_TYPE_WITH_BRACKETS = eINSTANCE.getPrimitiveTypeWithBrackets();

    /**
     * The meta object literal for the '<em><b>Primitivetype</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE = eINSTANCE.getPrimitiveTypeWithBrackets_Primitivetype();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BracketsImpl <em>Brackets</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BracketsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBrackets()
     * @generated
     */
    EClass BRACKETS = eINSTANCE.getBrackets();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeImpl <em>Class Or Interface Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassOrInterfaceTypeImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassOrInterfaceType()
     * @generated
     */
    EClass CLASS_OR_INTERFACE_TYPE = eINSTANCE.getClassOrInterfaceType();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLASS_OR_INTERFACE_TYPE__TYPE = eINSTANCE.getClassOrInterfaceType_Type();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClassifierTypeImpl <em>Classifier Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClassifierTypeImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClassifierType()
     * @generated
     */
    EClass CLASSIFIER_TYPE = eINSTANCE.getClassifierType();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLASSIFIER_TYPE__IDENTIFIER = eINSTANCE.getClassifierType_Identifier();

    /**
     * The meta object literal for the '<em><b>Typearguments</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLASSIFIER_TYPE__TYPEARGUMENTS = eINSTANCE.getClassifierType_Typearguments();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentsImpl <em>Type Arguments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeArguments()
     * @generated
     */
    EClass TYPE_ARGUMENTS = eINSTANCE.getTypeArguments();

    /**
     * The meta object literal for the '<em><b>Typeargument</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_ARGUMENTS__TYPEARGUMENT = eINSTANCE.getTypeArguments_Typeargument();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTypeArgument()
     * @generated
     */
    EClass TYPE_ARGUMENT = eINSTANCE.getTypeArgument();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_ARGUMENT__TYPE = eINSTANCE.getTypeArgument_Type();

    /**
     * The meta object literal for the '<em><b>Wildcard</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_ARGUMENT__WILDCARD = eINSTANCE.getTypeArgument_Wildcard();

    /**
     * The meta object literal for the '<em><b>Extends</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_ARGUMENT__EXTENDS = eINSTANCE.getTypeArgument_Extends();

    /**
     * The meta object literal for the '<em><b>Super</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_ARGUMENT__SUPER = eINSTANCE.getTypeArgument_Super();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl <em>Formal Parameter Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFormalParameterDecl()
     * @generated
     */
    EClass FORMAL_PARAMETER_DECL = eINSTANCE.getFormalParameterDecl();

    /**
     * The meta object literal for the '<em><b>Varargs</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAL_PARAMETER_DECL__VARARGS = eINSTANCE.getFormalParameterDecl_Varargs();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAL_PARAMETER_DECL__IDENTIFIER = eINSTANCE.getFormalParameterDecl_Identifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MethodBodyImpl <em>Method Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MethodBodyImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMethodBody()
     * @generated
     */
    EClass METHOD_BODY = eINSTANCE.getMethodBody();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorBodyImpl <em>Constructor Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorBodyImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructorBody()
     * @generated
     */
    EClass CONSTRUCTOR_BODY = eINSTANCE.getConstructorBody();

    /**
     * The meta object literal for the '<em><b>Blockstatement</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR_BODY__BLOCKSTATEMENT = eINSTANCE.getConstructorBody_Blockstatement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ModifiableImpl <em>Modifiable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ModifiableImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifiable()
     * @generated
     */
    EClass MODIFIABLE = eINSTANCE.getModifiable();

    /**
     * The meta object literal for the '<em><b>Modifiers</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODIFIABLE__MODIFIERS = eINSTANCE.getModifiable_Modifiers();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TypedImpl <em>Typed</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TypedImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTyped()
     * @generated
     */
    EClass TYPED = eINSTANCE.getTyped();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPED__TYPE = eINSTANCE.getTyped_Type();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationsImpl <em>Annotations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotations()
     * @generated
     */
    EClass ANNOTATIONS = eINSTANCE.getAnnotations();

    /**
     * The meta object literal for the '<em><b>Annotation</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATIONS__ANNOTATION = eINSTANCE.getAnnotations_Annotation();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl <em>Annotation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotation()
     * @generated
     */
    EClass ANNOTATION = eINSTANCE.getAnnotation();

    /**
     * The meta object literal for the '<em><b>Annotationname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNOTATION__ANNOTATIONNAME = eINSTANCE.getAnnotation_Annotationname();

    /**
     * The meta object literal for the '<em><b>Elementvaluepairs</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION__ELEMENTVALUEPAIRS = eINSTANCE.getAnnotation_Elementvaluepairs();

    /**
     * The meta object literal for the '<em><b>Elementvalue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION__ELEMENTVALUE = eINSTANCE.getAnnotation_Elementvalue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairsImpl <em>Element Value Pairs</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairsImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValuePairs()
     * @generated
     */
    EClass ELEMENT_VALUE_PAIRS = eINSTANCE.getElementValuePairs();

    /**
     * The meta object literal for the '<em><b>Elementvaluepair</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR = eINSTANCE.getElementValuePairs_Elementvaluepair();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairImpl <em>Element Value Pair</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValuePair()
     * @generated
     */
    EClass ELEMENT_VALUE_PAIR = eINSTANCE.getElementValuePair();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT_VALUE_PAIR__IDENTIFIER = eINSTANCE.getElementValuePair_Identifier();

    /**
     * The meta object literal for the '<em><b>Elementvalue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT_VALUE_PAIR__ELEMENTVALUE = eINSTANCE.getElementValuePair_Elementvalue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValueImpl <em>Element Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValueImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValue()
     * @generated
     */
    EClass ELEMENT_VALUE = eINSTANCE.getElementValue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValueArrayInitializerImpl <em>Element Value Array Initializer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ElementValueArrayInitializerImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getElementValueArrayInitializer()
     * @generated
     */
    EClass ELEMENT_VALUE_ARRAY_INITIALIZER = eINSTANCE.getElementValueArrayInitializer();

    /**
     * The meta object literal for the '<em><b>Elementvalue</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE = eINSTANCE.getElementValueArrayInitializer_Elementvalue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl <em>Annotation Type Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeDeclaration()
     * @generated
     */
    EClass ANNOTATION_TYPE_DECLARATION = eINSTANCE.getAnnotationTypeDeclaration();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNOTATION_TYPE_DECLARATION__IDENTIFIER = eINSTANCE.getAnnotationTypeDeclaration_Identifier();

    /**
     * The meta object literal for the '<em><b>Annotationtypeelementdeclaration</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION = eINSTANCE.getAnnotationTypeDeclaration_Annotationtypeelementdeclaration();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementDeclarationImpl <em>Annotation Type Element Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementDeclarationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeElementDeclaration()
     * @generated
     */
    EClass ANNOTATION_TYPE_ELEMENT_DECLARATION = eINSTANCE.getAnnotationTypeElementDeclaration();

    /**
     * The meta object literal for the '<em><b>Annotationtypeelementrest</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST = eINSTANCE.getAnnotationTypeElementDeclaration_Annotationtypeelementrest();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementRestImpl <em>Annotation Type Element Rest</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementRestImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationTypeElementRest()
     * @generated
     */
    EClass ANNOTATION_TYPE_ELEMENT_REST = eINSTANCE.getAnnotationTypeElementRest();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl <em>Annotation Method Or Constant Rest</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationMethodOrConstantRest()
     * @generated
     */
    EClass ANNOTATION_METHOD_OR_CONSTANT_REST = eINSTANCE.getAnnotationMethodOrConstantRest();

    /**
     * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD = eINSTANCE.getAnnotationMethodOrConstantRest_Method();

    /**
     * The meta object literal for the '<em><b>Constant</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT = eINSTANCE.getAnnotationMethodOrConstantRest_Constant();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl <em>Annotation Method Rest</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationMethodRest()
     * @generated
     */
    EClass ANNOTATION_METHOD_REST = eINSTANCE.getAnnotationMethodRest();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNOTATION_METHOD_REST__IDENTIFIER = eINSTANCE.getAnnotationMethodRest_Identifier();

    /**
     * The meta object literal for the '<em><b>Defaultvalue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_METHOD_REST__DEFAULTVALUE = eINSTANCE.getAnnotationMethodRest_Defaultvalue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationConstantRestImpl <em>Annotation Constant Rest</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AnnotationConstantRestImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAnnotationConstantRest()
     * @generated
     */
    EClass ANNOTATION_CONSTANT_REST = eINSTANCE.getAnnotationConstantRest();

    /**
     * The meta object literal for the '<em><b>Variabledeclarator</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNOTATION_CONSTANT_REST__VARIABLEDECLARATOR = eINSTANCE.getAnnotationConstantRest_Variabledeclarator();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DefaultValueImpl <em>Default Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.DefaultValueImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDefaultValue()
     * @generated
     */
    EClass DEFAULT_VALUE = eINSTANCE.getDefaultValue();

    /**
     * The meta object literal for the '<em><b>Elementvalue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DEFAULT_VALUE__ELEMENTVALUE = eINSTANCE.getDefaultValue_Elementvalue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockImpl <em>Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BlockImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlock()
     * @generated
     */
    EClass BLOCK = eINSTANCE.getBlock();

    /**
     * The meta object literal for the '<em><b>Blockstatement</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BLOCK__BLOCKSTATEMENT = eINSTANCE.getBlock_Blockstatement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockStatementImpl <em>Block Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BlockStatementImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlockStatement()
     * @generated
     */
    EClass BLOCK_STATEMENT = eINSTANCE.getBlockStatement();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.IdentifierHavingImpl <em>Identifier Having</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.IdentifierHavingImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getIdentifierHaving()
     * @generated
     */
    EClass IDENTIFIER_HAVING = eINSTANCE.getIdentifierHaving();

    /**
     * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IDENTIFIER_HAVING__IDENTIFIER = eINSTANCE.getIdentifierHaving_Identifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ExpressionImpl <em>Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getExpression()
     * @generated
     */
    EClass EXPRESSION = eINSTANCE.getExpression();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__TYPE = eINSTANCE.getExpression_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION__NAME = eINSTANCE.getExpression_Name();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__RIGHT = eINSTANCE.getExpression_Right();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ParenthesisExpressionImpl <em>Parenthesis Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ParenthesisExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getParenthesisExpression()
     * @generated
     */
    EClass PARENTHESIS_EXPRESSION = eINSTANCE.getParenthesisExpression();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARENTHESIS_EXPRESSION__EXPR = eINSTANCE.getParenthesisExpression_Expr();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CollectionLiteralImpl <em>Collection Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.CollectionLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCollectionLiteral()
     * @generated
     */
    EClass COLLECTION_LITERAL = eINSTANCE.getCollectionLiteral();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COLLECTION_LITERAL__ELEMENTS = eINSTANCE.getCollectionLiteral_Elements();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SetLiteralImpl <em>Set Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.SetLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSetLiteral()
     * @generated
     */
    EClass SET_LITERAL = eINSTANCE.getSetLiteral();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ListLiteralImpl <em>List Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ListLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getListLiteral()
     * @generated
     */
    EClass LIST_LITERAL = eINSTANCE.getListLiteral();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl <em>Case Part</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCasePart()
     * @generated
     */
    EClass CASE_PART = eINSTANCE.getCasePart();

    /**
     * The meta object literal for the '<em><b>Type Guard</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CASE_PART__TYPE_GUARD = eINSTANCE.getCasePart_TypeGuard();

    /**
     * The meta object literal for the '<em><b>Case</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CASE_PART__CASE = eINSTANCE.getCasePart_Case();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CASE_PART__THEN = eINSTANCE.getCasePart_Then();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ValidIDImpl <em>Valid ID</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ValidIDImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getValidID()
     * @generated
     */
    EClass VALID_ID = eINSTANCE.getValidID();

    /**
     * The meta object literal for the '<em><b>Parameter Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALID_ID__PARAMETER_TYPE = eINSTANCE.getValidID_ParameterType();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALID_ID__NAME = eINSTANCE.getValidID_Name();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl <em>Catch Clause</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCatchClause()
     * @generated
     */
    EClass CATCH_CLAUSE = eINSTANCE.getCatchClause();

    /**
     * The meta object literal for the '<em><b>Declared Param</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CATCH_CLAUSE__DECLARED_PARAM = eINSTANCE.getCatchClause_DeclaredParam();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CATCH_CLAUSE__EXPRESSION = eINSTANCE.getCatchClause_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl <em>Assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getAssignment()
     * @generated
     */
    EClass ASSIGNMENT = eINSTANCE.getAssignment();

    /**
     * The meta object literal for the '<em><b>Type For Variable Declaration</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION = eINSTANCE.getAssignment_TypeForVariableDeclaration();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSIGNMENT__FEATURE = eINSTANCE.getAssignment_Feature();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__VALUE = eINSTANCE.getAssignment_Value();

    /**
     * The meta object literal for the '<em><b>Assignable</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__ASSIGNABLE = eINSTANCE.getAssignment_Assignable();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BinaryOperationImpl <em>Binary Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BinaryOperationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBinaryOperation()
     * @generated
     */
    EClass BINARY_OPERATION = eINSTANCE.getBinaryOperation();

    /**
     * The meta object literal for the '<em><b>Left Operand</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BINARY_OPERATION__LEFT_OPERAND = eINSTANCE.getBinaryOperation_LeftOperand();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BINARY_OPERATION__FEATURE = eINSTANCE.getBinaryOperation_Feature();

    /**
     * The meta object literal for the '<em><b>Right Operand</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BINARY_OPERATION__RIGHT_OPERAND = eINSTANCE.getBinaryOperation_RightOperand();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.InstanceOfExpressionImpl <em>Instance Of Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.InstanceOfExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getInstanceOfExpression()
     * @generated
     */
    EClass INSTANCE_OF_EXPRESSION = eINSTANCE.getInstanceOfExpression();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INSTANCE_OF_EXPRESSION__EXPRESSION = eINSTANCE.getInstanceOfExpression_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl <em>Unary Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getUnaryOperation()
     * @generated
     */
    EClass UNARY_OPERATION = eINSTANCE.getUnaryOperation();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNARY_OPERATION__FEATURE = eINSTANCE.getUnaryOperation_Feature();

    /**
     * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNARY_OPERATION__OPERAND = eINSTANCE.getUnaryOperation_Operand();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.PostfixOperationImpl <em>Postfix Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.PostfixOperationImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPostfixOperation()
     * @generated
     */
    EClass POSTFIX_OPERATION = eINSTANCE.getPostfixOperation();

    /**
     * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference POSTFIX_OPERATION__OPERAND = eINSTANCE.getPostfixOperation_Operand();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute POSTFIX_OPERATION__FEATURE = eINSTANCE.getPostfixOperation_Feature();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl <em>Member Feature Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getMemberFeatureCall()
     * @generated
     */
    EClass MEMBER_FEATURE_CALL = eINSTANCE.getMemberFeatureCall();

    /**
     * The meta object literal for the '<em><b>Member Call Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET = eINSTANCE.getMemberFeatureCall_MemberCallTarget();

    /**
     * The meta object literal for the '<em><b>Null Safe</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMBER_FEATURE_CALL__NULL_SAFE = eINSTANCE.getMemberFeatureCall_NullSafe();

    /**
     * The meta object literal for the '<em><b>Explicit Static</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMBER_FEATURE_CALL__EXPLICIT_STATIC = eINSTANCE.getMemberFeatureCall_ExplicitStatic();

    /**
     * The meta object literal for the '<em><b>Type Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_FEATURE_CALL__TYPE_ARGUMENTS = eINSTANCE.getMemberFeatureCall_TypeArguments();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMBER_FEATURE_CALL__FEATURE = eINSTANCE.getMemberFeatureCall_Feature();

    /**
     * The meta object literal for the '<em><b>Explicit Operation Call</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL = eINSTANCE.getMemberFeatureCall_ExplicitOperationCall();

    /**
     * The meta object literal for the '<em><b>Member Call Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS = eINSTANCE.getMemberFeatureCall_MemberCallArguments();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLOldExpressionImpl <em>Old Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLOldExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLOldExpression()
     * @generated
     */
    EClass JML_OLD_EXPRESSION = eINSTANCE.getJMLOldExpression();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_OLD_EXPRESSION__EXPR = eINSTANCE.getJMLOldExpression_Expr();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLFreshExpressionImpl <em>Fresh Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLFreshExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLFreshExpression()
     * @generated
     */
    EClass JML_FRESH_EXPRESSION = eINSTANCE.getJMLFreshExpression();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_FRESH_EXPRESSION__EXPR = eINSTANCE.getJMLFreshExpression_Expr();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLResultExpressionImpl <em>Result Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLResultExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLResultExpression()
     * @generated
     */
    EClass JML_RESULT_EXPRESSION = eINSTANCE.getJMLResultExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl <em>For All Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLForAllExpression()
     * @generated
     */
    EClass JML_FOR_ALL_EXPRESSION = eINSTANCE.getJMLForAllExpression();

    /**
     * The meta object literal for the '<em><b>Init Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS = eINSTANCE.getJMLForAllExpression_InitExpressions();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_FOR_ALL_EXPRESSION__EXPRESSION = eINSTANCE.getJMLForAllExpression_Expression();

    /**
     * The meta object literal for the '<em><b>Update Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS = eINSTANCE.getJMLForAllExpression_UpdateExpressions();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl <em>Closure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getClosure()
     * @generated
     */
    EClass CLOSURE = eINSTANCE.getClosure();

    /**
     * The meta object literal for the '<em><b>Declared Formal Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOSURE__DECLARED_FORMAL_PARAMETERS = eINSTANCE.getClosure_DeclaredFormalParameters();

    /**
     * The meta object literal for the '<em><b>Explicit Syntax</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLOSURE__EXPLICIT_SYNTAX = eINSTANCE.getClosure_ExplicitSyntax();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOSURE__EXPRESSION = eINSTANCE.getClosure_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BlockExpressionImpl <em>Block Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BlockExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBlockExpression()
     * @generated
     */
    EClass BLOCK_EXPRESSION = eINSTANCE.getBlockExpression();

    /**
     * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BLOCK_EXPRESSION__EXPRESSIONS = eINSTANCE.getBlockExpression_Expressions();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.IfExpressionImpl <em>If Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.IfExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getIfExpression()
     * @generated
     */
    EClass IF_EXPRESSION = eINSTANCE.getIfExpression();

    /**
     * The meta object literal for the '<em><b>If</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_EXPRESSION__IF = eINSTANCE.getIfExpression_If();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_EXPRESSION__THEN = eINSTANCE.getIfExpression_Then();

    /**
     * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_EXPRESSION__ELSE = eINSTANCE.getIfExpression_Else();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SwitchExpressionImpl <em>Switch Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.SwitchExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSwitchExpression()
     * @generated
     */
    EClass SWITCH_EXPRESSION = eINSTANCE.getSwitchExpression();

    /**
     * The meta object literal for the '<em><b>Declared Param</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWITCH_EXPRESSION__DECLARED_PARAM = eINSTANCE.getSwitchExpression_DeclaredParam();

    /**
     * The meta object literal for the '<em><b>Switch</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWITCH_EXPRESSION__SWITCH = eINSTANCE.getSwitchExpression_Switch();

    /**
     * The meta object literal for the '<em><b>Cases</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWITCH_EXPRESSION__CASES = eINSTANCE.getSwitchExpression_Cases();

    /**
     * The meta object literal for the '<em><b>Default</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWITCH_EXPRESSION__DEFAULT = eINSTANCE.getSwitchExpression_Default();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl <em>For Loop Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getForLoopExpression()
     * @generated
     */
    EClass FOR_LOOP_EXPRESSION = eINSTANCE.getForLoopExpression();

    /**
     * The meta object literal for the '<em><b>Declared Param</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FOR_LOOP_EXPRESSION__DECLARED_PARAM = eINSTANCE.getForLoopExpression_DeclaredParam();

    /**
     * The meta object literal for the '<em><b>For Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FOR_LOOP_EXPRESSION__FOR_EXPRESSION = eINSTANCE.getForLoopExpression_ForExpression();

    /**
     * The meta object literal for the '<em><b>Each Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FOR_LOOP_EXPRESSION__EACH_EXPRESSION = eINSTANCE.getForLoopExpression_EachExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl <em>Basic For Loop Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBasicForLoopExpression()
     * @generated
     */
    EClass BASIC_FOR_LOOP_EXPRESSION = eINSTANCE.getBasicForLoopExpression();

    /**
     * The meta object literal for the '<em><b>Init Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS = eINSTANCE.getBasicForLoopExpression_InitExpressions();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BASIC_FOR_LOOP_EXPRESSION__EXPRESSION = eINSTANCE.getBasicForLoopExpression_Expression();

    /**
     * The meta object literal for the '<em><b>Update Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS = eINSTANCE.getBasicForLoopExpression_UpdateExpressions();

    /**
     * The meta object literal for the '<em><b>Each Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION = eINSTANCE.getBasicForLoopExpression_EachExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.WhileExpressionImpl <em>While Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.WhileExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getWhileExpression()
     * @generated
     */
    EClass WHILE_EXPRESSION = eINSTANCE.getWhileExpression();

    /**
     * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHILE_EXPRESSION__PREDICATE = eINSTANCE.getWhileExpression_Predicate();

    /**
     * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHILE_EXPRESSION__BODY = eINSTANCE.getWhileExpression_Body();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.DoWhileExpressionImpl <em>Do While Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.DoWhileExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getDoWhileExpression()
     * @generated
     */
    EClass DO_WHILE_EXPRESSION = eINSTANCE.getDoWhileExpression();

    /**
     * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DO_WHILE_EXPRESSION__BODY = eINSTANCE.getDoWhileExpression_Body();

    /**
     * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DO_WHILE_EXPRESSION__PREDICATE = eINSTANCE.getDoWhileExpression_Predicate();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.FeatureCallImpl <em>Feature Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.FeatureCallImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getFeatureCall()
     * @generated
     */
    EClass FEATURE_CALL = eINSTANCE.getFeatureCall();

    /**
     * The meta object literal for the '<em><b>Type Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_CALL__TYPE_ARGUMENTS = eINSTANCE.getFeatureCall_TypeArguments();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_CALL__FEATURE = eINSTANCE.getFeatureCall_Feature();

    /**
     * The meta object literal for the '<em><b>Explicit Operation Call</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_CALL__EXPLICIT_OPERATION_CALL = eINSTANCE.getFeatureCall_ExplicitOperationCall();

    /**
     * The meta object literal for the '<em><b>Feature Call Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_CALL__FEATURE_CALL_ARGUMENTS = eINSTANCE.getFeatureCall_FeatureCallArguments();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl <em>Constructor Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getConstructorCall()
     * @generated
     */
    EClass CONSTRUCTOR_CALL = eINSTANCE.getConstructorCall();

    /**
     * The meta object literal for the '<em><b>Constructor</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTRUCTOR_CALL__CONSTRUCTOR = eINSTANCE.getConstructorCall_Constructor();

    /**
     * The meta object literal for the '<em><b>Type Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR_CALL__TYPE_ARGUMENTS = eINSTANCE.getConstructorCall_TypeArguments();

    /**
     * The meta object literal for the '<em><b>Explicit Constructor Call</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL = eINSTANCE.getConstructorCall_ExplicitConstructorCall();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONSTRUCTOR_CALL__ARGUMENTS = eINSTANCE.getConstructorCall_Arguments();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.BooleanLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getBooleanLiteral()
     * @generated
     */
    EClass BOOLEAN_LITERAL = eINSTANCE.getBooleanLiteral();

    /**
     * The meta object literal for the '<em><b>Is True</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BOOLEAN_LITERAL__IS_TRUE = eINSTANCE.getBooleanLiteral_IsTrue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NullLiteralImpl <em>Null Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.NullLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNullLiteral()
     * @generated
     */
    EClass NULL_LITERAL = eINSTANCE.getNullLiteral();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.NumberLiteralImpl <em>Number Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.NumberLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getNumberLiteral()
     * @generated
     */
    EClass NUMBER_LITERAL = eINSTANCE.getNumberLiteral();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NUMBER_LITERAL__VALUE = eINSTANCE.getNumberLiteral_Value();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.StringLiteralImpl <em>String Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.StringLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getStringLiteral()
     * @generated
     */
    EClass STRING_LITERAL = eINSTANCE.getStringLiteral();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STRING_LITERAL__VALUE = eINSTANCE.getStringLiteral_Value();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.CharLiteralImpl <em>Char Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.CharLiteralImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getCharLiteral()
     * @generated
     */
    EClass CHAR_LITERAL = eINSTANCE.getCharLiteral();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHAR_LITERAL__VALUE = eINSTANCE.getCharLiteral_Value();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ThrowExpressionImpl <em>Throw Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ThrowExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getThrowExpression()
     * @generated
     */
    EClass THROW_EXPRESSION = eINSTANCE.getThrowExpression();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference THROW_EXPRESSION__EXPRESSION = eINSTANCE.getThrowExpression_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.ReturnExpressionImpl <em>Return Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.ReturnExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getReturnExpression()
     * @generated
     */
    EClass RETURN_EXPRESSION = eINSTANCE.getReturnExpression();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RETURN_EXPRESSION__EXPRESSION = eINSTANCE.getReturnExpression_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl <em>Try Catch Finally Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getTryCatchFinallyExpression()
     * @generated
     */
    EClass TRY_CATCH_FINALLY_EXPRESSION = eINSTANCE.getTryCatchFinallyExpression();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION = eINSTANCE.getTryCatchFinallyExpression_Expression();

    /**
     * The meta object literal for the '<em><b>Catch Clauses</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES = eINSTANCE.getTryCatchFinallyExpression_CatchClauses();

    /**
     * The meta object literal for the '<em><b>Finally Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION = eINSTANCE.getTryCatchFinallyExpression_FinallyExpression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.impl.SynchronizedExpressionImpl <em>Synchronized Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.impl.SynchronizedExpressionImpl
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getSynchronizedExpression()
     * @generated
     */
    EClass SYNCHRONIZED_EXPRESSION = eINSTANCE.getSynchronizedExpression();

    /**
     * The meta object literal for the '<em><b>Param</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYNCHRONIZED_EXPRESSION__PARAM = eINSTANCE.getSynchronizedExpression_Param();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYNCHRONIZED_EXPRESSION__EXPRESSION = eINSTANCE.getSynchronizedExpression_Expression();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.ModifierValue <em>Modifier Value</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.ModifierValue
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getModifierValue()
     * @generated
     */
    EEnum MODIFIER_VALUE = eINSTANCE.getModifierValue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue <em>Visibility Modifier Value</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getVisibilityModifierValue()
     * @generated
     */
    EEnum VISIBILITY_MODIFIER_VALUE = eINSTANCE.getVisibilityModifierValue();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier <em>Spec Member Modifier</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getJMLSpecMemberModifier()
     * @generated
     */
    EEnum JML_SPEC_MEMBER_MODIFIER = eINSTANCE.getJMLSpecMemberModifier();

    /**
     * The meta object literal for the '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveType <em>Primitive Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruvius.domains.jml.language.jML.PrimitiveType
     * @see tools.vitruvius.domains.jml.language.jML.impl.JMLPackageImpl#getPrimitiveType()
     * @generated
     */
    EEnum PRIMITIVE_TYPE = eINSTANCE.getPrimitiveType();

  }

} //JMLPackage
