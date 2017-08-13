package tools.vitruv.framework.tuid.impl

import java.util.Arrays
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Set

import org.eclipse.emf.ecore.EObject

import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree.Segment

class TuidImpl implements Tuid {
	static extension TuidManager = TuidManager::instance
	protected static val serialVersionUID = 5018494116382201707L
	static val Map<ForwardHashedBackwardLinkedTree<String>. Segment, List<Tuid>> LAST_SEGMENT_2_Tuid_INSTANCES_MAP = newHashMap

	static var SEGMENTS = generateForwardHashedBackwardLinkedTree

	var ForwardHashedBackwardLinkedTree<String>.Segment lastSegment

	static def reinitialize() {
		SEGMENTS = generateForwardHashedBackwardLinkedTree
	}

	static def synchronized Tuid getInstance(String tuidString) {
		return getInstance(tuidString, false)
	}

	/**
	 * See also {@link tools.vitruv.framework.tuid.Tuid#toStrings()} 
	 */
	def static String toStrings() '''
		Tuid segments:
		«SEGMENTS.toString»
		lastSegment2TuidMap:
		«LAST_SEGMENT_2_Tuid_INSTANCES_MAP.toString»
	'''

	/**
	 * See also {@link tools.vitruv.framework.tuid.Tuid#getInstance} 
	 */
	static def synchronized Tuid getInstance(String tuidString, boolean recursively) {
		if (tuidString === null)
			throw new IllegalArgumentException("The null string is no Tuid!")
		val splitTuidString = split(tuidString)
		var lastSegmentOrPrefix = SEGMENTS.getMaximalPrefix(splitTuidString)
		var Tuid instance
		val lastSegmentOrPrefixString = if (lastSegmentOrPrefix !== null) {
				lastSegmentOrPrefix.toString(VitruviusConstants.tuidSegmentSeperator)
			}
		if (lastSegmentOrPrefixString !== null && lastSegmentOrPrefixString.equals(tuidString)) {
			// the complete specified tuidString was already mapped
			val instances = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(lastSegmentOrPrefix)
			if (instances.nullOrEmpty) {
				if (!recursively)
					throw new IllegalStateException('''A Tuid instance for the last segment '«»«lastSegmentOrPrefix»' should already have been mapped for the tuidString '«»«tuidString»'!''')
			} else {
				return instances.get(0)
			}
		}
		// a real prefix of the specified tuidString or nothing was already mapped (but not
		// the complete tuidString)
		instance = new TuidImpl(splitTuidString)
		var lastSegment = (instance as TuidImpl).lastSegment
		// also create Tuids for all prefixes of the specified tuidString and register them
		val segmentIterator = lastSegment.iterator
		var ForwardHashedBackwardLinkedTree<String>.Segment pivot
		while (segmentIterator.hasNext) {
			pivot = segmentIterator.next
			getInstance(pivot.toString(VitruviusConstants.tuidSegmentSeperator), true)
		}
		return instance
	}

	/**
	 * See also {@link tools.vitruv.framework.tuid.Tuid#validate} 
	 */
	def static boolean validate() {
		val Set<String> treedTuidStrings = newHashSet
		val Collection<ForwardHashedBackwardLinkedTree<String>.Segment> segments = SEGMENTS.values
		segments.forEach [
			val String tuidString = it.toString(VitruviusConstants::getTuidSegmentSeperator())
			treedTuidStrings += tuidString
		]
		val tuids = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.values.map[it.get(0)].toList.immutableCopy

		if (treedTuidStrings.size !== tuids.length)
			throw new IllegalStateException('''
				«treedTuidStrings.size» Tuids are in the segment tree («treedTuidStrings») but «tuids.size» are mapped by their last segments («tuids»)!
			''')
		tuids.filter [ tuid |
			!treedTuidStrings.contains(tuid.toString)
		].forEach [ tuid |
			throw new IllegalStateException('''
				The Tuid '«tuid»' is mapped by its last segment but not in the tree!
			''')
		]
		return true
	}

