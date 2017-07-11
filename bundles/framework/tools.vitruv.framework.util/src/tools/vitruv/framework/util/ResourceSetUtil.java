package tools.vitruv.framework.util;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class ResourceSetUtil {
	public static void addExistingFactoriesToResourceSet(ResourceSet resourceSet) {
        Map<String, Object> globalExtensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        for (String extension : globalExtensionToFactoryMap.keySet()) {
            resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(extension,
                    globalExtensionToFactoryMap.get(extension));
        }
        if (resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().isEmpty()) {
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        }
	}
}
