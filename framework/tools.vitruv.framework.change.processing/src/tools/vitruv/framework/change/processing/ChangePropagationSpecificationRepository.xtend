package tools.vitruv.framework.change.processing

import tools.vitruv.framework.util.datatypes.VURI
import java.util.Map
import java.util.List
import java.util.HashMap
import java.util.ArrayList

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	private Map<VURI, List<ChangeProcessor>> metamodelToPropagationSpecifications;

	public new() {
		metamodelToPropagationSpecifications = new HashMap<VURI, List<ChangeProcessor>>();
	}
	
	public override List<ChangeProcessor> getChangePropagationSpecifications(VURI metamodelVuri) {
		val result = new ArrayList<ChangeProcessor>();
		if (metamodelToPropagationSpecifications.containsKey(metamodelVuri)) {
			result.addAll(metamodelToPropagationSpecifications.get(metamodelVuri));
		}
		return result;
	}
	
	public def void putChangePropagationSpecification(ChangeProcessor changePropagationSpecification) {
		val changedMetamodel = changePropagationSpecification.metamodelPair.first;
		if (!this.metamodelToPropagationSpecifications.containsKey(changedMetamodel)) {
			this.metamodelToPropagationSpecifications.put(changedMetamodel, new ArrayList<ChangeProcessor>());
		}
		val propagationSpecifications = this.metamodelToPropagationSpecifications.get(changedMetamodel);
		propagationSpecifications.add(changePropagationSpecification);
	}
	
	override iterator() {
		return metamodelToPropagationSpecifications.values.flatten.toList.iterator();
	}
	
}