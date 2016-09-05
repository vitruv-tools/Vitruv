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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;

/**
 * An class to be used or extended by clients to compare EObjects that can be used for multiple
 * comparisons in order to avoid comparing the same object trees twice. The implementation is based
 * on {@link org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper EcoreUtil.EqualityHelper}.
 *
 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper EcoreUtil.EqualityHelper
 *
 * @author Max E. Kramer
 */
public class ReusableEqualityHelper extends EqualityHelper {
    /** The recommended unique identifier for serialising. */
    private static final long serialVersionUID = 0L;

    /** The map storing for each eObject the set of equivalent eObjects. */
    private final Map<EObject, Set<EObject>> eObject2EqualEObjectsMap = new HashMap<EObject, Set<EObject>>();

    /**
     * A re-implementation of
     * {@link org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(EObject, EObject)
     * EcoreUtil.EqualityHelper.equals(EObject, EObject)}that makes instances ready for reuse for
     * multiple comparisons.<br/>
     * <br/>
     * As {@link org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper EqualityHelper} only uses
     * methods from the inherited HashMap in the method equals(EObject, EObject) it is sufficient to
     * override this method to make EqualityHelper helper reusable for multiple comparisons.<br/>
     * <br/>
     * The developers of EcoreUtil.EqualityHelper assumed (as stated in the JavaDoc) that an EObject
     * is equal to <i>at most one</i> EObject because they assumed that for each new comparison a
     * new Helper will be used. The corresponding discussion can be found at <a
     * href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=369653"
     * >bugs.eclipse.org/bugs/show_bug.cgi?id=369653</a>.<br/>
     * <br/>
     * The bug that an entry is never removed in cases where this method returns {@code false} was
     * fixed in a new release: <a
     * href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=369651">bugs.eclipse
     * .org/bugs/show_bug.cgi?id=369651</a>.
     *
     * @param eObject1
     *            an EObject
     * @param eObject2
     *            an EObject
     * @return whether the given eObjects are equal
     */
    @Override
    public boolean equals(final EObject eObject1, final EObject eObject2) {
        // UNCHANGED CODE from EcoreUtil.EqualityHelper except for five marked modification

        // If the first object is null, the second object must be null.
        //
        if (eObject1 == null) {
            return eObject2 == null;
        }

        // We know the first object isn't null, so if the second one is, it can't be equal.
        //
        if (eObject2 == null) {
            return false;
        }

        // Both eObject1 and eObject2 are not null.
        // If eObject1 has been compared already...
        //
        /*
         * ### BEGIN MK EQUALITYHELPER MODIFICATION 1 / 5 ###
         */
        // Object eObject1MappedValue = get(eObject1);
        // if (eObject1MappedValue != null)
        // {
        // // Then eObject2 must be that previous match.
        // //
        // return eObject1MappedValue == eObject2;
        // }
        //
        // // If eObject2 has been compared already...
        // //
        // Object eObject2MappedValue = get(eObject2);
        // if (eObject2MappedValue != null)
        // {
        // // Then eObject1 must be that match.
        // //
        // return eObject2MappedValue == eObject1;
        // }
        Set<EObject> equalEObjects = eObject2EqualEObjectsMap.get(eObject1);
        if (equalEObjects == null) {
            equalEObjects = eObject2EqualEObjectsMap.get(eObject2);
            if (equalEObjects != null) {
                if (equalEObjects.contains(eObject1)) {
                    return true;
                }
            }
        } else {
            if (equalEObjects.contains(eObject2)) {
                return true;
            }
        }
        /*
         * ### END MK EQUALITYHELPER MODIFICATION 1 / 5 ###
         */
        // Neither eObject1 nor eObject2 have been compared yet.

        // If eObject1 and eObject2 are the same instance...
        //
        if (eObject1 == eObject2) {
            // Match them and return true.
            //
            /*
             * ### BEGIN MK EQUALITYHELPER MODIFICATION 2 / 5 ###
             */
            // put(eObject1, eObject2);
            // put(eObject2, eObject1);
            putReusablyAndSymmetrically(eObject1, eObject2);
            putReusablyAndSymmetrically(eObject2, eObject1);
            /*
             * ### END MK EQUALITYHELPER MODIFICATION 2 / 5 ###
             */
            return true;
        }

        // If eObject1 is a proxy...
        //
        if (eObject1.eIsProxy()) {
            // Then the other object must be a proxy with the same URI.
            //
            if (((InternalEObject) eObject1).eProxyURI().equals(((InternalEObject) eObject2).eProxyURI())) {
                /*
                 * ### BEGIN MK EQUALITYHELPER MODIFICATION 3 / 5 ###
                 */
                // put(eObject1, eObject2);
                // put(eObject2, eObject1);
                putReusablyAndSymmetrically(eObject1, eObject2);
                putReusablyAndSymmetrically(eObject2, eObject1);
                /*
                 * ### END MK EQUALITYHELPER MODIFICATION 3 / 5 ###
                 */
                return true;
            } else {
                return false;
            }

        } else if (eObject2.eIsProxy()) {
            // If eObject1 isn't a proxy but eObject2 is, they can't be equal.
            //
            return false;
        }

        // If they don't have the same class, they can't be equal.
        //
        EClass eClass = eObject1.eClass();
        if (eClass != eObject2.eClass()) {
            return false;
        }

        // Assume from now on that they match.
        //
        /*
         * ### BEGIN MK EQUALITYHELPER MODIFICATION 4 / 5 ###
         */
        // put(eObject1, eObject2);
        // put(eObject2, eObject1);
        putReusablyAndSymmetrically(eObject1, eObject2);
        putReusablyAndSymmetrically(eObject2, eObject1);
        /*
         * ### END MK EQUALITYHELPER MODIFICATION 4 / 5 ###
         */

        // Check all the values.
        //
        for (int i = 0, size = eClass.getFeatureCount(); i < size; ++i) {
            // Ignore derived features.
            //
            EStructuralFeature feature = eClass.getEStructuralFeature(i);
            if (!feature.isDerived()) {
                if (!haveEqualFeature(eObject1, eObject2, feature)) {
                    /*
                     * ### BEGIN MK EQUALITYHELPER MODIFICATION 5 / 5 ###
                     */
                    removeReusablyAndSymmetrically(eObject1, eObject2);
                    /*
                     * ### END MK EQUALITYHELPER MODIFICATION 5 / 5 ###
                     */
                    return false;
                }
            }
        }

        // There's no reason they aren't equal, so they are.
        //
        return true;
    }

