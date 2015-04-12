package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;

public interface MIRMappingRegistry {
	public void addMapping(MIRMapping mapping);

	public EMFChangeResult applyAllMappings(EChange eChange,
			CorrespondenceInstance correspondenceInstance,
			MIRModelInformationProvider modelInformationProvider);
	
	/**
	 * Returns the MIRMapping that created a correspondence, or null if
	 * no mapping is coupled to the correspondence. To get all MIRMappings for an
	 * EObject, first get all correspondences from the {@link CorrespondenceInstance},
	 * then use this method.
	 * @param correspondence
	 * @return
	 */
	public MIRMapping getMappingForCorrespondence(Correspondence correspondence);
	
	/**
	 * Register a mapping for a correspondence that can then be retrieved by calling
	 * {@link #getMappingForCorrespondence(Correspondence)}.
	 * @param mapping
	 * @param correspondence
	 */
	public void registerMappingForCorrespondence(MIRMapping mapping, Correspondence correspondence);
}
