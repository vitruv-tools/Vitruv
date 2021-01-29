package tools.vitruv.testutils

import java.nio.file.Path
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.testutils.matchers.CorrespondenceModelContainer

import org.eclipse.xtend.lib.annotations.Delegate
import static tools.vitruv.testutils.UriMode.*
import java.util.List
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl

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
		val userInteraction = new TestUserInteraction
		val targetDomains = new VitruvDomainRepositoryImpl(
			changePropagationSpecifications.flatMap [List.of(sourceDomain, targetDomain)].toSet
		)
		virtualModel = new VirtualModelBuilder()
			.withStorageFolder(vsumPath)
			.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction))
			.withDomainRepository(targetDomains)
			.withChangePropagationSpecifications(changePropagationSpecifications)
			.build()
		testView = generateTestView(testProjectPath, userInteraction, targetDomains)
	}

	def package TestView generateTestView(Path testProjectPath, TestUserInteraction userInteraction, VitruvDomainRepository targetDomains) {
		new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel, targetDomains)
	}

	@AfterEach
	def final package void closeAfterTest() {
		virtualModel?.dispose()
		testView?.close()
	}

	override CorrespondenceModel getCorrespondenceModel() { virtualModel.correspondenceModel }

	def protected InternalVirtualModel getVirtualModel() { virtualModel }
}
