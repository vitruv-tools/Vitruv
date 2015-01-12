package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public abstract class PCMJaMoPPTransformationTestBase {

    protected ResourceSet resourceSet;

    protected abstract void afterTest();

    protected abstract CorrespondenceInstance getCorrespondenceInstance() throws Throwable;

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

}
