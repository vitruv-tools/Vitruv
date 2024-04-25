package tools.vitruv.framework.vsum.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class ModelInstanceFactory implements Resource.Factory {
	@Override
	public Resource createResource(URI uri) {
		return new ModelInstance(uri);
	}
}
