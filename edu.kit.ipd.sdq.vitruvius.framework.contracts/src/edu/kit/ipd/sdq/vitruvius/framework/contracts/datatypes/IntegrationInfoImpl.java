package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.IntegrationInfo;

public class IntegrationInfoImpl implements IntegrationInfo{

	private static final long serialVersionUID = 1122038928041994203L;
	private boolean createdByIntegration;
	
	public IntegrationInfoImpl() {
		createdByIntegration = true;
	}
	
	public IntegrationInfoImpl(boolean createdByIntegration) {
		this.createdByIntegration = createdByIntegration;
	}

	@Override
	public boolean isCreatedByIntegration() {
		return createdByIntegration;
	}

}
