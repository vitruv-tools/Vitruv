package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

public interface MIRMappingRegistry {
	public void addMapping(MIRMapping mapping);

	public EMFChangeResult applyAllMappings(EChange eChange,
			CorrespondenceInstance correspondenceInstance,
			MIRModelInformationProvider modelInformationProvider);
}
