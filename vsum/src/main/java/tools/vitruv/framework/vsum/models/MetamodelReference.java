package tools.vitruv.framework.vsum.models;

import static com.google.common.base.Preconditions.checkArgument;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * Identifies a metamodel by its root package.
 *
 * @param name the name of the root package, as used in qualified type names (e.g. {@code model}
 *     for {@code model::Component})
 * @param nsUri the namespace URI of the root package, which identifies the metamodel
 */
public record MetamodelReference(String name, String nsUri) {

  /** Validates the namespace URI. */
  public MetamodelReference {
    checkArgument(nsUri != null && !nsUri.isBlank(), "namespace URI must not be blank");
  }

  /**
   * Creates the reference to the metamodel of the given root package.
   *
   * @param rootPackage the root package
   * @return the reference
   */
  public static MetamodelReference of(EPackage rootPackage) {
    return new MetamodelReference(rootPackage.getName(), rootPackage.getNsURI());
  }

  /**
   * Gets the root package of the metamodel that defines the given class.
   *
   * @param eClass the class
   * @return the outermost package containing the class, {@code null} if the class has no package
   */
  public static EPackage rootPackageOf(EClass eClass) {
    EPackage ePackage = eClass.getEPackage();
    while (ePackage != null && ePackage.getESuperPackage() != null) {
      ePackage = ePackage.getESuperPackage();
    }
    return ePackage;
  }
}
