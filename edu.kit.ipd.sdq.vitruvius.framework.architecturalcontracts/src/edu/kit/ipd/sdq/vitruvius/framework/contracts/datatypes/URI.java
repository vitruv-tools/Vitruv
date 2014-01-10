package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;

public class URI {
	private org.eclipse.emf.common.util.URI emfURI;
	
	public URI(String uriString) {
		this.emfURI = EMFBridge.createPlatformResourceURI(uriString);
	}
	
	@Override
	public String toString() {
		return emfURI.toString();
	}
}
