package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;

import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ForwardHashedBackwardLinkedTree;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ForwardHashedBackwardLinkedTree.Segment;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

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
public class TUID {
    private static final ForwardHashedBackwardLinkedTree<String> SEGMENTS = new ForwardHashedBackwardLinkedTree<String>();
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
    public static synchronized TUID getInstance(final String tuidString) {
        return getInstance(tuidString, false);
    }

    private static TUID getInstance(final String tuidString, final boolean recursively) {
        if (tuidString == null) {
            throw new IllegalArgumentException("The null string is no TUID!");
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
                        throw new IllegalStateException("A TUID instance for the last segment '" + lastSegmentOrPrefix
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

    private ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
        return this.lastSegment;
    }
    
    /**
     * Returns the minimal suffix of the TUID, i.e. a String representation of the last segment.
     * @return
     */
    public String getMinimalSuffix() {
    	return getLastSegment().toString();
    }

    /**
     * Renames the <b>last</b> segment of this TUID instance to the specified
     * {@link newLastSegmentString}. If an instance for the resulting TUID already exists, then all
     * depending TUIDs of this instance and the destination instance are merged (as if
     * {@link #moveLastSegment(String)} would have been called).<br/>
     * <br/>
     * 
     * If an infix segment of a TUID should be changed (e.g. a#tochange#b) this method has to be
     * called on the TUID instance that ends with the segment to change (a#tochange).
     * 
     * @param newLastSegmentString
     *            the new name for the last segment
     * @throws an
     *             {@link IllegalArgumentException} if the specified {@link newLastSegmentString}
     *             contains the TUID separator
     */
    public void renameLastSegment(final String newLastSegmentString) {
        String segmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
        boolean containsSeparator = newLastSegmentString.indexOf(segmentSeperator) != -1;
        if (!containsSeparator) {
            ForwardHashedBackwardLinkedTree<String>.Segment ancestor = this.lastSegment.iterator().next();
            String destinationTUIDString = "";
            if (ancestor != null) {
                destinationTUIDString = ancestor.toString(segmentSeperator) + segmentSeperator;
            }
            destinationTUIDString += newLastSegmentString;
            moveLastSegment(destinationTUIDString);
        } else {
            throw new IllegalArgumentException("The last segment '" + this.lastSegment + "' of the TUID '" + this
                    + "' cannot be renamed to '" + newLastSegmentString
                    + "' because this String contains the TUID separator '" + segmentSeperator + "'!");
        }
    }

    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged. If the specified destination is identical to
     * 
     * @param fullDestinationTUIDString
     *            the full TUID string of the move destination
     */
    public void moveLastSegment(final String fullDestinationTUIDString) {
        TUID fullDestinationTUID = getInstance(fullDestinationTUIDString);
        moveLastSegment(fullDestinationTUID);
    }

    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged. If the specified destination is identical to
     * 
     * @param fullDestinationTUID
     *            the full TUID of the move destination
     */
    public void moveLastSegment(final TUID fullDestinationTUID) {
        Collection<Pair<ForwardHashedBackwardLinkedTree<String>.Segment,ForwardHashedBackwardLinkedTree<String>.Segment>> updatedSegmentPairs = SEGMENTS
                .mergeSegmentIntoAnother(this.lastSegment, fullDestinationTUID.lastSegment);
        // remove the entry for all old last segments that have the destinationTUID as real prefix
        // as a result new requests for these TUIDs will return the TUIDs that have the destination
        // path as pefix
        for (Pair<ForwardHashedBackwardLinkedTree<String>.Segment,ForwardHashedBackwardLinkedTree<String>.Segment> updatedSegmentPair : updatedSegmentPairs) {
        	Segment oldSegment = updatedSegmentPair.getFirst();
            Segment newSegment = updatedSegmentPair.getSecond();
            TUID tuid = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(oldSegment);
            tuid.lastSegment = newSegment;
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(oldSegment);
        }
    }
    
    private int findCommonPrefixSegmentCount(final TUID otherTUID) {
        String thisTUIDString = this.toString();
        String otherTUIDString = otherTUID.toString();
        String commonPrefix = Strings.commonPrefix(thisTUIDString, otherTUIDString);
        String[] commonPrefixSegments = commonPrefix.split(VitruviusConstants.getTUIDSegmentSeperator());
        int commonPrefixSegmentCount = commonPrefixSegments.length;
        if (!commonPrefix.endsWith(VitruviusConstants.getTUIDSegmentSeperator())) {
            --commonPrefixSegmentCount;
        }
        return Math.max(0, commonPrefixSegmentCount);
    }
    
    private int findCommonSuffixSegmentCount(final TUID otherTUID) {
        String thisTUIDString = this.toString();
        String otherTUIDString = otherTUID.toString();
        String commonSuffix = Strings.commonSuffix(thisTUIDString, otherTUIDString);
        String[] commonPrefixSegments = commonSuffix.split(VitruviusConstants.getTUIDSegmentSeperator());
        int commonSuffixSegmentCount = commonPrefixSegments.length;
        return Math.max(0, commonSuffixSegmentCount - 1);
    }
    
    private static String[] getSegments(TUID tuid) {
        return tuid.toString().split(VitruviusConstants.getTUIDSegmentSeperator());
    }
    
    private int getSegmentCount() {
        return getSegments(this).length;
    }
    
    private int getCommonPrefixSegmentsCountForUpdate(final TUID newTUID) {
        int oldSegmentCount = getSegmentCount();
        int newSegmentCount = newTUID.getSegmentCount();
        
        if (oldSegmentCount != newSegmentCount) {
            throw new IllegalArgumentException("Cannot update the TUID " + this + " because the new TUID " + newTUID + " has a different number of segments!");
        }
        
        int commonPrefixSegmentsCount = findCommonPrefixSegmentCount(newTUID);
        int commonSuffixSegmentsCount = findCommonSuffixSegmentCount(newTUID);
        int commonSegmentsCount = commonPrefixSegmentsCount + commonSuffixSegmentsCount;
        
        if (commonSegmentsCount + 1 != oldSegmentCount || commonSegmentsCount == 0) {
            throw new IllegalArgumentException("Cannot update the TUID " + this + " because the new TUID " + newTUID + " differs in more than one segment!");
        }
        
        return commonPrefixSegmentsCount;
    }
    
    private static TUID getSlicedTUID(TUID tuid, int numberOfSegments) {
        String[] segments = getSegments(tuid);
        String[] segmentsSlice = Arrays.copyOfRange(segments, 0, numberOfSegments);
        TUID slicedTUID = TUID.getInstance(StringUtils.join(segmentsSlice, VitruviusConstants.getTUIDSegmentSeperator()));
        return slicedTUID;
    }
    
    public TUID getOldTUIDForUpdate(final TUID newTUID) {
        int commonPrefixSegmentsCount = getCommonPrefixSegmentsCountForUpdate(newTUID);
        return getSlicedTUID(this, commonPrefixSegmentsCount + 1);
    }
    
    public TUID getNewTUIDForUpdate(final TUID newTUID) {
        int commonPrefixSegmentsCount = getCommonPrefixSegmentsCountForUpdate(newTUID);
        return getSlicedTUID(newTUID, commonPrefixSegmentsCount + 1);
    }
    
    public void updateSingleSegment(final TUID newTUID) {
        int commonPrefixSegmentsCount = getCommonPrefixSegmentsCountForUpdate(newTUID);

        String[] thisSegments = getSegments(this);
        String[] newSegments = getSegments(newTUID);
        String[] commonPrefixWithOldLastSegment = Arrays.copyOfRange(thisSegments, 0, commonPrefixSegmentsCount + 1);
        TUID tuidWithOldLastSegment = TUID.getInstance(StringUtils.join(commonPrefixWithOldLastSegment, VitruviusConstants.getTUIDSegmentSeperator()));
        tuidWithOldLastSegment.renameLastSegment(newSegments[commonPrefixSegmentsCount]);
        // enjoy the side-effect of TUID: the right segment of this and newTUID will be changed too
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

    @Override
    public int hashCode() {
        return 31 + lastSegment.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TUID)) {
            return false;
        }
        return lastSegment == ((TUID)obj).lastSegment;
    }

    

    // FIXME generate or write new hashCode (also for last segment)
}
