package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.EObject;

public class ModelInstance extends AbstractURIHaving {
    private EObject rootElement;

    public ModelInstance(final VURI uri, final EObject rootElement) {
        super(uri);
        this.rootElement = rootElement;
    }

    public EObject getRootElement() {
        if (this.rootElement == null) {
            // FIXME KEEP ON WORKING HERE: add a resource to modelInstance and remove rootElement?
        }
        return this.rootElement;
    }

}
