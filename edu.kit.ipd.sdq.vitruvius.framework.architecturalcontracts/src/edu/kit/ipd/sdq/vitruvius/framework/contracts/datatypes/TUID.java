package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ForwardHashedBackwardLinkedTree;

/**
 * Implements the multiton design pattern.
 * 
 * @author kramerm
 * 
 */
public class TUID {
    private static final ForwardHashedBackwardLinkedTree<String> SEGMENTS = new ForwardHashedBackwardLinkedTree<String>();
    private static final Map<ForwardHashedBackwardLinkedTree<String>.Segment, TUID> LAST_SEGMENT_2_TUID_INSTANCES_MAP = new HashMap<ForwardHashedBackwardLinkedTree<String>.Segment, TUID>();

    private ForwardHashedBackwardLinkedTree<String>.Segment lastSegment;

    /** Multiton classes should not have a public or default constructor. */
    private TUID(final List<String> splitTUIDString) {
        this.lastSegment = SEGMENTS.addNewSegmentsWhereNecessary(splitTUIDString);
    }

    public static synchronized TUID getInstance(final String tuidString) {
        return getInstance(tuidString, false);
    }

    private static TUID getInstance(final String tuidString, final boolean recursively) {
        if (tuidString == null) {
            throw new RuntimeException("The null string is no TUID!");
        } else {
            List<String> splitTUIDString = split(tuidString);
            ForwardHashedBackwardLinkedTree<String>.Segment lastSegmentOrPrefix = SEGMENTS
                    .getMaximalPrefix(splitTUIDString);
            TUID instance;
            if (lastSegmentOrPrefix != null
                    && lastSegmentOrPrefix.toString(VitruviusConstants.getTUIDSegmentSeperator()).equals(tuidString)) {
                // the complete specified tuidString was already mapped
                instance = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(lastSegmentOrPrefix);
                if (instance == null) {
                    if (!recursively) {
                        throw new RuntimeException("A TUID instance for the last segment '" + lastSegmentOrPrefix
                                + "' should already have been mapped for the tuidString '" + tuidString + "'!");
                    }
                } else {
                    return instance;
                }
            } // a real prefix of the specified tuidString or nothing was already mapped (but not
              // the complete tuidString)
            instance = new TUID(splitTUIDString);
            lastSegmentOrPrefix = instance.getLastSegment();
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(lastSegmentOrPrefix, instance);
            // also create TUIDs for all prefixes of the specified tuidString and register them
            Iterator<ForwardHashedBackwardLinkedTree<String>.Segment> segmentIterator = lastSegmentOrPrefix.iterator();
            ForwardHashedBackwardLinkedTree<String>.Segment pivot;
            while (segmentIterator.hasNext()) {
                pivot = segmentIterator.next();
                TUID subInstance = getInstance(pivot.toString(VitruviusConstants.getTUIDSegmentSeperator()), true);
                LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(subInstance.getLastSegment(), subInstance);
            }
            return instance;
        }
    }

    private static List<String> split(final String tuidString) {
        String seperator = VitruviusConstants.getTUIDSegmentSeperator();
        // TODO replace this possibly ArrayList with a LinkList if performance is not sufficient
        return Arrays.asList(tuidString.split(seperator));
    }

    protected ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
        return this.lastSegment;
    }

    // If a package is renamed in Java, then only the paths of all contained classifiers are
    // affected. The paths of subpackages are not affected but they are no longer subpackages.
    // If a package is renamed in Java, then the depth of its path may change arbitrarily. =>
    // renameLastSegment
    //
    // If a folder is renamed, then the paths of all contained elements are affected but the depth
    // may not change. => renameLastSegment
    //
    // If a package is moved in Java, then it may only be completely moved to another folder and
    // subpackages are not affected. It is not possible to move subpackages to another package. It
    // is however possible to move a package to a folder in which the package or a subpackage is
    // already existing, then the packages are merged. => moveLastSegment
    //
    // If a folder is moved, then the paths of all contained elements are affected and the depth may
    // change. If the destination folder already exists the containing elements of both folders are
    // merged. => moveLastSegment

    public void renameLastSegment(final String newLastSegmentString) {
        boolean containsSeparator = newLastSegmentString.indexOf(VitruviusConstants.getTUIDSegmentSeperator()) != -1;
        if (!containsSeparator) {
            SEGMENTS.changeSegmentValue(this.lastSegment, newLastSegmentString);
        } else {
            throw new IllegalArgumentException("The last segment '" + this.lastSegment + "' of the TUID '" + this
                    + "' cannot be renamed to '" + newLastSegmentString
                    + "' because this String contains the TUID separator '"
                    + VitruviusConstants.getTUIDSegmentSeperator() + "'!");
        }
    }

    public void moveLastSegment(final String fullDestinationTUIDString) {
        TUID fullDestinationTUID = getInstance(fullDestinationTUIDString);
        moveLastSegment(fullDestinationTUID);
    }

    public void moveLastSegment(final TUID fullDestinationTUID) {
        Collection<ForwardHashedBackwardLinkedTree<String>.Segment> obsoleteSegments = SEGMENTS
                .mergeSegmentIntoAnother(this.lastSegment, fullDestinationTUID.lastSegment);
        // remove the entry for all old last segments that have the destinationTUID as real prefix
        // as a result new requests for these TUIDs will return the TUIDs that have the destination
        // path as pefix
        for (ForwardHashedBackwardLinkedTree<String>.Segment obsoleteSegment : obsoleteSegments) {
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(obsoleteSegment);
        }
        LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(this.lastSegment);
        this.lastSegment = fullDestinationTUID.lastSegment;
    }

    @Override
    public String toString() {
        return this.lastSegment.toString(VitruviusConstants.getTUIDSegmentSeperator());
    }

    public static String toStrings() {
        return "TUID segments:\n" + SEGMENTS.toString() + "lastSegment2TUIDMap:\n"
                + LAST_SEGMENT_2_TUID_INSTANCES_MAP.toString();
    }

    public static boolean validate() {
        Set<String> treedTUIDStrings = new HashSet<String>();
        Collection<ForwardHashedBackwardLinkedTree<String>.Segment> segments = SEGMENTS.values();
        for (ForwardHashedBackwardLinkedTree<String>.Segment segment : segments) {
            String tuidString = segment.toString(VitruviusConstants.getTUIDSegmentSeperator());
            treedTUIDStrings.add(tuidString);
        }
        Collection<TUID> tuids = LAST_SEGMENT_2_TUID_INSTANCES_MAP.values();
        if (treedTUIDStrings.size() != tuids.size()) {
            throw new IllegalStateException(treedTUIDStrings.size() + " TUIDs are in the segment tree ("
                    + treedTUIDStrings + ") but " + tuids.size() + " are mapped by their last segments (" + tuids
                    + ")!");
        }
        for (TUID tuid : tuids) {
            String tuidString = tuid.toString();
            if (!treedTUIDStrings.contains(tuidString)) {
                throw new IllegalStateException("The TUID '" + tuidString
                        + "' is mapped by its last segment but not in the tree!");
            }
        }
        return true;
    }
}
