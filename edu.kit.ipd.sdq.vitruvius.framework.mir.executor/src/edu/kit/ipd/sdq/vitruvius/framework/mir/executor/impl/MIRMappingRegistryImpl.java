package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRegistry;

public class MIRMappingRegistryImpl implements MIRMappingRegistry {
	private List<MIRMapping> mappings;
	
	public MIRMappingRegistryImpl() {
		mappings = new ArrayList<MIRMapping>();
	}
	
	@Override
	public MIRMapping findFirstMatchingMapping(EObject eObject,
			CorrespondenceInstance correspondenceInstance) {
		
		for (MIRMapping mapping : mappings) {
			if (mapping.doesMatch(eObject, correspondenceInstance)) {
				return mapping;
			}
		}
		
		return null;
	}

	@Override
	public void addMapping(MIRMapping mapping) {
		mappings.add(mapping);
	}

}
