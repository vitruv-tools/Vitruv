package tools.vitruv.dsls.reactions.ui.validation

import tools.vitruv.dsls.reactions.validation.ReactionsLanguageValidator
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.common.elements.DomainReference
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import tools.vitruv.extensions.dslsruntime.reactions.marker.RuntimeProjectMarker
import tools.vitruv.dsls.common.ui.elements.CommonLanguageElementsEclipseValidation

class ReactionsLanguageEclipseValidator extends ReactionsLanguageValidator {
	@Check(NORMAL)
	def checkDomainDependency(DomainReference domainReference) {
		CommonLanguageElementsEclipseValidation.checkDomainDependency(this, services.typeReferences, domainReference)
	}

	@Check(NORMAL)
	override checkReactionsFile(ReactionsFile reactionsFile) {
		super.checkReactionsFile(reactionsFile)
		ProjectValidation.checkIsJavaPluginProject(this, reactionsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RuntimeProjectMarker,
			reactionsFile)
	}
}
