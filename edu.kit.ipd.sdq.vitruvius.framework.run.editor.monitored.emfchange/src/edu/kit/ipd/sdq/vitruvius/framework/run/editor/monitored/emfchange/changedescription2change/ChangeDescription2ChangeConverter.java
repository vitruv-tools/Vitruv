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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.FeatureChangeObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.ObjectChangeVisitor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.FeatureChangeKind;

/**
 * {@link ChangeDescription2ChangeConverter} is responsible for converting EMF
 * {@link ChangeDescription} objects to lists of {@link Change} objects such that the changes can be
 * applied to other instances of the model which are equal to the one to which the given change
 * description is equal.
 * 
 * This converter is able to identify object insertions which are not captured in the
 * {@link ChangeDescription}, e.g. additions of whole branches of objects. For those operations,
 * appropriate EChange instances are generated such that the whole respective tree is reconstructed
 * when the changes are applied.
 * 
 * Furthermore, the changes are sorted such that new objects are not referenced in changes placed
 * before the respective creation change. Likewise, deleted objects are not referenced after their
 * respective deletion change.
 */
public class ChangeDescription2ChangeConverter {

    private CategorizedChanges getCategorizedObjectChanges(EMap<EObject, EList<FeatureChange>> changes) {
        CategorizedChanges result = new CategorizedChanges();

        for (EObject changedObject : changes.keySet()) {
            for (FeatureChange change : changes.get(changedObject)) {
                result.addChange(changedObject, change);
            }
        }

        return result;
    }

    /**
     * Nondestructively converts the given {@link ChangeDescription} <code>cd</code> object to a
     * list of {@link Change} objects.
     * 
     * Although the model to which <code>cd</code> pertains remains unchanged in the senses of both
     * identity and equality, it may be temporarily modified by this method.
     * 
     * @param cd
     *            A {@link ChangeDescription} obtained from a ChangeRecorder.
     * @return The corresponding {@link List} of {@link Change} objects.
     * 
     */
    public List<Change> getChanges(ChangeDescription cd) {
        return getChanges(cd, null);
    }

    /**
     * Nondestructively converts the given {@link ChangeDescription} <code>cd</code> object to a
     * list of {@link Change} objects.
     * 
     * Although the model to which <code>cd</code> pertains remains unchanged in the senses of both
     * identity and equality, it may be temporarily modified by this method.
     * 
     * @param cd
     *            A {@link ChangeDescription} obtained from a ChangeRecorder.
     * @param uri
     *            The VURI to which <i>cd</i> pertains.
     * @return The corresponding {@link List} of {@link Change} objects.
     * 
     */
    public List<Change> getChanges(ChangeDescription cd, VURI uri) {
        List<EObject> objsToAttach = new ArrayList<>(cd.getObjectsToAttach());

        CategorizedChanges catChanges = getCategorizedObjectChanges(cd.getObjectChanges());
        CategorizedChanges preprocessedChanges = preprocessChanges(catChanges, objsToAttach, cd.getObjectsToDetach());

        List<EChange> echanges = convertToEChange(preprocessedChanges, objsToAttach, cd.getObjectsToDetach());
        return encloseObjectsInChange(echanges, uri);
    }

    private void moveNonContainmentChanges(Collection<IObjectChange> changes, Collection<IObjectChange> target) {
        for (Iterator<IObjectChange> it = changes.iterator(); it.hasNext();) {
            IObjectChange oc = it.next();
            if (!oc.isContainmentChange()) {
                target.add(oc);
                it.remove();
            }
        }
    }

