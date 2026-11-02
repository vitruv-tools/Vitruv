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
 * @param id the URI of the model relative to the folder of the virtual model, e.g. {@code
 *     models/a.family}; a stable id that does not depend on where the virtual model is stored
 * @param metamodels the metamodels of the elements of the model, in the order of their first
 *     element
 * @param rootCount the number of root elements of the model
 */
public record ModelDescriptor(
    URI uri, String id, List<MetamodelReference> metamodels, int rootCount) {

  /** Validates the URI and id and makes the metamodels immutable. */
  public ModelDescriptor {
    checkNotNull(uri, "uri");
    checkNotNull(id, "id");
    metamodels = metamodels == null ? List.of() : List.copyOf(metamodels);
  }

  /**
   * Describes the given model resource, using its full URI as id.
   *
   * @param resource the model resource
   * @return the description
   */
  public static ModelDescriptor of(Resource resource) {
    return of(resource, null);
  }

  /**
   * Describes the given model resource of a virtual model stored in the given folder.
   *
   * @param resource the model resource
   * @param folder the URI of the folder of the virtual model, ending with a slash; {@code null}
   *     to use the full URI of the model as id
   * @return the description
   */
  public static ModelDescriptor of(Resource resource, URI folder) {
    URI uri = resource.getURI();
    return new ModelDescriptor(
        uri,
        idOf(uri, folder),
        rootPackagesOf(resource).values().stream().map(MetamodelReference::of).toList(),
        resource.getContents().size());
  }

  /**
   * Gets the URI of a model relative to the folder of its virtual model.
   *
   * @param uri the URI of the model
   * @param folder the URI of the folder, ending with a slash, or {@code null}
   * @return the relative URI, or the full URI if it cannot be made relative to the folder
   */
  static String idOf(URI uri, URI folder) {
    if (folder == null || uri.isRelative()) {
      return uri.toString();
    }
    URI relative = uri.deresolve(folder, true, true, false);
    return relative.isRelative() ? relative.toString() : uri.toString();
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
