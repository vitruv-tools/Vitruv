package tools.vitruv.extensions.dslsruntime.mapping;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import tools.vitruv.extensions.dslsruntime.mapping.MappingExecutionState;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard;
import tools.vitruv.framework.util.bridges.EcoreBridge;
import tools.vitruv.framework.util.datatypes.VURI;

public class DefaultContainmentMapping extends AbstractMappingRealization {
	public final static DefaultContainmentMapping INSTANCE = new DefaultContainmentMapping();
	
	@Override
	public void applyEChange(EChange eChange, Blackboard blackboard, MappingExecutionState state) {
		MIRMappingHelper.ensureContainments(state::getAllAffectedEObjects, (objectToCreateContainmentFor) -> {
			state.addObjectForTuidUpdate(objectToCreateContainmentFor);
			
			VURI userChosenVuri = null;
			while ((userChosenVuri == null) || (URIUtil.existsResourceAtUri(userChosenVuri.getEMFUri()))) {
				final URI userChosenUri = state.getUserInteracting().selectURI(EcoreBridge.createSensibleString(objectToCreateContainmentFor));
				if (userChosenUri != null) {
					userChosenVuri = VURI.getInstance(userChosenUri);
				}
			}
			
			state.getResourceAccess().persistAsRoot(objectToCreateContainmentFor, userChosenVuri);
		});
		
		state.updateAllTuidsOfCachedObjects();
		state.persistAll();
	}

	@Override
	public String getMappingID() {
		return null;
	}
}
