package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ForwardHashedBackwardLinkedTree;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple;

/**
 * A class for Temporarily Unique IDentifiers (TUIDs) that internally uses a
 * {@link ForwardHashedBackwardLinkedTree} to ensure that depending TUIDs are indirectly changed,
 * i.e. a call to toString() always returns the current TUID based on the links to its predecessors.
 * Implements the multiton design pattern to ensure that there exists only one TUID instance per
 * String key.<br/>
 * <br/>
 *
 * Helpful thoughts for TUID modifications in file systems and Java projects:<br/>
 * <br/>
 * If a package is renamed in Java, then only the paths of all contained classifiers are affected.
 * The paths of subpackages are not affected but they are no longer subpackages. If a package is
 * renamed in Java, then the depth of its path may change arbitrarily. In such cases
 * {@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 *
 * If a folder is renamed, then the paths of all contained elements are affected but the depth may
 * not change. In such cases {@link #renameLastSegment(String)} should be called. <br/>
 * <br/>
 *
 * If a package is moved in Java, then it may only be completely moved to another folder and
 * subpackages are not affected. It is not possible to move subpackages to another package. It is
 * however possible to move a package to a folder in which the package or a subpackage is already
 * existing, then the packages are merged. In such cases {@link #moveLastSegment(String)} should be
 * called. <br/>
 * <br/>
 *
 * If a folder is moved, then the paths of all contained elements are affected and the depth may
 * change. If the destination folder already exists the containing elements of both folders are
 * merged. In such cases {@link #moveLastSegment(String)} should be called. <br/>
 * <br/>
 *
 * @author kramerm
 *
 */
public class TUID implements Serializable {
    private static final ForwardHashedBackwardLinkedTree<String> SEGMENTS = generateForwardHashedBackwardLinkedTree();

    private static ForwardHashedBackwardLinkedTree<String> generateForwardHashedBackwardLinkedTree() {
        return new ForwardHashedBackwardLinkedTree<String>();
    }

    private static final Map<ForwardHashedBackwardLinkedTree<String>.Segment, TUID> LAST_SEGMENT_2_TUID_INSTANCES_MAP = new HashMap<ForwardHashedBackwardLinkedTree<String>.Segment, TUID>();

    private ForwardHashedBackwardLinkedTree<String>.Segment lastSegment;

    /** Multiton classes should not have a public or default constructor. */
    private TUID(final List<String> splitTUIDString) {
        this.lastSegment = SEGMENTS.addNewSegmentsWhereNecessary(splitTUIDString);
    }

    /**
     * Returns the unique TUID (instance) for the specified tuidString (key).
     *
     * @param tuidString
     * @return the unique TUID for the specified tuidString
     */
    public static synchronized List<TUID> getInstance(final List<String> tuidStrings) {
        // FIXME MK replace with Xtend: map
        List<TUID> tuids = new ArrayList<TUID>(tuidStrings.size());
        for (String tuidString : tuidStrings) {
            tuids.add(getInstance(tuidString));
        }
        return tuids;
    }

    /**
     * Returns the unique TUID (instance) for the specified tuidString (key).
     *
     * @param tuidString
     * @return the unique TUID for the specified tuidString
     */
    public static synchronized TUID getInstance(final String tuidString) {
        return getInstance(tuidString, false);
    }

    /**
     * Changes the given tuid so that it points to the given newLastSegment.<br/>
     * <b>ATTENTION: This changes the hashcode of the given tuid!</b>
     *
     * @param tuid
     * @param newLastSegment
     */
    private static synchronized void updateInstance(final TUID tuid,
            final ForwardHashedBackwardLinkedTree<String>.Segment newLastSegment) {
        final ForwardHashedBackwardLinkedTree<String>.Segment oldSegment = tuid.lastSegment;
        tuid.lastSegment = newLastSegment;
        LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(oldSegment);
    }

