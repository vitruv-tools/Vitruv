/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.IObjectChange.FeatureChangeObjectChange;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.IObjectChange.ObjectChangeVisitor;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.tools.MapUtils;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;

/**
 * Utility methods for the changedescription2change package.
 */
final class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class);

    private Util() {
    }

    /**
     * Creates a map associating each {@link EObject} affected by changes in <code>changes</code>
     * with a list of changes affecting it.
     * 
     * @param changes
     *            A collection of {@link IObjectChange} objects.
     * @return A new map associating each {@link EObject} affected by changes in
     *         <code>changes</code> with a list of changes affecting it.
     */
    public static Map<EObject, List<IObjectChange>> getAffectedObjectMap(Collection<IObjectChange> changes) {
        Map<EObject, List<IObjectChange>> result = new HashMap<>();

        for (IObjectChange change : changes) {
            if (!result.containsKey(change.getAffectedObject())) {
                result.put(change.getAffectedObject(), new ArrayList<IObjectChange>());
            }
            result.get(change.getAffectedObject()).add(change);
        }

        return result;
    }

    private static boolean isContainmentReferenceChange(FeatureChangeObjectChange change) {
        if ((change.getChange().getFeature() instanceof EReference)) {
            EReference ref = (EReference) change.getChange().getFeature();
            return ref.isContainment();
        }
        return false;
    }

    private static void collectContainments(FeatureChangeObjectChange change,
            Map<EObject, Set<EObject>> resultCollector, Collection<EObject> remainingObjects) {
        if (!isContainmentReferenceChange(change)) {
            return;
        }

        EObject container = change.getAffectedObject();

        // If o is a list change, scan through the list changes for
        // remaining objects.
        if (change.getChange().getFeature().isMany()) {
            // Scan through all changes to all containment lists.
            for (ListChange lc : change.getChange().getListChanges()) {
                for (EObject refValue : lc.getReferenceValues()) {
                    // If the reference points to a remaining object,
                    // we've found the container of that object.
                    if (remainingObjects.contains(refValue)) {
                        MapUtils.addToSetMap(container, refValue, resultCollector);
                        remainingObjects.remove(refValue);
                    }
                }
            }
        } else {
            MapUtils.addToSetMap(change.getAffectedObject(), change.getChange().getReferenceValue(), resultCollector);
            remainingObjects.remove(change.getChange().getReferenceValue());
        }
    }

    private static void collectContainments(EChangeCompoundObjectChange change,
            Map<EObject, Set<EObject>> resultCollector, Collection<EObject> remainingObjects) {

        for (EChange eChange : change.getChanges()) {
            EReference affectedFeature;
            EObject createdObject;
            EObject affectedObject;

            if (eChange instanceof CreateNonRootEObjectInList<?>) {
                CreateNonRootEObjectInList<?> listChange = (CreateNonRootEObjectInList<?>) eChange;
                affectedFeature = listChange.getAffectedFeature();
                createdObject = listChange.getNewValue();
                affectedObject = listChange.getNewAffectedEObject();
            } else if (eChange instanceof CreateNonRootEObjectSingle<?>) {
                CreateNonRootEObjectSingle<?> singleChange = (CreateNonRootEObjectSingle<?>) eChange;
                affectedFeature = singleChange.getAffectedFeature();
                createdObject = singleChange.getNewValue();
                affectedObject = singleChange.getNewAffectedEObject();
            } else {
                continue;
            }

            if (affectedFeature.isContainment() && remainingObjects.contains(createdObject)) {
                MapUtils.addToSetMap(affectedObject, createdObject, resultCollector);
                remainingObjects.remove(createdObject);
            }
        }
    }

    /**
     * Creates a map associating each object in <code>objects</code> with the set of objects it
     * contains which are referenced as updates in <code>contChanges</code>.
     * 
     * @param objects
     *            A collection of {@link EObject} objects (as described above).
     * @param contChanges
     *            A collection of {@link IObjectChange} objects (as described above).
     * @return A map associating each object in <code>objects</code> with the set of objects it
     *         contains which are referenced as updates in <code>contChanges</code>.
     */
    public static Map<EObject, Set<EObject>> getContainments(Collection<EObject> objects,
            Collection<IObjectChange> contChanges) {
        final Map<EObject, Set<EObject>> result = new HashMap<>();

        // The set of objects for which no containment has been found yet.
        final Set<EObject> remainingObjects = new HashSet<>(objects);

        for (IObjectChange o : contChanges) {
            o.accept(new ObjectChangeVisitor() {
                @Override
                public void visit(IObjectChange change) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void visit(FeatureChangeObjectChange change) {
                    collectContainments(change, result, remainingObjects);
                }

                @Override
                public void visit(EChangeCompoundObjectChange change) {
                    collectContainments(change, result, remainingObjects);
                }
            });
        }

        for (EObject newObj : remainingObjects) {
            LOGGER.warn("Unresolved new object: " + newObj);
        }

        assert remainingObjects.isEmpty();

        return result;
    }
}
