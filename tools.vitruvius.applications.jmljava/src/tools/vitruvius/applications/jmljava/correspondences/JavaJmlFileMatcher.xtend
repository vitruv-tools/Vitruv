package tools.vitruvius.applications.jmljava.correspondences

import tools.vitruvius.applications.jmljava.models.JMLModelURIProvider
import tools.vitruvius.applications.jmljava.models.JavaModelURIProvider
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.util.bridges.EMFBridge
import tools.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.core.resources.IResource

/**
 * Matcher for Java and JML URIs, which produces a list of URI pairs.
 * A Java URI has a corresponding JML URI if their URIs are them same
 * starting from the source or specification folder (excluding the
 * file extension). For example the Java URI src/a/b/c/Test.java
 * matches the JML URI specs/a/b/c/Test.java
 */
class JavaJmlFileMatcher {
	
	static val Logger LOGGER = Logger.getLogger(JavaJmlFileMatcher)
	val List<VURI> javaModels
	val List<VURI> jmlModels
	
	new (List<VURI> javaModels, List<VURI> jmlModels) {
		this.javaModels = javaModels
		this.jmlModels = jmlModels
	}
	
	public def findMatchingFiles() {
		val matchedModels = new ArrayList<Pair<VURI, VURI>>();

		val javaSourceDirs = new JavaModelURIProvider().relevantSourceDirs
		val jmlSourceDirs = new JMLModelURIProvider().relevantSourceDirs

		for (VURI javaUri : javaModels) {
			val matchedJmlModel = findMatchingJmlFileFor(javaUri, javaSourceDirs, jmlSourceDirs)
			if (matchedJmlModel != null) {
				matchedModels.add(matchedJmlModel)
			}
		}

		return matchedModels
	}

	private def findMatchingJmlFileFor(VURI javaUri, Iterable<IResource> javaSourceDirs,
		Iterable<IResource> jmlSourceDirs) {
		// this dummy segment is necessary since during resolve and deresolve the last segment gets lost
		// after adding this segment a useless segment is lost and the original uri remains valid
		val dummySegmentString = "dummy.file";
		for (javaSourceDir : javaSourceDirs) {
			val srcDirUri = EMFBridge.getEMFPlatformUriForIResource(javaSourceDir).appendSegment(dummySegmentString)
			val relativeJmlUri = javaUri.EMFUri.deresolve(srcDirUri, false, true, true).trimFileExtension.
				appendFileExtension("jml");
			for (jmlSourceDir : jmlSourceDirs) {
				val matchingJMLModel = relativeJmlUri.resolve(
					EMFBridge.getEMFPlatformUriForIResource(jmlSourceDir).appendSegment(dummySegmentString))
				if (jmlModels.exists[it.EMFUri.equals(matchingJMLModel)]) {
					// match found
					return new Pair(javaUri, VURI.getInstance(matchingJMLModel));
				}
			}
		}

		LOGGER.debug("No matching JML file for " + javaUri + " found.");
		return null;
	}
	
}