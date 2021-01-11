package tools.vitruv.dsls.reactions.ui.validation

import tools.vitruv.dsls.reactions.validation.ReactionsLanguageValidator
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import tools.vitruv.dsls.mirbase.ui.validation.MirBaseEclipseValidation

class ReactionsLanguageEclipseValidator extends ReactionsLanguageValidator {
	static val RUNTIME_PROJECT_BUNDLE = "tools.vitruv.extensions.dslsruntime.reactions"
	static val RUNTIME_PROJECT_MARKER_TYPE = "tools.vitruv.extensions.dslruntime.reactions.RuntimeProjectMarker"

	@Check(NORMAL)
	def checkDomainDependency(DomainReference domainReference) {
		MirBaseEclipseValidation.checkDomainDependency(this, services.typeReferences, domainReference)
	}

	@Check(NORMAL)
	override checkReactionsFile(ReactionsFile reactionsFile) {
		super.checkReactionsFile(reactionsFile)
		ProjectValidation.checkIsJavaPluginProject(this, reactionsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RUNTIME_PROJECT_BUNDLE,
			RUNTIME_PROJECT_MARKER_TYPE, reactionsFile)
	}
}
