package tools.vitruv.variability.vave.util;

import vavemodel.BinaryExpression;
import vavemodel.False;
import vavemodel.Option;
import vavemodel.Term;
import vavemodel.True;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

/**
 * Compares two expression for structural equivalence.
 */
public class ExpressionComparator<Type extends Option> extends VavemodelSwitch<Boolean> {

	private Term<Type> e1;

	private ExpressionComparator(Term<Type> e1) {
		super();
		this.e1 = e1;
	}

	public static <T extends Option> Boolean equals(Term<T> e1, Term<T> e2) {
		return e1.eClass() == e2.eClass() && new ExpressionComparator<T>(e1).doSwitch(e2);
	}

	@Override
	public <T extends Option> Boolean caseBinaryExpression(BinaryExpression<T> expression) {
		return ExpressionComparator.<T>equals(((BinaryExpression<T>) e1).getTerm().get(0), expression.getTerm().get(0)) && ExpressionComparator.equals(((BinaryExpression<T>) e1).getTerm().get(1), expression.getTerm().get(1));
	}

	@Override
	public <T extends Option> Boolean caseUnaryExpression(UnaryExpression<T> expression) {
		return ExpressionComparator.<T>equals(((UnaryExpression<T>) e1).getTerm(), expression.getTerm());
	}

	@Override
	public <T extends Option> Boolean caseVariable(Variable<T> variable) {
		return OptionComparator.equals(variable.getOption(), ((Variable<T>) e1).getOption());
	}

	@Override
	public <T extends Option> Boolean caseTrue(True<T> object) {
		return object.eClass() == e1.eClass();
	}

	@Override
	public <T extends Option> Boolean caseFalse(False<T> object) {
		return object.eClass() == e1.eClass();
	}

}
