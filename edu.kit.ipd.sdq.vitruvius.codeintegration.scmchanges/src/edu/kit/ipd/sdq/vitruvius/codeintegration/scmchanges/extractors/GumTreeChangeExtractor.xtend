package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import com.github.gumtreediff.actions.RootsClassifier
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator
import com.github.gumtreediff.matchers.MappingStore
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeUtils
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters.GumTree2JdtAstConverterImpl
import java.util.ArrayList
import java.util.Comparator
import java.util.Set
import org.apache.log4j.Level
import org.apache.log4j.Logger
import java.util.List
import org.eclipse.emf.common.util.URI
import java.util.HashSet

class GumTreeChangeExtractor implements IAtomicChangeExtractor {
	
	private static final Logger logger = Logger.getLogger(typeof(GumTreeChangeExtractor))
	
	private String oldContent
	
	private String newContent
	
	private IContentValidator validator
	
	private URI fileUri
	
	private int validExtractions
	
	private int totalExtractions
	
	new(String oldContent, String newContent, URI fileUri) {
		this.oldContent = oldContent
		this.newContent = newContent
		this.fileUri = fileUri
		this.validator = null
		this.validExtractions = 0
		this.totalExtractions = 0
		logger.setLevel(Level.ALL)
	}
	
	override extract() {
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
		
		processDels(classifier, workingTree, mappings, contentList, converter, srcTreeContext.root)	
		processAdds(classifier, workingTree, mappings, contentList, converter, dstTreeContext.root)	
		processMvs(classifier, workingTree, mappings, contentList, converter)
		processUpds(classifier, workingTree, mappings, contentList, converter)
		
		contentList.add(converter.convertTree(dstTreeContext.root).toString)
		
		totalExtractions = totalExtractions + contentList.size
		
		if (validator != null) {
			val toRemove = new HashSet<String>();
			for (contentString : contentList) {
				if (!validator.isValid(contentString, fileUri)) {
					toRemove.add(contentString)
				}
			}
			contentList.removeAll(toRemove)
		}
		
		validExtractions = validExtractions +  contentList.size

		return contentList
		
	}
	
	private def processUpds(RootsClassifier classifier, ITree workingTree, MappingStore mappings, ArrayList<String> contentList, GumTree2JdtAstConverterImpl converter) {
		val rootUpds = getRootChanges(classifier.srcUpdTrees)
		for (updTree : rootUpds) {
			logger.info("Found UPD")
			val updatedNodeInWorkingTree = findNodeWithId(workingTree, updTree.id)
			val removed = removeNodeFromWorkingTree(updatedNodeInWorkingTree, workingTree, mappings)
			val updatedNodeInDstTree = mappings.getDst(updTree)
			val added = addNodeToWorkingTree(updatedNodeInDstTree, workingTree, mappings)
			if (added && removed) {
				contentList.add(converter.convertTree(workingTree).toString)
			} else if (! (!added && !removed)) {
				logger.warn("Couldn't add or remove but the other.")
			}
		}
	}
	
	private def processMvs(RootsClassifier classifier, ITree workingTree, MappingStore mappings, ArrayList<String> contentList, GumTree2JdtAstConverterImpl converter) {
		val rootMvs = getRootChanges(classifier.srcMvTrees)
		for (mvTree : rootMvs) {
			logger.info("Found MV")
			val movedNodeInWorkingTree = findNodeWithId(workingTree, mvTree.id)
			val removed = removeNodeFromWorkingTree(movedNodeInWorkingTree, workingTree, mappings)
			val movedNodeInDstTree = mappings.getDst(mvTree)
			val added = addNodeToWorkingTree(movedNodeInDstTree, workingTree, mappings)
			if (added && removed) {
				contentList.add(converter.convertTree(workingTree).toString)
			} else if (! (!added && !removed)) {
				logger.warn("Couldn't add or remove but the other.")
			}
		}
	}
	
	private def processAdds(RootsClassifier classifier, ITree workingTree, MappingStore mappings, ArrayList<String> contentList, GumTree2JdtAstConverterImpl converter, ITree completeDst) {
		val rootAdds = getRootChanges(classifier.dstAddTrees)
		rootAdds.sort(new OrderbyBreadthFirstOrderingOfCompleteTree(completeDst))
		for (addTree : rootAdds) {
			logger.info("Found ADD")
			if (addNodeToWorkingTree(addTree, workingTree, mappings)) {
				contentList.add(converter.convertTree(workingTree).toString)
			}
		}
	}
	
