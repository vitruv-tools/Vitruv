package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link IObjectChange} represents changes to {@link EObject EObjects}. Changes can either be
 * represented by a single {@link FeatureChange} or by a list of {@link EChange} objects.
 */
interface IObjectChange {

    /**
     * @return The object affected by this change.
     */
    public EObject getAffectedObject();

    /**
     * Part of the visitor pattern implementation for {@link IObjectChange}. Accepts an
     * {@link ObjectChangeVisitor} visitor, calling its {@link ObjectChangeVisitor#visit} method
     * with the {@link IObjectChange} instance as its parameter.
     * 
     * @param visitor
     *            The visitor whose {@link ObjectChangeVisitor#visit} method will be called.
     */
    public void accept(ObjectChangeVisitor visitor);

    /**
     * @return <code>true</code> iff the represented change affects a containment reference.
     */
    public boolean isContainmentChange();

    /**
     * {@link ObjectChangeVisitor} is an interface for the visitor part of the visitor pattern
     * implementation for {@link IObjectChange}.
     */
    interface ObjectChangeVisitor {
        /**
         * Visits an {@link IObjectChange} object.
         * 
         * @param change
         *            The visited object.
         */
        public void visit(IObjectChange change);

        /**
         * Visits a {@link FeatureChangeObjectChange} object.
         * 
         * @param change
         *            The visited object.
         */
        public void visit(FeatureChangeObjectChange change);

        /**
         * Visits a {@link EChangeCompoundObjectChange} object.
         * 
         * @param change
         *            The visited object.
         */
        public void visit(EChangeCompoundObjectChange change);
    }

    /**
     * {@link EChangeCompoundObjectChange} represents a list of {@link EChange} changes.
     */
    class EChangeCompoundObjectChange implements IObjectChange {
        private final EObject affectedObject;
        private final List<Change> changes;
        private final boolean isContainmentChange;

        /**
         * Constructs an {@link EChangeCompoundObjectChange} object.
         * 
         * @param affectedObject
         *            The {@link EObject} affected by the contained changes.
         * @param changes
         *            The list of represented {@link EChange} changes.
         * @param isContainmentChange
         *            <code>true</code> iff the changes affect a containment reference.
         */
        public EChangeCompoundObjectChange(EObject affectedObject, List<Change> changes, boolean isContainmentChange) {
            super();
            this.affectedObject = affectedObject;
            this.changes = changes;
            this.isContainmentChange = isContainmentChange;
        }

        @Override
        public boolean isContainmentChange() {
            return isContainmentChange;
        }

        @Override
        public EObject getAffectedObject() {
            return affectedObject;
        }

        @Override
        public void accept(ObjectChangeVisitor visitor) {
            visitor.visit(this);
        }

        /**
         * @return The represented list of {@link EChange} changes.
         */
        public List<Change> getChanges() {
            return this.changes;
        }

    }

    /**
     * {@link FeatureChangeObjectChange} represents changes to an {@link EObject} given as a
     * {@link FeatureChange}.
     */
    class FeatureChangeObjectChange implements IObjectChange {
        private final EObject changedObject;
        private final FeatureChange change;

        /**
         * Constructs a new {@link FeatureChangeObjectChange} object.
         * 
         * @param changedObject
         *            The {@link EObject} affected by the changes.
         * @param change
         *            The represented changes.
         */
        public FeatureChangeObjectChange(EObject changedObject, FeatureChange change) {
            this.change = change;
            this.changedObject = changedObject;
        }

        @Override
        public EObject getAffectedObject() {
            return changedObject;
        }

        /**
         * @return The represented {@link FeatureChange}.
         */
        public FeatureChange getChange() {
            return change;
        }

        @Override
        public boolean isContainmentChange() {
            if (change.getFeature() instanceof EReference) {
                EReference ref = (EReference) change.getFeature();
                return ref.isContainment();
            } else {
                return false;
            }
        }

        @Override
        public void accept(ObjectChangeVisitor visitor) {
            visitor.visit(this);
        }
    }
}
