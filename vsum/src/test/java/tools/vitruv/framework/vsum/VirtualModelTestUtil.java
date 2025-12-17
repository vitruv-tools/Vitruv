package tools.vitruv.framework.vsum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.Root;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.propagation.impl.AbstractChangePropagationSpecification;
import tools.vitruv.change.utils.ResourceAccess;

/** Utility methods for the VSUM and view test cases. */
public final class VirtualModelTestUtil {

  private VirtualModelTestUtil() {
    // Utility class, no instantiation
  }

  /**
   * Create a recorder, start recording a resource set, apply changes, stop and return the recorded
   * changes.
   */
  public static VitruviusChange<Uuid> recordChanges(
      ResourceSet resourceSet, UuidResolver uuidResolver, Runnable changesToPerform) {
    tools.vitruv.change.composite.description.VitruviusChange<tools.vitruv.change.atomic.uuid.Uuid>
        resolvedChange;
    try (ChangeRecorder recorder = new ChangeRecorder(resourceSet)) {
      recorder.addToRecording(resourceSet);
      recorder.beginRecording();
      changesToPerform.run();
      var result = recorder.endRecording();
      var changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);
      resolvedChange = changeResolver.assignIds(result);
    }
    return resolvedChange;
  }

  /** Creates an empty virtual model without a change propagation specification. */
  public static VirtualModel createAndLoadTestVirtualModel(Path folder) throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(folder)
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .buildAndInitialize();
  }

  /** Creates an empty virtual model with a {@link RedundancyChangePropagationSpecification}. */
  public static VirtualModel createAndLoadTestVirtualModelWithConsistencyPreservation(Path folder)
      throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(folder)
        .withChangePropagationSpecification(
            new RedundancyChangePropagationSpecification(
                MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI),
                MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI)))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .buildAndInitialize();
  }

  /**
   * Creates a model resource URI for a given project folder. Use the suffix to distinguish multiple
   * URIs.
   */
  public static URI createTestModelResourceUri(Path projectFolder, String suffix) {
    return URI.createFileURI(
        projectFolder.resolve("root" + suffix + ".allElementTypes").toString());
  }

  /** Specification for redundancy change propagation that creates copies of elements. */
  public static class RedundancyChangePropagationSpecification
      extends AbstractChangePropagationSpecification {

    /**
     * Gets the target resource URI for a resource copied from the source URI.
     *
     * @param sourceUri the source URI
     * @return the target URI for a resource copied from the source URI
     */
    public static URI getTargetResourceUri(URI sourceUri) {
      String sourceUriWithoutFileExtension = sourceUri.trimFileExtension().toFileString();
      String copySuffix = "Copy";

      if (sourceUriWithoutFileExtension.endsWith(copySuffix)) {
        String sourceUriWithoutSuffix =
            sourceUriWithoutFileExtension.substring(
                0, sourceUriWithoutFileExtension.length() - copySuffix.length());
        return URI.createFileURI(sourceUriWithoutSuffix + "." + sourceUri.fileExtension());
      } else {
        return URI.createFileURI(
            sourceUriWithoutFileExtension + copySuffix + "." + sourceUri.fileExtension());
      }
    }

    /**
     * Creates a new redundancy change propagation specification.
     *
     * @param sourceMetamodelDescriptor The source metamodel descriptor.
     * @param targetMetamodelDescriptor The target metamodel descriptor.
     */
    public RedundancyChangePropagationSpecification(
        MetamodelDescriptor sourceMetamodelDescriptor,
        MetamodelDescriptor targetMetamodelDescriptor) {
      super(sourceMetamodelDescriptor, targetMetamodelDescriptor);
    }

    @Override
    public boolean doesHandleChange(
        EChange<EObject> change,
        EditableCorrespondenceModelView<Correspondence> correspondenceModel) {
      if (change instanceof InsertRootEObject) {
        InsertRootEObject<?> insertChange = (InsertRootEObject<?>) change;
        return insertChange.getNewValue() instanceof Root;
      }
      return false;
    }

    @Override
    public void propagateChange(
        EChange<EObject> change,
        EditableCorrespondenceModelView<Correspondence> correspondenceModel,
        ResourceAccess resourceAccess) {
      if (!doesHandleChange(change, correspondenceModel)) {
        return;
      }

      InsertRootEObject<EObject> typedChange = (InsertRootEObject<EObject>) change;
      Root insertedRoot = (Root) typedChange.getNewValue();

      // If there is a corresponding element, reuse it, otherwise create one
      List<Root> correspondingRoots =
          correspondenceModel.getCorrespondingEObjects(insertedRoot).stream()
              .filter(Root.class::isInstance)
              .map(Root.class::cast)
              .collect(Collectors.toList());

      Root correspondingRoot;
      if (correspondingRoots.size() == 1) {
        correspondingRoot = correspondingRoots.get(0);
      } else {
        Root newRoot = aet.Root();
        newRoot.setId(insertedRoot.getId());
        correspondenceModel.addCorrespondenceBetween(insertedRoot, newRoot, null);
        correspondingRoot = newRoot;
      }

      if (insertedRoot.eContainer() != null) {
        List<Root> correspondingObjects =
            correspondenceModel.getCorrespondingEObjects(insertedRoot.eContainer(), null).stream()
                .filter(Root.class::isInstance)
                .map(Root.class::cast)
                .collect(Collectors.toList());
        assertEquals(1, correspondingObjects.size());
        correspondingObjects.get(0).setRecursiveRoot(correspondingRoot);
      }

      URI resourceURI = typedChange.getResource().getURI();
      resourceAccess.persistAsRoot(correspondingRoot, getTargetResourceUri(resourceURI));
    }
  }
}
