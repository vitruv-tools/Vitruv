package tools.vitruvius.framework.vsum.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
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
import org.eclipse.core.runtime.NullProgressMonitor;

import tools.vitruvius.framework.util.VitruviusConstants;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMConstants;

public class FileSystemHelper {
    /** Utility classes should not have a public or default constructor. */
    private FileSystemHelper() {
    }

    public static VURI getCorrespondencesVURI(final VURI... mmURIs) {
        IFile correspondenceFile = getCorrespondenceIFile(mmURIs);
        return VURI.getInstance(correspondenceFile);
    }

    public static void saveCorrespondenceModelMMURIs(final VURI[] mmURIs) {
        IFile correspondenceModelIFile = getCorrespondenceIFile(mmURIs);
        Set<VURI> mmURIsSet = new HashSet<VURI>(Arrays.asList(mmURIs));
        saveVURISetToFile(mmURIsSet, correspondenceModelIFile.getLocation().toOSString());
    }

    public static IFile getCorrespondenceIFile(final VURI[] mmURIs) {
        String fileName = getCorrespondenceFileName(mmURIs);
        return getCorrespondenceIFile(fileName);
    }

    public static IFile getCorrespondenceIFile(final String fileName) {
        IFolder correspondenceFolder = getCorrespondenceFolder();
        IFile correspondenceFile = correspondenceFolder.getFile(fileName);
        return correspondenceFile;
    }

    private static String getCorrespondenceFileName(final VURI[] mmURIs) {
        String fileExtSeparator = VitruviusConstants.getFileExtSeparator();
        String fileExt = VitruviusConstants.getCorrespondencesFileExt();
        VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
        Arrays.sort(copyOfMMURIs);
        String fileName = "";
        for (VURI uri : copyOfMMURIs) {

            String authority = uri.getEMFUri().authority();
            if (authority != null) {
                int indexOfLastDot = authority.lastIndexOf('.');

                fileName += authority.substring(indexOfLastDot + 1);

            }
            fileName += uri.toString().hashCode();
        }
        fileName = fileName + fileExtSeparator + fileExt;
        return fileName;
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
        saveObjectToFile(stringSet, fileName);
    }

    public static void saveObjectToFile(final Object object, final String fileName) {
        try {
            // TODO: this code could be optimized in a way that a new method is provide for sets of
            // strings where only the new strings are appended to the file
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not save '" + object + "' to file '" + fileName + "':  " + e);
        }
    }

    public static Set<VURI> loadVSUMvURIsFromFile() {
        String fileName = getVSUMMapFileName();
        return loadVURISetFromFile(fileName);

    }

    private static Set<VURI> loadVURISetFromFile(final String fileName) {
        Set<String> stringSet = loadStringSetFromFile(fileName);
        Set<VURI> vuris = new HashSet<VURI>(stringSet.size() * 2);
        for (String str : stringSet) {
            vuris.add(VURI.getInstance(str));
        }
        return vuris;
    }

    @SuppressWarnings("unchecked")
    private static Set<String> loadStringSetFromFile(final String fileName) {
        Object obj = loadObjectFromFile(fileName);
        if (obj == null) {
            return Collections.emptySet();
        } else if (obj instanceof Set<?>) {
            return (Set<String>) obj;
        } else {
            throw new RuntimeException("The file with the name '" + fileName + "' does not contain a set of strings!");
        }
    }

    public static Object loadObjectFromFile(final String fileName) {
        return loadObjectFromFile(fileName, null);
    }

    public static Object loadObjectFromFile(final String fileName, final ClassLoader cl) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream) {
                @Override
                protected Class<?> resolveClass(final ObjectStreamClass desc)
                        throws IOException, ClassNotFoundException {
                    try {
                        return super.resolveClass(desc);
                    } catch (ClassNotFoundException e) {
                        if (cl != null) {
                            String name = desc.getName();
                            return Class.forName(name, false, cl);
                        } else {
                            throw e;
                        }
                    }
                }
            };
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static IProject getProject(final String projectName) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        return root.getProject(projectName);
    }

    public static IProject getVSUMProject() {
        IProject vsumProject = getProject(VSUMConstants.VSUM_PROJECT_NAME);
        if (!vsumProject.exists()) {
            createProject(vsumProject);
        } else if (!vsumProject.isAccessible()) {
            deleteAndRecreateProject(vsumProject);
        }
        return vsumProject;

    }

    private static void deleteAndRecreateProject(final IProject vsumProject) {
        try {
            vsumProject.delete(true, new NullProgressMonitor());
            createProject(vsumProject);
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    public static void createProject(final IProject project) {
        try {
            project.create(null);
            project.open(null);
            // IProjectDescription description = project.getDescription();
            // description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
            // project.setDescription(description, null);
            createFolder(getCorrespondenceFolder(project));
            createFolder(getVSUMFolder(project));
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static IFolder getVSUMFolder(final IProject project) {
        return project.getFolder(VSUMConstants.VSUM_FOLDER_NAME);
    }

    public static void createFolder(final IFolder folder) throws CoreException {
        folder.create(false, true, null);
    }

    public static IFolder getCorrespondenceFolder() {
        IProject vsumProject = getVSUMProject();
        return getCorrespondenceFolder(vsumProject);
    }

    private static IFolder getCorrespondenceFolder(final IProject project) {
        IFolder correspondenceFolder = project.getFolder(VSUMConstants.CORRESPONDENCE_FOLDER_NAME);
        return correspondenceFolder;
    }

    private static String getVSUMMapFileName() {
        IFile file = getVSUMInstancesFile();
        return file.getLocation().toOSString();
    }

    public static IFile getVSUMInstancesFile() {
        return getVSUMInstancesFile("");
    }

    public static IFile getVSUMInstancesFile(final String prefix) {
        IFile file = getVSUMProject().getFolder(VSUMConstants.VSUM_FOLDER_NAME)
                .getFile(prefix + VSUMConstants.VSUM_INSTANCES_FILE_NAME);
        return file;
    }

}
