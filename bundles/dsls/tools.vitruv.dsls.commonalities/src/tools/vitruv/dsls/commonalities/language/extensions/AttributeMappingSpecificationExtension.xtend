package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.AttributeReadSpecification
import tools.vitruv.dsls.commonalities.language.AttributeSetSpecification

import static tools.vitruv.dsls.commonalities.language.extensions.AttributeDeclarationExtension.MOST_SPECIFIC_DATA_TYPE

@Utility
package class AttributeMappingSpecificationExtension {

	@Pure
	def static dispatch EDataType getProvidedType(AttributeReadSpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch EDataType getRequiredType(AttributeReadSpecification mappingSpec) {
		EcorePackage.eINSTANCE.EJavaObject
	}

	def static dispatch EDataType getProvidedType(AttributeSetSpecification mappingSpec) {
		MOST_SPECIFIC_DATA_TYPE
	}

	def static dispatch EDataType getRequiredType(AttributeSetSpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch EDataType getProvidedType(AttributeEqualitySpecification mappingSpec) {
		mappingSpec.attribute.type
	}

	@Pure
	def static dispatch EDataType getRequiredType(AttributeEqualitySpecification mappingSpec) {
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
}
