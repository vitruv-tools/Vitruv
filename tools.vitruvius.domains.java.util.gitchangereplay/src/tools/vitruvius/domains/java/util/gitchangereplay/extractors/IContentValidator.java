package tools.vitruvius.domains.java.util.gitchangereplay.extractors;

import org.eclipse.emf.common.util.URI;

public interface IContentValidator {
	
	boolean isValid(String content, URI contentUri);

}
