package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation

import static extension tools.vitruv.dsls.commonalities.language.extensions.AttributeMappingSpecificationExtension.*

@Utility
package class AttributeDeclarationExtension {

	/**
	 * A data type that will be handled as if every other data type is a
	 * supertype of it.
	 */
	public static val EDataType MOST_SPECIFIC_DATA_TYPE = null

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

		// we know that requiredType.isSuperTypeOf(providedType)
		return if (providedType != MOST_SPECIFIC_DATA_TYPE) providedType else requiredType
	}

	def private static isSuperTypeOf(EDataType a, EDataType b) {
		if (a == b) return true
		if (b === MOST_SPECIFIC_DATA_TYPE) return true
		if (a === MOST_SPECIFIC_DATA_TYPE) return false
		a.instanceClass.isAssignableFrom(b.instanceClass)
		return false
	}
}
