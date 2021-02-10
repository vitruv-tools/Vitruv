package tools.vitruv.framework.variability.vave.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class VaveXMIResourceFactoryImpl extends XMIResourceFactoryImpl {
	
	public VaveXMIResourceFactoryImpl() {
		super();
	}
	
	@Override
	public Resource createResource(URI uri)
	{
	  return new VaveXMIResourceImpl(uri);
	}

}
