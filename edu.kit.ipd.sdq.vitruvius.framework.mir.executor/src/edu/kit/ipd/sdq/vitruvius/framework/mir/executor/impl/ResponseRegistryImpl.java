package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

/**
 * @author Dominik Werle
 */
public class ResponseRegistryImpl implements ResponseRegistry {
	private Map<ResponseAction, Collection<Response>> responseActionToResponse;
	private Collection<Response> allResponses;
	
	public ResponseRegistryImpl() {
		responseActionToResponse = new HashMap<>();
		allResponses = new HashSet<>();
	}
	
	private static <K,V> Collection<V> getOrCreate(Map<K, Collection<V>> map, K key) {
		if (!map.containsKey(key)) {
			Collection<V> newCollection = new HashSet<>();
			map.put(key, newCollection);
			return newCollection;
		} else {
			return map.get(key);
		}
	}

	@Override
	public void addResponse(Response response) {
		getOrCreate(responseActionToResponse, response.getResponseAction()).add(response);
		allResponses.add(response);
	}

	@Override
	public void removeResponse(Response response) {
		getOrCreate(responseActionToResponse, response.getResponseAction()).remove(response);
		allResponses.remove(response);
	}

	@Override
	public Collection<Response> getResponsesFor(EClassifier classifier) {
		// TODO: also respect inheritance hierarchy
		Collection<Response> resultCollection = new HashSet<>();
		for (Response r : allResponses)
			if (r.getContextClassifier().equals(classifier))
				resultCollection.add(r);
		
		return resultCollection;
	}

	@Override
	public Collection<Response> getResponsesFor(ResponseAction responseAction) {
		return getOrCreate(responseActionToResponse, responseAction);
	}

	@Override
	public Collection<Response> getResponsesFor(EClassifier classifier,
			ResponseAction responseAction) {
		Collection<Response> resultCollection = new HashSet<>();
		for (Response r : getResponsesFor(classifier))
			if (r.getResponseAction().equals(responseAction))
				resultCollection.add(r);
		
		return resultCollection;
	}

	@Override
	public Collection<Response> getAllResponses() {
		return allResponses;
	}

}
