package tools.vitruv.extensions.dslsruntime.mappings.registry

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate

class SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> implements IElementsCandidatesAndInstanceHalvesRegistry<L,R>, CandidatesAndInstanceHalves4ReactionsRegistry<L,R> {
	@Accessors(PUBLIC_GETTER)
	val IElementsRegistry elementsRegistry
	@Accessors(PUBLIC_GETTER)
	@Delegate
	val ILeftAndRightInstanceHalvesRegistry<L,R> leftAndRightRegistry
	
	new(String mappingName) {
		this.leftAndRightRegistry = new HashMapLeftAndRightInstanceHalfRegistry(mappingName)
		this.elementsRegistry = new SetMultimapElementsRegistry(mappingName)
	}
	
	override <C extends EObject> void removeLeftElementAndCandidates(Class<C> clazz, C element) {
		elementsRegistry.removeElement(clazz, element)
		leftAndRightRegistry.removeLeftCandidatesForElement(element)
	}
	
	override <C extends EObject> void removeRightElementAndCandidates(Class<C> clazz, C element) {
		elementsRegistry.removeElement(clazz, element)
		leftAndRightRegistry.removeRightCandidatesForElement(element)
	}
	
	override void removeInvalidatedInstanceHalves(List<Object> leftElements, List<Object> rightElements) {
		val leftInstanceHalf = leftAndRightRegistry.getLeftInstanceHalfForElements(leftElements)
		val rightInstanceHalf = leftAndRightRegistry.getRightInstanceHalfForElements(rightElements)
		leftAndRightRegistry.removeLeftInstanceHalf(leftInstanceHalf)
		leftAndRightRegistry.removeRightInstanceHalf(rightInstanceHalf)
	}
	
	override void promoteValidatedCandidatesToInstances(List<Object> leftElements, List<Object> rightElements) {
		val leftCandidate = leftAndRightRegistry.getLeftCandidateForElements(leftElements)
		val rightCandidate = leftAndRightRegistry.getRightCandidateForElements(rightElements)
		leftAndRightRegistry.promoteLeftCandidateToInstanceHalf(leftCandidate)
		leftAndRightRegistry.promoteRightCandidateToInstanceHalf(rightCandidate)
	}
}