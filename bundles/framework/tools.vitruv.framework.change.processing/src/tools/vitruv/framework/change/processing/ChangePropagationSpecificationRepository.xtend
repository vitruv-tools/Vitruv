package tools.vitruv.framework.change.processing

import java.util.Map
import java.util.List
import java.util.HashMap
import java.util.ArrayList
import tools.vitruv.framework.domains.VitruvDomain

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	private Map<VitruvDomain, List<ChangePropagationSpecification>> metamodelToPropagationSpecifications;

	public new() {
		metamodelToPropagationSpecifications = new HashMap<VitruvDomain, List<ChangePropagationSpecification>>();
	}
	
	public override List<ChangePropagationSpecification> getChangePropagationSpecifications(VitruvDomain sourceDomain) {
		val result = new ArrayList<ChangePropagationSpecification>();
		if (metamodelToPropagationSpecifications.containsKey(sourceDomain)) {
			result.addAll(metamodelToPropagationSpecifications.get(sourceDomain));
		}
		return result;
	}
	
	public def void putChangePropagationSpecification(ChangePropagationSpecification changePropagationSpecification) {
		val changedDomain = changePropagationSpecification.sourceDomain;
		if (!this.metamodelToPropagationSpecifications.containsKey(changedDomain)) {
			this.metamodelToPropagationSpecifications.put(changedDomain, new ArrayList<ChangePropagationSpecification>());
		}
		val propagationSpecifications = this.metamodelToPropagationSpecifications.get(changedDomain);
		propagationSpecifications.add(changePropagationSpecification);
	}
	
	override iterator() {
		return metamodelToPropagationSpecifications.values.flatten.toList.iterator();
	}
	
}