package edu.kit.ipd.sdq.vitruvius.framework.vsum.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

public class FileSystemHelper {
    /** Utility classes should not have a public or default constructor. */
    private FileSystemHelper() {
    }

    public static VURI getCorrespondencesVURI(final VURI[] mmURIs) {
        String fileExt = VitruviusConstants.getCorrespondencesFileExt();
        return getCorrespondenceVURI(mmURIs, fileExt);
    }

    public static void saveCorrespondenceInstanceMMURIs(final VURI[] mmURIs) {
        String fileExt = VitruviusConstants.getCorrespondenceInstanceFileExt();
        IFile correspondenceInstanceIFile = getCorrespondenceIFile(mmURIs, fileExt);
        Set<VURI> mmURIsSet = new HashSet<VURI>(Arrays.asList(mmURIs));
        saveVURISetToFile(mmURIsSet, correspondenceInstanceIFile.getLocation().toOSString());
    }

    private static VURI getCorrespondenceVURI(final VURI[] mmURIs, final String fileExt) {
        IFile correspondenceFile = getCorrespondenceIFile(mmURIs, fileExt);
        return VURI.getInstance(correspondenceFile);
    }

    private static IFile getCorrespondenceIFile(final VURI[] mmURIs, final String fileExt) {
        IProject correspondenceProject = getVSUMProject();
        IFolder correspondenceFolder = getCorrespondenceFolder(correspondenceProject);
        VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
        Arrays.sort(copyOfMMURIs);
        String fileName = "";
        for (VURI uri : copyOfMMURIs) {
            fileName += uri.getLastSegment().replace('.', '_') + uri.toString().hashCode();
        }
        fileName += fileExt;
        IFile correspondenceFile = correspondenceFolder.getFile(fileName);
        return correspondenceFile;
    }

    public static void saveVSUMvURIsToFile(final Set<VURI> vuris) {
        String fileName = getVSUMMapFileName();
        saveVURISetToFile(vuris, fileName);
    }

    private static void saveVURISetToFile(final Set<VURI> vuris, final String fileName) {
        Set<String> stringSet = new HashSet<String>(vuris.size());
        for (VURI vuri : vuris) {
            stringSet.add(vuri.getEMFUri().toString());
        }
        saveStringSetToFile(stringSet, fileName);
    }

    private static void saveStringSetToFile(final Set<String> stringSet, final String fileName) {
        try {
            boolean append = true;
            FileOutputStream fileOutputStream = new FileOutputStream(fileName, append);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(stringSet);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not save map: " + stringSet + "to file " + fileName + e);
        }
    }

    public static Set<VURI> loadVSUMvURIsFromFile() {
        String fileName = getVSUMMapFileName();
        return loadVURISetFromFile(fileName);

    }

    private static Set<VURI> loadVURISetFromFile(final String fileName) {
        Set<String> stringSet = loadStringSetFromFile(fileName, String.class);
        Set<VURI> vuris = new HashSet<VURI>(stringSet.size() * 2);
        for (String str : stringSet) {
            vuris.add(VURI.getInstance(str));
        }
        return vuris;
    }

    private static <T> Set<T> loadStringSetFromFile(final String fileName, final Class<T> clazz) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            Object obj = ois.readObject();
            ois.close();
            @SuppressWarnings("unchecked")
            Set<T> stringSet = (Set<T>) obj;
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
            createFolder(getCorrespondenceFolder(project));
            createFolder(getVSMUFolder(project));
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static IFolder getVSMUFolder(final IProject project) {
        return project.getFolder(VSUMConstants.VSUM_FOLDER_NAME);
    }

    private static void createFolder(final IFolder folder) throws CoreException {
        folder.create(false, true, null);
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
