package tools.vitruv.extensions.dslsruntime.mappings.registry

import org.eclipse.emf.ecore.EObject
import java.util.List

interface IElementsCandidatesAndInstanceHalvesRegistry<L extends MappingInstanceHalf, R extends MappingInstanceHalf> {

	def <C extends EObject> void removeLeftElementAndCandidates(Class<C> clazz, C element)
	def <C extends EObject> void removeRightElementAndCandidates(Class<C> clazz, C element)	
	def void removeInvalidatedInstanceHalves(List<Object> leftElements, List<Object> rightElements)
	def void promoteValidatedCandidatesToInstances(List<Object> leftElements, List<Object> rightElements)
}