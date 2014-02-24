package edu.kit.ipd.sdq.vitruvius.tests.framework.run.SynchronisationTest;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.emftext.language.java.classifiers.Interface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.MonitoredEmfEditorImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;

public class SynchronisationTest {

    private static final Logger logger = Logger.getLogger(SynchronisationTest.class.getSimpleName());

    private static final String PROJECT_NAME = "MockupProject";
    private static final String MODEL_PATH = "testModels";
    private static final String MODEL_PATH_TMP = MODEL_PATH + "Tmp";
    private static final String PCM_REPOSITORY_FILE_NAME = MODEL_PATH + "/" + "pcmRepoTest.repository";
    private static final String PCM_REPOSITORY_NAME = "testRepository";
    private static final String PCM_INTERFACE_CREATION_NAME = "TestCreateInterface";
    private static final String PCM_MM_URI = "http://sdq.ipd.uka.de/PalladioComponentModel/5.0";

    private MonitoredEmfEditorImpl monitor;
    private VURI sourceModelURI;
    private SyncManagerImpl syncManager;
    private MetaRepositoryImpl metaRepository;

    @Before
    public void setUp() throws Exception {
        // set up syncManager, monitor and metaRepostitory
        initializeMetaRepository();
        SyncManagerImpl.setMetaRepositoryImpl(this.metaRepository);
        this.syncManager = SyncManagerImpl.getSyncManagerInstance();
        this.monitor = new MonitoredEmfEditorImpl(this.syncManager, this.syncManager.getModelProviding());
        // create pcm model instance
        Repository repository = RepositoryFactory.eINSTANCE.createRepository();
        repository.setEntityName(PCM_REPOSITORY_NAME);
        URI emfURI = URI.createFileURI(PROJECT_NAME + "/" + PCM_REPOSITORY_FILE_NAME);
        this.sourceModelURI = VURI.getInstance(emfURI.toString());
        // HACK: Set EMF URI in vuri to emfUri:
        Field f = this.sourceModelURI.getClass().getDeclaredField("emfURI");
        f.setAccessible(true);
        f.set(this.sourceModelURI, emfURI);

        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put("repository", new PcmResourceFactoryImpl());
        Resource resource = resourceSet.createResource(this.sourceModelURI.getEMFUri());
        if (null == resource) {
            fail("Could not create resource with URI: " + emfURI.toString());
            return;
        }
        resource.getContents().add(repository);
        Map<Object, Object> options = new HashMap<Object, Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            logger.warn("createRepositoryFile failed: Could not save resource: " + resource.getURI() + ". Exception: "
                    + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        createFileChange(this.sourceModelURI);
        addContentAdapter(repository);
        createInterface(repository);
    }

    private void initializeMetaRepository() {
        this.metaRepository = new MetaRepositoryImpl();
        VURI pcmMMUri = VURI.getInstance(PCM_MM_URI);
        // VURI jamoppMMUri = VURI.getInstance("");
        Metamodel pcmMM = new Metamodel(pcmMMUri, "repository");
        // Metamodel jamoppMM = new Metamodel(jamoppMMUri, "java");
        this.metaRepository.addMetamodel(pcmMM);
        // this.metaRepository.addMetamodel(jamoppMM);
    }

    /**
     * create an OperaitonInterface
     * 
     * @param repository
     */
    private void createInterface(final Repository repository) {
        OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setEntityName(PCM_INTERFACE_CREATION_NAME);
        opInterface.setRepository__Interface(repository);
    }

    /**
     * add monitor to root object of new file
     * 
     * @param resource
     *            resource containing the root EObject of the new file
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private void addContentAdapter(final EObject eObject) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field f = this.monitor.getClass().getDeclaredField("emfMonitorAdapter");
        f.setAccessible(true);
        EContentAdapter contentAdapter = (EContentAdapter) f.get(this.monitor);
        eObject.eAdapters().add(contentAdapter);
    }

    /**
     * synchronize file create, which internally creates a emf change for creation of root object in
     * repository model
     */
    private void createFileChange(final VURI vuri) {

        FileChange fileChange = new FileChange(FileChangeKind.CREATE);
        List<Change> changes = new ArrayList<Change>(1);
        changes.add(fileChange);
        this.syncManager.synchronizeChanges(changes, vuri);
    }

    @After
    public void tearDown() throws Exception {
        // TODO: move instances to modelInstanceName_tmp

    }

    /**
     * Triggers the synchronisation of the model. Should synchronize the creation of the interface.
     */
    @Test
    public void triggerSynchronisation() {
        this.monitor.triggerSynchronisation(this.sourceModelURI);
        // a java interface in the package "testRepository" should have been created
        String javaInterfaceLocation = "src/" + PCM_REPOSITORY_NAME + "/" + PCM_INTERFACE_CREATION_NAME + ".java";
        VURI interfaceVURI = VURI.getInstance(javaInterfaceLocation);
        ModelInstance newInterface = this.syncManager.getModelProviding().getModelInstanceCopy(interfaceVURI);

        logger.debug(newInterface + " contains: " + newInterface.getResource().getContents());
        for (EObject eObj : newInterface.getResource().getContents()) {
            if (eObj instanceof Interface) {
                Interface jamoppInterface = (Interface) eObj;
                logger.debug("Interface: " + jamoppInterface.getName() + " found (expected "
                        + PCM_INTERFACE_CREATION_NAME + ")");
                if (jamoppInterface.getName().equals(PCM_INTERFACE_CREATION_NAME)) {
                    return;
                }
            }
        }
        fail("Interface " + PCM_INTERFACE_CREATION_NAME + " not found in model " + interfaceVURI);
    }
}
