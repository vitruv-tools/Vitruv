package tools.vitruv.framework.vsum.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.models.ModelDescriptor;
import tools.vitruv.framework.vsum.models.ModelReadAccess;

/** Read access to the models of a {@link VirtualModelImpl}, valid until it is closed. */
final class VsumModelReadAccess implements ModelReadAccess, AutoCloseable {
  private final Collection<Resource> modelResources;
  private final UuidResolver uuidResolver;
  private final URI folder;
  private final List<EPackage> specificationMetamodels;
  private boolean closed;

  /**
   * Creates the access.
   *
   * @param modelResources the model resources of the virtual model
   * @param uuidResolver the UUID resolver of the virtual model
   * @param folder the URI of the folder of the virtual model, ending with a slash
   * @param specificationMetamodels the root packages of the metamodels of the change propagation
   *     specifications
   */
  VsumModelReadAccess(
      Collection<Resource> modelResources,
      UuidResolver uuidResolver,
      URI folder,
      List<EPackage> specificationMetamodels) {
    this.modelResources = Collections.unmodifiableCollection(modelResources);
    this.uuidResolver = uuidResolver;
    this.folder = folder;
    this.specificationMetamodels = List.copyOf(specificationMetamodels);
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
  public List<ModelDescriptor> getModels() {
    return getModelResources().stream()
        .map(resource -> ModelDescriptor.of(resource, folder))
        .sorted(Comparator.comparing(model -> model.uri().toString()))
        .toList();
  }

  @Override
  public List<EPackage> getMetamodels() {
    Map<String, EPackage> packages = new LinkedHashMap<>();
    ModelReadAccess.super.getMetamodels()
        .forEach(metamodel -> packages.put(metamodel.getNsURI(), metamodel));
    specificationMetamodels.forEach(
        metamodel -> packages.putIfAbsent(metamodel.getNsURI(), metamodel));
    return List.copyOf(packages.values());
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
