package tools.vitruv.framework.util.datatypes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.command.EMFCommandBridge;

public class ModelInstance extends AbstractURIHaving {
    private static final Logger LOGGER = Logger.getLogger(ModelInstance.class.getSimpleName());
    private Resource resource;
    private Map<Object, Object> lastUsedLoadOptions;

    public ModelInstance(final VURI uri, final Resource resource) {
        super(uri);
        if (resource == null) {
            throw new RuntimeException("Cannot create a model instance at the URI '" + uri + "' for a null resource!");
        }
        LOGGER.debug("Creating model instance for loaded resource with URI: " + uri.getEMFUri());
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
        String rootEObjectNamespace;
        try {
            rootEObjectNamespace = getUniqueRootEObject().eClass().getEPackage().getNsURI().toString();
        } catch (RuntimeException e) {
            LOGGER.warn("A unique root object could not be determined. Trying the first root object instead.");
            rootEObjectNamespace = getFirstRootEObject().eClass().getEPackage().getNsURI().toString();
        }
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
     * Returns the root element of the model instance, which occurs first (depending on the order in
     * the resource) and throws a {@link java.lang.RuntimeException RuntimeException} if there is no
     * root element.
     *
     * @return the root element
     */
    public EObject getFirstRootEObject() {
        try {
            return EcoreResourceBridge.getFirstRootEObject(this.resource, getURI().toString());
        } catch (RuntimeException re) {
            boolean forceLoad = true;
            this.load(this.lastUsedLoadOptions, forceLoad);
            return EcoreResourceBridge.getFirstRootEObject(this.resource, getURI().toString());
        }
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

    /**
     * Returns the root element of the model instance if it is the only one with a compatible type.
     * It is NOT necessary to have exactly one root element as long as only one of these element
     * matches the given type. If there is not exactly one such element a
     * {@link java.lang.RuntimeException RuntimeException} is thrown.
     *
     * @param rootElementClass
     *            the class of which the root element has to be an instance of
     * @return the root element
     */
    public <T extends EObject> T getUniqueTypedRootEObject(final Class<T> rootElementClass) {
        return EcoreResourceBridge.getUniqueTypedRootEObject(this.resource, getURI().toString(), rootElementClass);
    }

    public List<EObject> getRootElements() {
        return this.resource.getContents();
    }
    
    // TODO HK This should be done differently: The VSUM provides the editing domain!
    private synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
        if (null == TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resource.getResourceSet())) {
            TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(this.resource.getResourceSet());
        }
        return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resource.getResourceSet());
    }
    
    /**
     * Loads the resource into memory. The load can be forced by setting
     * forceLoadByDoingUnloadBeforeLoad to true, which means that the resource will be unloaded
     * before we load it again.
     *
     * Throws an {@link IllegalStateException} if the resource cannot be loaded.
     */
    public void load(final Map<Object, Object> loadOptions, final boolean forceLoadByDoingUnloadBeforeLoad) {
    	EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(() -> {
    		try {
    			if (null != loadOptions) {
    				this.lastUsedLoadOptions = loadOptions;
    			}
    			if (this.resource.isModified() || forceLoadByDoingUnloadBeforeLoad) {
    				// TODO If the model resource already exists, this method
    				// may also get called for already loaded resources
    				// (see ResourceRepositoryImpl#getAndLoadModelInstanceOriginal).
    				// If the resource has been marked as modified, any not yet
    				// saved model objects are lost after the following unload call.
    				this.resource.unload();
    			}
   				this.resource.load(this.lastUsedLoadOptions);
   				LOGGER.debug("Resource was loaded: " + resource.getURI());
    		} catch (IOException e) {
    			// 	soften
    			throw new IllegalStateException("Problem loading resource: " + resource.getURI());
    		}
    		return null;
        }, getTransactionalEditingDomain());
    }

    public void load(final Map<Object, Object> loadOptions) {
        load(loadOptions, false);
    }
}
