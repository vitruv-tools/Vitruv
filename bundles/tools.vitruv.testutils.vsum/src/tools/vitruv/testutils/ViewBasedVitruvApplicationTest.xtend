package tools.vitruv.testutils

import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.vsum.VirtualModel
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.change.propagation.ChangePropagationSpecification

import org.junit.jupiter.api.TestInfo
import java.nio.file.Path
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import org.junit.jupiter.api.AfterEach
import static com.google.common.base.Preconditions.checkArgument
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.testutils.views.UriMode
import tools.vitruv.change.propagation.ChangePropagationMode
import tools.vitruv.framework.remote.server.VitruvServer

@ExtendWith(TestLogging, TestProjectManager)
abstract class ViewBasedVitruvApplicationTest {
	InternalVirtualModel virtualModel
	Path testProjectPath
	@Accessors(PROTECTED_GETTER)
	TestUserInteraction userInteraction
	VitruvServer server

	/**
	 * Determines the {@link ChangePropagationSpecification}s to be used in this test.
	 */
	def protected abstract Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications()
	
	/**
	 * Determines the {@link ChangePropagationMode} to use in this test.
	 * If <code>true</code> is returned, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used.
	 * If <code>false</code> is returned, {@link ChangePropagationMode#SINGLE_STEP} is used.
	 * Defaults to <code>true</code>.
	 */
	def protected enableTransitiveCyclicChangePropagation() { true }

	/**
	 * Determines which {@link UriMode} should be used for this test.
	 */
	def protected UriMode getUriMode() { UriMode.FILE_URIS }

	@BeforeEach
	def final package void prepareVirtualModel(TestInfo testInfo, @TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
		val changePropagationSpecifications = this.changePropagationSpecifications
		val changePropagationMode = enableTransitiveCyclicChangePropagation ? ChangePropagationMode.TRANSITIVE_CYCLIC : ChangePropagationMode.SINGLE_STEP
		userInteraction = new TestUserInteraction
		virtualModel = new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withViewType(new TestViewType())
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withChangePropagationSpecifications(changePropagationSpecifications).buildAndInitialize()
		virtualModel.changePropagationMode = changePropagationMode
		this.testProjectPath = testProjectPath
		if(RemoteUsageUtil.shouldUseRemote) {
			server = new VitruvServer([virtualModel])
			server.start()
		}
	}

	@AfterEach
	def final package void closeAfterTest() {
		if(RemoteUsageUtil.shouldUseRemote) {
			server.stop()
		}
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
