package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.github.gumtreediff.actions.RootsClassifier;
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;
import com.github.gumtreediff.tree.TreeUtils;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters.GumTree2JdtAstConverterImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class GumTreeChangeExtractor {
  private final static Logger logger = Logger.getLogger(GumTreeChangeExtractor.class);
  
  private String oldContent;
  
  private String newContent;
  
  public GumTreeChangeExtractor(final String oldContent, final String newContent) {
    this.oldContent = oldContent;
    this.newContent = newContent;
    GumTreeChangeExtractor.logger.setLevel(Level.ALL);
  }
  
  public ArrayList<String> extract() {
    try {
      final JdtTreeGenerator generator = new JdtTreeGenerator();
      final TreeContext srcTreeContext = generator.generateFromString(this.oldContent);
      final TreeContext dstTreeContext = generator.generateFromString(this.newContent);
      GumTreeChangeExtractor.logger.info(" --- SOURCE TREE --- ");
      ITree _root = srcTreeContext.getRoot();
      String _prettyString = _root.toPrettyString(srcTreeContext);
      GumTreeChangeExtractor.logger.info(_prettyString);
      GumTreeChangeExtractor.logger.info(" --- DESTINATION TREE --- ");
      ITree _root_1 = dstTreeContext.getRoot();
      String _prettyString_1 = _root_1.toPrettyString(dstTreeContext);
      GumTreeChangeExtractor.logger.info(_prettyString_1);
      final ArrayList<String> contentList = new ArrayList<String>();
      final GumTree2JdtAstConverterImpl converter = new GumTree2JdtAstConverterImpl();
      ITree _root_2 = srcTreeContext.getRoot();
      CompilationUnit _convertTree = converter.convertTree(_root_2);
      String _string = _convertTree.toString();
      contentList.add(_string);
      Matchers _instance = Matchers.getInstance();
      ITree _root_3 = srcTreeContext.getRoot();
      ITree _root_4 = dstTreeContext.getRoot();
      final Matcher m = _instance.getMatcher(_root_3, _root_4);
      m.match();
      final MappingStore mappings = m.getMappings();
      final RootsClassifier classifier = new RootsClassifier(srcTreeContext, dstTreeContext, m);
      ITree _root_5 = srcTreeContext.getRoot();
      final ITree workingTree = _root_5.deepCopy();
      Set<ITree> _srcDelTrees = classifier.getSrcDelTrees();
      for (final ITree delTree : _srcDelTrees) {
        {
          GumTreeChangeExtractor.logger.info("Found DEL");
          boolean _removeNodeFromWorkingTree = this.removeNodeFromWorkingTree(delTree, true, workingTree, mappings);
          if (_removeNodeFromWorkingTree) {
            CompilationUnit _convertTree_1 = converter.convertTree(workingTree);
            String _string_1 = _convertTree_1.toString();
            contentList.add(_string_1);
          }
        }
      }
      Set<ITree> _dstAddTrees = classifier.getDstAddTrees();
      for (final ITree addTree : _dstAddTrees) {
        {
          GumTreeChangeExtractor.logger.info("Found ADD");
          boolean _addNodeToWorkingTree = this.addNodeToWorkingTree(addTree, false, workingTree, mappings);
          if (_addNodeToWorkingTree) {
            CompilationUnit _convertTree_1 = converter.convertTree(workingTree);
            String _string_1 = _convertTree_1.toString();
            contentList.add(_string_1);
          }
        }
      }
      Set<ITree> _srcMvTrees = classifier.getSrcMvTrees();
      for (final ITree mvTree : _srcMvTrees) {
        {
          GumTreeChangeExtractor.logger.info("Found MV");
          int _id = mvTree.getId();
          final ITree movedNodeInWorkingTree = this.findNodeWithId(workingTree, _id);
          final boolean removed = this.removeNodeFromWorkingTree(movedNodeInWorkingTree, true, workingTree, mappings);
          final ITree movedNodeInDstTree = mappings.getDst(mvTree);
          final boolean added = this.addNodeToWorkingTree(movedNodeInDstTree, false, workingTree, mappings);
          boolean _and = false;
          if (!added) {
            _and = false;
          } else {
            _and = removed;
          }
          if (_and) {
            CompilationUnit _convertTree_1 = converter.convertTree(workingTree);
            String _string_1 = _convertTree_1.toString();
            contentList.add(_string_1);
          } else {
            if ((!((!added) && (!removed)))) {
              GumTreeChangeExtractor.logger.warn("Couldn\'t add or remove but the other.");
            }
          }
        }
      }
      Set<ITree> _srcUpdTrees = classifier.getSrcUpdTrees();
      for (final ITree updTree : _srcUpdTrees) {
        {
          GumTreeChangeExtractor.logger.info("Found UPD");
          int _id = updTree.getId();
          final ITree updatedNodeInWorkingTree = this.findNodeWithId(workingTree, _id);
          final boolean removed = this.removeNodeFromWorkingTree(updatedNodeInWorkingTree, true, workingTree, mappings);
          final ITree updatedNodeInDstTree = mappings.getDst(updTree);
          final boolean added = this.addNodeToWorkingTree(updatedNodeInDstTree, false, workingTree, mappings);
          boolean _and = false;
          if (!added) {
            _and = false;
          } else {
            _and = removed;
          }
          if (_and) {
            CompilationUnit _convertTree_1 = converter.convertTree(workingTree);
            String _string_1 = _convertTree_1.toString();
            contentList.add(_string_1);
          } else {
            if ((!((!added) && (!removed)))) {
              GumTreeChangeExtractor.logger.warn("Couldn\'t add or remove but the other.");
            }
          }
        }
      }
      return contentList;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean addNodeToWorkingTree(final ITree addTree, final boolean isSrc, final ITree workingTree, final MappingStore mappings) {
    final ITree addCopy = addTree.deepCopy();
    ITree parent = addTree.getParent();
    if ((!isSrc)) {
      ITree _src = mappings.getSrc(parent);
      parent = _src;
    }
    boolean _notEquals = (!Objects.equal(parent, null));
    if (_notEquals) {
      final int parentId = parent.getId();
      final ITree parentInWorkingTree = this.findNodeWithId(workingTree, parentId);
      final List<ITree> children = parentInWorkingTree.getChildren();
      int pos = addTree.positionInParent();
      GumTreeChangeExtractor.logger.warn("Did not assure that position in parent is set correct yet. TODO/FIXME");
      int _size = children.size();
      boolean _greaterThan = (pos > _size);
      if (_greaterThan) {
        int _size_1 = children.size();
        pos = _size_1;
      }
      children.add(pos, addCopy);
      parentInWorkingTree.setChildren(children);
      return true;
    } else {
      GumTreeChangeExtractor.logger.info("Did not find parent in src. Most likely was not the root of the change. Need to improve change root finding. Doing nothing.");
      return false;
    }
  }
  
  public boolean removeNodeFromWorkingTree(final ITree delTree, final boolean isSrc, final ITree workingTree, final MappingStore mappings) {
    ITree parent = delTree.getParent();
    if ((!isSrc)) {
      ITree _src = mappings.getSrc(parent);
      parent = _src;
    }
    boolean _notEquals = (!Objects.equal(parent, null));
    if (_notEquals) {
      final int parentId = parent.getId();
      final ITree parentInWorkingTree = this.findNodeWithId(workingTree, parentId);
      boolean _equals = Objects.equal(parentInWorkingTree, null);
      if (_equals) {
        return false;
      }
      final List<ITree> children = parentInWorkingTree.getChildren();
      ITree toDelete = ((ITree) null);
      for (final ITree child : children) {
        int _id = child.getId();
        int _id_1 = delTree.getId();
        boolean _equals_1 = (_id == _id_1);
        if (_equals_1) {
          toDelete = child;
        }
      }
      boolean _equals_2 = Objects.equal(toDelete, null);
      if (_equals_2) {
        throw new RuntimeException("Could not find child.");
      }
      children.remove(toDelete);
      parentInWorkingTree.setChildren(children);
      return true;
    } else {
      GumTreeChangeExtractor.logger.info("Did not find parent in src. Most likely was not the root of the change. Need to improve change root finding. Doing nothing.");
      return false;
    }
  }
  
  public ITree findNodeWithId(final ITree tree, final int id) {
    List<ITree> _breadthFirst = TreeUtils.breadthFirst(tree);
    for (final ITree node : _breadthFirst) {
      int _id = node.getId();
      boolean _equals = (_id == id);
      if (_equals) {
        return node;
      }
    }
    GumTreeChangeExtractor.logger.warn("Could not find parent. Probably already deleted from working tree due to earlier operation.");
    return null;
  }
}
