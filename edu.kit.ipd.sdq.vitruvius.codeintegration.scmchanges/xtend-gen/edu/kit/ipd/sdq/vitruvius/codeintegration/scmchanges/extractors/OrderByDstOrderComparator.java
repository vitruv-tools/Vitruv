package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.ITree;
import com.google.common.base.Objects;
import java.util.Comparator;
import java.util.List;

/**
 * Used to sort children of a node by what the order looks like in the dst tree
 */
@SuppressWarnings("all")
public class OrderByDstOrderComparator implements Comparator<ITree> {
  private ITree dstParent;
  
  private MappingStore mappings;
  
  private MappingStore workingTreeMappings;
  
  public OrderByDstOrderComparator(final ITree dstParent, final MappingStore mappings, final MappingStore workingTreeMappings) {
    this.dstParent = dstParent;
    this.mappings = mappings;
    this.workingTreeMappings = workingTreeMappings;
  }
  
  @Override
  public int compare(final ITree o1, final ITree o2) {
    int _xblockexpression = (int) 0;
    {
      ITree dst1 = ((ITree) null);
      ITree dst2 = ((ITree) null);
      List<ITree> _children = this.dstParent.getChildren();
      boolean _contains = _children.contains(o1);
      if (_contains) {
        dst1 = o1;
      } else {
        ITree _dst = this.mappings.getDst(o1);
        dst1 = _dst;
        boolean _equals = Objects.equal(dst1, null);
        if (_equals) {
          ITree _dst_1 = this.workingTreeMappings.getDst(o1);
          dst1 = _dst_1;
        }
      }
      List<ITree> _children_1 = this.dstParent.getChildren();
      boolean _contains_1 = _children_1.contains(o2);
      if (_contains_1) {
        dst2 = o2;
      } else {
        ITree _dst_2 = this.mappings.getDst(o2);
        dst2 = _dst_2;
        boolean _equals_1 = Objects.equal(dst2, null);
        if (_equals_1) {
          ITree _dst_3 = this.workingTreeMappings.getDst(o2);
          dst2 = _dst_3;
        }
      }
      boolean _or = false;
      boolean _or_1 = false;
      boolean _equals_2 = Objects.equal(dst1, null);
      if (_equals_2) {
        _or_1 = true;
      } else {
        boolean _equals_3 = Objects.equal(dst2, null);
        _or_1 = _equals_3;
      }
      if (_or_1) {
        _or = true;
      } else {
        ITree _parent = dst1.getParent();
        ITree _parent_1 = dst2.getParent();
        boolean _notEquals = (!Objects.equal(_parent, _parent_1));
        _or = _notEquals;
      }
      if (_or) {
        return 1;
      }
      final int pos1 = dst1.positionInParent();
      final int pos2 = dst2.positionInParent();
      int _xifexpression = (int) 0;
      if ((pos1 < pos2)) {
        _xifexpression = (-1);
      } else {
        _xifexpression = 1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
