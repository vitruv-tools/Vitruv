package tools.vitruv.framework.vsum.models;

import java.util.ArrayList;
import java.util.Collection;
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

/**
 * Read access to the models of a virtual model, see {@link
 * tools.vitruv.framework.vsum.VirtualModel#readModels}.
 *
 * <p>The access is only valid during the call it is passed to, in which the virtual model does not
 * change. The models must not be modified through it; models are changed through views only.
 * Copy what is needed after the call.
 */
public interface ModelReadAccess {

  /**
   * Gets the model resources of the virtual model.
   *
   * @return the model resources, unmodifiable
   * @throws IllegalStateException if the access is used after the call it was passed to
   */
  Collection<Resource> getModelResources();

  /**
   * Gets the UUID of an element of a model, which identifies it within the virtual model.
   *
   * @param element an element of a model of the virtual model
   * @return the UUID, empty if the element has none
   * @throws IllegalStateException if the access is used after the call it was passed to
   */
  Optional<Uuid> getUuid(EObject element);

  /**
   * Gets the model resource with the given URI.
   *
   * @param uri the URI of the model
   * @return the model resource, empty if there is none
   */
  default Optional<Resource> getModelResource(URI uri) {
    return getModelResources().stream().filter(r -> r.getURI().equals(uri)).findFirst();
  }

  /**
   * Describes the models of the virtual model.
   *
   * @return the descriptions, sorted by URI
   */
  default List<ModelDescriptor> getModels() {
    return getModelResources().stream()
        .map(ModelDescriptor::of)
        .sorted(Comparator.comparing(model -> model.uri().toString()))
        .toList();
  }

  /**
   * Gets the root packages of the metamodels whose elements are contained in the models. The
   * packages are shared metamodel definitions and must not be modified. Implementations may add
   * further metamodels of the virtual model, such as those of its change propagation
   * specifications.
   *
   * @return the root packages, in the order of the models sorted by URI
   */
  default List<EPackage> getMetamodels() {
    Map<String, EPackage> packages = new LinkedHashMap<>();
    List<Resource> resources = new ArrayList<>(getModelResources());
    resources.sort(Comparator.comparing(resource -> resource.getURI().toString()));
    for (Resource resource : resources) {
      ModelDescriptor.rootPackagesOf(resource).forEach(packages::putIfAbsent);
    }
    return List.copyOf(packages.values());
  }
}
