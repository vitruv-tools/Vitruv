package tools.vitruv.testutils

import java.nio.file.Path
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.vsum.VirtualModelImpl
import tools.vitruv.testutils.matchers.CorrespondenceModelContainer

import org.eclipse.xtend.lib.annotations.Delegate
import static tools.vitruv.testutils.UriMode.*
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider

@ExtendWith(TestProjectManager, TestLogging)
abstract class VitruvApplicationTest implements CorrespondenceModelContainer, TestView {
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
	def protected UriMode getUriMode() { FILE_URIS }

	@BeforeEach
	def final package void prepareVirtualModelAndView(TestInfo testInfo, @TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
		TuidManager.instance.reinitialize()
		val changePropagationSpecifications = this.changePropagationSpecifications
		val domains = changePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
		var interactionProvider = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)
		var userInteractor = UserInteractionFactory.instance.createUserInteractor(interactionProvider)
		virtualModel = new VirtualModelImpl(vsumPath.toFile(), userInteractor, new VirtualModelConfiguration => [
			domains.forEach[domain|addMetamodel(domain)]
			changePropagationSpecifications.forEach[spec|addChangePropagationSpecification(spec)]
		])
		testView = generateTestView(testProjectPath, interactionProvider);
	}

	package def TestView generateTestView(Path testProjectPath,
		PredefinedInteractionResultProvider interactionProvider) {
		new ChangePublishingTestView(testProjectPath, interactionProvider, this.uriMode, virtualModel)
	}

	@AfterEach
	def final package void closeAfterTest() {
		close()
	}

	override CorrespondenceModel getCorrespondenceModel() { virtualModel.correspondenceModel }

	def protected InternalVirtualModel getVirtualModel() { virtualModel }

}
