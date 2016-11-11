package tools.vitruv.applications.pcmjava.util;

import java.util.ArrayList;
import java.util.List;

import tools.vitruv.domains.pcm.PcmDomain;
import tools.vitruv.domains.java.JavaDomain;
import tools.vitruv.framework.metamodel.Metamodel;

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
        result.add(new PcmDomain().getMetamodel());
        result.add(new JavaDomain().getMetamodel());
        return result;
    }

}
