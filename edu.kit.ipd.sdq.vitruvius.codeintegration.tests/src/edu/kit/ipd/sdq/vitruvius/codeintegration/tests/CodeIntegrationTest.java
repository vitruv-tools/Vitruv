package edu.kit.ipd.sdq.vitruvius.codeintegration.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import edu.kit.ipd.sdq.vitruvius.codeintegration.ui.commands.IntegrateProjectHandler;

public class CodeIntegrationTest {
	
	private static final String TEST_BUNDLE_NAME = "edu.kit.ipd.sdq.vitruvius.codeintegration.tests";
	private static final String TEST_PROJECT_NAME = "eu.fpetersen.cbs.pc";
	private static final String META_PROJECT_NAME = "vitruvius.meta";
	private IProject testProject;
	private IWorkspace workspace;
	
	@Before
	public void beforeTest() throws InvocationTargetException, InterruptedException, IOException, URISyntaxException {
		workspace = ResourcesPlugin.getWorkspace();
		
		importTestProjectFromBundleData();
		
		IProject project = workspace.getRoot().getProject(TEST_PROJECT_NAME);
		assert(project != null);
		testProject = project;
	}

	private void importTestProjectFromBundleData()
			throws IOException, URISyntaxException, InvocationTargetException, InterruptedException {
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		        public String queryOverwrite(String file) { return ALL; }
		};
		IPath workspacePath = workspace.getRoot().getFullPath().append("/" + TEST_PROJECT_NAME);
		
		Bundle bundle = Platform.getBundle(TEST_BUNDLE_NAME);
		URL projectBluePrintBundleURL = bundle.getEntry("example_code/eu.fpetersen.cbs.pc");
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
		IProject testProject = workspace.getRoot().getProject(TEST_PROJECT_NAME);
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
	public void testStandardCodeIntegration() throws CoreException, InterruptedException {
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
