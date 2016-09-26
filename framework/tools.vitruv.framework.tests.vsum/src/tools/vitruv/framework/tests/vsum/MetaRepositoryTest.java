package tools.vitruv.framework.tests.vsum;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;

import pcm_mockup.Pcm_mockupPackage;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metarepository.MetaRepositoryImpl;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.datatypes.VURI;
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
        TuidManager.getInstance().reinitialize();
    }

    public MetaRepositoryImpl createMetaRepository() {
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

}
