package tools.vitruv.framework.vsum.helper

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamClass
import java.util.Collections
import java.util.HashSet
import java.util.Set
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.VsumConstants

class FileSystemHelper {
	final String vsumName

	new(String vsumName) {
		this.vsumName = vsumName
	}

	def VURI getCorrespondencesVURI() {
		var IFile correspondenceFile = getCorrespondenceIFile()
		return VURI.getInstance(correspondenceFile)
	}

	def void saveCorrespondenceModelMMURIs() {
		var IFile correspondenceModelIFile = getCorrespondenceIFile()
		// FIXME This does nothing reasonable anymore
		var Set<VURI> mmURIsSet = new HashSet<VURI>()
		// Arrays.asList(mmURIs));
		saveVURISetToFile(mmURIsSet, correspondenceModelIFile.getLocation().toOSString())
	}

	def IFile getCorrespondenceIFile() {
		var String fileName = getCorrespondenceFileName()
		return getCorrespondenceIFile(fileName)
	}

	def IFile getCorrespondenceIFile(String fileName) {
		var IFolder correspondenceFolder = getCorrespondenceFolder()
		var IFile correspondenceFile = correspondenceFolder.getFile(fileName)
		return correspondenceFile
	}

	def private static String getCorrespondenceFileName() {
		var String fileExtSeparator = VitruviusConstants.getFileExtSeparator()
		var String fileExt = VitruviusConstants.getCorrespondencesFileExt()
		// VURI[] copyOfMMURIs = Arrays.copyOf(mmURIs, mmURIs.length);
		// Arrays.sort(copyOfMMURIs);
		var String fileName = ""
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
		fileName = '''Correspondences«fileExtSeparator»«fileExt»'''
		return fileName
	}

	def void saveVsumVURIsToFile(Set<VURI> vuris) {
		var String fileName = getVsumMapFileName()
		saveVURISetToFile(vuris, fileName)
	}

	def private static void saveVURISetToFile(Set<VURI> vuris, String fileName) {
		var Set<String> stringSet = new HashSet<String>(vuris.size())
		for (VURI vuri : vuris) {
			stringSet.add(vuri.getEMFUri().toString())
		}
		saveObjectToFile(stringSet, fileName)
	}

	def static void saveObjectToFile(Object object, String fileName) {
		try {
			// TODO: this code could be optimized in a way that a new method is provide for sets of
			// strings where only the new strings are appended to the file
			var FileOutputStream fileOutputStream = new FileOutputStream(fileName)
			var ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream)
			oos.writeObject(object)
			oos.flush()
			oos.close()
		} catch (IOException e) {
			throw new RuntimeException('''Could not save '«»«object»' to file '«»«fileName»':  «e»''')
		}

	}

	def Set<VURI> loadVsumVURIsFromFile() {
		var String fileName = getVsumMapFileName()
		return loadVURISetFromFile(fileName)
	}

	def private static Set<VURI> loadVURISetFromFile(String fileName) {
		var Set<String> stringSet = loadStringSetFromFile(fileName)
		var Set<VURI> vuris = new HashSet<VURI>(stringSet.size() * 2)
		for (String str : stringSet) {
			vuris.add(VURI.getInstance(str))
		}
		return vuris
	}

	@SuppressWarnings("unchecked")
	def private static Set<String> loadStringSetFromFile(String fileName) {
		var Object obj = loadObjectFromFile(fileName)
		if (obj === null) {
			return Collections.emptySet()
		} else if (obj instanceof Set<?>) {
			return (obj as Set<String>)
		} else {
			throw new RuntimeException('''The file with the name '«»«fileName»' does not contain a set of strings!''')
		}
	}

	def static Object loadObjectFromFile(String fileName) {
		return loadObjectFromFile(fileName, null)
	}

	def static Object loadObjectFromFile(String fileName, ClassLoader cl) {
		try {
			var FileInputStream fileInputStream = new FileInputStream(fileName)
			var ObjectInputStream ois = new ObjectInputStream(fileInputStream) {
				override protected Class<?> resolveClass(
					ObjectStreamClass desc) throws IOException, ClassNotFoundException {
					try {
						return super.resolveClass(desc)
					} catch (ClassNotFoundException e) {
						if (cl !== null) {
							var String name = desc.getName()
							return Class.forName(name, false, cl)
						} else {
							throw e
						}
					}

				}
			}
			var Object obj = ois.readObject()
			ois.close()
			return obj
		} catch (FileNotFoundException e) {
			return null
		} catch (IOException e) {
			throw new RuntimeException(e)
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e)
		}

	}

	def static IProject getProject(String projectName) {
		var IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot()
		return root.getProject(projectName)
	}

	def IProject getVsumProject() {
		var IProject vsumProject = getProject(this.vsumName)
		if (!vsumProject.exists()) {
			createProject(vsumProject)
		} else if (!vsumProject.isAccessible()) {
			deleteAndRecreateProject(vsumProject)
		}
		return vsumProject
	}

	def private void deleteAndRecreateProject(IProject vsumProject) {
		try {
			vsumProject.delete(true, new NullProgressMonitor())
			createProject(vsumProject)
		} catch (CoreException e) {
			// soften
			throw new RuntimeException(e)
		}

	}

	def void createProject(IProject project) {
		try {
			project.create(null)
			project.open(null)
			// IProjectDescription description = project.getDescription();
			// description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
			// project.setDescription(description, null);
			createFolder(getCorrespondenceFolder(project))
			createFolder(getVsumFolder(project))
		} catch (CoreException e) {
			// soften
			throw new RuntimeException(e)
		}

	}

	def private static IFolder getVsumFolder(IProject project) {
		return project.getFolder(VsumConstants.VSUM_FOLDER_NAME)
	}

	def static void createFolder(IFolder folder) throws CoreException {
		folder.create(false, true, null)
	}

	def IFolder getCorrespondenceFolder() {
		var IProject vsumProject = getVsumProject()
		return getCorrespondenceFolder(vsumProject)
	}

	def private IFolder getCorrespondenceFolder(IProject project) {
		var IFolder correspondenceFolder = project.getFolder(VsumConstants.CORRESPONDENCE_FOLDER_NAME)
		return correspondenceFolder
	}

	def private String getVsumMapFileName() {
		var IFile file = getVsumInstancesFile()
		return file.getLocation().toOSString()
	}

	def IFile getVsumInstancesFile() {
		return getVsumInstancesFile("")
	}

	def IFile getVsumInstancesFile(String prefix) {
		var IFile file = getVsumProject().getFolder(VsumConstants.VSUM_FOLDER_NAME).getFile(prefix +
			VsumConstants.VSUM_INSTANCES_FILE_NAME)
		return file
	}
}
