package tools.vitruv.variability.vave.tests.comparator;

/*******************************************************************************
 * Copyright (c) 2014
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Benjamin Klatt - initial API and implementation and/or initial documentation
 *    Martin Armbruster - extension for expanded JaMoPP
 *******************************************************************************/

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.emftext.commons.layout.util.LayoutSwitch;
import org.emftext.language.java.annotations.AnnotationAttributeSetting;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.util.AnnotationsSwitch;
import org.emftext.language.java.arrays.ArraySelector;
import org.emftext.language.java.arrays.util.ArraysSwitch;
import org.emftext.language.java.classifiers.AnonymousClass;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.util.ClassifiersSwitch;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.commons.NamespaceAwareElement;
import org.emftext.language.java.commons.util.CommonsSwitch;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.containers.util.ContainersSwitch;
import org.emftext.language.java.expressions.AdditiveExpression;
import org.emftext.language.java.expressions.AndExpression;
import org.emftext.language.java.expressions.AndExpressionChild;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.AssignmentExpressionChild;
import org.emftext.language.java.expressions.ConditionalAndExpression;
import org.emftext.language.java.expressions.ConditionalAndExpressionChild;
import org.emftext.language.java.expressions.ConditionalOrExpression;
import org.emftext.language.java.expressions.ConditionalOrExpressionChild;
import org.emftext.language.java.expressions.EqualityExpression;
import org.emftext.language.java.expressions.EqualityExpressionChild;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.expressions.InstanceOfExpression;
import org.emftext.language.java.expressions.InstanceOfExpressionChild;
import org.emftext.language.java.expressions.NestedExpression;
import org.emftext.language.java.expressions.RelationExpression;
import org.emftext.language.java.expressions.RelationExpressionChild;
import org.emftext.language.java.expressions.UnaryExpression;
import org.emftext.language.java.expressions.UnaryExpressionChild;
import org.emftext.language.java.expressions.util.ExpressionsSwitch;
import org.emftext.language.java.generics.ExtendsTypeArgument;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.SuperTypeArgument;
import org.emftext.language.java.generics.TypeParameter;
import org.emftext.language.java.generics.UnknownTypeArgument;
import org.emftext.language.java.generics.util.GenericsSwitch;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.StaticMemberImport;
import org.emftext.language.java.imports.util.ImportsSwitch;
import org.emftext.language.java.instantiations.ExplicitConstructorCall;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.instantiations.util.InstantiationsSwitch;
//import org.emftext.language.java.literals.BinaryIntegerLiteral;
//import org.emftext.language.java.literals.BinaryLongLiteral;
import org.emftext.language.java.literals.BooleanLiteral;
import org.emftext.language.java.literals.CharacterLiteral;
import org.emftext.language.java.literals.DecimalDoubleLiteral;
import org.emftext.language.java.literals.DecimalFloatLiteral;
import org.emftext.language.java.literals.DecimalIntegerLiteral;
import org.emftext.language.java.literals.DecimalLongLiteral;
import org.emftext.language.java.literals.HexDoubleLiteral;
import org.emftext.language.java.literals.HexFloatLiteral;
import org.emftext.language.java.literals.HexIntegerLiteral;
import org.emftext.language.java.literals.HexLongLiteral;
import org.emftext.language.java.literals.OctalIntegerLiteral;
import org.emftext.language.java.literals.OctalLongLiteral;
import org.emftext.language.java.literals.util.LiteralsSwitch;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.members.util.MembersSwitch;
import org.emftext.language.java.modifiers.util.ModifiersSwitch;
//import org.emftext.language.java.modules.AccessProvidingModuleDirective;
//import org.emftext.language.java.modules.ModuleReference;
//import org.emftext.language.java.modules.ProvidesModuleDirective;
//import org.emftext.language.java.modules.RequiresModuleDirective;
//import org.emftext.language.java.modules.UsesModuleDirective;
//import org.emftext.language.java.modules.util.ModulesSwitch;
import org.emftext.language.java.operators.AssignmentOperator;
import org.emftext.language.java.operators.EqualityOperator;
import org.emftext.language.java.operators.RelationOperator;
import org.emftext.language.java.operators.UnaryOperator;
import org.emftext.language.java.operators.util.OperatorsSwitch;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.util.ParametersSwitch;
import org.emftext.language.java.references.ElementReference;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.MethodCall;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.references.StringReference;
import org.emftext.language.java.references.util.ReferencesSwitch;
import org.emftext.language.java.statements.CatchBlock;
import org.emftext.language.java.statements.Conditional;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Jump;
import org.emftext.language.java.statements.JumpLabel;
import org.emftext.language.java.statements.LocalVariableStatement;
import org.emftext.language.java.statements.Return;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementListContainer;
import org.emftext.language.java.statements.Switch;
import org.emftext.language.java.statements.SynchronizedBlock;
import org.emftext.language.java.statements.Throw;
import org.emftext.language.java.statements.TryBlock;
import org.emftext.language.java.statements.util.StatementsSwitch;
import org.emftext.language.java.types.ClassifierReference;
//import org.emftext.language.java.types.InferableType;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.util.TypesSwitch;
import org.emftext.language.java.variables.AdditionalLocalVariable;
import org.emftext.language.java.variables.Variable;
import org.emftext.language.java.variables.util.VariablesSwitch;

import com.google.common.base.Strings;

/**
 * Internal switch class to prove element similarity.
 * 
 * <p>
 * The similarity case methods do not need to check for null values. It is assumed that the calling class does a null value check for the elements to compare in advanced, such as done by the SimilarityChecker class.
 * </p>
 * 
 * <p>
 * Check strategy:<br>
 * First all "not-similar"-criteria are checked. If none hits, true will be returned.
 * </p>
 */
public class SimilaritySwitch extends ComposedSwitch<Boolean> {

	/** The logger for this class. */
	private Logger logger = Logger.getLogger(SimilaritySwitch.class);

	/** The object to compare the switched element with. */
	private EObject compareElement = null;

	/** Internal similarity checker to compare container elements etc. */
	private SimilarityChecker similarityChecker = null;

