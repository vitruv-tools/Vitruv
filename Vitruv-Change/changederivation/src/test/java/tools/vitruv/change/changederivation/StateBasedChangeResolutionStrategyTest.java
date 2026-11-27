package tools.vitruv.change.changederivation;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pcm_mockup.Repository;
import tools.vitruv.change.atomic.hid.AtomicEChangeHierarchicalIdResolver;
import tools.vitruv.change.composite.description.impl.VitruviusChangeHierarchicalIdResolver;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.metamodels.PcmMockupCreators;

@ExtendWith(TestProjectManager.class)
class StateBasedChangeResolutionStrategyTest {
  private StateBasedChangeResolutionStrategy resolutionStrategy 
      = new DefaultStateBasedChangeResolutionStrategy();
  private VitruviusChangeHierarchicalIdResolver resolver;

  private ResourceSet resourceSetUnmodified;
  private ResourceSet resourceSetModified;

  private PcmMockupCreators elementCreator = PcmMockupCreators.pcm;
  private Resource stateBefore;
  private String uriStateBefore = "repository.pcm";
  private Resource stateAfter;
  private String uriStateAfter = "repository2.pcm";

  /**
   * Creates two mock PCM repositories and persists them under testProjectPath.
   * Also sets up resolver to apply changes of resolutionStrategy.
   *
   * @param testProjectPath - Path
   */
  @BeforeEach
  void createStatesToCompare(@TestProject Path testProjectPath) throws IOException {
    // Set up resource set to modify.
    resourceSetModified = withGlobalFactories(new ResourceSetImpl());
    resolver = new VitruviusChangeHierarchicalIdResolver(
        new AtomicEChangeHierarchicalIdResolver(resourceSetModified)
    );

    // Create the first repository.
    Repository repository = elementCreator.Repository();
    repository.setId("r1");
    repository.setName("Repository 1");

    var interfaceModel = elementCreator.Interface();
    interfaceModel.setId("i1");
    interfaceModel.setName("Model");
  
    var interfaceModelCreator = elementCreator.Interface();
    interfaceModelCreator.setId("i1c");
    interfaceModelCreator.setName("ModelCreator");
  
    var methodCreateModel = elementCreator.Method();
    methodCreateModel.setId("m1");
    methodCreateModel.setName("createModel");
  
    repository.getInterfaces().add(interfaceModel);
    repository.getInterfaces().add(interfaceModelCreator);
    interfaceModelCreator.getMethods().add(methodCreateModel);
    methodCreateModel.setReturnType(interfaceModel);
  
    resourceSetUnmodified = withGlobalFactories(new ResourceSetImpl());
    URI fileURI = URI.createFileURI(testProjectPath.resolve(uriStateBefore).toString());
    stateBefore = resourceSetUnmodified.createResource(fileURI);
    stateBefore.getContents().add(repository);
    stateBefore.save(null);

    // Now copy this repository and modify it.
    Repository repository2 = (Repository) new Copier().copy(repository);

    var interfaceModelCreatorCopy = repository2.getInterfaces().get(1);
    interfaceModelCreatorCopy.setName("InstanceCreator");
    var methodCreateModelCopy = interfaceModelCreatorCopy.getMethods().get(0);
    methodCreateModelCopy.setName("createInstance");

    var component1 = elementCreator.Component();
    component1.setId("c1");
    component1.setId("InstanceCreator");
    component1.setProvidedInterface(interfaceModelCreatorCopy);
    repository2.getComponents().add(component1);

    URI fileURI2 = URI.createFileURI(testProjectPath.resolve(uriStateAfter).toString());
    stateAfter = resourceSetUnmodified.createResource(fileURI2);
    stateAfter.getContents().add(repository2);
    stateAfter.save(null);
  }

  /**
   * When we derive the change sequence between stateAfter and stateBefore,
   * and apply it on a ResourceSet that contains stateBefore,
   * then resourceSetModified has one resource with a model
   * that equals the model of stateAfter.
   */
  @Test
  void testSequenceFromState1To2ProducesState2() {
    testCreationSequenceCreatesResource();
    var modificationChange = resolutionStrategy.getChangeSequenceBetween(stateAfter, stateBefore);
    resolver.resolveAndApply(modificationChange);
    var resultingModel = resourceSetModified.getResources().get(1).getContents().get(0);
    assertEqualModels(resultingModel, stateAfter.getContents().get(0));
  }

  private void assertEqualModels(EObject rootElement1, EObject rootElement2) {
    var comparisonResult = EMFCompare.builder().build()
        .compare(
            new DefaultComparisonScope(rootElement1, rootElement2, null));
    assertTrue(comparisonResult.getDifferences().isEmpty());
  }

  /**
   * When we create the change sequence for stateBefore
   * and apply it on resourceSetModified,
   * then resourceSetModified has one resource with one model, 
   * and this model is equal to the model of stateBefore.
   */
  @Test
  void testCreationSequenceCreatesResource() {
    var createChange = resolutionStrategy.getChangeSequenceForCreated(stateBefore);
    resolver.resolveAndApply(createChange);
    var resultingModel = resourceSetModified.getResources().get(0).getContents().get(0);
    assertEqualModels(resultingModel, stateBefore.getContents().get(0));
  }

  /**
   * When we create the delete sequence for stateBefore
   * and apply it on resourceSetModified after executing 
   * {@link testCreationSequenceCreatesResource},
   * then resourceSetModified has one resource without a model.
   */
  @Test
  void testDeletionSequenceDeletesResource() {
    testCreationSequenceCreatesResource();
    var deleteChange = resolutionStrategy.getChangeSequenceForDeleted(stateBefore);
    resolver.resolveAndApply(deleteChange);
    var resourceModified = resourceSetModified.getResources().get(0);
    assertTrue(resourceModified.getContents().isEmpty());
  }
}
