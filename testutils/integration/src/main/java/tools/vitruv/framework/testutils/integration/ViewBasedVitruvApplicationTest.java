package tools.vitruv.framework.testutils.integration;

import com.google.common.base.Preconditions;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.nio.file.Path;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

@ExtendWith({TestLogging.class, TestProjectManager.class})
public abstract class ViewBasedVitruvApplicationTest {
  private InternalVirtualModel virtualModel;
  private Path testProjectPath;
  private TestUserInteraction userInteraction;

  /** Determines the {@link ChangePropagationSpecification}s to be used in this test. */
  protected abstract Iterable<? extends ChangePropagationSpecification>
      getChangePropagationSpecifications();

  /**
   * Determines the {@link ChangePropagationMode} to use in this test. If <code>true</code> is
   * returned, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used. If <code>false</code> is
   * returned, {@link ChangePropagationMode#SINGLE_STEP} is used. Defaults to <code>true</code>.
   */
  protected boolean enableTransitiveCyclicChangePropagation() {
    return true;
  }

  /** Determines which {@link UriMode} should be used for this test. */
  protected UriMode getUriMode() {
    return UriMode.FILE_URIS;
  }

  @BeforeEach
  final void prepareVirtualModel(
      TestInfo testInfo,
      @TestProject Path testProjectPath,
      @TestProject(variant = "vsum") Path vsumPath) {
    try {
      Iterable<? extends ChangePropagationSpecification> changePropagationSpecifications =
          getChangePropagationSpecifications();
      ChangePropagationMode changePropagationMode =
          enableTransitiveCyclicChangePropagation()
              ? ChangePropagationMode.TRANSITIVE_CYCLIC
              : ChangePropagationMode.SINGLE_STEP;
      userInteraction = new TestUserInteraction();
      virtualModel =
          new VirtualModelBuilder()
              .withStorageFolder(vsumPath)
              .withUserInteractorForResultProvider(
                  new TestUserInteraction.ResultProvider(userInteraction))
              .withChangePropagationSpecification(
                  (ChangePropagationSpecification) changePropagationSpecifications)
              .buildAndInitialize();
      virtualModel.setChangePropagationMode(changePropagationMode);
      this.testProjectPath = testProjectPath;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  final void closeAfterTest() {
    if (virtualModel != null) {
      virtualModel.dispose();
    }
  }

  public URI getUri(Path viewRelativePath) {
    Preconditions.checkArgument(viewRelativePath != null, "The viewRelativePath must not be null!");
    Preconditions.checkArgument(
        !viewRelativePath.toString().isEmpty(), "The viewRelativePath must not be empty!");

    switch (getUriMode()) {
      case PLATFORM_URIS:
        // platform URIs must always use '/' and be relative to the project (fileName) rather than
        // the workspace
        return URI.createPlatformResourceURI(
            testProjectPath
                .getFileName()
                .resolve(viewRelativePath)
                .normalize()
                .toString()
                .replace("\\", "/"),
            true);
      case FILE_URIS:
        return URIUtil.createFileURI(
            testProjectPath.resolve(viewRelativePath).normalize().toFile());
      default:
        throw new IllegalStateException("Unsupported URI mode: " + getUriMode());
    }
  }

  protected VirtualModel getVirtualModel() {
    return virtualModel;
  }

  protected TestUserInteraction getUserInteraction() {
    return userInteraction;
  }
}
