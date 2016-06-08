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
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimplePropertyDescriptor;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class GumTree2JdtAstConverterImpl implements GumTree2JdtAstConverter {
  private final static Logger logger = Logger.getLogger(GumTree2JdtAstConverterImpl.class.getName());
  
  private String lastConvertedAsText = null;
  
  @Override
  public CompilationUnit convertTree(final ITree gumTree) {
    try {
      final ASTParser parser = ASTParser.newParser(AST.JLS8);
      final Document document = new Document("");
      String _get = document.get();
      char[] _charArray = _get.toCharArray();
      parser.setSource(_charArray);
      ASTNode _createAST = parser.createAST(null);
      final CompilationUnit unit = ((CompilationUnit) _createAST);
      unit.recordModifications();
      List<ITree> _children = gumTree.getChildren();
      for (final ITree child : _children) {
        AST _aST = unit.getAST();
        this.createAstNode(child, _aST, unit);
      }
      final TextEdit edits = unit.rewrite(document, null);
      edits.apply(document);
      String _get_1 = document.get();
      this.lastConvertedAsText = _get_1;
      return unit;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public String getLastConvertedAsText() {
    return this.lastConvertedAsText;
  }
  
  private void createAstNode(final ITree tree, final AST ast, final ASTNode parent) {
    int _type = tree.getType();
    final ASTNode astNode = ast.createInstance(_type);
    final List newNodeProperties = astNode.structuralPropertiesForType();
    for (final Object property : newNodeProperties) {
      {
        final StructuralPropertyDescriptor propertyDescr = ((StructuralPropertyDescriptor) property);
        boolean _isChildListProperty = propertyDescr.isChildListProperty();
        if (_isChildListProperty) {
          Object _structuralProperty = astNode.getStructuralProperty(propertyDescr);
          final List<ASTNode> list = ((List<ASTNode>) _structuralProperty);
          list.clear();
        }
      }
    }
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
    boolean found = false;
    for (final Object property_1 : parentProperties) {
      {
        final StructuralPropertyDescriptor propertyDescr = ((StructuralPropertyDescriptor) property_1);
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
                found = true;
              } else {
                Class<? extends ASTNode> _class = astNode.getClass();
                Class<? extends ASTNode> _class_1 = parent.getClass();
                String _format = String.format(
                  "Would add node of type %s to property %s of parent of type %s, but was already assigned.", _class, childDescr, _class_1);
                GumTree2JdtAstConverterImpl.logger.error(_format);
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
                  found = true;
                } else {
                  Class<? extends ASTNode> _class_2 = astNode.getClass();
                  Class<? extends ASTNode> _class_3 = parent.getClass();
                  String _format_1 = String.format(
                    "Would add node of type %s to list-property %s of parent of type %s, but was already assigned.", _class_2, childListDescr, _class_3);
                  GumTree2JdtAstConverterImpl.logger.error(_format_1);
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
      Class<? extends ASTNode> _class = astNode.getClass();
      Class<? extends ASTNode> _class_1 = parent.getClass();
      String _format = String.format("Did not find property for node of type %s in parent of type %s", _class, _class_1);
      GumTree2JdtAstConverterImpl.logger.warn(_format);
    }
    List<ITree> _children = tree.getChildren();
    for (final ITree child : _children) {
      this.createAstNode(child, ast, astNode);
    }
  }
}
