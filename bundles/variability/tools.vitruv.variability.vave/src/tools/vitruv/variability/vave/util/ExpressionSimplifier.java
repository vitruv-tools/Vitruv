package tools.vitruv.variability.vave.util;

import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.False;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.Term;
import vavemodel.True;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;
import vavemodel.util.VavemodelSwitch;

public class ExpressionSimplifier<Type extends Option> extends VavemodelSwitch<Term<Type>> {

	@Override
	public <T extends Option> Term<Type> caseConjunction(Conjunction<T> object) {
		Term<Type> leftChild = doSwitch(object.getTerm().get(0));
		Term<Type> rightChild = doSwitch(object.getTerm().get(1));
		if (leftChild instanceof False || rightChild instanceof False) {
			return VavemodelFactory.eINSTANCE.createFalse();
		} else if (leftChild instanceof True && rightChild instanceof True) {
			return VavemodelFactory.eINSTANCE.createTrue();
		} else if (leftChild instanceof True) {
			return rightChild;
		} else if (rightChild instanceof True) {
			return leftChild;
		} else {
			Conjunction<Type> conjunction = VavemodelFactory.eINSTANCE.createConjunction();
			conjunction.getTerm().add(leftChild);
			conjunction.getTerm().add(rightChild);
			return conjunction;
		}
	}

	@Override
	public <T extends Option> Term<Type> caseDisjunction(Disjunction<T> object) {
		Term<Type> leftChild = doSwitch(object.getTerm().get(0));
		Term<Type> rightChild = doSwitch(object.getTerm().get(1));
		if (leftChild instanceof True || rightChild instanceof True) {
			return VavemodelFactory.eINSTANCE.createTrue();
		} else if (leftChild instanceof False && rightChild instanceof False) {
			return VavemodelFactory.eINSTANCE.createFalse();
		} else if (leftChild instanceof False) {
			return rightChild;
		} else if (rightChild instanceof False) {
			return leftChild;
		} else {
			Disjunction<Type> disjunction = VavemodelFactory.eINSTANCE.createDisjunction();
			disjunction.getTerm().add(leftChild);
			disjunction.getTerm().add(rightChild);
			return disjunction;
		}
	}

	@Override
	public <T extends Option> Term<Type> caseNot(Not<T> object) {
		Term<Type> child = doSwitch(object.getTerm());
		if (child instanceof True) {
			return VavemodelFactory.eINSTANCE.createFalse();
		} else if (child instanceof False) {
			return VavemodelFactory.eINSTANCE.createTrue();
		} else {
			Not<Type> not = VavemodelFactory.eINSTANCE.createNot();
			not.setTerm(child);
			return not;
		}
	}

	@Override
	public <T extends Option> Term<Type> caseVariable(Variable<T> object) {
		Variable<Type> variable = VavemodelFactory.eINSTANCE.createVariable();
		variable.setOption((Type) object.getOption());
		return variable;
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
