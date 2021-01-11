package tools.vitruv.dsls.commonalities.ui.validation

import tools.vitruv.dsls.commonalities.validation.CommonalitiesLanguageValidator
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import tools.vitruv.dsls.commonalities.language.Participation

/**
 * Validations that are only applicable when running in Eclipse.
 */
class CommonalitiesLanguageEclipseValidator extends CommonalitiesLanguageValidator {
	static val RUNTIME_PROJECT_BUNDLE = "tools.vitruv.extensions.dslsruntime.commonalities"
	static val RUNTIME_PROJECT_MARKER_TYPE = "tools.vitruv.extensions.dslruntime.commonalities.marker.RuntimeProjectMarker"

	@Check(NORMAL)
	def checkProjectSetup(CommonalityFile commonalityFile) {
		ProjectValidation.checkIsJavaPluginProject(this, commonalityFile, COMMONALITY_FILE__CONCEPT)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RUNTIME_PROJECT_BUNDLE,
			RUNTIME_PROJECT_MARKER_TYPE, commonalityFile, COMMONALITY_FILE__CONCEPT)
	}

	@Check(NORMAL)
	def checkParticipationDomainOnClasspath(Participation participation) {
		ProjectValidation.checkDomainProjectIsOnClasspath(this, services.typeReferences, participation.domainName,
			participation)
	}
}
