package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectInput;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ViewType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

public class MetaRepositoryTest {

    private static final Logger logger = Logger.getLogger(MetaRepositoryTest.class.getSimpleName());

    public static final String PROJECT_URI = "MockupProject";
    public static final String PCM_FILE_EXT = "pcm_mockup";

    protected static final String PCM_MM_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.pcm_mockup";
    protected static final String UML_MM_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.uml_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";
    protected static final String PCM_UML_VT_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.pcm_uml_mockup_VT";

    @BeforeClass
    public static void beforeClass() {
        // initialize Logger when not done yet
        if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            Logger.getRootLogger().addAppender(new ConsoleAppender());
            Logger.getRootLogger().setLevel(Level.ALL);
        }
    }

    @Test
    public void testAll() {

        testAddMapping(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT);

        testAddViewType(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT, PCM_UML_VT_URI);

        testGetProjectInput(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT, PCM_UML_VT_URI);

        // generiere VSUM plugins (jetzt erst mal hart verdrahtet)

        // TODO BBB KEEP ON WORKING HERE
    }

    @AfterClass
    public static void tearDown() {
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
        String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_");
        IPath destinationPath = new Path("/" + VSUMConstants.VSUM_PROJECT_NAME + timestamp);
        try {
            project.open(new NullProgressMonitor());
            project.move(destinationPath, true, new NullProgressMonitor());
        } catch (CoreException e) {
            logger.warn("Could not move " + VSUMConstants.VSUM_PROJECT_NAME + "project to folder. " + destinationPath
                    + ". Reason: " + e);
        }
    }

    public MetaRepositoryImpl testMetaRepository() {
        MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
        assertNotNull(metaRepository);
        return metaRepository;
    }

    public Metamodel testAddMetamodel(final MetaRepositoryImpl metaRepository, final String nsURI, final VURI uri,
            final String fileExt) {
        Metamodel mm = new Metamodel(nsURI, uri, fileExt);
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
        VURI uri1 = VURI.getInstance(mm1URIString);
        Metamodel mm1 = testAddMetamodel(metaRepository, mm1URIString, uri1, fileExt1);

        VURI uri2 = VURI.getInstance(mm2URIString);
        Metamodel mm2 = testAddMetamodel(metaRepository, mm2URIString, uri2, fileExt2);

        Mapping mapping = new Mapping(mm1, mm2);
        metaRepository.addMapping(mapping);

        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping, mapping2);

        return new Pair<VURI, VURI>(uri1, uri2);
    }

    public void testAddViewType(final String uri1String, final String fileExt1, final String uri2String,
            final String fileExt2, final String viewTypeURIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();

        VURI uri1 = VURI.getInstance(uri1String);
        testAddMetamodel(metaRepository, uri1String, uri1, fileExt1);

        VURI uri2 = VURI.getInstance(uri2String);
        testAddMetamodel(metaRepository, uri2String, uri2, fileExt2);

        testAddViewType(metaRepository, uri1, uri2, viewTypeURIString);
    }

    public VURI testAddViewType(final MetaRepositoryImpl metaRepository, final VURI uri1, final VURI uri2,
            final String viewTypeURIString) {

        VURI viewTypeURI = VURI.getInstance(viewTypeURIString);
        ViewType vt = new ViewType(viewTypeURI, uri1, uri2);
        metaRepository.addViewType(vt);

        ViewType vt2 = metaRepository.getViewType(viewTypeURI);
        assertEquals(vt, vt2);

        return viewTypeURI;
    }

    private void testGetProjectInput(final String uri1String, final String fileExt1, final String uri2String,
            final String fileExt2, final String viewTypeURIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();

        Pair<VURI, VURI> mmURIs = testAddMapping(metaRepository, uri1String, fileExt1, uri2String, fileExt2);

        VURI uri1 = mmURIs.getFirst();
        VURI uri2 = mmURIs.getSecond();

        VURI vtURI = testAddViewType(metaRepository, uri1, uri2, viewTypeURIString);

        ProjectInput projectInput = metaRepository.getProjectInput();
        assertMetamodels(uri1, metaRepository, projectInput);
        assertMetamodels(uri2, metaRepository, projectInput);
        assertViewType(vtURI, metaRepository, projectInput);
        assertMapping(uri1, uri2, metaRepository, projectInput);
    }

    private void assertMetamodels(final VURI uri1, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableMap<VURI, Metamodel> uri2MetamodelMap = projectInput.getUri2MetamodelMap();
        Metamodel mm11 = uri2MetamodelMap.get(uri1);
        Metamodel mm12 = metaRepository.getMetamodel(uri1);
        assertEquals(mm11, mm12);
    }

    private void assertViewType(final VURI vtURI, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableMap<VURI, ViewType> uri2ViewTypeMap = projectInput.getUri2ViewTypeMap();
        ViewType viewType1 = uri2ViewTypeMap.get(vtURI);
        ViewType viewType2 = metaRepository.getViewType(vtURI);
        assertEquals(viewType1, viewType2);
    }

    private void assertMapping(final VURI uri1, final VURI uri2, final MetaRepositoryImpl metaRepository,
            final ProjectInput projectInput) {
        ClaimableConcatMap<VURI, Mapping> uris2MappingMap = projectInput.getUris2MappingMap();
        Mapping mapping1 = uris2MappingMap.get(uri1, uri2);
        Mapping mapping2 = metaRepository.getMapping(uri1, uri2);
        assertEquals(mapping1, mapping2);
    }
}