    /**
     * Preprocesses the given changes such that: implicit ("shadow") insertions are reflected by the
     * changes; no forward references occur; and deleted objects receive no other updates than the
     * deletion.
     * 
     * @param changes
     *            The changes to be preprocessed, categorized in containment and non-containment
     *            changes.
     * @param objsToAttach
     *            The set of objects which are attached to the model when the changes are applied.
     *            This object gets modified by this method.
     * @param objsToDetach
     *            The set of objects which are detached from the model when the changes are applied.
     * @return The preprocessed changes, categorized into containment and non-containment changes.
     */
    private CategorizedChanges preprocessChanges(CategorizedChanges changes, List<EObject> objsToAttach,
            List<EObject> objsToDetach) {
        IObjectChangePass shadowedCreateResolving = new ShadowInsertionResolvingPass(objsToAttach);
        IObjectChangePass forwardReferenceResolving = new ForwardReferenceResolvingPass(objsToAttach);
        IObjectChangePass objectDeletionResolving = new ObjectDeletionResolvingPass(objsToDetach);
        ShadowDeletionResolvingPass shadowedDeleteResolving = new ShadowDeletionResolvingPass(objsToDetach);

        List<IObjectChange> containmentChangeResolvingTrack = shadowedCreateResolving.runPass(changes
                .getContainmentChanges());

        // The shadow resolution pass may have added non-containment changes to its result.
        List<IObjectChange> nonContainmentChangeResolvingTrack = new ArrayList<>(changes.getOtherChanges());
        moveNonContainmentChanges(containmentChangeResolvingTrack, nonContainmentChangeResolvingTrack);

        containmentChangeResolvingTrack = forwardReferenceResolving.runPass(containmentChangeResolvingTrack);
        containmentChangeResolvingTrack = objectDeletionResolving.runPass(containmentChangeResolvingTrack);

        List<IObjectChange> contChangesWithoutShadowOps = shadowedDeleteResolving
                .runPass(containmentChangeResolvingTrack);

        nonContainmentChangeResolvingTrack = objectDeletionResolving.runPass(nonContainmentChangeResolvingTrack);

        return new CategorizedChanges(contChangesWithoutShadowOps, nonContainmentChangeResolvingTrack);
    }

    private List<EChange> convertToEChange(final CategorizedChanges changes, final List<EObject> addedObjects,
            final List<EObject> orphanedObjects) {
        List<EChange> result = convertToEChange(changes.getContainmentChanges(), addedObjects, orphanedObjects);
        result.addAll(convertToEChange(changes.getOtherChanges(), addedObjects, orphanedObjects));
        return result;
    }

    private List<EChange> convertToEChange(final List<IObjectChange> objectChanges, final List<EObject> addedObjects,
            final List<EObject> orphanedObjects) {
        final List<EChange> result = new ArrayList<>();

        for (IObjectChange oc : objectChanges) {
            oc.accept(new ObjectChangeVisitor() {

                @Override
                public void visit(EChangeCompoundObjectChange change) {
                    result.addAll(change.getChanges());
                }

                @Override
                public void visit(FeatureChangeObjectChange change) {
                    FeatureChange fc = change.getChange();
                    FeatureChangeKind changeKind = FeatureChangeKind.determineChangeKind(fc);
                    changeKind.getTranslationHelper().addChange(change.getAffectedObject(), fc, result, addedObjects,
                            orphanedObjects);
                }

                @Override
                public void visit(IObjectChange change) {
                    throw new UnsupportedOperationException();
                }
            });

        }
        return result;
    }

    private List<Change> encloseObjectsInChange(List<EChange> eChanges, VURI uri) {
        List<Change> result = new ArrayList<>();
        for (EChange e : eChanges) {
            result.add(EMFModelChangeFactory.createEMFModelChange(e, uri));
        }
        return result;
    }

    /**
     * A representation of {@link IObjectChange} instances categorized into lists of
     * containment-related and non-containment-related object changes.
     */
    private static class CategorizedChanges {
        private final List<IObjectChange> containmentChanges;
        private final List<IObjectChange> otherChanges;

        public CategorizedChanges(List<IObjectChange> containerChanges, List<IObjectChange> otherChanges) {
            super();
            this.containmentChanges = containerChanges;
            this.otherChanges = otherChanges;
        }

        public CategorizedChanges() {
            super();
            this.containmentChanges = new ArrayList<>();
            this.otherChanges = new ArrayList<>();
        }

        protected boolean isContainmentFeature(EStructuralFeature feature) {
            return (feature instanceof EReference && ((EReference) feature).isContainment());
        }

        public void addChange(EObject changedObj, FeatureChange change) {
            IObjectChange oc = new FeatureChangeObjectChange(changedObj, change);
            if (isContainmentFeature(change.getFeature())) {
                containmentChanges.add(oc);
            } else {
                otherChanges.add(oc);
            }
        }

        public List<IObjectChange> getContainmentChanges() {
            return containmentChanges;
        }

        public List<IObjectChange> getOtherChanges() {
            return otherChanges;
        }
    }
}
