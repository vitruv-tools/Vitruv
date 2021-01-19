package tools.vitruv.dsls.commonalities.testutils

import org.eclipse.xtext.xbase.testing.CompilationTestHelper
import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Accessors
import java.io.File
import org.eclipse.xtext.generator.OutputConfiguration
import java.util.Set

class ConfigurableCompilationTestHelper extends CompilationTestHelper {
	@Accessors
	var Path testWorkspace
	@Accessors
	var Set<OutputConfiguration> outputConfigurations

	override File createFreshTempDir() {
		testWorkspace?.toFile ?: super.createFreshTempDir
	}
}
