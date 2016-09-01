package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.IContentValidator
import edu.kit.ipd.sdq.vitruvius.domains.java.util.jamoppparser.JaMoPPParser
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