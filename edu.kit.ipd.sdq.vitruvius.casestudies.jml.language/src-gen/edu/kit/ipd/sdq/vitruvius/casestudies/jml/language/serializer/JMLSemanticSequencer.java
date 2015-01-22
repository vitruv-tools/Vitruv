package edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Annotation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.AnnotationConstantRest;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.AnnotationMethodOrConstantRest;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.AnnotationMethodRest;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.AnnotationTypeDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.AnnotationTypeElementDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Annotations;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Arguments;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Assignment;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.BasicForLoopExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.BinaryOperation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Block;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.BlockExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.BooleanLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Brackets;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CasePart;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CatchClause;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CharLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassBodyDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassOrInterfaceType;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassOrInterfaceTypeWithBrackets;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassifierDeclarationWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassifierType;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Closure;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Constructor;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ConstructorBody;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ConstructorCall;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.DeclaredException;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.DefaultValue;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.DoWhileExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ElementValueArrayInitializer;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ElementValuePair;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ElementValuePairs;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.EnumBodyDeclarations;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.EnumConstant;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.EnumConstants;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.EnumDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Expression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FeatureCall;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FieldDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ForLoopExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FormalParameterDecl;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.GenericMethodOrConstructorDecl;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.GenericMethodOrConstructorDeclOld;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.IfExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ImportDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.InstanceOfExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLAxiomExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLBehaviorBlock;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLConstraintExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLEnsuresExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLExceptionalBehaviorBlock;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLForAllExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLFreshExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLGhostElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLInvariantExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMemberModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMethodSpecificationWithModifierExtended;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMethodSpecificationWithModifierRegular;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLModelElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMultilineSpec;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLNormalBehaviorBlock;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLOldExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLRequiresExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLResultExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSinglelineSpec;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecificationOnlyElementWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLTypeExpressionWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ListLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifierRegular;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifierSpec;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberFeatureCall;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MethodDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NormalInterfaceDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NullLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NumberLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.PackageDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ParenthesisExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.PostfixOperation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.PrimitiveTypeWithBrackets;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.RegularModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ReturnExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.SetLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.StaticBlock;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.StringLiteral;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.SwitchExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.SynchronizedExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ThrowExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TryCatchFinallyExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TypeArgument;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TypeArguments;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TypeBound;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TypeParameter;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.TypeParameters;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.UnaryOperation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ValidID;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.VariableDeclarator;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.VisiblityModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.WhileExpression;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.services.JMLGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmGenericArrayTypeReference;
import org.eclipse.xtext.common.types.JvmInnerTypeReference;
import org.eclipse.xtext.common.types.JvmLowerBound;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmUpperBound;
import org.eclipse.xtext.common.types.JvmWildcardTypeReference;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.eclipse.xtext.xbase.serializer.XtypeSemanticSequencer;
import org.eclipse.xtext.xtype.XFunctionTypeRef;
import org.eclipse.xtext.xtype.XImportDeclaration;
import org.eclipse.xtext.xtype.XImportSection;
import org.eclipse.xtext.xtype.XtypePackage;

@SuppressWarnings("all")
public class JMLSemanticSequencer extends XtypeSemanticSequencer {

