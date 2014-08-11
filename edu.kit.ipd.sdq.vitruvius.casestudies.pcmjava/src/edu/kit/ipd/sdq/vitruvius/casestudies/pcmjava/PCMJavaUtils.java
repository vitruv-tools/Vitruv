package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava;

import java.util.HashSet;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;

/**
 * Util class for the PCM Java case study
 * 
 * @author Langhamm
 * 
 */
public class PCMJavaUtils {

    /**
     * Util classes should not have public constructor
     */
    private PCMJavaUtils() {

    }

    /**
     * creates and returns the metarepository for the PCM and Java case study
     * 
     * @return the PCMJava MetaRepository
     */
    public static MetaRepositoryImpl createPCMJavaMetarepository() {
        final MetaRepositoryImpl metarepository = new MetaRepositoryImpl();

        final VURI pcmMMUri = VURI.getInstance(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE);
        final Set<String> pcmNSUris = new HashSet<String>();
        pcmNSUris.add(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE_URI);
        pcmNSUris.add(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE_URI_REPOSITORY);
        final Metamodel pcmMM = new Metamodel(pcmNSUris, pcmMMUri, PCMJavaNamespace.REPOSITORY_FILE_EXTENSION);
        metarepository.addMetamodel(pcmMM);

        final VURI jaMoPPURI = VURI.getInstance(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Set<String> jamoppNSURIs = new HashSet<String>();
        for (String nsuri : PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE_URIS) {
            jamoppNSURIs.add(nsuri);
        }
        final Metamodel jaMoPPMM = new Metamodel(jamoppNSURIs, jaMoPPURI, new JaMoPPTUIDCalculatorAndResolver(),
                PCMJavaNamespace.JAVA_FILE_EXTENSION);
        metarepository.addMetamodel(jaMoPPMM);

        final Mapping pcmJavaMapping = new Mapping(pcmMM, jaMoPPMM);
        metarepository.addMapping(pcmJavaMapping);
        return metarepository;
    }

}
