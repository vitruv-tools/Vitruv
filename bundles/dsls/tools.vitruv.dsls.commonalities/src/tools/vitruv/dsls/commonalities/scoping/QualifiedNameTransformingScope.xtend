package tools.vitruv.dsls.commonalities.scoping

import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope

@FinalFieldsConstructor
class QualifiedNameTransformingScope implements IScope {
	val IScope delegate
	val Function<QualifiedName, QualifiedName> transformer
	
	override getAllElements() {
		delegate.allElements
	}
	
	override getElements(QualifiedName name) {
		delegate.getElements(transform(name))
	}
	
	override getElements(EObject object) {
		delegate.getElements(object)
	}
	
	override getSingleElement(QualifiedName name) {
		delegate.getSingleElement(transform(name))
	}
	
	override getSingleElement(EObject object) {
		delegate.getSingleElement(object)
	}
	
	def private QualifiedName transform(QualifiedName qName) {
		transformer.apply(qName)
	}
	
	override toString() {
		'''transforming «delegate»'''
	}
	
}