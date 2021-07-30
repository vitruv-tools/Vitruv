package tools.vitruv.variability.vave.impl;

import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.Equivalence;
import vavemodel.False;
import vavemodel.Implication;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.True;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class ExpressionValidator extends VavemodelSwitch<Boolean> {

	@Override
	public <T extends Option> Boolean caseConjunction(Conjunction<T> e) {
		return e.getTerm().size() == 2 && doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseDisjunction(Disjunction<T> e) {
		return e.getTerm().size() == 2 && doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseEquivalence(Equivalence<T> e) {
		return e.getTerm().size() == 2 && doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseImplication(Implication<T> e) {
		return e.getTerm().size() == 2 && doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseNot(Not<T> e) {
		return e.getTerm() != null && doSwitch(e.getTerm());
	}

	@Override
	public <T extends Option> Boolean caseVariable(Variable<T> variable) {
		if (variable.getOption() == null) {
			return false;
		}
		return true;
	}

	@Override
	public <T extends Option> Boolean caseTrue(True<T> constant) {
		return true;
	}

	@Override
	public <T extends Option> Boolean caseFalse(False<T> constant) {
		return true;
	}

}
