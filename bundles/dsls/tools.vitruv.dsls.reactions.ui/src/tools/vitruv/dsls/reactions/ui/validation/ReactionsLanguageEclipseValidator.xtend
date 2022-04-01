package tools.vitruv.dsls.reactions.ui.validation

import tools.vitruv.dsls.reactions.validation.ReactionsLanguageValidator
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsFile
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import tools.vitruv.extensions.dslsruntime.reactions.marker.RuntimeProjectMarker
import tools.vitruv.dsls.common.elements.MetamodelImport

class ReactionsLanguageEclipseValidator extends ReactionsLanguageValidator {
	@Check(NORMAL)
	override checkReactionsFile(ReactionsFile reactionsFile) {
		super.checkReactionsFile(reactionsFile)
		ProjectValidation.checkIsJavaPluginProject(this, reactionsFile)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RuntimeProjectMarker,
			reactionsFile)
	}

	@Check(NORMAL)
	def checkMetamodelOnClasspath(MetamodelImport metamodelImport) {
		if (metamodelImport.package !== null) {
			ProjectValidation.checkMetamodelProjectIsOnClasspath(this, services.typeReferences, metamodelImport.package,
				metamodelImport)
		}
	}
}
