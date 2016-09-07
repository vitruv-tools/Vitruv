package tools.vitruvius.extensions.dslsruntime.mapping

import com.google.common.collect.Sets
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.Candidate
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.CandidateGenerator
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MappingRealization
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EClass
import tools.vitruvius.framework.change.echange.EChange

class CandidateGeneratorImpl implements CandidateGenerator {
	public override Set<Candidate> createCandidates(EChange eChange, List<MappingRealization> requiredMappings,
		List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceModel mci) {

		val requiredMappingsInstances = requiredMappings.map[mci.getCorrespondencesForMapping(it)].toList

		val allAffectedObjects = MIRMappingHelper.getAllAffectedObjects(eChange);
		val resources = allAffectedObjects.map[eResource].filterNull.toSet

		val elements = elementTypes.map [ type |
			resources.map[res|res.getAllContents.filter[eObj|type.isInstance(eObj)].toSet].flatten.toSet
		]

		val requiredProduct = Sets.cartesianProduct(requiredMappingsInstances)
		val elementsProduct = Sets.cartesianProduct(elements)

		val result = newHashSet
		for (requiresInstance : requiredProduct) {
			for (elementsInstance : elementsProduct) {
				result.add(new Candidate(requiresInstance, elementsInstance))
			}
		}

		return result;
	}
}