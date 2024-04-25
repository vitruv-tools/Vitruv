package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;

public class PathmapAwareModelInstanceFactory implements Resource.Factory {
	@Override
	public Resource createResource(URI uri) {
		if (uri.toString().contains("pathmap")) {
			ResourceSet stateBasedResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
			loadOrCreateResource(stateBasedResourceSet, uri);
			ModelInstance modelInstance = new ModelInstance(uri);
			Resource stateBasedResource = stateBasedResourceSet.getResource(uri, false);
			modelInstance.getContents().addAll(EcoreUtil.copyAll(stateBasedResource.getContents()));
			modelInstance.setModified(false);
			return modelInstance;
		}
		return new ModelInstance(uri);
	}
}