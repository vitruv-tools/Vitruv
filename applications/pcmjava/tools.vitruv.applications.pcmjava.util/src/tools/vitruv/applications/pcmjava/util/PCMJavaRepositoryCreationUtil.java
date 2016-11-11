package tools.vitruv.applications.pcmjava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.vitruv.domains.java.util.JaMoPPNamespace;
import tools.vitruv.domains.pcm.PcmDomain;
import tools.vitruv.domains.java.util.jamopp.JaMoPPTUIDCalculatorAndResolver;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Util class for the PCM Java case study
 *
 * @author Langhamm
 *
 */
public class PCMJavaRepositoryCreationUtil {

    /**
     * Util classes should not have public constructor
     */
    private PCMJavaRepositoryCreationUtil() {

    }

    /**
     * creates and returns the metarepository for the PCM and Java case study
     *
     * @return the PCMJava MetaRepository
     */
    public static List<Metamodel> createPcmJamoppMetamodels() {
    	List<Metamodel> result = new ArrayList<Metamodel>();
        // PCM
        result.add(PcmDomain.getInstance().getMetamodel());
        
        // JaMoPP
        final VURI jaMoPPURI = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Set<String> jamoppNSURIs = new HashSet<String>();
        jamoppNSURIs.addAll(Arrays.asList(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE_URIS));
        final Metamodel jaMoPPMM = new Metamodel(jamoppNSURIs, jaMoPPURI, new JaMoPPTUIDCalculatorAndResolver(),
                JaMoPPNamespace.JAVA_FILE_EXTENSION);
        result.add(jaMoPPMM);
        
        return result;
    }

}
