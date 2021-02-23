package tools.vitruv.framework.util.datatypes;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;

import static com.google.common.base.Preconditions.checkArgument;

public class ModelInstance extends AbstractURIHaving {
	private static final Logger LOGGER = Logger.getLogger(ModelInstance.class);
	private Resource resource;

	public ModelInstance(final VURI uri, final Resource resource) {
		super(uri);
		checkArgument(resource != null, "cannot create a model instance at the URI %s for a null resource", uri);
		LOGGER.debug("Creating model instance for loaded resource with URI: " + uri.getEMFUri());
		this.resource = resource;
	}

	public Resource getResource() {
		return this.resource;
	}

}
