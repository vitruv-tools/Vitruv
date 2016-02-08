package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import java.util.Map
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import com.google.inject.Singleton

@Singleton
class MappingLanguageGeneratorStateProvider {
	private Map<MappingFile, MappingLanguageGeneratorState> states
	
	new() {
		states = newHashMap
	}
	
	public def clear() {
		states.clear
	}
	
	public def get(MappingFile mappingFile) {
		if (!states.containsKey(mappingFile)) {
			states.put(mappingFile, new MappingLanguageGeneratorState(mappingFile))
		}
		
		return states.get(mappingFile)
	}
}