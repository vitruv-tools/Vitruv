package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;

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
        if (getResource() != null && getResource().getContents().size() == 0) {
            throw new RuntimeException("Cannot get the metamodel URI for the model instance at the URI '" + getURI()
                    + "' because it has no root element!");
        }
        String rootEObjectNamespace = getUniqueRootEObject().eClass().getEPackage().getNsURI().toString();
        return VURI.getInstance(rootEObjectNamespace);
    }

    /**
     * Returns the root element of the model instance if it is unique (exactly one root element) and
     * throws a {@link java.lang.RuntimeException RuntimeException} otherwise.
     * 
     * @return the root element
     */
    public EObject getUniqueRootEObject() {
        return EcoreResourceBridge.getUniqueContentRoot(this.resource, getURI().toString());
    }

    /**
     * Returns the root element of the model instance if it is unique (exactly one root element) and
     * has the type of the given class and throws a {@link java.lang.RuntimeException
     * RuntimeException} otherwise.
     * 
     * @param rootElementClass
     *            the class of which the root element has to be an instance of
     * @return the root element
     */
    public <T extends EObject> T getUniqueRootEObjectIfCorrectlyTyped(final Class<T> rootElementClass) {
        return EcoreResourceBridge.getUniqueContentRootIfCorrectlyTyped(this.resource, getURI().toString(),
                rootElementClass);
    }

    public void load() {
        try {
            this.resource.load(Collections.emptyMap());
        } catch (IOException e) {
            // soften
            throw new RuntimeException(e);
        }
    }
}
