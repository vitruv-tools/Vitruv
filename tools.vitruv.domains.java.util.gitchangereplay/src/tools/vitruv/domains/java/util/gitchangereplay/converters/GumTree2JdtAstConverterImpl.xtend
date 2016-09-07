package tools.vitruv.domains.java.util.gitchangereplay.converters

import tools.vitruv.domains.java.util.gitchangereplay.converters.GumTree2JdtAstConverter
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.AST
import com.github.gumtreediff.tree.ITree
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor
import java.util.List
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor
import org.apache.log4j.Logger
import java.util.Map
import org.eclipse.jdt.core.dom.SimplePropertyDescriptor
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jface.text.Document
import org.eclipse.jdt.core.dom.CompilationUnit

class GumTree2JdtAstConverterImpl implements GumTree2JdtAstConverter {
	
	private static final Logger logger = Logger.getLogger(typeof(GumTree2JdtAstConverterImpl).name)
	
	private String lastConvertedAsText = null

	override convertTree(ITree gumTree) {
		val parser = ASTParser.newParser(AST.JLS8)
		//Empty document. Allows recording all changes then printing
		val document = new Document("")
		parser.setSource(document.get().toCharArray())
		val unit = parser.createAST(null) as CompilationUnit
		unit.recordModifications()
		for (ITree child : gumTree.children) {
			createAstNode(child, unit.AST, unit)
		}
		val edits = unit.rewrite(document, null)
		edits.apply(document)
		lastConvertedAsText = document.get()
		return unit
	}
	
	override getLastConvertedAsText() {
		return lastConvertedAsText
	}

	private def void createAstNode(ITree tree, AST ast, ASTNode parent) {
		val astNode = ast.createInstance(tree.type)
		
		//clean newly created node (remove all child properties in lists). E.g. array type gets created with one dimension by default. 
		//We'll add that later anyways so we'd end up with one more than needed.
		val newNodeProperties = astNode.structuralPropertiesForType
		for (property : newNodeProperties) {
			val propertyDescr = property as StructuralPropertyDescriptor
			if (propertyDescr.childListProperty) {
				val list = astNode.getStructuralProperty(propertyDescr) as List<ASTNode>
				list.clear
			}
		}
		
		
		val properties = tree.getMetadata("properties") as Map<String, Object>
		for (entry : properties.entrySet) {
			astNode.setProperty(entry.key, entry.value)
		}
		
		val simpleProperties = tree.getMetadata("simpleProperties") as Map<SimplePropertyDescriptor, Object>
		for (entry : simpleProperties.entrySet) {
			astNode.setStructuralProperty(entry.key, entry.value)	
		}
		
		
		
		// Add child by using the structuralProperty descriptors
		val parentProperties = parent.structuralPropertiesForType
		val propertyId = tree.getMetadata("propertyId")
		//logger.info(String.format("Looking for parent property of child with type %s with propertyId %s", astNode.class, propertyId))
		var found = false
		for (property : parentProperties) {
			val propertyDescr = property as StructuralPropertyDescriptor
			if (propertyDescr.id == propertyId) {
				if (propertyDescr.childProperty) {
					val childDescr = propertyDescr as ChildPropertyDescriptor
					if (childDescr.childType.isAssignableFrom(ASTNode.nodeClassForType(tree.type))) {
						if (!found) {
							parent.setStructuralProperty(propertyDescr, astNode)
//							logger.info(
//								String.format("Added node of type %s to property %s of parent of type %s",
//									astNode.class, childDescr, parent.class))
							found = true
						} else {
							logger.error(
								String.format(
									"Would add node of type %s to property %s of parent of type %s, but was already assigned.",
									astNode.class, childDescr, parent.class))
						}

					}
				} else if (propertyDescr.childListProperty) {
					val childListDescr = propertyDescr as ChildListPropertyDescriptor
					if (childListDescr.elementType.isAssignableFrom(ASTNode.nodeClassForType(tree.type))) {
						if (!found) {
							val childList = parent.getStructuralProperty(propertyDescr) as List<ASTNode>
							childList.add(astNode)
//							logger.info(
//								String.format("Added node of type %s to list-property %s of parent of type %s",
//									astNode.class, childListDescr, parent.class))
							found = true
						} else {
							logger.error(
								String.format(
									"Would add node of type %s to list-property %s of parent of type %s, but was already assigned.",
									astNode.class, childListDescr, parent.class))
						}

					}
				} else if (propertyDescr.simpleProperty) {
					logger.warn("Why did the gumtree have a simple property?!?")
				} else {
					throw new RuntimeException("A property of this kind, was not expected.")
				}
			}
		}
		
		if(!found) {
			logger.warn(String.format("Did not find property for node of type %s in parent of type %s", astNode.class, parent.class))
		}

		for (ITree child : tree.children) {
			createAstNode(child, ast, astNode)
		}
	}

}
