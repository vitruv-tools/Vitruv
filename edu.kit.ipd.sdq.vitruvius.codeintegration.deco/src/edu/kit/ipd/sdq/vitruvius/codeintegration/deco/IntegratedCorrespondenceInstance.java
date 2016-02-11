package edu.kit.ipd.sdq.vitruvius.codeintegration.deco;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.IntegrationInfo;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;

public class IntegratedCorrespondenceInstance extends 
		AbstractDelegatingCorrespondenceInstanceDecorator<Map<Correspondence, IntegrationInfo>> {
	
	private Map<Correspondence, IntegrationInfo> correspondencesWithIntegrationInfo;

	@SuppressWarnings("unchecked")
	public IntegratedCorrespondenceInstance(CorrespondenceInstanceDecorator correspondenceInstance) {
		super(correspondenceInstance, 
				(Class<Map<Correspondence, IntegrationInfo>>) new HashMap<Correspondence, 
				IntegrationInfo>().getClass());
		correspondencesWithIntegrationInfo = new HashMap<Correspondence, IntegrationInfo>();
	}

	@Override
	protected String getDecoratorFileExtPrefix() {
		return "integrated_";
	}

	@Override
	protected Map<Correspondence, IntegrationInfo> getDecoratorObject() {
		return correspondencesWithIntegrationInfo;
	}

	@Override
	protected void initializeFromDecoratorObject(Map<Correspondence, IntegrationInfo> object) {
		correspondencesWithIntegrationInfo = object;
	}

	@Override
	protected void initializeWithoutDecoratorObject() {
		// empty
	}
	
	public boolean createdByIntegration(Correspondence correspondence) {
		if (correspondencesWithIntegrationInfo.containsKey(correspondence)) {
			IntegrationInfo integrationInfo = correspondencesWithIntegrationInfo.get(correspondence);
			if (integrationInfo.isCreatedByIntegration()) {
				return true;
			}
		}
		return false;
	}
	
	public void setIntegrationInfo(Correspondence correspondence, IntegrationInfo integrationInfo) {
		correspondencesWithIntegrationInfo.put(correspondence, integrationInfo);
	}

}
