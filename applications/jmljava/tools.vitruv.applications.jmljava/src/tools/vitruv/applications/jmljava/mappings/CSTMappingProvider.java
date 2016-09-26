package tools.vitruv.applications.jmljava.mappings;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import tools.vitruv.framework.metamodel.MetamodelPair;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelRepository;

/**
 * Mapping provider for code, specifications and tests. This basically only maps Java on JML and
 * vice versa.
 */
public class CSTMappingProvider implements MappingProvider {

    private static final Logger LOGGER = Logger.getLogger(CSTMappingProvider.class);

    @Override
    public Iterable<MetamodelPair> getMappings(MetamodelRepository mmManaging) {
        LOGGER.debug("Constructing mappings for Java and JML.");

        List<MetamodelPair> mappings = new ArrayList<MetamodelPair>();

        final Metamodel mmJava = mmManaging.getMetamodel("java");
        final Metamodel mmJML = mmManaging.getMetamodel("jml");

        mappings.add(new MetamodelPair(mmJava, mmJML)); // this is bidirectional

        return mappings;
    }

}
