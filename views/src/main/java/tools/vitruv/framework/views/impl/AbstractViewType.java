package tools.vitruv.framework.views.impl;

import static com.google.common.base.Preconditions.checkArgument;

import org.eclipse.emf.ecore.EPackage;
import tools.vitruv.framework.views.ViewSelector;

/**
 * An abstract base implementation for view types.
 *
 * @param <S> the type of view selector used by this view type
 * @param <Id> the type of identifiers used by this view type
 */
public abstract class AbstractViewType<S extends ViewSelector, Id>
    implements ViewCreatingViewType<S, Id> {
  private final String name;
  private final EPackage metamodel;

  /**
   * constructor for view types without metamodel.
   *
   * @param name the name of the view type
   */
  protected AbstractViewType(String name) {
    checkArgument(name != null, "view type name must not be null");
    this.name = name;
    this.metamodel = null;
  }

  /**
   * constructor for view types with metamodel.
   *
   * @param name the name of the view type
   * @param metamodel the metamodel of the view type
   */
  protected AbstractViewType(String name, EPackage metamodel) {
    checkArgument(name != null, "view type name must not be null");
    checkArgument(metamodel != null, "view type metamodel must not be null");
    this.name = name;
    this.metamodel = metamodel;
  }

  /**
   * Returns the name of this view type.
   *
   * @return the name of this view type
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the metamodel of this view type.
   *
   * @return the metamodel of this view type
   */
  public EPackage getMetamodel() {
    return this.metamodel;
  }
}
