package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;

public class CorrespondenceInstance extends ModelInstance {
    private Mapping mapping;
    private Correspondences correspondences;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        // FIXME AAAA KEEP ON WORKING HERE
        // create model instance of the correspondence instance metamodel
        // link to this instance from a private field
        this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
    }

    public Mapping getMapping() {
        return this.mapping;
    }

    public Collection<Correspondence> getAllCorrespondences(final EObject eObject) {
        // this.correspondences.getCorrespondences().
        return null;
    }

    public void addCorrespondence(final Correspondence correspondence) {
        // TODO
    }
}