	private static def generateForwardHashedBackwardLinkedTree() {
		new ForwardHashedBackwardLinkedTree<String>
	}

	private static def void mapSegmentToTuid(Segment segment, Tuid tuid) {
		val segmentToTuidsListImmutable = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(segment)
		val List<Tuid> segmentToTuidsList = if (segmentToTuidsListImmutable !== null) {
				newArrayList(segmentToTuidsListImmutable)
			} else {
				newArrayList
			}
		segmentToTuidsList += tuid
		LAST_SEGMENT_2_Tuid_INSTANCES_MAP.put(segment, segmentToTuidsList)
	}

	/**
	 * Changes the given tuid so that it points to the given newLastSegment.<br/>
	 * <b>ATTENTION: This changes the hashcode of the given tuid!</b>
	 * @param tuid
	 * @param newLastSegment
	 */
	private static def synchronized void updateInstance(
		Tuid tuid,
		ForwardHashedBackwardLinkedTree<String>.Segment newLastSegment
	) {
		notifyListenerBeforeTuidUpdate(tuid)
		val oldSegment = (tuid as TuidImpl).lastSegment
		val List<Tuid> tuidsForOldSegment = newArrayList(LAST_SEGMENT_2_Tuid_INSTANCES_MAP.remove(oldSegment))
		tuidsForOldSegment.filter(TuidImpl).forEach [ other |
			other.lastSegment = newLastSegment
			mapSegmentToTuid(newLastSegment, other)
		]
		notifyListenerAfterTuidUpdate(tuid)
	}

	def static List<String> split(String tuidString) {
		val seperator = VitruviusConstants::tuidSegmentSeperator
		// TODO replace this possibly ArrayList with a LinkList if performance is not sufficient
		return Arrays::asList(tuidString.split(seperator))
	}

	/**
	 * Multiton classes should not have a public or default constructor.
	 */
	private new(List<String> splitTuidString) {
		lastSegment = SEGMENTS.addNewSegmentsWhereNecessary(splitTuidString)
		mapSegmentToTuid(lastSegment, this)
	}

	override hashCode() {
		return 31 + (if (lastSegment === null) 0 else lastSegment.hashCode)
	}

	override equals(Object obj) {
		if (this === obj) return true
		if (obj === null) return false
		if (getClass() !== obj.class)
			return false
		var Tuid other = obj as Tuid
		if (this.lastSegment === null) {
			if ((other as TuidImpl).lastSegment !== null) {
				return false
			}
		} else if (!this.lastSegment.equals((other as TuidImpl).lastSegment)) {
			return false
		}
		return true
	}

	override toString() {
		lastSegment.toString(VitruviusConstants::tuidSegmentSeperator)
	}

	/**
	 * {@inheritDoc}
	 */
	override updateTuid(EObject newObject) {
		updateTuid(this, newObject)
	}

	/**
	 * {@inheritDoc}
	 */
	override updateTuid(Tuid newTuid) {
		if (this == newTuid)
			return;
		moveLastSegment(newTuid)
	}

	/**
	 * Moves the last segment of this Tuid instance to the specified destination. If the destination
	 * already exists, then all depending Tuids of this instance and the destination instance are
	 * merged.
	 * @param fullDestinationTuidthe full Tuid of the move destination
	 * @return
	 */
	private def <T> void moveLastSegment(Tuid fullDestinationTuid) {
		val segmentPairs = SEGMENTS.mergeSegmentIntoAnother(lastSegment, (fullDestinationTuid as TuidImpl).lastSegment)
		segmentPairs.forEach [
			val oldSegment = first
			val oldTuid = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(oldSegment).get(0)
			val newSegment = second
			// this update changes the hashcode of the given tuid
			TuidImpl::updateInstance(oldTuid, newSegment)
		]
	}

}
