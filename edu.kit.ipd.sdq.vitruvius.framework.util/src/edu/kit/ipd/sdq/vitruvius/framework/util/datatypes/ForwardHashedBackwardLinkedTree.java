package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A data structure that stores for every value of type {@link T} a unique sequence of
 * {@link Segment segments} where every segment has a unique predecessor but possibly multiple
 * descendants. Combines hash maps to retrieve the segments in forward direction and a simple linked
 * list to retrieve segments in the backward direction.
 * 
 * @author kramerm
 * 
 * @param <T>
 */
public class ForwardHashedBackwardLinkedTree<T> {
    private RecursiveMap<T, Segment> recursiveMap = new RecursiveHashMap<T, Segment>(new SegmentCreatorAndLinker());

    /**
     * A segment of a backwards linked list that encapsulates a value of type {@link T}.
     * 
     * @author kramerm
     * 
     */
    public class Segment implements Iterable<Segment> {
        // note the private fields and methods of this nested class Segment are accessibly in the
        // nesting class ForwardHashedBackwardLinkedTree<T>
        private T value;
        private Segment ancestor;

        protected Segment(final T value) {
            this.setValue(value);
        }

        private T getValue() {
            return this.value;
        }
        
        public String getValueString() {
        	T value = getValue();
        	if (value == null) {
        		return "";
        	} else {
        		return value.toString();
        	}
        }

        private void setValue(final T value) {
            this.value = value;
        }

        private Segment getAncestor() {
            return this.ancestor;
        }

        protected void setAncestor(final Segment ancestor) {
            this.ancestor = ancestor;
        }

        public boolean hasAncestor() {
            return this.getAncestor() != null;
        }

        @Override
        public String toString() {
            return toString("");
        }

        /**
         * Returns a string representation of the backward linked list ending with this segment that
         * is separated by the specified {@link separator}.
         * 
         * @param separator
         *            the separator to be used between all segments
         * @return a string representation separated by the specified {@link separator}
         */
        public String toString(final String separator) {
            if (!hasAncestor()) {
                return "" + this.getValue(); // avoid NPE for value.toString()
            } else {
                return this.getAncestor().toString(separator) + separator + this.getValue();
            }
        }

        /**
         * Returns a (forward ordered) list of all values of the backward linked list ending with
         * this segment.
         * 
         * @return a list of all values of the linked list ending with this segment
         */
        public List<T> toValueList() {
            List<T> valueList = new LinkedList<T>();
            toValueList(valueList);
            return valueList;
        }

        private void toValueList(final List<T> valueList) {
            if (hasAncestor()) {
                getAncestor().toValueList(valueList);
            }
            valueList.add(this.getValue());
        }

        @Override
        public Iterator<Segment> iterator() {

            return new Iterator<Segment>() {
                private Segment current = Segment.this;

                @Override
                public boolean hasNext() {
                    return this.current.hasAncestor();
                }

                @Override
                public Segment next() {
                    return this.current = this.current.getAncestor();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException(Segment.class + " is not designed for deletion!");
                }
            };
        }
    }

    private class SegmentCreatorAndLinker implements ValueCreatorAndLinker<T, Segment> {

        @Override
        public Segment createNewValueForKey(final T key, final Segment valueForNextKey) {
            if (key != null) {
                Segment newSegment = new Segment(key);
                if (valueForNextKey != null) {
                    linkSubsequentValuesIfNecessary(newSegment, valueForNextKey);
                }
                return newSegment;
            } else {
                throw new IllegalArgumentException("Cannot create a new value for the null key and the '"
                        + valueForNextKey + "' value for the next key!");
            }
        }

        @Override
        public void linkSubsequentValuesIfNecessary(final Segment firstValue, final Segment secondValue) {
            boolean overrideExistingAncestor = false;
            linkSubsequentValues(firstValue, secondValue, overrideExistingAncestor);
        }

        @Override
        public void linkSubsequentValuesAndOverride(final Segment firstValue, final Segment secondValue) {
            boolean overrideExistingAncestor = true;
            linkSubsequentValues(firstValue, secondValue, overrideExistingAncestor);
        }

        private void linkSubsequentValues(final Segment firstValue, final Segment secondValue,
                final boolean overrideExistingAncestor) {
            if (secondValue == null || firstValue == null) {
                throw new IllegalArgumentException("Cannot link the second value '" + secondValue
                        + "' to the first value '" + firstValue + "' to make it a predecessor!");
            } else {
                if (overrideExistingAncestor || !secondValue.hasAncestor()) {
                    secondValue.setAncestor(firstValue);
                } else {
                    if (!overrideExistingAncestor && secondValue.getAncestor() != firstValue) {
                        throw new IllegalStateException("The segment '" + secondValue
                                + "' cannot be linked to the previous segment '" + firstValue
                                + "' because it is already linked to '" + secondValue.getAncestor() + "'!");
                    }
                }
            }
        }
    }

    /**
     * Returns the last segment that is currently in the tree and that represents the maximal prefix
     * for the specified segment values. If all values are already in the tree, then the last
     * segment pointing transitively to all previous values is returned. If even the first value is
     * not in the tree, then no prefix is in the tree and therefore {@link null} is returned.
     * 
     * @param segmentValues
     *            the sequence of segment values in correct order
     * @return the maximal prefix if existing, otherwise {@link null}
     */
    public Segment getMaximalPrefix(final List<T> segmentValues) {
        List<Segment> segmentsOfMaximalPrefix = this.recursiveMap.getValuesAsLongAsPossible(segmentValues);
        int prefixLength = segmentsOfMaximalPrefix.size();
        if (prefixLength == 0) {
            return null;
        } else {
            int indexOfLastSegment = prefixLength - 1;
            return segmentsOfMaximalPrefix.get(indexOfLastSegment);
        }
    }

    /**
     * Adds all the specified segment values that are not yet in the tree to the tree based on the
     * maximal prefix that is already in the tree and returns the segment for the last specified
     * value, which points transitively to all previous segments. If none of the specified segment
     * values are already in the tree, then all of them will be added. If all of the specified
     * segment values are already in the tree, then nothing will be changed and the segment for the
     * last specified value will be returned.
     * 
     * @param segmentValues
     *            the sequence of segment values in correct order
     * @return the segment for the last specified value
     */
    public Segment addNewSegmentsWhereNecessary(final List<T> segmentValues) {
        this.recursiveMap.putWhereNecessary(segmentValues);
        return this.recursiveMap.getLastValue(segmentValues);
    }

    /**
     * Merges the specified {@link origin} segment with the specified {@link destination} segment.
     * As a result all direct and indirect successors of {@link origin} become successors of
     * {@link destination}.
     * 
     * @param origin
     *            the origin segment with the successors to be merged
     * @param destination
     *            the destination segment to merge into
     * @return all direct and indirect successor segments that became obsolete because they existed
     *         already at the destination
     */
    public Collection<Pair<Segment, Segment>> mergeSegmentIntoAnother(final Segment origin, final Segment destination) {
        List<T> originValueList = origin.toValueList();
        List<T> destinationValueList = destination.toValueList();
        return this.recursiveMap.mergeLeafIntoAnother(origin, originValueList, destination, destinationValueList);
    }

    @Override
    public String toString() {
        return this.recursiveMap.toString();
    }

    /**
     * Returns all values that are stored in this data structure.
     * 
     * @return all values that are stored in this data structure
     */
    public Collection<Segment> values() {
        return this.recursiveMap.allValues();
    }
}
