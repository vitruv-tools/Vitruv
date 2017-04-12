package tools.vitruv.framework.tests.vsum;

import static org.junit.Assert.fail;

import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.BeforeClass;

import pcm_mockup.Pcm_mockupPackage;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelRepositoryImpl;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.datatypes.VURI;
import uml_mockup.Uml_mockupPackage;

public class MetaRepositoryTest {
    protected static final String PROJECT_FOLDER_NAME = "MockupProject";
    protected static final String VSUM_NAME = "VsumProject";

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
        // TestUtil.deleteAllProjectFolderCopies(PROJECT_FOLDER_NAME);
        // TestUtil.clearMetaProject(VSUM_NAME);
    }

    @Before
    public void beforeTest() {
        try {
            this.currentProjectFolderName = TestUtil.createProject(PROJECT_FOLDER_NAME, true).getName();
        } catch (CoreException e) {
            fail("Exception during creation of test project");
        }
        TuidManager.getInstance().reinitialize();
    }

    public Metamodel testAddMetamodel(final MetamodelRepositoryImpl metaRepository, final String nsURI, final VURI uri,
            final String fileExt) {
        Metamodel mm = TestUtil.createMetamodel(nsURI, uri, fileExt);
        metaRepository.addMetamodel(mm);
        return mm;
    }

}
