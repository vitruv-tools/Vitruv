package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ForwardHashedBackwardLinkedTree<T> {
    private RecursiveMap<T, Segment> recursiveMap = new RecursiveHashMap<T, Segment>(new SegmentCreatorAndLinker());

    public class Segment implements Iterable<Segment> {
        private T value;
        private Segment ancestor;

        protected Segment(final T value) {
            this.value = value;
        }

        private Segment getAncestor() {
            return this.ancestor;
        }

        protected void setAncestor(final Segment ancestor) {
            this.ancestor = ancestor;
        }

        public boolean hasAncestor() {
            return this.ancestor != null;
        }

        @Override
        public String toString() {
            return toString("");
        }

        public String toString(final String separator) {
            if (!hasAncestor()) {
                return "" + this.value; // avoid NPE for value.toString()
            } else {
                return this.ancestor.toString(separator) + separator + this.value;
            }
        }

        public List<T> toValueList() {
            List<T> valueList = new LinkedList<T>();
            toValueList(valueList);
            return valueList;
        }

        private void toValueList(final List<T> valueList) {
            if (hasAncestor()) {
                getAncestor().toValueList(valueList);
            }
            valueList.add(this.value);
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
                    throw new UnsupportedOperationException(
                            "ForwardHashedBackwardLinkedTree<T>.Segment is not designed for deletion!");
                }
            };
        }
    }

    private class SegmentCreatorAndLinker implements ValueCreatorAndLinker<T, Segment> {

        @Override
        public Segment createNewValueForKey(final T key, final Segment valueForNextKey) {
            if (key != null) {
                Segment newSegment = new Segment(key);
                linkSubsequentValuesIfNecessary(newSegment, valueForNextKey);
                return newSegment;
            } else {
                throw new IllegalArgumentException("Cannot create a new value for the null key and the '"
                        + valueForNextKey + "' value for the next key!");
            }
        }

        @Override
        public void linkSubsequentValuesIfNecessary(final Segment firstValue, final Segment secondValue) {
            if (secondValue != null) {
                if (!secondValue.hasAncestor()) {
                    secondValue.setAncestor(firstValue);
                } else {
                    if (secondValue.getAncestor() != firstValue) {
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

    public void changeSegmentValue(final Segment segmentToChange, final T newSegmentValue) {
        // TODO If this is not fast enough because of deep trees, then add a link from segments to
        // the RecursiveMap that they are contained in and use it to change the value in O(1)
        // instead of O(|treeDepth|)
        List<T> currentValueList = segmentToChange.toValueList();
        this.recursiveMap.updateLeafKey(segmentToChange, currentValueList, newSegmentValue);
    }
}
