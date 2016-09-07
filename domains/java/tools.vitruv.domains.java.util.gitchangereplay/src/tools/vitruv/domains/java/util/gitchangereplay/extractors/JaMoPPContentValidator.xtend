package tools.vitruv.domains.java.util.gitchangereplay.extractors

import tools.vitruv.domains.java.util.gitchangereplay.extractors.IContentValidator
import tools.vitruv.domains.java.util.jamoppparser.JaMoPPParser
import java.io.ByteArrayInputStream
import org.eclipse.emf.common.util.URI
import java.nio.charset.StandardCharsets

class JaMoPPContentValidator implements IContentValidator {
	
	override isValid(String content, URI contentUri) {
		try {
			val parser = new JaMoPPParser()
			val inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))
			// Check if parseable TODO how to check?!
			val cu = parser.parseCompilationUnitFromInputStream(contentUri, inputStream)
			if (cu != null) {
				return true
			}
		} catch (Exception e) {
			return false
		}
		return false
	}
	
}