package tools.vitruv.extensions.dslsruntime.reactions.helper

import java.util.Map
import java.util.HashMap
import java.util.List
import java.util.ArrayList
import java.util.Set
import java.util.HashSet
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.change.echange.EChange

class Change2ReactionsMap {
	private Map<Class<? extends EChange>, List<IReactionRealization>> change2reactionsMap;
	
	new() {
		this.change2reactionsMap = new HashMap<Class<? extends EChange>, List<IReactionRealization>>();
	}
	
	public def addReaction(Class<? extends EChange> eventType, IReactionRealization reaction) {
		if (this.change2reactionsMap.get(eventType) === null) {
			this.change2reactionsMap.put(eventType, (new ArrayList<IReactionRealization>()))
		}
		this.change2reactionsMap.get(eventType).add(reaction);
	}
	
	public def Set<IReactionRealization> getReactions(EChange event) {
		val result = new HashSet<IReactionRealization>();
		var dependantInterfaces = newLinkedList(event.class.interfaces);
		var superClass = event.class as Class<?>;
		while (superClass !== null) {
			val currentReactions = this.change2reactionsMap.get(superClass);
			if (currentReactions !== null) result.addAll(currentReactions);
			dependantInterfaces.addAll(superClass.interfaces);
			superClass = superClass.superclass;
		} 
		while (!dependantInterfaces.empty) {
			val currentInterface = dependantInterfaces.remove(0);
			val currentReactions = this.change2reactionsMap.get(currentInterface);
			if (currentReactions !== null) result.addAll(currentReactions);
			dependantInterfaces.addAll(currentInterface.interfaces);
		}
				
		return result;
	}
}