package edu.kit.ipd.sdq.vitruvius.tests.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectInput;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class MetaRepositoryTest {
    protected static final String PROJECT_FOLDER_NAME = "MockupProject";

    protected static final String PCM_MM_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.pcm_mockup";
    protected static final String UML_MM_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.uml_mockup";
    protected static final String PCM_FILE_EXT = "pcm_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";
    protected static final String PCM_UML_VT_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.pcm_uml_mockup_VT";

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
        this.currentProjectFolderName = TestUtil.copyProjectFolder(PROJECT_FOLDER_NAME);
    }

    @Test
    public void testAll() {

        testAddMapping(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT);

        testGetProjectInput(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT, PCM_UML_VT_URI);

        // generiere VSUM plugins (jetzt erst mal hart verdrahtet)
    }

    @After
    public void afterTest() {
        // empty
    }

    @AfterClass
    public static void afterClass() {
        // TestUtil.moveVSUMProjectToOwnFolder();
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
        testAddMapping(metaRepository, uri1String, fileExt1, uri2String, fileExt2);
    }

    public Pair<VURI, VURI> testAddMapping(final MetaRepositoryImpl metaRepository, final String mm1URIString,
            final String fileExt1, final String mm2URIString, final String fileExt2) {

        Mapping mapping = TestUtil.addMappingToRepository(metaRepository, mm1URIString, fileExt1, mm2URIString,
                fileExt2);

        VURI uri1 = mapping.getMetamodelA().getURI();
        VURI uri2 = mapping.getMetamodelB().getURI();

        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping, mapping2);

        return new Pair<VURI, VURI>(uri1, uri2);
    }

    private void testGetProjectInput(final String uri1String, final String fileExt1, final String uri2String,
            final String fileExt2, final String viewTypeURIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();

        Pair<VURI, VURI> mmURIs = testAddMapping(metaRepository, uri1String, fileExt1, uri2String, fileExt2);

        VURI uri1 = mmURIs.getFirst();
        VURI uri2 = mmURIs.getSecond();

        ProjectInput projectInput = metaRepository.getProjectInput();
        assertMetamodels(uri1, metaRepository, projectInput);
        assertMetamodels(uri2, metaRepository, projectInput);
        assertMapping(uri1, uri2, metaRepository, projectInput);
    }

    private void assertMetamodels(final VURI uri1, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableMap<VURI, Metamodel> uri2MetamodelMap = projectInput.getUri2MetamodelMap();
        Metamodel mm11 = uri2MetamodelMap.get(uri1);
        Metamodel mm12 = metaRepository.getMetamodel(uri1);
        assertEquals(mm11, mm12);
    }

    private void assertMapping(final VURI uri1, final VURI uri2, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableConcatMap<VURI, Mapping> uris2MappingMap = projectInput.getUris2MappingMap();
        Mapping mapping1 = uris2MappingMap.get(uri1, uri2);
        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping1, mapping2);
    }
}
