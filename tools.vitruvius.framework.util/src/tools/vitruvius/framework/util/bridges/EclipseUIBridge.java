package tools.vitruvius.framework.util.bridges;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class EclipseUIBridge {
	private static URI toURI(IFile file) {
		return toURI(file.getFullPath());
	}

	private static URI toURI(IPath path) {
		return URI.createPlatformResourceURI(path.toString(), true);
	}

	public static URI askForNewResource(String message) {
		final URI resourceURI = toURI(
				WorkspaceResourceDialog.openNewFile(getShell(), "Select new resource", message, null, null));
		return resourceURI;
	}

	public static URI askForNewResource(EObject eObject) {
		return askForNewResource(EcoreBridge.createSensibleString(eObject));
	}

	private static Shell getShell() {
		return PlatformUI.getWorkbench().getModalDialogShellProvider().getShell();
	}
}
