package tools.vitruvius.framework.tests.vsum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;

import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metarepository.MetaRepositoryImpl;
import tools.vitruvius.framework.tests.util.TestUtil;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.framework.util.datatypes.VURI;
import pcm_mockup.Pcm_mockupPackage;
import uml_mockup.Uml_mockupPackage;

public class MetaRepositoryTest {
    protected static final String PROJECT_FOLDER_NAME = "MockupProject";

    protected static final String PCM_MM_URI = Pcm_mockupPackage.eNS_URI;
    protected static final String UML_MM_URI = Uml_mockupPackage.eNS_URI;
    protected static final String PCM_FILE_EXT = "pcm_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";

    private String currentProjectFolderName = null;

    public String getCurrentProjectFolderName() {
        return this.currentProjectFolderName;
    }

    @BeforeClass
    public static void beforeClass() {
        // initialize Logger when not done yet
        TestUtil.initializeLogger();
        TestUtil.deleteAllProjectFolderCopies(PROJECT_FOLDER_NAME);
        TestUtil.clearMetaProject();
    }

    @Before
    public void beforeTest() {
        this.currentProjectFolderName = TestUtil.createProjectFolderWithTimestamp(PROJECT_FOLDER_NAME);
    }

    public void testAll() {
        testAddMapping(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT);
    }

    public MetaRepositoryImpl testMetaRepository() {
        MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
        assertNotNull(metaRepository);
        return metaRepository;
    }

    public Metamodel testAddMetamodel(final MetaRepositoryImpl metaRepository, final String nsURI, final VURI uri,
            final String fileExt) {
        Metamodel mm = TestUtil.createMetamodel(nsURI, uri, fileExt);
        metaRepository.addMetamodel(mm);
        return mm;
    }

    public void testAddMapping(final String uri1String, final String fileExt1, final String uri2String,
            final String fileExt2) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        addMapping(metaRepository, uri1String, fileExt1, uri2String, fileExt2);
    }

    public Pair<VURI, VURI> addMapping(final MetaRepositoryImpl metaRepository, final String mm1URIString,
            final String fileExt1, final String mm2URIString, final String fileExt2) {

        Mapping mapping = TestUtil.addMappingToRepository(metaRepository, mm1URIString, fileExt1, mm2URIString,
                fileExt2);

        VURI uri1 = mapping.getMetamodelA().getURI();
        VURI uri2 = mapping.getMetamodelB().getURI();

        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping, mapping2);

        return new Pair<VURI, VURI>(uri1, uri2);
    }

}
