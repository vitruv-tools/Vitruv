package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRModelInformationProvider;

public class MIRMappingRegistryImpl implements MIRMappingRegistry {
	private List<MIRMapping> mappings;
	
	public MIRMappingRegistryImpl() {
		mappings = new ArrayList<MIRMapping>();
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
}
