package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.FeatureChangeObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.ObjectChangeVisitor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools.MapUtils;

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

    private static void collectContainers(FeatureChangeObjectChange change, Map<EObject, Set<EObject>> resultCollector,
            Collection<EObject> remainingObjects) {
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

    private static void collectContainers(EChangeCompoundObjectChange change,
            Map<EObject, Set<EObject>> resultCollector, Collection<EObject> remainingObjects) {
        for (Change emfChange : change.getChanges()) {
            EChange eChange = ((EMFModelChange) emfChange).getEChange();
            if (eChange instanceof CreateNonRootEObject<?>) {
                CreateNonRootEObject<?> createChange = (CreateNonRootEObject<?>) eChange;
                if (createChange.getAffectedFeature().isContainment()
                        && remainingObjects.contains(createChange.getChangedEObject())) {
                    MapUtils.addToSetMap(createChange.getAffectedEObject(), createChange.getChangedEObject(),
                            resultCollector);
                    remainingObjects.remove(createChange.getChangedEObject());
                }
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
    public static Map<EObject, Set<EObject>> getContainers(Collection<EObject> objects,
            Collection<IObjectChange> contChanges) {
        final Map<EObject, Set<EObject>> result = new HashMap<>();

        // The set of objects for which no container has been found yet.
        final Set<EObject> remainingObjects = new HashSet<>(objects);

        for (IObjectChange o : contChanges) {
            o.accept(new ObjectChangeVisitor() {
                @Override
                public void visit(IObjectChange change) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void visit(FeatureChangeObjectChange change) {
                    collectContainers(change, result, remainingObjects);
                }

                @Override
                public void visit(EChangeCompoundObjectChange change) {
                    collectContainers(change, result, remainingObjects);
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
