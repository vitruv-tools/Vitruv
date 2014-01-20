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

package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

/**
 * A 2-tuple (also called double).
 *
 * @param <A>
 *           the type of the first element
 * @param <B>
 *           the type of the second element
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
    *           the first element
    * @param second
    *           the second element
    */
   public Pair(final A first, final B second) {
      this.first = first;
      this.second = second;
   }

   @Override
   public String toString() {
      return "Pair [first=" + getFirst() + ", second=" + getSecond() + "]";
   }

   /**
    * @return the first element
    */
   public A getFirst() {
      return first;
   }

   /**
    * @return the second element
    */
   public B getSecond() {
      return second;
   }
}