	/**
	 * Constructor requiring the element to compare with.
	 * 
	 * @param compareElement                The right-side / original element to check the similarity against.
	 * @param checkStatementPosition        Flag if the similarity check should consider the position of a statement or not.
	 * @param classifierNormalizations      A list of patterns replace any match in a classifier name with the defined replacement string.
	 * @param compilationUnitNormalizations A list of patterns replace any match in a compilation unit name with the defined replacement string.
	 * @param packageNormalizations         A list of package normalization patterns.
	 */
	public SimilaritySwitch(EObject compareElement, boolean checkStatementPosition, LinkedHashMap<Pattern, String> classifierNormalizations, LinkedHashMap<Pattern, String> compilationUnitNormalizations, LinkedHashMap<Pattern, String> packageNormalizations) {
		this.similarityChecker = new SimilarityChecker(classifierNormalizations, compilationUnitNormalizations, packageNormalizations);
		this.compareElement = compareElement;
		addSwitch(new AnnotationsSimilaritySwitch());
		addSwitch(new ArraysSimilaritySwitch());
		addSwitch(new ClassifiersSimilaritySwitch(classifierNormalizations));
		addSwitch(new CommonsSimilaritySwitch());
		addSwitch(new ContainersSimilaritySwitch(compilationUnitNormalizations, packageNormalizations));
		addSwitch(new ExpressionsSimilaritySwitch());
		addSwitch(new GenericsSimilaritySwitch());
		addSwitch(new ImportsSimilaritySwitch());
		addSwitch(new InstantiationsSimilaritySwitch());
		addSwitch(new LiteralsSimilaritySwitch());
		addSwitch(new MembersSimilaritySwitch());
		addSwitch(new ModifiersSimilaritySwitch());
		addSwitch(new OperatorsSimilaritySwitch());
		addSwitch(new ParametersSimilaritySwitch());
		addSwitch(new ReferencesSimilaritySwitch());
		addSwitch(new StatementsSimilaritySwitch(checkStatementPosition));
		addSwitch(new TypesSimilaritySwitch());
		addSwitch(new VariablesSimilaritySwitch());
		addSwitch(new LayoutSimilaritySwitch());
//		addSwitch(new ModulesSimilaritySwitch());
	}

	/**
	 * Similarity decisions for annotation elements.
	 */
	private class AnnotationsSimilaritySwitch extends AnnotationsSwitch<Boolean> {

