package tools.vitruv.extensions.dslsruntime.mappings.impl

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IElementsRegistry
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingInstanceHalf
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IMappingRegistry
import tools.vitruv.extensions.dslsruntime.mappings.interfaces.IBothSidesCombiningRegistry

/**
 * An object of this class maintains runtime information about instances of relevant
 * metaclasses, current instances and candidates for instances of a certain mapping
 * by using a registry for the metaclass instances (also called elements), and 
 * another registry that combines two registries for left and right instantiation halves and candidates
 * for these halves.
 */
class SetMultimapElementsAndHashMapCandidatesAndInstanceHalvesRegistry<L extends IMappingInstanceHalf, R extends IMappingInstanceHalf> implements IBothSidesCombiningRegistry<L,R>, IMappingRegistry<L,R> {
	@Accessors(PUBLIC_GETTER)
	val IElementsRegistry elementsRegistry
	@Accessors(PUBLIC_GETTER)
	@Delegate
	val HashMapLeftAndRightInstanceHalfRegistry<L,R> leftAndRightRegistry
	
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