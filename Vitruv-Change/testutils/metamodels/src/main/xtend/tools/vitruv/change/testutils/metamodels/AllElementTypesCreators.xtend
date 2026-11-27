package tools.vitruv.change.testutils.metamodels

import tools.vitruv.change.utils.activeannotations.ModelCreators
import allElementTypes.AllElementTypesFactory

@ModelCreators(factory=AllElementTypesFactory)
final class AllElementTypesCreators {
	public static val aet = new AllElementTypesCreators

	private new() {
	}
}
