package edu.kit.ipd.sdq.vitruvius.framework.mir.executor;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceInstanceDecorating;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;

public class CorrespondenceInstanceMappingDecorating implements CorrespondenceInstanceDecorating {

	@Override
	public CorrespondenceInstanceDecorator decorate(CorrespondenceInstanceDecorator correspondenceInstance) {
		return new MappedCorrespondenceInstance(correspondenceInstance);
	}

}
