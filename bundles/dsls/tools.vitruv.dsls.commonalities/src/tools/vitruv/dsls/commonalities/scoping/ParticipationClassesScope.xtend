package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase

@FinalFieldsConstructor
class ParticipationClassesScope implements IScope {
	@Inject IEObjectDescriptionProvider descriptionProvider
	val Commonality commonality

	override getAllElements() {
		commonality.participations.flatMap [allClasses].map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		val domainName = qName.domainName
		if (domainName === null) return emptyList()
		val className = qName.className
		if (className === null) return emptyList()

		return commonality.participations
			.filter [name == domainName]
			.flatMap [allClasses]
			.filter [name == className]
			.map(descriptionProvider)
	}

	override getElements(EObject object) {
		val objectURI = EcoreUtil2.getURI(object)
		return allElements.filter [it.EObjectOrProxy === object || it.EObjectURI == objectURI]
	}

	override getSingleElement(QualifiedName name) {
		getElements(name).head
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}
	
	override toString() {
		'''all participations classes of ‹«commonality»›'''
	}
	
	static class Factory extends InjectingFactoryBase {
		def forCommonality(Commonality commonality) {
			new ParticipationClassesScope(checkNotNull(commonality)).injectMembers
		}
	}
}