    /**
     * Registers two symmetric equivalence mappings for the given eObjects.
     *
     * @param eObject1
     *            an EObject
     * @param eObject2
     *            an EObject
     */
    private void putReusablyAndSymmetrically(final EObject eObject1, final EObject eObject2) {
        putReusably(eObject1, eObject2);
        putReusably(eObject2, eObject1);
    }

    /**
     * Registers an equivalence mapping for the given eObjects that can be used for later
     * comparisons.
     *
     * @param key
     *            an EObject
     * @param value
     *            an EObject
     */
    private void putReusably(final EObject key, final EObject value) {
        Set<EObject> equalEObjects = eObject2EqualEObjectsMap.get(key);
        if (equalEObjects == null) {
            equalEObjects = new HashSet<EObject>();
            eObject2EqualEObjectsMap.put(key, equalEObjects);
        }
        equalEObjects.add(value);
    }

    /**
     * Removes the two symmetric equivalence mappings for the given eObjects.
     *
     * @param eObject1
     *            an EObject
     * @param eObject2
     *            an EObject
     */
    private void removeReusablyAndSymmetrically(final EObject eObject1, final EObject eObject2) {
        removeReusably(eObject1, eObject2);
        removeReusably(eObject2, eObject1);
    }

    /**
     * Removes the equivalence mapping for the given eObjects so that it is not used for later
     * comparisons.
     *
     * @param key
     *            an EObject
     * @param value
     *            an EObject
     */
    private void removeReusably(final EObject key, final EObject value) {
        Set<EObject> equalEObjects = eObject2EqualEObjectsMap.get(key);
        if (equalEObjects == null) {
            throw new RuntimeException("Cannot remove equality mapping from '" + key + "' to '" + value
                    + "' as it does not exist!");
        } else {
            equalEObjects.remove(value);
        }
    }
}
