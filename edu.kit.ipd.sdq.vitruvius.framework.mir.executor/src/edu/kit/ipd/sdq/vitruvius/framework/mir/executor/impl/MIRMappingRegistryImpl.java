package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRModelInformationProvider;

public class MIRMappingRegistryImpl implements MIRMappingRegistry {
	private List<MIRMapping> mappings;
	private Map<Correspondence, MIRMapping> correspondence2mapping;
	
	public MIRMappingRegistryImpl() {
		mappings = new ArrayList<MIRMapping>();
		correspondence2mapping = new HashMap<Correspondence, MIRMapping>();
	}
	
	@Override
	public void addMapping(MIRMapping mapping) {
		mappings.add(mapping);
	}

	@Override
	public EMFChangeResult applyAllMappings(EChange eChange,
			CorrespondenceInstance correspondenceInstance,
			MIRModelInformationProvider modelInformationProvider) {
		EMFChangeResult result = new EMFChangeResult();
		for (MIRMapping mapping : mappings) {
			result.addChangeResult(mapping.applyEChange(eChange, correspondenceInstance, modelInformationProvider));
		}
		return result;
	}

	@Override
	public MIRMapping getMappingForCorrespondence(Correspondence correspondence) {
		return correspondence2mapping.get(correspondence);
	}

	@Override
	public void registerMappingForCorrespondence(MIRMapping mapping,
			Correspondence correspondence) {
		correspondence2mapping.put(correspondence, mapping);		
	}
}
