package tools.vitruv.dsls.commonalities.ui.validation

import tools.vitruv.dsls.commonalities.validation.CommonalitiesLanguageValidator
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import tools.vitruv.extensions.dslruntime.commonalities.marker.RuntimeProjectMarker
import tools.vitruv.dsls.common.elements.MetamodelImport

/**
 * Validations that are only applicable when running in Eclipse.
 */
class CommonalitiesLanguageEclipseValidator extends CommonalitiesLanguageValidator {
	@Check(NORMAL)
	def checkProjectSetup(CommonalityFile commonalityFile) {
		ProjectValidation.checkIsJavaPluginProject(this, commonalityFile, COMMONALITY_FILE__CONCEPT)
		ProjectValidation.checkRuntimeProjectIsOnClasspath(this, services.typeReferences, RuntimeProjectMarker,
			commonalityFile, COMMONALITY_FILE__CONCEPT)
	}
	
	@Check(NORMAL)
	def checkMetamodelOnClasspath(MetamodelImport metamodelImport) {
		if (metamodelImport.package !== null) {
			ProjectValidation.checkMetamodelProjectIsOnClasspath(this, services.typeReferences, metamodelImport.package,
				metamodelImport)
		}
	}
	
}
