package tools.vitruv.framework.views.changederivation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtendWith;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import pcm_mockup.Repository;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import static tools.vitruv.change.testutils.metamodels.PcmMockupCreators.pcm;
import static tools.vitruv.change.testutils.metamodels.UmlMockupCreators.uml;
import uml_mockup.UPackage;

@ExtendWith({TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class})
public abstract class StateChangePropagationTest {
    protected static final String PCM_FILE_EXT = "pcm_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";

    protected Path testProjectFolder;
    protected Resource umlCheckpoint;
    protected Resource pcmCheckpoint;
    protected Resource umlModel;
    protected Resource pcmModel;
    protected Repository pcmRoot;
    protected UPackage umlRoot;
    protected ChangeRecorder changeRecorder;
    protected ResourceSet resourceSet;
    protected ResourceSet checkpointResourceSet;

    /**
     * Creates the strategy, sets up the test model and prepares everything for determining changes.
     */
    @BeforeEach
    public void setup(@TestProject Path testProjectFolder) {
        this.testProjectFolder = testProjectFolder;
        // Setup:
        resourceSet = withGlobalFactories(new ResourceSetImpl());
        checkpointResourceSet = withGlobalFactories(new ResourceSetImpl());
        changeRecorder = new ChangeRecorder(resourceSet);
        // Create mockup models:
        record(resourceSet, rs -> {
            try {
                createPcmMockupModel();
                createUmlMockupModel();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        // create model checkpoints and start recording:
        umlCheckpoint = createCheckpoint(umlModel);
        pcmCheckpoint = createCheckpoint(pcmModel);
        startRecording(umlModel);
        startRecording(pcmModel);
    }

    public static Stream<Named<StateBasedChangeResolutionStrategy>> strategiesToTest() {
        return Stream.of(
                Named.of("identifiers when available",
                        new DefaultStateBasedChangeResolutionStrategy(
                                UseIdentifiers.WHEN_AVAILABLE)),
                Named.of("only identifiers",
                        new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.ONLY)),
                Named.of("never identifiers",
                        new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.NEVER)));
    }

    /**
     * Stops recording in case the test does not call getRecordedChanges() or
     * getChangeFromComparisonWithCheckpoint().
     */
    @AfterEach
    public void stopRecording() {
        changeRecorder.close();
    }

    /**
     * USE THIS METHOD TO COMPARE RESULTS! Compares two changes: The recorded change sequence and
     * the resolved changes by the state delta based strategy.
     */
    protected void compareChanges(Resource model, Resource checkpoint,
            StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
        model.save(null);
        VitruviusChange<EObject> deltaBasedChange = endRecording(model);
        VitruviusChange<HierarchicalId> unresolvedStateBasedChange =
                strategyToTest.getChangeSequenceBetween(model, checkpoint);
        assertNotNull(unresolvedStateBasedChange);
        VitruviusChange<EObject> stateBasedChange =
                VitruviusChangeResolverFactory.forHierarchicalIds(checkpoint.getResourceSet())
                        .resolveAndApply(unresolvedStateBasedChange);
        String message = getTextualRepresentation(stateBasedChange, deltaBasedChange);
        var stateBasedChangedObjects = stateBasedChange.getAffectedAndReferencedEObjects();
        var deltaBasedChangedObjects = deltaBasedChange.getAffectedAndReferencedEObjects();
        assertEquals(stateBasedChangedObjects.size(), deltaBasedChangedObjects.size(),
                "Got a different number of changed objects:\n" + message);
        stateBasedChangedObjects.forEach(stateBasedChangedObject -> {
            assertTrue(
                    deltaBasedChangedObjects.stream()
                            .anyMatch(it -> EcoreUtil.equals(it, stateBasedChangedObject)),
                    "Could not find this changed object in the delta based change:\n"
                            + stateBasedChangedObject + "\n\n" + message);
        });
    }

    /**
     * Returns the recorded change sequences (the "original" changes) for a specific model instance.
     */
    protected VitruviusChange<EObject> endRecording(Notifier notifier) {
        changeRecorder.removeFromRecording(notifier);
        return changeRecorder.endRecording();
    }

    protected String getTextualRepresentation(VitruviusChange<?> stateBasedChange,
            VitruviusChange<?> deltaBasedChange) {
        return "State-based " + stateBasedChange + "\n" + "Delta-based " + deltaBasedChange;
    }

    protected void createPcmMockupModel() throws IOException {
        pcmModel = resourceSet.createResource(getModelURI("My.pcm_mockup"));
        pcmRoot = pcm.Repository();
        pcmRoot.setName("RootRepository");
        pcmRoot.getInterfaces().add(pcm.Interface());
        pcmRoot.getComponents().add(pcm.Component());
        pcmModel.getContents().add(pcmRoot);
        pcmModel.save(null);
    }

    protected void createUmlMockupModel() throws IOException {
        umlModel = resourceSet.createResource(getModelURI("My.uml_mockup"));
        umlRoot = uml.Package();
        umlRoot.setName("RootPackage");
        umlRoot.getInterfaces().add(uml.Interface());
        umlRoot.getClasses().add(uml.Class());
        umlModel.getContents().add(umlRoot);
        umlModel.save(null);
    }

    protected void startRecording(Notifier notifier) {
        changeRecorder.addToRecording(notifier);
        if (!changeRecorder.isRecording()) {
            changeRecorder.beginRecording();
        }
    }

    protected <T extends Notifier> VitruviusChange<EObject> record(T notifier,
            java.util.function.Consumer<T> function) {
        startRecording(notifier);
        function.accept(notifier);
        return endRecording(notifier);
    }

    protected Resource createCheckpoint(Resource original) {
        return checkpointResourceSet.getResource(original.getURI(), true);
    }

    protected URI getModelURI(String modelFileName) {
        return createFileURI(testProjectFolder.resolve("model").resolve(modelFileName).toFile());
    }

    protected Resource getUmlCheckpoint() {
        return umlCheckpoint;
    }

    protected Resource getPcmCheckpoint() {
        return pcmCheckpoint;
    }

    protected Resource getUmlModel() {
        return umlModel;
    }

    protected Resource getPcmModel() {
        return pcmModel;
    }

    protected Repository getPcmRoot() {
        return pcmRoot;
    }

    protected UPackage getUmlRoot() {
        return umlRoot;
    }

    protected ResourceSet getResourceSet() {
        return resourceSet;
    }
}
