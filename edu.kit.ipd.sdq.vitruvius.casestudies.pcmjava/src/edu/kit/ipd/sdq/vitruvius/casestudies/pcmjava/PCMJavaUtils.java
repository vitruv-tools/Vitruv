package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp.JaMoPPTUIDCalculatorAndResolver;
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
        // PCM
        final VURI pcmMMUri = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final Set<String> pcmNSUris = new HashSet<String>();
        pcmNSUris.addAll(Arrays.asList(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE_URIS));
        String[] fileExtensions = new String[2];
        fileExtensions[0] = PCMJaMoPPNamespace.PCM.REPOSITORY_FILE_EXTENSION;
        fileExtensions[1] = PCMJaMoPPNamespace.PCM.SYSTEM_FILE_EXTENSION;
        final Metamodel pcmMM = new Metamodel(pcmNSUris, pcmMMUri, fileExtensions);
        metarepository.addMetamodel(pcmMM);
        // JaMoPP
        final VURI jaMoPPURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final Set<String> jamoppNSURIs = new HashSet<String>();
        jamoppNSURIs.addAll(Arrays.asList(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE_URIS));
        final Metamodel jaMoPPMM = new Metamodel(jamoppNSURIs, jaMoPPURI, new JaMoPPTUIDCalculatorAndResolver(),
                PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION);
        metarepository.addMetamodel(jaMoPPMM);

        final Mapping pcmJavaMapping = new Mapping(pcmMM, jaMoPPMM);
        metarepository.addMapping(pcmJavaMapping);
        return metarepository;
    }

}
