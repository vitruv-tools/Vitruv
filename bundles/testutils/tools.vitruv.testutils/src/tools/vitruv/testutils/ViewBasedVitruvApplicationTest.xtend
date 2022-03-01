package tools.vitruv.testutils

import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.vsum.VirtualModel
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.framework.propagation.ChangePropagationSpecification

import static tools.vitruv.testutils.UriMode.*
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import org.junit.jupiter.api.TestInfo
import java.nio.file.Path
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import org.junit.jupiter.api.AfterEach
import static com.google.common.base.Preconditions.checkArgument
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

@ExtendWith(TestLogging, TestProjectManager)
abstract class ViewBasedVitruvApplicationTest {
	InternalVirtualModel virtualModel
	Path testProjectPath
	@Accessors(PROTECTED_GETTER)
	TestUserInteraction userInteraction

	/**
	 * Determines the {@link ChangePropagationSpecification}s to be used in this test.
	 */
	def protected abstract Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications()

	/**
	 * Determines which {@link UriMode} should be used for this test.
	 */
	def protected UriMode getUriMode() { FILE_URIS }

	@BeforeEach
	def final package void prepareVirtualModel(TestInfo testInfo, @TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
		val changePropagationSpecifications = this.changePropagationSpecifications
		userInteraction = new TestUserInteraction
		val targetDomains = new VitruvDomainRepositoryImpl(
			changePropagationSpecifications.flatMap [
				val source = sourceMetamodelRootNsUris
				val target = targetMetamodelRootNsUris
				VitruvDomainProviderRegistry.allDomainProviders.map[domain].filter [
					source.contains(metamodelRootPackage.nsURI) || target.contains(metamodelRootPackage.nsURI)
				]
			].toSet
		)
		virtualModel = new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withDomainRepository(targetDomains) //
		.withChangePropagationSpecifications(changePropagationSpecifications).buildAndInitialize()
		this.testProjectPath = testProjectPath
	}

	@AfterEach
	def final package void closeAfterTest() {
		virtualModel?.dispose()
	}

	def getUri(Path viewRelativePath) {
		checkArgument(viewRelativePath !== null, "The viewRelativePath must not be null!")
		checkArgument(!viewRelativePath.isEmpty, "The viewRelativePath must not be empty!")
		return switch (uriMode) {
			case PLATFORM_URIS: {
				// platform URIs must always use '/' and be relative to the project (fileName) rather than the workspace
				createPlatformResourceURI(testProjectPath.fileName.resolve(viewRelativePath).normalize().join('/'),
					true)
			}
			case FILE_URIS:
				createFileURI(testProjectPath.resolve(viewRelativePath).normalize().toFile())
		}
	}

	def protected VirtualModel getVirtualModel() { virtualModel }

}
