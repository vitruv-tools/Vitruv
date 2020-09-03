package tools.vitruv.framework.correspondence

import tools.vitruv.framework.tuid.TuidResolver
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
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
	
	def createCorrespondenceModel(TuidResolver tuidResolver, UuidResolver uuidResolver, CommandCreatorAndExecutor modelCommandExecutor,
		VitruvDomainRepository domainRepository, VURI correspondencesVURI, Resource correspondencesResource) {
		return new InternalCorrespondenceModelImpl(tuidResolver, uuidResolver, modelCommandExecutor, domainRepository, correspondencesVURI, correspondencesResource);
	}
}