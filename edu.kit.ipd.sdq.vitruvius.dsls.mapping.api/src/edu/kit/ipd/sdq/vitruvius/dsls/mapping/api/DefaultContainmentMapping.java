package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreBridge;

public class DefaultContainmentMapping extends AbstractMappingRealization {
	public final static DefaultContainmentMapping INSTANCE = new DefaultContainmentMapping();
	
	@Override
	public void applyEChange(EChange eChange, Blackboard blackboard, MappingExecutionState state) {
		MIRMappingHelper.ensureContainments(state.getTransformationResult(), state::getAllAffectedEObjects, (objectToCreateContainmentFor) -> {
			state.addObjectForTuidUpdate(objectToCreateContainmentFor);
			
			VURI userChosenVuri = null;
			while ((userChosenVuri == null) || (EMFBridge.doesResourceExist(userChosenVuri.getEMFUri()))) {
				final URI userChosenUri = state.getUserInteracting().selectURI(EcoreBridge.createSensibleString(objectToCreateContainmentFor));
				if (userChosenUri != null) {
					userChosenVuri = VURI.getInstance(userChosenUri);
				}
			}
			
			state.getTransformationResult().addRootEObjectToSave(objectToCreateContainmentFor, userChosenVuri);
		});
		
		state.updateAllTuidsOfCachedObjects();
		state.persistAll();
	}

	@Override
	public String getMappingID() {
		return null;
	}
}
