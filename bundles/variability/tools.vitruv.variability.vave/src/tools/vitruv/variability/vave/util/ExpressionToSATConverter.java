package tools.vitruv.variability.vave.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.Disjunction;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.False;
import tools.vitruv.variability.vave.model.expression.Not;
import tools.vitruv.variability.vave.model.expression.True;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.Option;

/**
 * Converts an expression with options as literals (i.e., values of variables) into a collection of int arrays that can be passed to a SAT solver.
 */
public class ExpressionToSATConverter {

	public <T extends Option> Collection<int[]> convertExpr2Sat(Expression<T> expr) {
		Expression<T> exprCNF = ExpressionUtil.convertToCNF(expr);

		Collection<int[]> clauses = new ArrayList<>();
		this.createClausesRec(exprCNF, clauses);

		return clauses;
	}

	private <T extends Option> void createClausesRec(Expression<T> expr, Collection<int[]> clauses) {
		// if the expression is a conjunction we recurse
		if (expr instanceof Conjunction) {
			for (Expression<T> childExpr : ((Conjunction<T>) expr).getExpressions()) {
				this.createClausesRec(childExpr, clauses);
			}
		} else { // if (expr instanceof Disjunction) {
			// we create a new clause whenever we encounter a disjunction that has as a parent a conjunction
			int[] clause = this.createClause(expr);
			if (clause != null)
				clauses.add(clause);
		}
	}

	private <T extends Option> int[] createClause(Expression<T> expr) {
		List<Integer> clause = new ArrayList<>();
		this.createClauseRec(expr, clause);
		if (clause.contains(null)) {
			// we chose null to represent value true. therefore, if null appears in clause, simply ignore it.
			return null;
		}
		return clause.stream().mapToInt(v -> v).toArray();
	}

	private Map<Option, Integer> optionToIntMap = new HashMap<>();
	private int curVal = 0;

	public void setIntForOption(int value, Option option) {
		this.optionToIntMap.put(option, value);
		this.curVal++;
	}

	public void setIntsForOptions(Map<Option, Integer> optionToIntMap) {
		this.optionToIntMap.putAll(optionToIntMap);
		if (!optionToIntMap.isEmpty())
			this.curVal = optionToIntMap.values().stream().mapToInt(v -> v).max().getAsInt();
	}

	private <T extends Option> void createClauseRec(Expression<T> expr, List<Integer> literals) {
		// if the expression is a disjunction we recurse
		if (expr instanceof Disjunction) {
			for (Expression<T> childExpr : ((Disjunction<T>) expr).getExpressions()) {
				this.createClauseRec(childExpr, literals);
			}
		} else if (expr instanceof Not) {
			Expression<T> notOption = ((Not<T>) expr).getExpression();
			if (notOption instanceof Variable) {
				Variable<T> variable = (Variable<T>) ((Not<T>) expr).getExpression();
				Integer value = this.optionToIntMap.get(variable.getValue());
				if (value == null) {
					if (variable.getValue() instanceof FeatureRevision) {
						// look for feature of feature revision
						Optional<Option> featureOpt = this.optionToIntMap.entrySet().stream().filter(e -> e.getKey() instanceof Feature && Objects.equals(((Feature) e.getKey()).getName(), ((Feature) variable.getValue().eContainer()).getName())).map(e -> e.getKey()).findAny();
						if (featureOpt.isPresent()) {
							value = this.optionToIntMap.get(featureOpt.get());
							System.out.println("USED FEATURE VAL " + value + " FOR FEATURE REVISION " + variable.getValue());
						}
					}
					if (value == null) {
						value = ++this.curVal;
						this.optionToIntMap.put(variable.getValue(), value);
						System.out.println("NEW VAL : " + value + " for option " + variable.getValue());
						throw new RuntimeException("There was no value bound to option " + variable.getValue());
					}
				}
				literals.add(-value);
			} else if (notOption instanceof True) {
				// negated true is false -> do nothing
			} else if (notOption instanceof False) {
				literals.add(null);
			}
		} else if (expr instanceof Variable) {
			Variable<T> variable = (Variable<T>) expr;
			Integer value = this.optionToIntMap.get(variable.getValue());
			if (value == null) {
				if (variable.getValue() instanceof FeatureRevision) {
					// look for feature of feature revision
					Optional<Option> featureOpt = this.optionToIntMap.entrySet().stream().filter(e -> e.getKey() instanceof Feature && Objects.equals(((Feature) e.getKey()).getName(), ((Feature) variable.getValue().eContainer()).getName())).map(e -> e.getKey()).findAny();
					if (featureOpt.isPresent()) {
						value = this.optionToIntMap.get(featureOpt.get());
						System.out.println("USED FEATURE VAL " + value + " FOR FEATURE REVISION " + variable.getValue());
					}
				}
				if (value == null) {
					value = ++this.curVal;
					this.optionToIntMap.put(variable.getValue(), value);
					System.out.println("NEW VAL : " + value + " for option " + variable.getValue());
					throw new RuntimeException("There was no value bound to option " + variable.getValue());
				}
			}
			literals.add(value);
		} else if (expr instanceof True) {
			literals.add(null);
		} else if (expr instanceof False) {
			// false -> do nothing
		}
	}

}
