package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import com.github.gumtreediff.actions.RootsClassifier
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeUtils
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters.GumTree2JdtAstConverterImpl
import java.util.ArrayList
import org.apache.log4j.Logger
import com.github.gumtreediff.matchers.MappingStore
import org.apache.log4j.Level

class GumTreeChangeExtractor {
	
	private static final Logger logger = Logger.getLogger(typeof(GumTreeChangeExtractor))
	
	String oldContent
	
	String newContent
	
	new(String oldContent, String newContent) {
		this.oldContent = oldContent
		this.newContent = newContent
		logger.setLevel(Level.ALL)
	}
	
	def extract() {
		val generator = new JdtTreeGenerator()
		val srcTreeContext = generator.generateFromString(oldContent)
		val dstTreeContext = generator.generateFromString(newContent)
		logger.info(" --- SOURCE TREE --- ")
		logger.info(srcTreeContext.root.toPrettyString(srcTreeContext))
		logger.info(" --- DESTINATION TREE --- ")
		logger.info(dstTreeContext.root.toPrettyString(dstTreeContext))
		
		val contentList = new ArrayList()
		val converter = new GumTree2JdtAstConverterImpl()
		
		contentList.add(converter.convertTree(srcTreeContext.root).toString)
		
		val m = Matchers.getInstance().getMatcher(srcTreeContext.root, dstTreeContext.root); // retrieve the default matcher
		m.match();
		val mappings = m.getMappings();
		
		val classifier = new RootsClassifier(srcTreeContext, dstTreeContext, m)
		val workingTree = srcTreeContext.root.deepCopy
		for (delTree : classifier.srcDelTrees) {
			logger.info("Found DEL")
			if (removeNodeFromWorkingTree(delTree, true, workingTree, mappings)) {
				contentList.add(converter.convertTree(workingTree).toString)
			}
		}
		for (addTree : classifier.dstAddTrees) {
			logger.info("Found ADD")
			if (addNodeToWorkingTree(addTree, false, workingTree, mappings)) {
				contentList.add(converter.convertTree(workingTree).toString)
			}
		}
		for (mvTree : classifier.srcMvTrees) {
			logger.info("Found MV")
			val movedNodeInWorkingTree = findNodeWithId(workingTree, mvTree.id)
			val removed = removeNodeFromWorkingTree(movedNodeInWorkingTree, true, workingTree, mappings)
			val movedNodeInDstTree = mappings.getDst(mvTree)
			val added = addNodeToWorkingTree(movedNodeInDstTree, false, workingTree, mappings)
			if (added && removed) {
				contentList.add(converter.convertTree(workingTree).toString)
			} else if (! (!added && !removed)) {
				logger.warn("Couldn't add or remove but the other.")
			}
		}
		for (updTree : classifier.srcUpdTrees) {
			logger.info("Found UPD")
			val updatedNodeInWorkingTree = findNodeWithId(workingTree, updTree.id)
			val removed = removeNodeFromWorkingTree(updatedNodeInWorkingTree, true, workingTree, mappings)
			val updatedNodeInDstTree = mappings.getDst(updTree)
			val added = addNodeToWorkingTree(updatedNodeInDstTree, false, workingTree, mappings)
			if (added && removed) {
				contentList.add(converter.convertTree(workingTree).toString)
			} else if (! (!added && !removed)) {
				logger.warn("Couldn't add or remove but the other.")
			}
		}
		
		return contentList
		
	}
	
	def addNodeToWorkingTree(ITree addTree, boolean isSrc, ITree workingTree, MappingStore mappings) {
		val addCopy = addTree.deepCopy
		var parent = addTree.parent
		if (!isSrc) {
			parent = mappings.getSrc(parent)
		}
		if (parent != null) {
			val parentId = parent.id
			val parentInWorkingTree = findNodeWithId(workingTree, parentId)
			val children = parentInWorkingTree.children
			var pos = addTree.positionInParent
			// FIXME figure out how to correctly handle child order
			logger.warn("Did not assure that position in parent is set correct yet. TODO/FIXME")
			if (pos > children.size) {
				// Avoid out of bounds exception
				pos = children.size
			}
			children.add(pos, addCopy)
			parentInWorkingTree.children = children
			return true
			
		} else {
			logger.info("Did not find parent in src. Most likely was not the root of the change. Need to improve change root finding. Doing nothing.")
			return false
		}
		
	}
	
	def removeNodeFromWorkingTree(ITree delTree, boolean isSrc, ITree workingTree, MappingStore mappings) {
		var parent = delTree.parent
		if (!isSrc) {
			parent = mappings.getSrc(parent)
		}
		if (parent != null) {
			val parentId = parent.id
			val parentInWorkingTree = findNodeWithId(workingTree, parentId)
			if (parentInWorkingTree == null) {
				return false
			}
			val children = parentInWorkingTree.children
			var toDelete = null as ITree
			for (child : children) {
				if (child.id == delTree.id) {
					toDelete = child
				}
			}
			if (toDelete == null) {
				throw new RuntimeException("Could not find child.")
			}
			children.remove(toDelete)
			parentInWorkingTree.children = children
			return true
		} else {
			logger.info("Did not find parent in src. Most likely was not the root of the change. Need to improve change root finding. Doing nothing.")
			return false
		}
	}
	
	def findNodeWithId(ITree tree, int id) {
		for (node : TreeUtils.breadthFirst(tree)) {
			if (node.id == id) {
				return node
			}
		}
		logger.warn("Could not find parent. Probably already deleted from working tree due to earlier operation.")
		return null
	}
	
	
}