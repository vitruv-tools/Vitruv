package tools.vitruv.dsls.commonalities.scoping

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class ComposedScope implements IScope {
	
	List<IScope> delegates
	
	new(IScope... delegates) {
		this.delegates = delegates
	}
	
	def operator_plus(IScope delegate) {
		delegates += delegate
	}
	
	def operator_plus(IScope... delegate) {
		delegates += delegate
	}
	
	override getAllElements() {
		delegates.flatMap [allElements]
	}
	
	override getElements(QualifiedName name) {
		delegates.flatMap [getElements(name)]
	}
	
	override getElements(EObject object) {
		delegates.flatMap [getElements(object)]
	}
	
	override getSingleElement(QualifiedName name) {
		delegates.map[getSingleElement(name)].findFirst [it !== null]
	}
	
	override getSingleElement(EObject object) {
		delegates.map[getSingleElement(object)].findFirst [it !== null]
	}
	
	override toString() {
		'''
		[
			«FOR delegate : delegates»
			-> «delegate»
			«ENDFOR»
		]
		'''
	}
	
}