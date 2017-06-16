package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
import tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
import tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DistanceCalculator
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

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
		val comparison = [EChange a, EChange b|compareEchange(a, b)]
		val distance = DistanceCalculator::instance.levenshteinDistance(baseEchanges, compareEchanges, comparison)
		val Graph dependencyGraph = new SingleGraph("Conflict")
		cleanup
		new ConflictImpl(distance, dependencyGraph)
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

	private dispatch def boolean compareEchange(EChange e1, EChange e2) {
		false
	}

	private dispatch def boolean compareEchange(ReplaceSingleValuedEAttributeImpl<?, ?> e1,
		ReplaceSingleValuedEAttributeImpl<?, ?> e2) {
		val affectedObjectIsEqual = EcoreUtil::equals(e1.affectedEObject, e2.affectedEObject)
		val affectedFeatureIsEqual = EcoreUtil::equals(e1.affectedFeature, e2.affectedFeature)
		val newValueIsEqual = e1.newValue == e2.newValue
		val affectedContainer1 = e1.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.toPlatformString(false)
		val filtered = rootToRootMap.filter[p1, p2|affectedContainerPlatformString1.contains(p1)]
		val containerIsRootAndMapped = filtered.entrySet.map [
			val affectedContainer2 = e2.affectedEObject as InternalEObject
			val affectedContainerPlatformString2 = affectedContainer2.eProxyURI.toPlatformString(false)
			if (!affectedContainerPlatformString2.contains(value))
				throw new IllegalStateException('''No lying under root''')
			val s = affectedContainerPlatformString2.replace(value, key)
			val x = affectedContainerPlatformString1 == s
			return x
		].fold(false, [current, next|current || next])
		(affectedObjectIsEqual || containerIsRootAndMapped) && affectedFeatureIsEqual && newValueIsEqual
	}

	private dispatch def boolean compareEchange(CreateAndReplaceNonRootImpl<?, ?> e1,
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
		createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && newValueIsEqual
	}

	private dispatch def boolean compareEchange(CreateAndInsertNonRootImpl<?, ?> e1,
		CreateAndInsertNonRootImpl<?, ?> e2) {
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
		createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && newValueIsEqual
	}

}
