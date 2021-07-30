package tools.vitruv.variability.vave.impl;

import vavemodel.Configuration;
import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.Equivalence;
import vavemodel.Expression;
import vavemodel.False;
import vavemodel.Implication;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.True;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class ExpressionEvaluator extends VavemodelSwitch<Boolean> {

	private Configuration configuration;

	public ExpressionEvaluator(Configuration configuration) {
		this.configuration = configuration;
	}

	public boolean eval(Expression<? extends Option> expression) {
		return this.doSwitch(expression);
	}

	@Override
	public <T extends Option> Boolean caseConjunction(Conjunction<T> e) {
		return doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseDisjunction(Disjunction<T> e) {
		return doSwitch(e.getTerm().get(0)) || doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseEquivalence(Equivalence<T> e) {
		return doSwitch(e.getTerm().get(0)).equals(doSwitch(e.getTerm().get(1)));
	}

	@Override
	public <T extends Option> Boolean caseImplication(Implication<T> e) {
		return !doSwitch(e.getTerm().get(0)) || doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseNot(Not<T> e) {
		return !doSwitch(e.getTerm());
	}

	@Override
	public <T extends Option> Boolean caseVariable(Variable<T> variable) {
		return this.configuration.getOption().contains(variable.getOption());
	}

	@Override
	public <T extends Option> Boolean caseTrue(True<T> constant) {
		return true;
	}

	@Override
	public <T extends Option> Boolean caseFalse(False<T> constant) {
		return false;
	}

}
