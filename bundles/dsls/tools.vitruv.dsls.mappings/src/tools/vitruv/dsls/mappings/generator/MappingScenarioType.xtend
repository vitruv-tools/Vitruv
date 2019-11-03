package tools.vitruv.dsls.mappings.generator

enum MappingScenarioType {
	/**
	 * Mapping scenarios applied after a specific trigger reacted:
	 * 
	 * CREATE:  			the change could result in a mapping instance creation
	 * DELETE:  			the change could result in a mapping instance termination
	 * UPDATE:  			the parameter instances of the mapping instance have to be updated to preserve consistency
	 * CREATE_OR_DELETE: 	combination of create and delete scenario, 
	 * 	for example update triggers could lead to either instantiation or termination of a mapping instance,
	 *  depending on the actual change
	 * 
	 */
	CREATE,CREATE_OR_DELETE,DELETE,UPDATE
}
