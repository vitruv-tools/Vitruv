package edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.extractors;

import java.util.List;

public interface IAtomicChangeExtractor {
	
	List<String> extract();
	
	void setValidator(IContentValidator validator);
	
	int getNumberOfValidExtractions();
	
	int getNumberOfTotalExtractions();

}
