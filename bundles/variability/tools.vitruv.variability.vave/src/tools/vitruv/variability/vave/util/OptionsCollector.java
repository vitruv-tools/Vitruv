package tools.vitruv.variability.vave.util;

import java.util.ArrayList;
import java.util.Collection;

import vavemodel.BinaryExpression;
import vavemodel.False;
import vavemodel.Option;
import vavemodel.True;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class OptionsCollector extends VavemodelSwitch<Collection<Option>> {
	private Collection<Option> literals = new ArrayList<>();

	@Override
	public <T extends Option> Collection<Option> caseBinaryExpression(BinaryExpression<T> e) {
		doSwitch(e.getTerm().get(0));
		doSwitch(e.getTerm().get(1));
		return this.literals;
	}

	@Override
	public <T extends Option> Collection<Option> caseUnaryExpression(UnaryExpression<T> e) {
		doSwitch(e.getTerm());
		return this.literals;
	}

	@Override
	public <T extends Option> Collection<Option> caseVariable(Variable<T> variable) {
		this.literals.add(variable.getOption());
		return this.literals;
	}

	@Override
	public <T extends Option> Collection<Option> caseTrue(True<T> constant) {
		return this.literals;
	}

	@Override
	public <T extends Option> Collection<Option> caseFalse(False<T> constant) {
		return this.literals;
	}

}
