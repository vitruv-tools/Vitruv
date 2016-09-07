/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage
 * @generated
 */
public interface JMLFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  JMLFactory eINSTANCE = tools.vitruvius.domains.jml.language.jML.impl.JMLFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Compilation Unit</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Compilation Unit</em>'.
   * @generated
   */
  CompilationUnit createCompilationUnit();

  /**
   * Returns a new object of class '<em>Package Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Package Declaration</em>'.
   * @generated
   */
  PackageDeclaration createPackageDeclaration();

  /**
   * Returns a new object of class '<em>Import Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import Declaration</em>'.
   * @generated
   */
  ImportDeclaration createImportDeclaration();

  /**
   * Returns a new object of class '<em>Classifier Declaration With Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Classifier Declaration With Modifier</em>'.
   * @generated
   */
  ClassifierDeclarationWithModifier createClassifierDeclarationWithModifier();

  /**
   * Returns a new object of class '<em>Class Or Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Or Interface Declaration</em>'.
   * @generated
   */
  ClassOrInterfaceDeclaration createClassOrInterfaceDeclaration();

  /**
   * Returns a new object of class '<em>Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Modifier</em>'.
   * @generated
   */
  Modifier createModifier();

  /**
   * Returns a new object of class '<em>Regular Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Regular Modifier</em>'.
   * @generated
   */
  RegularModifier createRegularModifier();

  /**
   * Returns a new object of class '<em>Class Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Declaration</em>'.
   * @generated
   */
  ClassDeclaration createClassDeclaration();

  /**
   * Returns a new object of class '<em>Normal Class Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Normal Class Declaration</em>'.
   * @generated
   */
  NormalClassDeclaration createNormalClassDeclaration();

  /**
   * Returns a new object of class '<em>Type Parameters</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Parameters</em>'.
   * @generated
   */
  TypeParameters createTypeParameters();

  /**
   * Returns a new object of class '<em>Type Parameter</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Parameter</em>'.
   * @generated
   */
  TypeParameter createTypeParameter();

  /**
   * Returns a new object of class '<em>Type Bound</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Bound</em>'.
   * @generated
   */
  TypeBound createTypeBound();

  /**
   * Returns a new object of class '<em>Enum Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Enum Declaration</em>'.
   * @generated
   */
  EnumDeclaration createEnumDeclaration();

  /**
   * Returns a new object of class '<em>Enum Constants</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Enum Constants</em>'.
   * @generated
   */
  EnumConstants createEnumConstants();

  /**
   * Returns a new object of class '<em>Enum Constant</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Enum Constant</em>'.
   * @generated
   */
  EnumConstant createEnumConstant();

  /**
   * Returns a new object of class '<em>Enum Body Declarations</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Enum Body Declarations</em>'.
   * @generated
   */
  EnumBodyDeclarations createEnumBodyDeclarations();

  /**
   * Returns a new object of class '<em>Arguments</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Arguments</em>'.
   * @generated
   */
  Arguments createArguments();

  /**
   * Returns a new object of class '<em>Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interface Declaration</em>'.
   * @generated
   */
  InterfaceDeclaration createInterfaceDeclaration();

  /**
   * Returns a new object of class '<em>Normal Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Normal Interface Declaration</em>'.
   * @generated
   */
  NormalInterfaceDeclaration createNormalInterfaceDeclaration();

  /**
   * Returns a new object of class '<em>Class Body Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Body Declaration</em>'.
   * @generated
   */
  ClassBodyDeclaration createClassBodyDeclaration();

  /**
   * Returns a new object of class '<em>Static Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Static Block</em>'.
   * @generated
   */
  StaticBlock createStaticBlock();

  /**
   * Returns a new object of class '<em>Specified Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Specified Element</em>'.
   * @generated
   */
  JMLSpecifiedElement createJMLSpecifiedElement();

  /**
   * Returns a new object of class '<em>Multiline Spec</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Multiline Spec</em>'.
   * @generated
   */
  JMLMultilineSpec createJMLMultilineSpec();

  /**
   * Returns a new object of class '<em>Singleline Spec</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Singleline Spec</em>'.
   * @generated
   */
  JMLSinglelineSpec createJMLSinglelineSpec();

  /**
   * Returns a new object of class '<em>Expression Having</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Having</em>'.
   * @generated
   */
  JMLExpressionHaving createJMLExpressionHaving();

  /**
   * Returns a new object of class '<em>Visiblity Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Visiblity Modifier</em>'.
   * @generated
   */
  VisiblityModifier createVisiblityModifier();

  /**
   * Returns a new object of class '<em>Method Specification With Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Specification With Modifier</em>'.
   * @generated
   */
  JMLMethodSpecificationWithModifier createJMLMethodSpecificationWithModifier();

  /**
   * Returns a new object of class '<em>Method Specification With Modifier Regular</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Specification With Modifier Regular</em>'.
   * @generated
   */
  JMLMethodSpecificationWithModifierRegular createJMLMethodSpecificationWithModifierRegular();

  /**
   * Returns a new object of class '<em>Method Specification With Modifier Extended</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Specification With Modifier Extended</em>'.
   * @generated
   */
  JMLMethodSpecificationWithModifierExtended createJMLMethodSpecificationWithModifierExtended();

  /**
   * Returns a new object of class '<em>Method Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Specification</em>'.
   * @generated
   */
  JMLMethodSpecification createJMLMethodSpecification();

  /**
   * Returns a new object of class '<em>Method Behavior</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Behavior</em>'.
   * @generated
   */
  JMLMethodBehavior createJMLMethodBehavior();

  /**
   * Returns a new object of class '<em>Exceptional Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Exceptional Behavior Block</em>'.
   * @generated
   */
  JMLExceptionalBehaviorBlock createJMLExceptionalBehaviorBlock();

  /**
   * Returns a new object of class '<em>Normal Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Normal Behavior Block</em>'.
   * @generated
   */
  JMLNormalBehaviorBlock createJMLNormalBehaviorBlock();

  /**
   * Returns a new object of class '<em>Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Behavior Block</em>'.
   * @generated
   */
  JMLBehaviorBlock createJMLBehaviorBlock();

  /**
   * Returns a new object of class '<em>Method Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Expression</em>'.
   * @generated
   */
  JMLMethodExpression createJMLMethodExpression();

  /**
   * Returns a new object of class '<em>Ensures Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ensures Expression</em>'.
   * @generated
   */
  JMLEnsuresExpression createJMLEnsuresExpression();

  /**
   * Returns a new object of class '<em>Requires Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Requires Expression</em>'.
   * @generated
   */
  JMLRequiresExpression createJMLRequiresExpression();

  /**
   * Returns a new object of class '<em>Specification Only Element With Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Specification Only Element With Modifier</em>'.
   * @generated
   */
  JMLSpecificationOnlyElementWithModifier createJMLSpecificationOnlyElementWithModifier();

  /**
   * Returns a new object of class '<em>Specification Only Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Specification Only Element</em>'.
   * @generated
   */
  JMLSpecificationOnlyElement createJMLSpecificationOnlyElement();

  /**
   * Returns a new object of class '<em>Model Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Model Element</em>'.
   * @generated
   */
  JMLModelElement createJMLModelElement();

  /**
   * Returns a new object of class '<em>Ghost Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ghost Element</em>'.
   * @generated
   */
  JMLGhostElement createJMLGhostElement();

  /**
   * Returns a new object of class '<em>Type Expression With Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Expression With Modifier</em>'.
   * @generated
   */
  JMLTypeExpressionWithModifier createJMLTypeExpressionWithModifier();

  /**
   * Returns a new object of class '<em>Type Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Expression</em>'.
   * @generated
   */
  JMLTypeExpression createJMLTypeExpression();

  /**
   * Returns a new object of class '<em>Invariant Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Invariant Expression</em>'.
   * @generated
   */
  JMLInvariantExpression createJMLInvariantExpression();

  /**
   * Returns a new object of class '<em>Constraint Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Constraint Expression</em>'.
   * @generated
   */
  JMLConstraintExpression createJMLConstraintExpression();

  /**
   * Returns a new object of class '<em>Axiom Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Axiom Expression</em>'.
   * @generated
   */
  JMLAxiomExpression createJMLAxiomExpression();

  /**
   * Returns a new object of class '<em>Member Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Modifier</em>'.
   * @generated
   */
  JMLMemberModifier createJMLMemberModifier();

  /**
   * Returns a new object of class '<em>Member Decl With Modifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Decl With Modifier</em>'.
   * @generated
   */
  MemberDeclWithModifier createMemberDeclWithModifier();

  /**
   * Returns a new object of class '<em>Member Decl With Modifier Regular</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Decl With Modifier Regular</em>'.
   * @generated
   */
  MemberDeclWithModifierRegular createMemberDeclWithModifierRegular();

  /**
   * Returns a new object of class '<em>Member Decl With Modifier Spec</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Decl With Modifier Spec</em>'.
   * @generated
   */
  MemberDeclWithModifierSpec createMemberDeclWithModifierSpec();

  /**
   * Returns a new object of class '<em>Member Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Decl</em>'.
   * @generated
   */
  MemberDecl createMemberDecl();

  /**
   * Returns a new object of class '<em>Constructor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Constructor</em>'.
   * @generated
   */
  Constructor createConstructor();

  /**
   * Returns a new object of class '<em>Member Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Declaration</em>'.
   * @generated
   */
  MemberDeclaration createMemberDeclaration();

  /**
   * Returns a new object of class '<em>Generic Method Or Constructor Decl Old</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Generic Method Or Constructor Decl Old</em>'.
   * @generated
   */
  GenericMethodOrConstructorDeclOld createGenericMethodOrConstructorDeclOld();

  /**
   * Returns a new object of class '<em>Generic Method Or Constructor Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Generic Method Or Constructor Decl</em>'.
   * @generated
   */
  GenericMethodOrConstructorDecl createGenericMethodOrConstructorDecl();

  /**
   * Returns a new object of class '<em>Method Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Declaration</em>'.
   * @generated
   */
  MethodDeclaration createMethodDeclaration();

  /**
   * Returns a new object of class '<em>Field Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Field Declaration</em>'.
   * @generated
   */
  FieldDeclaration createFieldDeclaration();

  /**
   * Returns a new object of class '<em>Declared Exception</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Declared Exception</em>'.
   * @generated
   */
  DeclaredException createDeclaredException();

  /**
   * Returns a new object of class '<em>Variable Declarator</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable Declarator</em>'.
   * @generated
   */
  VariableDeclarator createVariableDeclarator();

  /**
   * Returns a new object of class '<em>Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type</em>'.
   * @generated
   */
  Type createType();

  /**
   * Returns a new object of class '<em>Class Or Interface Type With Brackets</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Or Interface Type With Brackets</em>'.
   * @generated
   */
  ClassOrInterfaceTypeWithBrackets createClassOrInterfaceTypeWithBrackets();

  /**
   * Returns a new object of class '<em>Primitive Type With Brackets</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Primitive Type With Brackets</em>'.
   * @generated
   */
  PrimitiveTypeWithBrackets createPrimitiveTypeWithBrackets();

  /**
   * Returns a new object of class '<em>Brackets</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Brackets</em>'.
   * @generated
   */
  Brackets createBrackets();

  /**
   * Returns a new object of class '<em>Class Or Interface Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Or Interface Type</em>'.
   * @generated
   */
  ClassOrInterfaceType createClassOrInterfaceType();

  /**
   * Returns a new object of class '<em>Classifier Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Classifier Type</em>'.
   * @generated
   */
  ClassifierType createClassifierType();

  /**
   * Returns a new object of class '<em>Type Arguments</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Arguments</em>'.
   * @generated
   */
  TypeArguments createTypeArguments();

  /**
   * Returns a new object of class '<em>Type Argument</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Argument</em>'.
   * @generated
   */
  TypeArgument createTypeArgument();

  /**
   * Returns a new object of class '<em>Formal Parameter Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Formal Parameter Decl</em>'.
   * @generated
   */
  FormalParameterDecl createFormalParameterDecl();

  /**
   * Returns a new object of class '<em>Method Body</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Method Body</em>'.
   * @generated
   */
  MethodBody createMethodBody();

  /**
   * Returns a new object of class '<em>Constructor Body</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Constructor Body</em>'.
   * @generated
   */
  ConstructorBody createConstructorBody();

  /**
   * Returns a new object of class '<em>Modifiable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Modifiable</em>'.
   * @generated
   */
  Modifiable createModifiable();

  /**
   * Returns a new object of class '<em>Typed</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Typed</em>'.
   * @generated
   */
  Typed createTyped();

  /**
   * Returns a new object of class '<em>Annotations</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotations</em>'.
   * @generated
   */
  Annotations createAnnotations();

  /**
   * Returns a new object of class '<em>Annotation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation</em>'.
   * @generated
   */
  Annotation createAnnotation();

  /**
   * Returns a new object of class '<em>Element Value Pairs</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element Value Pairs</em>'.
   * @generated
   */
  ElementValuePairs createElementValuePairs();

  /**
   * Returns a new object of class '<em>Element Value Pair</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element Value Pair</em>'.
   * @generated
   */
  ElementValuePair createElementValuePair();

  /**
   * Returns a new object of class '<em>Element Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element Value</em>'.
   * @generated
   */
  ElementValue createElementValue();

  /**
   * Returns a new object of class '<em>Element Value Array Initializer</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element Value Array Initializer</em>'.
   * @generated
   */
  ElementValueArrayInitializer createElementValueArrayInitializer();

  /**
   * Returns a new object of class '<em>Annotation Type Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Type Declaration</em>'.
   * @generated
   */
  AnnotationTypeDeclaration createAnnotationTypeDeclaration();

  /**
   * Returns a new object of class '<em>Annotation Type Element Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Type Element Declaration</em>'.
   * @generated
   */
  AnnotationTypeElementDeclaration createAnnotationTypeElementDeclaration();

  /**
   * Returns a new object of class '<em>Annotation Type Element Rest</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Type Element Rest</em>'.
   * @generated
   */
  AnnotationTypeElementRest createAnnotationTypeElementRest();

  /**
   * Returns a new object of class '<em>Annotation Method Or Constant Rest</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Method Or Constant Rest</em>'.
   * @generated
   */
  AnnotationMethodOrConstantRest createAnnotationMethodOrConstantRest();

  /**
   * Returns a new object of class '<em>Annotation Method Rest</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Method Rest</em>'.
   * @generated
   */
  AnnotationMethodRest createAnnotationMethodRest();

  /**
   * Returns a new object of class '<em>Annotation Constant Rest</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Constant Rest</em>'.
   * @generated
   */
  AnnotationConstantRest createAnnotationConstantRest();

  /**
   * Returns a new object of class '<em>Default Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Default Value</em>'.
   * @generated
   */
  DefaultValue createDefaultValue();

  /**
   * Returns a new object of class '<em>Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Block</em>'.
   * @generated
   */
  Block createBlock();

  /**
   * Returns a new object of class '<em>Block Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Block Statement</em>'.
   * @generated
   */
  BlockStatement createBlockStatement();

  /**
   * Returns a new object of class '<em>Identifier Having</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Identifier Having</em>'.
   * @generated
   */
  IdentifierHaving createIdentifierHaving();

  /**
   * Returns a new object of class '<em>Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression</em>'.
   * @generated
   */
  Expression createExpression();

  /**
   * Returns a new object of class '<em>Parenthesis Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parenthesis Expression</em>'.
   * @generated
   */
  ParenthesisExpression createParenthesisExpression();

  /**
   * Returns a new object of class '<em>Collection Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Collection Literal</em>'.
   * @generated
   */
  CollectionLiteral createCollectionLiteral();

  /**
   * Returns a new object of class '<em>Set Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Set Literal</em>'.
   * @generated
   */
  SetLiteral createSetLiteral();

  /**
   * Returns a new object of class '<em>List Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>List Literal</em>'.
   * @generated
   */
  ListLiteral createListLiteral();

  /**
   * Returns a new object of class '<em>Case Part</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Case Part</em>'.
   * @generated
   */
  CasePart createCasePart();

  /**
   * Returns a new object of class '<em>Valid ID</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Valid ID</em>'.
   * @generated
   */
  ValidID createValidID();

  /**
   * Returns a new object of class '<em>Catch Clause</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Catch Clause</em>'.
   * @generated
   */
  CatchClause createCatchClause();

  /**
   * Returns a new object of class '<em>Assignment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Assignment</em>'.
   * @generated
   */
  Assignment createAssignment();

  /**
   * Returns a new object of class '<em>Binary Operation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Binary Operation</em>'.
   * @generated
   */
  BinaryOperation createBinaryOperation();

  /**
   * Returns a new object of class '<em>Instance Of Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance Of Expression</em>'.
   * @generated
   */
  InstanceOfExpression createInstanceOfExpression();

  /**
   * Returns a new object of class '<em>Unary Operation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Unary Operation</em>'.
   * @generated
   */
  UnaryOperation createUnaryOperation();

  /**
   * Returns a new object of class '<em>Postfix Operation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Postfix Operation</em>'.
   * @generated
   */
  PostfixOperation createPostfixOperation();

  /**
   * Returns a new object of class '<em>Member Feature Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Member Feature Call</em>'.
   * @generated
   */
  MemberFeatureCall createMemberFeatureCall();

  /**
   * Returns a new object of class '<em>Old Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Old Expression</em>'.
   * @generated
   */
  JMLOldExpression createJMLOldExpression();

  /**
   * Returns a new object of class '<em>Fresh Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fresh Expression</em>'.
   * @generated
   */
  JMLFreshExpression createJMLFreshExpression();

  /**
   * Returns a new object of class '<em>Result Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Result Expression</em>'.
   * @generated
   */
  JMLResultExpression createJMLResultExpression();

  /**
   * Returns a new object of class '<em>For All Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>For All Expression</em>'.
   * @generated
   */
  JMLForAllExpression createJMLForAllExpression();

  /**
   * Returns a new object of class '<em>Closure</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Closure</em>'.
   * @generated
   */
  Closure createClosure();

  /**
   * Returns a new object of class '<em>Block Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Block Expression</em>'.
   * @generated
   */
  BlockExpression createBlockExpression();

  /**
   * Returns a new object of class '<em>If Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>If Expression</em>'.
   * @generated
   */
  IfExpression createIfExpression();

  /**
   * Returns a new object of class '<em>Switch Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Switch Expression</em>'.
   * @generated
   */
  SwitchExpression createSwitchExpression();

  /**
   * Returns a new object of class '<em>For Loop Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>For Loop Expression</em>'.
   * @generated
   */
  ForLoopExpression createForLoopExpression();

  /**
   * Returns a new object of class '<em>Basic For Loop Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Basic For Loop Expression</em>'.
   * @generated
   */
  BasicForLoopExpression createBasicForLoopExpression();

  /**
   * Returns a new object of class '<em>While Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>While Expression</em>'.
   * @generated
   */
  WhileExpression createWhileExpression();

  /**
   * Returns a new object of class '<em>Do While Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Do While Expression</em>'.
   * @generated
   */
  DoWhileExpression createDoWhileExpression();

  /**
   * Returns a new object of class '<em>Feature Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Feature Call</em>'.
   * @generated
   */
  FeatureCall createFeatureCall();

  /**
   * Returns a new object of class '<em>Constructor Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Constructor Call</em>'.
   * @generated
   */
  ConstructorCall createConstructorCall();

  /**
   * Returns a new object of class '<em>Boolean Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Boolean Literal</em>'.
   * @generated
   */
  BooleanLiteral createBooleanLiteral();

  /**
   * Returns a new object of class '<em>Null Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Null Literal</em>'.
   * @generated
   */
  NullLiteral createNullLiteral();

  /**
   * Returns a new object of class '<em>Number Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Number Literal</em>'.
   * @generated
   */
  NumberLiteral createNumberLiteral();

  /**
   * Returns a new object of class '<em>String Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>String Literal</em>'.
   * @generated
   */
  StringLiteral createStringLiteral();

  /**
   * Returns a new object of class '<em>Char Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Char Literal</em>'.
   * @generated
   */
  CharLiteral createCharLiteral();

  /**
   * Returns a new object of class '<em>Throw Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Throw Expression</em>'.
   * @generated
   */
  ThrowExpression createThrowExpression();

  /**
   * Returns a new object of class '<em>Return Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Return Expression</em>'.
   * @generated
   */
  ReturnExpression createReturnExpression();

  /**
   * Returns a new object of class '<em>Try Catch Finally Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Try Catch Finally Expression</em>'.
   * @generated
   */
  TryCatchFinallyExpression createTryCatchFinallyExpression();

  /**
   * Returns a new object of class '<em>Synchronized Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Synchronized Expression</em>'.
   * @generated
   */
  SynchronizedExpression createSynchronizedExpression();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  JMLPackage getJMLPackage();

} //JMLFactory
