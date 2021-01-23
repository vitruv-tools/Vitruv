package tools.vitruv.dsls.commonalities.ui.validation

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.ui.quickfix.XtextAssertions
import java.nio.file.Path
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.TestProject
import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*
import static extension tools.vitruv.dsls.commonalities.ui.setup.CommonalitiesProjectSetup.*
import org.eclipse.xtext.resource.FileExtensionProvider
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil
import tools.vitruv.testutils.xtext.BugFixedAbstractEditorTest

@ExtendWith(InjectionExtension, TestProjectManager)
@InjectWith(CommonalitiesLanguageUiInjectorProvider)
@DisplayName("missing bundles validation")
class MissingBundlesValidationTest extends BugFixedAbstractEditorTest {
	// a lot of cases are covered by MissingBundlesQuickfixTest
	@Inject
	extension var XtextAssertions xtextAssertions
	@Inject
	var FileExtensionProvider fileExtensionProvider

	var Path projectLocation

	@BeforeEach
	def void captureProjectLocation(@TestProject Path projectLocation) {
		this.projectLocation = projectLocation
	}

	@Test
	@DisplayName("does not add issues for missing bundles for commonality participations")
	def void commonalityParticipation() {
		val project = createProjectAt(projectName, projectLocation).setupAsCommonalitiesProject()

		project.createFile('''referenced.«fileExtension»''', '''
			concept test
			
			commonality Referenced {}
		''')
		val testFile = project.createFile('''test.«fileExtension»''', '''
			concept test
			
			commonality Test {
				with test:Referenced
			}
		''')
		IResourcesSetupUtil.waitForBuild()

		MatcherAssert.assertThat(openEditor(testFile).document, hasNoValidationIssues)
	}

	def getFileExtension() {
		return fileExtensionProvider.primaryFileExtension
	}

	def getProjectName() {
		projectLocation.fileName.toString
	}
}
