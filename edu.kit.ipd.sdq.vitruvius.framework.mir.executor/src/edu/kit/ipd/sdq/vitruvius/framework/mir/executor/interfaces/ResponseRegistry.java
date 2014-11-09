package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;

public interface ResponseRegistry {
	void addResponse(Response response);
	void removeResponse(Response response);
	
	Collection<Response> getResponsesFor(EClassifier classifier);
	Collection<Response> getResponsesFor(ResponseAction responseAction);
	Collection<Response> getResponsesFor(EClassifier classifier, ResponseAction responseAction);
	Collection<Response> getAllResponses();
}
