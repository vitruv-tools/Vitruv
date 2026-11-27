package tools.vitruv.branching.test.viewtype.impl;

import tools.vitruv.branching.test.viewtype.ChangeTransformingViewType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.views.impl.ModifiableView;

public class ChangeTransformingViewTypeImpl extends ChangeTransformingViewType {
  private List<Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>>> filters;

  public ChangeTransformingViewTypeImpl(String name) {
    super(name);
    this.filters = new ArrayList<>();
  }

  @Override
  public void commitViewChanges(ModifiableView view, VitruviusChange<HierarchicalId> viewChange) {
    var modifiedChange = viewChange.copy().getEChanges();
    for (Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>> filter : filters) {
      modifiedChange = filter.apply(modifiedChange);
    }
    super.commitViewChanges(view, VitruviusChangeFactory.getInstance().createTransactionalChange(modifiedChange));
  }

  public boolean registerFilter(Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>> filter) {
    return this.filters.add(filter);
  }

  @Override
  public boolean unregisterFilter(Function<List<EChange<HierarchicalId>>, List<EChange<HierarchicalId>>> filter) {
    return this.filters.remove(filter);
  }

}
