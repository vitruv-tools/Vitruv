package edu.kit.ipd.sdq.vitruvius.framework.vsum.helper;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

public class FileSystemHelper {
    /** Utility classes should not have a public or default constructor. */
    private FileSystemHelper() {
    }

    public static VURI createCorrespondenceInstanceURI(final VURI[] mmURIs) {
        IProject correspondenceProject = getCorrespondenceProject();
        IFolder correspondenceFolder = getCorrespondenceFolder(correspondenceProject);
        VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
        Arrays.sort(copyOfMMURIs);
        String fileName = "";
        for (VURI uri : copyOfMMURIs) {
            fileName += uri.toString();
        }
        IFile correspondenceFile = correspondenceFolder.getFile(fileName);
        return VURI.getInstance(correspondenceFile.getFullPath().toOSString());
    }

    public static IProject createCorrespondenceProject() {
        try {
            IProject project = getCorrespondenceProject();
            project.create(null);
            project.open(null);
            // IProjectDescription description = project.getDescription();
            // description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
            // project.setDescription(description, null);
            IFolder correspondenceFolder = getCorrespondenceFolder(project);
            correspondenceFolder.create(false, true, null);
            return project;
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static IProject getCorrespondenceProject() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        return root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
    }

    private static IFolder getCorrespondenceFolder(final IProject project) {
        IFolder correspondenceFolder = project.getFolder(VSUMConstants.CORRESPONDENCE_FOLDER_NAME);
        return correspondenceFolder;
    }

}
