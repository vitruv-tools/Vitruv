package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.ConflictDetector
import org.eclipse.emf.ecore.InternalEObject

//import tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
class ConflictDetectorImpl implements ConflictDetector {
	static val logger = Logger::getLogger(ConflictDetectorImpl)
	BranchDiff diff
	List<EChange> baseEchanges
	List<EChange> compareEchanges
	val Map<String, String> rootToRootMap

	new() {
		this(#{})
	}

	new(Map<String, String> rootToRootMap) {
		this.rootToRootMap = rootToRootMap
	}

	override detectConlicts(BranchDiff branchDiff) {
		// TODO PS Remove 
		logger.level = Level::DEBUG
		this.diff = branchDiff
		setup
		checkLength
		findMatchesInChangeMatches
		val distance = levenshteinDistance
		logger.debug('''Levenshtein distance is «distance»''')
		cleanup
		return null
	}

	private def void checkLength() {
		if (diff.baseChanges.length === diff.compareChanges.length) {
			logger.debug('''The change sequences have the same length: «diff.baseChanges.length»''')
		} else {
			logger.debug(diff.baseChanges.length)
			logger.debug(diff.compareChanges.length)
		}
	}

	private def void findMatchesInChangeMatches() {
		val List<Pair<ChangeMatch, ChangeMatch>> matches = new ArrayList
		diff.baseChanges.forEach[processBaseChange(originalChange)]
		logger.debug('''The are potential «matches.length» matches''')
	}

	private def processBaseChange(TransactionalChange originalChange) {
		if (originalChange.EChanges.empty)
			logger.debug("No Echange contained")
		else {
			originalChange.EChanges.forEach [ baseEChange |
				val correspondentChange = compareEchanges.findFirst[baseEChange == it]
				if (null !== correspondentChange) {
				}
			]
		}
	}

	private def void setup() {
		logger.debug('''Start setup''')
		baseEchanges = diff.baseChanges.map[originalChange.EChanges].flatten.toList
		compareEchanges = diff.compareChanges.map[originalChange.EChanges].flatten.toList

		logger.debug('''Base has «baseEchanges.length» EChanges''')
		logger.debug('''Compare has «compareEchanges.length» EChanges''')
		logger.debug('''End setup''')
	}

	private def void cleanup() {
		logger.debug('''Start cleanup''')
		baseEchanges = null
		compareEchanges = null
		diff = null
		logger.debug('''End cleanup''')
	}

	private dispatch def boolean compareEchanges(EChange e1, EChange e2) {
		val x = EcoreUtil::equals(e1, e2)
		logger.debug("e1 and e2")
		logger.debug(e1)
		logger.debug(e2)
		if (rootToRootMap.empty)
			return false
		return x
	}

//	private dispatch def boolean compareEchanges(ReplaceSingleValuedEAttributeImpl<?, ?> e1,
//		ReplaceSingleValuedEAttributeImpl<?, ?> e2) {
//			
//	}
	private dispatch def boolean compareEchanges(CreateAndReplaceNonRootImpl<?, ?> e1,
		CreateAndReplaceNonRootImpl<?, ?> e2) {
		val createdObjectIsEqual = EcoreUtil::equals(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val containerIsEqual = EcoreUtil::equals(e1.insertChange.affectedEObject, e2.insertChange.affectedEObject)
		val affectedContainer1 = e1.insertChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.toPlatformString(false)
		var containerIsRootAndMapped = if (rootToRootMap.containsKey(affectedContainerPlatformString1)) {
				val correspondencePlatformString = rootToRootMap.get(affectedContainerPlatformString1)
				val affectedContainer2 = e2.insertChange.affectedEObject as InternalEObject
				val affectedContainerPlatformString2 = affectedContainer2.eProxyURI.toPlatformString(false)
				correspondencePlatformString == affectedContainerPlatformString2
			} else
				false
		val newValueIsEqual = EcoreUtil::equals(e1.insertChange.newValue, e2.insertChange.newValue)
		return createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && newValueIsEqual
	}

	private def int levenshteinDistance() {
		val len0 = baseEchanges.length + 1
		val len1 = compareEchanges.length + 1

		// the array of distances                                                       
		var int[] cost = newIntArrayOfSize(len0)
		var int[] newcost = newIntArrayOfSize(len0)

		// initial cost of skipping prefix in String s0         
		for (i : 0 ..< len0)
			cost.set(i, i)

// dynamically computing the array of distances                                  
// transformation cost for each letter in s1   
		for (j : 1 ..< len1) {
			// initial cost of skipping prefix in String s1                             
			newcost.set(0, j)

			// transformation cost for each letter in s0                                
			for (i : 1 ..< len0) {
				// matching current letters in both strings                             
				var int match
				if (compareEchanges(baseEchanges.get(i - 1), compareEchanges.get(j - 1))) {
					match = 0
				} else {
					match = 1
				}

				// computing cost for each transformation                               
				val cost_replace = cost.get(i - 1) + match
				val cost_insert = cost.get(i) + 1
				val cost_delete = newcost.get(i - 1) + 1

				// keep minimum cost                                                    
				newcost.set(i, Math::min(Math::min(cost_insert, cost_delete), cost_replace))
			}

			// swap cost/newcost arrays                                                 
			val swap = cost
			cost = newcost
			newcost = swap
		}

		// the distance is the cost for transforming all letters in both strings        
		return cost.get(len0 - 1)
	}
}
