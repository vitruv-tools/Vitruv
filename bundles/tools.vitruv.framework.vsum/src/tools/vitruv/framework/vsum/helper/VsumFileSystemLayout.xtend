package tools.vitruv.framework.vsum.helper

import java.io.IOException
import java.nio.file.Path

import static com.google.common.base.Preconditions.checkState
import static java.nio.file.Files.createDirectories
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import org.eclipse.emf.common.util.URI

class VsumFileSystemLayout {
	static final String CORRESPONDENCES_FILE = "correspondences.correspondence";
	static final String MODELS_FILE = "models.models";
	static final String VSUM_FOLDER_NAME = "vsum";

	val Path vsumProjectFolder
	var prepared = false

	new(Path vsumProjectFolder) {
		this.vsumProjectFolder = vsumProjectFolder
	}

	def void prepare() throws IOException {
		createDirectories(vsumFolder)
		prepared = true
	}

	def URI getCorrespondencesURI() {
		checkPrepared()
		return vsumFolder.resolve(CORRESPONDENCES_FILE).toFile.createFileURI()
	}

	def Path getModelsNamesFilesPath() {
		checkPrepared()
		return vsumFolder.resolve(MODELS_FILE)
	}

	def Path getVsumProjectFolder() {
		return this.vsumProjectFolder
	}

	def private getVsumFolder() {
		vsumProjectFolder.resolve(VSUM_FOLDER_NAME)
	}

	override String toString() {
		return '''@«vsumProjectFolder»'''
	}

	def private void checkPrepared() {
		checkState(prepared, "The file system layout has not been loaded yet!")
	}
}
