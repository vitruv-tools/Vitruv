package tools.vitruv.change.changederivation;

import static com.google.common.base.Preconditions.checkArgument;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getReferencedProxies;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import lombok.val;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.match.IMatchEngine.Factory;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.postprocessor.PostProcessorDescriptorRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;

/**
 * Abstract base class for StateBasedChangeResolutionStrategies that uses EMFCompare
 * to resolve a diff to a sequence of individual changes.
 */
public abstract class AbstractStateBasedChangeResolutionStrategy
    implements StateBasedChangeResolutionStrategy {

  private void checkNoProxies(Resource resource, String stateNotice) {
    List<EObject> proxies = new LinkedList<>();
    getReferencedProxies(resource).forEach(proxies::add);

    checkArgument(
        proxies.isEmpty(),
        "%s '%s' should not contain proxies, but contains the following: %s",
        stateNotice, resource.getURI(),
        String.join(
          ", ",
          proxies.stream().map(EObject::toString).toList()
        )
    );
  }

  private VitruviusChange<HierarchicalId> recordChanges(Resource resource, Runnable function) {
    try (var changeRecorder = new ChangeRecorder(resource.getResourceSet())) {
      changeRecorder.beginRecording();
      changeRecorder.addToRecording(resource);
      function.run();
      val recordedChanges = changeRecorder.endRecording();
      val changeResolver = VitruviusChangeResolverFactory.forHierarchicalIds(
          resource.getResourceSet()
      );
      return changeResolver.assignIds(recordedChanges);
    }
  }

  @Override
  public VitruviusChange<HierarchicalId> getChangeSequenceBetween(
      Resource newState, Resource oldState) {
    checkArgument(oldState != null && newState != null,
        "old state or new state must not be null!"
    );
    checkNoProxies(newState, "new state");
    checkNoProxies(oldState, "old state");

    final ResourceSet monitoredResourceSet = withGlobalFactories(new ResourceSetImpl());
    final Resource currentStateCopy = ResourceCopier.copyViewResource(oldState, 
        monitoredResourceSet);

    return recordChanges(currentStateCopy, () -> {
      if (oldState.getURI() != newState.getURI()) {
        currentStateCopy.setURI(newState.getURI());
      }
      compareStatesAndReplayChanges(newState, currentStateCopy);
    });
  }

  @Override
  public VitruviusChange<HierarchicalId> getChangeSequenceForCreated(Resource newState) {
    checkArgument(newState != null, "new state must not be null!");
    checkNoProxies(newState, "new state");
    // Root elements can be automatically generated during resource creation (e.g., Java packages).
    // Thus, we create the resource and then monitor the re-insertion of the elements
    final ResourceSet monitoredResourceSet = withGlobalFactories(
      new ResourceSetImpl()
    );
    final Resource newResource = monitoredResourceSet.createResource(newState.getURI());
    newResource.getContents().clear();
    return recordChanges(newResource, () -> newResource
        .getContents()
        .addAll(EcoreUtil.copyAll(newState.getContents()))
    );
  }

  @Override
  public VitruviusChange<HierarchicalId> getChangeSequenceForDeleted(Resource oldState) {
    checkArgument(oldState != null, "old state must not be null!");
    checkNoProxies(oldState, "old state");
    // Setup resolver and copy state:
    val monitoredResourceSet = withGlobalFactories(new ResourceSetImpl());
    val currentStateCopy = ResourceCopier.copyViewResource(oldState, monitoredResourceSet);
    return recordChanges(currentStateCopy, () -> currentStateCopy.getContents().clear());
  }

  /**
   * Compares states using the EMFCompareInstance provided by {@link #getEMFCompareInstance()} 
   * and replays the changes to the current state.
   */
  private void compareStatesAndReplayChanges(Notifier newState, Notifier currentState) {
    val scope = new DefaultComparisonScope(newState, currentState, null);
    val differences = buildEMFCompare().compare(scope).getDifferences();
    // Replay the EMF compare differences
    val mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
    val merger = new BatchMerger(mergerRegistry);
    merger.copyAllLeftToRight(differences, new BasicMonitor());
  }

  private EMFCompare buildEMFCompare() {
    val registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
    getMatchEngineFactories().forEach(factory -> {
      factory.setRanking(20);
      registry.add(factory);
    });

    final PostProcessorDescriptorRegistryImpl<String> postProcessorRegistry =
        new PostProcessorDescriptorRegistryImpl<>();
    getPostProcessors().forEach(processor ->
        postProcessorRegistry.put(processor.getInstanceClassName(), processor)
    );

    return EMFCompare.builder()
      .setMatchEngineFactoryRegistry(registry)
      .setDiffEngine(getDiffEngine())
      .setPostProcessorRegistry(postProcessorRegistry)
      .build();
  }

  /**
   * {@link Factory}'s (MatchEngine + rank = MatchEngine suitable for comparison) used by EMFCompare
   * The rank will be overwritten by this class 
   * to ensure that it has a greater value than the default implementation.
   */
  protected abstract Collection<Factory> getMatchEngineFactories();

  /**
   * {@link IDiffEngine} used by EMFCompare.
   */
  protected abstract IDiffEngine getDiffEngine();

  /**
   * {@link IPostProcessor.Descriptor}'s (PostProcessor + Pattern where to be applied) 
   * used by EMFCompare.
   */
  protected abstract Collection<IPostProcessor.Descriptor> getPostProcessors();
}
