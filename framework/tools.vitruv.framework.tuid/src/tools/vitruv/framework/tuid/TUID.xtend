package tools.vitruv.framework.tuid

import java.io.Serializable
import java.util.Arrays
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Set
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree

import java.util.ArrayList
import java.util.Map
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree.Segment
import org.eclipse.emf.ecore.EObject

/** 
 * A class for Temporarily Unique IDentifiers (TUIDs) that internally uses a{@link ForwardHashedBackwardLinkedTree} to ensure that depending TUIDs are indirectly changed,
 * i.e. a call to toString() always returns the current TUID based on the links to its predecessors.
 * Implements the multiton design pattern to ensure that there exists only one TUID instance per
 * String key.<br/>
 * <br/>
 * Helpful thoughts for TUID modifications in file systems and Java projects:<br/>
 * <br/>
 * If a package is renamed in Java, then only the paths of all contained classifiers are affected.
 * The paths of subpackages are not affected but they are no longer subpackages. If a package is
 * renamed in Java, then the depth of its path may change arbitrarily. In such cases{@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 * If a folder is renamed, then the paths of all contained elements are affected but the depth may
 * not change. In such cases {@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 * If a package is moved in Java, then it may only be completely moved to another folder and
 * subpackages are not affected. It is not possible to move subpackages to another package. It is
 * however possible to move a package to a folder in which the package or a subpackage is already
 * existing, then the packages are merged. In such cases {@link #moveLastSegment(String)} should be
 * called. <br/>
 * <br/>
 * If a folder is moved, then the paths of all contained elements are affected and the depth may
 * change. If the destination folder already exists the containing elements of both folders are
 * merged. In such cases {@link #moveLastSegment(String)} should be called. <br/>
 * <br/>
 * @author kramerm
 */
final class TUID implements Serializable {

	protected static final long serialVersionUID = 5018494116382201707L

	static var SEGMENTS = generateForwardHashedBackwardLinkedTree()
	static val LAST_SEGMENT_2_TUID_INSTANCES_MAP = new HashMap<ForwardHashedBackwardLinkedTree<String>.Segment, TUID>()
	static Map<Segment, List<TUID>> LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP = new HashMap<Segment, List<TUID>>();

	package static def reinitialize() {
		SEGMENTS = generateForwardHashedBackwardLinkedTree();
	}

	package def updateTuid(TUID newTuid) {
		if (this.equals(newTuid)) {
			return;
		}
		renameOrMoveLastSegment(newTuid)
	}

	public def updateTuid(EObject newObject) {
		TuidManager.instance.updateTuid(this, newObject);
	}

	def private static generateForwardHashedBackwardLinkedTree() {
		return new ForwardHashedBackwardLinkedTree<String>()
	}

	var ForwardHashedBackwardLinkedTree<String>.Segment lastSegment

	/** 
	 * Multiton classes should not have a public or default constructor. 
	 */
	private new(List<String> splitTUIDString) {
		this.lastSegment = SEGMENTS.addNewSegmentsWhereNecessary(splitTUIDString)
	}

	/** 
	 * Returns the unique TUID (instance) for the specified tuidString (key).
	 * @param tuidString
	 * @return the unique TUID for the specified tuidString
	 */
	def static synchronized TUID getInstance(String tuidString) {
		return getInstance(tuidString, false)
	}

