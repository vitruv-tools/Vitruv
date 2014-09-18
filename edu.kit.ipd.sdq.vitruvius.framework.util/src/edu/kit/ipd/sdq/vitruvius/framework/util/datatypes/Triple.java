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
 * A 3-tuple (also called triplet).
 *
 * @param <A>
 *            the type of the first element
 * @param <B>
 *            the type of the second element
 * @param <C>
 *            the type of the third element
 * @author Max E. Kramer
 */
public class Triple<A, B, C> extends Pair<A, B> {
    /** The third element. */
    private final C third;

    /**
     * Constructs a new Triple using the given first, second and third element.
     *
     * @param first
     *            the first element
     * @param second
     *            the second element
     * @param third
     *            the third element
     */
    public Triple(final A first, final B second, final C third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public String toString() {
        return "Triple [first=" + this.getFirst() + ", second=" + this.getSecond() + ", third=" + this.getThird() + "]";
    }

    /**
     * @return the third element
     */
    public C getThird() {
        return this.third;
    }

    @Override
    public int hashCode() {
        final int firstAndSecond = super.hashCode();
        final int third = this.getThird() == null ? 0 : this.getThird().hashCode();

        return firstAndSecond + (2 * 37 * third);
    }
}
