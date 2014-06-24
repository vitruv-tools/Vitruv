package edu.kit.ipd.sdq.vitruvius.framework.vsum.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

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
        IProject correspondenceProject = getVSUMProject();
        IFolder correspondenceFolder = getCorrespondenceFolder(correspondenceProject);
        VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
        Arrays.sort(copyOfMMURIs);
        String fileName = "";
        for (VURI uri : copyOfMMURIs) {
            fileName += uri.getLastSegment() + uri.toString().hashCode();
        }
        IFile correspondenceFile = correspondenceFolder.getFile(fileName);
        return VURI.getInstance(correspondenceFile);
    }

    public static void saveVSUMvURIsToFile(final Set<String> stringSet) {
        String fileName = getVSUMMapFileName();
        saveStringSetToFile(stringSet, fileName);
    }

    private static void saveStringSetToFile(final Set<String> stringSet, final String fileName) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(stringSet);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not save map: " + stringSet + "to file " + fileName + e);
        }
    }

    public static Set<String> loadVSUMvURIsFromFile() {
        String fileName = getVSUMMapFileName();
        return loadStringSetFromFile(fileName);
    }

    private static Set<String> loadStringSetFromFile(final String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            Object obj = ois.readObject();
            ois.close();
            Set<?> set = (Set<?>) obj;
            Set<String> stringSet = (Set<String>) set;
            return stringSet;
        } catch (FileNotFoundException e) {
            return Collections.emptySet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static IProject getVSUMProject() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject vsumProject = root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
        if (!vsumProject.exists()) {
            createProject(vsumProject);
        }
        return vsumProject;

    }

    public static void createProject(final IProject project) {
        try {
            project.create(null);
            project.open(null);
            // IProjectDescription description = project.getDescription();
            // description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
            // project.setDescription(description, null);
            IFolder correspondenceFolder = getCorrespondenceFolder(project);
            correspondenceFolder.create(false, true, null);
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static IFolder getCorrespondenceFolder(final IProject project) {
        IFolder correspondenceFolder = project.getFolder(VSUMConstants.CORRESPONDENCE_FOLDER_NAME);
        return correspondenceFolder;
    }

    private static String getVSUMMapFileName() {
        IFile file = getVSUMProject().getFolder(VSUMConstants.VSUM_FOLDER_NAME).getFile(
                VSUMConstants.VSUM_INSTANCES_FILE_NAME);
        return file.getLocation().toOSString();
    }

}
