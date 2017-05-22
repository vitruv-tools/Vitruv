package tools.vitruv.framework.vsum.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
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

import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.VsumConstants;

public class FileSystemHelper {
    private final String vsumName;

    public FileSystemHelper(final String vsumName) {
        this.vsumName = vsumName;
    }

    public VURI getCorrespondencesVURI() {
        IFile correspondenceFile = getCorrespondenceIFile();
        return VURI.getInstance(correspondenceFile);
    }

    public void saveCorrespondenceModelMMURIs() {
        IFile correspondenceModelIFile = getCorrespondenceIFile();
        // FIXME This does nothing reasonable anymore
        Set<VURI> mmURIsSet = new HashSet<VURI>();// Arrays.asList(mmURIs));
        saveVURISetToFile(mmURIsSet, correspondenceModelIFile.getLocation().toOSString());
    }

    public IFile getCorrespondenceIFile() {
        String fileName = getCorrespondenceFileName();
        return getCorrespondenceIFile(fileName);
    }

    public IFile getCorrespondenceIFile(final String fileName) {
        IFolder correspondenceFolder = getCorrespondenceFolder();
        IFile correspondenceFile = correspondenceFolder.getFile(fileName);
        return correspondenceFile;
    }

    private static String getCorrespondenceFileName() {
        String fileExtSeparator = VitruviusConstants.getFileExtSeparator();
        String fileExt = VitruviusConstants.getCorrespondencesFileExt();
        // VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
        // Arrays.sort(copyOfMMURIs);
        String fileName = "";
        // for (VURI uri : copyOfMMURIs) {
        //
        // String authority = uri.getEMFUri().authority();
        // if (authority != null) {
        // int indexOfLastDot = authority.lastIndexOf('.');
        //
        // fileName += authority.substring(indexOfLastDot + 1);
        //
        // }
        // fileName += uri.toString().hashCode();
        // }
        fileName = "Correspondences" + fileExtSeparator + fileExt;
        return fileName;
    }

    public void saveVsumVURIsToFile(final Set<VURI> vuris) {
        String fileName = getVsumMapFileName();
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

    public Set<VURI> loadVsumVURIsFromFile() {
        String fileName = getVsumMapFileName();
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

    public IProject getVsumProject() {
        IProject vsumProject = getProject(this.vsumName);
        if (!vsumProject.exists()) {
            createProject(vsumProject);
        } else if (!vsumProject.isAccessible()) {
            deleteAndRecreateProject(vsumProject);
        }
        return vsumProject;

    }

    private void deleteAndRecreateProject(final IProject vsumProject) {
        try {
            vsumProject.delete(true, new NullProgressMonitor());
            createProject(vsumProject);
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    public void createProject(final IProject project) {
        try {
            project.create(null);
            project.open(null);
            // IProjectDescription description = project.getDescription();
            // description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
            // project.setDescription(description, null);
            createFolder(getCorrespondenceFolder(project));
            createFolder(getVsumFolder(project));
        } catch (CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static IFolder getVsumFolder(final IProject project) {
        return project.getFolder(VsumConstants.VSUM_FOLDER_NAME);
    }

    public static void createFolder(final IFolder folder) throws CoreException {
        folder.create(false, true, null);
    }

    public IFolder getCorrespondenceFolder() {
        IProject vsumProject = getVsumProject();
        return getCorrespondenceFolder(vsumProject);
    }

    private IFolder getCorrespondenceFolder(final IProject project) {
        IFolder correspondenceFolder = project.getFolder(VsumConstants.CORRESPONDENCE_FOLDER_NAME);
        return correspondenceFolder;
    }

    private String getVsumMapFileName() {
        IFile file = getVsumInstancesFile();
        return file.getLocation().toOSString();
    }

    public IFile getVsumInstancesFile() {
        return getVsumInstancesFile("");
    }

    public IFile getVsumInstancesFile(final String prefix) {
        IFile file = getVsumProject().getFolder(VsumConstants.VSUM_FOLDER_NAME)
                .getFile(prefix + VsumConstants.VSUM_INSTANCES_FILE_NAME);
        return file;
    }

}
