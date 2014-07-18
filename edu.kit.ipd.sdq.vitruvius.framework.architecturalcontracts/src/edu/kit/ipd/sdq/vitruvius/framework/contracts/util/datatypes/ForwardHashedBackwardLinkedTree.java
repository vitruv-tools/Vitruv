package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

public class ForwardHashedBackwardLinkedTree<T> {

    public class Segment {
        private T value;
        private Segment ancestor;

        public Segment(final T value, final Segment ancestor) {
            this.value = value;
            this.ancestor = ancestor;
        }

        public boolean isFirstSegment() {
            return this.ancestor == null;
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
    public Segment getMaximalPrefix(final T[] segmentValues) {
        // TODO Auto-generated method stub
        return null;
    }

    public Segment add(final T[] segmentValues) {
        // TODO Auto-generated method stub
        return null;
    }

    public Segment prepend(final String[] splitTUIDString, final Segment prefix) {
        // TODO Auto-generated method stub
        return null;
    }

    //
    // @SuppressWarnings("rawtypes")
    // public static class FirstEntry extends Entry {
    // private static final FirstEntry INSTANCE = new FirstEntry();
    //
    // @SuppressWarnings("unchecked")
    // private FirstEntry() {
    // super(null, null);
    // }
    //
    // public static FirstEntry getInstance() {
    // return INSTANCE;
    // }
    // }
}
