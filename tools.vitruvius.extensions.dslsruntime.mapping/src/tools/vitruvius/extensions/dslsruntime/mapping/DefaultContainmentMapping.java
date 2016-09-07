package tools.vitruvius.extensions.dslsruntime.mapping;

import org.eclipse.emf.common.util.URI;

import tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard;
import tools.vitruvius.framework.util.bridges.EMFBridge;
import tools.vitruvius.framework.util.bridges.EcoreBridge;
import tools.vitruvius.framework.util.datatypes.VURI;

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
