package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

public interface MIRMappingRegistry {
	public MIRMapping findFirstMatchingMapping(EObject eObject, CorrespondenceInstance correspondenceInstance);

	public void addMapping(MIRMapping mapping);
}
