package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

public interface MappedCorrespondenceInstance {
	EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping);
	boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping);
	CorrespondenceInstance getCorrespondenceInstance();
}
