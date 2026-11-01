package tools.vitruv.framework.vsum.models;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Describes a model of a virtual model.
 *
 * @param uri the URI of the model, which identifies it within the virtual model
 * @param metamodels the metamodels of the elements of the model, in the order of their first
 *     element
 * @param rootCount the number of root elements of the model
 */
public record ModelDescriptor(URI uri, List<MetamodelReference> metamodels, int rootCount) {

  /** Validates the URI and makes the metamodels immutable. */
  public ModelDescriptor {
    checkNotNull(uri, "uri");
    metamodels = metamodels == null ? List.of() : List.copyOf(metamodels);
  }

  /**
   * Describes the given model resource.
   *
   * @param resource the model resource
   * @return the description
   */
  public static ModelDescriptor of(Resource resource) {
    return new ModelDescriptor(
        resource.getURI(),
        rootPackagesOf(resource).values().stream().map(MetamodelReference::of).toList(),
        resource.getContents().size());
  }

  /**
   * Collects the root packages of all elements of the given resource.
   *
   * @param resource the resource
   * @return the root packages by their namespace URI, in the order of their first element
   */
  static Map<String, EPackage> rootPackagesOf(Resource resource) {
    Map<String, EPackage> packages = new LinkedHashMap<>();
    for (TreeIterator<EObject> iterator = resource.getAllContents(); iterator.hasNext(); ) {
      EPackage rootPackage = MetamodelReference.rootPackageOf(iterator.next().eClass());
      if (rootPackage != null && rootPackage.getNsURI() != null) {
        packages.putIfAbsent(rootPackage.getNsURI(), rootPackage);
      }
    }
    return packages;
  }
}
