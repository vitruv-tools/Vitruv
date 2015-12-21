package edu.kit.ipd.sdq.vitruvius.dsls.response.executor.impl

import java.util.Map
import java.util.HashMap
import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Event
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseRealization;

class Change2ResponseMap {
	private Map<Class<? extends Event>, List<ResponseRealization>> change2responseMap;
	
	new() {
		this.change2responseMap = new HashMap<Class<? extends Event>, List<ResponseRealization>>();
	}
	
	public def addResponse(Class<? extends Event> eventType, ResponseRealization response) {
		if (this.change2responseMap.get(eventType) == null) {
			this.change2responseMap.put(eventType, (new ArrayList<ResponseRealization>()))
		}
		this.change2responseMap.get(eventType).add(response);
	}
	
	public def List<ResponseRealization> getResponses(Event event) {
		if (this.change2responseMap.get(event.class) != null) {
			return this.change2responseMap.get(event.class).immutableCopy 
		}
		return new ArrayList<ResponseRealization>();
	}
}