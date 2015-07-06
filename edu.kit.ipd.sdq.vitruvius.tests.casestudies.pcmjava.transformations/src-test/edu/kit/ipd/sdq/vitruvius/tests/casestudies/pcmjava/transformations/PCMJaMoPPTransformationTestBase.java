package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuterBase;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.ChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm.TestUserInteractor;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public abstract class PCMJaMoPPTransformationTestBase {

    protected ResourceSet resourceSet;

    protected TestUserInteractor testUserInteractor;

    protected abstract void afterTest();

    protected abstract CorrespondenceInstance getCorrespondenceInstance() throws Throwable;

    protected void beforeTest() throws Throwable {
    }

    @Before
    public void beforeEachTest() throws Throwable {
        this.beforeTest();
    }

    @BeforeClass
    public static void setUpAllTests() {
        TestUtil.initializeLogger();
    }

    /**
     * Test watcher that moves src and model files as well as the VSUM project (which are created
     * during the previous test) to own folders and removes the PCMJavaBuilder from the project
     */
    @Rule
    public TestWatcher watchmen = new TestWatcher() {
        @Override
        protected void finished(final org.junit.runner.Description description) {
            PCMJaMoPPTransformationTestBase.this.afterTest();
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

    protected void setUserInteractor(final UserInteracting newUserInteracting, final SyncManagerImpl syncManagerImpl)
            throws Throwable {
        final EMFModelPropagationEngineImpl emfModelPropagationEngineImpl = TestUtil.getFieldFromClass(
                SyncManagerImpl.class, "changePropagating", syncManagerImpl);
        final TransformationExecutingProvidingImpl transformationExecutingProvidingImpl = TestUtil.getFieldFromClass(
                EMFModelPropagationEngineImpl.class, "transformationExecutingProviding", emfModelPropagationEngineImpl);
        final ClaimableMap<Pair<VURI, VURI>, EMFModelTransformationExecuting> transformationExecuterMap = TestUtil
                .getFieldFromClass(TransformationExecutingProvidingImpl.class, "transformationExecuterMap",
                        transformationExecutingProvidingImpl);
        PCMJaMoPPTransformationExecuterBase pcmJaMoPPTransformationExecuter = null;
        for (final EMFModelTransformationExecuting emfModelTransformationExecuting : transformationExecuterMap.values()) {
            if (emfModelTransformationExecuting instanceof PCMJaMoPPTransformationExecuterBase) {
                pcmJaMoPPTransformationExecuter = (PCMJaMoPPTransformationExecuterBase) emfModelTransformationExecuting;
                break;
            }
        }
        if (null == pcmJaMoPPTransformationExecuter) {
            throw new RuntimeException("Could not find an PCMJaMoPPTransformationExecuter that is currently active.");
        }
        final ChangeSynchronizer changeSynchronizer = TestUtil.getFieldFromClass(
                PCMJaMoPPTransformationExecuterBase.class, "changeSynchronizer", pcmJaMoPPTransformationExecuter);
        changeSynchronizer.setUserInteracting(newUserInteracting);
    }
}
