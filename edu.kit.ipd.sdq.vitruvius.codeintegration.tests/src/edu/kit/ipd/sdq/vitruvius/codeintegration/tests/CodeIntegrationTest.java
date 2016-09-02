package edu.kit.ipd.sdq.vitruvius.codeintegration.tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.events.BuildCommand;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.internal.resources.ProjectInfo;
import org.eclipse.core.internal.resources.ResourceInfo;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.builder.PCMJavaAddBuilder;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.builder.PCMJavaBuilder;
import edu.kit.ipd.sdq.vitruvius.domains.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.domains.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm.util.CodeIntegrationUtils;

@SuppressWarnings("restriction")
public class CodeIntegrationTest {

    private static final Logger logger = Logger.getLogger(CodeIntegrationTest.class.getSimpleName());

    private static final String META_PROJECT_NAME = "vitruvius.meta";
    private IProject testProject;
    private IWorkspace workspace;

    @Before
    public void beforeTest() throws InvocationTargetException, InterruptedException, IOException, URISyntaxException {
        this.workspace = ResourcesPlugin.getWorkspace();

        CodeIntegrationUtils.importTestProjectFromBundleData(this.workspace, getTestProjectName(), getTestBundleName(), getTestSourceAndModelFolder());

        final IProject project = this.workspace.getRoot().getProject(this.getTestProjectName());
        assert project != null;
        this.testProject = project;
    }

    protected String getTestProjectName() {
        return CodeIntegrationTestCBSNamespace.TEST_PROJECT_NAME;
    }

    protected String getTestBundleName() {
        return CodeIntegrationTestCBSNamespace.TEST_BUNDLE_NAME;
    }

    protected String getTestSourceAndModelFolder() {
        return CodeIntegrationTestCBSNamespace.SOURCE_CODE_PATH;
    }

    protected IProject getTestProject() {
        return this.testProject;
    }


    @After
    public void afterTest() throws CoreException, InterruptedException {
        // Delete test project
        final IProject testProject = this.workspace.getRoot().getProject(this.getTestProjectName());
        if (testProject.exists()) {
            final DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
            testProject.delete(true, true, progress);
            while (!progress.isDone()) {
                Thread.sleep(100);
            }
        }

        // Delete vitruvius.meta project
        final IProject metaProject = this.workspace.getRoot().getProject(META_PROJECT_NAME);
        if (metaProject.exists()) {
            final DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
            metaProject.delete(true, true, progress);
            while (!progress.isDone()) {
                Thread.sleep(100);
            }
        }
    }

    @Test
    public void testStandardCodeIntegration() throws Throwable {
        CodeIntegrationUtils.integratProject(getTestProject());

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject metaProject = workspace.getRoot().getProject(META_PROJECT_NAME);
        DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
        metaProject.refreshLocal(IResource.DEPTH_INFINITE, progress);
        while (!progress.isDone()) {
            Thread.sleep(100);
        }
        Assert.assertNotNull(metaProject);

        final IFolder corrFolder = metaProject.getFolder("correspondence");
        Assert.assertNotNull(corrFolder);
        final IResource[] members = corrFolder.members();

        final Stream<IResource> correspondenceFiles = Arrays.asList(members).stream().filter(r -> r instanceof IFile);
        Assert.assertEquals(1, correspondenceFiles.filter(r -> r.getName().endsWith(".correspondence")).count());

        final IFolder vsumFolder = metaProject.getFolder("vsum");
        Assert.assertNotNull(vsumFolder);

        // add PCM Java Builder to Project under test
        final PCMJavaAddBuilder pcmJavaBuilder = new PCMJavaAddBuilder();
        pcmJavaBuilder.addBuilderToProject(this.testProject);
        // build the project
        progress = new DoneFlagProgressMonitor();
        this.testProject.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, PCMJavaBuilder.BUILDER_ID,
                new HashMap<String, String>(), progress);
        while (!progress.isDone()) {
            Thread.sleep(100);
        }

