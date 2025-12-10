package tools.vitruv.framework.views.changederivation;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.PcmMockupCreators.pcm;
import static tools.vitruv.change.testutils.metamodels.UmlMockupCreators.uml;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtendWith;
import pcm_mockup.Repository;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import uml_mockup.UPackage;

/**
 * Abstract base class for testing state-based change propagation strategies. Provides setup and
 * utilities for comparing state-based changes with delta-based changes.
 */
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
    record(
        resourceSet,
        rs -> {
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

  /**
   * Provides the different strategies to test.
   *
   * @return the strategies to test
   */
  public static Stream<Named<StateBasedChangeResolutionStrategy>> strategiesToTest() {
    return Stream.of(
        Named.of(
            "identifiers when available",
            new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.WHEN_AVAILABLE)),
        Named.of(
            "only identifiers", new DefaultStateBasedChangeResolutionStrategy(UseIdentifiers.ONLY)),
        Named.of(
            "never identifiers",
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
   * USE THIS METHOD TO COMPARE RESULTS! Compares two changes: The recorded change sequence and the
   * resolved changes by the state delta based strategy.
   */
  protected void compareChanges(
      Resource model, Resource checkpoint, StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
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
    assertEquals(
        stateBasedChangedObjects.size(),
        deltaBasedChangedObjects.size(),
        "Got a different number of changed objects:\n" + message);
    stateBasedChangedObjects.forEach(
        stateBasedChangedObject -> {
          assertTrue(
              deltaBasedChangedObjects.stream()
                  .anyMatch(it -> EcoreUtil.equals(it, stateBasedChangedObject)),
              "Could not find this changed object in the delta based change:\n"
                  + stateBasedChangedObject
                  + "\n\n"
                  + message);
        });
  }

  /**
   * Returns the recorded change sequences (the "original" changes) for a specific model instance.
   */
  protected VitruviusChange<EObject> endRecording(Notifier notifier) {
    changeRecorder.removeFromRecording(notifier);
    return changeRecorder.endRecording();
  }

  /**
   * Gets a textual representation of the two changes for easier debugging.
   *
   * @param stateBasedChange the state-based change
   * @param deltaBasedChange the delta-based change
   * @return a textual representation of the two changes
   */
  protected String getTextualRepresentation(
      VitruviusChange<?> stateBasedChange, VitruviusChange<?> deltaBasedChange) {
    return "State-based " + stateBasedChange + "\n" + "Delta-based " + deltaBasedChange;
  }

  /** Creates and records changes in a resource set. */
  protected void createPcmMockupModel() throws IOException {
    pcmModel = resourceSet.createResource(getModelURI("My.pcm_mockup"));
    pcmRoot = pcm.Repository();
    pcmRoot.setName("RootRepository");
    pcmRoot.getInterfaces().add(pcm.Interface());
    pcmRoot.getComponents().add(pcm.Component());
    pcmModel.getContents().add(pcmRoot);
    pcmModel.save(null);
  }

  /**
   * Creates and records changes in a resource set.
   *
   * @throws IOException if an I/O error occurs
   */
  protected void createUmlMockupModel() throws IOException {
    umlModel = resourceSet.createResource(getModelURI("My.uml_mockup"));
    umlRoot = uml.Package();
    umlRoot.setName("RootPackage");
    umlRoot.getInterfaces().add(uml.Interface());
    umlRoot.getClasses().add(uml.Class());
    umlModel.getContents().add(umlRoot);
    umlModel.save(null);
  }

  /**
   * Starts recording changes on the given notifier.
   *
   * @param notifier the notifier to start recording on
   */
  protected void startRecording(Notifier notifier) {
    changeRecorder.addToRecording(notifier);
    if (!changeRecorder.isRecording()) {
      changeRecorder.beginRecording();
    }
  }

  /**
   * Records changes performed by the given function on the given notifier.
   *
   * @param <T> the type of the notifier
   * @param notifier the notifier to record changes on
   * @param function the function that performs changes on the notifier
   * @return the recorded changes
   */
  protected <T extends Notifier> VitruviusChange<EObject> record(
      T notifier, java.util.function.Consumer<T> function) {
    startRecording(notifier);
    function.accept(notifier);
    return endRecording(notifier);
  }

  /**
   * Creates a checkpoint resource for the given original resource.
   *
   * @param original the original resource
   * @return the checkpoint resource
   */
  protected Resource createCheckpoint(Resource original) {
    return checkpointResourceSet.getResource(original.getURI(), true);
  }

  /**
   * gets the model URI for the given model file name.
   *
   * @param modelFileName the model file name
   * @return the model URI
   */
  protected URI getModelURI(String modelFileName) {
    return createFileURI(testProjectFolder.resolve("model").resolve(modelFileName).toFile());
  }

  /**
   * Gets the UML checkpoint resource.
   *
   * @return the UML checkpoint resource
   */
  protected Resource getUmlCheckpoint() {
    return umlCheckpoint;
  }

  /**
   * Gets the PCM checkpoint resource.
   *
   * @return the PCM checkpoint resource
   */
  protected Resource getPcmCheckpoint() {
    return pcmCheckpoint;
  }

  /**
   * Gets the UML model resource.
   *
   * @return the UML model resource
   */
  protected Resource getUmlModel() {
    return umlModel;
  }

  /**
   * Gets the PCM model resource.
   *
   * @return the PCM model resource
   */
  protected Resource getPcmModel() {
    return pcmModel;
  }

  /**
   * Gets the PCM root repository.
   *
   * @return the PCM root repository
   */
  protected Repository getPcmRoot() {
    return pcmRoot;
  }

  /**
   * Gets the UML root package.
   *
   * @return the UML root package
   */
  protected UPackage getUmlRoot() {
    return umlRoot;
  }

  /**
   * Gets the resource set used in the test.
   *
   * @return the resource set
   */
  protected ResourceSet getResourceSet() {
    return resourceSet;
  }
}
