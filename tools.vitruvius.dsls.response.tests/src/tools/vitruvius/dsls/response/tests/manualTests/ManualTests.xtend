package tools.vitruvius.dsls.response.tests.manualTests

import org.junit.Test
import tools.vitruvius.dsls.response.api.generator.ResponseBuilderFactory
import allElementTypes.AllElementTypesPackage
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2
import tools.vitruvius.dsls.response.api.generator.IResponseEnvironmentGenerator
import tools.vitruvius.dsls.response.ui.internal.ResponseActivator
import org.eclipse.core.runtime.NullProgressMonitor
import com.google.inject.Provider
import com.google.inject.Inject

class ManualTests {
	private static class Injections {
		@Inject Provider<EclipseResourceFileSystemAccess2> fsaProv;
	}
	@Test
	public def testGenerateResponseEnvironment() {
		val injections = new Injections();
		val injector = ResponseActivator.getInstance().getInjector(ResponseActivator.EDU_KIT_IPD_SDQ_VITRUVIUS_DSLS_RESPONSE_RESPONSELANGUAGE);
		injector.injectMembers(injections);
		val environmentGenerator = injector.getInstance(IResponseEnvironmentGenerator);
		val response = new ResponseBuilderFactory().createResponseBuilder()
			.setName("TestResponse")
			.setTrigger(AllElementTypesPackage.eINSTANCE)
			.setTargetChange(AllElementTypesPackage.eINSTANCE)
			.setExecutionBlock(''' {
				println("That's it");
			}
			''')
		val fsa = injections.fsaProv.get();
		val thisProject = ResourcesPlugin.workspace.root.getProject("tools.vitruvius.dsls.response.tests");
		environmentGenerator.cleanAndSetProject(thisProject);
		environmentGenerator.addResponse("test", response.generateResponse());
		fsa.project = thisProject;
		fsa.outputPath = "src-gen";
		fsa.currentSource = "src";
		fsa.monitor = new NullProgressMonitor();
		environmentGenerator.generateEnvironment(fsa);
	}
}