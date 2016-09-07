package tools.vitruvius.extensions.dslsruntime.response.helper

import java.util.Map
import java.util.HashMap
import java.util.List
import java.util.ArrayList
import java.util.Set
import java.util.HashSet
import tools.vitruvius.extensions.dslsruntime.response.IResponseRealization
import tools.vitruvius.framework.change.echange.EChange

class Change2ResponseMap {
	private Map<Class<? extends EChange>, List<IResponseRealization>> change2responseMap;
	
	new() {
		this.change2responseMap = new HashMap<Class<? extends EChange>, List<IResponseRealization>>();
	}
	
	public def addResponse(Class<? extends EChange> eventType, IResponseRealization response) {
		if (this.change2responseMap.get(eventType) == null) {
			this.change2responseMap.put(eventType, (new ArrayList<IResponseRealization>()))
		}
		this.change2responseMap.get(eventType).add(response);
	}
	
	public def Set<IResponseRealization> getResponses(EChange event) {
		val result = new HashSet<IResponseRealization>();
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