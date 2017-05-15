package tools.vitruv.dsls.reactions.tests.manualTests

import org.junit.Test
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2
import tools.vitruv.dsls.reactions.ui.internal.ReactionsActivator
import org.eclipse.core.runtime.NullProgressMonitor
import com.google.inject.Provider
import com.google.inject.Inject
import tools.vitruv.dsls.reactions.api.generator.IReactionsEnvironmentGenerator
import tools.vitruv.dsls.reactions.api.generator.ReactionBuilderFactory
import org.junit.Ignore
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider

class ManualTests {
	private static class Injections {
		@Inject
		Provider<EclipseResourceFileSystemAccess2> fsaProv
	}

	@Ignore
	@Test
	public def testGenerateReactionsEnvironment() {
		val injections = new Injections
		val injector = ReactionsActivator::instance.getInjector(
			ReactionsActivator::TOOLS_VITRUV_DSLS_REACTIONS_REACTIONSLANGUAGE)
		injector.injectMembers(injections)
		val environmentGenerator = injector.getInstance(IReactionsEnvironmentGenerator)
		val reaction = new ReactionBuilderFactory().createReactionBuilder
		reaction.name = "TestReaction"
		reaction.trigger = new AllElementTypesDomainProvider().domain
		reaction.targetChange = new AllElementTypesDomainProvider().domain
		reaction.executionBlock = ''' {
			 println("That's it");
			}
			'''
		val fsa = injections.fsaProv.get
		val thisProject = ResourcesPlugin::workspace.root.getProject("tools.vitruv.dsls.reactions.tests")
		environmentGenerator.cleanAndSetProject(thisProject)
		environmentGenerator.addReaction("test", reaction.generateReaction)
		fsa.project = thisProject
		fsa.outputPath = "src-gen"
		fsa.currentSource = "src"
		fsa.monitor = new NullProgressMonitor
		environmentGenerator.generateEnvironment(fsa)
	}
}
