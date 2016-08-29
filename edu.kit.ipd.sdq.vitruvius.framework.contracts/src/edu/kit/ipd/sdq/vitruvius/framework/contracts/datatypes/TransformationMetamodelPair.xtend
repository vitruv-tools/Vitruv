package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI

final class TransformationMetamodelPair extends Pair<VURI, VURI> {

	new(VURI fromModel, VURI toModel) {
		super(fromModel, toModel);
	}
	
}