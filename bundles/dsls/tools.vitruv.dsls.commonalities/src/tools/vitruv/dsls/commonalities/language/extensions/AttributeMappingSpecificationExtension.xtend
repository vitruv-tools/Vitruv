package tools.vitruv.dsls.commonalities.language.extensions

import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import org.eclipse.emf.ecore.EClassifier
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration

@Utility
package class AttributeMappingSpecificationExtension {

	@Pure
	def static dispatch EClassifier getProvidedType(extension AttributeMappingSpecifiation mappingSpec) {
	}

	@Pure
	def static dispatch EClassifier getRequiredType(extension AttributeMappingSpecifiation mappingSpec) {}

	@Pure
	def static dispatch EClassifier getProvidedType(extension AttributeEqualitySpecification mappingSpec) {
		attribute.type
	}

	@Pure
	def static dispatch EClassifier getRequiredType(extension AttributeEqualitySpecification mappingSpec) {
		attribute.type
	}

	def static AttributeDeclaration getAttributeDeclaration(extension AttributeMappingSpecifiation mappingSpec) {
		if (eContainer instanceof AttributeDeclaration) {
			eContainer
		}
		throw new RuntimeException("Found a AttributeMappingSpecification that’s not in a AttributeDeclaration!")
	}

	def static dispatch getParticipation(extension AttributeMappingSpecifiation mappingSpec) {
	}

	def static dispatch getParticipation(extension AttributeEqualitySpecification mappingSpec) {
		attribute.participationClass.participation
	}
}
