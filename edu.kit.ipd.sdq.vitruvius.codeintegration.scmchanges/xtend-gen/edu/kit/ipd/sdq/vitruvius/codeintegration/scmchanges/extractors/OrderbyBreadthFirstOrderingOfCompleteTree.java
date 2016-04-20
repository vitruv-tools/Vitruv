package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeUtils;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("all")
public class OrderbyBreadthFirstOrderingOfCompleteTree implements Comparator<ITree> {
  private List<ITree> completeTreeOrderedByBreadthFirst;
  
  private int factor;
  
  public OrderbyBreadthFirstOrderingOfCompleteTree(final ITree completeTree, final boolean invert) {
    List<ITree> _breadthFirst = TreeUtils.breadthFirst(completeTree);
    this.completeTreeOrderedByBreadthFirst = _breadthFirst;
    int _xifexpression = (int) 0;
    if ((!invert)) {
      _xifexpression = 1;
    } else {
      _xifexpression = (-1);
    }
    this.factor = _xifexpression;
  }
  
  public OrderbyBreadthFirstOrderingOfCompleteTree(final ITree completeTree) {
    List<ITree> _breadthFirst = TreeUtils.breadthFirst(completeTree);
    this.completeTreeOrderedByBreadthFirst = _breadthFirst;
    this.factor = 1;
  }
  
  @Override
  public int compare(final ITree o1, final ITree o2) {
    int _indexOf = this.completeTreeOrderedByBreadthFirst.indexOf(o1);
    int _indexOf_1 = this.completeTreeOrderedByBreadthFirst.indexOf(o2);
    boolean _lessThan = (_indexOf < _indexOf_1);
    if (_lessThan) {
      return ((-1) * this.factor);
    } else {
      return (1 * this.factor);
    }
  }
}
