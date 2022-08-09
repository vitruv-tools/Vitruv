package tools.vitruv.testutils

import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Delegate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.vsum.internal.InternalVirtualModel

import tools.vitruv.testutils.views.UriMode
import tools.vitruv.testutils.views.TestView
import tools.vitruv.testutils.views.ChangePublishingTestView

@ExtendWith(TestLogging, TestProjectManager)
abstract class VitruvApplicationTest implements TestView {
	InternalVirtualModel virtualModel
	@Delegate
	TestView testView

	/**
	 * Determines the {@link ChangePropagationSpecification}s to be used in this test.
	 */
	def protected abstract Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications()

	/**
	 * Determines which {@link UriMode} should be used for this test.
	 */
	def protected UriMode getUriMode() { UriMode.FILE_URIS }

	@BeforeEach
	def final package void prepareVirtualModelAndView(TestInfo testInfo, @TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
		val changePropagationSpecifications = this.changePropagationSpecifications
		val userInteraction = new TestUserInteraction
		virtualModel = new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withChangePropagationSpecifications(changePropagationSpecifications).buildAndInitialize()
		testView = generateTestView(testProjectPath, userInteraction)
	}

	def package TestView generateTestView(Path testProjectPath, TestUserInteraction userInteraction) {
		new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel)
	}

	@AfterEach
	def final package void closeAfterTest() {
		virtualModel?.dispose()
		testView?.close()
	}

	def package InternalVirtualModel getInternalVirtualModel() { virtualModel }

	def protected VirtualModel getVirtualModel() { virtualModel }

}
