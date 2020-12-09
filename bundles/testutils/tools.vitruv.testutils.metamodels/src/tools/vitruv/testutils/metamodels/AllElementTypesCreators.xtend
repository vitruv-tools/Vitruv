package tools.vitruv.testutils.metamodels

import tools.vitruv.testutils.activeannotations.ModelCreators
import allElementTypes.AllElementTypesFactory

@ModelCreators(factory=AllElementTypesFactory)
final class AllElementTypesCreators {
	public static val aet = new AllElementTypesCreators

	private new() {
	}
}
