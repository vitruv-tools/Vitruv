package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*
import tools.vitruv.dsls.commonalities.language.elements.MetamodelProvider
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.dsls.common.elements.MetamodelImport
import tools.vitruv.dsls.common.elements.ElementsPackage
import java.util.Set
import java.util.HashSet
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.emf.ecore.EPackage

class MetamodelMetaclassesScope implements IScope {
	val IEObjectDescriptionProvider descriptionProvider
	val MetamodelProvider metamodelProvider
	val Set<String> metamodelNames = new HashSet

	new(Resource resource, IEObjectDescriptionProvider descriptionProvider, MetamodelProvider provider) {
		this.descriptionProvider = descriptionProvider
		this.metamodelProvider = provider
		val importedMetamodels = resource.extractImportedMetamodels
		this.metamodelNames += importedMetamodels.map[name].toSet
		importedMetamodels.forEach[metamodelProvider.registerReferencedMetamodel(name, ePackage)]
	}
	
	@Data
	static class ImportedMetamodel {
		val String name
		val EPackage ePackage
	}

	private def extractImportedMetamodels(Resource res) {
		var imports = res.allContents.filter[eClass == ElementsPackage.eINSTANCE.getMetamodelImport].toList.filter(
			MetamodelImport).filter[package !== null]
		val importedMetamodels = imports.map [
			new ImportedMetamodel(it.name ?: it.package.name, it.package)
		]
		return importedMetamodels
	}

	override getAllElements() {
		metamodelNames.map[metamodelProvider.getMetamodelByName(it)].flatMap[metaclasses].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		val metamodelName = qName.metamodelName
		if(metamodelName === null || !metamodelNames.contains(metamodelName)) return #[]
		val className = qName.className
		if(className === null) return #[]

		return (metamodelProvider.getMetamodelByName(metamodelName)?.metaclasses ?: #[]). //
		filter[name == className].map(descriptionProvider)
	}

	override getElements(EObject object) {
		val objectURI = EcoreUtil2.getURI(object)
		return allElements.filter[it.EObjectOrProxy === object || it.EObjectURI == objectURI]
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}

	override toString() {
		'''«MetamodelMetaclassesScope.simpleName» for metamodels «metamodelNames»'''
	}

}
