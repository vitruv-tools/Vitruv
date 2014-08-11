package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava;

public class PCMJavaNamespace {
    /**
     * no public constructor
     */
    private PCMJavaNamespace() {
    }

    // namespaces for Metamodels
    public static final String PCM_METAMODEL_NAMESPACE = "http://sdq.ipd.uka.de/PalladioComponentModel/5.0";
    public static final String JAMOPP_METAMODEL_NAMESPACE = "http://www.emftext.org/java";

    // URIs for metamodels
    public static final String PCM_METAMODEL_NAMESPACE_URI = PCM_METAMODEL_NAMESPACE;
    public static final String PCM_METAMODEL_NAMESPACE_URI_REPOSITORY = "http://sdq.ipd.uka.de/PalladioComponentModel/Repository/5.0";
    public static final String[] JAMOPP_METAMODEL_NAMESPACE_URIS = { "http://www.emftext.org/java",
            "http://www.emftext.org/java/containers", "http://www.emftext.org/java/classifiers",
            "http://www.emftext.org/java/expressions", "http://www.emftext.org/java/literals",
            "http://www.emftext.org/java/members", "http://www.emftext.org/java/modifiers",
            "http://www.emftext.org/java/operators", "http://www.emftext.org/java/references",
            "http://www.emftext.org/java/statements", "http://www.emftext.org/java/types",
            "http://www.emftext.org/java/imports", "http://www.emftext.org/java/parameters" };

    // file extensions
    public static final String REPOSITORY_FILE_EXTENSION = "repository";
    public static final String JAVA_FILE_EXTENSION = "java";

    // ID of PCMJavaBuilder
    public static final String BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder.id";

}
