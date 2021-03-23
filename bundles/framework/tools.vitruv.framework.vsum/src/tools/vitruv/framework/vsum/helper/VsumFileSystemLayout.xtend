package tools.vitruv.framework.vsum.helper

import java.io.IOException
import java.net.URLEncoder
import java.nio.file.Path
import tools.vitruv.framework.util.datatypes.VURI

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static java.nio.charset.StandardCharsets.UTF_8
import static java.nio.file.Files.createDirectories
import static tools.vitruv.framework.vsum.VsumConstants.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI

class VsumFileSystemLayout {
	static final String CORRESPONDENCES_FILE_EXT = "correspondence";
	static final String UUID_FILE_EXT = "uuid";
	
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
		checkArgument(metadataKey.get(metadataKey.length - 1).contains('.'), "metadataKey is missing a file extension!")
		
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
		return VURI.getInstance(metadataPath.toFile.createFileURI)
	}
	
	def VURI getCorrespondencesVURI() {
		checkPrepared()
		return VURI.getInstance(correspondenceModelPath.toFile.createFileURI) 
	}
	
	def private getCorrespondenceModelPath() {
		correspondenceFolder.resolve('''Correspondences.«CORRESPONDENCES_FILE_EXT»''')
	}
	
	def VURI getUuidProviderAndResolverVURI() {
		checkPrepared()
		val uuidPath = uuidProviderAndResolverFolder.resolve('''Uuid.«UUID_FILE_EXT»''')
		return VURI.getInstance(uuidPath.toFile.createFileURI) 
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