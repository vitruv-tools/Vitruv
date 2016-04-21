package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import org.eclipse.emf.common.util.URI;

public interface IContentValidator {
	
	boolean isValid(String content, URI contentUri);

}
