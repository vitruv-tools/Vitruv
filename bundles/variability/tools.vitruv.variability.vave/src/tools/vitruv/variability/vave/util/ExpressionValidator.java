package tools.vitruv.variability.vave.util;

import vavemodel.BinaryExpression;
import vavemodel.False;
import vavemodel.Option;
import vavemodel.True;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class ExpressionValidator extends VavemodelSwitch<Boolean> {

	@Override
	public <T extends Option> Boolean caseBinaryExpression(BinaryExpression<T> e) {
		return e.getTerm().size() == 2 && doSwitch(e.getTerm().get(0)) && doSwitch(e.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseUnaryExpression(UnaryExpression<T> e) {
		return e.getTerm() != null && doSwitch(e.getTerm());
	}

	@Override
	public <T extends Option> Boolean caseVariable(Variable<T> variable) {
		//return variable.getOption() != null;
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
