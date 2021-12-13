package tools.vitruv.variability.vave.util;

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
import vavemodel.VavemodelFactory;
import vavemodel.util.VavemodelSwitch;

public class ExpressionToCNFConverter extends VavemodelSwitch<Expression<? extends Option>> {

	public ExpressionToCNFConverter() {
	}

	public <T extends Option> Expression<T> convert(Expression<T> expression) {
		return (Expression<T>) this.doSwitch(expression);
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseConjunction(Conjunction<T> e) {
		Conjunction<T> newConjunction = VavemodelFactory.eINSTANCE.createConjunction();
		newConjunction.getTerm().add((Expression<T>) doSwitch(e.getTerm().get(0)));
		newConjunction.getTerm().add((Expression<T>) doSwitch(e.getTerm().get(1)));
		// return (Expression<T>) doSwitch(newConjunction);
		return newConjunction;
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseDisjunction(Disjunction<T> e) {
		Expression<T> leftCNF = (Expression<T>) doSwitch(e.getTerm().get(0));
		Expression<T> rightCNF = (Expression<T>) doSwitch(e.getTerm().get(1));

		if (leftCNF == null || rightCNF == null)
			throw new IllegalStateException("CNF is null");

		if ((leftCNF instanceof Not || leftCNF instanceof Disjunction || leftCNF instanceof Variable) && (rightCNF instanceof Not || rightCNF instanceof Disjunction || rightCNF instanceof Variable)) {
			// we are done with recursion
			Disjunction<T> newDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			newDisjunction.getTerm().add(leftCNF);
			newDisjunction.getTerm().add(rightCNF);
			// return (Expression<T>) doSwitch(newDisjunction);
			return newDisjunction;
		}
//		else if (leftCNF instanceof Conjunction && rightCNF instanceof Conjunction) {
//			// create all four combinations of terms
//		} else if ((leftCNF instanceof UnaryExpression || leftCNF instanceof Disjunction) && rightCNF instanceof Conjunction) {
//			
//		} else if ((rightCNF instanceof UnaryExpression || rightCNF instanceof Disjunction) && leftCNF instanceof Conjunction) {
//			
//		}
		if (leftCNF instanceof Conjunction) {
			Conjunction<T> newConjunction = VavemodelFactory.eINSTANCE.createConjunction();
			Disjunction<T> newLeftDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			Disjunction<T> newRightDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			newConjunction.getTerm().add(newLeftDisjunction);
			newConjunction.getTerm().add(newRightDisjunction);
			newLeftDisjunction.getTerm().add((Expression<T>) doSwitch(((Conjunction<T>) leftCNF).getTerm().get(0)));
			newLeftDisjunction.getTerm().add((Expression<T>) doSwitch(rightCNF));
			newRightDisjunction.getTerm().add((Expression<T>) doSwitch(((Conjunction<T>) leftCNF).getTerm().get(1)));
			newRightDisjunction.getTerm().add(rightCNF);
			return doSwitch(newConjunction);
		}
		if (rightCNF instanceof Conjunction) {
			Conjunction<T> newConjunction = VavemodelFactory.eINSTANCE.createConjunction();
			Disjunction<T> newLeftDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			Disjunction<T> newRightDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			newConjunction.getTerm().add(newLeftDisjunction);
			newConjunction.getTerm().add(newRightDisjunction);
			newLeftDisjunction.getTerm().add((Expression<T>) doSwitch(leftCNF));
			newLeftDisjunction.getTerm().add((Expression<T>) doSwitch(((Conjunction<T>) rightCNF).getTerm().get(0)));
			newRightDisjunction.getTerm().add(leftCNF);
			newRightDisjunction.getTerm().add((Expression<T>) doSwitch(((Conjunction<T>) rightCNF).getTerm().get(1)));
			return doSwitch(newConjunction);
		}

		throw new IllegalStateException("ERROR");
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseEquivalence(Equivalence<T> e) {
		throw new IllegalStateException("ERROR");
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseImplication(Implication<T> e) {
		throw new IllegalStateException("ERROR");
	}

	@Override
	public <T extends Option> Expression<T> caseNot(Not<T> e) {
		if (e.getTerm() instanceof Variable) {
			Not<T> newNot = VavemodelFactory.eINSTANCE.createNot();
			newNot.setTerm((Variable<T>) doSwitch(e.getTerm()));
			return newNot;
		} else if (e.getTerm() instanceof Not) {
			return (Expression<T>) doSwitch(((Not) e.getTerm()).getTerm());
		} else if (e.getTerm() instanceof Conjunction) {
			Conjunction<T> conjunction = (Conjunction<T>) e.getTerm();
			Not<T> newLeftNot = VavemodelFactory.eINSTANCE.createNot();
			newLeftNot.setTerm((Expression<T>) doSwitch(conjunction.getTerm().get(0)));
			Not<T> newRightNot = VavemodelFactory.eINSTANCE.createNot();
			newRightNot.setTerm((Expression<T>) doSwitch(conjunction.getTerm().get(1)));
			Disjunction<T> newDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			newDisjunction.getTerm().add(newLeftNot);
			newDisjunction.getTerm().add(newRightNot);
			return (Expression<T>) doSwitch(newDisjunction);
		} else if (e.getTerm() instanceof Disjunction) {
			Disjunction<T> disjunction = (Disjunction<T>) e.getTerm();
			Not<T> newLeftNot = VavemodelFactory.eINSTANCE.createNot();
			newLeftNot.setTerm((Expression<T>) doSwitch(disjunction.getTerm().get(0)));
			Not<T> newRightNot = VavemodelFactory.eINSTANCE.createNot();
			newRightNot.setTerm((Expression<T>) doSwitch(disjunction.getTerm().get(1)));
			Conjunction<T> newConjunction = VavemodelFactory.eINSTANCE.createConjunction();
			newConjunction.getTerm().add(newLeftNot);
			newConjunction.getTerm().add(newRightNot);
			return (Expression<T>) doSwitch(newConjunction);
		}
		throw new IllegalStateException("ERROR");
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseVariable(Variable<T> variable) {
		Variable<T> newVariable = VavemodelFactory.eINSTANCE.createVariable();
		newVariable.setOption(variable.getOption());
		return newVariable;
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseTrue(True<T> constant) {
		return VavemodelFactory.eINSTANCE.createTrue();
	}

	@Override
	public <T extends Option> Expression<? extends Option> caseFalse(False<T> constant) {
		return VavemodelFactory.eINSTANCE.createFalse();
	}

}
