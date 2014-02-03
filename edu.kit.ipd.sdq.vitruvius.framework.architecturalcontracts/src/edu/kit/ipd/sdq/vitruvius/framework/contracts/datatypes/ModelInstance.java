package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.resource.Resource;

public class ModelInstance extends AbstractURIHaving {
    private Resource resource;

    public ModelInstance(final VURI uri, final Resource resource) {
        super(uri);
        if (resource == null) {
            throw new RuntimeException("Cannot create a model instance at the URI '" + uri + "' for a null resource!");
        }
        this.resource = resource;
    }

    public Resource getResource() {
        return this.resource;
    }

    public VURI getMetamodeURI() {
        if (this.resource.getContents().size() == 0) {
            throw new RuntimeException("Cannot get the metamodel URI for the model instance at the URI '" + getURI()
                    + "' because it has no root element!");
        }
        return VURI.getInstance(this.resource.getContents().get(0).eClass().getEPackage().getNsURI().toString());
    }

}
