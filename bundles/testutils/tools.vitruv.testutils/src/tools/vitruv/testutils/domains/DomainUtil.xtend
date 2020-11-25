package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.domains.VitruvDomainProvider
import static com.google.common.base.Preconditions.checkState
import java.nio.file.Path

@Utility
class DomainUtil {
	/**
	 * @return the file extension for the domain provided by the {@code domainProvider}. 
	 */
	static def String getFileExtension(VitruvDomainProvider<?> domainProvider) {
		val domainFileExtensions = domainProvider.domain.fileExtensions
		checkState(domainFileExtensions.size >
			0, '''The domain «domainProvider.domain.name» defines no file extensions!''')
		return domainFileExtensions.get(0)
	}

	/**
	 * @return the provided {@code modelPath}, with the file extension for {@code domainProvider} appended
	 */
	static def getModelFileName(String modelPath, VitruvDomainProvider<?> domainProvider) {
		getModelFileName(Path.of(modelPath), domainProvider)
	}

	/**
	 * @return the provided {@code modelPath}, with the file extension for {@code domainProvider} appended
	 */
	static def getModelFileName(Path modelPath, VitruvDomainProvider<?> domainProvider) {
		modelPath.resolveSibling('''«modelPath.fileName».«domainProvider.fileExtension»''')
	}
}
