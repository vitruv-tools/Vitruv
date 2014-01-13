package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;

public class VURI implements Comparable<VURI> {
	private org.eclipse.emf.common.util.URI emfURI;
	
	public VURI(String uriString) {
		this.emfURI = EMFBridge.createPlatformResourceURI(uriString);
	}
	
	public VURI(URI emfURI) {
		this.emfURI = emfURI;
	}
	
	@Override
	public String toString() {
		return emfURI.toString();
	}

	@Override
	public int compareTo(VURI otherURI) {
		return this.toString().compareTo(otherURI.toString());
	}
}
