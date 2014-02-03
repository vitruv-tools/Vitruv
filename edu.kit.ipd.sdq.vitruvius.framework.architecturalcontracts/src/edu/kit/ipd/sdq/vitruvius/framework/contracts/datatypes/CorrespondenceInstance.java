package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.resource.Resource;

public class CorrespondenceInstance extends ModelInstance {
    private Mapping mapping;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        // FIXME AAAA KEEP ON WORKING HERE
        // create model instance of the correspondence instance metamodel
        // link to this instance from a private field
    }

}
