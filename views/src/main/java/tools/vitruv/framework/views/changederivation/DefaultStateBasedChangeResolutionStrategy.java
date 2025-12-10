package tools.vitruv.framework.views.changederivation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.match.IMatchEngine.Factory.Registry;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import static com.google.common.base.Preconditions.checkArgument;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;

/**
 * This default strategy for diff based state changes uses EMFCompare to resolve
 * a
 * diff to a sequence of individual changes.
 */
public class DefaultStateBasedChangeResolutionStrategy implements StateBasedChangeResolutionStrategy {
    /** The identifier matching behavior used by this strategy */
    private final UseIdentifiers useIdentifiers;

    /**
     * Creates a new instance with the default identifier matching behavior
     * which is match by identifier when available.
     */
    public DefaultStateBasedChangeResolutionStrategy() {
        this(UseIdentifiers.WHEN_AVAILABLE);
    }

    /**
     * Creates a new instance with the provided identifier matching behavior.
     * 
     * @param useIdentifiers The identifier matching behavior to use.
     */
    public DefaultStateBasedChangeResolutionStrategy(UseIdentifiers useIdentifiers) {
        this.useIdentifiers = useIdentifiers;
    }

    private void checkNoProxies(Resource resource, String stateNotice) {
        List<String> proxies = StreamSupport.stream(ResourceUtil.getReferencedProxies(resource).spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.toList());
        checkArgument(proxies.isEmpty(),
                "%s '%s' should not contain proxies, but contains the following: %s",
                stateNotice, resource.getURI(), String.join(", ", proxies));
    }

    @Override
    public VitruviusChange<HierarchicalId> getChangeSequenceBetween(Resource newState, Resource oldState) {
        checkArgument(oldState != null && newState != null,
                "old state or new state must not be null!");
        checkNoProxies(newState, "new state");
        checkNoProxies(oldState, "old state");

        ResourceSetImpl monitoredResourceSet = new ResourceSetImpl();
        Resource currentStateCopy = ResourceCopier.copyViewResource(oldState, monitoredResourceSet);

        return record(currentStateCopy, () -> {
            if (!oldState.getURI().equals(newState.getURI())) {
                currentStateCopy.setURI(newState.getURI());
            }
            compareStatesAndReplayChanges(newState, currentStateCopy);
        });
    }

    @Override
    public VitruviusChange<HierarchicalId> getChangeSequenceForCreated(Resource newState) {
        checkArgument(newState != null, "new state must not be null!");
        checkNoProxies(newState, "new state");

        // It is possible that root elements are automatically generated during resource
        // creation (e.g., Java packages).
        // Thus, we create the resource and then monitor the re-insertion of the
        // elements
        ResourceSetImpl monitoredResourceSet = new ResourceSetImpl();
        Resource newResource = monitoredResourceSet.createResource(newState.getURI());
        newResource.getContents().clear();

        return record(newResource, () -> {
            newResource.getContents().addAll(EcoreUtil.copyAll(newState.getContents()));
        });
    }

    @Override
    public VitruviusChange<HierarchicalId> getChangeSequenceForDeleted(Resource oldState) {
        checkArgument(oldState != null, "old state must not be null!");
        checkNoProxies(oldState, "old state");

        // Setup resolver and copy state:
        ResourceSetImpl monitoredResourceSet = new ResourceSetImpl();
        Resource currentStateCopy = ResourceCopier.copyViewResource(oldState, monitoredResourceSet);

        return record(currentStateCopy, () -> {
            currentStateCopy.getContents().clear();
        });
    }

    private VitruviusChange<HierarchicalId> record(Resource resource, Runnable function) {
        try (ChangeRecorder changeRecorder = new ChangeRecorder(resource.getResourceSet())) {
            changeRecorder.beginRecording();
            changeRecorder.addToRecording(resource);
            function.run();

            VitruviusChange<EObject> recordedChanges = changeRecorder.endRecording();
            var changeResolver = VitruviusChangeResolverFactory.forHierarchicalIds(resource.getResourceSet());
            return changeResolver.assignIds(recordedChanges);
        }
    }

    /**
     * Compares states using EMFCompare and replays the changes to the current
     * state.
     */
    private void compareStatesAndReplayChanges(Notifier newState, Notifier currentState) {
        DefaultComparisonScope scope = new DefaultComparisonScope(newState, currentState, null);

        Registry matchEngineRegistry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
        matchEngineRegistry.add(new MatchEngineFactoryImpl(useIdentifiers));

        EMFCompare emfCompare = EMFCompare.builder()
                .setMatchEngineFactoryRegistry(matchEngineRegistry)
                .build();

        Comparison comparison = emfCompare.compare(scope);

        // Replay the EMF compare differences
        org.eclipse.emf.compare.merge.IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
        BatchMerger merger = new BatchMerger(mergerRegistry);
        merger.copyAllLeftToRight(comparison.getDifferences(), new BasicMonitor());
    }

    public UseIdentifiers getUseIdentifiers() {
        return useIdentifiers;
    }
}