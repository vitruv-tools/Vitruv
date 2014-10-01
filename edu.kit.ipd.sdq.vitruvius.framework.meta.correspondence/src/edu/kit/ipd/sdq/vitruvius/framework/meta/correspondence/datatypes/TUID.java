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

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
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
    
    /**
     * Changes the given tuid so that it points to the given newLastSegment.<br/>
     * <b>ATTENTION: This changes the hashcode of the given tuid!</b>
     * @param tuid
     * @param newLastSegment
     */
    private static synchronized void updateInstance(TUID tuid, ForwardHashedBackwardLinkedTree<String>.Segment newLastSegment) {
    	ForwardHashedBackwardLinkedTree<String>.Segment oldSegment = tuid.lastSegment;
    	tuid.lastSegment = newLastSegment;
        LAST_SEGMENT_2_TUID_INSTANCES_MAP.remove(oldSegment);
    }

    private static TUID getInstance(final String tuidString, final boolean recursively) {
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
            lastSegmentOrPrefix = instance.getLastSegment();
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(lastSegmentOrPrefix, instance);
            // also create TUIDs for all prefixes of the specified tuidString and register them
            final Iterator<ForwardHashedBackwardLinkedTree<String>.Segment> segmentIterator = lastSegmentOrPrefix
                    .iterator();
            ForwardHashedBackwardLinkedTree<String>.Segment pivot;
            while (segmentIterator.hasNext()) {
                pivot = segmentIterator.next();
                final TUID subInstance = getInstance(pivot.toString(VitruviusConstants.getTUIDSegmentSeperator()), true);
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
     * Returns the minimal suffix of the TUID, i.e. a String representation of the last segment.
     *
     * @return
     */
    public String getMinimalSuffix() {
        return this.getLastSegment().toString();
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
    	renameLastSegment(newLastSegmentString, null, null);
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
    public void renameLastSegment(final String newLastSegmentString, BeforeHashCodeUpdateLambda before, AfterHashCodeUpdateLambda after) {
        final String segmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
        final boolean containsSeparator = newLastSegmentString.indexOf(segmentSeperator) != -1;
        if (!containsSeparator) {
            final ForwardHashedBackwardLinkedTree<String>.Segment ancestor = this.lastSegment.iterator().next();
            String destinationTUIDString = "";
            if (ancestor != null) {
                destinationTUIDString = ancestor.toString(segmentSeperator) + segmentSeperator;
            }
            destinationTUIDString += newLastSegmentString;
            this.moveLastSegment(destinationTUIDString, before, after);
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
    	moveLastSegment(fullDestinationTUIDString, null, null);
    }
    
    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged. If the specified destination is identical to
     *
     * @param fullDestinationTUIDString
     *            the full TUID string of the move destination
     */
    public void moveLastSegment(final String fullDestinationTUIDString, BeforeHashCodeUpdateLambda before, AfterHashCodeUpdateLambda after) {
        final TUID fullDestinationTUID = getInstance(fullDestinationTUIDString);
        this.moveLastSegment(fullDestinationTUID, before, after);
    }

    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged. If the specified destination is identical to
     *
     * @param fullDestinationTUID
     *            the full TUID of the move destination
     * @return 
     */
    public void moveLastSegment(final TUID fullDestinationTUID) {
       moveLastSegment(fullDestinationTUID, null, null);
    }
    
    /**
     * Moves the last segment of this TUID instance to the specified destination. If the destination
     * already exists, then all depending TUIDs of this instance and the destination instance are
     * merged. If the specified destination is identical to
     *
     * @param fullDestinationTUID
     *            the full TUID of the move destination
     * @return 
     */
	public void moveLastSegment(final TUID fullDestinationTUID, BeforeHashCodeUpdateLambda before, AfterHashCodeUpdateLambda after) {
    	Collection<Pair<ForwardHashedBackwardLinkedTree<String>.Segment, ForwardHashedBackwardLinkedTree<String>.Segment>> segmentPairs = SEGMENTS.mergeSegmentIntoAnother(this.lastSegment, fullDestinationTUID.lastSegment);
        	for (Pair<ForwardHashedBackwardLinkedTree<String>.Segment, ForwardHashedBackwardLinkedTree<String>.Segment> segmentPair : segmentPairs) {
        		ForwardHashedBackwardLinkedTree<String>.Segment oldSegment = segmentPair.getFirst();
            	TUID tuid = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(oldSegment);
            	ForwardHashedBackwardLinkedTree<String>.Segment newSegment = segmentPair.getSecond();
            	Triple<TUID, Set<Correspondence>, Set<TUID>> removedMapEntries = null;
            	if (before != null) {
            		removedMapEntries = before.performPreAction(tuid, newSegment);
            	}
            	// this update changes the hashcode of the given tuid
                TUID.updateInstance(tuid, newSegment);
            	if (after != null && removedMapEntries != null) {
            		after.performPostAction(removedMapEntries);
            	}
            }
    }

    private int findCommonPrefixSegmentCount(final TUID otherTUID) {
        final String thisTUIDString = this.toString();
        final String otherTUIDString = otherTUID.toString();
        final String commonPrefix = Strings.commonPrefix(thisTUIDString, otherTUIDString);
        final String[] commonPrefixSegments = commonPrefix.split(VitruviusConstants.getTUIDSegmentSeperator());
        int commonPrefixSegmentCount = commonPrefixSegments.length;
        if (!commonPrefix.endsWith(VitruviusConstants.getTUIDSegmentSeperator())) {
            --commonPrefixSegmentCount;
        }
        return Math.max(0, commonPrefixSegmentCount);
    }

    private int findCommonSuffixSegmentCount(final TUID otherTUID) {
        final String thisTUIDString = this.toString();
        final String otherTUIDString = otherTUID.toString();
        final String commonSuffix = Strings.commonSuffix(thisTUIDString, otherTUIDString);
        final String[] commonPrefixSegments = commonSuffix.split(VitruviusConstants.getTUIDSegmentSeperator());
        final int commonSuffixSegmentCount = commonPrefixSegments.length;
        return Math.max(0, commonSuffixSegmentCount - 1);
    }

    private static String[] getSegments(final TUID tuid) {
        return tuid.toString().split(VitruviusConstants.getTUIDSegmentSeperator());
    }

    private int getSegmentCount() {
        return getSegments(this).length;
    }

    private int getCommonPrefixSegmentsCountForUpdate(final TUID newTUID) {
        final int oldSegmentCount = this.getAndEnsureEqualSegmentCount(newTUID);

        final int commonPrefixSegmentsCount = this.findCommonPrefixSegmentCount(newTUID);
        final int commonSuffixSegmentsCount = this.findCommonSuffixSegmentCount(newTUID);
        final int commonSegmentsCount = commonPrefixSegmentsCount + commonSuffixSegmentsCount;

        if (commonSegmentsCount + 1 != oldSegmentCount || commonSegmentsCount == 0) {
            throw new IllegalArgumentException("Cannot update the TUID " + this + " because the new TUID " + newTUID
                    + " differs in more than one segment!");
        }

        return commonPrefixSegmentsCount;
    }

    public int getAndEnsureEqualSegmentCount(final TUID newTUID) {
        final int oldSegmentCount = this.getSegmentCount();

        final int newSegmentCount = newTUID.getSegmentCount();

        if (oldSegmentCount != newSegmentCount) {
            throw new IllegalArgumentException("Cannot update the TUID " + this + " because the new TUID " + newTUID
                    + " has a different number of segments!");
        }
        return oldSegmentCount;
    }

    private int getFistSegmentChange(final TUID newTUID) {
        final int segmentsCount = this.getAndEnsureEqualSegmentCount(newTUID);
        final int commonPrefixSegmentCount = this.findCommonPrefixSegmentCount(newTUID);
        if (commonPrefixSegmentCount + 1 > segmentsCount) {
            return -1;
        }
        return commonPrefixSegmentCount + 1;
    }

    private static TUID getSlicedTUID(final TUID tuid, final int numberOfSegments) {
        final String[] segments = getSegments(tuid);
        final String[] segmentsSlice = Arrays.copyOfRange(segments, 0, numberOfSegments);
        final TUID slicedTUID = TUID.getInstance(StringUtils.join(segmentsSlice,
                VitruviusConstants.getTUIDSegmentSeperator()));
        return slicedTUID;
    }

    public TUID getOldTUIDForUpdate(final TUID newTUID) {
        final int commonPrefixSegmentsCount = this.getCommonPrefixSegmentsCountForUpdate(newTUID);
        return getSlicedTUID(this, commonPrefixSegmentsCount + 1);
    }

    public TUID getNewTUIDForUpdate(final TUID newTUID) {
        final int commonPrefixSegmentsCount = this.getCommonPrefixSegmentsCountForUpdate(newTUID);
        return getSlicedTUID(newTUID, commonPrefixSegmentsCount + 1);
    }

    public void updateSingleSegment(final TUID newTUID) {
    	updateSingleSegment(newTUID, null, null);
    }
    
    public void updateSingleSegment(final TUID newTUID, BeforeHashCodeUpdateLambda before, AfterHashCodeUpdateLambda after) {
        final int commonPrefixSegmentsCount = this.getCommonPrefixSegmentsCountForUpdate(newTUID);

        final String[] thisSegments = getSegments(this);
        final String[] newSegments = getSegments(newTUID);
        final String[] commonPrefixWithOldLastSegment = Arrays.copyOfRange(thisSegments, 0,
                commonPrefixSegmentsCount + 1);
        final TUID tuidWithOldLastSegment = TUID.getInstance(StringUtils.join(commonPrefixWithOldLastSegment,
                VitruviusConstants.getTUIDSegmentSeperator()));
        tuidWithOldLastSegment.renameLastSegment(newSegments[commonPrefixSegmentsCount], before, after);
        // enjoy the side-effect of TUID: the right segment of this and newTUID will be changed too
    }
    
    /**
     * Updates multiple segments at once. It gets the first segment that changed and then updates
     * only the specific part of the TUID.
     *
     * @param newTUID
     */
    public void updateMultipleSegments(final TUID newTUID) {
    	updateMultipleSegments(newTUID, null, null);
    }

    /**
     * Updates multiple segments at once. It gets the first segment that changed and then updates
     * only the specific part of the TUID.
     *
     * @param newTUID
     */
    public void updateMultipleSegments(final TUID newTUID, BeforeHashCodeUpdateLambda before, AfterHashCodeUpdateLambda after) {
    	int firstSegmentChanged = this.getFistSegmentChange(newTUID);
        while (-1 != firstSegmentChanged) {
            final TUID tmpNewTUID = TUID.getSlicedTUID(newTUID, firstSegmentChanged);
            final TUID tmpOldTUID = TUID.getSlicedTUID(this, firstSegmentChanged);
            tmpOldTUID.updateSingleSegment(tmpNewTUID, before, after);
            if (this.toString().equals(newTUID.toString())) {
                break;
            }
            firstSegmentChanged = this.getFistSegmentChange(newTUID);
        }
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
            throw new IllegalStateException(treedTUIDStrings.size() + " TUIDs are in the segment tree ("
                    + treedTUIDStrings + ") but " + tuids.size() + " are mapped by their last segments (" + tuids
                    + ")!");
        }
        for (final TUID tuid : tuids) {
            final String tuidString = tuid.toString();
            if (!treedTUIDStrings.contains(tuidString)) {
                throw new IllegalStateException("The TUID '" + tuidString
                        + "' is mapped by its last segment but not in the tree!");
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 + this.lastSegment.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof TUID)) {
            return false;
        }
        return this.lastSegment == ((TUID) obj).lastSegment;
    }

    // FIXME generate or write new hashCode (also for last segment)
    
    public interface BeforeHashCodeUpdateLambda {
		Triple<TUID, Set<Correspondence>, Set<TUID>> performPreAction(TUID tuid,
				ForwardHashedBackwardLinkedTree<String>.Segment newSegment);
    }
    
    public interface AfterHashCodeUpdateLambda {
		void performPostAction(
				Triple<TUID, Set<Correspondence>, Set<TUID>> removedMapEntries);
    }
}
