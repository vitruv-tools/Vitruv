package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Singleton
import org.eclipse.xtend.lib.annotations.Accessors

@Singleton
@Accessors
class CommonalitiesGenerationSettings {
	public static val CREATE_REACTIONS_FILES_DEFAULT = false
	package new(){}
	
	var boolean createReactionFiles = CREATE_REACTIONS_FILES_DEFAULT
	
}