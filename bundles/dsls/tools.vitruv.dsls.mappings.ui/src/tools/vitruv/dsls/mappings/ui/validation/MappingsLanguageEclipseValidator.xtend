package tools.vitruv.dsls.mappings.ui.validation

import tools.vitruv.dsls.mappings.validation.MappingsLanguageValidator
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.mirbase.ui.validation.MirBaseEclipseValidation

class MappingsLanguageEclipseValidator extends MappingsLanguageValidator {
	static val RUNTIME_PROJECT_BUNDLE = "tools.vitruv.extensions.dslsruntime.mappings"
	static val RUNTIME_PROJECT_MARKER_TYPE = "tools.vitruv.extensions.dslruntime.mappings.RuntimeProjectMarker"

	@Check(NORMAL)
	def checkDomainDependency(DomainReference domainReference) {
		MirBaseEclipseValidation.checkDomainDependency(this, services.typeReferences, domainReference)
	}

	@Check(NORMAL)
	def checkMappingBaseFile(MappingsFile mappingsFile) {
		ProjectValidation.checkIsJavaPluginProject(this, mappingsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RUNTIME_PROJECT_BUNDLE,
			RUNTIME_PROJECT_MARKER_TYPE, mappingsFile)
	}
}
