package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IContainer.Manager
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

class MappingParameterScopeFinder {

	private static IContainer.Manager containerManager;
	private static ResourceDescriptionsProvider resourceDescriptionsProivder

	def static init(Manager manager, ResourceDescriptionsProvider provider) {
		MappingParameterScopeFinder.containerManager = manager
		MappingParameterScopeFinder.resourceDescriptionsProivder = provider
	}

	def static findReferences(EObject object) {
		val resource = object.eResource
		val uri = EcoreUtil.getURI(object)
		val descriptions = resourceDescriptionsProivder.getResourceDescriptions(resource)
		val description = descriptions.getResourceDescription(resource.URI)
		description.getExportedObjectsByObject(object).forEach[
			println('''«it.name»  «it.EObjectOrProxy»  «it.EObjectURI»''')
		]
		val container = containerManager.getContainer(description, descriptions)
/* 		container.resourceDescriptions.forEach [
			it.referenceDescriptions.filter [
				it.sourceEObjectUri == uri
			].forEach [
println('''from «it.sourceEObjectUri»
to «it.targetEObjectUri»
ref «it.EReference.name»''')
			]
		]
*/
	}

}
