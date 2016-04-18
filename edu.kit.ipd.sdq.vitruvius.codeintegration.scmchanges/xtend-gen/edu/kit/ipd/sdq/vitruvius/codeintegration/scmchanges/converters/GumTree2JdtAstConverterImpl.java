package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters;

import com.github.gumtreediff.tree.ITree;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters.GumTree2JdtAstConverter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimplePropertyDescriptor;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

@SuppressWarnings("all")
public class GumTree2JdtAstConverterImpl implements GumTree2JdtAstConverter {
  private final static Logger logger = Logger.getLogger(GumTree2JdtAstConverterImpl.class.getName());
  
  @Override
  public CompilationUnit convertTree(final ITree gumTree) {
    final AST ast = AST.newAST(AST.JLS8);
    final CompilationUnit root = ast.newCompilationUnit();
    List<ITree> _children = gumTree.getChildren();
    for (final ITree child : _children) {
      this.createAstNode(child, ast, root);
    }
    return root;
  }
  
  private void createAstNode(final ITree tree, final AST ast, final ASTNode parent) {
    int _type = tree.getType();
    final ASTNode astNode = ast.createInstance(_type);
    Object _metadata = tree.getMetadata("properties");
    final Map<String, Object> properties = ((Map<String, Object>) _metadata);
    Set<Map.Entry<String, Object>> _entrySet = properties.entrySet();
    for (final Map.Entry<String, Object> entry : _entrySet) {
      String _key = entry.getKey();
      Object _value = entry.getValue();
      astNode.setProperty(_key, _value);
    }
    Object _metadata_1 = tree.getMetadata("simpleProperties");
    final Map<SimplePropertyDescriptor, Object> simpleProperties = ((Map<SimplePropertyDescriptor, Object>) _metadata_1);
    Set<Map.Entry<SimplePropertyDescriptor, Object>> _entrySet_1 = simpleProperties.entrySet();
    for (final Map.Entry<SimplePropertyDescriptor, Object> entry_1 : _entrySet_1) {
      SimplePropertyDescriptor _key_1 = entry_1.getKey();
      Object _value_1 = entry_1.getValue();
      astNode.setStructuralProperty(_key_1, _value_1);
    }
    final List parentProperties = parent.structuralPropertiesForType();
    final Object propertyId = tree.getMetadata("propertyId");
    Class<? extends ASTNode> _class = astNode.getClass();
    String _format = String.format("Looking for parent property of child with type %s with propertyId %s", _class, propertyId);
    GumTree2JdtAstConverterImpl.logger.info(_format);
    boolean found = false;
    for (final Object property : parentProperties) {
      {
        final StructuralPropertyDescriptor propertyDescr = ((StructuralPropertyDescriptor) property);
        String _id = propertyDescr.getId();
        boolean _equals = Objects.equal(_id, propertyId);
        if (_equals) {
          boolean _isChildProperty = propertyDescr.isChildProperty();
          if (_isChildProperty) {
            final ChildPropertyDescriptor childDescr = ((ChildPropertyDescriptor) propertyDescr);
            Class _childType = childDescr.getChildType();
            int _type_1 = tree.getType();
            Class _nodeClassForType = ASTNode.nodeClassForType(_type_1);
            boolean _isAssignableFrom = _childType.isAssignableFrom(_nodeClassForType);
            if (_isAssignableFrom) {
              if ((!found)) {
                parent.setStructuralProperty(propertyDescr, astNode);
                Class<? extends ASTNode> _class_1 = astNode.getClass();
                Class<? extends ASTNode> _class_2 = parent.getClass();
                String _format_1 = String.format("Added node of type %s to property %s of parent of type %s", _class_1, childDescr, _class_2);
                GumTree2JdtAstConverterImpl.logger.info(_format_1);
                found = true;
              } else {
                Class<? extends ASTNode> _class_3 = astNode.getClass();
                Class<? extends ASTNode> _class_4 = parent.getClass();
                String _format_2 = String.format(
                  "Would add node of type %s to property %s of parent of type %s, but was already assigned.", _class_3, childDescr, _class_4);
                GumTree2JdtAstConverterImpl.logger.error(_format_2);
              }
            }
          } else {
            boolean _isChildListProperty = propertyDescr.isChildListProperty();
            if (_isChildListProperty) {
              final ChildListPropertyDescriptor childListDescr = ((ChildListPropertyDescriptor) propertyDescr);
              Class _elementType = childListDescr.getElementType();
              int _type_2 = tree.getType();
              Class _nodeClassForType_1 = ASTNode.nodeClassForType(_type_2);
              boolean _isAssignableFrom_1 = _elementType.isAssignableFrom(_nodeClassForType_1);
              if (_isAssignableFrom_1) {
                if ((!found)) {
                  Object _structuralProperty = parent.getStructuralProperty(propertyDescr);
                  final List<ASTNode> childList = ((List<ASTNode>) _structuralProperty);
                  childList.add(astNode);
                  Class<? extends ASTNode> _class_5 = astNode.getClass();
                  Class<? extends ASTNode> _class_6 = parent.getClass();
                  String _format_3 = String.format("Added node of type %s to list-property %s of parent of type %s", _class_5, childListDescr, _class_6);
                  GumTree2JdtAstConverterImpl.logger.info(_format_3);
                  found = true;
                } else {
                  Class<? extends ASTNode> _class_7 = astNode.getClass();
                  Class<? extends ASTNode> _class_8 = parent.getClass();
                  String _format_4 = String.format(
                    "Would add node of type %s to list-property %s of parent of type %s, but was already assigned.", _class_7, childListDescr, _class_8);
                  GumTree2JdtAstConverterImpl.logger.error(_format_4);
                }
              }
            } else {
              boolean _isSimpleProperty = propertyDescr.isSimpleProperty();
              if (_isSimpleProperty) {
                GumTree2JdtAstConverterImpl.logger.warn("Why did the gumtree have a simple property?!?");
              } else {
                throw new RuntimeException("A property of this kind, was not expected.");
              }
            }
          }
        }
      }
    }
    if ((!found)) {
      Class<? extends ASTNode> _class_1 = astNode.getClass();
      Class<? extends ASTNode> _class_2 = parent.getClass();
      String _format_1 = String.format("Did not find property for node of type %s in parent of type %s", _class_1, _class_2);
      GumTree2JdtAstConverterImpl.logger.warn(_format_1);
    }
    List<ITree> _children = tree.getChildren();
    for (final ITree child : _children) {
      this.createAstNode(child, ast, astNode);
    }
  }
  
  /**
   * Reverse of com.github.gumtreediff.gen.jdt.JdtVisitor#getLabel
   */
  private void injectGumtreeLabel(final ASTNode node, final String label) {
  }
}
