package tools.vitruv.dsls.commonalities.ui.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.testutils.domains.AllElementTypes2DomainProvider
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider
import tools.vitruv.testutils.domains.UmlMockupDomainProvider
import tools.vitruv.testutils.domains.PcmMockupDomainProvider
import static extension tools.vitruv.testutils.util.TestSetup.getFileExtension
import java.nio.file.Path

@Utility
class ModelPathCreators {
	// We don’t use TestSetup.getProjectModelPath because we don’t want to have to specify the path in the commonalities
	static def allElementTypes2(String modelName) {
		Path.of('''«modelName».«new AllElementTypes2DomainProvider().fileExtension»''')
	}

	static def allElementTypes(String modelName) {
		Path.of('''«modelName».«new AllElementTypesDomainProvider().fileExtension»''')
	}

	static def uml_mockup(String modelName) {
		Path.of('''«modelName».«new UmlMockupDomainProvider().fileExtension»''')
	}

	static def pcm_mockup(String modelName) {
		Path.of('''«modelName».«new PcmMockupDomainProvider().fileExtension»''')
	}
}
