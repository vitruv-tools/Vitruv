package tools.vitruv.variability.vave.impl;

import java.util.ArrayList;
import java.util.Collection;

import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.Equivalence;
import vavemodel.Feature;
import vavemodel.Implication;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class FeatureCollector extends VavemodelSwitch<Collection<Option>> {
	private Collection<Option> literals = new ArrayList<>();
	@Override
	public <T extends Option> Collection<Option> caseConjunction(Conjunction<T> e) {
		doSwitch(e.getTerm().get(0));
		doSwitch(e.getTerm().get(1));
		return this.literals;
	}
	@Override
	public <T extends Option> Collection<Option> caseDisjunction(Disjunction<T> e) {
		doSwitch(e.getTerm().get(0));
		doSwitch(e.getTerm().get(1));
		return this.literals;
	}
	@Override
	public <T extends Option> Collection<Option> caseEquivalence(Equivalence<T> e) {
		doSwitch(e.getTerm().get(0));
		doSwitch(e.getTerm().get(1));
		return this.literals;
	}
	@Override
	public <T extends Option> Collection<Option> caseImplication(Implication<T> e) {
		doSwitch(e.getTerm().get(0));
		doSwitch(e.getTerm().get(1));
		return this.literals;
	}
	@Override
	public <T extends Option> Collection<Option> caseNot(Not<T> e) {
		doSwitch(e.getTerm());
		return this.literals;
	}
	@Override
	public <T extends Option> Collection<Option> caseVariable(Variable<T> variable) {
		this.literals.add(variable.getOption());
		return this.literals;
	}
}
