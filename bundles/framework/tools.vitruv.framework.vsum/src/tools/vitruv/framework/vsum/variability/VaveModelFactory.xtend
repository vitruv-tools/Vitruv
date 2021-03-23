package tools.vitruv.framework.vsum.variability

import tools.vitruv.framework.vsum.variability.VaveModelImpl
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource

final class VaveModelFactory {
		static VaveModelFactory instance;
	
	private new() {	}
	
	static def getInstance() {
		if (instance === null) {
			instance = new VaveModelFactory();
		}
		
		return instance;
	}
	
//	def createVaveModel() {
//		return new VaveModelImpl();
//	}
	
	def createVaveModel(UuidResolver uuidResolver, VURI vaveVURI, Resource vaveResource) {
		return new VaveModelImpl(uuidResolver, vaveVURI, vaveResource);
	}
}