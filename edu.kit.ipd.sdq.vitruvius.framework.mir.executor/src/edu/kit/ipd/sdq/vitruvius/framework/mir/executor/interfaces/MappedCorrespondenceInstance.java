package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public interface MappedCorrespondenceInstance {
	EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping);
	boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping);
	CorrespondenceInstance getCorrespondenceInstance();
	EObject getMappingTarget(EObject eObject, CorrespondenceInstance correspondenceInstance, MIRMappingRealization mapping);
	boolean unregisterMappingForCorrespondence(MIRMappingRealization mapping, Correspondence correspondence);
	void registerMappingForCorrespondence(MIRMappingRealization mapping, Correspondence correspondence);
	Collection<MIRMappingRealization> getMappingsForCorrespondence(Correspondence correspondence);
	
	SameTypeCorrespondence getMappedCorrespondence(EObject eObject, MIRMappingRealization abstractMIRMappingRealization);
	Pair<TUID, EObject> getCorrespondenceTarget(EObject eObject, Correspondence correspondence);
}
