package tools.vitruv.framework.vsum.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.models.ModelReadAccess;

/** Read access to the models of a {@link VirtualModelImpl}, valid until it is closed. */
final class VsumModelReadAccess implements ModelReadAccess, AutoCloseable {
  private final Collection<Resource> modelResources;
  private final UuidResolver uuidResolver;
  private boolean closed;

  VsumModelReadAccess(Collection<Resource> modelResources, UuidResolver uuidResolver) {
    this.modelResources = Collections.unmodifiableCollection(modelResources);
    this.uuidResolver = uuidResolver;
  }

  @Override
  public Collection<Resource> getModelResources() {
    checkOpen();
    return modelResources;
  }

  @Override
  public Optional<Uuid> getUuid(EObject element) {
    checkOpen();
    return element != null && uuidResolver.hasUuid(element)
        ? Optional.of(uuidResolver.getUuid(element))
        : Optional.empty();
  }

  @Override
  public void close() {
    closed = true;
  }

  private void checkOpen() {
    if (closed) {
      throw new IllegalStateException("The models can only be read during readModels");
    }
  }
}
