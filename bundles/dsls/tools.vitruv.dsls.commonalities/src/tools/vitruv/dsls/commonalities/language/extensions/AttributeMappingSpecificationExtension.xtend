package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EDataType
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation

@Utility
package class AttributeMappingSpecificationExtension {

	@Pure
	def static dispatch EDataType getProvidedType(extension AttributeMappingSpecifiation mappingSpec) {
	}

	@Pure
	def static dispatch EDataType getRequiredType(extension AttributeMappingSpecifiation mappingSpec) {}

	@Pure
	def static dispatch EDataType getProvidedType(extension AttributeEqualitySpecification mappingSpec) {
		attribute.type
	}

	@Pure
	def static dispatch EDataType getRequiredType(extension AttributeEqualitySpecification mappingSpec) {
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
