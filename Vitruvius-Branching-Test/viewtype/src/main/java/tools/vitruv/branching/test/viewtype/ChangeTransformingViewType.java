package tools.vitruv.branching.test.viewtype;

import tools.vitruv.framework.views.impl.ChangeDerivingView;
import tools.vitruv.branching.test.viewtype.impl.ChangeTransformingViewTypeImpl;

import java.util.List;
import java.util.function.Function;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.impl.IdentityMappingViewType;

/**
 * Extends the {@link IdentityMappingViewType} by
 * additionally providing the functionality to add filters which filter lists of
 * {@link EChange} that are used to commit changes to a view. The filters may,
 * e.g., chose to omit certain kinds of changes.
 */
public abstract class ChangeTransformingViewType extends IdentityMappingViewType {

  /**
   * 
   * @param name the name of the viewtype
   */
  public ChangeTransformingViewType(String name) {
    super(name);
  }

  /**
   * Registers the given filter which is applied to changes on this View.
   * 
   * @param filter the filter to register to this {@link ChangeTransformingView}
   * @return whether the register operation was successful.
   */
  public abstract boolean registerFilter(Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>> filter);

  /**
   * Unregisters the given filter which will no longer be applied to changes on
   * the view.
   * 
   * @param filter the filter to unregister from this
   *               {@link ChangeTransformingView}
   * @return whether the unregister operation was successful.
   */
  public abstract boolean unregisterFilter(Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>> filter);

  /**
   * Builds and returns a new {@link ChangeTransformingView} without any filters
   * added. The created View is a {@link ChangeDerivingView}.
   * 
   * @return a new instance of a {@link ChangeTransformingView}
   */
  public static ChangeTransformingViewType create(String name) {
    return new ChangeTransformingViewTypeImpl(name);
  }
}