        this.assertStandardCodeIntegrationTest();

    }

    protected void assertStandardCodeIntegrationTest() throws Throwable {
        final CorrespondenceModel ci = this.getCorrespondenceModel();
        // TODO check if correspondences are correct
        final Set<Correspondence> correspondences = ci.getAllCorrespondencesWithoutDependencies();
        Assert.assertNotNull(correspondences);
        Assert.assertFalse(correspondences.isEmpty());

        final TUID frameCodeTuid = TUID.valueOf(
                "http://www.emftext.org/java#platform:/resource/eu.fpetersen.cbs.pc/src/eu/fpetersen/cbs/pc/data/Frame.java#classifier-_-Frame");
        final List<TUID> frameCodeTuids = Collections.singletonList(frameCodeTuid);
        final Set<Correspondence> frameCodeCorrs = ci.getCorrespondencesForTUIDs(frameCodeTuids);

        Assert.assertNotNull(frameCodeCorrs);
        Assert.assertFalse(frameCodeCorrs.isEmpty());
        Assert.assertEquals(1, frameCodeCorrs.size());

        final Correspondence frameCorr = frameCodeCorrs.iterator().next();
        if (frameCorr.getATUIDs().contains(frameCodeTuid)) {
            final EList<TUID> bs = frameCorr.getBTUIDs();
            Assert.assertNotNull(bs);
            Assert.assertEquals(1, bs.size());

            final TUID b = bs.get(0);
            Assert.assertEquals(
                    TUID.valueOf(
                            "http://palladiosimulator.org/PalladioComponentModel/Repository/5.1#platform:/resource/eu.fpetersen.cbs.pc/model/internal_architecture_model.repository#_auhdcMWvEeWLAeSW2tt_XQ#_auwuAMWvEeWLAeSW2tt_XQ"),
                    b);
        } else if (frameCorr.getBTUIDs().contains(frameCodeTuid)) {
            final EList<TUID> as = frameCorr.getATUIDs();
            Assert.assertNotNull(as);
            Assert.assertEquals(1, as.size());

            final TUID a = as.get(0);
            Assert.assertEquals(
                    TUID.valueOf(
                            "http://palladiosimulator.org/PalladioComponentModel/Repository/5.1#platform:/resource/eu.fpetersen.cbs.pc/model/internal_architecture_model.repository#_auhdcMWvEeWLAeSW2tt_XQ#_auwuAMWvEeWLAeSW2tt_XQ"),
                    a);
        } else {
            Assert.assertTrue(false);
        }
    }

    protected CorrespondenceModel getCorrespondenceModel() throws Throwable {
        final VSUMImpl vsum = this.getVSUM();
        final VURI jaMoPPVURI = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final VURI pcmVURI = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE);
        final CorrespondenceModel corresponcenceInstance = vsum
                .getCorrespondenceModel(pcmVURI, jaMoPPVURI);
        return corresponcenceInstance;
    }

    private VSUMImpl getVSUM() throws Throwable {
        final PCMJavaBuilder pcmJavaBuilder = this.getPCMJavaBuilderFromProject();
        if (null == pcmJavaBuilder) {
            return null;
        }
        final VSUMImpl vsum = JavaBridge.getFieldFromClass(VitruviusEmfBuilder.class, "vsum", pcmJavaBuilder);
        return vsum;
    }

    private PCMJavaBuilder getPCMJavaBuilderFromProject() throws Throwable {
        final Project project = (Project) this.testProject;
        final ResourceInfo info = project.getResourceInfo(false, false);
        final ProjectDescription description = ((ProjectInfo) info).getDescription();
        final boolean makeCopy = false;
        for (final ICommand command : description.getBuildSpec(makeCopy)) {
            if (command.getBuilderName().equals(PCMJavaBuilder.BUILDER_ID)) {
                final BuildCommand buildCommand = (BuildCommand) command;
                final IncrementalProjectBuilder ipb = buildCommand.getBuilder(this.testProject.getActiveBuildConfig());
                final PCMJavaBuilder pcmJavaBuilder = (PCMJavaBuilder) ipb;
                return pcmJavaBuilder;
            }
        }
        logger.warn("Could not find any PCMJavaBuilder");
        return null;
    }

    /**
     * Thread-safe simple progress monitor for knowing when a job is done.
     *
     */
    private class DoneFlagProgressMonitor extends NullProgressMonitor {

        private final AtomicBoolean isDone = new AtomicBoolean(false);

        @Override
        public void done() {
            this.isDone.set(true);
        }

        public boolean isDone() {
            return this.isDone.get();
        }

    }

}
