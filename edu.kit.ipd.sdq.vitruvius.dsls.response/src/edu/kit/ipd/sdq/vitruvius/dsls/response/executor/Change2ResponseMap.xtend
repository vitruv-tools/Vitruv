package edu.kit.ipd.sdq.vitruvius.dsls.response.executor

import java.util.Map
import java.util.HashMap
import java.util.List
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import java.util.Set
import java.util.HashSet

class Change2ResponseMap {
	private Map<Class<? extends EChange>, List<ResponseRealization>> change2responseMap;
	
	new() {
		this.change2responseMap = new HashMap<Class<? extends EChange>, List<ResponseRealization>>();
	}
	
	public def addResponse(Class<? extends EChange> eventType, ResponseRealization response) {
		if (this.change2responseMap.get(eventType) == null) {
			this.change2responseMap.put(eventType, (new ArrayList<ResponseRealization>()))
		}
		this.change2responseMap.get(eventType).add(response);
	}
	
	public def Set<ResponseRealization> getResponses(EChange event) {
		val result = new HashSet<ResponseRealization>();
		var dependantInterfaces = newLinkedList(event.class.interfaces);
		var superClass = event.class as Class<?>;
		while (superClass != null) {
			val currentResponse = this.change2responseMap.get(superClass);
			if (currentResponse != null) result.addAll(currentResponse);
			dependantInterfaces.addAll(superClass.interfaces);
			superClass = superClass.superclass;
		} 
		while (!dependantInterfaces.empty) {
			val currentInterface = dependantInterfaces.remove(0);
			val currentResponse = this.change2responseMap.get(currentInterface);
			if (currentResponse != null) result.addAll(currentResponse);
			dependantInterfaces.addAll(currentInterface.interfaces);
		}
				
		return result;
	}
}