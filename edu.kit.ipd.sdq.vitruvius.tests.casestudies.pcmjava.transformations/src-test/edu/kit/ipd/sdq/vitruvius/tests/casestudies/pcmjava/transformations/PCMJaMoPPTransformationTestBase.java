package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.jamopppcm.util.JaMoPPPCMTestUtil;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class PCMJaMoPPTransformationTestBase {

    protected VSUMImpl vsum;
    protected MetaRepositoryImpl metaRepository;
    private SyncManagerImpl syncManager;
    protected ChangeRecorder changeRecorder;
    ChangeDescription2ChangeConverter changeDescrition2ChangeConverter;

    private CorrespondenceInstance correspondenceInstance;
    protected ResourceSet resourceSet;

    @BeforeClass
    public static void setUpAllTests() {
        TestUtil.initializeLogger();
    }

    /**
     * Set up SyncMangaer and metaRepository facility. Creates a fresh VSUM, Metarepository etc.
     * before each test
     *
     * @throws Exception
     */
    @Before
    public void setUpTest() throws Exception {
        
    	metaRepository = JaMoPPPCMTestUtil.createJaMoPPPCMMetaRepository();
        this.vsum = TestUtil.createVSUM(metaRepository);
        final TransformationExecutingProvidingImpl syncTransformationProvider = new TransformationExecutingProvidingImpl();
        final EMFModelPropagationEngineImpl propagatingChange = new EMFModelPropagationEngineImpl(
                syncTransformationProvider);
        this.syncManager = new SyncManagerImpl(this.vsum, propagatingChange, this.vsum, metaRepository, this.vsum);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new ChangeRecorder();
        this.changeDescrition2ChangeConverter = new ChangeDescription2ChangeConverter();
    }

    /**
     * Test watcher that moves src files (which are created during the previous test) to a own
     * folder
     */
    @Rule
    public TestWatcher watchmen = new TestWatcher() {
        @Override
        protected void finished(final org.junit.runner.Description description) {
            PCMJaMoPPTransformationTestBase.this.correspondenceInstance = null;
            PCMJaMoPPTransformationTestBase.this.resourceSet = null;
            final String previousMethodName = description.getMethodName();
            TestUtil.moveSrcFilesFromMockupProjectToPathWithTimestamp(previousMethodName);
            TestUtil.moveModelFilesFromMockupProjectToPathWithTimestamp(previousMethodName);
            TestUtil.moveVSUMProjectToOwnFolderWithTimepstamp(previousMethodName);
        };
    };

    public String getProjectPath() {
        return TestUtil.PROJECT_URI + "/";
    }

    private CorrespondenceInstance getCorrespondenceInstanceForProject(final String projectName) throws Throwable {
        final VURI pcmMMUri = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        return this.vsum.getCorrespondenceInstanceOriginal(pcmMMUri, jaMoPPURI);
    }

    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        if (null == this.correspondenceInstance) {
            this.correspondenceInstance = this.getCorrespondenceInstanceForProject(TestUtil.PROJECT_URI);
        }
        return this.correspondenceInstance;
    }

    protected void triggerSynchronization(final VURI vuri) {
        final ChangeDescription cd = this.changeRecorder.endRecording();
        cd.applyAndReverse();
        final List<Change> changes = this.changeDescrition2ChangeConverter.getChanges(cd, vuri);
        cd.applyAndReverse();
        this.syncManager.synchronizeChanges(changes);
        this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
    }

    protected void triggerSynchronization(final EObject eObject) {
        final VURI vuri = VURI.getInstance(eObject.eResource());
        this.triggerSynchronization(vuri);
    }

    protected void synchronizeFileChange(final FileChangeKind fileChangeKind, final VURI vuri) {
        final FileChange fileChange = new FileChange(fileChangeKind, vuri);
        this.syncManager.synchronizeChange(fileChange);
    }
}
