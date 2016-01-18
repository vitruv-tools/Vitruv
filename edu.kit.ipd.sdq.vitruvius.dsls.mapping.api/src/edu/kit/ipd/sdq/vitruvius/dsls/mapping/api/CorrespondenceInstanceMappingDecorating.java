package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceInstanceDecorating;

public class CorrespondenceInstanceMappingDecorating implements CorrespondenceInstanceDecorating {

	@Override
	public CorrespondenceInstanceDecorator decorate(CorrespondenceInstanceDecorator correspondenceInstance) {
		return new MappedCorrespondenceInstance(correspondenceInstance);
	}

}
