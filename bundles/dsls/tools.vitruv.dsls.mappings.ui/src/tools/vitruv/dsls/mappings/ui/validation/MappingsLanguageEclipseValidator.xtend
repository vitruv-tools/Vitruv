package tools.vitruv.dsls.mappings.ui.validation

import tools.vitruv.dsls.mappings.validation.MappingsLanguageValidator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.eclipse.xtext.validation.Check
import tools.vitruv.extensions.dslsruntime.mappings.marker.RuntimeProjectMarker

class MappingsLanguageEclipseValidator extends MappingsLanguageValidator {
	@Check(NORMAL)
	def checkMappingBaseFile(MappingsFile mappingsFile) {
		ProjectValidation.checkIsJavaPluginProject(this, mappingsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RuntimeProjectMarker,
			mappingsFile)
	}
}
