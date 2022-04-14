package tools.vitruv.testutils.metamodels

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Path

@Utility
class TestMetamodelsFileExtensions {
	static def allElementTypes(String modelName) {
		Path.of(modelName + ".allElementTypes")
	}

	static def allElementTypes2(String modelName) {
		Path.of(modelName + ".allElementTypes2")
	}

	static def pcm_mockup(String modelName) {
		Path.of(modelName + ".pcm_mockup")
	}

	static def uml_mockup(String modelName) {
		Path.of(modelName + ".uml_mockup")
	}
}