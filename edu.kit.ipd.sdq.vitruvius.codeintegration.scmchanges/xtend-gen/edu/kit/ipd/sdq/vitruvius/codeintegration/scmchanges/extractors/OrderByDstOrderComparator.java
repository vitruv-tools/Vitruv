package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

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
  
  public OrderByDstOrderComparator(final ITree dstParent) {
    this.dstParent = dstParent;
  }
  
  @Override
  public int compare(final ITree o1, final ITree o2) {
    int _xblockexpression = (int) 0;
    {
      ITree dst1 = ((ITree) null);
      ITree dst2 = ((ITree) null);
      List<ITree> _children = this.dstParent.getChildren();
      for (final ITree dstChild : _children) {
        int _id = dstChild.getId();
        int _id_1 = o1.getId();
        boolean _equals = (_id == _id_1);
        if (_equals) {
          dst1 = dstChild;
        } else {
          int _id_2 = dstChild.getId();
          int _id_3 = o2.getId();
          boolean _equals_1 = (_id_2 == _id_3);
          if (_equals_1) {
            dst2 = dstChild;
          }
        }
      }
      boolean _or = false;
      boolean _equals_2 = Objects.equal(dst1, null);
      if (_equals_2) {
        _or = true;
      } else {
        boolean _equals_3 = Objects.equal(dst2, null);
        _or = _equals_3;
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