		@Override
		public Boolean caseAnnotationInstance(AnnotationInstance instance1) {
			AnnotationInstance instance2 = (AnnotationInstance) compareElement;

			Classifier class1 = instance1.getAnnotation();
			Classifier class2 = instance2.getAnnotation();
			Boolean classifierSimilarity = similarityChecker.isSimilar(class1, class2);
			if (classifierSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			String namespace1 = instance1.getNamespacesAsString();
			String namespace2 = instance2.getNamespacesAsString();
			if (namespace1 == null) {
				return (namespace2 == null);
			} else {
				return (namespace1.equals(namespace2));
			}
		}

		@Override
		public Boolean caseAnnotationAttributeSetting(AnnotationAttributeSetting setting1) {
			AnnotationAttributeSetting setting2 = (AnnotationAttributeSetting) compareElement;
			Boolean similarity = similarityChecker.isSimilar(setting1.getAttribute(), setting2.getAttribute());
			if (similarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decision for array elements.
	 * <p>
	 * All array elements are strongly typed. They have no identifying attributes. Their location and runtime type are assumed to be checked before this switch is called. So nothing to check here.
	 */
	private class ArraysSimilaritySwitch extends ArraysSwitch<Boolean> {
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for classifier elements.
	 */
	private class ClassifiersSimilaritySwitch extends ClassifiersSwitch<Boolean> {

		/**
		 * A list of patterns replace any match in a classifier name with the defined replacement string.
		 */
		private Map<Pattern, String> classifierNormalizationPatterns = null;

		/**
		 * Constructor to set the required configurations.
		 * 
		 * @param classifierNormalizationPatterns A list of patterns replace any match in a classifier name with the defined replacement string.
		 */
		public ClassifiersSimilaritySwitch(Map<Pattern, String> classifierNormalizationPatterns) {
			this.classifierNormalizationPatterns = classifierNormalizationPatterns;
		}

		/**
		 * Concrete classifiers such as classes and interface are identified by their location and name. The location is considered implicitly.
		 * 
		 * @param classifier1 the classifier to compare with the compareelement
		 * @return True or false whether they are similar or not.
		 */
		@Override
		public Boolean caseConcreteClassifier(ConcreteClassifier classifier1) {

			ConcreteClassifier classifier2 = (ConcreteClassifier) compareElement;

			String name1 = NormalizationUtil.normalize(classifier1.getQualifiedName(), classifierNormalizationPatterns);
			String name2 = Strings.nullToEmpty(classifier2.getQualifiedName());

			return (name1.equals(name2));
		}

		/**
		 * Anonymous classes are considered to be similar.
		 * 
		 * @param anon the anonymous class to compare with the compare element.
		 * @return true.
		 */
		@Override
		public Boolean caseAnonymousClass(AnonymousClass anon) {
			return Boolean.TRUE;
		}

	}

	/**
	 * Similarity decisions for commons elements.
	 */
	private class CommonsSimilaritySwitch extends CommonsSwitch<Boolean> {

		/**
		 * Check named element
		 * 
		 * Similarity is defined by the names of the elements.
		 * 
		 * @param element1 The method call to compare with the compare element.
		 * @return True As null always means null.
		 */
		@Override
		public Boolean caseNamedElement(NamedElement element1) {
			NamedElement element2 = (NamedElement) compareElement;

			if (element1.getName() == null) {
				return (element2.getName() == null);
			}

			return (element1.getName().equals(element2.getName()));
		}
	}

	/**
	 * Similarity decisions for container elements.
	 */
	private class ContainersSimilaritySwitch extends ContainersSwitch<Boolean> {

		private LinkedHashMap<Pattern, String> compilationUnitNormalizations = null;

		private LinkedHashMap<Pattern, String> packageNormalizations = null;

		/**
		 * Constructor to set the required configurations.
		 * 
		 * @param compilationUnitNormalizations A list of patterns replace any match in a classifier name with the defined replacement string.
		 * @param packageNormalizations         A list of package normalization patterns.
		 */
		public ContainersSimilaritySwitch(LinkedHashMap<Pattern, String> compilationUnitNormalizations, LinkedHashMap<Pattern, String> packageNormalizations) {
			this.compilationUnitNormalizations = compilationUnitNormalizations;
			this.packageNormalizations = packageNormalizations;
		}

		/**
		 * Check the similarity of two CompilationUnits.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>Comparing their names (including renamings)</li>
		 * <li>Comparing their namespaces' values (including renamings)</li>
		 * </ul>
		 * Note: CompilationUnit names are full qualified. So it is important to apply classifier as well as package renaming normalizations to them.
		 * 
		 * @param unit1 The compilation unit to compare with the compareElement.
		 * @return True/False whether they are similar or not.
		 */
		@Override
		public Boolean caseCompilationUnit(CompilationUnit unit1) {

			CompilationUnit unit2 = (CompilationUnit) compareElement;

			String name1 = NormalizationUtil.normalize(unit1.getName(), compilationUnitNormalizations);
			name1 = NormalizationUtil.normalize(name1, packageNormalizations);
			String name2 = unit2.getName();
			if (!name1.equals(name2)) {
				return Boolean.FALSE;
			}

			String namespaceString1 = NormalizationUtil.normalizeNamespace(unit1.getNamespacesAsString(), packageNormalizations);
			String namespaceString2 = Strings.nullToEmpty(unit2.getNamespacesAsString());
			if (!namespaceString1.equals(namespaceString2)) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		/**
		 * Check package similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>full qualified package path</li>
		 * </ul>
		 * 
		 * @param package1 The package to compare with the compare element.
		 * @return True/False if the packages are similar or not.
		 */
		@Override
		public Boolean casePackage(Package package1) {
			Package package2 = (Package) compareElement;

			String packagePath1 = JaMoPPModelUtil.buildNamespacePath(package1);
			packagePath1 = NormalizationUtil.normalizeNamespace(packagePath1, packageNormalizations);
			String packagePath2 = JaMoPPModelUtil.buildNamespacePath(package2);
			if (!packagePath1.equals(packagePath2)) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

//		/**
//		 * Check module similarity.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>module names</li>
//		 * </ul>
//		 * 
//		 * @param module1 The module to compare with the compare element.
//		 * @return True/False if the modules are similar or not.
//		 */
//		@Override
//		public Boolean caseModule(org.emftext.language.java.containers.Module module1) {
//			org.emftext.language.java.containers.Module module2 = (org.emftext.language.java.containers.Module) compareElement;
//			if (!module1.getName().equals(module2.getName())) {
//				return Boolean.FALSE;
//			}
//			return Boolean.TRUE;
//		}
	}

	/**
	 * Similarity decisions for expression elements.
	 * <p>
	 * All expression elements are strong typed with no identifying attributes or non-containment references. Their location and runtime types are assumed to be checked before this switch is called.
	 * </p>
	 */
	private class ExpressionsSimilaritySwitch extends ExpressionsSwitch<Boolean> {

		@Override
		public Boolean caseAssignmentExpression(AssignmentExpression exp1) {

			AssignmentExpression exp2 = (AssignmentExpression) compareElement;

			AssignmentExpressionChild child1 = exp1.getChild();
			AssignmentExpressionChild child2 = exp2.getChild();
			Boolean childSimilarity = similarityChecker.isSimilar(child1, child2);
			if (childSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			AssignmentOperator op1 = exp1.getAssignmentOperator();
			AssignmentOperator op2 = exp2.getAssignmentOperator();
			Boolean operatorSimilarity = similarityChecker.isSimilar(op1, op2);
			if (operatorSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			Expression value1 = exp1.getValue();
			Expression value2 = exp2.getValue();
			Boolean valueSimilarity = similarityChecker.isSimilar(value1, value2);
			if (valueSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseEqualityExpression(EqualityExpression exp1) {
			EqualityExpression exp2 = (EqualityExpression) compareElement;

			// check operator equality
			EList<EqualityOperator> operators1 = exp1.getEqualityOperators();
			EList<EqualityOperator> operators2 = exp2.getEqualityOperators();
			Boolean operatorSimilarity = similarityChecker.areSimilar(operators1, operators2);
			if (operatorSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check expression equality
			EList<EqualityExpressionChild> children1 = exp1.getChildren();
			EList<EqualityExpressionChild> children2 = exp2.getChildren();
			Boolean childSimilarity = similarityChecker.areSimilar(children1, children2);
			if (childSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseRelationExpression(RelationExpression exp1) {

			RelationExpression exp2 = (RelationExpression) compareElement;

			// check operator equality
			EList<RelationOperator> operators1 = exp1.getRelationOperators();
			EList<RelationOperator> operators2 = exp2.getRelationOperators();
			Boolean operatorSimilarity = similarityChecker.areSimilar(operators1, operators2);
			if (operatorSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check expression equality
			EList<RelationExpressionChild> children1 = exp1.getChildren();
			EList<RelationExpressionChild> children2 = exp2.getChildren();
			Boolean childSimilarity = similarityChecker.areSimilar(children1, children2);
			if (childSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseAndExpression(AndExpression exp1) {

			AndExpression exp2 = (AndExpression) compareElement;

			// check expression equality
			EList<AndExpressionChild> children1 = exp1.getChildren();
			EList<AndExpressionChild> children2 = exp2.getChildren();
			Boolean childSimilarity = similarityChecker.areSimilar(children1, children2);
			if (childSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseUnaryExpression(UnaryExpression exp1) {

			UnaryExpression exp2 = (UnaryExpression) compareElement;

			// check operator equality
			EList<UnaryOperator> operators1 = exp1.getOperators();
			EList<UnaryOperator> operators2 = exp2.getOperators();
			Boolean operatorSimilarity = similarityChecker.areSimilar(operators1, operators2);
			if (operatorSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check expression equality
			UnaryExpressionChild child1 = exp1.getChild();
			UnaryExpressionChild child2 = exp2.getChild();
			return similarityChecker.isSimilar(child1, child2);
		}

		@Override
		public Boolean caseAdditiveExpression(AdditiveExpression exp1) {

			AdditiveExpression exp2 = (AdditiveExpression) compareElement;

			Boolean opSimilarity = similarityChecker.areSimilar(exp1.getAdditiveOperators(), exp2.getAdditiveOperators());
			if (opSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return similarityChecker.areSimilar(exp1.getChildren(), exp2.getChildren());
		}

		@Override
		public Boolean caseInstanceOfExpression(InstanceOfExpression exp1) {

			InstanceOfExpression exp2 = (InstanceOfExpression) compareElement;

			// check type equality
			TypeReference typeReference1 = exp1.getTypeReference();
			TypeReference typeReference2 = exp2.getTypeReference();
			Boolean typeSimilarity = similarityChecker.isSimilar(typeReference1, typeReference2);
			if (typeSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check expression equality
			InstanceOfExpressionChild child1 = exp1.getChild();
			InstanceOfExpressionChild child2 = exp2.getChild();
			return similarityChecker.isSimilar(child1, child2);
		}

		@Override
		public Boolean caseConditionalOrExpression(ConditionalOrExpression exp1) {

			ConditionalOrExpression exp2 = (ConditionalOrExpression) compareElement;

			// check expression equality
			EList<ConditionalOrExpressionChild> children1 = exp1.getChildren();
			EList<ConditionalOrExpressionChild> children2 = exp2.getChildren();
			return similarityChecker.areSimilar(children1, children2);
		}

		@Override
		public Boolean caseConditionalAndExpression(ConditionalAndExpression exp1) {

			ConditionalAndExpression exp2 = (ConditionalAndExpression) compareElement;

			// check expression equality
			EList<ConditionalAndExpressionChild> children1 = exp1.getChildren();
			EList<ConditionalAndExpressionChild> children2 = exp2.getChildren();
			return similarityChecker.areSimilar(children1, children2);
		}

		@Override
		public Boolean caseNestedExpression(NestedExpression exp1) {

			NestedExpression exp2 = (NestedExpression) compareElement;

			// check expression equality
			Expression childExp1 = exp1.getExpression();
			Expression childExp2 = exp2.getExpression();
			return similarityChecker.isSimilar(childExp1, childExp2);
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for the generic elements.
	 */
	private class GenericsSimilaritySwitch extends GenericsSwitch<Boolean> {
		@Override
		public Boolean caseQualifiedTypeArgument(QualifiedTypeArgument qta1) {
			QualifiedTypeArgument qta2 = (QualifiedTypeArgument) compareElement;
			return similarityChecker.isSimilar(qta1.getTypeReference(), qta2.getTypeReference());
		}

		@Override
		public Boolean caseSuperTypeArgument(SuperTypeArgument sta1) {
			SuperTypeArgument sta2 = (SuperTypeArgument) compareElement;
			return similarityChecker.isSimilar(sta1.getSuperType(), sta2.getSuperType());
		}

		@Override
		public Boolean caseExtendsTypeArgument(ExtendsTypeArgument eta1) {
			ExtendsTypeArgument eta2 = (ExtendsTypeArgument) compareElement;
			return similarityChecker.isSimilar(eta1.getExtendType(), eta2.getExtendType());
		}

		@Override
		public Boolean caseUnknownTypeArgument(UnknownTypeArgument arg) {
			return Boolean.TRUE;
		}

		@Override
		public Boolean caseTypeParameter(TypeParameter param1) {
			TypeParameter param2 = (TypeParameter) compareElement;

			if (!param1.getName().equals(param2.getName())) {
				return Boolean.FALSE;
			}

			return similarityChecker.areSimilar(param1.getExtendTypes(), param2.getExtendTypes());
		}
	}

	/**
	 * Similarity decisions for the import elements.
	 */
	private class ImportsSimilaritySwitch extends ImportsSwitch<Boolean> {

		@Override
		public Boolean caseClassifierImport(ClassifierImport import1) {

			ClassifierImport import2 = (ClassifierImport) compareElement;

			Boolean similarity = similarityChecker.isSimilar(import1.getClassifier(), import2.getClassifier());
			if (similarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			String namespace1 = Strings.nullToEmpty(import1.getNamespacesAsString());
			String namespace2 = Strings.nullToEmpty(import2.getNamespacesAsString());
			return (namespace1.equals(namespace2));
		}

		@Override
		public Boolean caseStaticMemberImport(StaticMemberImport import1) {

			StaticMemberImport import2 = (StaticMemberImport) compareElement;

			if (import1.getStaticMembers().size() != import2.getStaticMembers().size()) {
				return Boolean.FALSE;
			}
			for (int i = 0; i < import1.getStaticMembers().size(); i++) {
				ReferenceableElement member1 = import1.getStaticMembers().get(i);
				ReferenceableElement member2 = import2.getStaticMembers().get(i);
				Boolean similarity = similarityChecker.isSimilar(member1, member2);
				if (similarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
			}

			String namespace1 = Strings.nullToEmpty(import1.getNamespacesAsString());
			String namespace2 = Strings.nullToEmpty(import2.getNamespacesAsString());
			return (namespace1.equals(namespace2));
		}
	}

	/**
	 * Similarity decisions for object instantiation elements.
	 */
	private class InstantiationsSimilaritySwitch extends InstantiationsSwitch<Boolean> {

		/**
		 * Check class instance creation similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>instance type similarity</li>
		 * <li>number of constructor arguments</li>
		 * <li>types of constructor arguments</li>
		 * </ul>
		 * 
		 * @param call1 The class instance creation to compare with the compare element.
		 * @return True/False if the class instance creations are similar or not.
		 */
		@Override
		public Boolean caseExplicitConstructorCall(ExplicitConstructorCall call1) {

			ExplicitConstructorCall call2 = (ExplicitConstructorCall) compareElement;

			// check the class instance types
			Boolean typeSimilarity = similarityChecker.isSimilar(call1.getCallTarget(), call2.getCallTarget());
			if (typeSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check number of type arguments
			EList<Expression> cic1Args = call1.getArguments();
			EList<Expression> cic2Args = call2.getArguments();
			if (cic1Args.size() != cic2Args.size()) {
				return Boolean.FALSE;
			}

			// check the argument similarity
			for (int i = 0; i < cic1Args.size(); i++) {
				Boolean argumentSimilarity = similarityChecker.isSimilar(cic1Args.get(i), cic2Args.get(i));
				if (argumentSimilarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseNewConstructorCall(NewConstructorCall call1) {
			NewConstructorCall call2 = (NewConstructorCall) compareElement;

			Type type1 = call1.getTypeReference().getTarget();
			Type type2 = call2.getTypeReference().getTarget();
			Boolean typeSimilarity = similarityChecker.isSimilar(type1, type2);
			if (typeSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			EList<Expression> types1 = call1.getArguments();
			EList<Expression> types2 = call2.getArguments();
			if (types1.size() != types2.size()) {
				return Boolean.FALSE;
			}
			for (int i = 0; i < types1.size(); i++) {
				Expression argType1 = types1.get(i);
				Expression argType2 = types2.get(i);
				Boolean similarity = similarityChecker.isSimilar(argType1, argType2);
				if (similarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for literal elements.
	 */
	private class LiteralsSimilaritySwitch extends LiteralsSwitch<Boolean> {

		@Override
		public Boolean caseBooleanLiteral(BooleanLiteral boolean1) {
			BooleanLiteral boolean2 = (BooleanLiteral) compareElement;
			return (boolean1.isValue() == boolean2.isValue());
		}

		@Override
		public Boolean caseCharacterLiteral(CharacterLiteral char1) {
			CharacterLiteral char2 = (CharacterLiteral) compareElement;
			return char1.getValue() == char2.getValue();
		}

		@Override
		public Boolean caseDecimalFloatLiteral(DecimalFloatLiteral float1) {
			DecimalFloatLiteral float2 = (DecimalFloatLiteral) compareElement;
			return compareDouble(float1.getDecimalValue(), float2.getDecimalValue());
		}

		@Override
		public Boolean caseHexFloatLiteral(HexFloatLiteral float1) {
			HexFloatLiteral float2 = (HexFloatLiteral) compareElement;
			return compareDouble(float1.getHexValue(), float2.getHexValue());
		}

		@Override
		public Boolean caseDecimalDoubleLiteral(DecimalDoubleLiteral double1) {
			DecimalDoubleLiteral double2 = (DecimalDoubleLiteral) compareElement;
			return compareDouble(double1.getDecimalValue(), double2.getDecimalValue());
		}

		@Override
		public Boolean caseHexDoubleLiteral(HexDoubleLiteral double1) {
			HexDoubleLiteral double2 = (HexDoubleLiteral) compareElement;
			return compareDouble(double1.getHexValue(), double2.getHexValue());
		}

		private boolean compareDouble(double d1, double d2) {
			return d1 == d2 || Double.isNaN(d1) && Double.isNaN(d2);
		}

		@Override
		public Boolean caseDecimalIntegerLiteral(DecimalIntegerLiteral int1) {
			DecimalIntegerLiteral int2 = (DecimalIntegerLiteral) compareElement;
			return (int1.getDecimalValue().equals(int2.getDecimalValue()));
		}

		@Override
		public Boolean caseHexIntegerLiteral(HexIntegerLiteral int1) {
			HexIntegerLiteral int2 = (HexIntegerLiteral) compareElement;
			return (int1.getHexValue().equals(int2.getHexValue()));
		}

		@Override
		public Boolean caseOctalIntegerLiteral(OctalIntegerLiteral int1) {
			OctalIntegerLiteral int2 = (OctalIntegerLiteral) compareElement;
			return (int1.getOctalValue().equals(int2.getOctalValue()));
		}

		@Override
		public Boolean caseDecimalLongLiteral(DecimalLongLiteral long1) {
			DecimalLongLiteral long2 = (DecimalLongLiteral) compareElement;
			return (long1.getDecimalValue().equals(long2.getDecimalValue()));
		}

		@Override
		public Boolean caseHexLongLiteral(HexLongLiteral long1) {
			HexLongLiteral long2 = (HexLongLiteral) compareElement;
			return (long1.getHexValue().equals(long2.getHexValue()));
		}

		@Override
		public Boolean caseOctalLongLiteral(OctalLongLiteral long1) {
			OctalLongLiteral long2 = (OctalLongLiteral) compareElement;
			return (long1.getOctalValue().equals(long2.getOctalValue()));
		}

//		@Override
//		public Boolean caseBinaryLongLiteral(BinaryLongLiteral long1) {
//			BinaryLongLiteral long2 = (BinaryLongLiteral) compareElement;
//			return long1.getBinaryValue().equals(long2.getBinaryValue());
//		}
//
//		@Override
//		public Boolean caseBinaryIntegerLiteral(BinaryIntegerLiteral int1) {
//			BinaryIntegerLiteral int2 = (BinaryIntegerLiteral) compareElement;
//			return int1.getBinaryValue().equals(int2.getBinaryValue());
//		}

		/**
		 * Check null literal similarity.<br>
		 * 
		 * Null literals are always assumed to be similar.
		 * 
		 * @param object The literal to compare with the compare element.
		 * @return True As null always means null.
		 */
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for the member elements.
	 */
	private class MembersSimilaritySwitch extends MembersSwitch<Boolean> {

		/**
		 * Check abstract method declaration similarity. Similarity is checked by
		 * <ul>
		 * <li>name</li>
		 * <li>parameter list size</li>
		 * <li>parameter types</li>
		 * <li>name</li>
		 * <li>container for
		 * <ul>
		 * <li>AbstractTypeDeclaration</li>
		 * <li>AnonymousClassDeclaration</li>
		 * <li>Model</li>
		 * </ul>
		 * </li>
		 * </ul>
		 * 
		 * The container must be checked to check similarity for referenced methods.
		 * 
		 * 
		 * @param method1 The abstract method declaration to compare with the compare element.
		 * @return True/False if the abstract method declarations are similar or not.
		 */
		@Override
		public Boolean caseMethod(Method method1) {

			Method method2 = (Method) compareElement;

			// if methods have different names they are not similar.
			if (!method1.getName().equals(method2.getName())) {
				return Boolean.FALSE;
			}

			if (method1.getParameters().size() != method2.getParameters().size()) {
				return Boolean.FALSE;
			}

			for (int i = 0; i < method1.getParameters().size(); i++) {
				Parameter param1 = method1.getParameters().get(i);
				Parameter param2 = method2.getParameters().get(i);
				Type type1 = param1.getTypeReference().getTarget();
				Type type2 = param2.getTypeReference().getTarget();
				Boolean typeSimilarity = similarityChecker.isSimilar(type1, type2);
				if (typeSimilarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
				if (param1.getTypeReference().getArrayDimension() != param2.getTypeReference().getArrayDimension()) {
					return Boolean.FALSE;
				}
			}

			/*
			 * ************************************** methods as members of regular classes
			 */
			if (method1.getContainingConcreteClassifier() != null) {
				ConcreteClassifier type1 = method1.getContainingConcreteClassifier();
				ConcreteClassifier type2 = method2.getContainingConcreteClassifier();
				return similarityChecker.isSimilar(type1, type2);
			}

			/*
			 * ************************************** methods as members of anonymous classes
			 */
			if (method1.getContainingAnonymousClass() != null) {
				AnonymousClass type1 = method1.getContainingAnonymousClass();
				AnonymousClass type2 = method2.getContainingAnonymousClass();
				Boolean typeSimilarity = similarityChecker.isSimilar(type1, type2);
				if (typeSimilarity != null) {
					return typeSimilarity;
				}
			}

			logger.warn("MethodDeclaration in unknown container: " + method1.getName() + " : " + method1.eContainer());
			return super.caseMethod(method1);
		}

		/**
		 * Check constuctor declaration similarity. Similarity is checked by
		 * <ul>
		 * <li>name</li>
		 * <li>parameter list size</li>
		 * <li>parameter types</li>
		 * <li>name</li>
		 * <li>container for
		 * <ul>
		 * <li>AbstractTypeDeclaration</li>
		 * <li>AnonymousClassDeclaration</li>
		 * <li>Model</li>
		 * </ul>
		 * </li>
		 * </ul>
		 * 
		 * The container must be checked to check similarity for referenced methods.
		 * 
		 * 
		 * @param constructor1 The abstract method declaration to compare with the compare element.
		 * @return True/False if the abstract method declarations are similar or not.
		 */
		@Override
		public Boolean caseConstructor(Constructor constructor1) {

			Constructor constructor2 = (Constructor) compareElement;

			// if methods have different names they are not similar.
			if (!constructor1.getName().equals(constructor2.getName())) {
				return Boolean.FALSE;
			}

			EList<Parameter> params1 = constructor1.getParameters();
			EList<Parameter> params2 = constructor2.getParameters();
			Boolean parameterSimilarity = similarityChecker.areSimilar(params1, params2);
			if (parameterSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			/*
			 * ************************************** methods as members of regular classes
			 */
			if (constructor1.getContainingConcreteClassifier() != null) {
				ConcreteClassifier type1 = constructor1.getContainingConcreteClassifier();
				ConcreteClassifier type2 = constructor2.getContainingConcreteClassifier();
				return similarityChecker.isSimilar(type1, type2);
			}

			/*
			 * ************************************** methods as members of anonymous classes
			 */
			if (constructor1.getContainingAnonymousClass() != null) {
				AnonymousClass type1 = constructor1.getContainingAnonymousClass();
				AnonymousClass type2 = constructor2.getContainingAnonymousClass();
				Boolean typeSimilarity = similarityChecker.isSimilar(type1, type2);
				if (typeSimilarity != null) {
					return typeSimilarity;
				}
			}

			logger.warn("ConstructorDeclaration in unknown container: " + constructor1.getName() + " : " + constructor1.eContainer().getClass().getSimpleName());
			return super.caseConstructor(constructor1);
		}

		@Override
		public Boolean caseEnumConstant(EnumConstant const1) {
			EnumConstant const2 = (EnumConstant) compareElement;
			String name1 = Strings.nullToEmpty(const1.getName());
			String name2 = Strings.nullToEmpty(const2.getName());
			return (name1.equals(name2));
		}

		@Override
		public Boolean caseMember(Member member1) {
			Member member2 = (Member) compareElement;
			String name1 = Strings.nullToEmpty(member1.getName());
			String name2 = Strings.nullToEmpty(member2.getName());
			return (name1.equals(name2));
		}
	}

	/**
	 * Similarity decisions for modifier elements.
	 * <p>
	 * All modifier elements are strong typed with no identifying attributes or non-containment references. Their location and runtime types are assumed to be checked before this switch is called.
	 * </p>
	 */
	private class ModifiersSimilaritySwitch extends ModifiersSwitch<Boolean> {
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for operator elements.
	 * <p>
	 * All operator elements are strong typed with no identifying attributes or non-containment references. Their location and runtime types are assumed to be checked before this switch is called.
	 * </p>
	 */
	private class OperatorsSimilaritySwitch extends OperatorsSwitch<Boolean> {
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for parameter elements.
	 * <p>
	 * Parameters are variables and for this named elements. So their names must be checked but no more identifying attributes or references exist.
	 * </p>
	 */
	private class ParametersSimilaritySwitch extends ParametersSwitch<Boolean> {
		@Override
		public Boolean caseParameter(Parameter param1) {
			Parameter param2 = (Parameter) compareElement;
			String name1 = Strings.nullToEmpty(param1.getName());
			String name2 = Strings.nullToEmpty(param2.getName());
			return (name1.equals(name2));
		}
	}

	/**
	 * Similarity decisions for reference elements.
	 */
	private class ReferencesSimilaritySwitch extends ReferencesSwitch<Boolean> {

		@Override
		public Boolean caseStringReference(StringReference ref1) {

			StringReference ref2 = (StringReference) compareElement;
			if (ref1.getValue() == null) {
				return (ref2.getValue() == null);
			}

			return (ref1.getValue().equals(ref2.getValue()));
		}

		@Override
		public Boolean caseIdentifierReference(IdentifierReference ref1) {

			IdentifierReference ref2 = (IdentifierReference) compareElement;
			ReferenceableElement target1 = ref1.getTarget();
			ReferenceableElement target2 = ref2.getTarget();

			// target identity similarity
			Boolean similarity = similarityChecker.isSimilar(target1, target2);
			if (similarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			if (target1 != null) {
				// target container similarity
				// check this only if the reference target is located
				// in another container than the reference itself.
				// Otherwise such a situation would lead to endless loops
				// e.g. for for "(Iterator i = c.iterator(); i.hasNext(); ) {"
				// Attention: The reference could be encapsulated by an expression!
				EObject ref1Container = JaMoPPElementUtil.getFirstContainerNotOfGivenType(ref1, Expression.class, ArraySelector.class);
				EObject ref2Container = JaMoPPElementUtil.getFirstContainerNotOfGivenType(ref2, Expression.class, ArraySelector.class);
				EObject target1Container = target1.eContainer();
				EObject target2Container = target2.eContainer();
				if (target1Container != ref1Container && target2Container != ref2Container && target1Container != ref1 && target2Container != ref2) {
					Boolean containerSimilarity = similarityChecker.isSimilar(target1Container, target2Container);
					if (containerSimilarity == Boolean.FALSE) {
						return Boolean.FALSE;
					}
				}
			}

			if (ref1.getArraySelectors().size() != ref2.getArraySelectors().size()) {
				return Boolean.FALSE;
			}
			for (int i = 0; i < ref1.getArraySelectors().size(); i++) {
				ArraySelector selector1 = ref1.getArraySelectors().get(i);
				ArraySelector selector2 = ref2.getArraySelectors().get(i);
				Boolean positionSimilarity = similarityChecker.isSimilar(selector1.getPosition(), selector2.getPosition());
				if (positionSimilarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
			}

			Reference next1 = ref1.getNext();
			Reference next2 = ref2.getNext();
			Boolean nextSimilarity = similarityChecker.isSimilar(next1, next2);
			if (nextSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		/**
		 * Check element reference similarity.<br>
		 * 
		 * Is checked by the target (the method called). Everything else are containment references checked indirectly.
		 * 
		 * @param ref1 The method call to compare with the compare element.
		 * @return True As null always means null.
		 */
		@Override
		public Boolean caseElementReference(ElementReference ref1) {
			ElementReference ref2 = (ElementReference) compareElement;

			Boolean targetSimilarity = similarityChecker.isSimilar(ref1.getTarget(), ref2.getTarget());
			if (targetSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		/**
		 * Proof method call similarity.
		 * 
		 * Similarity is decided by the method referenced and the arguments passed by.
		 * 
		 * @param call1 The left / modified method call to compare with the original one.
		 * @return True/False if the method calls are similar or not.
		 */
		@Override
		public Boolean caseMethodCall(MethodCall call1) {
			MethodCall call2 = (MethodCall) compareElement;

			Boolean targetSimilarity = similarityChecker.isSimilar(call1.getTarget(), call2.getTarget());
			if (targetSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			if (call1.getArguments().size() != call2.getArguments().size()) {
				return Boolean.FALSE;
			}

			for (int i = 0; i < call1.getArguments().size(); i++) {
				Expression exp1 = call1.getArguments().get(i);
				Expression exp2 = call2.getArguments().get(i);
				Boolean argSimilarity = similarityChecker.isSimilar(exp1, exp2);
				if (argSimilarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
			}

			Boolean nextSimilarity = similarityChecker.isSimilar(call1.getNext(), call2.getNext());
			if (nextSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for the statement elements.
	 */
	private class StatementsSimilaritySwitch extends StatementsSwitch<Boolean> {

		/**
		 * Flag if the position of a statement should be considered for similarity or not.
		 */
		private boolean checkStatementPosition = true;

		/**
		 * Constructor to set required configurations.
		 * 
		 * @param checkStatementPosition Flag if the position of a statement should be considered for similarity or not.
		 */
		public StatementsSimilaritySwitch(boolean checkStatementPosition) {
			this.checkStatementPosition = checkStatementPosition;
		}

		/**
		 * Check expression statement similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>similarity statements expressions</li>
		 * </ul>
		 * 
		 * @param statement1 The expression statement to compare with the compare element.
		 * @return True/False if the expression statements are similar or not.
		 */
		@Override
		public Boolean caseExpressionStatement(ExpressionStatement statement1) {

			ExpressionStatement statement2 = (ExpressionStatement) compareElement;

			Expression exp1 = statement1.getExpression();
			Expression exp2 = statement2.getExpression();

			Boolean expSimilarity = similarityChecker.isSimilar(exp1, exp2);
			if (expSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			// check predecessor similarity
			if (checkStatementPosition) {
				if (differentPredecessor(statement1, statement2) && differentSuccessor(statement1, statement2)) {
					return Boolean.FALSE;
				}
			}

			return Boolean.TRUE;
		}

		/**
		 * Check the similarity of a variable declaration.
		 * 
		 * The similarity is decided by the declared variables name only. A changed variable type or value initialization should lead to a changed statement not a new one.
		 * 
		 * @param varStmt1 The variable to compare with the original / right-side one
		 * @return True/False if they are similar or not.
		 */
		@Override
		public Boolean caseLocalVariableStatement(LocalVariableStatement varStmt1) {
			LocalVariableStatement varStmt2 = (LocalVariableStatement) compareElement;

			Variable var1 = varStmt1.getVariable();
			Variable var2 = varStmt2.getVariable();
			Boolean varSimilarity = similarityChecker.isSimilar(var1, var2);
			if (varSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		/**
		 * Check return statement similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>expressions similarity</li>
		 * </ul>
		 * 
		 * @param returnStatement1 The return statement to compare with the compare element.
		 * @return True/False if the return statements are similar or not.
		 */
		@Override
		public Boolean caseReturn(Return returnStatement1) {

			Return returnStatement2 = (Return) compareElement;

			Expression exp1 = returnStatement1.getReturnValue();
			Expression exp2 = returnStatement2.getReturnValue();

			return similarityChecker.isSimilar(exp1, exp2);
		}

		/**
		 * Check synchronized statement similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>expression similarity</li>
		 * </ul>
		 * 
		 * @param statement1 The synchronized statement to compare with the compare element.
		 * @return True/False if the synchronized statements are similar or not.
		 */
		@Override
		public Boolean caseSynchronizedBlock(SynchronizedBlock statement1) {

			SynchronizedBlock statement2 = (SynchronizedBlock) compareElement;

			Expression exp1 = statement1.getLockProvider();
			Expression exp2 = statement2.getLockProvider();
			Boolean similarity = similarityChecker.isSimilar(exp1, exp2);
			if (similarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			if (checkStatementPosition) {
				if (differentPredecessor(statement1, statement2) && differentSuccessor(statement1, statement2)) {
					return Boolean.FALSE;
				}
			}
			return Boolean.TRUE;
		}

		/**
		 * Check throw statement similarity.<br>
		 * 
		 * Only one throw statement can exist at the same code location. As a result the container similarity checked implicitly is enough for this.
		 * 
		 * @param throwStatement1 The throw statement to compare with the compare element.
		 * @return True/False if the throw statements are similar or not.
		 */
		@Override
		public Boolean caseThrow(Throw throwStatement1) {
			return Boolean.TRUE;
		}

		@Override
		public Boolean caseTryBlock(TryBlock try1) {
			TryBlock try2 = (TryBlock) compareElement;

			if (checkStatementPosition && differentPredecessor(try1, try2)) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseCatchBlock(CatchBlock catchBlock1) {

			CatchBlock catchBlock2 = (CatchBlock) compareElement;

			OrdinaryParameter catchedException1 = catchBlock1.getParameter();
			OrdinaryParameter catchedException2 = catchBlock2.getParameter();

			Boolean exceptionSimilarity = similarityChecker.isSimilar(catchedException1, catchedException2);
			if (exceptionSimilarity == Boolean.FALSE) {
				return exceptionSimilarity;
			}

			return Boolean.TRUE;
		}

		/**
		 * Check if two conditional statements are similar.
		 * 
		 * Similarity is checked by:
		 * <ul>
		 * <li>similarity of the expressions</li>
		 * </ul>
		 * 
		 * The then and else statements are not checked as part of the condition statement check because this is only about the container if statement similarity. The contained statements are checked in a separate step of the compare process if the enclosing condition statement matches.
		 * 
		 * @param conditional1 The statement to compare with the compare element.
		 * @return True/False whether they are similar or not.
		 */
		@Override
		public Boolean caseConditional(Conditional conditional1) {

			Conditional conditional2 = (Conditional) compareElement;

			Expression expression1 = conditional1.getCondition();
			Expression expression2 = conditional2.getCondition();
			Boolean expressionSimilarity = similarityChecker.isSimilar(expression1, expression2);
			if (expressionSimilarity == Boolean.FALSE) {
				return expressionSimilarity;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseJump(Jump jump1) {
			Jump jump2 = (Jump) compareElement;

			Boolean similarity = similarityChecker.isSimilar(jump1.getTarget(), jump2.getTarget());
			if (similarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseJumpLabel(JumpLabel label1) {

			JumpLabel label2 = (JumpLabel) compareElement;

			String name1 = Strings.nullToEmpty(label1.getName());
			String name2 = Strings.nullToEmpty(label2.getName());

			return (name1.equals(name2));
		}

		@Override
		public Boolean caseSwitch(Switch switch1) {
			Switch switch2 = (Switch) compareElement;

			return similarityChecker.isSimilar(switch1.getVariable(), switch2.getVariable());
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}

		/**
		 * Decide of two statements differ from each other or not.
		 * 
		 * @param statement1 The first statement to compare
		 * @param statement2 The second statement to compare.
		 * @return True if they differ, null if not.
		 */
		private boolean differentPredecessor(Statement statement1, Statement statement2) {
			Statement pred1 = getPredecessor(statement1);
			Statement pred2 = getPredecessor(statement2);
			Boolean similarity = similarityChecker.isSimilar(pred1, pred2, false);
			return similarity == Boolean.FALSE;
		}

		/**
		 * Check if two statements have differing successor statements.
		 * 
		 * @param statement1 The first statement to check.
		 * @param statement2 The second statement to check.
		 * @return True if their successor differ, false if not.
		 */
		private boolean differentSuccessor(Statement statement1, Statement statement2) {
			Statement pred1 = getSuccessor(statement1);
			Statement pred2 = getSuccessor(statement2);
			Boolean similarity = similarityChecker.isSimilar(pred1, pred2, false);
			return similarity == Boolean.FALSE;
		}

		/**
		 * Get the predecessor statement of a statement within the parents container statement list.<br>
		 * If a statement is the first, the only one, or the container is not a {@link StatementListContainer}, or no predecessor exists, null will be returned.
		 * 
		 * @param statement The statement to get the predecessor for.
		 * @return The predecessor or null if non exists.
		 */
		private Statement getPredecessor(Statement statement) {

			int pos = JaMoPPElementUtil.getPositionInContainer(statement);
			if (pos > 0) {
				StatementListContainer container = (StatementListContainer) statement.eContainer();
				return container.getStatements().get(pos - 1);
			}

			return null;
		}

		/**
		 * Get the successor statement of a statement within the parents container statement list.<br>
		 * If a statement is the last, the only one, or the container is not a {@link StatementListContainer}, no successor exists, null will be returned.
		 * 
		 * @param statement The statement to get the predecessor for.
		 * @return The predecessor or null if non exists.
		 */
		private Statement getSuccessor(Statement statement) {

			int pos = JaMoPPElementUtil.getPositionInContainer(statement);
			if (pos != -1) {
				StatementListContainer container = (StatementListContainer) statement.eContainer();
				if (container.getStatements().size() > pos + 1) {
					return container.getStatements().get(pos + 1);
				}
			}

			return null;
		}
	}

	/**
	 * Similarity decisions for elements of the types package.
	 */
	private class TypesSimilaritySwitch extends TypesSwitch<Boolean> {

		/**
		 * Check element reference similarity.<br>
		 * 
		 * Is checked by the target (the method called). Everything else are containment references checked indirectly.
		 * 
		 * @param ref1 The method call to compare with the compare element.
		 * @return True As null always means null.
		 */
		@Override
		public Boolean caseClassifierReference(ClassifierReference ref1) {
			ClassifierReference ref2 = (ClassifierReference) compareElement;

			Boolean targetSimilarity = similarityChecker.isSimilar(ref1.getTarget(), ref2.getTarget());
			if (targetSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseTypeReference(TypeReference ref1) {

			TypeReference ref2 = (TypeReference) compareElement;

			Boolean targetSimilarity = similarityChecker.isSimilar(ref1.getTarget(), ref2.getTarget());
			if (targetSimilarity == Boolean.FALSE) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseNamespaceClassifierReference(NamespaceClassifierReference ref1) {

			NamespaceClassifierReference ref2 = (NamespaceClassifierReference) compareElement;

			String namespace1 = Strings.nullToEmpty(ref1.getNamespacesAsString());
			String namespace2 = Strings.nullToEmpty(ref2.getNamespacesAsString());
			if (!namespace1.equals(namespace2)) {
				return Boolean.FALSE;
			}

			ClassifierReference pureRef1 = ref1.getPureClassifierReference();
			ClassifierReference pureRef2 = ref2.getPureClassifierReference();

			return similarityChecker.isSimilar(pureRef1, pureRef2);
		}

		/**
		 * Primitive types are always similar as their class similarity is assumed before by the outer {@link SimilarityChecker}.
		 * 
		 * Note: The fall back to the default case is not sufficient here, as the common TypeReference case would be used before, leading to a loop.
		 * 
		 * @param type The primitive type object.
		 * @return TRUE
		 */
		@Override
		public Boolean casePrimitiveType(PrimitiveType type) {
			return Boolean.TRUE;
		}

		/**
		 * Inferable types are considered to be similar.
		 * 
		 * @param type The element to compare with the compare element.
		 * @return true.
		 */
		@Override
		public Boolean caseInferableType(InferableType type) {
			return Boolean.TRUE;
		}

		/**
		 * Primitive type elements are strongly typed and the exact type is already checked by the outer {@link SimilarityChecker}. <br>
		 * {@inheritDoc}
		 */
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity decisions for the variable elements.
	 */
	private class VariablesSimilaritySwitch extends VariablesSwitch<Boolean> {

		/**
		 * Check variable declaration similarity.<br>
		 * Similarity is checked by
		 * <ul>
		 * <li>variable name</li>
		 * <li>variable container (name space)</li>
		 * </ul>
		 * 
		 * @param var1 The variable declaration to compare with the compare element.
		 * @return True/False if the variable declarations are similar or not.
		 */
		@Override
		public Boolean caseVariable(Variable var1) {

			Variable var2 = (Variable) compareElement;

			// check the variables name equality
			if (!var1.getName().equals(var2.getName())) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		@Override
		public Boolean caseAdditionalLocalVariable(AdditionalLocalVariable var1) {
			AdditionalLocalVariable var2 = (AdditionalLocalVariable) compareElement;

			// check the variables name equality
			String name1 = Strings.nullToEmpty(var1.getName());
			String name2 = Strings.nullToEmpty(var2.getName());
			if (!name1.equals(name2)) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}
	}

	/**
	 * Similarity Decisions for layout information is always true as they are not considered for now.
	 */
	private class LayoutSimilaritySwitch extends LayoutSwitch<Boolean> {
		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

//	/**
//	 * Similarity Decisions for module elements.
//	 */
//	private class ModulesSimilaritySwitch extends ModulesSwitch<Boolean> {
//		/**
//		 * Check ModuleReference similarity.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>module names</li>
//		 * </ul>
//		 * 
//		 * @param modRef1 The module reference to compare with the compare element.
//		 * @return True/False if the module references are similar or not.
//		 */
//		@Override
//		public Boolean caseModuleReference(ModuleReference modRef1) {
//			ModuleReference modRef2 = (ModuleReference) compareElement;
//			if (compareNamespacesByPart(modRef1, modRef2)) {
//				return Boolean.TRUE;
//			}
//			return Boolean.FALSE;
//		}
//
//		/**
//		 * Check similarity for access providing module directives.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>the provided package</li>
//		 * </ul>
//		 * 
//		 * @param dir1 The access providing module directive to compare with the compare element.
//		 * @return True/False if the module directives are similar or not.
//		 */
//		@Override
//		public Boolean caseAccessProvidingModuleDirective(AccessProvidingModuleDirective dir1) {
//			AccessProvidingModuleDirective dir2 = (AccessProvidingModuleDirective) compareElement;
//			if (!compareNamespacesByPart(dir1, dir2)) {
//				return Boolean.FALSE;
//			}
//			return Boolean.TRUE;
//		}
//
//		/**
//		 * Check similarity for require module directives.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>required modules</li>
//		 * </ul>
//		 * 
//		 * @param dir1 The require module directive to compare with the compare element.
//		 * @return True/False if the module directives are similar or not.
//		 */
//		@Override
//		public Boolean caseRequiresModuleDirective(RequiresModuleDirective dir1) {
//			RequiresModuleDirective dir2 = (RequiresModuleDirective) compareElement;
//			return similarityChecker.isSimilar(dir1.getRequiredModule(), dir2.getRequiredModule());
//		}
//
//		/**
//		 * Check similarity for provide module directives.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>provided types</li>
//		 * </ul>
//		 * 
//		 * @param dir1 The provide module directive to compare with the compare element.
//		 * @return True/False if the module directives are similar or not.
//		 */
//		@Override
//		public Boolean caseProvidesModuleDirective(ProvidesModuleDirective dir1) {
//			ProvidesModuleDirective dir2 = (ProvidesModuleDirective) compareElement;
//			return similarityChecker.isSimilar(dir1.getTypeReference(), dir2.getTypeReference());
//		}
//
//		/**
//		 * Check similarity for use module directives.<br>
//		 * Similarity is checked by
//		 * <ul>
//		 * <li>used types</li>
//		 * </ul>
//		 * 
//		 * @param dir1 The use module directive to compare with the compare element.
//		 * @return True/False if the module directives are similar or not.
//		 */
//		@Override
//		public Boolean caseUsesModuleDirective(UsesModuleDirective dir1) {
//			UsesModuleDirective dir2 = (UsesModuleDirective) compareElement;
//			return similarityChecker.isSimilar(dir1.getTypeReference(), dir2.getTypeReference());
//		}
//	}

	/**
	 * Compares the namespaces of two elements by comparing each part of the namespaces.
	 * 
	 * @param ele1 the first element.
	 * @param ele2 the second element to compare to the first element.
	 * @return true if the number of parts of the namespaces and each part in both namespaces are equal. false otherwise.
	 */
	private boolean compareNamespacesByPart(NamespaceAwareElement ele1, NamespaceAwareElement ele2) {
		if (ele1.getNamespaces().size() != ele2.getNamespaces().size()) {
			return false;
		}
		for (int idx = 0; idx < ele1.getNamespaces().size(); idx++) {
			if (!ele1.getNamespaces().get(idx).equals(ele2.getNamespaces().get(idx))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The default case for not explicitly handled elements always returns null to identify the open decision.
	 * 
	 * @param object The object to compare with the compare element.
	 * @return null
	 */
	@Override
	public Boolean defaultCase(EObject object) {
		return null;
	}
}
