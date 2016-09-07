package tools.vitruvius.dsls.mapping.util;

import java.io.IOException;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruvius.framework.util.bridges.EcoreBridge;

public class EclipseHelper {
	private final static Logger LOGGER = Logger.getLogger(EclipseHelper.class);
	
	private static URI toURI(IFile file) { return toURI(file.getFullPath()); }
	private static URI toURI(IPath path) { return URI.createPlatformResourceURI(path.toString(), true); }

	public static URI askForNewResource(String message) {
		// FIXME make interchangable or move to UserInteractor
//		return new URI(random.nextInt();
//		final URI resourceURI = URI.createPlatformResourceURI("MockupProject/model/repository_tmp_" + (repositoryCounter++) + ".repository", false); 
		final URI resourceURI = toURI(WorkspaceResourceDialog.openNewFile(getShell(), "Select new resource", message, null, null));
		return resourceURI;
	}
	
	public static URI askForNewResource(EObject eObject) {
		return askForNewResource(EcoreBridge.createSensibleString(eObject));
	}
	
	@Deprecated
	public static Resource askAndSaveResource(EObject obj) {
		final URI newResourceURI = askForNewResource(obj);
		final Resource newResource = createAndSaveResourceForEObject(obj, newResourceURI);
		
		return newResource;
	}

	@Deprecated
	public static Resource createAndSaveResourceForEObject(EObject eObj, URI uri) {
		final ResourceSet resSet = new ResourceSetImpl();
		final Resource res = resSet.createResource(uri);
		
		res.getContents().add(eObj);
		try {
			res.save(Collections.emptyMap());
			LOGGER.info("created resource at " + uri.toString());
		} catch (IOException e) {
			LOGGER.warn("could not create resource at " + uri.toString(), e);
		}
		return res;
	}
	
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getModalDialogShellProvider().getShell();
	}
}
