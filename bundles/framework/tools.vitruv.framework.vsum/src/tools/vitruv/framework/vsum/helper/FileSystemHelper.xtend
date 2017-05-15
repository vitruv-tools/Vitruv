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
		val correspondenceFile = getCorrespondenceIFile
		VURI::getInstance(correspondenceFile)
	}

	def void saveCorrespondenceModelMMURIs() {
		val correspondenceModelIFile = getCorrespondenceIFile
		// FIXME This does nothing reasonable anymore
		val Set<VURI> mmURIsSet = new HashSet<VURI>
		// Arrays::asList(mmURIs))
		saveVURISetToFile(mmURIsSet, correspondenceModelIFile.location.toOSString)
	}

	def IFile getCorrespondenceIFile() {
		val fileName = getCorrespondenceFileName
		getCorrespondenceIFile(fileName)
	}

	def IFile getCorrespondenceIFile(String fileName) {
		val correspondenceFolder = getCorrespondenceFolder()
		val correspondenceFile = correspondenceFolder.getFile(fileName)
		correspondenceFile
	}

	def private static String getCorrespondenceFileName() {
		val fileExtSeparator = VitruviusConstants::fileExtSeparator
		val fileExt = VitruviusConstants::correspondencesFileExt
		// VURI[] copyOfMMURIs = Arrays::copyOf(mmURIs, mmURIs.length)
		// Arrays::sort(copyOfMMURIs)
		var fileName = ""
		// for (VURI uri : copyOfMMURIs) {
		//
		// String authority = uri.eMFUri.authority
		// if (authority != null) {
		// int indexOfLastDot = authority.lastIndexOf('.')
		//
		// fileName += authority.substring(indexOfLastDot + 1)
		//
		// }
		// fileName += uri.toString.hashCode()
		// }
		fileName = '''Correspondences«fileExtSeparator»«fileExt»'''
		fileName
	}

	def void saveVsumVURIsToFile(Set<VURI> vuris) {
		val fileName = getVsumMapFileName
		saveVURISetToFile(vuris, fileName)
	}

	def private static void saveVURISetToFile(Set<VURI> vuris, String fileName) {
		val Set<String> stringSet = new HashSet<String>(vuris.size)
		vuris.forEach[stringSet.add(EMFUri.toString)]
		saveObjectToFile(stringSet, fileName)
	}

	def static void saveObjectToFile(Object object, String fileName) {
		try {
			// TODO: this code could be optimized in a way that a new method is provide for sets of
			// strings where only the new strings are appended to the file
			val FileOutputStream fileOutputStream = new FileOutputStream(fileName)
			val ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream)
			oos.writeObject(object)
			oos.flush
			oos.close
		} catch (IOException e) {
			throw new RuntimeException('''Could not save '«»«object»' to file '«»«fileName»':  «e»''')
		}
	}

	def Set<VURI> loadVsumVURIsFromFile() {
		val fileName = getVsumMapFileName
		loadVURISetFromFile(fileName)
	}

	def private static Set<VURI> loadVURISetFromFile(String fileName) {
		val Set<String> stringSet = loadStringSetFromFile(fileName)
		val Set<VURI> vuris = new HashSet<VURI>(stringSet.size * 2)
		stringSet.forEach[vuris.add(VURI::getInstance(it))]
		vuris
	}

	@SuppressWarnings("unchecked")
	def private static Set<String> loadStringSetFromFile(String fileName) {
		val obj = loadObjectFromFile(fileName)
		if (obj === null) {
			Collections::emptySet
		} else if (obj instanceof Set<?>) {
			obj as Set<String>
		} else {
			throw new RuntimeException('''The file with the name '«»«fileName»' does not contain a set of strings!''')
		}
	}

	def static Object loadObjectFromFile(String fileName) {
		loadObjectFromFile(fileName, null)
	}

	def static Object loadObjectFromFile(String fileName, ClassLoader cl) {
		try {
			val FileInputStream fileInputStream = new FileInputStream(fileName)
			val ObjectInputStream ois = new ObjectInputStream(fileInputStream) {
				override protected Class<?> resolveClass(
					ObjectStreamClass desc) throws IOException, ClassNotFoundException {
					try {
						return super.resolveClass(desc)
					} catch (ClassNotFoundException e) {
						if (cl !== null) {
							var String name = desc.name
							return Class::forName(name, false, cl)
						} else {
							throw e
						}
					}

				}
			}
			var Object obj = ois.readObject
			ois.close
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
		val root = ResourcesPlugin::workspace.root
		root.getProject(projectName)
	}

	def IProject getVsumProject() {
		val vsumProject = getProject(this.vsumName)
		if (!vsumProject.exists) {
			createProject(vsumProject)
		} else if (!vsumProject.accessible) {
			deleteAndRecreateProject(vsumProject)
		}
		vsumProject
	}

	def private void deleteAndRecreateProject(IProject vsumProject) {
		try {
			vsumProject.delete(true, new NullProgressMonitor)
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
			// IProjectDescription description = project.description
			// description.natureIds = new String[] { VITRUVIUSNATURE::ID }
			// project.setDescription(description, null)
			createFolder(getCorrespondenceFolder(project))
			createFolder(getVsumFolder(project))
		} catch (CoreException e) {
			// soften
			throw new RuntimeException(e)
		}

	}

	def private static IFolder getVsumFolder(IProject project) {
		project.getFolder(VsumConstants::VSUM_FOLDER_NAME)
	}

	def static void createFolder(IFolder folder) throws CoreException {
		folder.create(false, true, null)
	}

	def IFolder getCorrespondenceFolder() {
		val vsumProject = getVsumProject
		getCorrespondenceFolder(vsumProject)
	}

	def private IFolder getCorrespondenceFolder(IProject project) {
		val correspondenceFolder = project.getFolder(VsumConstants::CORRESPONDENCE_FOLDER_NAME)
		correspondenceFolder
	}

	def private String getVsumMapFileName() {
		val file = getVsumInstancesFile
		file.location.toOSString
	}

	def IFile getVsumInstancesFile() {
		getVsumInstancesFile("")
	}

	def IFile getVsumInstancesFile(String prefix) {
		val file = getVsumProject.getFolder(VsumConstants::VSUM_FOLDER_NAME).getFile(prefix +
			VsumConstants::VSUM_INSTANCES_FILE_NAME)
		file
	}
}
