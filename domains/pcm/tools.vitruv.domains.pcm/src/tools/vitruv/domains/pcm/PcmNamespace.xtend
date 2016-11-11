package tools.vitruv.domains.pcm

import org.palladiosimulator.pcm.PcmPackage
import org.eclipse.emf.ecore.EPackage

final class PcmNamespace {
	private new() {}

	// file extensions
	public static final String REPOSITORY_FILE_EXTENSION = "repository";
	public static final String SYSTEM_FILE_EXTENSION = "system";

	// MM Namespace
	public static final EPackage ROOT_PACKAGE = PcmPackage.eINSTANCE;
	public static final String METAMODEL_NAMESPACE = PcmPackage.eNS_URI
	
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
