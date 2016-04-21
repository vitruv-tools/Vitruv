package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import java.util.List;

public interface IAtomicChangeExtractor {
	
	List<String> extract();
	
	void setValidator(IContentValidator validator);
	
	int getNumberOfValidExtractions();
	
	int getNumberOfTotalExtractions();

}
