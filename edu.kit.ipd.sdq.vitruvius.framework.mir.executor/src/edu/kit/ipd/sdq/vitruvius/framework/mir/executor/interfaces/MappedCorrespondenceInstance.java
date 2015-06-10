package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.ecore.EObject;

public interface MappedCorrespondenceInstance {
	public EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping);

	boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping);
}
