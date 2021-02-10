package tools.vitruv.framework.variability.vave;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class VaveXMIResourceImpl extends XMIResourceImpl {
	
	public VaveXMIResourceImpl() {
		super();
	}

	public VaveXMIResourceImpl(URI uri) {
		super(uri);
	}

	public static void saveResource(Resource resource) {
		Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			resource.save(saveOptions);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Resource createAndAddResource() {
		ResourceSet resSet = new ResourceSetImpl();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("vavemodel", new VaveXMIResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("vavemodel",
				new VaveXMIResourceFactoryImpl());
		Resource vaveRes = resSet.createResource(URI.createURI("models/vave.vavemodel"));
		return vaveRes;
	}

}
