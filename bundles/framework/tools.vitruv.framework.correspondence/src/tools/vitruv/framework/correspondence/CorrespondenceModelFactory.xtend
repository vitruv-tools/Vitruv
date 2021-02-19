package tools.vitruv.framework.correspondence

import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl
import tools.vitruv.framework.util.command.CommandCreatorAndExecutor

final class CorrespondenceModelFactory {
	static CorrespondenceModelFactory instance;
	
	private new() {	}
	
	static def getInstance() {
		if (instance === null) {
			instance = new CorrespondenceModelFactory();
		}
		
		return instance;
	}
	
	def createCorrespondenceModel(UuidResolver uuidResolver, CommandCreatorAndExecutor modelCommandExecutor,
		VURI correspondencesVURI, Resource correspondencesResource) {
		return new InternalCorrespondenceModelImpl(uuidResolver, modelCommandExecutor, correspondencesVURI, correspondencesResource);
	}
}