package edu.kit.ipd.sdq.vitruvius.codeintegration.deco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.IntegrationInfo;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;

public class IntegratedCorrespondenceInstance extends 
		AbstractDelegatingCorrespondenceInstanceDecorator<Map<Correspondence, IntegrationInfo>> {
	
	protected Logger logger = Logger.getLogger(IntegratedCorrespondenceInstance.class);
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
		Map<Correspondence, IntegrationInfo> resolvedCorrespondencesWithIntegrationInfo = new HashMap<Correspondence, IntegrationInfo>();
		//Set<Correspondence> allLoadedCorrs = this.getAllCorrespondencesWithoutDependencies();
		List<Correspondence> allLoadedCorrs = this.getAllCorrespondences();
		for (Entry<Correspondence, IntegrationInfo> entry : object.entrySet()) {
			Correspondence corrFromFile = entry.getKey();
			Optional<Correspondence> opt = allLoadedCorrs.stream().parallel().filter(c -> c.getATUIDs().equals(corrFromFile.getATUIDs()) && c.getBTUIDs().equals(corrFromFile.getBTUIDs())).findFirst();
			if (opt.isPresent()) {
				logger.info("Resolved corresponce while loading decorator. " + entry.getKey());
				Correspondence loadedCorrespondence = opt.get();
				resolvedCorrespondencesWithIntegrationInfo.put(loadedCorrespondence, entry.getValue());
			} else {
				logger.warn("No loaded correspondence was found for decorated correspondence " 
						+ entry.getKey() + ". Using serialized correspondence from decorator file.");
				resolvedCorrespondencesWithIntegrationInfo.put(entry.getKey(), entry.getValue());
			}
		}
		correspondencesWithIntegrationInfo = resolvedCorrespondencesWithIntegrationInfo;
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
