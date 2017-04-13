package tools.vitruv.framework.tests.vsum;

import java.util.ArrayList;
import java.util.List;

import pcm_mockup.Pcm_mockupPackage;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelRepositoryImpl;
import tools.vitruv.framework.tests.VitruviusTest;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import uml_mockup.Uml_mockupPackage;

public abstract class AbstractVsumTest extends VitruviusTest {
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

    protected InternalVirtualModel createMetaRepositoryAndVsum(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        List<Metamodel> metamodels = new ArrayList<Metamodel>();
        metamodels.add(new Metamodel(VURI.getInstance(mm1URIString), mm1URIString,
                new AttributeTuidCalculatorAndResolver(mm1URIString, "id"), fileExt1));
        metamodels.add(new Metamodel(VURI.getInstance(mm2URIString), mm2URIString,
                new AttributeTuidCalculatorAndResolver(mm2URIString, "id"), fileExt2));
        return TestUtil.createVirtualModel(VSUM_NAME, metamodels);
    }

}
