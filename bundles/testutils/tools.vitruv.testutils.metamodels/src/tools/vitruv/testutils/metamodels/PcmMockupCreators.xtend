package tools.vitruv.testutils.metamodels

import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=Pcm_mockupFactory, stripPrefix = "P")
final class PcmMockupCreators {
	public static val pcm = new PcmMockupCreators()

	private new() {
	}
}
