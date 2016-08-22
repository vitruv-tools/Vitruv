package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api

import com.google.common.collect.Sets
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.Candidate
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.CandidateGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MappingRealization
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange

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