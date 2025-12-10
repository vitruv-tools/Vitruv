package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/** Represents a model instance in the VSUM framework. */
public class ModelInstance {
  private static final Logger LOGGER = LogManager.getLogger(ModelInstance.class);
  private final Resource resource;

  /**
   * Creates a new model instance for the given resource.
   *
   * @param resource the resource of the model instance
   */
  public ModelInstance(Resource resource) {
    checkArgument(resource != null, "cannot create a model instance for a null resource");
    this.resource = resource;
    LOGGER.debug("Create model instance for resource with URI: {}", this.resource.getURI());
  }

  /**
   * Gets the URI of the model instance.
   *
   * @return the URI of the model instance
   */
  public URI getURI() {
    return resource.getURI();
  }

  /**
   * Gets the resource of the model instance.
   *
   * @return the resource of the model instance
   */
  public Resource getResource() {
    return resource;
  }

  /**
   * Adds a root EObject to the model instance.
   *
   * @param root the root EObject to add
   */
  public void addRoot(EObject root) {
    resource.getContents().add(root);
    resource.setModified(true);
    LOGGER.debug("Add root to resource: {}", resource);
  }

  /** Marks the model instance as modified. */
  public void markModified() {
    resource.setModified(true);
  }

  /**
   * Checks if the model instance is empty.
   *
   * @return true if the model instance is empty, false otherwise
   */
  public boolean isEmpty() {
    return resource.getContents().isEmpty();
  }

  /** Saves the model instance. */
  public void save() {
    if (!resource.isModified()) {
      return;
    }
    LOGGER.debug("Save resource: {}", resource);
    try {
      resource.save(null);
      resource.setModified(false);
    } catch (IOException e) {
      LOGGER.error("Model could not be saved: {}", getURI(), e);
      throw new IllegalStateException("Could not save URI " + getURI(), e);
    }
  }

  /** Deletes the model instance. */
  public void delete() {
    LOGGER.debug("Delete resource: {}", resource);
    try {
      resource.delete(null);
    } catch (IOException e) {
      LOGGER.error("Deletion of resource {} did not work.", resource, e);
      throw new IllegalStateException("Could not delete URI " + getURI(), e);
    }
  }
}