    private static synchronized TUID getInstance(final String tuidString, final boolean recursively) {
        if (tuidString == null) {
            throw new IllegalArgumentException("The null string is no TUID!");
        } else {
            final List<String> splitTUIDString = split(tuidString);
            ForwardHashedBackwardLinkedTree<String>.Segment lastSegmentOrPrefix = SEGMENTS
                    .getMaximalPrefix(splitTUIDString);
            TUID instance;
            if (lastSegmentOrPrefix != null
                    && lastSegmentOrPrefix.toString(VitruviusConstants.getTUIDSegmentSeperator()).equals(tuidString)) {
                // the complete specified tuidString was already mapped
                instance = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(lastSegmentOrPrefix);
                if (instance == null) {
                    if (!recursively) {
                        throw new IllegalStateException("A TUID instance for the last segment '" + lastSegmentOrPrefix
                                + "' should already have been mapped for the tuidString '" + tuidString + "'!");
                    }
                } else {
                    return instance;
                }
            } // a real prefix of the specified tuidString or nothing was already mapped (but not
              // the complete tuidString)
            instance = new TUID(splitTUIDString);
            ForwardHashedBackwardLinkedTree<String>.Segment lastSegment = instance.getLastSegment();
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(lastSegment, instance);
            // also create TUIDs for all prefixes of the specified tuidString and register them
            final Iterator<ForwardHashedBackwardLinkedTree<String>.Segment> segmentIterator = lastSegment.iterator();
            ForwardHashedBackwardLinkedTree<String>.Segment pivot;
            while (segmentIterator.hasNext()) {
                pivot = segmentIterator.next();
                final TUID subInstance = getInstance(pivot.toString(VitruviusConstants.getTUIDSegmentSeperator()),
                        true);
                LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(subInstance.getLastSegment(), subInstance);
            }
            return instance;
        }
    }

    private static List<String> split(final String tuidString) {
        final String seperator = VitruviusConstants.getTUIDSegmentSeperator();
        // TODO replace this possibly ArrayList with a LinkList if performance is not sufficient
        return Arrays.asList(tuidString.split(seperator));
    }

