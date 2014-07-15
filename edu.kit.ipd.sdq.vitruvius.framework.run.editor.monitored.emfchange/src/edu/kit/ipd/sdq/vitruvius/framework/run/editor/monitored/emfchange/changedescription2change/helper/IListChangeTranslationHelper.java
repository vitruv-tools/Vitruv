package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

/**
 * {@link IListChangeTranslationHelper} is an interface for helpers translating {@link ListChange}
 * objects obtained from a {@link FeatureChange} to the corresponding {@link Change} objects.
 */
interface IListChangeTranslationHelper {
    /**
     * Translates a given {@link FeatureChange} to its {@link Change} representation for an
     * {@link org.eclipse.emf.ecore.EAttribute} feature.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be translated.
     * @param lc
     *            The {@link ListChange} contained in <code>fc</code> to be translated.
     * @param target
     *            The {@link List} of {@link Change} objects where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects);

    /**
     * Translates a given {@link FeatureChange} to its {@link Change} representation for an
     * {@link org.eclipse.emf.ecore.EAttribute} feature.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be translated.
     * @param lc
     *            The {@link ListChange} contained in <code>fc</code> to be translated.
     * @param target
     *            The {@link List} of {@link Change} objects where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects);
}
