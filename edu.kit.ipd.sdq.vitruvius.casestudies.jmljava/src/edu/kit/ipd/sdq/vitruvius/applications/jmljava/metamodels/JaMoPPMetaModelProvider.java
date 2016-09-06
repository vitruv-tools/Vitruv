package edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

/**
 * Provider for the JaMoPP meta-model.
 */
public class JaMoPPMetaModelProvider extends MetaModelProviderBase {

    public static final String[] NS_URIS = { "http://www.emftext.org/java", "http://www.emftext.org/java/containers",
            "http://www.emftext.org/java/classifiers", "http://www.emftext.org/java/expressions",
            "http://www.emftext.org/java/literals", "http://www.emftext.org/java/members",
            "http://www.emftext.org/java/modifiers", "http://www.emftext.org/java/operators",
            "http://www.emftext.org/java/references", "http://www.emftext.org/java/statements",
            "http://www.emftext.org/java/types", "http://www.emftext.org/java/imports",
            "http://www.emftext.org/java/parameters" };
    public static final String[] EXTENSIONS = { "java" };
    public static final VURI URI = VURI.getInstance("http://www.emftext.org/java");

    @Override
    public void registerFactory() {
        // EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);
        // Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java",
        // new JavaSourceOrClassFileResourceFactoryImpl());
        // Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put("java",
        // new JavaSourceOrClassFileResourceFactoryImpl());
        // Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
        // new XMIResourceFactoryImpl());
    }

    @Override
    protected Metamodel constructMetaModel() {
        final Set<String> jamoppNSURIs = new HashSet<String>(Arrays.asList(NS_URIS));
        return new Metamodel(jamoppNSURIs, URI, new JaMoPPTUIDCalculatorAndResolver(), EXTENSIONS);
    }

}
