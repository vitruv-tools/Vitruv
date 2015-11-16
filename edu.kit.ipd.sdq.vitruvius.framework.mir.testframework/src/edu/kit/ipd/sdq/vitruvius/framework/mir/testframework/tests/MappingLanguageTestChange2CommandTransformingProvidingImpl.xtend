package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair

class MappingLanguageTestChange2CommandTransformingProvidingImpl extends Change2CommandTransformingProvidingImpl {
	new() {
		this.transformationExecuterMap = new ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming>();
		// do not add instances from extension points
	}
	
	def addChange2CommandTransforming(Change2CommandTransforming transformationExecuting) {
        val transformableMetamodels = transformationExecuting.getTransformableMetamodels();
        for (transformableMetamodel : transformableMetamodels) {
            this.transformationExecuterMap.put(transformableMetamodel, transformationExecuting);
        }
	}
}