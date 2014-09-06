package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

/**
 * {@link ApplicatorConfiguration} is a class used for storing configuration data and shared objects
 * used by the various package-level EChange change applicators.
 */
class ApplicatorConfiguration {
    private final ModelTranslator translator;
    private final TransModelObjectCopier objectCopier;

    private final Resource source;

    private final Resource target;

    private final Collection<Change> sourceChanges;

    public ApplicatorConfiguration(Resource source, Resource target, Collection<Change> sourceChanges) {
        super();
        this.source = source;
        this.target = target;
        this.sourceChanges = sourceChanges;

        this.translator = new ModelTranslator(source, target);
        this.objectCopier = new TransModelObjectCopier(translator);
    }

    public ModelTranslator getTranslator() {
        return translator;
    }

    public TransModelObjectCopier getObjectCopier() {
        return objectCopier;
    }

    public Resource getSource() {
        return source;
    }

    public Resource getTarget() {
        return target;
    }

    public Collection<Change> getSourceChanges() {
        return sourceChanges;
    }
}
