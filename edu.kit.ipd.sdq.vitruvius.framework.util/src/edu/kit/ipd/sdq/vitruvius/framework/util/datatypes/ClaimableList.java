package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.List;

public interface ClaimableList<E> extends List<E> {
    /**
     * Returns the element at the specified position in this list and throws a {@link java.lang.RuntimeException} if the list contains no element at the specified position.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     * @throws RuntimeException if the list contains no element at the specified position
     * @see java.util.List#get(int)
     */
    E claimValueForIndex(int index);
    
    /**
     * Inserts the element at the specified position in this list with the
     * specified element. If the list previously contained a another element than the specified element, then a
     * {@link java.lang.RuntimeException} is thrown.
     *
     * @param index index of the element to insert
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @see java.util.List#set(int,E)
     */
    E setClaimingNullOrSameListed(int index, E element);
    
    /**
     * Returns <tt>true</tt> if this list contains an element at the specified
     * position.
     *
     * @param key index of the element for which presence is to be tested
     * @return <tt>true</tt> if this list contains an element at the specified position
     * @see java.util.Map#containsKey(K)
     */
     boolean containsElementAtPosition(int index);
}