	@Inject
	private JMLGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == JMLPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case JMLPackage.ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getElementValueRule() ||
				   context == grammarAccess.getModifierRule()) {
					sequence_Annotation(context, (Annotation) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATION_CONSTANT_REST:
				if(context == grammarAccess.getAnnotationConstantRestRule()) {
					sequence_AnnotationConstantRest(context, (AnnotationConstantRest) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST:
				if(context == grammarAccess.getAnnotationMethodOrConstantRestRule() ||
				   context == grammarAccess.getAnnotationTypeElementRestRule() ||
				   context == grammarAccess.getTypedRule()) {
					sequence_AnnotationMethodOrConstantRest(context, (AnnotationMethodOrConstantRest) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATION_METHOD_REST:
				if(context == grammarAccess.getAnnotationMethodRestRule()) {
					sequence_AnnotationMethodRest(context, (AnnotationMethodRest) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATION_TYPE_DECLARATION:
				if(context == grammarAccess.getAnnotationTypeDeclarationRule() ||
				   context == grammarAccess.getAnnotationTypeElementRestRule() ||
				   context == grammarAccess.getClassOrInterfaceDeclarationRule() ||
				   context == grammarAccess.getInterfaceDeclarationRule() ||
				   context == grammarAccess.getMemberDeclRule()) {
					sequence_AnnotationTypeDeclaration(context, (AnnotationTypeDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION:
				if(context == grammarAccess.getAnnotationTypeElementDeclarationRule() ||
				   context == grammarAccess.getModifiableRule()) {
					sequence_AnnotationTypeElementDeclaration(context, (AnnotationTypeElementDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ANNOTATIONS:
				if(context == grammarAccess.getAnnotationsRule()) {
					sequence_Annotations(context, (Annotations) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ARGUMENTS:
				if(context == grammarAccess.getArgumentsRule()) {
					sequence_Arguments(context, (Arguments) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ASSIGNMENT:
				if(context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getParenthesizedExpressionRule()) {
					sequence_Assignment_MemberFeatureCall(context, (Assignment) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_MemberFeatureCall(context, (Assignment) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BASIC_FOR_LOOP_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBasicForLoopExpressionRule() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_BasicForLoopExpression(context, (BasicForLoopExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BINARY_OPERATION:
				if(context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getParenthesizedExpressionRule()) {
					sequence_AdditiveExpression_AndExpression_Assignment_EqualityExpression_MultiplicativeExpression_OrExpression_OtherOperatorExpression_RelationalExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_AdditiveExpression_AndExpression_EqualityExpression_MultiplicativeExpression_OrExpression_OtherOperatorExpression_RelationalExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_AdditiveExpression_AndExpression_EqualityExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_AdditiveExpression_EqualityExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_AdditiveExpression_MultiplicativeExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_AdditiveExpression_MultiplicativeExpression_OtherOperatorExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0()) {
					sequence_AdditiveExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0()) {
					sequence_MultiplicativeExpression(context, (BinaryOperation) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BLOCK:
				if(context == grammarAccess.getBlockRule() ||
				   context == grammarAccess.getMethodBodyRule()) {
					sequence_Block(context, (Block) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BLOCK_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockExpressionRule() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_BlockExpression(context, (BlockExpression) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getExpressionInClosureRule()) {
					sequence_ExpressionInClosure(context, (BlockExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BOOLEAN_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getBooleanLiteralRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_BooleanLiteral(context, (BooleanLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.BRACKETS:
				if(context == grammarAccess.getBracketsRule()) {
					sequence_Brackets(context, (Brackets) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CASE_PART:
				if(context == grammarAccess.getCasePartRule()) {
					sequence_CasePart(context, (CasePart) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CATCH_CLAUSE:
				if(context == grammarAccess.getCatchClauseRule()) {
					sequence_CatchClause(context, (CatchClause) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CHAR_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getCharLiteralRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_CharLiteral(context, (CharLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLASS_BODY_DECLARATION:
				if(context == grammarAccess.getClassBodyDeclarationRule()) {
					sequence_ClassBodyDeclaration(context, (ClassBodyDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLASS_OR_INTERFACE_TYPE:
				if(context == grammarAccess.getClassOrInterfaceTypeRule()) {
					sequence_ClassOrInterfaceType(context, (ClassOrInterfaceType) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS:
				if(context == grammarAccess.getClassOrInterfaceTypeWithBracketsRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_ClassOrInterfaceTypeWithBrackets(context, (ClassOrInterfaceTypeWithBrackets) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER:
				if(context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getClassifierDeclarationWithModifierRule() ||
				   context == grammarAccess.getModifiableRule()) {
					sequence_ClassifierDeclarationWithModifier(context, (ClassifierDeclarationWithModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLASSIFIER_TYPE:
				if(context == grammarAccess.getClassifierTypeRule()) {
					sequence_ClassifierType(context, (ClassifierType) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CLOSURE:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getClosureRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_Closure(context, (Closure) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getShortClosureRule()) {
					sequence_ShortClosure(context, (Closure) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.COMPILATION_UNIT:
				if(context == grammarAccess.getCompilationUnitRule()) {
					sequence_CompilationUnit(context, (CompilationUnit) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CONSTRUCTOR:
				if(context == grammarAccess.getConstructorRule() ||
				   context == grammarAccess.getMemberDeclRule()) {
					sequence_Constructor(context, (Constructor) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CONSTRUCTOR_BODY:
				if(context == grammarAccess.getConstructorBodyRule()) {
					sequence_ConstructorBody(context, (ConstructorBody) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.CONSTRUCTOR_CALL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getConstructorCallRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ConstructorCall(context, (ConstructorCall) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.DECLARED_EXCEPTION:
				if(context == grammarAccess.getDeclaredExceptionRule()) {
					sequence_DeclaredException(context, (DeclaredException) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.DEFAULT_VALUE:
				if(context == grammarAccess.getDefaultValueRule()) {
					sequence_DefaultValue(context, (DefaultValue) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.DO_WHILE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getDoWhileExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_DoWhileExpression(context, (DoWhileExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER:
				if(context == grammarAccess.getElementValueRule() ||
				   context == grammarAccess.getElementValueArrayInitializerRule()) {
					sequence_ElementValueArrayInitializer(context, (ElementValueArrayInitializer) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ELEMENT_VALUE_PAIR:
				if(context == grammarAccess.getElementValuePairRule()) {
					sequence_ElementValuePair(context, (ElementValuePair) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ELEMENT_VALUE_PAIRS:
				if(context == grammarAccess.getElementValuePairsRule()) {
					sequence_ElementValuePairs(context, (ElementValuePairs) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ENUM_BODY_DECLARATIONS:
				if(context == grammarAccess.getEnumBodyDeclarationsRule()) {
					sequence_EnumBodyDeclarations(context, (EnumBodyDeclarations) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ENUM_CONSTANT:
				if(context == grammarAccess.getEnumConstantRule()) {
					sequence_EnumConstant(context, (EnumConstant) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ENUM_CONSTANTS:
				if(context == grammarAccess.getEnumConstantsRule()) {
					sequence_EnumConstants(context, (EnumConstants) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.ENUM_DECLARATION:
				if(context == grammarAccess.getAnnotationTypeElementRestRule() ||
				   context == grammarAccess.getClassDeclarationRule() ||
				   context == grammarAccess.getClassOrInterfaceDeclarationRule() ||
				   context == grammarAccess.getEnumDeclarationRule() ||
				   context == grammarAccess.getIdentifierHavingRule() ||
				   context == grammarAccess.getMemberDeclRule()) {
					sequence_EnumDeclaration(context, (EnumDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.EXPRESSION:
				if(context == grammarAccess.getVarDeclRule()) {
					sequence_VarDecl(context, (Expression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.FEATURE_CALL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_FeatureCall(context, (FeatureCall) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.FIELD_DECLARATION:
				if(context == grammarAccess.getFieldDeclarationRule()) {
					sequence_FieldDeclaration(context, (FieldDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.FOR_LOOP_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getForLoopExpressionRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ForLoopExpression(context, (ForLoopExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.FORMAL_PARAMETER_DECL:
				if(context == grammarAccess.getFormalParameterDeclRule() ||
				   context == grammarAccess.getModifiableRule() ||
				   context == grammarAccess.getTypedRule()) {
					sequence_FormalParameterDecl(context, (FormalParameterDecl) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL:
				if(context == grammarAccess.getGenericMethodOrConstructorDeclRule() ||
				   context == grammarAccess.getMemberDeclRule()) {
					sequence_GenericMethodOrConstructorDecl(context, (GenericMethodOrConstructorDecl) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD:
				if(context == grammarAccess.getGenericMethodOrConstructorDeclOldRule()) {
					sequence_GenericMethodOrConstructorDeclOld(context, (GenericMethodOrConstructorDeclOld) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.IF_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getIfExpressionRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_IfExpression(context, (IfExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.IMPORT_DECLARATION:
				if(context == grammarAccess.getImportDeclarationRule()) {
					sequence_ImportDeclaration(context, (ImportDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.INSTANCE_OF_EXPRESSION:
				if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0()) {
					sequence_RelationalExpression(context, (InstanceOfExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_AXIOM_EXPRESSION:
				if(context == grammarAccess.getJMLAxiomExpressionRule() ||
				   context == grammarAccess.getJMLExpressionHavingRule() ||
				   context == grammarAccess.getJMLTypeExpressionRule()) {
					sequence_JMLAxiomExpression(context, (JMLAxiomExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_BEHAVIOR_BLOCK:
				if(context == grammarAccess.getJMLBehaviorBlockRule() ||
				   context == grammarAccess.getJMLMethodBehaviorRule() ||
				   context == grammarAccess.getJMLMethodSpecificationRule()) {
					sequence_JMLBehaviorBlock(context, (JMLBehaviorBlock) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_CONSTRAINT_EXPRESSION:
				if(context == grammarAccess.getJMLConstraintExpressionRule() ||
				   context == grammarAccess.getJMLExpressionHavingRule() ||
				   context == grammarAccess.getJMLTypeExpressionRule()) {
					sequence_JMLConstraintExpression(context, (JMLConstraintExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_ENSURES_EXPRESSION:
				if(context == grammarAccess.getJMLEnsuresExpressionRule() ||
				   context == grammarAccess.getJMLExpressionHavingRule() ||
				   context == grammarAccess.getJMLMethodExpressionRule() ||
				   context == grammarAccess.getJMLMethodSpecificationRule()) {
					sequence_JMLEnsuresExpression(context, (JMLEnsuresExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_EXCEPTIONAL_BEHAVIOR_BLOCK:
				if(context == grammarAccess.getJMLExceptionalBehaviorBlockRule() ||
				   context == grammarAccess.getJMLMethodBehaviorRule() ||
				   context == grammarAccess.getJMLMethodSpecificationRule()) {
					sequence_JMLExceptionalBehaviorBlock(context, (JMLExceptionalBehaviorBlock) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_FOR_ALL_EXPRESSION:
				if(context == grammarAccess.getJMLForAllExpressionRule()) {
					sequence_JMLForAllExpression(context, (JMLForAllExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_FRESH_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getJMLFreshExpressionRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_JMLFreshExpression(context, (JMLFreshExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_GHOST_ELEMENT:
				if(context == grammarAccess.getJMLGhostElementRule() ||
				   context == grammarAccess.getJMLSpecificationOnlyElementRule()) {
					sequence_JMLGhostElement(context, (JMLGhostElement) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_INVARIANT_EXPRESSION:
				if(context == grammarAccess.getJMLExpressionHavingRule() ||
				   context == grammarAccess.getJMLInvariantExpressionRule() ||
				   context == grammarAccess.getJMLTypeExpressionRule()) {
					sequence_JMLInvariantExpression(context, (JMLInvariantExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_MEMBER_MODIFIER:
				if(context == grammarAccess.getJMLMemberModifierRule()) {
					sequence_JMLMemberModifier(context, (JMLMemberModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED:
				if(context == grammarAccess.getJMLMethodSpecificationWithModifierRule() ||
				   context == grammarAccess.getJMLMethodSpecificationWithModifierExtendedRule()) {
					sequence_JMLMethodSpecificationWithModifierExtended(context, (JMLMethodSpecificationWithModifierExtended) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR:
				if(context == grammarAccess.getJMLMethodSpecificationWithModifierRule() ||
				   context == grammarAccess.getJMLMethodSpecificationWithModifierRegularRule()) {
					sequence_JMLMethodSpecificationWithModifierRegular(context, (JMLMethodSpecificationWithModifierRegular) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_MODEL_ELEMENT:
				if(context == grammarAccess.getJMLModelElementRule() ||
				   context == grammarAccess.getJMLSpecificationOnlyElementRule()) {
					sequence_JMLModelElement(context, (JMLModelElement) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_MULTILINE_SPEC:
				if(context == grammarAccess.getClassBodyDeclarationRule() ||
				   context == grammarAccess.getJMLMultilineSpecRule() ||
				   context == grammarAccess.getJMLSpecifiedElementRule()) {
					sequence_JMLMultilineSpec(context, (JMLMultilineSpec) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_NORMAL_BEHAVIOR_BLOCK:
				if(context == grammarAccess.getJMLMethodBehaviorRule() ||
				   context == grammarAccess.getJMLMethodSpecificationRule() ||
				   context == grammarAccess.getJMLNormalBehaviorBlockRule()) {
					sequence_JMLNormalBehaviorBlock(context, (JMLNormalBehaviorBlock) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_OLD_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getJMLOldExpressionRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_JMLOldExpression(context, (JMLOldExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_REQUIRES_EXPRESSION:
				if(context == grammarAccess.getJMLExpressionHavingRule() ||
				   context == grammarAccess.getJMLMethodExpressionRule() ||
				   context == grammarAccess.getJMLMethodSpecificationRule() ||
				   context == grammarAccess.getJMLRequiresExpressionRule()) {
					sequence_JMLRequiresExpression(context, (JMLRequiresExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_RESULT_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getJMLResultExpressionRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_JMLResultExpression(context, (JMLResultExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_SINGLELINE_SPEC:
				if(context == grammarAccess.getClassBodyDeclarationRule() ||
				   context == grammarAccess.getJMLSinglelineSpecRule() ||
				   context == grammarAccess.getJMLSpecifiedElementRule()) {
					sequence_JMLSinglelineSpec(context, (JMLSinglelineSpec) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER:
				if(context == grammarAccess.getJMLSpecificationOnlyElementWithModifierRule()) {
					sequence_JMLSpecificationOnlyElementWithModifier(context, (JMLSpecificationOnlyElementWithModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.JML_TYPE_EXPRESSION_WITH_MODIFIER:
				if(context == grammarAccess.getJMLTypeExpressionWithModifierRule()) {
					sequence_JMLTypeExpressionWithModifier(context, (JMLTypeExpressionWithModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.LIST_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getCollectionLiteralRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getListLiteralRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ListLiteral(context, (ListLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.MEMBER_DECL_WITH_MODIFIER_REGULAR:
				if(context == grammarAccess.getMemberDeclWithModifierRule() ||
				   context == grammarAccess.getMemberDeclWithModifierRegularRule() ||
				   context == grammarAccess.getModifiableRule()) {
					sequence_MemberDeclWithModifierRegular(context, (MemberDeclWithModifierRegular) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.MEMBER_DECL_WITH_MODIFIER_SPEC:
				if(context == grammarAccess.getMemberDeclWithModifierRule() ||
				   context == grammarAccess.getMemberDeclWithModifierSpecRule() ||
				   context == grammarAccess.getModifiableRule()) {
					sequence_MemberDeclWithModifierSpec(context, (MemberDeclWithModifierSpec) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.MEMBER_DECLARATION:
				if(context == grammarAccess.getMemberDeclRule() ||
				   context == grammarAccess.getMemberDeclarationRule() ||
				   context == grammarAccess.getTypedRule()) {
					sequence_MemberDeclaration(context, (MemberDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.MEMBER_FEATURE_CALL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_MemberFeatureCall(context, (MemberFeatureCall) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.METHOD_DECLARATION:
				if(context == grammarAccess.getIdentifierHavingRule() ||
				   context == grammarAccess.getMethodDeclarationRule()) {
					sequence_MethodDeclaration(context, (MethodDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.NORMAL_CLASS_DECLARATION:
				if(context == grammarAccess.getAnnotationTypeElementRestRule() ||
				   context == grammarAccess.getClassDeclarationRule() ||
				   context == grammarAccess.getClassOrInterfaceDeclarationRule() ||
				   context == grammarAccess.getMemberDeclRule() ||
				   context == grammarAccess.getNormalClassDeclarationRule()) {
					sequence_NormalClassDeclaration(context, (NormalClassDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.NORMAL_INTERFACE_DECLARATION:
				if(context == grammarAccess.getAnnotationTypeElementRestRule() ||
				   context == grammarAccess.getClassOrInterfaceDeclarationRule() ||
				   context == grammarAccess.getInterfaceDeclarationRule() ||
				   context == grammarAccess.getMemberDeclRule() ||
				   context == grammarAccess.getNormalInterfaceDeclarationRule()) {
					sequence_NormalInterfaceDeclaration(context, (NormalInterfaceDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.NULL_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getNullLiteralRule() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_NullLiteral(context, (NullLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.NUMBER_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getNumberLiteralRule() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_NumberLiteral(context, (NumberLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.PACKAGE_DECLARATION:
				if(context == grammarAccess.getPackageDeclarationRule()) {
					sequence_PackageDeclaration(context, (PackageDeclaration) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.PARENTHESIS_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesisExpressionRule() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ParenthesisExpression(context, (ParenthesisExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.POSTFIX_OPERATION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_PostfixOperation(context, (PostfixOperation) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS:
				if(context == grammarAccess.getPrimitiveTypeWithBracketsRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_PrimitiveTypeWithBrackets(context, (PrimitiveTypeWithBrackets) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.REGULAR_MODIFIER:
				if(context == grammarAccess.getModifierRule() ||
				   context == grammarAccess.getRegularModifierRule()) {
					sequence_RegularModifier(context, (RegularModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.RETURN_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getReturnExpressionRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ReturnExpression(context, (ReturnExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.SET_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getCollectionLiteralRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getSetLiteralRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_SetLiteral(context, (SetLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.STATIC_BLOCK:
				if(context == grammarAccess.getClassBodyDeclarationRule() ||
				   context == grammarAccess.getStaticBlockRule()) {
					sequence_StaticBlock(context, (StaticBlock) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.STRING_LITERAL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getLiteralRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getStringLiteralRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_StringLiteral(context, (StringLiteral) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.SWITCH_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getSwitchExpressionRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_SwitchExpression(context, (SwitchExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.SYNCHRONIZED_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getSynchronizedExpressionRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_SynchronizedExpression(context, (SynchronizedExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.THROW_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getThrowExpressionRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_ThrowExpression(context, (ThrowExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getTryCatchFinallyExpressionRule() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_TryCatchFinallyExpression(context, (TryCatchFinallyExpression) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TYPE_ARGUMENT:
				if(context == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeArgument(context, (TypeArgument) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TYPE_ARGUMENTS:
				if(context == grammarAccess.getTypeArgumentsRule()) {
					sequence_TypeArguments(context, (TypeArguments) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TYPE_BOUND:
				if(context == grammarAccess.getTypeBoundRule()) {
					sequence_TypeBound(context, (TypeBound) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TYPE_PARAMETER:
				if(context == grammarAccess.getTypeParameterRule()) {
					sequence_TypeParameter(context, (TypeParameter) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.TYPE_PARAMETERS:
				if(context == grammarAccess.getGenericMethodOrConstructorDeclOldRule()) {
					sequence_GenericMethodOrConstructorDeclOld_TypeParameters(context, (TypeParameters) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getTypeParametersRule()) {
					sequence_TypeParameters(context, (TypeParameters) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.UNARY_OPERATION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule()) {
					sequence_UnaryOperation(context, (UnaryOperation) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.VALID_ID:
				if(context == grammarAccess.getFullJvmFormalParameterRule()) {
					sequence_FullJvmFormalParameter(context, (ValidID) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getJvmFormalParameterRule()) {
					sequence_JvmFormalParameter(context, (ValidID) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.VARIABLE_DECLARATOR:
				if(context == grammarAccess.getIdentifierHavingRule() ||
				   context == grammarAccess.getVariableDeclaratorRule()) {
					sequence_VariableDeclarator(context, (VariableDeclarator) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.VISIBLITY_MODIFIER:
				if(context == grammarAccess.getVisiblityModifierRule()) {
					sequence_VisiblityModifier(context, (VisiblityModifier) semanticObject); 
					return; 
				}
				else break;
			case JMLPackage.WHILE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getAssignmentAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getBlockStatementRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionOrVarDeclarationRule() ||
				   context == grammarAccess.getMemberFeatureCallRule() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getAssignmentAssignableAction_1_0_0_0_0() ||
				   context == grammarAccess.getMemberFeatureCallAccess().getMemberFeatureCallMemberCallTargetAction_1_1_0_0_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getOtherOperatorExpressionRule() ||
				   context == grammarAccess.getOtherOperatorExpressionAccess().getBinaryOperationLeftOperandAction_1_0_0_0() ||
				   context == grammarAccess.getParenthesizedExpressionRule() ||
				   context == grammarAccess.getPostfixOperationRule() ||
				   context == grammarAccess.getPostfixOperationAccess().getPostfixOperationOperandAction_1_0_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getBinaryOperationLeftOperandAction_1_1_0_0_0() ||
				   context == grammarAccess.getRelationalExpressionAccess().getInstanceOfExpressionExpressionAction_1_0_0_0_0() ||
				   context == grammarAccess.getUnaryOperationRule() ||
				   context == grammarAccess.getWhileExpressionRule()) {
					sequence_WhileExpression(context, (WhileExpression) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == TypesPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case TypesPackage.JVM_GENERIC_ARRAY_TYPE_REFERENCE:
				if(context == grammarAccess.getJvmArgumentTypeReferenceRule() ||
				   context == grammarAccess.getJvmTypeReferenceRule() ||
				   context == grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0()) {
					sequence_JvmTypeReference(context, (JvmGenericArrayTypeReference) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_INNER_TYPE_REFERENCE:
				if(context == grammarAccess.getJvmArgumentTypeReferenceRule() ||
				   context == grammarAccess.getJvmParameterizedTypeReferenceRule() ||
				   context == grammarAccess.getJvmParameterizedTypeReferenceAccess().getJvmInnerTypeReferenceOuterAction_1_4_0_0_0() ||
				   context == grammarAccess.getJvmTypeReferenceRule() ||
				   context == grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0()) {
					sequence_JvmParameterizedTypeReference(context, (JvmInnerTypeReference) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_LOWER_BOUND:
				if(context == grammarAccess.getJvmLowerBoundAndedRule()) {
					sequence_JvmLowerBoundAnded(context, (JvmLowerBound) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getJvmLowerBoundRule()) {
					sequence_JvmLowerBound(context, (JvmLowerBound) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_PARAMETERIZED_TYPE_REFERENCE:
				if(context == grammarAccess.getJvmArgumentTypeReferenceRule() ||
				   context == grammarAccess.getJvmParameterizedTypeReferenceRule() ||
				   context == grammarAccess.getJvmParameterizedTypeReferenceAccess().getJvmInnerTypeReferenceOuterAction_1_4_0_0_0() ||
				   context == grammarAccess.getJvmTypeReferenceRule() ||
				   context == grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0()) {
					sequence_JvmParameterizedTypeReference(context, (JvmParameterizedTypeReference) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_TYPE_PARAMETER:
				if(context == grammarAccess.getJvmTypeParameterRule()) {
					sequence_JvmTypeParameter(context, (JvmTypeParameter) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_UPPER_BOUND:
				if(context == grammarAccess.getJvmUpperBoundAndedRule()) {
					sequence_JvmUpperBoundAnded(context, (JvmUpperBound) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getJvmUpperBoundRule()) {
					sequence_JvmUpperBound(context, (JvmUpperBound) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.JVM_WILDCARD_TYPE_REFERENCE:
				if(context == grammarAccess.getJvmArgumentTypeReferenceRule() ||
				   context == grammarAccess.getJvmWildcardTypeReferenceRule()) {
					sequence_JvmWildcardTypeReference(context, (JvmWildcardTypeReference) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == XtypePackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case XtypePackage.XFUNCTION_TYPE_REF:
				if(context == grammarAccess.getJvmArgumentTypeReferenceRule() ||
				   context == grammarAccess.getJvmTypeReferenceRule() ||
				   context == grammarAccess.getXFunctionTypeRefRule()) {
					sequence_XFunctionTypeRef(context, (XFunctionTypeRef) semanticObject); 
					return; 
				}
				else break;
			case XtypePackage.XIMPORT_DECLARATION:
				if(context == grammarAccess.getXImportDeclarationRule()) {
					sequence_XImportDeclaration(context, (XImportDeclaration) semanticObject); 
					return; 
				}
				else break;
			case XtypePackage.XIMPORT_SECTION:
				if(context == grammarAccess.getXImportSectionRule()) {
					sequence_XImportSection(context, (XImportSection) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression) | 
	 *         (leftOperand=RelationalExpression_BinaryOperation_1_1_0_0_0 feature=OpCompare rightOperand=OtherOperatorExpression) | 
	 *         (leftOperand=EqualityExpression_BinaryOperation_1_0_0_0 feature=OpEquality rightOperand=RelationalExpression) | 
	 *         (leftOperand=AndExpression_BinaryOperation_1_0_0_0 feature=OpAnd rightOperand=EqualityExpression) | 
	 *         (leftOperand=OrExpression_BinaryOperation_1_0_0_0 feature=OpOr rightOperand=AndExpression) | 
	 *         (leftOperand=Assignment_BinaryOperation_1_1_0_0_0 feature=OpMultiAssign rightOperand=Assignment)
	 *     )
	 */
	protected void sequence_AdditiveExpression_AndExpression_Assignment_EqualityExpression_MultiplicativeExpression_OrExpression_OtherOperatorExpression_RelationalExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression) | 
	 *         (leftOperand=RelationalExpression_BinaryOperation_1_1_0_0_0 feature=OpCompare rightOperand=OtherOperatorExpression) | 
	 *         (leftOperand=EqualityExpression_BinaryOperation_1_0_0_0 feature=OpEquality rightOperand=RelationalExpression) | 
	 *         (leftOperand=AndExpression_BinaryOperation_1_0_0_0 feature=OpAnd rightOperand=EqualityExpression) | 
	 *         (leftOperand=OrExpression_BinaryOperation_1_0_0_0 feature=OpOr rightOperand=AndExpression)
	 *     )
	 */
	protected void sequence_AdditiveExpression_AndExpression_EqualityExpression_MultiplicativeExpression_OrExpression_OtherOperatorExpression_RelationalExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression) | 
	 *         (leftOperand=RelationalExpression_BinaryOperation_1_1_0_0_0 feature=OpCompare rightOperand=OtherOperatorExpression) | 
	 *         (leftOperand=EqualityExpression_BinaryOperation_1_0_0_0 feature=OpEquality rightOperand=RelationalExpression) | 
	 *         (leftOperand=AndExpression_BinaryOperation_1_0_0_0 feature=OpAnd rightOperand=EqualityExpression)
	 *     )
	 */
	protected void sequence_AdditiveExpression_AndExpression_EqualityExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression) | 
	 *         (leftOperand=RelationalExpression_BinaryOperation_1_1_0_0_0 feature=OpCompare rightOperand=OtherOperatorExpression) | 
	 *         (leftOperand=EqualityExpression_BinaryOperation_1_0_0_0 feature=OpEquality rightOperand=RelationalExpression)
	 *     )
	 */
	protected void sequence_AdditiveExpression_EqualityExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation)
	 *     )
	 */
	protected void sequence_AdditiveExpression_MultiplicativeExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression)
	 *     )
	 */
	protected void sequence_AdditiveExpression_MultiplicativeExpression_OtherOperatorExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (leftOperand=AdditiveExpression_BinaryOperation_1_0_0_0 feature=OpAdd rightOperand=MultiplicativeExpression) | 
	 *         (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation) | 
	 *         (leftOperand=OtherOperatorExpression_BinaryOperation_1_0_0_0 feature=OpOther rightOperand=AdditiveExpression) | 
	 *         (leftOperand=RelationalExpression_BinaryOperation_1_1_0_0_0 feature=OpCompare rightOperand=OtherOperatorExpression)
	 *     )
	 */
	protected void sequence_AdditiveExpression_MultiplicativeExpression_OtherOperatorExpression_RelationalExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (variabledeclarator+=VariableDeclarator variabledeclarator+=VariableDeclarator*)
	 */
	protected void sequence_AnnotationConstantRest(EObject context, AnnotationConstantRest semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=Type (method=AnnotationMethodRest | constant=AnnotationConstantRest))
	 */
	protected void sequence_AnnotationMethodOrConstantRest(EObject context, AnnotationMethodOrConstantRest semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID defaultvalue=DefaultValue?)
	 */
	protected void sequence_AnnotationMethodRest(EObject context, AnnotationMethodRest semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID annotationtypeelementdeclaration+=AnnotationTypeElementDeclaration*)
	 */
	protected void sequence_AnnotationTypeDeclaration(EObject context, AnnotationTypeDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifiers+=Modifier* annotationtypeelementrest=AnnotationTypeElementRest)
	 */
	protected void sequence_AnnotationTypeElementDeclaration(EObject context, AnnotationTypeElementDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (annotationname=AnnotationName (elementvaluepairs=ElementValuePairs | elementvalue=ElementValue)?)
	 */
	protected void sequence_Annotation(EObject context, Annotation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     annotation+=Annotation+
	 */
	protected void sequence_Annotations(EObject context, Annotations semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expressions+=Expression expressions+=Expression)
	 */
	protected void sequence_Arguments(EObject context, Arguments semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (typeForVariableDeclaration=ValidID? feature=FeatureCallID value=Assignment) | 
	 *         (assignable=MemberFeatureCall_Assignment_1_0_0_0_0 feature=FeatureCallID value=Assignment)
	 *     )
	 */
	protected void sequence_Assignment_MemberFeatureCall(EObject context, Assignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (initExpressions+=ExpressionOrVarDeclaration initExpressions+=ExpressionOrVarDeclaration*)? 
	 *         expression=Expression? 
	 *         (updateExpressions+=Expression updateExpressions+=Expression*)? 
	 *         eachExpression=Expression
	 *     )
	 */
	protected void sequence_BasicForLoopExpression(EObject context, BasicForLoopExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((expressions+=ExpressionOrVarDeclaration expressions+=ExpressionOrVarDeclaration)*)
	 */
	protected void sequence_BlockExpression(EObject context, BlockExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (blockstatement+=BlockStatement*)
	 */
	protected void sequence_Block(EObject context, Block semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (isTrue?='true'?)
	 */
	protected void sequence_BooleanLiteral(EObject context, BooleanLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {Brackets}
	 */
	protected void sequence_Brackets(EObject context, Brackets semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (typeGuard=JvmTypeReference? case=Expression? then=Expression?)
	 */
	protected void sequence_CasePart(EObject context, CasePart semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (declaredParam=FullJvmFormalParameter expression=Expression)
	 */
	protected void sequence_CatchClause(EObject context, CatchClause semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.CATCH_CLAUSE__DECLARED_PARAM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.CATCH_CLAUSE__DECLARED_PARAM));
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.CATCH_CLAUSE__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.CATCH_CLAUSE__EXPRESSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getCatchClauseAccess().getDeclaredParamFullJvmFormalParameterParserRuleCall_2_0(), semanticObject.getDeclaredParam());
		feeder.accept(grammarAccess.getCatchClauseAccess().getExpressionExpressionParserRuleCall_4_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=CHAR
	 */
	protected void sequence_CharLiteral(EObject context, CharLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {ClassBodyDeclaration}
	 */
	protected void sequence_ClassBodyDeclaration(EObject context, ClassBodyDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type+=ClassifierType type+=ClassifierType* brackets+=Brackets*)
	 */
	protected void sequence_ClassOrInterfaceTypeWithBrackets(EObject context, ClassOrInterfaceTypeWithBrackets semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type+=ClassifierType type+=ClassifierType*)
	 */
	protected void sequence_ClassOrInterfaceType(EObject context, ClassOrInterfaceType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifiers+=Modifier* classOrInterfaceDeclaration=ClassOrInterfaceDeclaration)
	 */
	protected void sequence_ClassifierDeclarationWithModifier(EObject context, ClassifierDeclarationWithModifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID typearguments=TypeArguments?)
	 */
	protected void sequence_ClassifierType(EObject context, ClassifierType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((declaredFormalParameters+=JvmFormalParameter declaredFormalParameters+=JvmFormalParameter*)? explicitSyntax?='|')? 
	 *         expression=ExpressionInClosure
	 *     )
	 */
	protected void sequence_Closure(EObject context, Closure semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (packagedeclaration=PackageDeclaration? importdeclaration+=ImportDeclaration* typedeclaration+=ClassifierDeclarationWithModifier*)
	 */
	protected void sequence_CompilationUnit(EObject context, CompilationUnit semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (blockstatement+=BlockStatement*)
	 */
	protected void sequence_ConstructorBody(EObject context, ConstructorBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         constructor=QualifiedName 
	 *         (typeArguments+=JvmArgumentTypeReference typeArguments+=JvmArgumentTypeReference*)? 
	 *         (explicitConstructorCall?='(' (arguments+=ShortClosure | (arguments+=Expression arguments+=Expression*))?)? 
	 *         arguments+=Closure?
	 *     )
	 */
	protected void sequence_ConstructorCall(EObject context, ConstructorCall semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         identifier=ID 
	 *         (parameters+=FormalParameterDecl parameters+=FormalParameterDecl*)? 
	 *         (exceptions+=DeclaredException exceptions+=DeclaredException*)? 
	 *         constructorbody=ConstructorBody?
	 *     )
	 */
	protected void sequence_Constructor(EObject context, Constructor semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=QualifiedName
	 */
	protected void sequence_DeclaredException(EObject context, DeclaredException semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.DECLARED_EXCEPTION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.DECLARED_EXCEPTION__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDeclaredExceptionAccess().getNameQualifiedNameParserRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     elementvalue=ElementValue
	 */
	protected void sequence_DefaultValue(EObject context, DefaultValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.DEFAULT_VALUE__ELEMENTVALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.DEFAULT_VALUE__ELEMENTVALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDefaultValueAccess().getElementvalueElementValueParserRuleCall_1_0(), semanticObject.getElementvalue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (body=Expression predicate=Expression)
	 */
	protected void sequence_DoWhileExpression(EObject context, DoWhileExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((elementvalue+=ElementValue elementvalue+=ElementValue*)?)
	 */
	protected void sequence_ElementValueArrayInitializer(EObject context, ElementValueArrayInitializer semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID elementvalue=ElementValue)
	 */
	protected void sequence_ElementValuePair(EObject context, ElementValuePair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.ELEMENT_VALUE_PAIR__IDENTIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.ELEMENT_VALUE_PAIR__IDENTIFIER));
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.ELEMENT_VALUE_PAIR__ELEMENTVALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.ELEMENT_VALUE_PAIR__ELEMENTVALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getElementValuePairAccess().getIdentifierIDTerminalRuleCall_0_0(), semanticObject.getIdentifier());
		feeder.accept(grammarAccess.getElementValuePairAccess().getElementvalueElementValueParserRuleCall_2_0(), semanticObject.getElementvalue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (elementvaluepair+=ElementValuePair elementvaluepair+=ElementValuePair*)
	 */
	protected void sequence_ElementValuePairs(EObject context, ElementValuePairs semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (classbodydeclaration+=ClassBodyDeclaration*)
	 */
	protected void sequence_EnumBodyDeclarations(EObject context, EnumBodyDeclarations semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (annotations=Annotations? identifier=ID arguments=Arguments? classbodydeclaration+=ClassBodyDeclaration*)
	 */
	protected void sequence_EnumConstant(EObject context, EnumConstant semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (enumconstant+=EnumConstant enumconstant+=EnumConstant*)
	 */
	protected void sequence_EnumConstants(EObject context, EnumConstants semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID (implementedTypes+=Type implementedTypes+=Type*)? enumconstants=EnumConstants? bodyDeclarations=EnumBodyDeclarations?)
	 */
	protected void sequence_EnumDeclaration(EObject context, EnumDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expressions+=ExpressionOrVarDeclaration*)
	 */
	protected void sequence_ExpressionInClosure(EObject context, BlockExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (typeArguments+=JvmArgumentTypeReference typeArguments+=JvmArgumentTypeReference*)? 
	 *         feature=IdOrSuper 
	 *         (explicitOperationCall?='(' (featureCallArguments+=ShortClosure | (featureCallArguments+=Expression featureCallArguments+=Expression*))?)? 
	 *         featureCallArguments+=Closure?
	 *     )
	 */
	protected void sequence_FeatureCall(EObject context, FeatureCall semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (variabledeclarator+=VariableDeclarator variabledeclarator+=VariableDeclarator*)
	 */
	protected void sequence_FieldDeclaration(EObject context, FieldDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (declaredParam=JvmFormalParameter forExpression=Expression eachExpression=Expression)
	 */
	protected void sequence_ForLoopExpression(EObject context, ForLoopExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifiers+=Modifier* type=Type varargs?='...'? identifier=ID)
	 */
	protected void sequence_FormalParameterDecl(EObject context, FormalParameterDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (parameterType=ValidID name=ValidID)
	 */
	protected void sequence_FullJvmFormalParameter(EObject context, ValidID semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.VALID_ID__PARAMETER_TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.VALID_ID__PARAMETER_TYPE));
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.VALID_ID__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.VALID_ID__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFullJvmFormalParameterAccess().getParameterTypeValidIDParserRuleCall_0_0(), semanticObject.getParameterType());
		feeder.accept(grammarAccess.getFullJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     constructor=Constructor
	 */
	protected void sequence_GenericMethodOrConstructorDeclOld(EObject context, GenericMethodOrConstructorDeclOld semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGenericMethodOrConstructorDeclOldAccess().getConstructorConstructorParserRuleCall_1_0(), semanticObject.getConstructor());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         typeparameter+=TypeParameter 
	 *         typeparameter+=TypeParameter* 
	 *         type=Type? 
	 *         identifier=ID 
	 *         (parameters+=FormalParameterDecl parameters+=FormalParameterDecl*)? 
	 *         (exceptions+=DeclaredException exceptions+=DeclaredException*)? 
	 *         methodbody=MethodBody?
	 *     )
	 */
	protected void sequence_GenericMethodOrConstructorDeclOld_TypeParameters(EObject context, TypeParameters semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (typeParameters=TypeParameters ((type=Type method=MethodDeclaration) | method=MethodDeclaration | constructor=Constructor))
	 */
	protected void sequence_GenericMethodOrConstructorDecl(EObject context, GenericMethodOrConstructorDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (if=Expression then=Expression else=Expression?)
	 */
	protected void sequence_IfExpression(EObject context, IfExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (static?='static'? qualifiedname=QualifiedName wildcard?='*'?)
	 */
	protected void sequence_ImportDeclaration(EObject context, ImportDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expression
	 */
	protected void sequence_JMLAxiomExpression(EObject context, JMLAxiomExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLAxiomExpressionAccess().getExprExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     specifications+=JMLMethodExpression+
	 */
	protected void sequence_JMLBehaviorBlock(EObject context, JMLBehaviorBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expression
	 */
	protected void sequence_JMLConstraintExpression(EObject context, JMLConstraintExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLConstraintExpressionAccess().getExprExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expression
	 */
	protected void sequence_JMLEnsuresExpression(EObject context, JMLEnsuresExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLEnsuresExpressionAccess().getExprExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     specifications+=JMLMethodExpression+
	 */
	protected void sequence_JMLExceptionalBehaviorBlock(EObject context, JMLExceptionalBehaviorBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((initExpressions+=VarDecl initExpressions+=VarDecl*)? expression=Expression? (updateExpressions+=Expression updateExpressions+=Expression*)?)
	 */
	protected void sequence_JMLForAllExpression(EObject context, JMLForAllExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=ParenthesisExpression
	 */
	protected void sequence_JMLFreshExpression(EObject context, JMLFreshExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (instance?='instance'? element=MemberDeclWithModifierSpec)
	 */
	protected void sequence_JMLGhostElement(EObject context, JMLGhostElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expression
	 */
	protected void sequence_JMLInvariantExpression(EObject context, JMLInvariantExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLInvariantExpressionAccess().getExprExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     modifier=JMLSpecMemberModifier
	 */
	protected void sequence_JMLMemberModifier(EObject context, JMLMemberModifier semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_MEMBER_MODIFIER__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_MEMBER_MODIFIER__MODIFIER));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLMemberModifierAccess().getModifierJMLSpecMemberModifierEnumRuleCall_0(), semanticObject.getModifier());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (modifier+=VisiblityModifier* spec=JMLMethodSpecification)
	 */
	protected void sequence_JMLMethodSpecificationWithModifierExtended(EObject context, JMLMethodSpecificationWithModifierExtended semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifier+=VisiblityModifier* spec=JMLMethodSpecification)
	 */
	protected void sequence_JMLMethodSpecificationWithModifierRegular(EObject context, JMLMethodSpecificationWithModifierRegular semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (instance?='instance'? element=MemberDeclWithModifierSpec)
	 */
	protected void sequence_JMLModelElement(EObject context, JMLModelElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         jmlTypeSpecifications+=JMLTypeExpressionWithModifier+ | 
	 *         (
	 *             jmlSpecifications+=JMLMethodSpecificationWithModifier* 
	 *             (modelElement=JMLSpecificationOnlyElementWithModifier | element=MemberDeclWithModifierRegular)
	 *         )
	 *     )
	 */
	protected void sequence_JMLMultilineSpec(EObject context, JMLMultilineSpec semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     specifications+=JMLMethodExpression+
	 */
	protected void sequence_JMLNormalBehaviorBlock(EObject context, JMLNormalBehaviorBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=ParenthesisExpression
	 */
	protected void sequence_JMLOldExpression(EObject context, JMLOldExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expression
	 */
	protected void sequence_JMLRequiresExpression(EObject context, JMLRequiresExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.JML_EXPRESSION_HAVING__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJMLRequiresExpressionAccess().getExprExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {JMLResultExpression}
	 */
	protected void sequence_JMLResultExpression(EObject context, JMLResultExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         element=MemberDeclWithModifierRegular | 
	 *         jmlTypeSpecifications+=JMLTypeExpressionWithModifier | 
	 *         (
	 *             jmlSpecifications+=JMLMethodSpecificationWithModifier 
	 *             jmlSpecifications+=JMLMethodSpecificationWithModifier* 
	 *             element=MemberDeclWithModifierRegular
	 *         )
	 *     )
	 */
	protected void sequence_JMLSinglelineSpec(EObject context, JMLSinglelineSpec semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifier+=VisiblityModifier* element=JMLSpecificationOnlyElement)
	 */
	protected void sequence_JMLSpecificationOnlyElementWithModifier(EObject context, JMLSpecificationOnlyElementWithModifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifier+=VisiblityModifier* spec=JMLTypeExpression)
	 */
	protected void sequence_JMLTypeExpressionWithModifier(EObject context, JMLTypeExpressionWithModifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (parameterType=ValidID? name=ValidID)
	 */
	protected void sequence_JvmFormalParameter(EObject context, ValidID semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((elements+=Expression elements+=Expression*)?)
	 */
	protected void sequence_ListLiteral(EObject context, ListLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifiers+=Modifier* jmlModifiers+=JMLMemberModifier* memberdecl=MemberDecl)
	 */
	protected void sequence_MemberDeclWithModifierRegular(EObject context, MemberDeclWithModifierRegular semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (modifiers+=Modifier* jmlModifiers+=JMLMemberModifier* memberdecl=MemberDecl)
	 */
	protected void sequence_MemberDeclWithModifierSpec(EObject context, MemberDeclWithModifierSpec semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=Type? (method=MethodDeclaration | field=FieldDeclaration))
	 */
	protected void sequence_MemberDeclaration(EObject context, MemberDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (assignable=MemberFeatureCall_Assignment_1_0_0_0_0 feature=FeatureCallID value=Assignment)
	 */
	protected void sequence_MemberFeatureCall(EObject context, Assignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         memberCallTarget=MemberFeatureCall_MemberFeatureCall_1_1_0_0_0 
	 *         (nullSafe?='?.' | explicitStatic?='::')? 
	 *         (typeArguments+=JvmArgumentTypeReference typeArguments+=JvmArgumentTypeReference*)? 
	 *         feature=IdOrSuper 
	 *         (explicitOperationCall?='(' (memberCallArguments+=ShortClosure | (memberCallArguments+=Expression memberCallArguments+=Expression*))?)? 
	 *         memberCallArguments+=Closure?
	 *     )
	 */
	protected void sequence_MemberFeatureCall(EObject context, MemberFeatureCall semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         identifier=ID 
	 *         (parameters+=FormalParameterDecl parameters+=FormalParameterDecl*)? 
	 *         (exceptions+=DeclaredException exceptions+=DeclaredException*)? 
	 *         methodbody=MethodBody?
	 *     )
	 */
	protected void sequence_MethodDeclaration(EObject context, MethodDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftOperand=MultiplicativeExpression_BinaryOperation_1_0_0_0 feature=OpMulti rightOperand=UnaryOperation)
	 */
	protected void sequence_MultiplicativeExpression(EObject context, BinaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         identifier=ID 
	 *         typeparameters=TypeParameters? 
	 *         superType=Type? 
	 *         (implementedTypes+=Type implementedTypes+=Type*)? 
	 *         bodyDeclarations+=ClassBodyDeclaration*
	 *     )
	 */
	protected void sequence_NormalClassDeclaration(EObject context, NormalClassDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID typeparameters=TypeParameters? (implementedTypes+=Type implementedTypes+=Type*)? bodyDeclarations+=ClassBodyDeclaration*)
	 */
	protected void sequence_NormalInterfaceDeclaration(EObject context, NormalInterfaceDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {NullLiteral}
	 */
	protected void sequence_NullLiteral(EObject context, NullLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Number
	 */
	protected void sequence_NumberLiteral(EObject context, NumberLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     qualifiedname=QualifiedName
	 */
	protected void sequence_PackageDeclaration(EObject context, PackageDeclaration semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.PACKAGE_DECLARATION__QUALIFIEDNAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.PACKAGE_DECLARATION__QUALIFIEDNAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPackageDeclarationAccess().getQualifiednameQualifiedNameParserRuleCall_1_0(), semanticObject.getQualifiedname());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expr=ParenthesizedExpression | expr=JMLForAllExpression)
	 */
	protected void sequence_ParenthesisExpression(EObject context, ParenthesisExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (operand=PostfixOperation_PostfixOperation_1_0_0 feature=OpPostfix)
	 */
	protected void sequence_PostfixOperation(EObject context, PostfixOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (primitivetype=PrimitiveType brackets+=Brackets*)
	 */
	protected void sequence_PrimitiveTypeWithBrackets(EObject context, PrimitiveTypeWithBrackets semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     modifier=ModifierValue
	 */
	protected void sequence_RegularModifier(EObject context, RegularModifier semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.REGULAR_MODIFIER__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.REGULAR_MODIFIER__MODIFIER));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRegularModifierAccess().getModifierModifierValueEnumRuleCall_0(), semanticObject.getModifier());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expression=RelationalExpression_InstanceOfExpression_1_0_0_0_0 type=Type)
	 */
	protected void sequence_RelationalExpression(EObject context, InstanceOfExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expression=Expression?)
	 */
	protected void sequence_ReturnExpression(EObject context, ReturnExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((elements+=Expression elements+=Expression*)?)
	 */
	protected void sequence_SetLiteral(EObject context, SetLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((declaredFormalParameters+=JvmFormalParameter declaredFormalParameters+=JvmFormalParameter*)? explicitSyntax?='|' expression=Expression)
	 */
	protected void sequence_ShortClosure(EObject context, Closure semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (static?='static'? block=Block)
	 */
	protected void sequence_StaticBlock(EObject context, StaticBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_StringLiteral(EObject context, StringLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((declaredParam=JvmFormalParameter switch=Expression) | (declaredParam=JvmFormalParameter? switch=Expression)) 
	 *         cases+=CasePart* 
	 *         default=Expression?
	 *     )
	 */
	protected void sequence_SwitchExpression(EObject context, SwitchExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (param=Expression expression=Expression)
	 */
	protected void sequence_SynchronizedExpression(EObject context, SynchronizedExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expression=Expression
	 */
	protected void sequence_ThrowExpression(EObject context, ThrowExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expression=Expression ((catchClauses+=CatchClause+ finallyExpression=Expression?) | finallyExpression=Expression))
	 */
	protected void sequence_TryCatchFinallyExpression(EObject context, TryCatchFinallyExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=Type | (wildcard?='?' ((extends?='extends' | super?='super') type=Type)?))
	 */
	protected void sequence_TypeArgument(EObject context, TypeArgument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (typeargument+=TypeArgument typeargument+=TypeArgument*)
	 */
	protected void sequence_TypeArguments(EObject context, TypeArguments semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type+=Type type+=Type*)
	 */
	protected void sequence_TypeBound(EObject context, TypeBound semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID typebound=TypeBound?)
	 */
	protected void sequence_TypeParameter(EObject context, TypeParameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (typeparameter+=TypeParameter typeparameter+=TypeParameter*)
	 */
	protected void sequence_TypeParameters(EObject context, TypeParameters semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (feature=OpUnary operand=UnaryOperation)
	 */
	protected void sequence_UnaryOperation(EObject context, UnaryOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (((type=Type name=ValidID) | name=ValidID) right=Expression?)
	 */
	protected void sequence_VarDecl(EObject context, Expression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (identifier=ID brackets+=Brackets* expression=Expression?)
	 */
	protected void sequence_VariableDeclarator(EObject context, VariableDeclarator semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     modifier=VisibilityModifierValue
	 */
	protected void sequence_VisiblityModifier(EObject context, VisiblityModifier semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, JMLPackage.Literals.VISIBLITY_MODIFIER__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JMLPackage.Literals.VISIBLITY_MODIFIER__MODIFIER));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVisiblityModifierAccess().getModifierVisibilityModifierValueEnumRuleCall_0(), semanticObject.getModifier());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (predicate=Expression body=Expression)
	 */
	protected void sequence_WhileExpression(EObject context, WhileExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
