package tools.vitruv.framework.util.datatypes.impl

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import org.eclipse.emf.common.CommonPlugin
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.VURI

class VURIImpl extends VURI implements Serializable {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	@Accessors(PUBLIC_GETTER)
	transient URI emfURI

	/** 
	 * Multiton classes should not have a public or default constructor. 
	 */
	new(String uriString) {
		emfURI = EMFBridge::createURI(uriString)
	}

	override toString() '''«emfURI.toString»'''

	override compareTo(VURI otherVURI) {
		toString.compareTo(otherVURI.toString)
	}

	def dispatch equals(Object o) {
		false
	}

	def dispatch equals(VURI v) {
		toString == v.toString
	}

	override String toResolvedAbsolutePath() {
		CommonPlugin::resolve(emfURI).toFileString
	}

	override URI getEMFUri() {
		emfURI
	}

	override String getFileExtension() {
		emfURI.fileExtension
	}

	override String getLastSegment() {
		val lastSegment = emfURI.lastSegment
		if (null === lastSegment)
			""
		else
			lastSegment
	}

	override createVURIByReplacing(String toReplace, String toInsert) {
		return VURI::getInstance(URI::createURI(toString.replace(toReplace, toInsert)))
	}

	/** 
	 * Returns a new VURI that is created from the actual VURI by replacing its
	 * file extension with newFileExt
	 * @param newFileExt
	 * @return the new VURI with the replaced file extension
	 */
	override VURI replaceFileExtension(String newFileExt) {
		VURI::getInstance(emfURI.trimFileExtension.appendFileExtension(newFileExt))
	}

	// Needed for serialization
	protected def void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(emfURI.toString)
	}

	// Needed for deserialization
	protected def void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		val emfURIString = stream.readObject as String
		emfURI = EMFBridge::createURI(emfURIString)
	}

}
