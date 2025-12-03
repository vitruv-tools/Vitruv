package tools.vitruv.framework.views;

import org.eclipse.emf.ecore.EPackage;

/**
 * A Vitruv view type on the virtual model, providing a view selector and allows creating views.
 *
 * @param <S> the type of view selector this view type uses
 */
public interface ViewType<S extends ViewSelector> {

  /** Returns the EPackage of the underlying metamodel of the view type or null, if no explicit metamodel exists. */
  EPackage getMetamodel();

  /** Returns the name of the view type. */
  String getName();

  /**
   * Returns the view selector of the view type, which allows configuring views based on the given
   * {@link ChangeableViewSource}.
   *
   * @param viewSource the {@link ChangeableViewSource} from which views shall be derived.
   * @return a {@link ViewSelector} for this view type
   */
  S createSelector(ChangeableViewSource viewSource);
}
