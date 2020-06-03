package tools.vitruv.dsls.commonalities.scoping

import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription
import org.eclipse.xtext.scoping.IScope

@FinalFieldsConstructor
class NameTransformingScope implements IScope {

	val IScope delegate
	val Function<QualifiedName, QualifiedName> queryTransformer
	val Function<QualifiedName, QualifiedName> resultTransformer

	override getAllElements() {
		delegate.allElements.map[transformResult]
	}

	override getElements(QualifiedName name) {
		delegate.getElements(transformQuery(name)).map[transformResult]
	}

	override getElements(EObject object) {
		delegate.getElements(object).map[transformResult]
	}

	override getSingleElement(QualifiedName name) {
		delegate.getSingleElement(transformQuery(name)).transformResult
	}

	override getSingleElement(EObject object) {
		delegate.getSingleElement(object).transformResult
	}

	private def QualifiedName transformQuery(QualifiedName name) {
		queryTransformer.apply(name)
	}

	private def QualifiedName transformResult(QualifiedName name) {
		resultTransformer.apply(name)
	}

	private def IEObjectDescription transformResult(IEObjectDescription description) {
		if (description === null) return null
		val transformedName = transformResult(description.name)
		return new AliasedEObjectDescription(transformedName, description)
	}

	override toString() {
		'''transforming «delegate»'''
	}
}
