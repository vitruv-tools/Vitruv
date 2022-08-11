package tools.vitruv.variability.vave.util;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.variability.vave.model.expression.BinaryExpression;
import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.Constant;
import tools.vitruv.variability.vave.model.expression.Disjunction;
import tools.vitruv.variability.vave.model.expression.Equivalence;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.False;
import tools.vitruv.variability.vave.model.expression.Implication;
import tools.vitruv.variability.vave.model.expression.NaryExpression;
import tools.vitruv.variability.vave.model.expression.Not;
import tools.vitruv.variability.vave.model.expression.True;
import tools.vitruv.variability.vave.model.expression.UnaryExpression;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.SystemRevision;

/**
 * Utility class for expressions.
 */
public final class ExpressionUtil {

	private ExpressionUtil() {
	}

	/*
	 * Compares two expression for structural equivalence.
	 */
	public static <T> boolean structuralEquivalence(Expression<T> e1, Expression<T> e2) {
		if (e1.eClass() != e2.eClass())
			return false;
		if (e1 instanceof NaryExpression) {
			for (int i = 0; i < ((NaryExpression<T>) e1).getExpressions().size(); i++) {
				if (!ExpressionUtil.<T>structuralEquivalence(((NaryExpression<T>) e1).getExpressions().get(i), ((NaryExpression<T>) e2).getExpressions().get(i))) {
					return false;
				}
			}
			return true;
		} else if (e1 instanceof BinaryExpression)
			return ExpressionUtil.<T>structuralEquivalence(((BinaryExpression<T>) e1).getLeft(), ((BinaryExpression<T>) e2).getLeft()) && ExpressionUtil.structuralEquivalence(((BinaryExpression<T>) e1).getRight(), ((BinaryExpression<T>) e2).getRight());
		else if (e1 instanceof UnaryExpression)
			return ExpressionUtil.<T>structuralEquivalence(((UnaryExpression<T>) e1).getExpression(), ((UnaryExpression<T>) e2).getExpression());
		else if (e1 instanceof Variable)
			return OptionUtil.compare((EObject) ((Variable<T>) e1).getValue(), (EObject) ((Variable<T>) e2).getValue());
		else if (e1 instanceof Constant)
			return e1.eClass() == e2.eClass();
		else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Creates a copy of a given expression. All terms are copied including variables and constants. The options of variables are not copied.
	 */
	public static <T> Expression<T> copy(Expression<T> expression) {
		if (expression instanceof NaryExpression) {
			NaryExpression<T> copiedExpression = (NaryExpression<T>) ExpressionFactory.eINSTANCE.create(expression.eClass());
			for (Expression<T> childExpression : ((NaryExpression<T>) expression).getExpressions())
				copiedExpression.getExpressions().add(copy(childExpression));
			return (NaryExpression<T>) copiedExpression;
		} else if (expression instanceof BinaryExpression) {
			BinaryExpression<T> copiedExpression = (BinaryExpression<T>) ExpressionFactory.eINSTANCE.create(expression.eClass());
			copiedExpression.setLeft(copy(((BinaryExpression<T>) expression).getLeft()));
			copiedExpression.setRight(copy(((BinaryExpression<T>) expression).getRight()));
			return (BinaryExpression<T>) copiedExpression;
		} else if (expression instanceof UnaryExpression) {
			UnaryExpression<T> copiedExpression = (UnaryExpression<T>) ExpressionFactory.eINSTANCE.create(expression.eClass());
			copiedExpression.setExpression(copy(((UnaryExpression<T>) expression).getExpression()));
			return (UnaryExpression<T>) copiedExpression;
		} else if (expression instanceof Variable) {
			Variable<T> copiedVariable = ExpressionFactory.eINSTANCE.createVariable();
			copiedVariable.setValue(((Variable<T>) expression).getValue());
			return (Variable<T>) copiedVariable;
		} else if (expression instanceof Constant) {
			return (Constant<T>) ExpressionFactory.eINSTANCE.create(expression.eClass());
		} else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Evaluates an expression with respect to a given configuration.
	 */
	public static <T extends Option> boolean eval(Expression<T> expression, Configuration configuration) {
		if (expression instanceof Conjunction) {
			for (Expression<T> childExpression : ((Conjunction<T>) expression).getExpressions()) {
				if (!eval(childExpression, configuration))
					return false;
			}
			return true;
		} else if (expression instanceof Disjunction) {
			for (Expression<T> childExpression : ((Disjunction<T>) expression).getExpressions()) {
				if (eval(childExpression, configuration))
					return true;
			}
			return false;
		} else if (expression instanceof Equivalence)
			return eval(((Equivalence<T>) expression).getLeft(), configuration) == eval(((Equivalence<T>) expression).getRight(), configuration);
		else if (expression instanceof Implication)
			return !eval(((Implication<T>) expression).getLeft(), configuration) || eval(((Implication<T>) expression).getRight(), configuration);
		else if (expression instanceof Not)
			return !eval(((Not<T>) expression).getExpression(), configuration);
		else if (expression instanceof Variable) {
			// return this.configuration.getOption().contains(variable.getOption());
			// for feature revisions: check if the configuration contains this feature revision or a newer one
			Variable<T> variable = (Variable<T>) expression;
			return configuration.getOptions().contains(variable.getValue())
					|| variable.getValue() instanceof FeatureRevision && configuration.getOptions().stream().filter(o -> o instanceof FeatureRevision && ((FeatureRevision) o).eContainer().equals(((FeatureRevision) variable.getValue()).eContainer()) && ((FeatureRevision) o).getRevisionID() >= ((FeatureRevision) variable.getValue()).getRevisionID()).findAny().isPresent();
		} else if (expression instanceof True)
			return true;
		else if (expression instanceof False)
			return false;
		else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Returns a string representation of a given expression.
	 */
	public static String toString(Expression<?> expression) {
		if (expression instanceof Conjunction)
			return ((Conjunction<?>) expression).getExpressions().stream().map(e -> toString(e)).collect(Collectors.joining(" AND "));
		else if (expression instanceof Disjunction)
			return ((Disjunction<?>) expression).getExpressions().stream().map(e -> toString(e)).collect(Collectors.joining(" OR "));
		else if (expression instanceof Equivalence)
			return toString(((Equivalence<?>) expression).getLeft()) + " <=> " + toString(((Equivalence<?>) expression).getRight());
		else if (expression instanceof Implication)
			return toString(((Equivalence<?>) expression).getLeft()) + " => " + toString(((Equivalence<?>) expression).getRight());
		else if (expression instanceof Not)
			return "!" + toString(((Not) expression).getExpression());
		else if (expression instanceof Variable) {
			Object o = ((Variable<?>) expression).getValue();
			if (o instanceof Feature)
				return ((Feature) o).getName();
			else if (o instanceof FeatureRevision) {
				return ((Feature) ((FeatureRevision) o).eContainer()).getName() + "." + ((FeatureRevision) o).getRevisionID();
			} else if (o instanceof SystemRevision) {
				return ((SystemRevision) o).getRevisionID() + "";
			}
			return o.toString();
		} else if (expression instanceof True)
			return "TRUE";
		else if (expression instanceof False)
			return "FALSE";
		else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Returns a new expression that is a simplification of the old expression.
	 */
	public static <T> Expression<T> simplify(Expression<T> expression) {
		if (expression instanceof Conjunction) {
			Conjunction<T> newConjunction = ExpressionFactory.eINSTANCE.createConjunction();
			for (Expression<T> childExpression : ((Conjunction<T>) expression).getExpressions()) {
				Expression<T> newChildExpression = simplify(childExpression);
				if (newChildExpression instanceof False) // if one child is false, the entire conjunction is false
					return ExpressionFactory.eINSTANCE.createFalse();
				if (!(newChildExpression instanceof True)) // if a child is true, then it can be ignored
					newConjunction.getExpressions().add(newChildExpression);
			}
			if (newConjunction.getExpressions().isEmpty()) // if all children are true, then the entire conjunction is true
				return ExpressionFactory.eINSTANCE.createTrue();
			if (newConjunction.getExpressions().size() == 1) // if the conjunction only has one child, we do not need the conjunction
				return newConjunction.getExpressions().get(0);
			return newConjunction;
		} else if (expression instanceof Disjunction) {
			Disjunction<T> newDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
			for (Expression<T> childExpression : ((Disjunction<T>) expression).getExpressions()) {
				Expression<T> newChildExpression = simplify(childExpression);
				if (newChildExpression instanceof True)
					return ExpressionFactory.eINSTANCE.createTrue();
				if (!(newChildExpression instanceof False))
					newDisjunction.getExpressions().add(newChildExpression);
			}
			if (newDisjunction.getExpressions().isEmpty())
				return ExpressionFactory.eINSTANCE.createFalse();
			if (newDisjunction.getExpressions().size() == 1)
				return newDisjunction.getExpressions().get(0);
			return newDisjunction;
		} else if (expression instanceof Equivalence) {
			Equivalence<T> equivalence = (Equivalence<T>) expression;
			Expression<T> leftChild = simplify(equivalence.getLeft());
			Expression<T> rightChild = simplify(equivalence.getRight());
			if (leftChild instanceof True && rightChild instanceof True)
				return ExpressionFactory.eINSTANCE.createTrue();
			else if (leftChild instanceof False && rightChild instanceof False)
				return ExpressionFactory.eINSTANCE.createTrue();
			else if (leftChild instanceof True && rightChild instanceof False)
				return ExpressionFactory.eINSTANCE.createFalse();
			else if (leftChild instanceof False && rightChild instanceof True)
				return ExpressionFactory.eINSTANCE.createFalse();
			else if (structuralEquivalence(leftChild, rightChild))
				return ExpressionFactory.eINSTANCE.createTrue();
			else {
				Equivalence<T> newEquivalence = ExpressionFactory.eINSTANCE.createEquivalence();
				newEquivalence.setLeft(leftChild);
				newEquivalence.setRight(rightChild);
				return newEquivalence;
			}
		} else if (expression instanceof Implication) {
			Implication<T> implication = (Implication<T>) expression;
			Expression<T> leftChild = simplify(implication.getLeft());
			Expression<T> rightChild = simplify(implication.getRight());
			if (leftChild instanceof False || rightChild instanceof True)
				return ExpressionFactory.eINSTANCE.createTrue();
			else {
				Implication<T> newImplication = ExpressionFactory.eINSTANCE.createImplication();
				newImplication.setLeft(leftChild);
				newImplication.setRight(rightChild);
				return newImplication;
			}
		} else if (expression instanceof Not) {
			Expression<T> child = simplify(((Not<T>) expression).getExpression());
			if (child instanceof True) {
				return ExpressionFactory.eINSTANCE.createFalse();
			} else if (child instanceof False) {
				return ExpressionFactory.eINSTANCE.createTrue();
			} else {
				Not<T> not = ExpressionFactory.eINSTANCE.createNot();
				not.setExpression(child);
				return not;
			}
		} else if (expression instanceof Variable) {
			Variable<T> variable = ExpressionFactory.eINSTANCE.createVariable();
			variable.setValue((T) ((Variable<T>) expression).getValue());
			return variable;
		} else if (expression instanceof True)
			return ExpressionFactory.eINSTANCE.createTrue();
		else if (expression instanceof False)
			return ExpressionFactory.eINSTANCE.createFalse();
		else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Validates the well-formedness of an expression.
	 */
	public static <T> boolean validate(Expression<T> expression) {
		if (expression instanceof NaryExpression) {
			for (Expression<T> childExpression : ((NaryExpression<T>) expression).getExpressions())
				if (!validate(childExpression))
					return false;
			return true;
		} else if (expression instanceof BinaryExpression)
			return validate(((BinaryExpression<T>) expression).getLeft()) && validate(((BinaryExpression<T>) expression).getRight());
		else if (expression instanceof UnaryExpression)
			return ((UnaryExpression<T>) expression).getExpression() != null && validate(((UnaryExpression<T>) expression).getExpression());
		else if (expression instanceof Variable)
			return true;
		else if (expression instanceof Constant)
			return true;
		else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

	/*
	 * Converts the given expression into CNF.
	 */
	public static <T> Expression<T> convertToCNF(Expression<T> expression) {
		if (expression instanceof Conjunction) {
			Conjunction<T> newConjunction = ExpressionFactory.eINSTANCE.createConjunction();
			for (Expression<T> childExpression : ((Conjunction<T>) expression).getExpressions()) {
				Expression<T> newChildExpression = convertToCNF(childExpression);
				newConjunction.getExpressions().add(newChildExpression);
			}
			return newConjunction;
		} else if (expression instanceof Disjunction) {
			Disjunction<T> disjunction = (Disjunction<T>) expression;
			Disjunction<T> newDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
			boolean allAreEitherNotVariableDisjunction = true;
			for (Expression<T> childExpression : disjunction.getExpressions()) {
				Expression<T> newChildCNF = (Expression<T>) convertToCNF(childExpression);
				if (newChildCNF == null)
					throw new IllegalStateException("CNF is null");

				if (!(newChildCNF instanceof Not || newChildCNF instanceof Disjunction || newChildCNF instanceof Variable))
					allAreEitherNotVariableDisjunction = false;

				if (newChildCNF instanceof Conjunction) {
					Conjunction<T> newConjunction = ExpressionFactory.eINSTANCE.createConjunction();
					// for every expression in the conjunction
					for (Expression<T> childOfNewChildCNF : ((Conjunction<T>) newChildCNF).getExpressions()) {
						// create a disjunction
						Disjunction<T> newChildDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
						newConjunction.getExpressions().add(newChildDisjunction);
						// of the respective element of the conjunction ...
						newChildDisjunction.getExpressions().add(convertToCNF(childOfNewChildCNF));
						// ... and all the other expressions of the disjunction
						for (Expression<T> childExpression2 : disjunction.getExpressions()) {
							if (childExpression != childExpression2)
								newChildDisjunction.getExpressions().add(convertToCNF(childExpression2));
						}
					}
					return convertToCNF(newConjunction);
				}

				newDisjunction.getExpressions().add(newChildCNF);
			}

			// if all children are either not or disjunction or variable
			if (allAreEitherNotVariableDisjunction) {
				// we are done with recursion
				// return convertToCNF(newDisjunction);
				return newDisjunction;
			}

			throw new IllegalStateException("ERROR");
		} else if (expression instanceof Equivalence) {
			throw new IllegalStateException("ERROR");
		} else if (expression instanceof Implication) {
			throw new IllegalStateException("ERROR");
		} else if (expression instanceof Not) {
			Not<T> not = (Not<T>) expression;
			if (not.getExpression() instanceof Variable) {
				Not<T> newNot = ExpressionFactory.eINSTANCE.createNot();
				newNot.setExpression((Variable<T>) convertToCNF(not.getExpression()));
				return newNot;
			} else if (not.getExpression() instanceof Not) {
				return (Expression<T>) convertToCNF(((Not<T>) not.getExpression()).getExpression());
			} else if (not.getExpression() instanceof Conjunction) {
				Conjunction<T> conjunction = (Conjunction<T>) not.getExpression();
				Disjunction<T> newDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
				for (Expression<T> childExpression : conjunction.getExpressions()) {
					Not<T> newChildNot = ExpressionFactory.eINSTANCE.createNot();
					newChildNot.setExpression((Expression<T>) convertToCNF(childExpression));
					newDisjunction.getExpressions().add(newChildNot);
				}
				return (Expression<T>) convertToCNF(newDisjunction);
			} else if (not.getExpression() instanceof Disjunction) {
				Disjunction<T> disjunction = (Disjunction<T>) not.getExpression();
				Conjunction<T> newConjunction = ExpressionFactory.eINSTANCE.createConjunction();
				for (Expression<T> childExpression : disjunction.getExpressions()) {
					Not<T> newChildNot = ExpressionFactory.eINSTANCE.createNot();
					newChildNot.setExpression((Expression<T>) convertToCNF(disjunction.getExpressions().get(0)));
					newConjunction.getExpressions().add(newChildNot);
				}
				return (Expression<T>) convertToCNF(newConjunction);
			}
			throw new IllegalStateException("ERROR");
		} else if (expression instanceof Variable) {
			Variable<T> newVariable = ExpressionFactory.eINSTANCE.createVariable();
			newVariable.setValue(((Variable<T>) expression).getValue());
			return newVariable;
		} else if (expression instanceof True) {
			return ExpressionFactory.eINSTANCE.createTrue();
		} else if (expression instanceof False) {
			return ExpressionFactory.eINSTANCE.createFalse();
		} else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

//	public static <T> Collection<int[]> convertToSAT(Expression<T> expression) {
//		return null; // TODO
//	}

}
