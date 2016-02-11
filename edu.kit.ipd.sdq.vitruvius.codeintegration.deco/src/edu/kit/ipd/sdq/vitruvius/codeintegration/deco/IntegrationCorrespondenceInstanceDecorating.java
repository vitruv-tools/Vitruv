package edu.kit.ipd.sdq.vitruvius.codeintegration.deco;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceInstanceDecorating;

public class IntegrationCorrespondenceInstanceDecorating implements CorrespondenceInstanceDecorating {

	@Override
	public CorrespondenceInstanceDecorator decorate(CorrespondenceInstanceDecorator correspondenceInstance) {
		return new IntegratedCorrespondenceInstance(correspondenceInstance);
	}

}
