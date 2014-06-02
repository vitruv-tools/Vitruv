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
    public static final String JAMOPP_METAMODEL_NAMESPACE_URI = JAMOPP_METAMODEL_NAMESPACE;
    public static final String JAMPPP_METAMODEL_NAMESPACE_URI_CONTAINER = "http://www.emftext.org/java/containers";

    // file extensions
    public static final String REPOSITORY_FILE_EXTENSION = "repository";
    public static final String JAVA_FILE_EXTENSION = "java";

    // ID of PCMJavaBuilder
    public static final String BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder.id";

}
