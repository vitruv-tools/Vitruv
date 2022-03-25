package tools.vitruv.framework.propagation

import java.util.Map
import java.util.List
import java.util.ArrayList
import java.util.HashMap
import tools.vitruv.framework.change.Metamodel

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	Map<Metamodel, List<ChangePropagationSpecification>> sourceMetamodelToPropagationSpecifications

	new(Iterable<ChangePropagationSpecification> specifications) {
		sourceMetamodelToPropagationSpecifications = new HashMap
		specifications.forEach [ specification |
			sourceMetamodelToPropagationSpecifications.computeIfAbsent(specification.sourceMetamodel, [
				new ArrayList
			]).add(specification)
		]
	}

	override List<ChangePropagationSpecification> getChangePropagationSpecifications(Metamodel sourceMetamodel) {
		sourceMetamodelToPropagationSpecifications.keySet.filter[sourceMetamodel.contains(it)].flatMap [
			sourceMetamodelToPropagationSpecifications.get(it)
		].toList
	}

	override iterator() {
		return sourceMetamodelToPropagationSpecifications.values.flatten.toList.iterator()
	}
}
