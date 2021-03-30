package tools.vitruv.testutils

import java.nio.file.Path
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.vsum.internal.InternalVirtualModel

import org.eclipse.xtend.lib.annotations.Delegate
import static tools.vitruv.testutils.UriMode.*
import java.util.List
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.vsum.VirtualModel

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
	def protected UriMode getUriMode() { FILE_URIS }

	@BeforeEach
	def final package void prepareVirtualModelAndView(TestInfo testInfo, @TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
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
			.buildAndInitialize()
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

	def package InternalVirtualModel getInternalVirtualModel() { virtualModel }
	
	def protected VirtualModel getVirtualModel() { virtualModel }
	
}