	def private static synchronized TUID getInstance(String tuidString, boolean recursively) {
		if (tuidString === null) {
			throw new IllegalArgumentException("The null string is no TUID!")
		} else {
			val splitTUIDString = split(tuidString)
			var lastSegmentOrPrefix = SEGMENTS.getMaximalPrefix(splitTUIDString)
			var TUID instance
			val lastSegmentOrPrefixString = if (lastSegmentOrPrefix != null) {
					lastSegmentOrPrefix.toString(VitruviusConstants.getTUIDSegmentSeperator());
				}
			if (lastSegmentOrPrefixString != null && lastSegmentOrPrefixString.equals(tuidString)) {
				// the complete specified tuidString was already mapped
				instance = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(lastSegmentOrPrefix)
				if (instance ===
					null) {
					if (!recursively) {
						throw new IllegalStateException('''A TUID instance for the last segment '«»«lastSegmentOrPrefix»' should already have been mapped for the tuidString '«»«tuidString»'!''')
					}
				} else {
					return instance
				}
			}
			// a real prefix of the specified tuidString or nothing was already mapped (but not
			// the complete tuidString)
			instance = new TUID(splitTUIDString)
			var lastSegment = instance.getLastSegment()
			LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(lastSegment, instance) // also create TUIDs for all prefixes of the specified tuidString and register them
			val lastSegmentsMapUnmodifiable = LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.get(lastSegment);
			val lastSegmentsMap = new ArrayList(
				if(lastSegmentsMapUnmodifiable != null) lastSegmentsMapUnmodifiable else #[]);
			lastSegmentsMap.add(instance);
			LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.put(lastSegment, lastSegmentsMap);
			val segmentIterator = lastSegment.iterator()
			var ForwardHashedBackwardLinkedTree<String>.Segment pivot
			while (segmentIterator.hasNext()) {
				pivot = segmentIterator.next()
				val TUID subInstance = getInstance(pivot.toString(VitruviusConstants.getTUIDSegmentSeperator()), true)
				LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(subInstance.getLastSegment(), subInstance)
				val lastSegmentsMapUnmodifiableSub = LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.get(
					subInstance.getLastSegment());
				val lastSegmentsMapSub = new ArrayList(
					if(lastSegmentsMapUnmodifiableSub != null) lastSegmentsMapUnmodifiableSub else #[]);
				lastSegmentsMapSub.add(subInstance);
				LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.put(subInstance.getLastSegment(), lastSegmentsMapSub);
			}
			return instance
		}
	}

	/** 
	 * Changes the given tuid so that it points to the given newLastSegment.<br/>
	 * <b>ATTENTION: This changes the hashcode of the given tuid!</b>
	 * @param tuid
	 * @param newLastSegment
	 */
	def private static synchronized void updateInstance(TUID tuid,
		ForwardHashedBackwardLinkedTree<String>.Segment newLastSegment) {
		val oldSegment = tuid.lastSegment
		val tuidsForNewSegmentUnmodifiable = LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.remove(newLastSegment);
		val tuidsForNewSegment = new ArrayList<TUID>(
			if(tuidsForNewSegmentUnmodifiable != null) tuidsForNewSegmentUnmodifiable else #[]);
		val tuidsForOldSegment = new ArrayList<TUID>(LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.remove(oldSegment));
		tuidsForNewSegment.addAll(tuidsForOldSegment);
		for (representant : tuidsForOldSegment) {
			representant.lastSegment = newLastSegment;
		}
		LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.put(newLastSegment, tuidsForNewSegment);
		LAST_SEGMENT_2_ALL_TUID_INSTANCES_MAP.remove(oldSegment);
		LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(oldSegment)
		LAST_SEGMENT_2_TUID_INSTANCES_MAP.putIfAbsent(newLastSegment, tuid);
	}

	def private static List<String> split(String tuidString) {
		val seperator = VitruviusConstants.getTUIDSegmentSeperator()
		// TODO replace this possibly ArrayList with a LinkList if performance is not sufficient
		return Arrays.asList(tuidString.split(seperator))
	}

	def private ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
		return this.lastSegment
	}

	// TODO MK Xtend improvement: define null as default value for before and after using an active annotation in all rename and move methods
	/**
	 * Either a) renames the last segment of this TUID to the last segment of the given {@link anotherTUID} 
	 * if they differ and all previous segments are the same or b) moves the last segment of this TUID to the second but last 
	 * segment of the given {@link anotherTUID} if both have at least one previous segment.
	 * 
	 * @throws IllegalArgumentException if both conditions are not met
	 */
	def void renameOrMoveLastSegment(TUID anotherTUID) {
		val newLastSegment = getNewLastSegmentIfIdenticalExceptForLastSegment(anotherTUID)
		if (newLastSegment != null) {
			renameLastSegment(newLastSegment)
		} else {
			moveLastSegment(anotherTUID)
		}
	}

	def private String getRenameExceptionMsg(
		TUID anotherTUID) '''«getRenameOrMoveExceptionMsgPrefix(anotherTUID)» have at least two segments!'''

	def private String getRenameOrMoveExceptionMsgPrefix(
		TUID anotherTUID) '''To either rename or move the last segment of '«this»' according to '«anotherTUID»' both TUIDs have to'''

	/** 
	 * Renames the <b>last</b> segment of this TUID instance to the given {@link newLastSegmentString}. If an instance for the resulting TUID already exists, then all
	 * depending TUIDs of this instance and the destination instance are merged.<br/>
	 * <br/>
	 * @param newLastSegmentString the new name for the last segment
	 * @throws an {@link IllegalArgumentException} if the specified {@link newLastSegmentString} contains the TUID separator
	 */
	def void renameLastSegment(String newLastSegmentString) {
		val segmentSeperator = VitruviusConstants.getTUIDSegmentSeperator()
		val containsSeparator = newLastSegmentString.indexOf(segmentSeperator) !== -1
		if (!containsSeparator) {
			val TUID fullDestinationTUID = getTUIDWithNewLastSegment(newLastSegmentString)
			moveLastSegment(
				fullDestinationTUID)
		} else {
			throw new IllegalArgumentException('''The last segment '«this.lastSegment»' of the TUID '«this»' cannot be renamed to '«newLastSegmentString»' because this String contains the TUID separator '«segmentSeperator»'!''')
		}
	}

	def private TUID getTUIDWithNewLastSegment(String newLastSegmentString) {
		val segmentSeperator = VitruviusConstants.getTUIDSegmentSeperator()
		val ancestor = this.lastSegment.iterator().next()
		var tuidWithNewLastSegmentString = ""
		if (ancestor !== null) {
			tuidWithNewLastSegmentString = ancestor.toString(segmentSeperator) + segmentSeperator
		}
		tuidWithNewLastSegmentString += newLastSegmentString
		return getInstance(tuidWithNewLastSegmentString)
	}

	/** 
	 * Renames the <b>last</b> segment of this TUID instance to the last segment of the given {@link anotherTUID} 
	 * if they differ and all previous segments are the same.<br/>
	 * 
	 * All TUIDs depending on the last segment of this TUID and on the last segment of {@link anotherTUID} are merged.<br/>
	 * <br/>
	 * @param anotherTUID the TUID with the new last segment
	 */
	def void renameLastSegment(TUID anotherTUID) {
		val newLastSegmentString = getNewLastSegmentIfIdenticalExceptForLastSegment(anotherTUID)
		if (newLastSegmentString != null) {
			renameLastSegment(newLastSegmentString)
		} else {
			throw new IllegalArgumentException(getRenameExceptionMsg(anotherTUID))
		}
	}

	def private String getNewLastSegmentIfIdenticalExceptForLastSegment(TUID anotherTUID) {
		val newLastSegmentString = anotherTUID?.getLastSegment()?.getValueString()
		val tuidWithNewLastSegment = getTUIDWithNewLastSegment(newLastSegmentString)
		if (tuidWithNewLastSegment != null && tuidWithNewLastSegment.equals(anotherTUID)) {
			return newLastSegmentString
		} else {
			return null
		}
	}

	/** 
	 * Moves the last segment of this TUID instance to the specified destination. If the destination
	 * already exists, then all depending TUIDs of this instance and the destination instance are
	 * merged.
	 * @param fullDestinationTUIDthe full TUID of the move destination
	 * @return
	 */
	def private <T> void moveLastSegment(TUID fullDestinationTUID) {
		val segmentPairs = SEGMENTS.mergeSegmentIntoAnother(this.lastSegment, fullDestinationTUID.lastSegment)
		for (segmentPair : segmentPairs) {
			val oldSegment = segmentPair.getFirst()
			val oldTUID = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(oldSegment)
			val newSegment = segmentPair.getSecond()
			TuidManager.instance.notifyListenerBeforeTuidUpdate(oldTUID)
			// this update changes the hashcode of the given tuid
			TUID.updateInstance(oldTUID, newSegment)
			TuidManager.instance.notifyListenerAfterTuidUpdate(oldTUID)
		}
	}

	override String toString() {
		return this.lastSegment.toString(VitruviusConstants.getTUIDSegmentSeperator())
	}

	/** 
	 * Returns a String representation of all registered TUID instances.
	 * @return a String representation of all registered TUID instances
	 */
	def static String toStrings() {
		return '''TUID segments:
«SEGMENTS.toString()»
lastSegment2TUIDMap:
«LAST_SEGMENT_2_TUID_INSTANCES_MAP.toString()»'''
	}

	/** 
	 * Returns whether the TUID instance is valid in the sense that all TUID instances that are
	 * contained in the forward (tree) registry are also contained in the backward (link) registry
	 * and vice-versa.
	 * @return whether the TUID instance is valid
	 * @throws a {@link IllegalStateException} if the TUID instance is not valid
	 */
	def static boolean validate() {
		val Set<String> treedTUIDStrings = new HashSet<String>()
		val Collection<ForwardHashedBackwardLinkedTree<String>.Segment> segments = SEGMENTS.values()
		for (ForwardHashedBackwardLinkedTree<String>.Segment segment : segments) {
			val String tuidString = segment.toString(VitruviusConstants.getTUIDSegmentSeperator())
			treedTUIDStrings.add(tuidString)
		}
		val Collection<TUID> tuids = LAST_SEGMENT_2_TUID_INSTANCES_MAP.values()
		if (treedTUIDStrings.size() !==	tuids.size()) {
			throw new IllegalStateException('''«treedTUIDStrings.size()» TUIDs are in the segment tree («treedTUIDStrings») but «tuids.size()» are mapped by their last segments («tuids»)!''')
		}
		for (TUID tuid : tuids) {
			val String tuidString = tuid.toString()
			if (!treedTUIDStrings.contains(
				tuidString)) {
				throw new IllegalStateException('''The TUID '«»«tuidString»' is mapped by its last segment but not in the tree!''')
			}
		}
		return true
	}

	override int hashCode() {
		return 31 + (if(this.lastSegment === null) 0 else this.lastSegment.hashCode())
	}

	override boolean equals(Object obj) {
		if(this === obj) return true
		if(obj === null) return false
		if (getClass() !== obj.getClass()) {
			return false
		}
		var TUID other = obj as TUID
		if (this.lastSegment === null) {
			if (other.lastSegment !== null) {
				return false
			}
		} else if (!this.lastSegment.equals(other.lastSegment)) {
			return false
		}
		return true
	}

}
