package tools.vitruv.framework.vsum.helper

import edu.kit.ipd.sdq.activextendannotations.CloseResource
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamClass
import java.net.URLEncoder
import java.nio.file.Path
import java.util.HashSet
import java.util.Set
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.VURI

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static java.nio.charset.StandardCharsets.UTF_8
import static java.nio.file.Files.*
import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.newOutputStream
import static tools.vitruv.framework.util.VitruviusConstants.*
import static tools.vitruv.framework.vsum.VsumConstants.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.nio.file.NoSuchFileException

class VsumFileSystemLayout {
	val Path vsumProjectFolder
	var prepared = false
	
	new(Path vsumProjectFolder) {
		this.vsumProjectFolder = vsumProjectFolder 
	}
	
	def void prepare() throws IOException {
		createDirectories(uuidProviderAndResolverFolder) 
		createDirectories(correspondenceFolder) 
		createDirectories(vsumFolder) 
		createDirectories(consistencyMetadataFolder) 
		prepared = true 
	}
	
	def private Path getMetadataFilePath(String... metadataKey) {
		checkArgument(metadataKey !== null || metadataKey.length > 0, "The key must have at least one part!")
		checkArgument(metadataKey.get(metadataKey.length - 1).contains(fileExtSeparator), "metadataKey is missing a file extension!")
		
		return metadataKey.fold(Path.of("")) [ last, keyPart |
			checkArgument(keyPart !== null, "A key part must not be null!")
			// URL-encoding the string makes it save for being a file part,
			// except for the cases '', '.' and '..'
			// we thus use _ as an escape character
			// This also ensures that the resulting path is always located
			// within the metadata folder.
			val preparedKeyPart = switch (keyPart) {
				case ".": "_."
				case "..": "_._."
				case "": "_"
				default: keyPart.replaceAll("_", "__")
			}
			last.resolve(URLEncoder.encode(preparedKeyPart, UTF_8))
		]
	}
	
	/** 
	 * Gets the {@link VURI} of a model that stores metadata.
	 * @param metadataKeyThe key uniquely identifying the metadata model. The different parts of the key
	 * can be used to convey some sort of hierarchy in the metadata. The key may contain
	 * arbitrary characters. The last key part contains the metadata model's file name
	 * and extension.
	 * @return the VURI of the specified metadata model
	 */
	def VURI getConsistencyMetadataModelVURI(String... metadataKey) {
		checkPrepared()
		var metadataPath = consistencyMetadataFolder.resolve(getMetadataFilePath(metadataKey)) 
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(metadataPath.toFile)) 
	}
	
	def VURI getCorrespondencesVURI() {
		checkPrepared()
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(correspondenceModelPath.toFile)) 
	}
	
	def private getCorrespondenceModelPath() {
		correspondenceFolder.resolve('''Correspondences«fileExtSeparator»«correspondencesFileExt»''')
	}
	
	def VURI getUuidProviderAndResolverVURI() {
		checkPrepared()
		val uuidPath = uuidProviderAndResolverFolder.resolve('''Uuid«fileExtSeparator»«uuidFileExt»''')
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(uuidPath.toFile)) 
	}
	
	def void saveCorrespondenceModelMMURIs() {
		checkPrepared()
		// FIXME This does nothing reasonable anymore
		var Set<VURI> mmURIsSet=new HashSet() 
		// Arrays.asList(mmURIs));
		saveVURISetToFile(mmURIsSet, correspondenceModelPath) 
	}
	
	def void saveVsumVURIs(Set<VURI> vuris) {
		checkPrepared()
		saveVURISetToFile(vuris, vsumInstancesFile) 
	}
	
	def private static void saveVURISetToFile(Set<VURI> vuris, Path file) {
		val stringSet = vuris.mapFixedTo(new HashSet(vuris.size)) [EMFUri.toString]
		saveObjectToFile(stringSet, file) 
	}
	
	def private static void saveObjectToFile(Object object, Path file) {
		try {
			// TODO: this code could be optimized in a way that a new method is provide for sets of
			// strings where only the new strings are appended to the file
			saveObjectToStream(object, new ObjectOutputStream(newOutputStream(file)))
		} catch (IOException e) {
			throw new RuntimeException('''Could not save ‹«object»› to ‹«file»›''', e)
		}
	}
	
	def private static void saveObjectToStream(Object object, ObjectOutputStream outputStream) {
		outputStream.writeObject(object)
		outputStream.flush()
	}
	
	def Set<VURI> loadVsumVURIs() {
		checkPrepared()
		loadVURISetFromFile(vsumInstancesFile) 
	}
	
	def private static Set<VURI> loadVURISetFromFile(Path file) {
		val stringSet = loadStringSetFromFile(file)
		stringSet.mapFixedTo(new HashSet(stringSet.size)) [VURI.getInstance(it)]
	}
	
	def private static Set<String> loadStringSetFromFile(Path file) {
		val object = loadObjectFromFile(file)
		switch(object) {
			case null: emptySet
			Set<String>: object
			default: throw new RuntimeException('''The file '«file»' does not contain a set of strings!''')
		} 
	}
	
	def private static Object loadObjectFromFile(Path file) {
		return loadObjectFromFile(file, null) 
	}
	
	def private static Object loadObjectFromFile(Path file, ClassLoader cl) {
		try {
			loadObjectsFromStream(newInputStream(file), cl)
		} catch (NoSuchFileException e) {
			return null 
		}
	}
	
	def private static Object loadObjectsFromStream(@CloseResource InputStream inputStream, ClassLoader classLoader) {
		val objectInputStream = if (classLoader === null) {
			new ObjectInputStream(inputStream)
		} else {
			new ObjectInputStream(inputStream) {
				override protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
					try {
						super.resolveClass(desc) 
					} catch (ClassNotFoundException e) {
						var String name = desc.getName() 
						Class.forName(name, false, classLoader) 
					}
				}
			}
		}
		loadObjectsFromStream(objectInputStream) 
	}
	
	def private static Object loadObjectsFromStream(@CloseResource ObjectInputStream objectStream) {
		objectStream.readObject()
	}

	def Path getVsumProjectFolder() {
		return this.vsumProjectFolder 
	}

	def private getVsumFolder() {
		vsumProjectFolder.resolve(VSUM_FOLDER_NAME) 
	}

	def private getCorrespondenceFolder() {
		vsumProjectFolder.resolve(CORRESPONDENCE_FOLDER_NAME) 
	}

	def private getUuidProviderAndResolverFolder() {
		vsumProjectFolder.resolve(UUID_PROVIDER_AND_RESOLVER_FOLDER_NAME) 
	}

	def private Path getConsistencyMetadataFolder() {
		vsumProjectFolder.resolve(CONSISTENCY_METADATA_FOLDER_NAME) 
	}
	
	def Path getVsumInstancesFile() {
		checkPrepared() 
		vsumFolder.resolve(VSUM_INSTANCES_FILE_NAME) 
	}
	
	override String toString() {
		return '''@«vsumProjectFolder»''' 
	}
	
	def private void checkPrepared() {
		checkState(prepared, "The file system layout has not been loaded yet!") 
	}
}