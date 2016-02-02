package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MIRUserInteracting;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreBridge;

public class DefaultContainmentMapping extends AbstractMappingRealization {
	public final static DefaultContainmentMapping INSTANCE = new DefaultContainmentMapping();
	
	private MIRUserInteracting userInteracting = new EclipseDialogMIRUserInteracting();

	public void setUserInteracting(MIRUserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	@Override
	public void applyEChange(EChange eChange, Blackboard blackboard, MappingExecutionState state) {
		MIRMappingHelper.ensureContainments(state, state::getAllAffectedEObjects, (objectToCreateContainmentFor) -> {
			state.addObjectForTuidUpdate(objectToCreateContainmentFor);
			state.addRootEObjectToSave(objectToCreateContainmentFor, VURI.getInstance(
					userInteracting.askForNewResource(EcoreBridge.createSensibleString(objectToCreateContainmentFor))));
		});
		
		state.updateAllTuidsOfCachedObjects();
		state.persistAll();
	}

	@Override
	public String getMappingID() {
		return null;
	}

	@Override
	public MappingRealization getInstance() {
		return null;
	}
}
