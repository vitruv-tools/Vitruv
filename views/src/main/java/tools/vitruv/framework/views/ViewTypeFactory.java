package tools.vitruv.framework.views;

import org.eclipse.emf.ecore.EPackage;
import tools.vitruv.framework.views.impl.IdentityMappingViewType;

/** Factory for creating view types. */
public final class ViewTypeFactory {

  private ViewTypeFactory() {
    // Utility class, no instantiation
  }

  /**
   * Creates a view type that generates a view via a one-to-one (identity) mapping of selected
   * resource root elements in a view source to a {@link View}.
   *
   * @param name the name of the view type
   * @return a new identity mapping view type
   */
  public static ViewType<? extends ViewSelector> createIdentityMappingViewType(String name) {
    return new IdentityMappingViewType(name);
  }

  /**
   * Creates a view type that generates a view via a one-to-one (identity) mapping of selected
   * resource root elements in a view source to a {@link View}.
   *
   * @param name the name of the view type
   * @param metamodel the metamodel EPackage for the view type
   * @return a new identity mapping view type with the specified metamodel
   */
  public static ViewType<? extends ViewSelector> createIdentityMappingViewType(
      String name, EPackage metamodel) {
    return new IdentityMappingViewType(name, metamodel);
  }
}
