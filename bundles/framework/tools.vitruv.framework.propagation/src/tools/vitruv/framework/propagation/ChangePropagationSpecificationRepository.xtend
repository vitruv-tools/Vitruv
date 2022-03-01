package tools.vitruv.framework.propagation

import java.util.Map
import java.util.List
import java.util.ArrayList
import java.util.HashMap

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	Map<String, List<ChangePropagationSpecification>> sourceMetamodelNsUriToPropagationSpecifications

	new(Iterable<ChangePropagationSpecification> specifications) {
		sourceMetamodelNsUriToPropagationSpecifications = new HashMap
		specifications.forEach [ specification |
			specification.sourceMetamodelRootNsUris.forEach [ nsUri |
				sourceMetamodelNsUriToPropagationSpecifications.computeIfAbsent(nsUri, [new ArrayList]).add(
					specification)
			]
		]
	}

	override List<ChangePropagationSpecification> getChangePropagationSpecifications(String sourceMetamodelRootNsUri) {
		val result = sourceMetamodelNsUriToPropagationSpecifications.get(sourceMetamodelRootNsUri)
		return if(result !== null) new ArrayList(result) else emptyList
	}

	override iterator() {
		return sourceMetamodelNsUriToPropagationSpecifications.values.flatten.toList.iterator()
	}
}
