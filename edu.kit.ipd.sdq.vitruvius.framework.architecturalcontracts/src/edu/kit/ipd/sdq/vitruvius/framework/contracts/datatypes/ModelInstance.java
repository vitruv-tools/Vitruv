package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.resource.Resource;

public class ModelInstance extends AbstractURIHaving {
    private Resource resource;
    private Metamodel metamodel;
    private VURI mmURI;

    public ModelInstance(final VURI uri, final Resource resource) {
        super(uri);
        this.resource = resource;
        if (null != resource || 0 == resource.getContents().size()) {

        }
        this.mmURI = VURI.getInstance(resource.getContents().get(0).eClass().getEPackage().getNsURI().toString());
    }

    public Resource getResource() {
        // if (this.rootElement == null) {
        // FIXME KEEP ON WORKING HERE: add a resource to modelInstance and remove rootElement?
        // }
        // return this.rootElement;
        return this.resource;
    }

    public VURI getMetamodeURI() {
        return this.mmURI;
    }

}
