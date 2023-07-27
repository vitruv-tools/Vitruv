package tools.vitruv.framework.remote.common.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class XMIResourceSetImpl extends ResourceSetImpl {
    @Override
    public Resource createResource(URI uri, String contentType) {
        var resource = new XMIResourceImpl(uri);
        this.getResources().add(resource);
        return resource;
    }
}
