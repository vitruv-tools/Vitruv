package tools.vitruv.variability.vave.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureRevision;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.Term;
import vavemodel.Variable;

public class ExpressionToSATConverter {

	public Collection<int[]> convertExpr2Sat(Expression<? extends Object> expr) {
		ExpressionToCNFConverter converter = new ExpressionToCNFConverter();
		Expression<? extends Object> exprCNF = converter.convert(expr);

		Collection<int[]> clauses = new ArrayList<>();
		this.createClausesRec(exprCNF, clauses);

		return clauses;
	}

	private void createClausesRec(Term<? extends Option> expr, Collection<int[]> clauses) {
		// if the expression is a conjunction we recurse
		if (expr instanceof Conjunction) {
			this.createClausesRec(((Conjunction<? extends Option>) expr).getTerm().get(0), clauses);
			this.createClausesRec(((Conjunction<? extends Option>) expr).getTerm().get(1), clauses);
		} else { // if (expr instanceof Disjunction) {
			// we create a new clause whenever we encounter a disjunction that has as a parent a conjunction
			int[] clause = this.createClause(expr);
			clauses.add(clause);
		}
	}

	private int[] createClause(Term<? extends Option> expr) {
		List<Integer> clause = new ArrayList<>();
		this.createClauseRec(expr, clause);
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

	private void createClauseRec(Term<? extends Option> expr, List<Integer> literals) {
		// if the expression is a disjunction we recurse
		if (expr instanceof Disjunction) {
			this.createClauseRec(((Disjunction<? extends Option>) expr).getTerm().get(0), literals);
			this.createClauseRec(((Disjunction<? extends Option>) expr).getTerm().get(1), literals);
		} else if (expr instanceof Not) {
			Variable<? extends Option> variable = (Variable<? extends Option>) ((Not<? extends Option>) expr).getTerm();
			Integer value = this.optionToIntMap.get(variable.getOption());
			if (value == null) {
				if (variable.getOption() instanceof FeatureRevision) {
					// look for feature of feature revision
					Optional<Option> featureOpt = this.optionToIntMap.entrySet().stream().filter(e -> e.getKey() instanceof Feature && Objects.equals(((Feature) e.getKey()).getName(), ((Feature) variable.getOption().eContainer()).getName())).map(e -> e.getKey()).findAny();
					if (featureOpt.isPresent()) {
						value = this.optionToIntMap.get(featureOpt.get());
						System.out.println("USED FEATURE VAL " + value + " FOR FEATURE REVISION " + variable.getOption());
					}
				}
				if (value == null) {
					value = ++this.curVal;
					this.optionToIntMap.put(variable.getOption(), value);
					System.out.println("NEW VAL : " + value + " for option " + variable.getOption());
					throw new RuntimeException("There was no value bound to option " + variable.getOption());
				}
			}
			literals.add(-value);
		} else if (expr instanceof Variable) {
			Variable<? extends Option> variable = (Variable<? extends Option>) expr;
			Integer value = this.optionToIntMap.get(variable.getOption());
			if (value == null) {
				if (variable.getOption() instanceof FeatureRevision) {
					// look for feature of feature revision
					Optional<Option> featureOpt = this.optionToIntMap.entrySet().stream().filter(e -> e.getKey() instanceof Feature && Objects.equals(((Feature) e.getKey()).getName(), ((Feature) variable.getOption().eContainer()).getName())).map(e -> e.getKey()).findAny();
					if (featureOpt.isPresent()) {
						value = this.optionToIntMap.get(featureOpt.get());
						System.out.println("USED FEATURE VAL " + value + " FOR FEATURE REVISION " + variable.getOption());
					}
				}
				if (value == null) {
					value = ++this.curVal;
					this.optionToIntMap.put(variable.getOption(), value);
					System.out.println("NEW VAL : " + value + " for option " + variable.getOption());
					throw new RuntimeException("There was no value bound to option " + variable.getOption());
				}
			}
			literals.add(value);
		}
	}

}
