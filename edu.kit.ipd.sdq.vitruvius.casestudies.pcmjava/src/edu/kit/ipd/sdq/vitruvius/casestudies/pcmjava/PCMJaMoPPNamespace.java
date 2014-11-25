package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava;

public class PCMJaMoPPNamespace {
    /**
     * no public constructor
     */
    private PCMJaMoPPNamespace() {
    }

    // ID of PCMJavaBuilder
    public static final String BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder.id";

    public static class JaMoPP {
        private JaMoPP() {
        }

        // file extensions
        public static final String JAVA_FILE_EXTENSION = "java";
        // MM Namespaces
        public static final String JAMOPP_METAMODEL_NAMESPACE = "http://www.emftext.org/java";
        public static final String[] JAMOPP_METAMODEL_NAMESPACE_URIS = { "http://www.emftext.org/java",
            "http://www.emftext.org/java/containers", "http://www.emftext.org/java/classifiers",
            "http://www.emftext.org/java/expressions", "http://www.emftext.org/java/literals",
            "http://www.emftext.org/java/members", "http://www.emftext.org/java/modifiers",
            "http://www.emftext.org/java/operators", "http://www.emftext.org/java/references",
            "http://www.emftext.org/java/statements", "http://www.emftext.org/java/types",
            "http://www.emftext.org/java/imports", "http://www.emftext.org/java/parameters" };

        // Attributes
        public static String JAMOPP_ATTRIBUTE_NAME = "name";
        public static String JAMOPP_ANNOTATIONS_AND_MODIFIERS_REFERENCE_NAME = "annotationsAndModifiers";
        public static String JAMOPP_PARAMETER_ATTRIBUTE_TYPE_REFERENCE = "typeReference";
    }

    public static class PCM {
        private PCM() {
        }

        // file extensions
        public static final String REPOSITORY_FILE_EXTENSION = "repository";
        public static final String SYSTEM_FILE_EXTENSION = "system";
        // MM Namespace
        public static final String PCM_METAMODEL_NAMESPACE = "http://sdq.ipd.uka.de/PalladioComponentModel/5.0";
        public static final String PCM_METAMODEL_NAMESPACE_URI = PCM_METAMODEL_NAMESPACE;
        public static final String PCM_METAMODEL_NAMESPACE_URI_REPOSITORY = "http://sdq.ipd.uka.de/PalladioComponentModel/Repository/5.0";
        public static final String PCM_METAMODEL_NAMESPACE_URI_SYSTEM = "http://sdq.ipd.uka.de/PalladioComponentModel/System/5.0";;
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
        public static Object SYSTEM_ASSEMBLY_CONTEXTS__COMPOSED_STRUCTURE = "assemblyContexts__ComposedStructure";
    }
}
