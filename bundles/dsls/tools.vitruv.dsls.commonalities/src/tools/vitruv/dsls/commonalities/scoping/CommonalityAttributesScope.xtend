package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class CommonalityAttributesScope implements IScope {
	@Inject IEObjectDescriptionProvider descriptionProvider
	val Commonality commonality

	private def allAttributes() {
		commonality.attributes
	}

	override getAllElements() {
		allAttributes.map(descriptionProvider)
	}

	override getElements(QualifiedName qName) {
		val memberName = qName.memberName
		return if (memberName !== null) { 
			allAttributes.filter[name == memberName].map(descriptionProvider)
		} else {
			emptyList()
		}
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
	
	static class Factory extends InjectingFactoryBase {
		def forCommonality(Commonality commonality) {
			new CommonalityAttributesScope(checkNotNull(commonality)).injectMembers
		}
	}
}
