package tools.vitruv.change.testutils.metamodels

import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.change.utils.activeannotations.ModelCreators

@ModelCreators(factory=Pcm_mockupFactory, stripPrefix = "P")
final class PcmMockupCreators {
	public static val pcm = new PcmMockupCreators()
}
