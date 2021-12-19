package tools.vitruv.variability.vave.util;

import vavemodel.BinaryExpression;
import vavemodel.False;
import vavemodel.Option;
import vavemodel.Term;
import vavemodel.True;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;
import vavemodel.util.VavemodelSwitch;

/**
 * Creates a copy of a given expression. All terms are copied including variables and constants. The options of variables are not copied.
 */
public class ExpressionCopier<Type extends Option> extends VavemodelSwitch<Term<Type>> {

	private ExpressionCopier() {
		super();
	}

	public static <T extends Option> Term<T> copy(Term<T> e) {
		return new ExpressionCopier<T>().doSwitch(e);
	}

	@Override
	public <T extends Option> Term<Type> caseBinaryExpression(BinaryExpression<T> expression) {
		BinaryExpression<T> copiedExpression = (BinaryExpression<T>) VavemodelFactory.eINSTANCE.create(expression.eClass());
		copiedExpression.getTerm().add((Term<T>) this.doSwitch(expression.getTerm().get(0)));
		copiedExpression.getTerm().add((Term<T>) this.doSwitch(expression.getTerm().get(1)));
		return (Term<Type>) copiedExpression;
	}

	@Override
	public <T extends Option> Term<Type> caseUnaryExpression(UnaryExpression<T> expression) {
		UnaryExpression<T> copiedExpression = (UnaryExpression<T>) VavemodelFactory.eINSTANCE.create(expression.eClass());
		copiedExpression.setTerm((Term<T>) this.doSwitch(expression.getTerm()));
		return (Term<Type>) copiedExpression;
	}

	@Override
	public <T extends Option> Term<Type> caseVariable(Variable<T> variable) {
		Variable<T> copiedVariable = VavemodelFactory.eINSTANCE.createVariable();
		copiedVariable.setOption(variable.getOption());
		return (Term<Type>) copiedVariable;
	}

	@Override
	public <T extends Option> Term<Type> caseTrue(True<T> object) {
		return VavemodelFactory.eINSTANCE.createTrue();
	}

	@Override
	public <T extends Option> Term<Type> caseFalse(False<T> object) {
		return VavemodelFactory.eINSTANCE.createFalse();
	}

}
