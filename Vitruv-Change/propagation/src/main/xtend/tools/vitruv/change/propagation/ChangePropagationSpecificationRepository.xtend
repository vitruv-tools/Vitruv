package tools.vitruv.change.propagation

import java.util.Map
import java.util.List
import java.util.ArrayList
import java.util.HashMap
import tools.vitruv.change.composite.MetamodelDescriptor

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	Map<MetamodelDescriptor, List<ChangePropagationSpecification>> sourceMetamodelToPropagationSpecifications

	new(Iterable<ChangePropagationSpecification> specifications) {
		sourceMetamodelToPropagationSpecifications = new HashMap
		specifications.forEach [ specification |
			sourceMetamodelToPropagationSpecifications.computeIfAbsent(specification.sourceMetamodelDescriptor, [
				new ArrayList
			]).add(specification)
		]
	}

	override List<ChangePropagationSpecification> getChangePropagationSpecifications(
		MetamodelDescriptor sourceMetamodelDescriptor) {
		sourceMetamodelToPropagationSpecifications.keySet.filter[sourceMetamodelDescriptor.contains(it)].flatMap [
			sourceMetamodelToPropagationSpecifications.get(it)
		].toList
	}

	override iterator() {
		return sourceMetamodelToPropagationSpecifications.values.flatten.toList.iterator()
	}
}