	private def processDels(RootsClassifier classifier, ITree workingTree, MappingStore mappings, ArrayList<String> contentList, GumTree2JdtAstConverterImpl converter, ITree completeSrc) {
		val rootDels = getRootChanges(classifier.srcDelTrees)
		rootDels.sort(new OrderbyBreadthFirstOrderingOfCompleteTree(completeSrc, true))
		for (delTree : rootDels) {
			logger.info("Found DEL")
			if (removeNodeFromWorkingTree(delTree, workingTree, mappings)) {
				contentList.add(converter.convertTree(workingTree).toString)
			}
		}
	}
	
	private def getRootChanges(Set<ITree> allChanges) {
		val rootChanges = new ArrayList<ITree>()
		// Make sure that only root changes are executed
		for (tree : allChanges) {
			if (!allChanges.contains(tree.parent)) {
				rootChanges.add(tree)
			}
		}
		return rootChanges
	}
	
	private def addNodeToWorkingTree(ITree addTree, ITree workingTree, MappingStore mappings) {
		val addCopy = addTree.deepCopy
		var dstParent = addTree.parent
		val srcParent = mappings.getSrc(dstParent)

		if (srcParent != null) {
			val parentId = srcParent.id
			val parentInWorkingTree = findNodeWithId(workingTree, parentId)
			val children = parentInWorkingTree.children
			var pos = addTree.positionInParent
			if (pos > children.size) {
				// Avoid out of bounds exception
				pos = children.size
			}
			children.add(pos, addCopy)
			// we'll try to figure out correct order by looking at dst order
			children.sort(new OrderByDstOrderComparator(dstParent))
			parentInWorkingTree.children = children
			return true
			
		} else {
			logger.warn("Did not find parent in src. Should only happen for non-root changes, that we should exclude earlier.")
			return false
		}
		
	}
	
	private def removeNodeFromWorkingTree(ITree delTree, ITree workingTree, MappingStore mappings) {
		var srcParent = delTree.parent
		if (srcParent != null) {
			val parentId = srcParent.id
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
			logger.warn("Did not find parent in src. Should only happen for non-root changes, that we should exclude earlier.")
			return false
		}
	}
	
	private def findNodeWithId(ITree tree, int id) {
		for (node : TreeUtils.breadthFirst(tree)) {
			if (node.id == id) {
				return node
			}
		}
		logger.warn("Could not find parent. Probably already deleted from working tree due to earlier operation.")
		return null
	}
	
	override setValidator(IContentValidator validator) {
		this.validator = validator
	}
	
	override getNumberOfTotalExtractions() {
		return totalExtractions;
	}
	
	override getNumberOfValidExtractions() {
		return validExtractions;
	}
	
}

class OrderbyBreadthFirstOrderingOfCompleteTree implements Comparator<ITree> {
	
	List<ITree> completeTreeOrderedByBreadthFirst;
	
	int factor
	
	new(ITree completeTree, boolean invert) {
		this.completeTreeOrderedByBreadthFirst = TreeUtils.breadthFirst(completeTree)
		this.factor = if (!invert) 1 else -1 
	}
	
	new(ITree completeTree) {
		this.completeTreeOrderedByBreadthFirst = TreeUtils.breadthFirst(completeTree)
		this.factor = 1
	}
	
	override compare(ITree o1, ITree o2) {
		if (completeTreeOrderedByBreadthFirst.indexOf(o1) < completeTreeOrderedByBreadthFirst.indexOf(o2)) {
			return -1 * factor
		} else {
			return 1 * factor
		}
	}
	
}

/**
 * Used to sort children of a node by what the order looks like in the dst tree
 */
class OrderByDstOrderComparator implements Comparator<ITree> {
	
	ITree dstParent
	
	new(ITree dstParent) {
		this.dstParent = dstParent
	}
	
	override compare(ITree o1, ITree o2) {
		var dst1 = null as ITree;
		var dst2 = null as ITree;
		for (dstChild : dstParent.children) {
			if (dstChild.id == o1.id) {
				dst1 = dstChild
			} else if (dstChild.id == o2.id) {
				dst2 = dstChild
			}
		}
		if (dst1 == null || dst2 == null) {
			return 1
		}
		val pos1 = dst1.positionInParent
		val pos2 = dst2.positionInParent
		if (pos1 < pos2) {
			-1
		} else {
			1
		}
	}
	
}