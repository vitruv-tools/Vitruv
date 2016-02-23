package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

abstract class AbstractResponseChange2CommandTransformingProviding implements Change2CommandTransformingProviding {
	protected ClaimableMap<Pair<VURI, VURI>, Change2CommandTransforming> change2CommandTransformingsMap;
	
	new() {
		this.change2CommandTransformingsMap = new ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming>();
	}
	
	protected def void initializeChange2CommandTransformationMap(List<Change2CommandTransforming> transformationExecutingList) {
		for (transformationExecuting : transformationExecutingList) {
			val transformableMetamodels = transformationExecuting.getTransformableMetamodels();
			for (transformableMetamodel : transformableMetamodels) {
				this.change2CommandTransformingsMap.put(transformableMetamodel, transformationExecuting);
			}
		}
	}
	
	public override Change2CommandTransforming getChange2CommandTransforming(VURI mmURI1, VURI mmURI2) {
		val vuriPair = new Pair<VURI, VURI>(mmURI1, mmURI2);
		return this.change2CommandTransformingsMap.claimValueForKey(vuriPair);
	}
	
	public override iterator() {
		return change2CommandTransformingsMap.values().iterator();
	}
	
}