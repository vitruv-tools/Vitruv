package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm.util

import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.URISyntaxException
import java.net.URL
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Platform
import org.eclipse.ui.dialogs.IOverwriteQuery
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider
import org.eclipse.ui.wizards.datatransfer.ImportOperation
import org.osgi.framework.Bundle
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration.ui.commands.IntegrateProjectHandler

class CodeIntegrationUtils {
	private new(){ 
		
	}
	
	def public static void integratProject(IProject project) {
		val integrateProjectHander = new IntegrateProjectHandler();
        integrateProjectHander.integrateProject(project);
	}
	
	 def public static void importTestProjectFromBundleData(IWorkspace workspace, String testProjectName,
	 	String testBundleName, String testSourceAndModelFolder)
            throws IOException, URISyntaxException, InvocationTargetException, InterruptedException {
        val IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
            @Override
            override String queryOverwrite( String file) {
                return ALL;
            }
        };
        val IPath workspacePath = workspace.getRoot().getFullPath().append("/" + testProjectName);

        val Bundle bundle = Platform.getBundle(testBundleName);
        val URL projectBluePrintBundleURL = bundle.getEntry(testSourceAndModelFolder)
        val URL fileURL = FileLocator.resolve(projectBluePrintBundleURL);
        val File file = new File(fileURL.toURI());
        val String baseDir = file.getAbsolutePath();// location of files to import
        val ImportOperation importOperation = new ImportOperation(workspacePath, new File(baseDir),
                FileSystemStructureProvider.INSTANCE, overwriteQuery);
        importOperation.setCreateContainerStructure(false);
        val DoneFlagProgressMonitor progress = new DoneFlagProgressMonitor();
        importOperation.run(progress);

        // Wait for the project to be imported 
        while (!progress.isDone()) {
            Thread.sleep(100);
        }
    }
   
}