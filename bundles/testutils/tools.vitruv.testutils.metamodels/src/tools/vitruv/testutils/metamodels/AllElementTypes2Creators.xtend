package tools.vitruv.testutils.metamodels

import allElementTypes2.AllElementTypes2Factory
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=AllElementTypes2Factory)
final class AllElementTypes2Creators {
	public static val aet2 = new AllElementTypes2Creators()
}
