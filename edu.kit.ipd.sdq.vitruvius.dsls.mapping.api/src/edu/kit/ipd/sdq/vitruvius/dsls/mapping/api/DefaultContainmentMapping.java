package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreBridge;

public class DefaultContainmentMapping extends AbstractMappingRealization {
	public final static DefaultContainmentMapping INSTANCE = new DefaultContainmentMapping();
	
	@Override
	public void applyEChange(EChange eChange, Blackboard blackboard, MappingExecutionState state) {
		MIRMappingHelper.ensureContainments(state.getTransformationResult(), state::getAllAffectedEObjects, (objectToCreateContainmentFor) -> {
			state.addObjectForTuidUpdate(objectToCreateContainmentFor);
			state.getTransformationResult().addRootEObjectToSave(objectToCreateContainmentFor, VURI.getInstance(
					state.getUserInteracting().selectURI(EcoreBridge.createSensibleString(objectToCreateContainmentFor))));
		});
		
		state.updateAllTuidsOfCachedObjects();
		state.persistAll();
	}

	@Override
	public String getMappingID() {
		return null;
	}
}
