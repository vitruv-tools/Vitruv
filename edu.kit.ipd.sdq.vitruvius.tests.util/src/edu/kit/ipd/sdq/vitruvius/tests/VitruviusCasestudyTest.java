package edu.kit.ipd.sdq.vitruvius.tests;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

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
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * Base class for all Vitruvius case study tests
 *
 * @author langhamm
 *
 */
public abstract class VitruviusCasestudyTest {

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
            VitruviusCasestudyTest.this.afterTest();
            VitruviusCasestudyTest.this.resourceSet = null;
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
        final Class<?> emfModelTransformationExecuterClass = this.getEMFModelTransformationExecuterClass();
        final String nameOfChangeSynchronizerField = this.getNameOfChangeSynchronizerField();
        EMFModelTransformationExecuting emfTransformationExecuter = null;
        for (final EMFModelTransformationExecuting emfModelTransformationExecuting : transformationExecuterMap.values()) {
            if (emfModelTransformationExecuterClass.isInstance(emfModelTransformationExecuting)) {
                emfTransformationExecuter = emfModelTransformationExecuting;
                break;
            }
        }
        if (null == emfTransformationExecuter) {
            throw new RuntimeException("Could not find an EMFModelTransformationExecuting that is currently active.");
        }
        final ChangeSynchronizer changeSynchronizer = TestUtil.getFieldFromClass(emfModelTransformationExecuterClass,
                nameOfChangeSynchronizerField, emfTransformationExecuter);
        changeSynchronizer.setUserInteracting(newUserInteracting);
    }

    protected abstract Class<?> getEMFModelTransformationExecuterClass();

    protected String getNameOfChangeSynchronizerField() {
        return "changeSynchronizer";
    }
}
