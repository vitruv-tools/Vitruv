package tools.vitruv.framework.propagation

import java.util.List
import tools.vitruv.framework.change.Metamodel

interface ChangePropagationSpecificationProvider extends Iterable<ChangePropagationSpecification> {
	/**
	 * Delivers change propagation specifications that react to changes in instances of the
	 * given metamodel. This covers specifications for the exact same metamodel and for
	 * those metamodels in which the given one is contained.
	 * 
	 * @param sourceMetamodel the metamodel to find change propagation specifications for
	 * @return change propagation specifications reacting to changes in instances of the given metamodel
	 * 
	 * @see Metamodel#contains
	 */
	def List<ChangePropagationSpecification> getChangePropagationSpecifications(Metamodel sourceMetamodel)
}
