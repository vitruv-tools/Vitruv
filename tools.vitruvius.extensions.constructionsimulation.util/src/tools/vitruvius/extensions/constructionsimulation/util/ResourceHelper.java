package tools.vitruvius.extensions.constructionsimulation.util;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * class that provides basic resource helper methods
 *
 * @author langhamm
 *
 */
public class ResourceHelper {

    private ResourceHelper() {

    }

    /**
     * Creates an platform URI for a resource
     *
     * @param resource
     *            : the resource
     * @return the model uri
     */
    public static URI createPlatformUriForResource(final Resource resource) {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IPath workspaceDir = workspace.getRoot().getLocation();
        String workspaceString = workspaceDir.toString();
        workspaceString = workspaceString.replace("/", File.separator);

        final String platRelativeModelLoc = resource.getURI().toFileString().replace(workspaceString, "");
        final URI uri = URI.createPlatformResourceURI(platRelativeModelLoc, true);
        return uri;
    }

    /**
     * converts an absolute EMF Resource to a workspace relative Eclipse IResource.
     *
     * @param emfResource
     *            : C:\Workspace\Project\Model\model.abc
     * @return : L/Project/Model/model.abc
     */
    public static IResource absoluteEmfResourceToWorkspaceRelativeIResource(final Resource emfResource) {
        final URI uri = emfResource.getURI();

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();

        final String fileString = uri.toFileString();
        final String rootString = root.getLocation().toString();
        final String relativeString = fileString.substring(rootString.length());

        final IResource iResource = root.getFile(new Path(relativeString));

        return iResource;
    }

}
