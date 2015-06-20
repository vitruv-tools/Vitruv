package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;

public interface MappedCorrespondenceInstance {
	EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping);
	boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping);
	CorrespondenceInstance getCorrespondenceInstance();
	EObject getMappingTarget(EObject eObject, CorrespondenceInstance correspondenceInstance, MIRMappingRealization mapping);
	boolean unregisterMappingForCorrespondence(MIRMappingRealization mapping, Correspondence correspondence);
	void registerMappingForCorrespondence(MIRMappingRealization mapping, Correspondence correspondence);
	Collection<MIRMappingRealization> getMappingsForCorrespondence(Correspondence correspondence);
}
