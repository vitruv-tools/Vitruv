package edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.traversal.util;

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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * A class to be used or extended by clients to compare variants of EObjects that is ensuring that
 * values of unordered references are considered equal if they contain the same elements in
 * different order. The implementation is based on {@link ReusableEqualityHelper}.<br/>
 * <br/>
 * <b>Attention</b>: This implementation should be optimised for performance before it is used for
 * comparison of models that contain huge lists as the algorithm runs in asymptotic quadratic time!
 *
 * @see ReusableEqualityHelper
 *
 * @author Max E. Kramer
 */
public class UnorderedReferencesRespectingEqualityHelper extends ReusableEqualityHelper {
    /** The recommended unique identifier for serialising. */
    private static final long serialVersionUID = 0L;

    /**
     * A re-implementation of
     * {@link org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#haveEqualReference(EObject, EObject, EReference)
     * EqualityHelper.haveEqualReference(EObject, EObject, EReference)} that considers values of
     * unordered references as equal if they contain the same elements in different order.<br/>
     * <br/>
     * An explanation for the fact that the implementation of
     * {@link org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper EcoreUtil.EqualityHelper} behaves
     * differently can be found at <a
     * href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=369661">bugs
     * .eclipse.org/bugs/show_bug.cgi?id=369661)</a>.<br/>
     * <br/>
     * <b>Attention</b>: This implementation should be optimised for performance before it is used
     * for comparison of models that contain huge lists as the algorithm runs in asymptotic
     * quadratic time!
     *
     * @param eObject1
     *            an EObject
     * @param eObject2
     *            an EObject
     * @param reference
     *            a reference present in the given eObjects
     * @return whether the given values for the given reference of the given eObjects are equal
     */
    @SuppressWarnings("unchecked")
    @Override
    protected boolean haveEqualReference(final EObject eObject1, final EObject eObject2, final EReference reference) {
        if (!reference.isMany() || reference.isOrdered()) {
            return super.haveEqualReference(eObject1, eObject2, reference);
        } else {
            Object value1 = eObject1.eGet(reference);
            Object value2 = eObject2.eGet(reference);
            // now we know that the reference is an unordered list
            return equalsIgnoreOrder((List<EObject>) value1, (List<EObject>) value2);
        }
    }

    /**
     * @param list1
     *            a list of EObjects
     * @param list2
     *            a list of EObjects
     * @return whether the given lists contain pair-wise equal elements while ignoring the order.
     */
    private boolean equalsIgnoreOrder(final List<EObject> list1, final List<EObject> list2) {
        int size1 = list1.size();
        if (size1 != list2.size()) {
            return false;
        } else if (size1 == 0) {
            return true;
        } else if (size1 == 1) {
            return equals(list1.get(0), list2.get(0));
        }
        // RATIONALE MK we cannot avoid comparing "every" element of list1 with "every" element of
        // list2
        // but we can remove equal elements to speed up the comparison
        // as long as comparison is more expensive than the creation of two list copies this pays
        // off
        // FIXME MK optimise order ignoring list comparison algorithm (runs in asymptotic quadratic
        // time)
        List<EObject> list1Copy = new LinkedList<EObject>(list1);
        List<EObject> list2Copy = new LinkedList<EObject>(list2);
        Iterator<EObject> list1Iterator = list1Copy.iterator();
        while (list1Iterator.hasNext() && !list2Copy.isEmpty()) {
            EObject list1Element = list1Iterator.next();
            boolean matched = false;
            Iterator<EObject> list2Iterator = list2Copy.iterator();
            while (list2Iterator.hasNext() && !matched) {
                EObject list2Element = list2Iterator.next();
                matched = equals(list1Element, list2Element);
            }
            if (matched) {
                list1Iterator.remove();
                list2Iterator.remove();
            } else {
                return false;
            }
        }
        return true;
    }
}
