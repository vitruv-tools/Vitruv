/*******************************************************************************
 * Copyright (c) 2012 University of Luxembourg and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Max E. Kramer - initial API and implementation
 ******************************************************************************/

package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

/**
 * A 2-tuple (also called double).
 *
 * @param <A>
 *            the type of the first element
 * @param <B>
 *            the type of the second element
 * @author Max E. Kramer
 */
public class Pair<A, B> {
    /** The first element. */
    private final A first;
    /** The second element. */
    private final B second;

    /**
     * Constructs a new Pair using the given first and second element.
     *
     * @param first
     *            the first element
     * @param second
     *            the second element
     */
    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair [first=" + this.getFirst() + ", second=" + this.getSecond() + "]";
    }

    /**
     * @return the first element
     */
    public A getFirst() {
        return this.first;
    }

    /**
     * @return the second element
     */
    public B getSecond() {
        return this.second;
    }

    /**
     * Override equals from object. Copied from
     * http://stackoverflow.com/questions/1628718/java-type-safety-generics-equals
     */
    @Override
    public boolean equals(final Object otherObj) {
        if (null == otherObj || !(otherObj instanceof Pair<?, ?>)) {
            return false;
        }
        final Pair<?, ?> otherPair = (Pair<?, ?>) otherObj;
        return this.equal(this.getFirst(), otherPair.getFirst()) && this.equal(this.getSecond(), this.getSecond());
    }

    private boolean equal(final Object o1, final Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    /**
     * Override hashCode from object. Copied from
     * http://stackoverflow.com/questions/1628718/java-type-safety-generics-equals
     */
    @Override
    public int hashCode() {
        final int first = this.getFirst() == null ? 0 : this.getFirst().hashCode();
        final int second = this.getSecond() == null ? 0 : this.getSecond().hashCode();

        return first + (37 * second);
    }
}
