package tools.vitruv.dsls.commonalities.language.elements

import com.google.inject.Singleton
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.HashMap
import org.eclipse.emf.ecore.EPackage

@Singleton
class MetamodelProvider {

	/*
	 * In order to be referenced from a Xtext language, EObjects must be
	 * contained in a resource. So we create a fake resource to put our domain
	 * adapters in. This resource is never serialized and has no other purpose.
	 */
	val static CONTAINER_RESOURCE_URI = URI.createURI('synthetic:/commonalities/metamodelAdapters')
	val container = createContainerResource

	Map<String, Metamodel> allMetamodelsByName = new HashMap()

	package new() {
	}

	def registerReferencedMetamodel(String name, EPackage ePackage) {
		if (!allMetamodelsByName.containsKey(name)) {
			val metamodel = LanguageElementsFactory.eINSTANCE.createMetamodel.withClassifierProvider(
				ClassifierProvider.INSTANCE).forEPackage(ePackage)
			allMetamodelsByName.put(name, metamodel)
			container.contents += metamodel
		}
	}
	
	private def createContainerResource() {
		val resourceSet = new ResourceSetImpl
		return resourceSet.createResource(CONTAINER_RESOURCE_URI)
	}

	def getMetamodelByName(String name) {
		allMetamodelsByName.get(name)
	}

	def getAllMetamodels() {
		allMetamodelsByName.values
	}
}
