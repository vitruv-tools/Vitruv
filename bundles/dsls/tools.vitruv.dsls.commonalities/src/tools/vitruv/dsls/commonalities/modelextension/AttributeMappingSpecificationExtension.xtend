package tools.vitruv.dsls.commonalities.modelextension

import tools.vitruv.dsls.commonalities.commonalitiesLanguage.AttributeMappingSpecifiation
import org.eclipse.emf.ecore.EClassifier
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.AttributeEqualitySpecification
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility package class AttributeMappingSpecificationExtension {

	@Pure
	def static dispatch EClassifier getProvidedType(AttributeMappingSpecifiation mappingSpec) {
	}

	@Pure
	def static dispatch EClassifier getRequiredType(AttributeMappingSpecifiation mappingSpec) {}

	@Pure
	def static dispatch EClassifier getProvidedType(AttributeEqualitySpecification mappingSpec) {
	}

	@Pure
	def static dispatch EClassifier getRequiredType(AttributeEqualitySpecification mappingSpec) {}
}
