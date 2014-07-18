package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashMap;
import java.util.Map;

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
    private TUID(final String[] splitTUIDString) {
        this.lastSegment = SEGMENTS.add(splitTUIDString);
    }

    public TUID(final String[] splitTUIDString, final ForwardHashedBackwardLinkedTree<String>.Segment prefix) {
        this.lastSegment = SEGMENTS.prepend(splitTUIDString, prefix);
    }

    public static synchronized TUID getInstance(final String tuidString) {
        if (tuidString == null) {
            throw new RuntimeException("The null string is no TUID!");
        } else {
            String[] splitTUIDString = split(tuidString);
            ForwardHashedBackwardLinkedTree<String>.Segment lastSegmentOrPrefix = SEGMENTS
                    .getMaximalPrefix(splitTUIDString);
            TUID instance;
            if (lastSegmentOrPrefix == null) {
                // neither the specified tuidString nor a prefix of it was mapped so far
                instance = new TUID(splitTUIDString);
            } else if (lastSegmentOrPrefix.toString().equals(tuidString)) {
                // the complete specified tuidString was already mapped
                instance = LAST_SEGMENT_2_TUID_INSTANCES_MAP.get(lastSegmentOrPrefix);
                if (instance == null) {
                    throw new RuntimeException("A TUID instance for the last segment '" + lastSegmentOrPrefix
                            + "' should already have been mapped for the tuidString '" + tuidString + "'!");
                } else {
                    return instance;
                }
            } else {
                // a real prefix of the specified tuidString was already mapped (but not the
                // complete tuidString)
                instance = new TUID(splitTUIDString, lastSegmentOrPrefix);
            }
            LAST_SEGMENT_2_TUID_INSTANCES_MAP.put(instance.getLastSegment(), instance);
            return instance;
        }
    }

    private static String[] split(final String tuidString) {
        String seperator = VitruviusConstants.getTUIDSegmentSeperator();
        return tuidString.split(seperator);
    }

    protected ForwardHashedBackwardLinkedTree<String>.Segment getLastSegment() {
        return this.lastSegment;
    }

    @Override
    public String toString() {
        return this.lastSegment.toString();
    }
}
