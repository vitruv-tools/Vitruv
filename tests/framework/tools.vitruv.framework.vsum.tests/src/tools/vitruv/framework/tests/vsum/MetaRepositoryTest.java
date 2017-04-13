package tools.vitruv.framework.tests.vsum;

import pcm_mockup.Pcm_mockupPackage;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelRepositoryImpl;
import tools.vitruv.framework.tests.VitruviusTest;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.util.datatypes.VURI;
import uml_mockup.Uml_mockupPackage;

public class MetaRepositoryTest extends VitruviusTest {
    protected static final String PROJECT_FOLDER_NAME = "MockupProject";
    protected static final String VSUM_NAME = "VsumProject";

    protected static final String PCM_MM_URI = Pcm_mockupPackage.eNS_URI;
    protected static final String UML_MM_URI = Uml_mockupPackage.eNS_URI;
    protected static final String PCM_FILE_EXT = "pcm_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";

    public String getCurrentProjectFolderName() {
        return getCurrentTestProject().getName();
    }

    public Metamodel testAddMetamodel(final MetamodelRepositoryImpl metaRepository, final String nsURI, final VURI uri,
            final String fileExt) {
        Metamodel mm = TestUtil.createMetamodel(nsURI, uri, fileExt);
        metaRepository.addMetamodel(mm);
        return mm;
    }

}
