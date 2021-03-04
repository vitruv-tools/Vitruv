package tools.vitruv.dsls.mappings.ui.validation

import tools.vitruv.dsls.mappings.validation.MappingsLanguageValidator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.eclipse.xtext.validation.Check
import tools.vitruv.extensions.dslsruntime.mappings.marker.RuntimeProjectMarker
import tools.vitruv.dsls.common.elements.DomainReference
import tools.vitruv.dsls.common.ui.elements.CommonLanguageElementsEclipseValidation

class MappingsLanguageEclipseValidator extends MappingsLanguageValidator {
	@Check(NORMAL)
	def checkDomainDependency(DomainReference domainReference) {
		CommonLanguageElementsEclipseValidation.checkDomainDependency(this, services.typeReferences, domainReference)
	}

	@Check(NORMAL)
	def checkMappingBaseFile(MappingsFile mappingsFile) {
		ProjectValidation.checkIsJavaPluginProject(this, mappingsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RuntimeProjectMarker,
			mappingsFile)
	}
}
