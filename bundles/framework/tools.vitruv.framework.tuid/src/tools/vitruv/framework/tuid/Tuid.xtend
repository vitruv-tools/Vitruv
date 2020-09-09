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
import tools.vitruv.framework.util.datatypes.ForwardHashedBackwardLinkedTree.Segment
import org.eclipse.emf.ecore.EObject

/** 
 * A class for Temporarily Unique IDentifiers (Tuids) that internally uses a{@link ForwardHashedBackwardLinkedTree} to ensure that depending Tuids are indirectly changed,
 * i.e. a call to toString() always returns the current Tuid based on the links to its predecessors.
 * Implements the multiton design pattern to ensure that there exists only one Tuid instance per
 * String key.<br/>
 * <br/>
 * Helpful thoughts for Tuid modifications in file systems and Java projects:<br/>
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
final class Tuid implements Serializable {
	public static final String META_ELEMENT_PREFIX = "MetaElement"
	 
	protected static final long serialVersionUID = 5018494116382201707L

	static var SEGMENTS = generateForwardHashedBackwardLinkedTree()
	static val LAST_SEGMENT_2_Tuid_INSTANCES_MAP = new HashMap<ForwardHashedBackwardLinkedTree<String>.Segment, List<Tuid>>()

	package static def reinitialize() {
		SEGMENTS = generateForwardHashedBackwardLinkedTree();
	}

	def updateTuid(EObject newObject) {
		TuidManager.instance.updateTuid(this, newObject);
	}

	def private static generateForwardHashedBackwardLinkedTree() {
		return new ForwardHashedBackwardLinkedTree<String>()
	}

	var ForwardHashedBackwardLinkedTree<String>.Segment lastSegment

	/** 
	 * Multiton classes should not have a public or default constructor. 
	 */
	private new(List<String> splitTuidString) {
		this.lastSegment = SEGMENTS.addNewSegmentsWhereNecessary(splitTuidString);
		mapSegmentToTuid(lastSegment, this);
	}

	/** 
	 * Returns the unique Tuid (instance) for the specified tuidString (key).
	 * @param tuidString
	 * @return the unique Tuid for the specified tuidString
	 */
	def static synchronized Tuid getInstance(String tuidString) {
		return getInstance(tuidString, false)
	}

	def private static synchronized Tuid getInstance(String tuidString, boolean recursively) {
		if (tuidString === null) {
			throw new IllegalArgumentException("The null string is no Tuid!")
		} else {
			val splitTuidString = split(tuidString)
			var lastSegmentOrPrefix = SEGMENTS.getMaximalPrefix(splitTuidString)
			var Tuid instance
			val lastSegmentOrPrefixString = if (lastSegmentOrPrefix !== null) {
					lastSegmentOrPrefix.toString(VitruviusConstants.getTuidSegmentSeperator());
				}
			if (lastSegmentOrPrefixString !== null && lastSegmentOrPrefixString.equals(tuidString)) {
				// the complete specified tuidString was already mapped
				val instances = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(lastSegmentOrPrefix);
				if (instances.
					nullOrEmpty) {
					if (!recursively) {
						throw new IllegalStateException('''A Tuid instance for the last segment '«»«lastSegmentOrPrefix»' should already have been mapped for the tuidString '«»«tuidString»'!''')
					}
				} else {
					return instances.get(0)
				}
			}
			// a real prefix of the specified tuidString or nothing was already mapped (but not
			// the complete tuidString)
			instance = new Tuid(splitTuidString)
			var lastSegment = instance.getLastSegment()
			// also create Tuids for all prefixes of the specified tuidString and register them
			val segmentIterator = lastSegment.iterator()
			var ForwardHashedBackwardLinkedTree<String>.Segment pivot
			while (segmentIterator.hasNext()) {
				pivot = segmentIterator.next()
				getInstance(pivot.toString(VitruviusConstants.getTuidSegmentSeperator()), true)
			}
			return instance
		}
	}

	private static def void mapSegmentToTuid(Segment segment, Tuid tuid) {
		val segmentToTuidsListImmutable = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(segment);
		val segmentToTuidsList = if (segmentToTuidsListImmutable !== null) {
				new ArrayList(segmentToTuidsListImmutable);
			} else {
				new ArrayList<Tuid>();
			}
		segmentToTuidsList.add(tuid);
		LAST_SEGMENT_2_Tuid_INSTANCES_MAP.put(segment, segmentToTuidsList);
	}

	/** 
	 * Changes the given tuid so that it points to the given newLastSegment.<br/>
	 * <b>ATTENTION: This changes the hashcode of the given tuid!</b>
	 * @param tuid
	 * @param newLastSegment
	 */
	def private static synchronized void updateInstance(Tuid tuid,
		ForwardHashedBackwardLinkedTree<String>.Segment newLastSegment) {
		TuidManager.instance.notifyListenerBeforeTuidUpdate(tuid)
		val oldSegment = tuid.lastSegment
		val tuidsForOldSegment = new ArrayList<Tuid>(LAST_SEGMENT_2_Tuid_INSTANCES_MAP.remove(oldSegment));
		for (representant : tuidsForOldSegment) {
			representant.lastSegment = newLastSegment;
			mapSegmentToTuid(newLastSegment, representant);
		}
		TuidManager.instance.notifyListenerAfterTuidUpdate(tuid)
	}

	def private static List<String> split(String tuidString) {
		val seperator = VitruviusConstants.getTuidSegmentSeperator()
		// TODO replace this possibly ArrayList with a LinkList if performance is not sufficient
		return Arrays.asList(tuidString.split(seperator))
	}

	def private ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
		return this.lastSegment
	}

	/**
	 * Either a) renames the last segment of this Tuid to the last segment of the given {@link newTuid} 
	 * if they differ and all previous segments are the same or b) moves the last segment of this Tuid to the second but last 
	 * segment of the given {@link newTuid}.
	 */
	def void updateTuid(Tuid newTuid) {
		if (this.equals(newTuid)) {
			return;
		}
		moveLastSegment(newTuid)
	}

	/** 
	 * Moves the last segment of this Tuid instance to the specified destination. If the destination
	 * already exists, then all depending Tuids of this instance and the destination instance are
	 * merged.
	 * @param fullDestinationTuidthe full Tuid of the move destination
	 * @return
	 */
	def private <T> void moveLastSegment(Tuid fullDestinationTuid) {
		val segmentPairs = SEGMENTS.mergeSegmentIntoAnother(this.lastSegment, fullDestinationTuid.lastSegment)
		for (segmentPair : segmentPairs) {
			val oldSegment = segmentPair.getFirst()
			val oldTuid = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.get(oldSegment).get(0)
			val newSegment = segmentPair.getSecond()
			// this update changes the hashcode of the given tuid
			Tuid.updateInstance(oldTuid, newSegment)
		}
	}

	override String toString() {
		return this.lastSegment.toString(VitruviusConstants.getTuidSegmentSeperator())
	}

	/** 
	 * Returns a String representation of all registered Tuid instances.
	 * @return a String representation of all registered Tuid instances
	 */
	def static String toStrings() {
		return '''Tuid segments:
«SEGMENTS.toString()»
lastSegment2TuidMap:
«LAST_SEGMENT_2_Tuid_INSTANCES_MAP.toString()»'''
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
		var Tuid other = obj as Tuid
		if (this.lastSegment === null) {
			if (other.lastSegment !== null) {
				return false
			}
		} else if (!this.lastSegment.equals(other.lastSegment)) {
			return false
		}
		return true
	}

	/** 
	 * Returns whether the Tuid instance is valid in the sense that all Tuid instances that are
	 * contained in the forward (tree) registry are also contained in the backward (link) registry
	 * and vice-versa.
	 * @return whether the Tuid instance is valid
	 * @throws a {@link IllegalStateException} if the Tuid instance is not valid
	 */
	def static boolean validate() {
		val Set<String> treedTuidStrings = new HashSet<String>()
		val Collection<ForwardHashedBackwardLinkedTree<String>.Segment> segments = SEGMENTS.values()
		for (ForwardHashedBackwardLinkedTree<String>.Segment segment : segments) {
			val String tuidString = segment.toString(VitruviusConstants.getTuidSegmentSeperator())
			treedTuidStrings.add(tuidString)
		}
		val Collection<Tuid> tuids = LAST_SEGMENT_2_Tuid_INSTANCES_MAP.values().map[it.get(0)].toList;
		if (treedTuidStrings.size() !==
			tuids.
				size()) {
					throw new IllegalStateException('''«treedTuidStrings.size()» Tuids are in the segment tree («treedTuidStrings») but «tuids.size()» are mapped by their last segments («tuids»)!''')
				}
				for (Tuid tuid : tuids) {
					val String tuidString = tuid.toString()
					if (!treedTuidStrings.contains(
						tuidString)) {
						throw new IllegalStateException('''The Tuid '«»«tuidString»' is mapped by its last segment but not in the tree!''')
					}
				}
				return true
			}

	def boolean isMetaElementTuid() {
		return this.toString().startsWith(META_ELEMENT_PREFIX);
	}
}
