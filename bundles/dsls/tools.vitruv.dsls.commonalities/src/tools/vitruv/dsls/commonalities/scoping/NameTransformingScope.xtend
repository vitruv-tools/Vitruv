package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription
import org.eclipse.xtext.scoping.IScope

@FinalFieldsConstructor
abstract class NameTransformingScope implements IScope {
	protected val IScope delegate

	override getAllElements() {
		delegate.allElements.map [transformResult()]
	}

	override getElements(QualifiedName name) {
		delegate.getElements(transformQuery(name)).map [transformResult()]
	}

	override getElements(EObject object) {
		delegate.getElements(object).map [transformResult()]
	}

	override getSingleElement(QualifiedName name) {
		delegate.getSingleElement(transformQuery(name)).transformResult()
	}

	override getSingleElement(EObject object) {
		delegate.getSingleElement(object).transformResult()
	}

	def protected abstract QualifiedName transformQuery(QualifiedName name)

	def protected abstract QualifiedName transformResult(QualifiedName name)

	private def IEObjectDescription transformResult(IEObjectDescription description) {
		if (description === null) return null
		val transformedName = transformResult(description.name)
		return new AliasedEObjectDescription(transformedName, description)
	}

	override toString() {
		'''transforming «delegate»'''
	}
}