    private ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
        return this.lastSegment;
    }

    /**
     * Renames the <b>last</b> segment of this TUID instance to the specified
     * {@link newLastSegmentString}. If an instance for the resulting TUID already exists, then all
     * depending TUIDs of this instance and the destination instance are merged.<br/>
     * <br/>
     *
     * If infix segments of a TUID should be changed (e.g. a#tochange#b) use the
     * {@link renameSegments} method.
     *
     * @param newLastSegmentString
     *            the new name for the last segment
     * @throws an
     *             {@link IllegalArgumentException} if the specified {@link newLastSegmentString}
     *             contains the TUID separator
     */
    public void renameLastSegment(final String newLastSegmentString) {
        this.renameLastSegment(newLastSegmentString, null, null);
    }

    /**
     * Renames the <b>last</b> segment of this TUID instance to the specified
     * {@link newLastSegmentString}. If an instance for the resulting TUID already exists, then all
     * depending TUIDs of this instance and the destination instance are merged.<br/>
     * <br/>
     *
     * If infix segments of a TUID should be changed (e.g. a#tochange#b) use the
     * {@link renameSegments} method.
     *
     * @param newLastSegmentString
     *            the new name for the last segment
     * @throws an
     *             {@link IllegalArgumentException} if the specified {@link newLastSegmentString}
     *             contains the TUID separator
     */
    private void renameLastSegment(final String newLastSegmentString, final BeforeHashCodeUpdateLambda before,
            final AfterHashCodeUpdateLambda after) {
        final String segmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
        final boolean containsSeparator = newLastSegmentString.indexOf(segmentSeperator) != -1;
        if (!containsSeparator) {
            final ForwardHashedBackwardLinkedTree<String>.Segment ancestor = this.lastSegment.iterator().next();
            String fullDestinationTUIDString = "";
            if (ancestor != null) {
                fullDestinationTUIDString = ancestor.toString(segmentSeperator) + segmentSeperator;
            }
            fullDestinationTUIDString += newLastSegmentString;
            final TUID fullDestinationTUID = getInstance(fullDestinationTUIDString);
            moveLastSegment(fullDestinationTUID, before, after);
        } else {
            throw new IllegalArgumentException("The last segment '" + this.lastSegment + "' of the TUID '" + this
                    + "' cannot be renamed to '" + newLastSegmentString
                    + "' because this String contains the TUID separator '" + segmentSeperator + "'!");
        }
    }

    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged.
     *
     * @param fullDestinationTUID
     *            the full TUID of the move destination
     * @return
     */
    private void moveLastSegment(final TUID fullDestinationTUID, final BeforeHashCodeUpdateLambda before,
            final AfterHashCodeUpdateLambda after) {
        final Collection<Pair<ForwardHashedBackwardLinkedTree<String>.Segment, ForwardHashedBackwardLinkedTree<String>.Segment>> segmentPairs = SEGMENTS
                .mergeSegmentIntoAnother(this.lastSegment, fullDestinationTUID.lastSegment);
        for (final Pair<ForwardHashedBackwardLinkedTree<String>.Segment, ForwardHashedBackwardLinkedTree<String>.Segment> segmentPair : segmentPairs) {
            final ForwardHashedBackwardLinkedTree<String>.Segment oldSegment = segmentPair.getFirst();
            final TUID oldTUID = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(oldSegment);
            final ForwardHashedBackwardLinkedTree<String>.Segment newSegment = segmentPair.getSecond();
            Triple<TUID, String, Iterable<Pair<List<TUID>, Set<Correspondence>>>> removedMapEntries = null;
            if (before != null) {
                removedMapEntries = before.performPreAction(oldTUID);
            }
            // this update changes the hashcode of the given tuid
            TUID.updateInstance(oldTUID, newSegment);
            if (after != null && removedMapEntries != null) {
                after.performPostAction(removedMapEntries);
            }
        }
    }

    private int getCommonPrefixSegmentsCount(final TUID otherTUID) {
        final String thisTUIDString = toString();
        final String otherTUIDString = otherTUID.toString();
        final String commonPrefix = Strings.commonPrefix(thisTUIDString, otherTUIDString);
        if ("".equals(commonPrefix)) {
            return 0;
        } else {
            final String[] commonPrefixSegments = commonPrefix.split(VitruviusConstants.getTUIDSegmentSeperator());
            if (commonPrefix.endsWith(VitruviusConstants.getTUIDSegmentSeperator())
                    || thisTUIDString.equals(otherTUIDString)) {
                return commonPrefixSegments.length;
            } else {
                // the last segment is only partially shared and therefore not common
                return commonPrefixSegments.length - 1;
            }
        }
    }

    private static String[] getSegments(final TUID tuid) {
        return tuid.toString().split(VitruviusConstants.getTUIDSegmentSeperator());
    }

    private int getSegmentCount() {
        return getSegments(this).length;
    }

    private int getAndEnsureEqualSegmentCount(final TUID newTUID) {
        final int oldSegmentCount = getSegmentCount();
        final int newSegmentCount = newTUID.getSegmentCount();
        if (oldSegmentCount != newSegmentCount) {
            throw new IllegalArgumentException("Cannot update the TUID " + this + " because the new TUID " + newTUID
                    + " has a different number of segments!");
        }
        return oldSegmentCount;
    }

    private int getFirstSegmentToChange(final TUID newTUID) {
        final int segmentsCount = getAndEnsureEqualSegmentCount(newTUID);
        final int commonPrefixSegmentCount = getCommonPrefixSegmentsCount(newTUID);
        if (commonPrefixSegmentCount == segmentsCount) {
            return -1;
        }
        return commonPrefixSegmentCount + 1;
    }

    private static TUID getTUIDPrefix(final TUID tuid, final int length) {
        final String[] segments = getSegments(tuid);
        final String[] prefixSegments = Arrays.copyOfRange(segments, 0, length);
        final TUID prefixTUID = TUID
                .getInstance(StringUtils.join(prefixSegments, VitruviusConstants.getTUIDSegmentSeperator()));
        return prefixTUID;
    }

    /**
     * Renames a single segments or multiple segments of this TUID instance so that it represents
     * the specified new TUID. It is <b>not</b> possible to remove or add segments, i.e. the number
     * of segments has to stay unchanged. The position and number of segments that should be changed
     * are not restricted. <br/>
     *
     * If only the last segment should be changed the {@link renameLastSegment} method can be used.
     *
     * @param newTUID
     *            the TUID according to which the segments shall be renamed
     * @throws an
     *             {@link IllegalArgumentException} if {@link newTUID} has a different number of
     *             segments
     */
    public void renameSegments(final TUID newTUID) {
        this.renameSegments(newTUID, null, null);
    }

    /**
     * Renames a single segment or multiple segments of this TUID instance so that it represents the
     * specified new TUID. It is <b>not</b> possible to remove or add segments, i.e. the number of
     * segments has to stay unchanged. The position and number of segments that should be changed
     * are not restricted. <br/>
     *
     * If only the last segment should be changed the {@link renameLastSegment} method can be used.
     *
     * @param newTUID
     *            the TUID according to which the segments shall be renamed
     * @returns the pairs of TUID representing all performed individual changes of last segments
     * @throws an
     *             {@link IllegalArgumentException} if {@link newTUID} has a different number of
     *             segments
     */
    public List<Pair<String, String>> renameSegments(final TUID newTUID, final BeforeHashCodeUpdateLambda before,
            final AfterHashCodeUpdateLambda after) {
        final List<Pair<String, String>> changedPairs = new LinkedList<Pair<String, String>>();
        int firstSegmentToChange = getFirstSegmentToChange(newTUID);
        while (-1 != firstSegmentToChange) {
            final TUID oldPrefixPlusFirstSegmentToChange = TUID.getTUIDPrefix(this, firstSegmentToChange);
            final TUID newPrefixPlusFirstChangedSegment = TUID.getTUIDPrefix(newTUID, firstSegmentToChange);
            changedPairs.add(new Pair<String, String>(oldPrefixPlusFirstSegmentToChange.toString(),
                    newPrefixPlusFirstChangedSegment.toString()));
            oldPrefixPlusFirstSegmentToChange.moveLastSegment(newPrefixPlusFirstChangedSegment, before, after);
            // enjoy the side-effect of TUID: the right segment of this and newTUID will be changed
            // too
            firstSegmentToChange = getFirstSegmentToChange(newTUID);
        }
        return changedPairs;
        // FIXME MK REMOVE CHANGEDPAIRS
    }

    @Override
    public String toString() {
        return this.lastSegment.toString(VitruviusConstants.getTUIDSegmentSeperator());
    }

    /**
     * Returns a String representation of all registered TUID instances.
     *
     * @return a String representation of all registered TUID instances
     */
    public static String toStrings() {
        return "TUID segments:\n" + SEGMENTS.toString() + "lastSegment2TUIDMap:\n"
                + LAST_SEGMENT_2_TUID_INSTANCES_MAP.toString();
    }

    /**
     * Returns whether the TUID instance is valid in the sense that all TUID instances that are
     * contained in the forward (tree) registry are also contained in the backward (link) registry
     * and vice-versa.
     *
     * @return whether the TUID instance is valid
     * @throws a
     *             {@link IllegalStateException} if the TUID instance is not valid
     */
    public static boolean validate() {
        final Set<String> treedTUIDStrings = new HashSet<String>();
        final Collection<ForwardHashedBackwardLinkedTree<String>.Segment> segments = SEGMENTS.values();
        for (final ForwardHashedBackwardLinkedTree<String>.Segment segment : segments) {
            final String tuidString = segment.toString(VitruviusConstants.getTUIDSegmentSeperator());
            treedTUIDStrings.add(tuidString);
        }
        final Collection<TUID> tuids = LAST_SEGMENT_2_TUID_INSTANCES_MAP.values();
        if (treedTUIDStrings.size() != tuids.size()) {
            throw new IllegalStateException(
                    treedTUIDStrings.size() + " TUIDs are in the segment tree (" + treedTUIDStrings + ") but "
                            + tuids.size() + " are mapped by their last segments (" + tuids + ")!");
        }
        for (final TUID tuid : tuids) {
            final String tuidString = tuid.toString();
            if (!treedTUIDStrings.contains(tuidString)) {
                throw new IllegalStateException(
                        "The TUID '" + tuidString + "' is mapped by its last segment but not in the tree!");
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 + ((this.lastSegment == null) ? 0 : this.lastSegment.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TUID other = (TUID) obj;
        if (this.lastSegment == null) {
            if (other.lastSegment != null)
                return false;
        } else if (!this.lastSegment.equals(other.lastSegment))
            return false;
        return true;
    }

    public interface BeforeHashCodeUpdateLambda {
        Triple<TUID, String, Iterable<Pair<List<TUID>, Set<Correspondence>>>> performPreAction(TUID oldTUID);
    }

    public interface AfterHashCodeUpdateLambda {
        void performPostAction(Triple<TUID, String, Iterable<Pair<List<TUID>, Set<Correspondence>>>> removedMapEntries);
    }

    private Object writeReplace() throws ObjectStreamException {
        final TUID4Serialization tuid4Serialization = new TUID4Serialization();
        tuid4Serialization.setTUIDString(toString());
        return tuid4Serialization;
    }
}
