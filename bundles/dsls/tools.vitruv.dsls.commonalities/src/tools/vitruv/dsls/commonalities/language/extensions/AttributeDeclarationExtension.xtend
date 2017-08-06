package tools.vitruv.dsls.commonalities.language.extensions

import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.EClassifier
import static extension tools.vitruv.dsls.commonalities.language.extensions.AttributeMappingSpecificationExtension.*
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import org.eclipse.emf.ecore.EDataType

@Utility package class AttributeDeclarationExtension {

	/**
	 * Foo bar
	 */
	def static EDataType getType(AttributeDeclaration attribute) {
		if (attribute.mappings.length === 0) return EcorePackage.eINSTANCE.EJavaObject

		val mappingIterator = attribute.mappings.iterator
		val firstMapping = mappingIterator.next
		var requiredType = firstMapping.requiredType
		var providedType = firstMapping.providedType
		for (var AttributeMappingSpecifiation mapping; mappingIterator.hasNext; mapping = mappingIterator.next) {
			// if this is not the case, the specification itself is invalid, so we skip it.
			if (mapping !== null && mapping.requiredType.isSuperTypeOf(mapping.providedType)) {
				if (!mapping.requiredType.isSuperTypeOf(requiredType)) {
					if (mapping.requiredType.isSuperTypeOf(providedType)) {
						requiredType = mapping.requiredType
					} else {
						// specification not compatible to others
					}
				}
				if (!providedType.isSuperTypeOf(mapping.providedType)) {
					if (requiredType.isSuperTypeOf(mapping.providedType)) {
						providedType = mapping.providedType
					}
				}
			}
		}

		// assert requiredType.isSuperTypeOf(providedType)
		return providedType
	}

	def private static isSuperTypeOf(EClassifier a, EClassifier b) {
		if (a == b) return true
		if (a instanceof EClass) {
			if (b instanceof EClass) {
				return a.isSuperTypeOf(b)
			}
		}
		return false
	}
}
