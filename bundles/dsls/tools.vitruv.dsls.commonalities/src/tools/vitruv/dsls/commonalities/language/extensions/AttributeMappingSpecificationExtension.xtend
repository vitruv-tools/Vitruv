package tools.vitruv.dsls.commonalities.language.extensions

import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import org.eclipse.emf.ecore.EDataType

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
