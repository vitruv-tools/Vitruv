package edu.kit.ipd.sdq.vitruvius.domains.pcm.util

class PCMNamespace {
	private new() {
	}

	// file extensions
	public static final String REPOSITORY_FILE_EXTENSION = "repository";
	public static final String SYSTEM_FILE_EXTENSION = "system";
	// MM Namespace
	public static final String PCM_METAMODEL_NAMESPACE = "http://palladiosimulator.org/PalladioComponentModel/5.1";
	private static final String PCM_METAMODEL_NAMESPACE_URI_REPOSITORY = "http://palladiosimulator.org/PalladioComponentModel/Repository/5.1";
	private static final String PCM_METAMODEL_NAMESPACE_URI_SYSTEM = "http://palladiosimulator.org/PalladioComponentModel/System/5.1";
	private static final String PCM_METAMODEL_NAMESPACE_URI_COMPOSITION = "http://palladiosimulator.org/PalladioComponentModel/Core/Composition/5.1";
	private static final String PCM_METAMODEL_NAMESPACE_URI_SEFF = "http://palladiosimulator.org/PalladioComponentModel/SEFF/5.1";
	private static final String PCM_METAMODEL_NAMESPACE_URI = PCM_METAMODEL_NAMESPACE;
	public static final String[] PCM_METAMODEL_NAMESPACE_URIS = #[
		PCM_METAMODEL_NAMESPACE_URI,
		PCM_METAMODEL_NAMESPACE_URI_REPOSITORY,
		PCM_METAMODEL_NAMESPACE_URI_SYSTEM,
		PCM_METAMODEL_NAMESPACE_URI_COMPOSITION,
		PCM_METAMODEL_NAMESPACE_URI_SEFF
	];
	
	// Attributes and References
	public static String PCM_ATTRIBUTE_ENTITY_NAME = "entityName";
	public static String PCM_PARAMETER_ATTRIBUTE_DATA_TYPE = "dataType__Parameter";
	public static String PCM_OPERATION_SIGNATURE_RETURN_TYPE = "returnType__OperationSignature";
	public static String DATATYPE_INNERDECLARATION = "datatype_InnerDeclaration";
	public static String INNER_DECLARATION_COMPOSITE_DATA_TYPE = "innerDeclaration_CompositeDataType";
	public static String OPERATION_PROVIDED_ROLE_PROVIDING_ENTITY = "providingEntity_ProvidedRole";
	public static String OPERATION_PROVIDED_ROLE_PROVIDED_INTERFACE = "providedInterface__OperationProvidedRole";
	public static String COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = "providedRoles_InterfaceProvidingEntity";
	public static String COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = "requiredRoles_InterfaceRequiringEntity";
	public static String SYSTEM_ASSEMBLY_CONTEXTS__COMPOSED_STRUCTURE = "assemblyContexts__ComposedStructure";
	public static String ASSEMBLY_CONTEXT_ENCAPSULATED_COMPONENT = "encapsulatedComponent__AssemblyContext";
}
