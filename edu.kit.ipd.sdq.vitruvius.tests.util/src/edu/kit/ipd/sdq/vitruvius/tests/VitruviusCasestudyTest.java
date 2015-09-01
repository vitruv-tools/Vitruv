package edu.kit.ipd.sdq.vitruvius.tests;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaBridge;
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

    protected void setUserInteractor(final UserInteracting newUserInteracting,
            final ChangeSynchronizerImpl changeSynchronizerImpl) throws Throwable {
        final Change2CommandTransformingProviding change2CommandProviding = JavaBridge.getFieldFromClass(
                ChangeSynchronizerImpl.class, "change2CommandTransformingProviding", changeSynchronizerImpl);
        final ClaimableMap<Pair<VURI, VURI>, Change2CommandTransforming> transformationExecuterMap = JavaBridge
                .getFieldFromClass(Change2CommandTransformingProvidingImpl.class, "transformationExecuterMap",
                        change2CommandProviding);
        final Class<?> change2CommandTransformerClass = this.getChange2CommandTransformerClass();
        final String nameOfChangeSynchronizerField = this.getNameOfChangeSynchronizerField();
        Change2CommandTransforming change2CommandTransforming = null;
        for (final Change2CommandTransforming currentChange2CommandTransforming : transformationExecuterMap.values()) {
            if (change2CommandTransformerClass.isInstance(currentChange2CommandTransforming)) {
                change2CommandTransforming = currentChange2CommandTransforming;
                break;
            }
        }
        if (null == change2CommandTransforming) {
            throw new RuntimeException("Could not find a Change2CommandTransforming that is currently active.");
        }
        final TransformationExecuter transformationExecuter = JavaBridge.getFieldFromClass(change2CommandTransformerClass,
                nameOfChangeSynchronizerField, change2CommandTransforming);
        transformationExecuter.setUserInteracting(newUserInteracting);
    }

    protected abstract Class<?> getChange2CommandTransformerClass();

    protected String getNameOfChangeSynchronizerField() {
        return "transformationExecuter";
    }
}
