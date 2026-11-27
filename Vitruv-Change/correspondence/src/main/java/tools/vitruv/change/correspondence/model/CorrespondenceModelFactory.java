package tools.vitruv.change.correspondence.model;

import org.eclipse.emf.common.util.URI;

/** Factory for creating {@link PersistableCorrespondenceModel}s. */
public final class CorrespondenceModelFactory {
  private CorrespondenceModelFactory() {}

  /**
   * Creates a new {@link PersistableCorrespondenceModel} with the given resource URI.
   *
   * @param resourceUri the URI of the resource to which the correspondence model is persisted
   * @return the created {@link PersistableCorrespondenceModel}
   */
  public static PersistableCorrespondenceModel createPersistableCorrespondenceModel(
      URI resourceUri) {
    return new PersistableCorrespondenceModelImpl(resourceUri);
  }
}
