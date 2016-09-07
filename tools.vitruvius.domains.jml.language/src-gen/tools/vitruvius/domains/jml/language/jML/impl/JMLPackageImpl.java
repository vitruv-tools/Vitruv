/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Annotation;
import tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest;
import tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest;
import tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest;
import tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration;
import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration;
import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest;
import tools.vitruvius.domains.jml.language.jML.Annotations;
import tools.vitruvius.domains.jml.language.jML.Arguments;
import tools.vitruvius.domains.jml.language.jML.Assignment;
import tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression;
import tools.vitruvius.domains.jml.language.jML.BinaryOperation;
import tools.vitruvius.domains.jml.language.jML.Block;
import tools.vitruvius.domains.jml.language.jML.BlockExpression;
import tools.vitruvius.domains.jml.language.jML.BlockStatement;
import tools.vitruvius.domains.jml.language.jML.BooleanLiteral;
import tools.vitruvius.domains.jml.language.jML.Brackets;
import tools.vitruvius.domains.jml.language.jML.CasePart;
import tools.vitruvius.domains.jml.language.jML.CatchClause;
import tools.vitruvius.domains.jml.language.jML.CharLiteral;
import tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration;
import tools.vitruvius.domains.jml.language.jML.ClassDeclaration;
import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType;
import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets;
import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier;
import tools.vitruvius.domains.jml.language.jML.ClassifierType;
import tools.vitruvius.domains.jml.language.jML.Closure;
import tools.vitruvius.domains.jml.language.jML.CollectionLiteral;
import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.Constructor;
import tools.vitruvius.domains.jml.language.jML.ConstructorBody;
import tools.vitruvius.domains.jml.language.jML.ConstructorCall;
import tools.vitruvius.domains.jml.language.jML.DeclaredException;
import tools.vitruvius.domains.jml.language.jML.DefaultValue;
import tools.vitruvius.domains.jml.language.jML.DoWhileExpression;
import tools.vitruvius.domains.jml.language.jML.ElementValue;
import tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer;
import tools.vitruvius.domains.jml.language.jML.ElementValuePair;
import tools.vitruvius.domains.jml.language.jML.ElementValuePairs;
import tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations;
import tools.vitruvius.domains.jml.language.jML.EnumConstant;
import tools.vitruvius.domains.jml.language.jML.EnumConstants;
import tools.vitruvius.domains.jml.language.jML.EnumDeclaration;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.FeatureCall;
import tools.vitruvius.domains.jml.language.jML.FieldDeclaration;
import tools.vitruvius.domains.jml.language.jML.ForLoopExpression;
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl;
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld;
import tools.vitruvius.domains.jml.language.jML.IdentifierHaving;
import tools.vitruvius.domains.jml.language.jML.IfExpression;
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration;
import tools.vitruvius.domains.jml.language.jML.InstanceOfExpression;
import tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLAxiomExpression;
import tools.vitruvius.domains.jml.language.jML.JMLBehaviorBlock;
import tools.vitruvius.domains.jml.language.jML.JMLConstraintExpression;
import tools.vitruvius.domains.jml.language.jML.JMLEnsuresExpression;
import tools.vitruvius.domains.jml.language.jML.JMLExceptionalBehaviorBlock;
import tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving;
import tools.vitruvius.domains.jml.language.jML.JMLFactory;
import tools.vitruvius.domains.jml.language.jML.JMLForAllExpression;
import tools.vitruvius.domains.jml.language.jML.JMLFreshExpression;
import tools.vitruvius.domains.jml.language.jML.JMLGhostElement;
import tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression;
import tools.vitruvius.domains.jml.language.jML.JMLMemberModifier;
import tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior;
import tools.vitruvius.domains.jml.language.jML.JMLMethodExpression;
import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification;
import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierExtended;
import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierRegular;
import tools.vitruvius.domains.jml.language.jML.JMLModelElement;
import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLNormalBehaviorBlock;
import tools.vitruvius.domains.jml.language.jML.JMLOldExpression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLRequiresExpression;
import tools.vitruvius.domains.jml.language.jML.JMLResultExpression;
import tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpression;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier;
import tools.vitruvius.domains.jml.language.jML.ListLiteral;
import tools.vitruvius.domains.jml.language.jML.MemberDecl;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.language.jML.MemberFeatureCall;
import tools.vitruvius.domains.jml.language.jML.MethodBody;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;
import tools.vitruvius.domains.jml.language.jML.Modifiable;
import tools.vitruvius.domains.jml.language.jML.Modifier;
import tools.vitruvius.domains.jml.language.jML.ModifierValue;
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.NullLiteral;
import tools.vitruvius.domains.jml.language.jML.NumberLiteral;
import tools.vitruvius.domains.jml.language.jML.PackageDeclaration;
import tools.vitruvius.domains.jml.language.jML.ParenthesisExpression;
import tools.vitruvius.domains.jml.language.jML.PostfixOperation;
import tools.vitruvius.domains.jml.language.jML.PrimitiveType;
import tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets;
import tools.vitruvius.domains.jml.language.jML.RegularModifier;
import tools.vitruvius.domains.jml.language.jML.ReturnExpression;
import tools.vitruvius.domains.jml.language.jML.SetLiteral;
import tools.vitruvius.domains.jml.language.jML.StaticBlock;
import tools.vitruvius.domains.jml.language.jML.StringLiteral;
import tools.vitruvius.domains.jml.language.jML.SwitchExpression;
import tools.vitruvius.domains.jml.language.jML.SynchronizedExpression;
import tools.vitruvius.domains.jml.language.jML.ThrowExpression;
import tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.TypeArgument;
import tools.vitruvius.domains.jml.language.jML.TypeArguments;
import tools.vitruvius.domains.jml.language.jML.TypeBound;
import tools.vitruvius.domains.jml.language.jML.TypeParameter;
import tools.vitruvius.domains.jml.language.jML.TypeParameters;
import tools.vitruvius.domains.jml.language.jML.Typed;
import tools.vitruvius.domains.jml.language.jML.UnaryOperation;
import tools.vitruvius.domains.jml.language.jML.ValidID;
import tools.vitruvius.domains.jml.language.jML.VariableDeclarator;
import tools.vitruvius.domains.jml.language.jML.VisibilityModifierValue;
import tools.vitruvius.domains.jml.language.jML.VisiblityModifier;
import tools.vitruvius.domains.jml.language.jML.WhileExpression;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.xtext.common.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JMLPackageImpl extends EPackageImpl implements JMLPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compilationUnitEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass packageDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass importDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classifierDeclarationWithModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classOrInterfaceDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass modifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass regularModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass normalClassDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeParametersEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeParameterEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeBoundEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enumDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enumConstantsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enumConstantEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enumBodyDeclarationsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass argumentsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass interfaceDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass normalInterfaceDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classBodyDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass staticBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlSpecifiedElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMultilineSpecEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlSinglelineSpecEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlExpressionHavingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass visiblityModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodSpecificationWithModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodSpecificationWithModifierRegularEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodSpecificationWithModifierExtendedEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodSpecificationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodBehaviorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlExceptionalBehaviorBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlNormalBehaviorBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlBehaviorBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMethodExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlEnsuresExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlRequiresExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlSpecificationOnlyElementWithModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlSpecificationOnlyElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlModelElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlGhostElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlTypeExpressionWithModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlTypeExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlInvariantExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlConstraintExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlAxiomExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlMemberModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberDeclWithModifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberDeclWithModifierRegularEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberDeclWithModifierSpecEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constructorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass genericMethodOrConstructorDeclOldEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass genericMethodOrConstructorDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass methodDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass declaredExceptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass variableDeclaratorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classOrInterfaceTypeWithBracketsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass primitiveTypeWithBracketsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bracketsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classOrInterfaceTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classifierTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeArgumentsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeArgumentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass formalParameterDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass methodBodyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constructorBodyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass modifiableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typedEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass elementValuePairsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass elementValuePairEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass elementValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass elementValueArrayInitializerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationTypeDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationTypeElementDeclarationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationTypeElementRestEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationMethodOrConstantRestEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationMethodRestEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annotationConstantRestEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass defaultValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass blockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass blockStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass identifierHavingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass expressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parenthesisExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass collectionLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass setLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass listLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass casePartEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass validIDEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass catchClauseEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assignmentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass binaryOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass instanceOfExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass unaryOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass postfixOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass memberFeatureCallEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlOldExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlFreshExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlResultExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jmlForAllExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass closureEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass blockExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass ifExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass switchExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass forLoopExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass basicForLoopExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass whileExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass doWhileExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass featureCallEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constructorCallEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass booleanLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass nullLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass numberLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass stringLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass charLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass throwExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass returnExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass tryCatchFinallyExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass synchronizedExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum modifierValueEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum visibilityModifierValueEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum jmlSpecMemberModifierEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum primitiveTypeEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private JMLPackageImpl()
  {
    super(eNS_URI, JMLFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link JMLPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static JMLPackage init()
  {
    if (isInited) return (JMLPackage)EPackage.Registry.INSTANCE.getEPackage(JMLPackage.eNS_URI);

    // Obtain or create and register package
    JMLPackageImpl theJMLPackage = (JMLPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof JMLPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new JMLPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theJMLPackage.createPackageContents();

    // Initialize created meta-data
    theJMLPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theJMLPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(JMLPackage.eNS_URI, theJMLPackage);
    return theJMLPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompilationUnit()
  {
    return compilationUnitEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompilationUnit_Packagedeclaration()
  {
    return (EReference)compilationUnitEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompilationUnit_Importdeclaration()
  {
    return (EReference)compilationUnitEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompilationUnit_Typedeclaration()
  {
    return (EReference)compilationUnitEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPackageDeclaration()
  {
    return packageDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPackageDeclaration_Qualifiedname()
  {
    return (EAttribute)packageDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getImportDeclaration()
  {
    return importDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportDeclaration_Static()
  {
    return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportDeclaration_Qualifiedname()
  {
    return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportDeclaration_Wildcard()
  {
    return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassifierDeclarationWithModifier()
  {
    return classifierDeclarationWithModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClassifierDeclarationWithModifier_ClassOrInterfaceDeclaration()
  {
    return (EReference)classifierDeclarationWithModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassOrInterfaceDeclaration()
  {
    return classOrInterfaceDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModifier()
  {
    return modifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRegularModifier()
  {
    return regularModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRegularModifier_Modifier()
  {
    return (EAttribute)regularModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassDeclaration()
  {
    return classDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNormalClassDeclaration()
  {
    return normalClassDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNormalClassDeclaration_Identifier()
  {
    return (EAttribute)normalClassDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalClassDeclaration_Typeparameters()
  {
    return (EReference)normalClassDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalClassDeclaration_SuperType()
  {
    return (EReference)normalClassDeclarationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalClassDeclaration_ImplementedTypes()
  {
    return (EReference)normalClassDeclarationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalClassDeclaration_BodyDeclarations()
  {
    return (EReference)normalClassDeclarationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypeParameters()
  {
    return typeParametersEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameters_Typeparameter()
  {
    return (EReference)typeParametersEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameters_Type()
  {
    return (EReference)typeParametersEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTypeParameters_Identifier()
  {
    return (EAttribute)typeParametersEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameters_Parameters()
  {
    return (EReference)typeParametersEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameters_Exceptions()
  {
    return (EReference)typeParametersEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameters_Methodbody()
  {
    return (EReference)typeParametersEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypeParameter()
  {
    return typeParameterEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTypeParameter_Identifier()
  {
    return (EAttribute)typeParameterEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeParameter_Typebound()
  {
    return (EReference)typeParameterEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypeBound()
  {
    return typeBoundEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeBound_Type()
  {
    return (EReference)typeBoundEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnumDeclaration()
  {
    return enumDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumDeclaration_ImplementedTypes()
  {
    return (EReference)enumDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumDeclaration_Enumconstants()
  {
    return (EReference)enumDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumDeclaration_BodyDeclarations()
  {
    return (EReference)enumDeclarationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnumConstants()
  {
    return enumConstantsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumConstants_Enumconstant()
  {
    return (EReference)enumConstantsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnumConstant()
  {
    return enumConstantEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumConstant_Annotations()
  {
    return (EReference)enumConstantEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnumConstant_Identifier()
  {
    return (EAttribute)enumConstantEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumConstant_Arguments()
  {
    return (EReference)enumConstantEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumConstant_Classbodydeclaration()
  {
    return (EReference)enumConstantEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnumBodyDeclarations()
  {
    return enumBodyDeclarationsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnumBodyDeclarations_Classbodydeclaration()
  {
    return (EReference)enumBodyDeclarationsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getArguments()
  {
    return argumentsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getArguments_Expressions()
  {
    return (EReference)argumentsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInterfaceDeclaration()
  {
    return interfaceDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNormalInterfaceDeclaration()
  {
    return normalInterfaceDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNormalInterfaceDeclaration_Identifier()
  {
    return (EAttribute)normalInterfaceDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalInterfaceDeclaration_Typeparameters()
  {
    return (EReference)normalInterfaceDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalInterfaceDeclaration_ImplementedTypes()
  {
    return (EReference)normalInterfaceDeclarationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNormalInterfaceDeclaration_BodyDeclarations()
  {
    return (EReference)normalInterfaceDeclarationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassBodyDeclaration()
  {
    return classBodyDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getStaticBlock()
  {
    return staticBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getStaticBlock_Static()
  {
    return (EAttribute)staticBlockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getStaticBlock_Block()
  {
    return (EReference)staticBlockEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLSpecifiedElement()
  {
    return jmlSpecifiedElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecifiedElement_JmlTypeSpecifications()
  {
    return (EReference)jmlSpecifiedElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecifiedElement_JmlSpecifications()
  {
    return (EReference)jmlSpecifiedElementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecifiedElement_Element()
  {
    return (EReference)jmlSpecifiedElementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMultilineSpec()
  {
    return jmlMultilineSpecEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLMultilineSpec_ModelElement()
  {
    return (EReference)jmlMultilineSpecEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLSinglelineSpec()
  {
    return jmlSinglelineSpecEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLExpressionHaving()
  {
    return jmlExpressionHavingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLExpressionHaving_Expr()
  {
    return (EReference)jmlExpressionHavingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVisiblityModifier()
  {
    return visiblityModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVisiblityModifier_Modifier()
  {
    return (EAttribute)visiblityModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodSpecificationWithModifier()
  {
    return jmlMethodSpecificationWithModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLMethodSpecificationWithModifier_Modifier()
  {
    return (EReference)jmlMethodSpecificationWithModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLMethodSpecificationWithModifier_Spec()
  {
    return (EReference)jmlMethodSpecificationWithModifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodSpecificationWithModifierRegular()
  {
    return jmlMethodSpecificationWithModifierRegularEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodSpecificationWithModifierExtended()
  {
    return jmlMethodSpecificationWithModifierExtendedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodSpecification()
  {
    return jmlMethodSpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodBehavior()
  {
    return jmlMethodBehaviorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLMethodBehavior_Specifications()
  {
    return (EReference)jmlMethodBehaviorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLExceptionalBehaviorBlock()
  {
    return jmlExceptionalBehaviorBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLNormalBehaviorBlock()
  {
    return jmlNormalBehaviorBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLBehaviorBlock()
  {
    return jmlBehaviorBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMethodExpression()
  {
    return jmlMethodExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLEnsuresExpression()
  {
    return jmlEnsuresExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLRequiresExpression()
  {
    return jmlRequiresExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLSpecificationOnlyElementWithModifier()
  {
    return jmlSpecificationOnlyElementWithModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecificationOnlyElementWithModifier_Modifier()
  {
    return (EReference)jmlSpecificationOnlyElementWithModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecificationOnlyElementWithModifier_Element()
  {
    return (EReference)jmlSpecificationOnlyElementWithModifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLSpecificationOnlyElement()
  {
    return jmlSpecificationOnlyElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getJMLSpecificationOnlyElement_Instance()
  {
    return (EAttribute)jmlSpecificationOnlyElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLSpecificationOnlyElement_Element()
  {
    return (EReference)jmlSpecificationOnlyElementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLModelElement()
  {
    return jmlModelElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLGhostElement()
  {
    return jmlGhostElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLTypeExpressionWithModifier()
  {
    return jmlTypeExpressionWithModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLTypeExpressionWithModifier_Modifier()
  {
    return (EReference)jmlTypeExpressionWithModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLTypeExpressionWithModifier_Spec()
  {
    return (EReference)jmlTypeExpressionWithModifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLTypeExpression()
  {
    return jmlTypeExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLInvariantExpression()
  {
    return jmlInvariantExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLConstraintExpression()
  {
    return jmlConstraintExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLAxiomExpression()
  {
    return jmlAxiomExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLMemberModifier()
  {
    return jmlMemberModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getJMLMemberModifier_Modifier()
  {
    return (EAttribute)jmlMemberModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberDeclWithModifier()
  {
    return memberDeclWithModifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberDeclWithModifier_JmlModifiers()
  {
    return (EReference)memberDeclWithModifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberDeclWithModifier_Memberdecl()
  {
    return (EReference)memberDeclWithModifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberDeclWithModifierRegular()
  {
    return memberDeclWithModifierRegularEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberDeclWithModifierSpec()
  {
    return memberDeclWithModifierSpecEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberDecl()
  {
    return memberDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstructor()
  {
    return constructorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstructor_Identifier()
  {
    return (EAttribute)constructorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructor_Parameters()
  {
    return (EReference)constructorEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructor_Exceptions()
  {
    return (EReference)constructorEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructor_Constructorbody()
  {
    return (EReference)constructorEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberDeclaration()
  {
    return memberDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberDeclaration_Method()
  {
    return (EReference)memberDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberDeclaration_Field()
  {
    return (EReference)memberDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGenericMethodOrConstructorDeclOld()
  {
    return genericMethodOrConstructorDeclOldEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericMethodOrConstructorDeclOld_Constructor()
  {
    return (EReference)genericMethodOrConstructorDeclOldEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGenericMethodOrConstructorDecl()
  {
    return genericMethodOrConstructorDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericMethodOrConstructorDecl_TypeParameters()
  {
    return (EReference)genericMethodOrConstructorDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericMethodOrConstructorDecl_Type()
  {
    return (EReference)genericMethodOrConstructorDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericMethodOrConstructorDecl_Method()
  {
    return (EReference)genericMethodOrConstructorDeclEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericMethodOrConstructorDecl_Constructor()
  {
    return (EReference)genericMethodOrConstructorDeclEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMethodDeclaration()
  {
    return methodDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMethodDeclaration_Parameters()
  {
    return (EReference)methodDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMethodDeclaration_Exceptions()
  {
    return (EReference)methodDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMethodDeclaration_Methodbody()
  {
    return (EReference)methodDeclarationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFieldDeclaration()
  {
    return fieldDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFieldDeclaration_Variabledeclarator()
  {
    return (EReference)fieldDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDeclaredException()
  {
    return declaredExceptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDeclaredException_Name()
  {
    return (EAttribute)declaredExceptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVariableDeclarator()
  {
    return variableDeclaratorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVariableDeclarator_Brackets()
  {
    return (EReference)variableDeclaratorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVariableDeclarator_Expression()
  {
    return (EReference)variableDeclaratorEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getType()
  {
    return typeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getType_Brackets()
  {
    return (EReference)typeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassOrInterfaceTypeWithBrackets()
  {
    return classOrInterfaceTypeWithBracketsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClassOrInterfaceTypeWithBrackets_Type()
  {
    return (EReference)classOrInterfaceTypeWithBracketsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPrimitiveTypeWithBrackets()
  {
    return primitiveTypeWithBracketsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPrimitiveTypeWithBrackets_Primitivetype()
  {
    return (EAttribute)primitiveTypeWithBracketsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBrackets()
  {
    return bracketsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassOrInterfaceType()
  {
    return classOrInterfaceTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClassOrInterfaceType_Type()
  {
    return (EReference)classOrInterfaceTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassifierType()
  {
    return classifierTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getClassifierType_Identifier()
  {
    return (EAttribute)classifierTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClassifierType_Typearguments()
  {
    return (EReference)classifierTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypeArguments()
  {
    return typeArgumentsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeArguments_Typeargument()
  {
    return (EReference)typeArgumentsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypeArgument()
  {
    return typeArgumentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypeArgument_Type()
  {
    return (EReference)typeArgumentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTypeArgument_Wildcard()
  {
    return (EAttribute)typeArgumentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTypeArgument_Extends()
  {
    return (EAttribute)typeArgumentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTypeArgument_Super()
  {
    return (EAttribute)typeArgumentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFormalParameterDecl()
  {
    return formalParameterDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFormalParameterDecl_Varargs()
  {
    return (EAttribute)formalParameterDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFormalParameterDecl_Identifier()
  {
    return (EAttribute)formalParameterDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMethodBody()
  {
    return methodBodyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstructorBody()
  {
    return constructorBodyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructorBody_Blockstatement()
  {
    return (EReference)constructorBodyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModifiable()
  {
    return modifiableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getModifiable_Modifiers()
  {
    return (EReference)modifiableEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTyped()
  {
    return typedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTyped_Type()
  {
    return (EReference)typedEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotations()
  {
    return annotationsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotations_Annotation()
  {
    return (EReference)annotationsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotation()
  {
    return annotationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnotation_Annotationname()
  {
    return (EAttribute)annotationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotation_Elementvaluepairs()
  {
    return (EReference)annotationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotation_Elementvalue()
  {
    return (EReference)annotationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getElementValuePairs()
  {
    return elementValuePairsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getElementValuePairs_Elementvaluepair()
  {
    return (EReference)elementValuePairsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getElementValuePair()
  {
    return elementValuePairEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElementValuePair_Identifier()
  {
    return (EAttribute)elementValuePairEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getElementValuePair_Elementvalue()
  {
    return (EReference)elementValuePairEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getElementValue()
  {
    return elementValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getElementValueArrayInitializer()
  {
    return elementValueArrayInitializerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getElementValueArrayInitializer_Elementvalue()
  {
    return (EReference)elementValueArrayInitializerEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationTypeDeclaration()
  {
    return annotationTypeDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnotationTypeDeclaration_Identifier()
  {
    return (EAttribute)annotationTypeDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationTypeDeclaration_Annotationtypeelementdeclaration()
  {
    return (EReference)annotationTypeDeclarationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationTypeElementDeclaration()
  {
    return annotationTypeElementDeclarationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationTypeElementDeclaration_Annotationtypeelementrest()
  {
    return (EReference)annotationTypeElementDeclarationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationTypeElementRest()
  {
    return annotationTypeElementRestEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationMethodOrConstantRest()
  {
    return annotationMethodOrConstantRestEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationMethodOrConstantRest_Method()
  {
    return (EReference)annotationMethodOrConstantRestEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationMethodOrConstantRest_Constant()
  {
    return (EReference)annotationMethodOrConstantRestEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationMethodRest()
  {
    return annotationMethodRestEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnotationMethodRest_Identifier()
  {
    return (EAttribute)annotationMethodRestEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationMethodRest_Defaultvalue()
  {
    return (EReference)annotationMethodRestEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnotationConstantRest()
  {
    return annotationConstantRestEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnotationConstantRest_Variabledeclarator()
  {
    return (EReference)annotationConstantRestEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDefaultValue()
  {
    return defaultValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDefaultValue_Elementvalue()
  {
    return (EReference)defaultValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBlock()
  {
    return blockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBlock_Blockstatement()
  {
    return (EReference)blockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBlockStatement()
  {
    return blockStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIdentifierHaving()
  {
    return identifierHavingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIdentifierHaving_Identifier()
  {
    return (EAttribute)identifierHavingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExpression()
  {
    return expressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExpression_Type()
  {
    return (EReference)expressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExpression_Name()
  {
    return (EAttribute)expressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExpression_Right()
  {
    return (EReference)expressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParenthesisExpression()
  {
    return parenthesisExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getParenthesisExpression_Expr()
  {
    return (EReference)parenthesisExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCollectionLiteral()
  {
    return collectionLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCollectionLiteral_Elements()
  {
    return (EReference)collectionLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSetLiteral()
  {
    return setLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getListLiteral()
  {
    return listLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCasePart()
  {
    return casePartEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCasePart_TypeGuard()
  {
    return (EReference)casePartEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCasePart_Case()
  {
    return (EReference)casePartEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCasePart_Then()
  {
    return (EReference)casePartEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getValidID()
  {
    return validIDEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValidID_ParameterType()
  {
    return (EAttribute)validIDEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValidID_Name()
  {
    return (EAttribute)validIDEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCatchClause()
  {
    return catchClauseEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCatchClause_DeclaredParam()
  {
    return (EReference)catchClauseEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCatchClause_Expression()
  {
    return (EReference)catchClauseEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAssignment()
  {
    return assignmentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAssignment_TypeForVariableDeclaration()
  {
    return (EAttribute)assignmentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAssignment_Feature()
  {
    return (EAttribute)assignmentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAssignment_Value()
  {
    return (EReference)assignmentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAssignment_Assignable()
  {
    return (EReference)assignmentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBinaryOperation()
  {
    return binaryOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBinaryOperation_LeftOperand()
  {
    return (EReference)binaryOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBinaryOperation_Feature()
  {
    return (EAttribute)binaryOperationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBinaryOperation_RightOperand()
  {
    return (EReference)binaryOperationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInstanceOfExpression()
  {
    return instanceOfExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInstanceOfExpression_Expression()
  {
    return (EReference)instanceOfExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUnaryOperation()
  {
    return unaryOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUnaryOperation_Feature()
  {
    return (EAttribute)unaryOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getUnaryOperation_Operand()
  {
    return (EReference)unaryOperationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPostfixOperation()
  {
    return postfixOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getPostfixOperation_Operand()
  {
    return (EReference)postfixOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPostfixOperation_Feature()
  {
    return (EAttribute)postfixOperationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemberFeatureCall()
  {
    return memberFeatureCallEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberFeatureCall_MemberCallTarget()
  {
    return (EReference)memberFeatureCallEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMemberFeatureCall_NullSafe()
  {
    return (EAttribute)memberFeatureCallEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMemberFeatureCall_ExplicitStatic()
  {
    return (EAttribute)memberFeatureCallEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberFeatureCall_TypeArguments()
  {
    return (EReference)memberFeatureCallEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMemberFeatureCall_Feature()
  {
    return (EAttribute)memberFeatureCallEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMemberFeatureCall_ExplicitOperationCall()
  {
    return (EAttribute)memberFeatureCallEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemberFeatureCall_MemberCallArguments()
  {
    return (EReference)memberFeatureCallEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLOldExpression()
  {
    return jmlOldExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLOldExpression_Expr()
  {
    return (EReference)jmlOldExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLFreshExpression()
  {
    return jmlFreshExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLFreshExpression_Expr()
  {
    return (EReference)jmlFreshExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLResultExpression()
  {
    return jmlResultExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJMLForAllExpression()
  {
    return jmlForAllExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLForAllExpression_InitExpressions()
  {
    return (EReference)jmlForAllExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLForAllExpression_Expression()
  {
    return (EReference)jmlForAllExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getJMLForAllExpression_UpdateExpressions()
  {
    return (EReference)jmlForAllExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClosure()
  {
    return closureEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClosure_DeclaredFormalParameters()
  {
    return (EReference)closureEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getClosure_ExplicitSyntax()
  {
    return (EAttribute)closureEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getClosure_Expression()
  {
    return (EReference)closureEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBlockExpression()
  {
    return blockExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBlockExpression_Expressions()
  {
    return (EReference)blockExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIfExpression()
  {
    return ifExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIfExpression_If()
  {
    return (EReference)ifExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIfExpression_Then()
  {
    return (EReference)ifExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIfExpression_Else()
  {
    return (EReference)ifExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSwitchExpression()
  {
    return switchExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSwitchExpression_DeclaredParam()
  {
    return (EReference)switchExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSwitchExpression_Switch()
  {
    return (EReference)switchExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSwitchExpression_Cases()
  {
    return (EReference)switchExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSwitchExpression_Default()
  {
    return (EReference)switchExpressionEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getForLoopExpression()
  {
    return forLoopExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getForLoopExpression_DeclaredParam()
  {
    return (EReference)forLoopExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getForLoopExpression_ForExpression()
  {
    return (EReference)forLoopExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getForLoopExpression_EachExpression()
  {
    return (EReference)forLoopExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBasicForLoopExpression()
  {
    return basicForLoopExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBasicForLoopExpression_InitExpressions()
  {
    return (EReference)basicForLoopExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBasicForLoopExpression_Expression()
  {
    return (EReference)basicForLoopExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBasicForLoopExpression_UpdateExpressions()
  {
    return (EReference)basicForLoopExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getBasicForLoopExpression_EachExpression()
  {
    return (EReference)basicForLoopExpressionEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWhileExpression()
  {
    return whileExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWhileExpression_Predicate()
  {
    return (EReference)whileExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWhileExpression_Body()
  {
    return (EReference)whileExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDoWhileExpression()
  {
    return doWhileExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDoWhileExpression_Body()
  {
    return (EReference)doWhileExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDoWhileExpression_Predicate()
  {
    return (EReference)doWhileExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFeatureCall()
  {
    return featureCallEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureCall_TypeArguments()
  {
    return (EReference)featureCallEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFeatureCall_Feature()
  {
    return (EAttribute)featureCallEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFeatureCall_ExplicitOperationCall()
  {
    return (EAttribute)featureCallEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureCall_FeatureCallArguments()
  {
    return (EReference)featureCallEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstructorCall()
  {
    return constructorCallEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstructorCall_Constructor()
  {
    return (EAttribute)constructorCallEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructorCall_TypeArguments()
  {
    return (EReference)constructorCallEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstructorCall_ExplicitConstructorCall()
  {
    return (EAttribute)constructorCallEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstructorCall_Arguments()
  {
    return (EReference)constructorCallEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBooleanLiteral()
  {
    return booleanLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBooleanLiteral_IsTrue()
  {
    return (EAttribute)booleanLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNullLiteral()
  {
    return nullLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNumberLiteral()
  {
    return numberLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNumberLiteral_Value()
  {
    return (EAttribute)numberLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getStringLiteral()
  {
    return stringLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getStringLiteral_Value()
  {
    return (EAttribute)stringLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCharLiteral()
  {
    return charLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCharLiteral_Value()
  {
    return (EAttribute)charLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getThrowExpression()
  {
    return throwExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getThrowExpression_Expression()
  {
    return (EReference)throwExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReturnExpression()
  {
    return returnExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getReturnExpression_Expression()
  {
    return (EReference)returnExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTryCatchFinallyExpression()
  {
    return tryCatchFinallyExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTryCatchFinallyExpression_Expression()
  {
    return (EReference)tryCatchFinallyExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTryCatchFinallyExpression_CatchClauses()
  {
    return (EReference)tryCatchFinallyExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTryCatchFinallyExpression_FinallyExpression()
  {
    return (EReference)tryCatchFinallyExpressionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSynchronizedExpression()
  {
    return synchronizedExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSynchronizedExpression_Param()
  {
    return (EReference)synchronizedExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSynchronizedExpression_Expression()
  {
    return (EReference)synchronizedExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getModifierValue()
  {
    return modifierValueEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getVisibilityModifierValue()
  {
    return visibilityModifierValueEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getJMLSpecMemberModifier()
  {
    return jmlSpecMemberModifierEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getPrimitiveType()
  {
    return primitiveTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLFactory getJMLFactory()
  {
    return (JMLFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    compilationUnitEClass = createEClass(COMPILATION_UNIT);
    createEReference(compilationUnitEClass, COMPILATION_UNIT__PACKAGEDECLARATION);
    createEReference(compilationUnitEClass, COMPILATION_UNIT__IMPORTDECLARATION);
    createEReference(compilationUnitEClass, COMPILATION_UNIT__TYPEDECLARATION);

    packageDeclarationEClass = createEClass(PACKAGE_DECLARATION);
    createEAttribute(packageDeclarationEClass, PACKAGE_DECLARATION__QUALIFIEDNAME);

    importDeclarationEClass = createEClass(IMPORT_DECLARATION);
    createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__STATIC);
    createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__QUALIFIEDNAME);
    createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__WILDCARD);

    classifierDeclarationWithModifierEClass = createEClass(CLASSIFIER_DECLARATION_WITH_MODIFIER);
    createEReference(classifierDeclarationWithModifierEClass, CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION);

    classOrInterfaceDeclarationEClass = createEClass(CLASS_OR_INTERFACE_DECLARATION);

    modifierEClass = createEClass(MODIFIER);

    regularModifierEClass = createEClass(REGULAR_MODIFIER);
    createEAttribute(regularModifierEClass, REGULAR_MODIFIER__MODIFIER);

    classDeclarationEClass = createEClass(CLASS_DECLARATION);

    normalClassDeclarationEClass = createEClass(NORMAL_CLASS_DECLARATION);
    createEAttribute(normalClassDeclarationEClass, NORMAL_CLASS_DECLARATION__IDENTIFIER);
    createEReference(normalClassDeclarationEClass, NORMAL_CLASS_DECLARATION__TYPEPARAMETERS);
    createEReference(normalClassDeclarationEClass, NORMAL_CLASS_DECLARATION__SUPER_TYPE);
    createEReference(normalClassDeclarationEClass, NORMAL_CLASS_DECLARATION__IMPLEMENTED_TYPES);
    createEReference(normalClassDeclarationEClass, NORMAL_CLASS_DECLARATION__BODY_DECLARATIONS);

    typeParametersEClass = createEClass(TYPE_PARAMETERS);
    createEReference(typeParametersEClass, TYPE_PARAMETERS__TYPEPARAMETER);
    createEReference(typeParametersEClass, TYPE_PARAMETERS__TYPE);
    createEAttribute(typeParametersEClass, TYPE_PARAMETERS__IDENTIFIER);
    createEReference(typeParametersEClass, TYPE_PARAMETERS__PARAMETERS);
    createEReference(typeParametersEClass, TYPE_PARAMETERS__EXCEPTIONS);
    createEReference(typeParametersEClass, TYPE_PARAMETERS__METHODBODY);

    typeParameterEClass = createEClass(TYPE_PARAMETER);
    createEAttribute(typeParameterEClass, TYPE_PARAMETER__IDENTIFIER);
    createEReference(typeParameterEClass, TYPE_PARAMETER__TYPEBOUND);

    typeBoundEClass = createEClass(TYPE_BOUND);
    createEReference(typeBoundEClass, TYPE_BOUND__TYPE);

    enumDeclarationEClass = createEClass(ENUM_DECLARATION);
    createEReference(enumDeclarationEClass, ENUM_DECLARATION__IMPLEMENTED_TYPES);
    createEReference(enumDeclarationEClass, ENUM_DECLARATION__ENUMCONSTANTS);
    createEReference(enumDeclarationEClass, ENUM_DECLARATION__BODY_DECLARATIONS);

    enumConstantsEClass = createEClass(ENUM_CONSTANTS);
    createEReference(enumConstantsEClass, ENUM_CONSTANTS__ENUMCONSTANT);

    enumConstantEClass = createEClass(ENUM_CONSTANT);
    createEReference(enumConstantEClass, ENUM_CONSTANT__ANNOTATIONS);
    createEAttribute(enumConstantEClass, ENUM_CONSTANT__IDENTIFIER);
    createEReference(enumConstantEClass, ENUM_CONSTANT__ARGUMENTS);
    createEReference(enumConstantEClass, ENUM_CONSTANT__CLASSBODYDECLARATION);

    enumBodyDeclarationsEClass = createEClass(ENUM_BODY_DECLARATIONS);
    createEReference(enumBodyDeclarationsEClass, ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION);

    argumentsEClass = createEClass(ARGUMENTS);
    createEReference(argumentsEClass, ARGUMENTS__EXPRESSIONS);

    interfaceDeclarationEClass = createEClass(INTERFACE_DECLARATION);

    normalInterfaceDeclarationEClass = createEClass(NORMAL_INTERFACE_DECLARATION);
    createEAttribute(normalInterfaceDeclarationEClass, NORMAL_INTERFACE_DECLARATION__IDENTIFIER);
    createEReference(normalInterfaceDeclarationEClass, NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS);
    createEReference(normalInterfaceDeclarationEClass, NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES);
    createEReference(normalInterfaceDeclarationEClass, NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS);

    classBodyDeclarationEClass = createEClass(CLASS_BODY_DECLARATION);

    staticBlockEClass = createEClass(STATIC_BLOCK);
    createEAttribute(staticBlockEClass, STATIC_BLOCK__STATIC);
    createEReference(staticBlockEClass, STATIC_BLOCK__BLOCK);

    jmlSpecifiedElementEClass = createEClass(JML_SPECIFIED_ELEMENT);
    createEReference(jmlSpecifiedElementEClass, JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS);
    createEReference(jmlSpecifiedElementEClass, JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS);
    createEReference(jmlSpecifiedElementEClass, JML_SPECIFIED_ELEMENT__ELEMENT);

    jmlMultilineSpecEClass = createEClass(JML_MULTILINE_SPEC);
    createEReference(jmlMultilineSpecEClass, JML_MULTILINE_SPEC__MODEL_ELEMENT);

    jmlSinglelineSpecEClass = createEClass(JML_SINGLELINE_SPEC);

    jmlExpressionHavingEClass = createEClass(JML_EXPRESSION_HAVING);
    createEReference(jmlExpressionHavingEClass, JML_EXPRESSION_HAVING__EXPR);

    visiblityModifierEClass = createEClass(VISIBLITY_MODIFIER);
    createEAttribute(visiblityModifierEClass, VISIBLITY_MODIFIER__MODIFIER);

    jmlMethodSpecificationWithModifierEClass = createEClass(JML_METHOD_SPECIFICATION_WITH_MODIFIER);
    createEReference(jmlMethodSpecificationWithModifierEClass, JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER);
    createEReference(jmlMethodSpecificationWithModifierEClass, JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC);

    jmlMethodSpecificationWithModifierRegularEClass = createEClass(JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR);

    jmlMethodSpecificationWithModifierExtendedEClass = createEClass(JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED);

    jmlMethodSpecificationEClass = createEClass(JML_METHOD_SPECIFICATION);

    jmlMethodBehaviorEClass = createEClass(JML_METHOD_BEHAVIOR);
    createEReference(jmlMethodBehaviorEClass, JML_METHOD_BEHAVIOR__SPECIFICATIONS);

    jmlExceptionalBehaviorBlockEClass = createEClass(JML_EXCEPTIONAL_BEHAVIOR_BLOCK);

    jmlNormalBehaviorBlockEClass = createEClass(JML_NORMAL_BEHAVIOR_BLOCK);

    jmlBehaviorBlockEClass = createEClass(JML_BEHAVIOR_BLOCK);

    jmlMethodExpressionEClass = createEClass(JML_METHOD_EXPRESSION);

    jmlEnsuresExpressionEClass = createEClass(JML_ENSURES_EXPRESSION);

    jmlRequiresExpressionEClass = createEClass(JML_REQUIRES_EXPRESSION);

    jmlSpecificationOnlyElementWithModifierEClass = createEClass(JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER);
    createEReference(jmlSpecificationOnlyElementWithModifierEClass, JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER);
    createEReference(jmlSpecificationOnlyElementWithModifierEClass, JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT);

    jmlSpecificationOnlyElementEClass = createEClass(JML_SPECIFICATION_ONLY_ELEMENT);
    createEAttribute(jmlSpecificationOnlyElementEClass, JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE);
    createEReference(jmlSpecificationOnlyElementEClass, JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT);

    jmlModelElementEClass = createEClass(JML_MODEL_ELEMENT);

    jmlGhostElementEClass = createEClass(JML_GHOST_ELEMENT);

    jmlTypeExpressionWithModifierEClass = createEClass(JML_TYPE_EXPRESSION_WITH_MODIFIER);
    createEReference(jmlTypeExpressionWithModifierEClass, JML_TYPE_EXPRESSION_WITH_MODIFIER__MODIFIER);
    createEReference(jmlTypeExpressionWithModifierEClass, JML_TYPE_EXPRESSION_WITH_MODIFIER__SPEC);

    jmlTypeExpressionEClass = createEClass(JML_TYPE_EXPRESSION);

    jmlInvariantExpressionEClass = createEClass(JML_INVARIANT_EXPRESSION);

    jmlConstraintExpressionEClass = createEClass(JML_CONSTRAINT_EXPRESSION);

    jmlAxiomExpressionEClass = createEClass(JML_AXIOM_EXPRESSION);

    jmlMemberModifierEClass = createEClass(JML_MEMBER_MODIFIER);
    createEAttribute(jmlMemberModifierEClass, JML_MEMBER_MODIFIER__MODIFIER);

    memberDeclWithModifierEClass = createEClass(MEMBER_DECL_WITH_MODIFIER);
    createEReference(memberDeclWithModifierEClass, MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS);
    createEReference(memberDeclWithModifierEClass, MEMBER_DECL_WITH_MODIFIER__MEMBERDECL);

    memberDeclWithModifierRegularEClass = createEClass(MEMBER_DECL_WITH_MODIFIER_REGULAR);

    memberDeclWithModifierSpecEClass = createEClass(MEMBER_DECL_WITH_MODIFIER_SPEC);

    memberDeclEClass = createEClass(MEMBER_DECL);

    constructorEClass = createEClass(CONSTRUCTOR);
    createEAttribute(constructorEClass, CONSTRUCTOR__IDENTIFIER);
    createEReference(constructorEClass, CONSTRUCTOR__PARAMETERS);
    createEReference(constructorEClass, CONSTRUCTOR__EXCEPTIONS);
    createEReference(constructorEClass, CONSTRUCTOR__CONSTRUCTORBODY);

    memberDeclarationEClass = createEClass(MEMBER_DECLARATION);
    createEReference(memberDeclarationEClass, MEMBER_DECLARATION__METHOD);
    createEReference(memberDeclarationEClass, MEMBER_DECLARATION__FIELD);

    genericMethodOrConstructorDeclOldEClass = createEClass(GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD);
    createEReference(genericMethodOrConstructorDeclOldEClass, GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR);

    genericMethodOrConstructorDeclEClass = createEClass(GENERIC_METHOD_OR_CONSTRUCTOR_DECL);
    createEReference(genericMethodOrConstructorDeclEClass, GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS);
    createEReference(genericMethodOrConstructorDeclEClass, GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE);
    createEReference(genericMethodOrConstructorDeclEClass, GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD);
    createEReference(genericMethodOrConstructorDeclEClass, GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR);

    methodDeclarationEClass = createEClass(METHOD_DECLARATION);
    createEReference(methodDeclarationEClass, METHOD_DECLARATION__PARAMETERS);
    createEReference(methodDeclarationEClass, METHOD_DECLARATION__EXCEPTIONS);
    createEReference(methodDeclarationEClass, METHOD_DECLARATION__METHODBODY);

    fieldDeclarationEClass = createEClass(FIELD_DECLARATION);
    createEReference(fieldDeclarationEClass, FIELD_DECLARATION__VARIABLEDECLARATOR);

    declaredExceptionEClass = createEClass(DECLARED_EXCEPTION);
    createEAttribute(declaredExceptionEClass, DECLARED_EXCEPTION__NAME);

    variableDeclaratorEClass = createEClass(VARIABLE_DECLARATOR);
    createEReference(variableDeclaratorEClass, VARIABLE_DECLARATOR__BRACKETS);
    createEReference(variableDeclaratorEClass, VARIABLE_DECLARATOR__EXPRESSION);

    typeEClass = createEClass(TYPE);
    createEReference(typeEClass, TYPE__BRACKETS);

    classOrInterfaceTypeWithBracketsEClass = createEClass(CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS);
    createEReference(classOrInterfaceTypeWithBracketsEClass, CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS__TYPE);

    primitiveTypeWithBracketsEClass = createEClass(PRIMITIVE_TYPE_WITH_BRACKETS);
    createEAttribute(primitiveTypeWithBracketsEClass, PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE);

    bracketsEClass = createEClass(BRACKETS);

    classOrInterfaceTypeEClass = createEClass(CLASS_OR_INTERFACE_TYPE);
    createEReference(classOrInterfaceTypeEClass, CLASS_OR_INTERFACE_TYPE__TYPE);

    classifierTypeEClass = createEClass(CLASSIFIER_TYPE);
    createEAttribute(classifierTypeEClass, CLASSIFIER_TYPE__IDENTIFIER);
    createEReference(classifierTypeEClass, CLASSIFIER_TYPE__TYPEARGUMENTS);

    typeArgumentsEClass = createEClass(TYPE_ARGUMENTS);
    createEReference(typeArgumentsEClass, TYPE_ARGUMENTS__TYPEARGUMENT);

    typeArgumentEClass = createEClass(TYPE_ARGUMENT);
    createEReference(typeArgumentEClass, TYPE_ARGUMENT__TYPE);
    createEAttribute(typeArgumentEClass, TYPE_ARGUMENT__WILDCARD);
    createEAttribute(typeArgumentEClass, TYPE_ARGUMENT__EXTENDS);
    createEAttribute(typeArgumentEClass, TYPE_ARGUMENT__SUPER);

    formalParameterDeclEClass = createEClass(FORMAL_PARAMETER_DECL);
    createEAttribute(formalParameterDeclEClass, FORMAL_PARAMETER_DECL__VARARGS);
    createEAttribute(formalParameterDeclEClass, FORMAL_PARAMETER_DECL__IDENTIFIER);

    methodBodyEClass = createEClass(METHOD_BODY);

    constructorBodyEClass = createEClass(CONSTRUCTOR_BODY);
    createEReference(constructorBodyEClass, CONSTRUCTOR_BODY__BLOCKSTATEMENT);

    modifiableEClass = createEClass(MODIFIABLE);
    createEReference(modifiableEClass, MODIFIABLE__MODIFIERS);

    typedEClass = createEClass(TYPED);
    createEReference(typedEClass, TYPED__TYPE);

    annotationsEClass = createEClass(ANNOTATIONS);
    createEReference(annotationsEClass, ANNOTATIONS__ANNOTATION);

    annotationEClass = createEClass(ANNOTATION);
    createEAttribute(annotationEClass, ANNOTATION__ANNOTATIONNAME);
    createEReference(annotationEClass, ANNOTATION__ELEMENTVALUEPAIRS);
    createEReference(annotationEClass, ANNOTATION__ELEMENTVALUE);

    elementValuePairsEClass = createEClass(ELEMENT_VALUE_PAIRS);
    createEReference(elementValuePairsEClass, ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR);

    elementValuePairEClass = createEClass(ELEMENT_VALUE_PAIR);
    createEAttribute(elementValuePairEClass, ELEMENT_VALUE_PAIR__IDENTIFIER);
    createEReference(elementValuePairEClass, ELEMENT_VALUE_PAIR__ELEMENTVALUE);

    elementValueEClass = createEClass(ELEMENT_VALUE);

    elementValueArrayInitializerEClass = createEClass(ELEMENT_VALUE_ARRAY_INITIALIZER);
    createEReference(elementValueArrayInitializerEClass, ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE);

    annotationTypeDeclarationEClass = createEClass(ANNOTATION_TYPE_DECLARATION);
    createEAttribute(annotationTypeDeclarationEClass, ANNOTATION_TYPE_DECLARATION__IDENTIFIER);
    createEReference(annotationTypeDeclarationEClass, ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION);

    annotationTypeElementDeclarationEClass = createEClass(ANNOTATION_TYPE_ELEMENT_DECLARATION);
    createEReference(annotationTypeElementDeclarationEClass, ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST);

    annotationTypeElementRestEClass = createEClass(ANNOTATION_TYPE_ELEMENT_REST);

    annotationMethodOrConstantRestEClass = createEClass(ANNOTATION_METHOD_OR_CONSTANT_REST);
    createEReference(annotationMethodOrConstantRestEClass, ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD);
    createEReference(annotationMethodOrConstantRestEClass, ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT);

    annotationMethodRestEClass = createEClass(ANNOTATION_METHOD_REST);
    createEAttribute(annotationMethodRestEClass, ANNOTATION_METHOD_REST__IDENTIFIER);
    createEReference(annotationMethodRestEClass, ANNOTATION_METHOD_REST__DEFAULTVALUE);

    annotationConstantRestEClass = createEClass(ANNOTATION_CONSTANT_REST);
    createEReference(annotationConstantRestEClass, ANNOTATION_CONSTANT_REST__VARIABLEDECLARATOR);

    defaultValueEClass = createEClass(DEFAULT_VALUE);
    createEReference(defaultValueEClass, DEFAULT_VALUE__ELEMENTVALUE);

    blockEClass = createEClass(BLOCK);
    createEReference(blockEClass, BLOCK__BLOCKSTATEMENT);

    blockStatementEClass = createEClass(BLOCK_STATEMENT);

    identifierHavingEClass = createEClass(IDENTIFIER_HAVING);
    createEAttribute(identifierHavingEClass, IDENTIFIER_HAVING__IDENTIFIER);

    expressionEClass = createEClass(EXPRESSION);
    createEReference(expressionEClass, EXPRESSION__TYPE);
    createEAttribute(expressionEClass, EXPRESSION__NAME);
    createEReference(expressionEClass, EXPRESSION__RIGHT);

    parenthesisExpressionEClass = createEClass(PARENTHESIS_EXPRESSION);
    createEReference(parenthesisExpressionEClass, PARENTHESIS_EXPRESSION__EXPR);

    collectionLiteralEClass = createEClass(COLLECTION_LITERAL);
    createEReference(collectionLiteralEClass, COLLECTION_LITERAL__ELEMENTS);

    setLiteralEClass = createEClass(SET_LITERAL);

    listLiteralEClass = createEClass(LIST_LITERAL);

    casePartEClass = createEClass(CASE_PART);
    createEReference(casePartEClass, CASE_PART__TYPE_GUARD);
    createEReference(casePartEClass, CASE_PART__CASE);
    createEReference(casePartEClass, CASE_PART__THEN);

    validIDEClass = createEClass(VALID_ID);
    createEAttribute(validIDEClass, VALID_ID__PARAMETER_TYPE);
    createEAttribute(validIDEClass, VALID_ID__NAME);

    catchClauseEClass = createEClass(CATCH_CLAUSE);
    createEReference(catchClauseEClass, CATCH_CLAUSE__DECLARED_PARAM);
    createEReference(catchClauseEClass, CATCH_CLAUSE__EXPRESSION);

    assignmentEClass = createEClass(ASSIGNMENT);
    createEAttribute(assignmentEClass, ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION);
    createEAttribute(assignmentEClass, ASSIGNMENT__FEATURE);
    createEReference(assignmentEClass, ASSIGNMENT__VALUE);
    createEReference(assignmentEClass, ASSIGNMENT__ASSIGNABLE);

    binaryOperationEClass = createEClass(BINARY_OPERATION);
    createEReference(binaryOperationEClass, BINARY_OPERATION__LEFT_OPERAND);
    createEAttribute(binaryOperationEClass, BINARY_OPERATION__FEATURE);
    createEReference(binaryOperationEClass, BINARY_OPERATION__RIGHT_OPERAND);

    instanceOfExpressionEClass = createEClass(INSTANCE_OF_EXPRESSION);
    createEReference(instanceOfExpressionEClass, INSTANCE_OF_EXPRESSION__EXPRESSION);

    unaryOperationEClass = createEClass(UNARY_OPERATION);
    createEAttribute(unaryOperationEClass, UNARY_OPERATION__FEATURE);
    createEReference(unaryOperationEClass, UNARY_OPERATION__OPERAND);

    postfixOperationEClass = createEClass(POSTFIX_OPERATION);
    createEReference(postfixOperationEClass, POSTFIX_OPERATION__OPERAND);
    createEAttribute(postfixOperationEClass, POSTFIX_OPERATION__FEATURE);

    memberFeatureCallEClass = createEClass(MEMBER_FEATURE_CALL);
    createEReference(memberFeatureCallEClass, MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET);
    createEAttribute(memberFeatureCallEClass, MEMBER_FEATURE_CALL__NULL_SAFE);
    createEAttribute(memberFeatureCallEClass, MEMBER_FEATURE_CALL__EXPLICIT_STATIC);
    createEReference(memberFeatureCallEClass, MEMBER_FEATURE_CALL__TYPE_ARGUMENTS);
    createEAttribute(memberFeatureCallEClass, MEMBER_FEATURE_CALL__FEATURE);
    createEAttribute(memberFeatureCallEClass, MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL);
    createEReference(memberFeatureCallEClass, MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS);

    jmlOldExpressionEClass = createEClass(JML_OLD_EXPRESSION);
    createEReference(jmlOldExpressionEClass, JML_OLD_EXPRESSION__EXPR);

    jmlFreshExpressionEClass = createEClass(JML_FRESH_EXPRESSION);
    createEReference(jmlFreshExpressionEClass, JML_FRESH_EXPRESSION__EXPR);

    jmlResultExpressionEClass = createEClass(JML_RESULT_EXPRESSION);

    jmlForAllExpressionEClass = createEClass(JML_FOR_ALL_EXPRESSION);
    createEReference(jmlForAllExpressionEClass, JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS);
    createEReference(jmlForAllExpressionEClass, JML_FOR_ALL_EXPRESSION__EXPRESSION);
    createEReference(jmlForAllExpressionEClass, JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS);

    closureEClass = createEClass(CLOSURE);
    createEReference(closureEClass, CLOSURE__DECLARED_FORMAL_PARAMETERS);
    createEAttribute(closureEClass, CLOSURE__EXPLICIT_SYNTAX);
    createEReference(closureEClass, CLOSURE__EXPRESSION);

    blockExpressionEClass = createEClass(BLOCK_EXPRESSION);
    createEReference(blockExpressionEClass, BLOCK_EXPRESSION__EXPRESSIONS);

    ifExpressionEClass = createEClass(IF_EXPRESSION);
    createEReference(ifExpressionEClass, IF_EXPRESSION__IF);
    createEReference(ifExpressionEClass, IF_EXPRESSION__THEN);
    createEReference(ifExpressionEClass, IF_EXPRESSION__ELSE);

    switchExpressionEClass = createEClass(SWITCH_EXPRESSION);
    createEReference(switchExpressionEClass, SWITCH_EXPRESSION__DECLARED_PARAM);
    createEReference(switchExpressionEClass, SWITCH_EXPRESSION__SWITCH);
    createEReference(switchExpressionEClass, SWITCH_EXPRESSION__CASES);
    createEReference(switchExpressionEClass, SWITCH_EXPRESSION__DEFAULT);

    forLoopExpressionEClass = createEClass(FOR_LOOP_EXPRESSION);
    createEReference(forLoopExpressionEClass, FOR_LOOP_EXPRESSION__DECLARED_PARAM);
    createEReference(forLoopExpressionEClass, FOR_LOOP_EXPRESSION__FOR_EXPRESSION);
    createEReference(forLoopExpressionEClass, FOR_LOOP_EXPRESSION__EACH_EXPRESSION);

    basicForLoopExpressionEClass = createEClass(BASIC_FOR_LOOP_EXPRESSION);
    createEReference(basicForLoopExpressionEClass, BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS);
    createEReference(basicForLoopExpressionEClass, BASIC_FOR_LOOP_EXPRESSION__EXPRESSION);
    createEReference(basicForLoopExpressionEClass, BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS);
    createEReference(basicForLoopExpressionEClass, BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION);

    whileExpressionEClass = createEClass(WHILE_EXPRESSION);
    createEReference(whileExpressionEClass, WHILE_EXPRESSION__PREDICATE);
    createEReference(whileExpressionEClass, WHILE_EXPRESSION__BODY);

    doWhileExpressionEClass = createEClass(DO_WHILE_EXPRESSION);
    createEReference(doWhileExpressionEClass, DO_WHILE_EXPRESSION__BODY);
    createEReference(doWhileExpressionEClass, DO_WHILE_EXPRESSION__PREDICATE);

    featureCallEClass = createEClass(FEATURE_CALL);
    createEReference(featureCallEClass, FEATURE_CALL__TYPE_ARGUMENTS);
    createEAttribute(featureCallEClass, FEATURE_CALL__FEATURE);
    createEAttribute(featureCallEClass, FEATURE_CALL__EXPLICIT_OPERATION_CALL);
    createEReference(featureCallEClass, FEATURE_CALL__FEATURE_CALL_ARGUMENTS);

    constructorCallEClass = createEClass(CONSTRUCTOR_CALL);
    createEAttribute(constructorCallEClass, CONSTRUCTOR_CALL__CONSTRUCTOR);
    createEReference(constructorCallEClass, CONSTRUCTOR_CALL__TYPE_ARGUMENTS);
    createEAttribute(constructorCallEClass, CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL);
    createEReference(constructorCallEClass, CONSTRUCTOR_CALL__ARGUMENTS);

    booleanLiteralEClass = createEClass(BOOLEAN_LITERAL);
    createEAttribute(booleanLiteralEClass, BOOLEAN_LITERAL__IS_TRUE);

    nullLiteralEClass = createEClass(NULL_LITERAL);

    numberLiteralEClass = createEClass(NUMBER_LITERAL);
    createEAttribute(numberLiteralEClass, NUMBER_LITERAL__VALUE);

    stringLiteralEClass = createEClass(STRING_LITERAL);
    createEAttribute(stringLiteralEClass, STRING_LITERAL__VALUE);

    charLiteralEClass = createEClass(CHAR_LITERAL);
    createEAttribute(charLiteralEClass, CHAR_LITERAL__VALUE);

    throwExpressionEClass = createEClass(THROW_EXPRESSION);
    createEReference(throwExpressionEClass, THROW_EXPRESSION__EXPRESSION);

    returnExpressionEClass = createEClass(RETURN_EXPRESSION);
    createEReference(returnExpressionEClass, RETURN_EXPRESSION__EXPRESSION);

    tryCatchFinallyExpressionEClass = createEClass(TRY_CATCH_FINALLY_EXPRESSION);
    createEReference(tryCatchFinallyExpressionEClass, TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION);
    createEReference(tryCatchFinallyExpressionEClass, TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES);
    createEReference(tryCatchFinallyExpressionEClass, TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION);

    synchronizedExpressionEClass = createEClass(SYNCHRONIZED_EXPRESSION);
    createEReference(synchronizedExpressionEClass, SYNCHRONIZED_EXPRESSION__PARAM);
    createEReference(synchronizedExpressionEClass, SYNCHRONIZED_EXPRESSION__EXPRESSION);

    // Create enums
    modifierValueEEnum = createEEnum(MODIFIER_VALUE);
    visibilityModifierValueEEnum = createEEnum(VISIBILITY_MODIFIER_VALUE);
    jmlSpecMemberModifierEEnum = createEEnum(JML_SPEC_MEMBER_MODIFIER);
    primitiveTypeEEnum = createEEnum(PRIMITIVE_TYPE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    classifierDeclarationWithModifierEClass.getESuperTypes().add(this.getModifiable());
    classifierDeclarationWithModifierEClass.getESuperTypes().add(this.getBlockStatement());
    regularModifierEClass.getESuperTypes().add(this.getModifier());
    classDeclarationEClass.getESuperTypes().add(this.getClassOrInterfaceDeclaration());
    classDeclarationEClass.getESuperTypes().add(this.getMemberDecl());
    normalClassDeclarationEClass.getESuperTypes().add(this.getClassDeclaration());
    normalClassDeclarationEClass.getESuperTypes().add(this.getAnnotationTypeElementRest());
    typeParametersEClass.getESuperTypes().add(this.getGenericMethodOrConstructorDeclOld());
    enumDeclarationEClass.getESuperTypes().add(this.getClassDeclaration());
    enumDeclarationEClass.getESuperTypes().add(this.getAnnotationTypeElementRest());
    enumDeclarationEClass.getESuperTypes().add(this.getIdentifierHaving());
    interfaceDeclarationEClass.getESuperTypes().add(this.getClassOrInterfaceDeclaration());
    interfaceDeclarationEClass.getESuperTypes().add(this.getMemberDecl());
    normalInterfaceDeclarationEClass.getESuperTypes().add(this.getInterfaceDeclaration());
    normalInterfaceDeclarationEClass.getESuperTypes().add(this.getAnnotationTypeElementRest());
    staticBlockEClass.getESuperTypes().add(this.getClassBodyDeclaration());
    jmlSpecifiedElementEClass.getESuperTypes().add(this.getClassBodyDeclaration());
    jmlMultilineSpecEClass.getESuperTypes().add(this.getJMLSpecifiedElement());
    jmlSinglelineSpecEClass.getESuperTypes().add(this.getJMLSpecifiedElement());
    jmlMethodSpecificationWithModifierRegularEClass.getESuperTypes().add(this.getJMLMethodSpecificationWithModifier());
    jmlMethodSpecificationWithModifierExtendedEClass.getESuperTypes().add(this.getJMLMethodSpecificationWithModifier());
    jmlMethodBehaviorEClass.getESuperTypes().add(this.getJMLMethodSpecification());
    jmlExceptionalBehaviorBlockEClass.getESuperTypes().add(this.getJMLMethodBehavior());
    jmlNormalBehaviorBlockEClass.getESuperTypes().add(this.getJMLMethodBehavior());
    jmlBehaviorBlockEClass.getESuperTypes().add(this.getJMLMethodBehavior());
    jmlMethodExpressionEClass.getESuperTypes().add(this.getJMLExpressionHaving());
    jmlMethodExpressionEClass.getESuperTypes().add(this.getJMLMethodSpecification());
    jmlEnsuresExpressionEClass.getESuperTypes().add(this.getJMLMethodExpression());
    jmlRequiresExpressionEClass.getESuperTypes().add(this.getJMLMethodExpression());
    jmlModelElementEClass.getESuperTypes().add(this.getJMLSpecificationOnlyElement());
    jmlGhostElementEClass.getESuperTypes().add(this.getJMLSpecificationOnlyElement());
    jmlTypeExpressionEClass.getESuperTypes().add(this.getJMLExpressionHaving());
    jmlInvariantExpressionEClass.getESuperTypes().add(this.getJMLTypeExpression());
    jmlConstraintExpressionEClass.getESuperTypes().add(this.getJMLTypeExpression());
    jmlAxiomExpressionEClass.getESuperTypes().add(this.getJMLTypeExpression());
    memberDeclWithModifierEClass.getESuperTypes().add(this.getModifiable());
    memberDeclWithModifierRegularEClass.getESuperTypes().add(this.getMemberDeclWithModifier());
    memberDeclWithModifierSpecEClass.getESuperTypes().add(this.getMemberDeclWithModifier());
    constructorEClass.getESuperTypes().add(this.getMemberDecl());
    memberDeclarationEClass.getESuperTypes().add(this.getMemberDecl());
    memberDeclarationEClass.getESuperTypes().add(this.getTyped());
    genericMethodOrConstructorDeclEClass.getESuperTypes().add(this.getMemberDecl());
    methodDeclarationEClass.getESuperTypes().add(this.getIdentifierHaving());
    variableDeclaratorEClass.getESuperTypes().add(this.getIdentifierHaving());
    classOrInterfaceTypeWithBracketsEClass.getESuperTypes().add(this.getType());
    primitiveTypeWithBracketsEClass.getESuperTypes().add(this.getType());
    formalParameterDeclEClass.getESuperTypes().add(this.getModifiable());
    formalParameterDeclEClass.getESuperTypes().add(this.getTyped());
    annotationEClass.getESuperTypes().add(this.getModifier());
    annotationEClass.getESuperTypes().add(this.getElementValue());
    elementValueArrayInitializerEClass.getESuperTypes().add(this.getElementValue());
    annotationTypeDeclarationEClass.getESuperTypes().add(this.getInterfaceDeclaration());
    annotationTypeDeclarationEClass.getESuperTypes().add(this.getAnnotationTypeElementRest());
    annotationTypeElementDeclarationEClass.getESuperTypes().add(this.getModifiable());
    annotationMethodOrConstantRestEClass.getESuperTypes().add(this.getTyped());
    annotationMethodOrConstantRestEClass.getESuperTypes().add(this.getAnnotationTypeElementRest());
    blockEClass.getESuperTypes().add(this.getMethodBody());
    expressionEClass.getESuperTypes().add(this.getBlockStatement());
    parenthesisExpressionEClass.getESuperTypes().add(this.getExpression());
    collectionLiteralEClass.getESuperTypes().add(this.getExpression());
    setLiteralEClass.getESuperTypes().add(this.getCollectionLiteral());
    listLiteralEClass.getESuperTypes().add(this.getCollectionLiteral());
    assignmentEClass.getESuperTypes().add(this.getExpression());
    binaryOperationEClass.getESuperTypes().add(this.getExpression());
    instanceOfExpressionEClass.getESuperTypes().add(this.getExpression());
    unaryOperationEClass.getESuperTypes().add(this.getExpression());
    postfixOperationEClass.getESuperTypes().add(this.getExpression());
    memberFeatureCallEClass.getESuperTypes().add(this.getExpression());
    jmlOldExpressionEClass.getESuperTypes().add(this.getExpression());
    jmlFreshExpressionEClass.getESuperTypes().add(this.getExpression());
    jmlResultExpressionEClass.getESuperTypes().add(this.getExpression());
    jmlForAllExpressionEClass.getESuperTypes().add(this.getExpression());
    closureEClass.getESuperTypes().add(this.getExpression());
    blockExpressionEClass.getESuperTypes().add(this.getExpression());
    ifExpressionEClass.getESuperTypes().add(this.getExpression());
    switchExpressionEClass.getESuperTypes().add(this.getExpression());
    forLoopExpressionEClass.getESuperTypes().add(this.getExpression());
    basicForLoopExpressionEClass.getESuperTypes().add(this.getExpression());
    whileExpressionEClass.getESuperTypes().add(this.getExpression());
    doWhileExpressionEClass.getESuperTypes().add(this.getExpression());
    featureCallEClass.getESuperTypes().add(this.getExpression());
    constructorCallEClass.getESuperTypes().add(this.getExpression());
    booleanLiteralEClass.getESuperTypes().add(this.getExpression());
    nullLiteralEClass.getESuperTypes().add(this.getExpression());
    numberLiteralEClass.getESuperTypes().add(this.getExpression());
    stringLiteralEClass.getESuperTypes().add(this.getExpression());
    charLiteralEClass.getESuperTypes().add(this.getExpression());
    throwExpressionEClass.getESuperTypes().add(this.getExpression());
    returnExpressionEClass.getESuperTypes().add(this.getExpression());
    tryCatchFinallyExpressionEClass.getESuperTypes().add(this.getExpression());
    synchronizedExpressionEClass.getESuperTypes().add(this.getExpression());

    // Initialize classes and features; add operations and parameters
    initEClass(compilationUnitEClass, CompilationUnit.class, "CompilationUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCompilationUnit_Packagedeclaration(), this.getPackageDeclaration(), null, "packagedeclaration", null, 0, 1, CompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCompilationUnit_Importdeclaration(), this.getImportDeclaration(), null, "importdeclaration", null, 0, -1, CompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCompilationUnit_Typedeclaration(), this.getClassifierDeclarationWithModifier(), null, "typedeclaration", null, 0, -1, CompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    addEOperation(compilationUnitEClass, ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(packageDeclarationEClass, PackageDeclaration.class, "PackageDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPackageDeclaration_Qualifiedname(), ecorePackage.getEString(), "qualifiedname", null, 0, 1, PackageDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(importDeclarationEClass, ImportDeclaration.class, "ImportDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getImportDeclaration_Static(), ecorePackage.getEBoolean(), "static", null, 0, 1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getImportDeclaration_Qualifiedname(), ecorePackage.getEString(), "qualifiedname", null, 0, 1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getImportDeclaration_Wildcard(), ecorePackage.getEBoolean(), "wildcard", null, 0, 1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classifierDeclarationWithModifierEClass, ClassifierDeclarationWithModifier.class, "ClassifierDeclarationWithModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getClassifierDeclarationWithModifier_ClassOrInterfaceDeclaration(), this.getClassOrInterfaceDeclaration(), null, "classOrInterfaceDeclaration", null, 0, 1, ClassifierDeclarationWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classOrInterfaceDeclarationEClass, ClassOrInterfaceDeclaration.class, "ClassOrInterfaceDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(modifierEClass, Modifier.class, "Modifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(regularModifierEClass, RegularModifier.class, "RegularModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRegularModifier_Modifier(), this.getModifierValue(), "modifier", null, 0, 1, RegularModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classDeclarationEClass, ClassDeclaration.class, "ClassDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(normalClassDeclarationEClass, NormalClassDeclaration.class, "NormalClassDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNormalClassDeclaration_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, NormalClassDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalClassDeclaration_Typeparameters(), this.getTypeParameters(), null, "typeparameters", null, 0, 1, NormalClassDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalClassDeclaration_SuperType(), this.getType(), null, "superType", null, 0, 1, NormalClassDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalClassDeclaration_ImplementedTypes(), this.getType(), null, "implementedTypes", null, 0, -1, NormalClassDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalClassDeclaration_BodyDeclarations(), this.getClassBodyDeclaration(), null, "bodyDeclarations", null, 0, -1, NormalClassDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeParametersEClass, TypeParameters.class, "TypeParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTypeParameters_Typeparameter(), this.getTypeParameter(), null, "typeparameter", null, 0, -1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTypeParameters_Type(), this.getType(), null, "type", null, 0, 1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTypeParameters_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTypeParameters_Parameters(), this.getFormalParameterDecl(), null, "parameters", null, 0, -1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTypeParameters_Exceptions(), this.getDeclaredException(), null, "exceptions", null, 0, -1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTypeParameters_Methodbody(), this.getMethodBody(), null, "methodbody", null, 0, 1, TypeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeParameterEClass, TypeParameter.class, "TypeParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTypeParameter_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, TypeParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTypeParameter_Typebound(), this.getTypeBound(), null, "typebound", null, 0, 1, TypeParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeBoundEClass, TypeBound.class, "TypeBound", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTypeBound_Type(), this.getType(), null, "type", null, 0, -1, TypeBound.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(enumDeclarationEClass, EnumDeclaration.class, "EnumDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEnumDeclaration_ImplementedTypes(), this.getType(), null, "implementedTypes", null, 0, -1, EnumDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnumDeclaration_Enumconstants(), this.getEnumConstants(), null, "enumconstants", null, 0, 1, EnumDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnumDeclaration_BodyDeclarations(), this.getEnumBodyDeclarations(), null, "bodyDeclarations", null, 0, 1, EnumDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(enumConstantsEClass, EnumConstants.class, "EnumConstants", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEnumConstants_Enumconstant(), this.getEnumConstant(), null, "enumconstant", null, 0, -1, EnumConstants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(enumConstantEClass, EnumConstant.class, "EnumConstant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEnumConstant_Annotations(), this.getAnnotations(), null, "annotations", null, 0, 1, EnumConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnumConstant_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, EnumConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnumConstant_Arguments(), this.getArguments(), null, "arguments", null, 0, 1, EnumConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnumConstant_Classbodydeclaration(), this.getClassBodyDeclaration(), null, "classbodydeclaration", null, 0, -1, EnumConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(enumBodyDeclarationsEClass, EnumBodyDeclarations.class, "EnumBodyDeclarations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEnumBodyDeclarations_Classbodydeclaration(), this.getClassBodyDeclaration(), null, "classbodydeclaration", null, 0, -1, EnumBodyDeclarations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(argumentsEClass, Arguments.class, "Arguments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getArguments_Expressions(), this.getExpression(), null, "expressions", null, 0, -1, Arguments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(interfaceDeclarationEClass, InterfaceDeclaration.class, "InterfaceDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(normalInterfaceDeclarationEClass, NormalInterfaceDeclaration.class, "NormalInterfaceDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNormalInterfaceDeclaration_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, NormalInterfaceDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalInterfaceDeclaration_Typeparameters(), this.getTypeParameters(), null, "typeparameters", null, 0, 1, NormalInterfaceDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalInterfaceDeclaration_ImplementedTypes(), this.getType(), null, "implementedTypes", null, 0, -1, NormalInterfaceDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNormalInterfaceDeclaration_BodyDeclarations(), this.getClassBodyDeclaration(), null, "bodyDeclarations", null, 0, -1, NormalInterfaceDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classBodyDeclarationEClass, ClassBodyDeclaration.class, "ClassBodyDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(staticBlockEClass, StaticBlock.class, "StaticBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getStaticBlock_Static(), ecorePackage.getEBoolean(), "static", null, 0, 1, StaticBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getStaticBlock_Block(), this.getBlock(), null, "block", null, 0, 1, StaticBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlSpecifiedElementEClass, JMLSpecifiedElement.class, "JMLSpecifiedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLSpecifiedElement_JmlTypeSpecifications(), this.getJMLTypeExpressionWithModifier(), null, "jmlTypeSpecifications", null, 0, -1, JMLSpecifiedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLSpecifiedElement_JmlSpecifications(), this.getJMLMethodSpecificationWithModifier(), null, "jmlSpecifications", null, 0, -1, JMLSpecifiedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLSpecifiedElement_Element(), this.getMemberDeclWithModifierRegular(), null, "element", null, 0, 1, JMLSpecifiedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlMultilineSpecEClass, JMLMultilineSpec.class, "JMLMultilineSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLMultilineSpec_ModelElement(), this.getJMLSpecificationOnlyElementWithModifier(), null, "modelElement", null, 0, 1, JMLMultilineSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlSinglelineSpecEClass, JMLSinglelineSpec.class, "JMLSinglelineSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlExpressionHavingEClass, JMLExpressionHaving.class, "JMLExpressionHaving", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLExpressionHaving_Expr(), this.getExpression(), null, "expr", null, 0, 1, JMLExpressionHaving.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(visiblityModifierEClass, VisiblityModifier.class, "VisiblityModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVisiblityModifier_Modifier(), this.getVisibilityModifierValue(), "modifier", null, 0, 1, VisiblityModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlMethodSpecificationWithModifierEClass, JMLMethodSpecificationWithModifier.class, "JMLMethodSpecificationWithModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLMethodSpecificationWithModifier_Modifier(), this.getVisiblityModifier(), null, "modifier", null, 0, -1, JMLMethodSpecificationWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLMethodSpecificationWithModifier_Spec(), this.getJMLMethodSpecification(), null, "spec", null, 0, 1, JMLMethodSpecificationWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlMethodSpecificationWithModifierRegularEClass, JMLMethodSpecificationWithModifierRegular.class, "JMLMethodSpecificationWithModifierRegular", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlMethodSpecificationWithModifierExtendedEClass, JMLMethodSpecificationWithModifierExtended.class, "JMLMethodSpecificationWithModifierExtended", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlMethodSpecificationEClass, JMLMethodSpecification.class, "JMLMethodSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlMethodBehaviorEClass, JMLMethodBehavior.class, "JMLMethodBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLMethodBehavior_Specifications(), this.getJMLMethodExpression(), null, "specifications", null, 0, -1, JMLMethodBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlExceptionalBehaviorBlockEClass, JMLExceptionalBehaviorBlock.class, "JMLExceptionalBehaviorBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlNormalBehaviorBlockEClass, JMLNormalBehaviorBlock.class, "JMLNormalBehaviorBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlBehaviorBlockEClass, JMLBehaviorBlock.class, "JMLBehaviorBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlMethodExpressionEClass, JMLMethodExpression.class, "JMLMethodExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlEnsuresExpressionEClass, JMLEnsuresExpression.class, "JMLEnsuresExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlRequiresExpressionEClass, JMLRequiresExpression.class, "JMLRequiresExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlSpecificationOnlyElementWithModifierEClass, JMLSpecificationOnlyElementWithModifier.class, "JMLSpecificationOnlyElementWithModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLSpecificationOnlyElementWithModifier_Modifier(), this.getVisiblityModifier(), null, "modifier", null, 0, -1, JMLSpecificationOnlyElementWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLSpecificationOnlyElementWithModifier_Element(), this.getJMLSpecificationOnlyElement(), null, "element", null, 0, 1, JMLSpecificationOnlyElementWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlSpecificationOnlyElementEClass, JMLSpecificationOnlyElement.class, "JMLSpecificationOnlyElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getJMLSpecificationOnlyElement_Instance(), ecorePackage.getEBoolean(), "instance", null, 0, 1, JMLSpecificationOnlyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLSpecificationOnlyElement_Element(), this.getMemberDeclWithModifierSpec(), null, "element", null, 0, 1, JMLSpecificationOnlyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlModelElementEClass, JMLModelElement.class, "JMLModelElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlGhostElementEClass, JMLGhostElement.class, "JMLGhostElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlTypeExpressionWithModifierEClass, JMLTypeExpressionWithModifier.class, "JMLTypeExpressionWithModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLTypeExpressionWithModifier_Modifier(), this.getVisiblityModifier(), null, "modifier", null, 0, -1, JMLTypeExpressionWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLTypeExpressionWithModifier_Spec(), this.getJMLTypeExpression(), null, "spec", null, 0, 1, JMLTypeExpressionWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlTypeExpressionEClass, JMLTypeExpression.class, "JMLTypeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlInvariantExpressionEClass, JMLInvariantExpression.class, "JMLInvariantExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlConstraintExpressionEClass, JMLConstraintExpression.class, "JMLConstraintExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlAxiomExpressionEClass, JMLAxiomExpression.class, "JMLAxiomExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlMemberModifierEClass, JMLMemberModifier.class, "JMLMemberModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getJMLMemberModifier_Modifier(), this.getJMLSpecMemberModifier(), "modifier", null, 0, 1, JMLMemberModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(memberDeclWithModifierEClass, MemberDeclWithModifier.class, "MemberDeclWithModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMemberDeclWithModifier_JmlModifiers(), this.getJMLMemberModifier(), null, "jmlModifiers", null, 0, -1, MemberDeclWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMemberDeclWithModifier_Memberdecl(), this.getMemberDecl(), null, "memberdecl", null, 0, 1, MemberDeclWithModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(memberDeclWithModifierRegularEClass, MemberDeclWithModifierRegular.class, "MemberDeclWithModifierRegular", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(memberDeclWithModifierSpecEClass, MemberDeclWithModifierSpec.class, "MemberDeclWithModifierSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(memberDeclEClass, MemberDecl.class, "MemberDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(constructorEClass, Constructor.class, "Constructor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstructor_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConstructor_Parameters(), this.getFormalParameterDecl(), null, "parameters", null, 0, -1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConstructor_Exceptions(), this.getDeclaredException(), null, "exceptions", null, 0, -1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConstructor_Constructorbody(), this.getConstructorBody(), null, "constructorbody", null, 0, 1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(memberDeclarationEClass, MemberDeclaration.class, "MemberDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMemberDeclaration_Method(), this.getMethodDeclaration(), null, "method", null, 0, 1, MemberDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMemberDeclaration_Field(), this.getFieldDeclaration(), null, "field", null, 0, 1, MemberDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(genericMethodOrConstructorDeclOldEClass, GenericMethodOrConstructorDeclOld.class, "GenericMethodOrConstructorDeclOld", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGenericMethodOrConstructorDeclOld_Constructor(), this.getConstructor(), null, "constructor", null, 0, 1, GenericMethodOrConstructorDeclOld.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(genericMethodOrConstructorDeclEClass, GenericMethodOrConstructorDecl.class, "GenericMethodOrConstructorDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGenericMethodOrConstructorDecl_TypeParameters(), this.getTypeParameters(), null, "typeParameters", null, 0, 1, GenericMethodOrConstructorDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGenericMethodOrConstructorDecl_Type(), this.getType(), null, "type", null, 0, 1, GenericMethodOrConstructorDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGenericMethodOrConstructorDecl_Method(), this.getMethodDeclaration(), null, "method", null, 0, 1, GenericMethodOrConstructorDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGenericMethodOrConstructorDecl_Constructor(), this.getConstructor(), null, "constructor", null, 0, 1, GenericMethodOrConstructorDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(methodDeclarationEClass, MethodDeclaration.class, "MethodDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMethodDeclaration_Parameters(), this.getFormalParameterDecl(), null, "parameters", null, 0, -1, MethodDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMethodDeclaration_Exceptions(), this.getDeclaredException(), null, "exceptions", null, 0, -1, MethodDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMethodDeclaration_Methodbody(), this.getMethodBody(), null, "methodbody", null, 0, 1, MethodDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fieldDeclarationEClass, FieldDeclaration.class, "FieldDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFieldDeclaration_Variabledeclarator(), this.getVariableDeclarator(), null, "variabledeclarator", null, 0, -1, FieldDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(declaredExceptionEClass, DeclaredException.class, "DeclaredException", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDeclaredException_Name(), ecorePackage.getEString(), "name", null, 0, 1, DeclaredException.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(variableDeclaratorEClass, VariableDeclarator.class, "VariableDeclarator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVariableDeclarator_Brackets(), this.getBrackets(), null, "brackets", null, 0, -1, VariableDeclarator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVariableDeclarator_Expression(), this.getExpression(), null, "expression", null, 0, 1, VariableDeclarator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getType_Brackets(), this.getBrackets(), null, "brackets", null, 0, -1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classOrInterfaceTypeWithBracketsEClass, ClassOrInterfaceTypeWithBrackets.class, "ClassOrInterfaceTypeWithBrackets", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getClassOrInterfaceTypeWithBrackets_Type(), this.getClassifierType(), null, "type", null, 0, -1, ClassOrInterfaceTypeWithBrackets.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(primitiveTypeWithBracketsEClass, PrimitiveTypeWithBrackets.class, "PrimitiveTypeWithBrackets", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPrimitiveTypeWithBrackets_Primitivetype(), this.getPrimitiveType(), "primitivetype", null, 0, 1, PrimitiveTypeWithBrackets.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(bracketsEClass, Brackets.class, "Brackets", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(classOrInterfaceTypeEClass, ClassOrInterfaceType.class, "ClassOrInterfaceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getClassOrInterfaceType_Type(), this.getClassifierType(), null, "type", null, 0, -1, ClassOrInterfaceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classifierTypeEClass, ClassifierType.class, "ClassifierType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getClassifierType_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, ClassifierType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getClassifierType_Typearguments(), this.getTypeArguments(), null, "typearguments", null, 0, 1, ClassifierType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeArgumentsEClass, TypeArguments.class, "TypeArguments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTypeArguments_Typeargument(), this.getTypeArgument(), null, "typeargument", null, 0, -1, TypeArguments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeArgumentEClass, TypeArgument.class, "TypeArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTypeArgument_Type(), this.getType(), null, "type", null, 0, 1, TypeArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTypeArgument_Wildcard(), ecorePackage.getEBoolean(), "wildcard", null, 0, 1, TypeArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTypeArgument_Extends(), ecorePackage.getEBoolean(), "extends", null, 0, 1, TypeArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTypeArgument_Super(), ecorePackage.getEBoolean(), "super", null, 0, 1, TypeArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(formalParameterDeclEClass, FormalParameterDecl.class, "FormalParameterDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFormalParameterDecl_Varargs(), ecorePackage.getEBoolean(), "varargs", null, 0, 1, FormalParameterDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFormalParameterDecl_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, FormalParameterDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(methodBodyEClass, MethodBody.class, "MethodBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(constructorBodyEClass, ConstructorBody.class, "ConstructorBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getConstructorBody_Blockstatement(), this.getBlockStatement(), null, "blockstatement", null, 0, -1, ConstructorBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(modifiableEClass, Modifiable.class, "Modifiable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getModifiable_Modifiers(), this.getModifier(), null, "modifiers", null, 0, -1, Modifiable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typedEClass, Typed.class, "Typed", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTyped_Type(), this.getType(), null, "type", null, 0, 1, Typed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationsEClass, Annotations.class, "Annotations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAnnotations_Annotation(), this.getAnnotation(), null, "annotation", null, 0, -1, Annotations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAnnotation_Annotationname(), ecorePackage.getEString(), "annotationname", null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnotation_Elementvaluepairs(), this.getElementValuePairs(), null, "elementvaluepairs", null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnotation_Elementvalue(), this.getElementValue(), null, "elementvalue", null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(elementValuePairsEClass, ElementValuePairs.class, "ElementValuePairs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getElementValuePairs_Elementvaluepair(), this.getElementValuePair(), null, "elementvaluepair", null, 0, -1, ElementValuePairs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(elementValuePairEClass, ElementValuePair.class, "ElementValuePair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getElementValuePair_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, ElementValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getElementValuePair_Elementvalue(), this.getElementValue(), null, "elementvalue", null, 0, 1, ElementValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(elementValueEClass, ElementValue.class, "ElementValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(elementValueArrayInitializerEClass, ElementValueArrayInitializer.class, "ElementValueArrayInitializer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getElementValueArrayInitializer_Elementvalue(), this.getElementValue(), null, "elementvalue", null, 0, -1, ElementValueArrayInitializer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationTypeDeclarationEClass, AnnotationTypeDeclaration.class, "AnnotationTypeDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAnnotationTypeDeclaration_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, AnnotationTypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnotationTypeDeclaration_Annotationtypeelementdeclaration(), this.getAnnotationTypeElementDeclaration(), null, "annotationtypeelementdeclaration", null, 0, -1, AnnotationTypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationTypeElementDeclarationEClass, AnnotationTypeElementDeclaration.class, "AnnotationTypeElementDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAnnotationTypeElementDeclaration_Annotationtypeelementrest(), this.getAnnotationTypeElementRest(), null, "annotationtypeelementrest", null, 0, 1, AnnotationTypeElementDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationTypeElementRestEClass, AnnotationTypeElementRest.class, "AnnotationTypeElementRest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(annotationMethodOrConstantRestEClass, AnnotationMethodOrConstantRest.class, "AnnotationMethodOrConstantRest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAnnotationMethodOrConstantRest_Method(), this.getAnnotationMethodRest(), null, "method", null, 0, 1, AnnotationMethodOrConstantRest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnotationMethodOrConstantRest_Constant(), this.getAnnotationConstantRest(), null, "constant", null, 0, 1, AnnotationMethodOrConstantRest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationMethodRestEClass, AnnotationMethodRest.class, "AnnotationMethodRest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAnnotationMethodRest_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, AnnotationMethodRest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnotationMethodRest_Defaultvalue(), this.getDefaultValue(), null, "defaultvalue", null, 0, 1, AnnotationMethodRest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annotationConstantRestEClass, AnnotationConstantRest.class, "AnnotationConstantRest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAnnotationConstantRest_Variabledeclarator(), this.getVariableDeclarator(), null, "variabledeclarator", null, 0, -1, AnnotationConstantRest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(defaultValueEClass, DefaultValue.class, "DefaultValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDefaultValue_Elementvalue(), this.getElementValue(), null, "elementvalue", null, 0, 1, DefaultValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(blockEClass, Block.class, "Block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getBlock_Blockstatement(), this.getBlockStatement(), null, "blockstatement", null, 0, -1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(blockStatementEClass, BlockStatement.class, "BlockStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(identifierHavingEClass, IdentifierHaving.class, "IdentifierHaving", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getIdentifierHaving_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, IdentifierHaving.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(expressionEClass, Expression.class, "Expression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getExpression_Type(), this.getType(), null, "type", null, 0, 1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExpression_Name(), ecorePackage.getEString(), "name", null, 0, 1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExpression_Right(), this.getExpression(), null, "right", null, 0, 1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parenthesisExpressionEClass, ParenthesisExpression.class, "ParenthesisExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getParenthesisExpression_Expr(), this.getExpression(), null, "expr", null, 0, 1, ParenthesisExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(collectionLiteralEClass, CollectionLiteral.class, "CollectionLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCollectionLiteral_Elements(), this.getExpression(), null, "elements", null, 0, -1, CollectionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(setLiteralEClass, SetLiteral.class, "SetLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(listLiteralEClass, ListLiteral.class, "ListLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(casePartEClass, CasePart.class, "CasePart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCasePart_TypeGuard(), theTypesPackage.getJvmTypeReference(), null, "typeGuard", null, 0, 1, CasePart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCasePart_Case(), this.getExpression(), null, "case", null, 0, 1, CasePart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCasePart_Then(), this.getExpression(), null, "then", null, 0, 1, CasePart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(validIDEClass, ValidID.class, "ValidID", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getValidID_ParameterType(), ecorePackage.getEString(), "parameterType", null, 0, 1, ValidID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getValidID_Name(), ecorePackage.getEString(), "name", null, 0, 1, ValidID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(catchClauseEClass, CatchClause.class, "CatchClause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCatchClause_DeclaredParam(), this.getValidID(), null, "declaredParam", null, 0, 1, CatchClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCatchClause_Expression(), this.getExpression(), null, "expression", null, 0, 1, CatchClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assignmentEClass, Assignment.class, "Assignment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAssignment_TypeForVariableDeclaration(), ecorePackage.getEString(), "typeForVariableDeclaration", null, 0, 1, Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAssignment_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAssignment_Value(), this.getExpression(), null, "value", null, 0, 1, Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAssignment_Assignable(), this.getExpression(), null, "assignable", null, 0, 1, Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(binaryOperationEClass, BinaryOperation.class, "BinaryOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getBinaryOperation_LeftOperand(), this.getExpression(), null, "leftOperand", null, 0, 1, BinaryOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBinaryOperation_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, BinaryOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getBinaryOperation_RightOperand(), this.getExpression(), null, "rightOperand", null, 0, 1, BinaryOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(instanceOfExpressionEClass, InstanceOfExpression.class, "InstanceOfExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInstanceOfExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, InstanceOfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(unaryOperationEClass, UnaryOperation.class, "UnaryOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUnaryOperation_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, UnaryOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getUnaryOperation_Operand(), this.getExpression(), null, "operand", null, 0, 1, UnaryOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(postfixOperationEClass, PostfixOperation.class, "PostfixOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPostfixOperation_Operand(), this.getExpression(), null, "operand", null, 0, 1, PostfixOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPostfixOperation_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, PostfixOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(memberFeatureCallEClass, MemberFeatureCall.class, "MemberFeatureCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMemberFeatureCall_MemberCallTarget(), this.getExpression(), null, "memberCallTarget", null, 0, 1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMemberFeatureCall_NullSafe(), ecorePackage.getEBoolean(), "nullSafe", null, 0, 1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMemberFeatureCall_ExplicitStatic(), ecorePackage.getEBoolean(), "explicitStatic", null, 0, 1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMemberFeatureCall_TypeArguments(), theTypesPackage.getJvmTypeReference(), null, "typeArguments", null, 0, -1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMemberFeatureCall_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMemberFeatureCall_ExplicitOperationCall(), ecorePackage.getEBoolean(), "explicitOperationCall", null, 0, 1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMemberFeatureCall_MemberCallArguments(), this.getExpression(), null, "memberCallArguments", null, 0, -1, MemberFeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlOldExpressionEClass, JMLOldExpression.class, "JMLOldExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLOldExpression_Expr(), this.getParenthesisExpression(), null, "expr", null, 0, 1, JMLOldExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlFreshExpressionEClass, JMLFreshExpression.class, "JMLFreshExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLFreshExpression_Expr(), this.getParenthesisExpression(), null, "expr", null, 0, 1, JMLFreshExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jmlResultExpressionEClass, JMLResultExpression.class, "JMLResultExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(jmlForAllExpressionEClass, JMLForAllExpression.class, "JMLForAllExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJMLForAllExpression_InitExpressions(), this.getExpression(), null, "initExpressions", null, 0, -1, JMLForAllExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLForAllExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, JMLForAllExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJMLForAllExpression_UpdateExpressions(), this.getExpression(), null, "updateExpressions", null, 0, -1, JMLForAllExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(closureEClass, Closure.class, "Closure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getClosure_DeclaredFormalParameters(), this.getValidID(), null, "declaredFormalParameters", null, 0, -1, Closure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getClosure_ExplicitSyntax(), ecorePackage.getEBoolean(), "explicitSyntax", null, 0, 1, Closure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getClosure_Expression(), this.getExpression(), null, "expression", null, 0, 1, Closure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(blockExpressionEClass, BlockExpression.class, "BlockExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getBlockExpression_Expressions(), this.getExpression(), null, "expressions", null, 0, -1, BlockExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(ifExpressionEClass, IfExpression.class, "IfExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getIfExpression_If(), this.getExpression(), null, "if", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getIfExpression_Then(), this.getExpression(), null, "then", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getIfExpression_Else(), this.getExpression(), null, "else", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(switchExpressionEClass, SwitchExpression.class, "SwitchExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSwitchExpression_DeclaredParam(), this.getValidID(), null, "declaredParam", null, 0, 1, SwitchExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSwitchExpression_Switch(), this.getExpression(), null, "switch", null, 0, 1, SwitchExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSwitchExpression_Cases(), this.getCasePart(), null, "cases", null, 0, -1, SwitchExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSwitchExpression_Default(), this.getExpression(), null, "default", null, 0, 1, SwitchExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(forLoopExpressionEClass, ForLoopExpression.class, "ForLoopExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getForLoopExpression_DeclaredParam(), this.getValidID(), null, "declaredParam", null, 0, 1, ForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getForLoopExpression_ForExpression(), this.getExpression(), null, "forExpression", null, 0, 1, ForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getForLoopExpression_EachExpression(), this.getExpression(), null, "eachExpression", null, 0, 1, ForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(basicForLoopExpressionEClass, BasicForLoopExpression.class, "BasicForLoopExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getBasicForLoopExpression_InitExpressions(), this.getExpression(), null, "initExpressions", null, 0, -1, BasicForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getBasicForLoopExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, BasicForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getBasicForLoopExpression_UpdateExpressions(), this.getExpression(), null, "updateExpressions", null, 0, -1, BasicForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getBasicForLoopExpression_EachExpression(), this.getExpression(), null, "eachExpression", null, 0, 1, BasicForLoopExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(whileExpressionEClass, WhileExpression.class, "WhileExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getWhileExpression_Predicate(), this.getExpression(), null, "predicate", null, 0, 1, WhileExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getWhileExpression_Body(), this.getExpression(), null, "body", null, 0, 1, WhileExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(doWhileExpressionEClass, DoWhileExpression.class, "DoWhileExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDoWhileExpression_Body(), this.getExpression(), null, "body", null, 0, 1, DoWhileExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDoWhileExpression_Predicate(), this.getExpression(), null, "predicate", null, 0, 1, DoWhileExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(featureCallEClass, FeatureCall.class, "FeatureCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFeatureCall_TypeArguments(), theTypesPackage.getJvmTypeReference(), null, "typeArguments", null, 0, -1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFeatureCall_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFeatureCall_ExplicitOperationCall(), ecorePackage.getEBoolean(), "explicitOperationCall", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFeatureCall_FeatureCallArguments(), this.getExpression(), null, "featureCallArguments", null, 0, -1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constructorCallEClass, ConstructorCall.class, "ConstructorCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstructorCall_Constructor(), ecorePackage.getEString(), "constructor", null, 0, 1, ConstructorCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConstructorCall_TypeArguments(), theTypesPackage.getJvmTypeReference(), null, "typeArguments", null, 0, -1, ConstructorCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getConstructorCall_ExplicitConstructorCall(), ecorePackage.getEBoolean(), "explicitConstructorCall", null, 0, 1, ConstructorCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConstructorCall_Arguments(), this.getExpression(), null, "arguments", null, 0, -1, ConstructorCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(booleanLiteralEClass, BooleanLiteral.class, "BooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBooleanLiteral_IsTrue(), ecorePackage.getEBoolean(), "isTrue", null, 0, 1, BooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(nullLiteralEClass, NullLiteral.class, "NullLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(numberLiteralEClass, NumberLiteral.class, "NumberLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNumberLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1, NumberLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(stringLiteralEClass, StringLiteral.class, "StringLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getStringLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1, StringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(charLiteralEClass, CharLiteral.class, "CharLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCharLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1, CharLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(throwExpressionEClass, ThrowExpression.class, "ThrowExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getThrowExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, ThrowExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(returnExpressionEClass, ReturnExpression.class, "ReturnExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getReturnExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, ReturnExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(tryCatchFinallyExpressionEClass, TryCatchFinallyExpression.class, "TryCatchFinallyExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTryCatchFinallyExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, TryCatchFinallyExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTryCatchFinallyExpression_CatchClauses(), this.getCatchClause(), null, "catchClauses", null, 0, -1, TryCatchFinallyExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTryCatchFinallyExpression_FinallyExpression(), this.getExpression(), null, "finallyExpression", null, 0, 1, TryCatchFinallyExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(synchronizedExpressionEClass, SynchronizedExpression.class, "SynchronizedExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSynchronizedExpression_Param(), this.getExpression(), null, "param", null, 0, 1, SynchronizedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSynchronizedExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, SynchronizedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(modifierValueEEnum, ModifierValue.class, "ModifierValue");
    addEEnumLiteral(modifierValueEEnum, ModifierValue.PUBLIC);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.PROTECTED);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.PRIVATE);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.STATIC);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.ABSTRACT);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.FINAL);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.NATIVE);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.SYNCHRONIZED);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.TRANSIENT);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.VOLATILE);
    addEEnumLiteral(modifierValueEEnum, ModifierValue.STRICTFP);

    initEEnum(visibilityModifierValueEEnum, VisibilityModifierValue.class, "VisibilityModifierValue");
    addEEnumLiteral(visibilityModifierValueEEnum, VisibilityModifierValue.PUBLIC);
    addEEnumLiteral(visibilityModifierValueEEnum, VisibilityModifierValue.PROTECTED);
    addEEnumLiteral(visibilityModifierValueEEnum, VisibilityModifierValue.PRIVATE);
    addEEnumLiteral(visibilityModifierValueEEnum, VisibilityModifierValue.STATIC);

    initEEnum(jmlSpecMemberModifierEEnum, JMLSpecMemberModifier.class, "JMLSpecMemberModifier");
    addEEnumLiteral(jmlSpecMemberModifierEEnum, JMLSpecMemberModifier.PURE);
    addEEnumLiteral(jmlSpecMemberModifierEEnum, JMLSpecMemberModifier.HELPER);
    addEEnumLiteral(jmlSpecMemberModifierEEnum, JMLSpecMemberModifier.PUBLIC);
    addEEnumLiteral(jmlSpecMemberModifierEEnum, JMLSpecMemberModifier.PROTECTED);

    initEEnum(primitiveTypeEEnum, PrimitiveType.class, "PrimitiveType");
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.BOOLEAN);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.CHAR);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.BYTE);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.SHORT);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.INT);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.LONG);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.FLOAT);
    addEEnumLiteral(primitiveTypeEEnum, PrimitiveType.DOUBLE);

    // Create resource
    createResource(eNS_URI);
  }

} //JMLPackageImpl
