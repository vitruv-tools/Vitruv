package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link IChangeTranslationHelper} is an interface for helper classes translating
 * {@link FeatureChange} objects to {@link EChange} objects.
 */
public interface IChangeTranslationHelper {
    /**
     * Translates a given {@link FeatureChange} to its {@link EChange} representation.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be translated.
     * @param target
     *            The {@link List} of {@link EChange} objects where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public void addChange(EObject affectedObject, FeatureChange fc, List<EChange> target, List<EObject> addedObjects,
            List<EObject> orphanedObjects);
}
