package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.AttributeReadSpecification
import tools.vitruv.dsls.commonalities.language.AttributeSetSpecification
import tools.vitruv.dsls.commonalities.language.elements.Classifier
import tools.vitruv.dsls.commonalities.language.elements.WellKnownClassifiers

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*

@Utility
package class AttributeMappingSpecificationExtension {

	@Pure
	def static dispatch Classifier getProvidedType(AttributeReadSpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch Classifier getRequiredType(AttributeReadSpecification mappingSpec) {
		WellKnownClassifiers.JAVA_OBJECT
	}

	def static dispatch Classifier getProvidedType(AttributeSetSpecification mappingSpec) {
		WellKnownClassifiers.MOST_SPECIFIC_TYPE
	}

	def static dispatch Classifier getRequiredType(AttributeSetSpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch Classifier getProvidedType(AttributeEqualitySpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch Classifier getRequiredType(AttributeEqualitySpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	def static AttributeDeclaration getAttributeDeclaration(AttributeMappingSpecifiation mappingSpec) {
		if (mappingSpec.eContainer instanceof AttributeDeclaration) {
			mappingSpec.eContainer
		}
		throw new RuntimeException("Found an AttributeMappingSpecification that’s not in an AttributeDeclaration!")
	}

	def static getParticipation(AttributeMappingSpecifiation mappingSpec) {
		mappingSpec.attribute.participationClass.participation
	}
	
	def static getDeclaringAttribute(AttributeMappingSpecifiation mappingSpec) {
		mappingSpec.eContainer as AttributeDeclaration
	}

	def static dispatch isWrite(AttributeSetSpecification spec) {
		true
	}

	def static dispatch isWrite(AttributeReadSpecification spec) {
		false
	}

	def static dispatch isWrite(AttributeEqualitySpecification spec) {
		true
	}
	
	def static dispatch isRead(AttributeSetSpecification spec) {
		false
	}

	def static dispatch isRead(AttributeReadSpecification spec) {
		true
	}

	def static dispatch isRead(AttributeEqualitySpecification spec) {
		true
	}
}
