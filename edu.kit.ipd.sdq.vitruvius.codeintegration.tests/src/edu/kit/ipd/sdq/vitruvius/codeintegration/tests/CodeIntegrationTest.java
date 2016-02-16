package edu.kit.ipd.sdq.vitruvius.codeintegration.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaAddBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder;
import edu.kit.ipd.sdq.vitruvius.codeintegration.ui.commands.IntegrateProjectHandler;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class CodeIntegrationTest {
	
    

	private static final Logger logger = Logger.getLogger(CodeIntegrationTest.class.getSimpleName());
	
	private static final String TEST_BUNDLE_NAME = "edu.kit.ipd.sdq.vitruvius.codeintegration.tests";
	private static final String TEST_PROJECT_NAME = "eu.fpetersen.cbs.pc";
	private static final String SOURCE_CODE_PATH = "example_code/eu.fpetersen.cbs.pc";
	
	private static final String META_PROJECT_NAME = "vitruvius.meta";
	private IProject testProject;
	private IWorkspace workspace;
	
	@Before
	public void beforeTest() throws InvocationTargetException, InterruptedException, IOException, URISyntaxException {
		workspace = ResourcesPlugin.getWorkspace();
		
		importTestProjectFromBundleData();
		
		IProject project = workspace.getRoot().getProject(getTestProjectName());
		assert(project != null);
		testProject = project;
	}

	protected String getTestProjectName() {
		return TEST_PROJECT_NAME;
	}

	protected String getTestBundleName() {
		return TEST_BUNDLE_NAME;
	}
	
	protected String getTestSourceAndModelFolder() {
		return SOURCE_CODE_PATH;
	}
	
	private void importTestProjectFromBundleData()
			throws IOException, URISyntaxException, InvocationTargetException, InterruptedException {
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		        public String queryOverwrite(String file) { return ALL; }
		};
		IPath workspacePath = workspace.getRoot().getFullPath().append("/" + getTestProjectName());
		
		Bundle bundle = Platform.getBundle(getTestBundleName());
		URL projectBluePrintBundleURL = bundle.getEntry(getTestSourceAndModelFolder());
		URL fileURL = FileLocator.resolve(projectBluePrintBundleURL);
		File file = new File(fileURL.toURI());
		
		
		String baseDir = file.getAbsolutePath();// location of files to import
		ImportOperation importOperation = new ImportOperation(workspacePath,
		        new File(baseDir), FileSystemStructureProvider.INSTANCE, overwriteQuery);
		importOperation.setCreateContainerStructure(false);
		DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
		importOperation.run(progress);
		
		//Wait for the project to be imported
		while (!progress.isDone()) {
			Thread.sleep(100);
		}
	}


	@After
	public void afterTest() throws CoreException, InterruptedException {
		//Delete test project
		IProject testProject = workspace.getRoot().getProject(getTestProjectName());
		if (testProject.exists()) {
			DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
			testProject.delete(true, true, progress);
			while (!progress.isDone()) {
				Thread.sleep(100);
			}
		}
		
		//Delete vitruvius.meta project
		IProject metaProject = workspace.getRoot().getProject(META_PROJECT_NAME);
		if (metaProject.exists()) {
			DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
			metaProject.delete(true, true, progress);
			while (!progress.isDone()) {
				Thread.sleep(100);
			}
		}
	}
	
	@Test
	public void testStandardCodeIntegration() throws Throwable {
		IntegrateProjectHandler integrateProjectHander = new IntegrateProjectHandler();
		integrateProjectHander.integrateProject(testProject);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject metaProject = workspace.getRoot().getProject(META_PROJECT_NAME);
		DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
		metaProject.refreshLocal(IResource.DEPTH_INFINITE, progress);
		while (!progress.isDone()) {
			Thread.sleep(100);
		}
		Assert.assertNotNull(metaProject);
		
		IFolder corrFolder = metaProject.getFolder("correspondence");
		Assert.assertNotNull(corrFolder);
		IResource[] members = corrFolder.members();
		
		Stream<IResource> correspondenceFiles = Arrays.asList(members).stream().filter(r -> r instanceof IFile);
		Assert.assertEquals(2,correspondenceFiles.filter(r -> (r.getName().endsWith(".correspondence") || r.getName().endsWith(".integrated_correspondence"))).count());
			
		IFolder vsumFolder = metaProject.getFolder("vsum");
		Assert.assertNotNull(vsumFolder);
		
		// add PCM Java Builder to Project under test
        final PCMJavaAddBuilder pcmJavaBuilder = new PCMJavaAddBuilder();
        pcmJavaBuilder.addBuilderToProject(testProject);
        // build the project
        progress = new DoneFlagProgressMonitor();
        testProject.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, PCMJaMoPPNamespace.BUILDER_ID, new HashMap<String, String>(),
                progress);
        while (!progress.isDone()) {
			Thread.sleep(100);
		}
        
        CorrespondenceInstance ci = getCorrespondenceInstance();
        //TODO check if correspondences are correct
        Set<Correspondence> correspondences = ci.getAllCorrespondencesWithoutDependencies();
        Assert.assertNotNull(correspondences);
        Assert.assertFalse(correspondences.isEmpty());
        
        TUID frameCodeTuid = TUID.valueOf("http://www.emftext.org/java#platform:/resource/eu.fpetersen.cbs.pc/src/eu/fpetersen/cbs/pc/data/Frame.java#classifier-_-Frame");
        List<TUID> frameCodeTuids = Collections.singletonList(frameCodeTuid);
        Set<Correspondence> frameCodeCorrs = ci.getCorrespondencesForTUIDs(frameCodeTuids);
        
        Assert.assertNotNull(frameCodeCorrs);
        Assert.assertFalse(frameCodeCorrs.isEmpty());
        Assert.assertEquals(1, frameCodeCorrs.size());
        
        Correspondence frameCorr = frameCodeCorrs.iterator().next();
        if (frameCorr.getATUIDs().contains(frameCodeTuid)) {
        	EList<TUID> bs = frameCorr.getBTUIDs();
        	Assert.assertNotNull(bs);
        	Assert.assertEquals(1, bs.size());
        	
        	TUID b = bs.get(0);
        	Assert.assertEquals(TUID.valueOf("http://palladiosimulator.org/PalladioComponentModel/Repository/5.1#platform:/resource/eu.fpetersen.cbs.pc/model/internal_architecture_model.repository#_auhdcMWvEeWLAeSW2tt_XQ#_auwuAMWvEeWLAeSW2tt_XQ"), b);
        } else if (frameCorr.getBTUIDs().contains(frameCodeTuid)) {
        	EList<TUID> as = frameCorr.getATUIDs();
        	Assert.assertNotNull(as);
        	Assert.assertEquals(1, as.size());
        	
        	TUID a = as.get(0);
        	Assert.assertEquals(TUID.valueOf("http://palladiosimulator.org/PalladioComponentModel/Repository/5.1#platform:/resource/eu.fpetersen.cbs.pc/model/internal_architecture_model.repository#_auhdcMWvEeWLAeSW2tt_XQ#_auwuAMWvEeWLAeSW2tt_XQ"), a);
        } else {
        	Assert.assertTrue(false);
        }
		
	}
	
    private CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        final VSUMImpl vsum = this.getVSUM();
        final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final CorrespondenceInstance corresponcenceInstance = vsum.getCorrespondenceInstanceOriginal(pcmVURI,
                jaMoPPVURI);
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

    @SuppressWarnings("restriction")
	private PCMJavaBuilder getPCMJavaBuilderFromProject() throws Throwable {
        final Project project = (Project) testProject;
        final ResourceInfo info = project.getResourceInfo(false, false);
        final ProjectDescription description = ((ProjectInfo) info).getDescription();
        final boolean makeCopy = false;
        for (final ICommand command : description.getBuildSpec(makeCopy)) {
            if (command.getBuilderName().equals(PCMJaMoPPNamespace.BUILDER_ID)) {
                final BuildCommand buildCommand = (BuildCommand) command;
                final IncrementalProjectBuilder ipb = buildCommand
                        .getBuilder(testProject.getActiveBuildConfig());
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
		
		private AtomicBoolean isDone = new AtomicBoolean(false);
		
		@Override
		public void done() {
			isDone.set(true);
		}
		
		public boolean isDone() {
			return isDone.get();
		}
		
	}

}
