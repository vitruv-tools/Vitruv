package tools.vitruv.framework.testutils.integration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.ChangePublishingTestView;
import tools.vitruv.change.testutils.views.NonTransactionalTestView;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.ModelInstance;

/**
 * Default implementation of a {@link VirtualModelBasedTestView} using a {@link VirtualModel} for
 * model management and change propagation.
 */
public class DefaultVirtualModelBasedTestView
    implements VirtualModelBasedTestView, NonTransactionalTestView {
  private InternalVirtualModel virtualModel;
  private NonTransactionalTestView testView;
  private Iterable<ChangePropagationSpecification> changePropagationSpecifications;
  private UriMode uriMode;

  /** Creates a new {@link DefaultVirtualModelBasedTestView} with the given parameters. */
  public DefaultVirtualModelBasedTestView(
      Path testProjectPath,
      Path vsumPath,
      Iterable<? extends ChangePropagationSpecification> changePropagationSpecifications,
      UriMode uriMode) {
    this.changePropagationSpecifications =
        toChangePropagationSpecList(changePropagationSpecifications);
    this.uriMode = uriMode;
    TestUserInteraction userInteraction = new TestUserInteraction();
    this.virtualModel = generateVirtualModel(vsumPath, userInteraction);
    this.testView = generateTestView(testProjectPath, userInteraction);
  }

  private Iterable<ChangePropagationSpecification> toChangePropagationSpecList(
      Iterable<? extends ChangePropagationSpecification> specs) {
    List<ChangePropagationSpecification> list = new ArrayList<>();
    for (ChangePropagationSpecification spec : specs) {
      list.add(spec);
    }
    return list;
  }

  private InternalVirtualModel generateVirtualModel(
      Path vsumPath, TestUserInteraction userInteraction) {
    try {
      return new VirtualModelBuilder()
          .withStorageFolder(vsumPath)
          .withUserInteractorForResultProvider(
              new TestUserInteraction.ResultProvider(userInteraction))
          .withChangePropagationSpecifications(changePropagationSpecifications)
          .buildAndInitialize();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private NonTransactionalTestView generateTestView(
      Path testProjectPath, TestUserInteraction userInteraction) {
    UuidResolver uuidResolver = virtualModel.getUuidResolver();
    Function<URI, Resource> resourceResolver =
        uri -> {
          ModelInstance modelInstance = virtualModel.getModelInstance(uri);
          return modelInstance != null ? modelInstance.getResource() : null;
        };
    return new ChangePublishingTestView(
        testProjectPath,
        userInteraction,
        this.uriMode,
        virtualModel,
        uuidResolver,
        resourceResolver);
  }

  @Override
  public VirtualModel getVirtualModel() {
    return this.virtualModel;
  }

  @Override
  public void close() {
    try {
      if (this.virtualModel != null) {
        this.virtualModel.dispose();
      }
      if (this.testView != null) {
        this.testView.close();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** Disposes resources used by the test view. */
  public void disposeViewResources() {
    testView.disposeViewResources();
  }

  /** Gets an EObject of the specified type from the given path.. */
  @Override
  public <T extends EObject> T from(Class<T> type, Path path) {
    return testView.from(type, path);
  }

  /** Gets an EObject of the specified type from the given resource. */
  @Override
  public <T extends EObject> T from(Class<T> type, Resource resource) {
    return testView.from(type, resource);
  }

  /** Gets an EObject of the specified type from the given URI. */
  public <T extends EObject> T from(Class<T> type, URI uri) {
    return testView.from(type, uri);
  }

  /** Gets the URI for the given path. */
  public URI getUri(Path path) {
    return testView.getUri(path);
  }

  /** Gets the user interaction instance used by this test view. */
  public TestUserInteraction getUserInteraction() {
    return testView.getUserInteraction();
  }

  /** Moves the given resource to the specified path. */
  public void moveTo(Resource resource, Path path) {
    testView.moveTo(resource, path);
  }

  /** Moves the given resource to the specified URI. */
  public void moveTo(Resource resource, URI uri) {
    testView.moveTo(resource, uri);
  }

  /** Propagates the recorded changes. */
  public List<PropagatedChange> propagate() {
    return testView.propagate();
  }

  /** Propagates changes for the given notifier using the provided consumer. */
  public <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
    return testView.propagate(notifier, consumer);
  }

  /** Records changes for the given notifier using the provided consumer. */
  public <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
    return testView.record(notifier, consumer);
  }

  /** Gets the resource at the given path. */
  @Override
  public Resource resourceAt(Path path) {
    return testView.resourceAt(path);
  }

  /** Gets the resource at the given URI. */
  public Resource resourceAt(URI uri) {
    return testView.resourceAt(uri);
  }

  /** Sets whether to dispose view resources after propagation. */
  public void setDisposeViewResourcesAfterPropagation(boolean value) {
    testView.setDisposeViewResourcesAfterPropagation(value);
  }

  /** Starts recording changes for the given notifier. */
  public <T extends Notifier> T startRecordingChanges(T notifier) {
    return testView.startRecordingChanges(notifier);
  }

  /** Stops recording changes for the given notifier. */
  public <T extends Notifier> T stopRecordingChanges(T notifier) {
    return testView.stopRecordingChanges(notifier);
  }
}
