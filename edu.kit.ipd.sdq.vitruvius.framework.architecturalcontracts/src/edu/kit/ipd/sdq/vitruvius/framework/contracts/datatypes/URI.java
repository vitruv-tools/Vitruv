package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.UniquelyConcatenateable;

public class URI implements UniquelyConcatenateable<URI> {
	private org.eclipse.emf.common.util.URI emfURI;

	@Override
	public String concatenate(URI otherURI) {
		String thisString = this.toString();
		String otherString = otherURI.toString();
		String concatenation;
		if (thisString.compareTo(otherString) <= 0) {
			concatenation = thisString + otherString;
		} else {
			concatenation = otherString + thisString;
		}
		return concatenation;
	}
	
	@Override
	public String toString() {
		return emfURI.toString();
	}
}
