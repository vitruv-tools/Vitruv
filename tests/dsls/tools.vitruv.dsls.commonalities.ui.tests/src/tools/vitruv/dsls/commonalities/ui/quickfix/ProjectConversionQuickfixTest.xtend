package tools.vitruv.dsls.commonalities.ui.quickfix

import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.InjectWith
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.junit.jupiter.api.Test
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*
import static extension java.nio.file.Files.*
import org.junit.jupiter.api.Assertions
import static extension tools.vitruv.dsls.common.ui.ProjectAccess.*
import java.util.List
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil
import tools.vitruv.testutils.xtext.BugFixedAbstractQuickfixTest
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.core.resources.IFile
import tools.vitruv.testutils.DisableAutoBuild

@DisplayName("quick fixes for project natures")
@ExtendWith(InjectionExtension, TestProjectManager, DisableAutoBuild)
@InjectWith(CommonalitiesLanguageUiInjectorProvider)
class ProjectConversionQuickfixTest extends BugFixedAbstractQuickfixTest {
	Path projectLocation
	static val testCommonality = '''
		concept test
		
		commonality Test {}
	'''
	
	@BeforeEach
	def void captureTestProject(@TestProject Path projectLocation) {
		this.projectLocation = projectLocation
	}

	@Test
	@DisplayName("converts the plugin nature to a Java project")
	def void fixNotPluginProject() {
		val testProject = createProjectAt(projectName, projectLocation) => [configureAsJavaProject()]

		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.NOT_A_PLUGIN_PROJECT,
			new Quickfix('Convert the project to a plugin project', null, testCommonality)
		)
		IResourcesSetupUtil.waitForBuild()

		Assertions.assertTrue(testProject.isPluginProject, "is plugin project")
		Assertions.assertTrue(
			projectLocation.resolve('META-INF').resolve('MANIFEST.MF').isRegularFile(),
			"MANIFEST.MF exists"
		)
		Assertions.assertTrue(
			projectLocation.resolve('build.properties').isRegularFile(),
			"build.properties exists"
		)
	}

	@Test
	@DisplayName("adds the Java nature to a plugin project")
	def void fixNotJavaProject() {
		val testProject = createProjectAt(projectName, projectLocation) => [
			open(null)
			setDescription(description => [
				natureIds = List.of(IBundleProjectDescription.PLUGIN_NATURE, XtextProjectHelper.NATURE_ID)
			], null)
		]

		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.NOT_A_JAVA_PROJECT,
			new Quickfix('Convert the project to a Java project', null, testCommonality)
		)
		IResourcesSetupUtil.waitForBuild()

		Assertions.assertTrue(testProject.isJavaProject, "is Java project")
	}

	@Test
	@DisplayName("adds the Java and the plugin nature to a plain project")
	def void fixNotPlainProject() {
		val testProject = createProjectAt(projectName, projectLocation) => [open(null)]

		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.NOT_A_JAVA_PROJECT,
			new Quickfix('Convert the project to a Java project', null, testCommonality)
		)
		IResourcesSetupUtil.waitForBuild()
		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.NOT_A_PLUGIN_PROJECT,
			new Quickfix('Convert the project to a plugin project', null, testCommonality)
		)
		IResourcesSetupUtil.waitForBuild()

		Assertions.assertTrue(testProject.isJavaProject, "is Java project")
		Assertions.assertTrue(testProject.isPluginProject, "is plugin project")
		Assertions.assertTrue(
			projectLocation.resolve('META-INF').resolve('MANIFEST.MF').isRegularFile(),
			"MANIFEST.MF exists"
		)
		Assertions.assertTrue(
			projectLocation.resolve('build.properties').isRegularFile(),
			"build.properties exists"
		)
	}

	override getProjectName() {
		projectLocation.fileName.toString
	}
	
	override XtextEditor openInEditor(IFile dslFile) {
		// the parent implementation waits for the build, but building the DSL
		// file will add the Java nature (the EMF builder does that). So let’s
		// do not build.
		try {
			return openEditor(dslFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
