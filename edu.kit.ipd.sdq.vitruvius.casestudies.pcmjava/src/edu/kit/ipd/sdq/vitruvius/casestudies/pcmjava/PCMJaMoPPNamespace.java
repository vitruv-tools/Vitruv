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
        // MM Namespace
        public static final String PCM_METAMODEL_NAMESPACE = "http://sdq.ipd.uka.de/PalladioComponentModel/5.0";
        public static final String PCM_METAMODEL_NAMESPACE_URI = PCM_METAMODEL_NAMESPACE;
        public static final String PCM_METAMODEL_NAMESPACE_URI_REPOSITORY = "http://sdq.ipd.uka.de/PalladioComponentModel/Repository/5.0";
        // Attributes
        public static String PCM_ATTRIBUTE_ENTITY_NAME = "entityName";
        public static String PCM_PARAMETER_ATTRIBUTE_PARAMETER_NAME = "parameterName";
        public static String PCM_PARAMETER_ATTRIBUTE_DATA_TYPE = "dataType__Parameter";

    }

}
