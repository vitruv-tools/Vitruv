package tools.vitruv.framework.vsum.persistence;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.messages.InfoMessages;

/**
 * Stores the complete state after every propagation: each model as its own resource, the list of
 * model URIs ({@code models.models}), the UUIDs ({@code uuid.uuid}) and the correspondences. This
 * is the default persistence of a virtual model.
 */
public class StateBasedModelPersistence implements ModelPersistence {
  private static final Logger LOGGER = LogManager.getLogger(StateBasedModelPersistence.class);

  @Override
  public void load(ModelPersistenceContext context) {
    try {
      readModelsFile(context);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    context.loadCorrespondences();
  }

  private static void readModelsFile(ModelPersistenceContext context) throws IOException {
    VsumFileSystemLayout fileSystemLayout = context.getFileSystemLayout();
    List<URI> modelUris;
    try {
      modelUris =
          Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath()).stream()
              .map(URI::createURI)
              .toList();

    } catch (NoSuchFileException e) {
      // There are no existing models, so don't do anything
      return;
    }
    modelUris.forEach(uri -> loadOrCreateResource(context.getModelsResourceSet(), uri));
    context.getUuidResolver().loadFromUri(fileSystemLayout.getUuidsURI());
    modelUris.forEach(context::registerModel);
  }

  @Override
  public void save(ModelPersistenceContext context) {
    VsumFileSystemLayout fileSystemLayout = context.getFileSystemLayout();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(InfoMessages.DEBUG_SAVING_ALL_MODELS, fileSystemLayout);
    }
    context.saveOrDeleteModels();
    context.getCorrespondenceModel().save();
    try {
      writeModelsFile(context);
      context.getUuidResolver().storeAtUri(fileSystemLayout.getUuidsURI());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void writeModelsFile(ModelPersistenceContext context) throws IOException {
    Files.write(
        context.getFileSystemLayout().getModelsNamesFilesPath(),
        context.getModelsResourceSet().getResources().stream()
            .map(Resource::getURI)
            .map(URI::toString)
            .toList());
  }
}
