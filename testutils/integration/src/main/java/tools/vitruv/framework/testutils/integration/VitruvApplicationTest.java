package tools.vitruv.framework.testutils.integration;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.vsum.VirtualModel;

/** Abstract base class for Vitruv application tests using a {@link VirtualModelBasedTestView}. */
@ExtendWith({TestLogging.class, TestProjectManager.class})
public abstract class VitruvApplicationTest implements VirtualModelBasedTestView {
  private VirtualModelBasedTestView testView;

  /** Determines the {@link ChangePropagationSpecification}s to be used in this test. */
  protected abstract Iterable<? extends ChangePropagationSpecification>
      getChangePropagationSpecifications();

  /** Determines which {@link UriMode} should be used for this test. */
  protected UriMode getUriMode() {
    return UriMode.FILE_URIS;
  }

  /**
   * Initializes the test view before each test.
   *
   * @param testProjectPath the path to the test project
   * @param vsumPath the path to the virtual model storage
   */
  @BeforeEach
  public final void initialize(
      @TestProject Path testProjectPath, @TestProject(variant = "vsum") Path vsumPath) {
    this.testView = generateTestView(testProjectPath, vsumPath);
  }

  /**
   * Generates the test view to be used in this test.
   *
   * @param testProjectPath the path to the test project
   * @param vsumPath the path to the virtual model storage
   * @return the generated test view
   */
  public VirtualModelBasedTestView generateTestView(Path testProjectPath, Path vsumPath) {
    return new DefaultVirtualModelBasedTestView(
        testProjectPath, vsumPath, getChangePropagationSpecifications(), getUriMode());
  }

  @AfterEach
  public final void closeAfterTest() throws Exception {
    if (this.testView != null) {
      this.testView.close();
    }
  }

  @Override
  public void close() throws Exception {
    if (this.testView != null) {
      this.testView.close();
    }
  }

  @Override
  public <T extends EObject> T from(Class<T> type, Path path) {
    return this.testView.from(type, path);
  }

  @Override
  public <T extends EObject> T from(Class<T> type, Resource resource) {
    return this.testView.from(type, resource);
  }

  @Override
  public <T extends EObject> T from(Class<T> type, URI uri) {
    return this.testView.from(type, uri);
  }

  @Override
  public URI getUri(Path path) {
    return this.testView.getUri(path);
  }

  @Override
  public TestUserInteraction getUserInteraction() {
    return this.testView.getUserInteraction();
  }

  @Override
  public VirtualModel getVirtualModel() {
    return this.testView.getVirtualModel();
  }

  @Override
  public void moveTo(Resource resource, Path path) {
    this.testView.moveTo(resource, path);
  }

  @Override
  public void moveTo(Resource resource, URI uri) {
    this.testView.moveTo(resource, uri);
  }

  @Override
  public <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
    return this.testView.propagate(notifier, consumer);
  }

  @Override
  public <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
    return this.testView.record(notifier, consumer);
  }

  @Override
  public Resource resourceAt(Path path) {
    return this.testView.resourceAt(path);
  }

  @Override
  public Resource resourceAt(URI uri) {
    return this.testView.resourceAt(uri);
  }
}
