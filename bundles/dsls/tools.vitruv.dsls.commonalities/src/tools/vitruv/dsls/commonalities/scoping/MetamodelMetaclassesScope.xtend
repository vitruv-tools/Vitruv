package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*
import tools.vitruv.dsls.commonalities.language.elements.MetamodelProvider

@Singleton
class MetamodelMetaclassesScope implements IScope {

	@Inject MetamodelProvider metamodelProvider
	@Inject IEObjectDescriptionProvider descriptionProvider

	override getAllElements() {
		metamodelProvider.allDomains.flatMap[metaclasses].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		val domainName = qName.domainName
		if (domainName === null) return #[]
		val className = qName.className
		if (className === null) return #[]

		return (metamodelProvider.getDomainByName(domainName)?.metaclasses ?: #[])
			.filter[name == className]
			.map(descriptionProvider)
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
		'''«MetamodelMetaclassesScope.simpleName» for domains «metamodelProvider.allDomains.toList»'''
	}
}
